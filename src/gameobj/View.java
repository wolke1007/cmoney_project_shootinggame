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
import util.Point;

/**
 *
 * @author Cloud-Razer
 */
public class View extends GameObject{

    private int dir;
    private boolean isStand;
    
    private Delay moveDelay;
    
    private float moveSpeed; // per frame
    private float actMoveSpeed;
    
    private float width;
    private float height;

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
//        if (!this.isStand && this.moveDelay.isTrig()) {
//            move();
//        }
    }
    
    public void move(){
        // view 其實不需要移動
//        Point destination = this.movement.getDestination(false);
//        if(destination != null){
//            this.offset(destination);
//        }
    }
    
    @Override
    public void paintComponent(Graphics g) {
        g.drawLine(0, (int)this.getCenterY(), 1000, (int)this.getCenterY());
        g.drawLine((int)this.getCenterX(), 0, (int)this.getCenterX(), 1000);
        return;
    }
    
}
