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

    public Actor(int[] steps,int x, int y, int moveSpeed, String src) {//src => Global.ACTOR
        super(x, y, Global.UNIT_X, Global.UNIT_Y, Global.UNIT_X, Global.UNIT_Y);
        setWidth(Global.UNIT_X);
        setHeight(Global.UNIT_Y);
        this.renderer = new RendererToRotate(src, super.getX(), super.getY(), Global.mouseX, Global.mouseY);
        this.isStand = true;
        this.view = new View(Global.ACTOR_X - (Global.VIEW_WIDTH / 2 - Global.UNIT_X / 2),
                            Global.ACTOR_Y - (Global.VIEW_HEIGHT / 2 - Global.UNIT_X / 2),
                            Global.VIEW_WIDTH, Global.VIEW_HEIGHT
                            );
        setActorMoveSpeedDetail(moveSpeed);
        movement = new Move(this);
    }//多載 建構子 當前版本
    
    public Move movement(){
        return this.movement;
    }

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

    public void setDir(int dir) {
        this.dir = dir;
        this.view.setDir(dir);
    }
    
    public int centerX(){
        return super.getX() + this.width / 2;
    }
    public int centerY(){
        return this.getY() + this.height / 2;
    }
    
    @Override
    public void update() {
        this.renderer.setCenterX(super.getX());
        this.renderer.setCenterY(super.getY());
        this.renderer.setGoalCenterX(Global.mouseX);
        this.renderer.setGoalCenterY(Global.mouseY);
        renderer.update();
        //以上一定要更新
        
        if (!this.isStand && this.moveDelay.isTrig()) {
            move();
        }
    }

    private void move() {
        this.movement.doMoving();
        this.view.move();
    }

    @Override
    public void paintComponent(Graphics g) {
        this.renderer.paint(g, super.getX(), super.getY(), this.width, this.height);
        this.view.paint(g);
    }

}
