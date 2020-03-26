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
    private int moveSpeed = 59; // per frame
    
    private int x;
    private int y;
    private int width;
    private int height;

    public Actor(int serial, int[] steps, int x, int y, View view) {
        super(x, y, Global.UNIT_X, Global.UNIT_Y, Global.UNIT_X, Global.UNIT_Y);
        this.x = x;
        this.y = y;
        this.width = Global.UNIT_X;
        this.height = Global.UNIT_Y;
        this.renderer = new Renderer(serial, steps, 60 - this.moveSpeed, Global.ACTOR);
        this.isStand = true;
        this.view = view;
        this.moveDelay = new Delay(60 - this.moveSpeed);
        this.moveDelay.start();
    }
    
    public void setStand(boolean isStand){
        this.isStand = isStand;
        if(this.isStand){
            this.moveDelay.stop();
        }else{
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
        if(!this.isStand && this.moveDelay.isTrig()){
            move();
            // 利用 setX setY 讓 debug 的方形碰撞偵測框跟著 Actor 實體移動
            setX(this.x + this.width/ 2);
            setY(this.y + this.height / 2);
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
