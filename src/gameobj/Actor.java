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

            
    public Actor(int serial, int[] steps, int x, int y, View view) {
        super(x, y, Global.UNIT_X, Global.UNIT_Y, Global.UNIT_X, Global.UNIT_Y);
        this.x = x;
        this.y = y;
        this.moveSpeed = 59;
        this.width = Global.UNIT_X;
        this.height = Global.UNIT_Y;
        this.renderer = new Renderer(serial, steps, 60 - this.moveSpeed, Global.ACTOR);
        this.isStand = true;
        this.view = view;
        this.moveDelay = new Delay(60 - this.moveSpeed);
        this.moveDelay.start();
        movement = new Move(this);
    }//老師版本 (未來可以改圖及呈現方式)

    public Actor(int[] steps,int x, int y, int moveSpeed, String src) {//src => Global.ACTOR
        super(x, y, Global.UNIT_X, Global.UNIT_Y, Global.UNIT_X, Global.UNIT_Y);
        setX(x);
        setY(y);
        setWidth(Global.UNIT_X);
        setHeight(Global.UNIT_Y);

        this.renderer = new RendererToRotate(src, super.getX(), super.getY(), Global.mouseX, Global.mouseY);
        this.isStand = true;
        setView(view);
        setActorMoveSpeedDetail(moveSpeed);
        movement = new Move(this);
    }//多載 建構子 當前版本
    
    public Move movement(){
        return this.movement;
    }

    @Override
    public void setX(int x) {
        this.x = x;
        super.setX(x);
    }

    @Override
    public void setY(int y) {
        this.y = y;
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
        return this.x + this.width / 2;
    }
    public int centerY(){
        return this.y + this.height / 2;
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
            // 利用 setX setY 讓 debug 的方形碰撞偵測框跟著 Actor 實體移動
            super.offset(centerX(), centerY());
        }
    }

    private void move() {
        int speed = 4; // 這邊 hardcode  寫死了，如果未來有需要這邊要開接口出來調整速度
        Rect act = this.rect;
        Rect view = this.view.rect;
//        this.movement.doMoving();

//        switch (this.dir) {
//            case Global.UP: // go up
//                moveFourWay(this.dir);
//                break;
//            case Global.DOWN: // go down
//                moveFourWay(this.dir);
//                break;
//            case Global.LEFT: // go left
//                moveFourWay(this.dir);
//                break;
//            case Global.RIGHT: // go right
//                moveFourWay(this.dir);
//                break;
//            case Global.UP_LEFT: // go up-left
//                if(act.screenEdgeCheck("up") || act.screenEdgeCheck("left")){
//                    if(!act.screenEdgeCheck("up")){ moveFourWay(1); }
//                    if(!act.screenEdgeCheck("left")){ moveFourWay(4); }
//                }else{
//                    this.offset(this.x -= Global.UNIT_X / speed, this.y -= Global.UNIT_Y / speed);
//                }
//                if(!view.screenEdgeCheck("up")){
//                    this.view.move(this.dir);
//                }
//                break;
//            case Global.UP_RIGHT: // go up-right
//                if(act.screenEdgeCheck("up") || act.screenEdgeCheck("right")){
//                    if(!act.screenEdgeCheck("up")){ moveFourWay(1); }
//                    if(!act.screenEdgeCheck("right")){ moveFourWay(7); }
//                }else{
//                    this.offset(this.x += Global.UNIT_X / speed, this.y += Global.UNIT_Y / speed);
//                }
//                if(!view.screenEdgeCheck("up")){
//                    this.view.move(this.dir);
//                }
//                break;
//            case Global.DOWN_LEFT: // go down-left
//                if(act.screenEdgeCheck("up") || act.screenEdgeCheck("right")){
//                    if(!act.screenEdgeCheck("up")){ moveFourWay(1); }
//                    if(!act.screenEdgeCheck("right")){ moveFourWay(7); }
//                }else{
//                    this.offset(this.x += Global.UNIT_X / speed, this.y += Global.UNIT_Y / speed);
//                }
//                if(!view.screenEdgeCheck("up")){
//                    this.view.move(this.dir);
//                }
//                break;
//            case Global.DOWN_RIGHT: // go down-right
//                if(act.screenEdgeCheck("up") || act.screenEdgeCheck("right")){
//                    if(!act.screenEdgeCheck("up")){ moveFourWay(1); }
//                    if(!act.screenEdgeCheck("right")){ moveFourWay(7); }
//                }else{
//                    this.offset(this.x += Global.UNIT_X / speed, this.y += Global.UNIT_Y / speed);
//                }
//                if(!view.screenEdgeCheck("up")){
//                    this.view.move(this.dir);
//                }
//                break;
//        }
    }
    
//    private void moveFourWay(int dir){
//        Rect act = this.rect;
//        Rect view = this.view.rect;
//        switch (this.dir) {
//            case 1: // go up
//                if(!act.screenEdgeCheck("up")){
//                    this.offset(this.x, this.y -= Global.UNIT_Y / speed);
//                }
//                if(!view.screenEdgeCheck("up")){
//                    this.view.move(this.dir);
//                }
//                break;
//            case 2: // go down
//                if(!act.screenEdgeCheck("down")){
//                    this.offset(this.x, this.y += Global.UNIT_Y / speed);
//                }
//                if(!view.screenEdgeCheck("down")){
//                    this.view.move(this.dir);
//                }   
//                break;
//            case 4: // go left
//                if(!act.screenEdgeCheck("left")){
//                    this.offset(this.x -= Global.UNIT_X / speed, this.y);
//                }
//                if(!view.screenEdgeCheck("left")){
//                    this.view.move(this.dir);
//                }   
//                break;
//            case 7: // go right
//                if(!act.screenEdgeCheck("right")){
//                    this.offset(this.x += Global.UNIT_X / speed, this.y);
//                }
//                if(!view.screenEdgeCheck("right")){
//                    this.view.move(this.dir);
//                }   
//                break;
//        }
//    }
    
//    private boolean isTwoTrigger(){
//        return true;
//    }

    @Override
    public void paintComponent(Graphics g) {
        this.renderer.paint(g, super.getX(), super.getY(), this.width, this.height);
    }

}
