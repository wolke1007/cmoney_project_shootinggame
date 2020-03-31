/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameobj;

import java.awt.Graphics;
import util.Delay;
import util.Global;
import util.Move;

/**
 *
 * @author Cloud-Razer
 */
public class View extends GameObject{

    private int dir;
    private boolean isStand;
    
    private Delay moveDelay;
    
    private int moveSpeed; // per frame
    private int actMoveSpeed;
    
    private int width;
    private int height;

    private Move movement;
    
    public View(int x, int y, int moveSpeed, int width, int height) {
        super("rect", x, y, width, height, width, height);
        Global.log("rec x" + x + " ,y" + y + " ,width:" + width + " ,height:" + height);
        this.width = width;
        this.height = height;
        this.isStand = true;
        setViewMoveSpeedDetail(moveSpeed);
        movement = new Move(this);
    }

    public Move movement(){
        return this.movement;
    }
   
    @Override
    public void setDir(int dir) {
        Global.log("view set dir:"+dir);
        this.dir = dir;
    }
    
    public void setStand(boolean isStand){
        this.isStand = isStand;
    }
    
    @Override
    public void setMovementPressedStatus(int dir, boolean status) {
        this.movement().setPressedStatus(dir, status);
    }
    
    private void setViewMoveSpeedDetail(int moveSpeed) {
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

    @Override
    public void update() {
        if (!this.isStand && this.moveDelay.isTrig()) {
            move();
        }
    }
    
    public void move(){
        this.movement.moving();
    }

    @Override
    public void paintComponent(Graphics g) {
        return;
    }
    
}
