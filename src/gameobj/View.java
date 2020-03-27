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
//    private Renderer renderer;
    private boolean isStand;
    
//    private Delay moveDelay;
//    private int moveSpeed = 59; // per frame
    
    private int x;
    private int y;
    private int width;
    private int height;

    private Move movement;
    
    public View(int x, int y, int width, int height) {
        super(x, y, width, height, width, height);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.isStand = true;
        movement = new Move(this);
    }

    public boolean isMeetScreenEdge(){
        return this.rect.intersects(0, 0, Global.FRAME_X, Global.FRAME_Y);
    }
    
    public void setDir(int dir) {
        this.dir = dir;
    }
    
    public void setStand(boolean isStand){
        this.isStand = isStand;
    }
    
    public void move(){
        this.movement.doMoving();
    }
    
    @Override
    public void update() {
        setX(this.x + this.width/ 2);
        setY(this.y + this.height / 2);
    }

    @Override
    public void paintComponent(Graphics g) {
        return;
    }
    
}
