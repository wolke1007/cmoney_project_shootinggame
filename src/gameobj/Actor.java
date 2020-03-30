/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameobj;

import graph.Rect;
import java.awt.Color;
import java.awt.Graphics;
import util.Delay;
import util.Global;
import util.Move;
import util.Point;

/**
 *
 * @author Cloud-Razer
 */
public class Actor extends GameObject {

    private int dir;
    private RendererToRotate renderer;
    private boolean isStand;
    private View view;

    private Delay moveDelay;

    private int moveSpeed; // per frame
    private int actMoveSpeed;

    private int width;
    private int height;

    private Move movement;
    private Map[] currentViewMap;

    public Actor(int[] steps, int x, int y, int moveSpeed, String src, Map[] currentViewMap) {//src => Global.ACTOR
        super("rect", x, y, Global.UNIT_X, Global.UNIT_Y, Global.UNIT_X, Global.UNIT_Y);
        setWidth(Global.UNIT_X);
        setHeight(Global.UNIT_Y);
        this.currentViewMap = currentViewMap;
        this.renderer = new RendererToRotate(src, super.getX(), super.getY(), Global.mouseX, Global.mouseY);
        this.isStand = true;
        setActorMoveSpeedDetail(moveSpeed);
        movement = new Move(this);
    }//多載 建構子 當前版本

    @Override
    public void setX(int x) {
        super.setX(x);
    }

    @Override
    public void setY(int y) {
        super.setY(y);
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    private void setActorMoveSpeedDetail(int moveSpeed) {
        this.moveSpeed = limitRange(moveSpeed);
        this.actMoveSpeed = 60 - this.moveSpeed;
        this.moveDelay = new Delay(this.actMoveSpeed);
        this.moveDelay.start();
    }//初始化用

    private int limitRange(int range) {
        if (range < 0) {
            return range = 0;
        } else if (range > 60) {
            return range = 60;
        }
        return range;
    }//限制範圍 0-60

    public void setMoveSpeed(int moveSpeed) {
        this.moveSpeed = limitRange(moveSpeed);
        this.actMoveSpeed = 60 - this.moveSpeed;
        this.moveDelay.setDelayFrame(this.actMoveSpeed);
    }//修改角色移動速度

    public int getMoveSpeed() {
        return this.moveSpeed;
    }//取得目前的速度設定

    public void setStand(boolean isStand) {
        this.isStand = isStand;
        if (this.isStand) {
            this.moveDelay.stop();
        } else {
            this.moveDelay.start();
        }
    }

    @Override
    public void setDir(int dir) {
        this.dir = dir;
    }

    public int getCenterX() {
        return super.getX() + this.width / 2;
    }

    public int getCenterY() {
        return this.getY() + this.height / 2;
    }

    @Override
    public void setMovementPressedStatus(int dir, boolean status) {
        this.movement.setPressedStatus(dir, status);
    }

    @Override
    public void update() {
        if (!this.isStand && this.moveDelay.isTrig()) {
            move();
        }
        // 需要先移動再 RenderToRotate 避免 Actor 的圖片跟不上碰撞框的移動
        // 以下一定要更新
        this.renderer.setCenterX(super.getX());
        this.renderer.setCenterY(super.getY());
        this.renderer.setGoalCenterX(Global.mouseX);
        this.renderer.setGoalCenterY(Global.mouseY);
        renderer.update();
    }

    private void move() {
        // 這段邏輯應該也要搬進 game loop 的 update 中做判斷，理論上才能避免一頭插進牆壁裡面
        Point destination = this.movement.getDestination(false);
        Point correctedDest = null;
        if(destination != null){
            correctedDest = this.movement.correctedDest(destination); // 如果移動會超過要阻止
        }else{
            return;
        }
        if(correctedDest != null){
            Global.log("correct dest");
            this.movement.moving(correctedDest);
        }else{
            Global.log("moving false");
            this.movement.moving(this.movement.getDestination(false));
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        this.renderer.paint(g, super.getX(), super.getY(), this.width, this.height);
    }

}
