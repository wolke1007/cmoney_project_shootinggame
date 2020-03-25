/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameobj;

import java.awt.Graphics;
import util.Delay;
import util.Global;

/**
 *
 * @author Cloud-Razer
 */
public class Map extends GameObject {
    
    private int dir;
    private Renderer renderer;
    private boolean isStand;
    
    private Delay moveDelay;
    private int moveSpeed = 59; // per frame
    
    private int x;
    private int y;
    private int width;
    private int height;
    
    public Map(int serial, int x, int y){
//        super(x, y, Global.FRAME_X, Global.FRAME_Y, Global.FRAME_X, Global.FRAME_Y);
        super(x, y, Global.SCREEN_X - 2, Global.SCREEN_Y - 2, Global.SCREEN_X - 2, Global.SCREEN_Y - 2);
        this.x = x;
        this.y = y;
        this.width = Global.SCREEN_X;
        this.height = Global.SCREEN_Y;
        this.renderer = new Renderer(serial, new int[]{0}, 60 - moveSpeed, Global.BACKGROUND);
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
            setX(this.x + this.width/ 2);
            setY(this.y + this.height / 2);
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
        setX(this.x + this.width/ 2);
        setY(this.y + this.height / 2);
        this.renderer.paint(g, this.x, this.y, this.width, this.height);
    }

}