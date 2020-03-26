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

/**
 *
 * @author Cloud-Razer
 */
public class Actor extends GameObject {

    private int dir;
    private Renderer renderer;
    private boolean isStand;
    private View view;
    
    private Delay moveDelay;
    private int rendererToDelay;//換畫面的delay接口
    private int actPaintRendererToDelay;
    private int moveSpeed; // per frame
    private int actMoveSpeed;

    private int x;
    private int y;
    private int width;
    private int height;

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
    }//老師版本 (未來可以改圖及呈現方式)

    public Actor(int[] steps,int x, int y, int moveSpeed, String src) {//src => Global.ACTOR
        super(x, y, Global.UNIT_X, Global.UNIT_Y, Global.UNIT_X, Global.UNIT_Y);
        setActX(x);
        setActY(y);
        setWidth(Global.UNIT_X);
        setHeight(Global.UNIT_Y);
        setActPaintRendererToDelay(steps,this.rendererToDelay, src);
        setStand(true);
        setActorMoveSpeedDetail(moveSpeed);
    }//多載 建構子 當前版本

    public void setActX(int x) {
        this.x = x;
    }

    public void setActY(int y) {
        this.y = y;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    private void setActPaintRendererToDelay(int[] steps,int rendererToDelay, String src) {
        this.rendererToDelay = limitRange(rendererToDelay);
        this.actPaintRendererToDelay = 60 - this.rendererToDelay;
        this.renderer = new Renderer(steps,this.actPaintRendererToDelay, src);
    }//初始化用

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
    
    public void setRendererToDelay(int rendererToDelay){
        this.rendererToDelay = limitRange(rendererToDelay);
        this.actPaintRendererToDelay = 60 - this.rendererToDelay;
        this.renderer.setDelay(rendererToDelay);
    }//修改畫面更新速度 // 後續應修改成按照按鍵計算時間
    
    public int  getRendererToDelay(){
        return this.rendererToDelay;
    }//取得畫面更新速度

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
        this.renderer.setDir(dir);
        this.view.setDir(dir);
    }

    @Override
    public void update() {
        renderer.update();
        if (!this.isStand && this.moveDelay.isTrig()) {
            move();
            // 利用 setX setY 讓 debug 的方形碰撞偵測框跟著 Actor 實體移動
            super.setX(this.x + this.width / 2);
            super.setY(this.y + this.height / 2);
        }
    }

    private void move() {
        int speed = 4;
        Rect act = this.rect;
        Rect view = this.view.rect;
        switch (this.dir) {
            case Global.UP:
                if(!act.screenEdgeCheck("up")){
                    this.y -= Global.UNIT_Y / speed;
                }
                if(!view.screenEdgeCheck("up")){
                    this.view.move(this.dir);
                }
                break;
            case Global.DOWN:
                if(!act.screenEdgeCheck("down")){
                    this.y += Global.UNIT_Y / speed;
                }
                if(!view.screenEdgeCheck("down")){
                    this.view.move(this.dir);
                }   
                break;
            case Global.LEFT:
                if(!act.screenEdgeCheck("left")){
                    this.x -= Global.UNIT_X / speed;
                }
                if(!view.screenEdgeCheck("left")){
                    this.view.move(this.dir);
                }   
                break;
            case Global.RIGHT:
                if(!act.screenEdgeCheck("right")){
                    this.x += Global.UNIT_X / speed;
                }
                if(!view.screenEdgeCheck("right")){
                    this.view.move(this.dir);
                }   
                break;
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        setX(this.x + this.width/ 2);
        setY(this.y + this.height / 2);
        this.renderer.paint(g, this.x, this.y, this.width, this.height);
    }

}
