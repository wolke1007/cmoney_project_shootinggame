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

    private float moveSpeed; // per frame
    private float actMoveSpeed;

    private Move movement;
    private Map[] currentViewMap;
    
    private int moveDistance;

    public Actor(String colliderType, float x, float y, int moveSpeed, String[] path, Map[] currentViewMap) {//src => Global.ACTOR
        super(colliderType, x, y, Global.UNIT_X, Global.UNIT_Y, Global.UNIT_X, Global.UNIT_Y);
        this.currentViewMap = currentViewMap;
        this.renderer = new RendererToRotate(path, super.getX(), super.getY(), Global.mouseX, Global.mouseY);
        this.isStand = true;
        setActorMoveSpeedDetail(moveSpeed);
        movement = new Move(this);
        this.moveDistance = 10;
    }//多載 建構子 當前版本

    @Override
    public void setX(float x) {
        super.setX(x);
    }

    @Override
    public void setY(float y) {
        super.setY(y);
    }
    
    private void setMoveDistance(int moveDistance){
        this.moveDistance = moveDistance;
    }
    private int getMoveDistance(){
        return this.moveDistance;
    }

    private void setActorMoveSpeedDetail(float moveSpeed) {
        this.moveSpeed = limitRange(moveSpeed);
        this.actMoveSpeed = 60 - this.moveSpeed;
        this.moveDelay = new Delay(this.actMoveSpeed);
        this.moveDelay.start();
    }//初始化用

    private float limitRange(float range) {
        if (range < 0) {
            return range = 0;
        } else if (range > 60) {
            return range = 60;
        }
        return range;
    }//限制範圍 0-60

    public void setMoveSpeed(float moveSpeed) {
        this.moveSpeed = limitRange(moveSpeed);
        this.actMoveSpeed = 60 - this.moveSpeed;
        this.moveDelay.setDelayFrame(this.actMoveSpeed);
    }//修改角色移動速度

    public float getMoveSpeed() {
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

    public float centerX() {
        return super.getCenterX();
    }

    public float centerY() {
        return super.getCenterY();
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
        this.renderer.setX(super.getX());
        this.renderer.setY(super.getY());
        this.renderer.setGoalCenterX(Global.mouseX);
        this.renderer.setGoalCenterY(Global.mouseY);
        this.renderer.update();
    }

    private void move() {
        this.movement.moving(this.moveDistance);
    }

    @Override
    public void paintComponent(Graphics g) {
        this.renderer.paint(g);
    }

}
