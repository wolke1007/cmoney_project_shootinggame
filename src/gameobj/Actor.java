/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameobj;

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
    
    private Delay moveDelay;
    private int moveSpeed = 59; // per frame
    
    private int x;
    private int y;
    private int width;
    private int height;

    public Actor(int serial, int[] steps, int x, int y) {
        super(x, y, Global.UNIT_X, Global.UNIT_Y, Global.UNIT_X, Global.UNIT_Y);
        this.x = x;
        this.y = y;
        this.width = Global.UNIT_X;
        this.height = Global.UNIT_Y;
        this.renderer = new Renderer(serial, steps, 60 - moveSpeed);
        this.isStand = true;
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
    }

    @Override
    public void update() {
        renderer.update();
        if(!this.isStand && this.moveDelay.isTrig()){
            move();
            // 利用 setX setY 讓 debug 的方形碰撞偵測框跟著 Actor 實體移動
            setX(this.x);
            setY(this.y);
        }
        
    }

    private void move() {
        switch (this.dir) {
            case Global.LEFT:
                this.x -= Global.UNIT_X / 4;
                break;
            case Global.UP:
                this.y -= Global.UNIT_Y / 4;
                break;
            case Global.RIGHT:
                this.x += Global.UNIT_X / 4;
                break;
            case Global.DOWN:
                this.y += Global.UNIT_Y / 4;
                break;
        }
    }
    
    @Override
    public void paintComponent(Graphics g) {
        this.renderer.paint(g, this.rect.left(), this.rect.top(), this.rect.width(), this.rect.height());
    }

}
