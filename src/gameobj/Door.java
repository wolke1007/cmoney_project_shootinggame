/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameobj;

import controllers.ImagePath;
import static gameobj.Map.r;
import java.awt.Graphics;
import renderer.Renderer;
import util.Delay;
import util.Global;

/**
 *
 * @author Cloud-Razer
 */
public class Door extends Barrier {

    protected String name;
    private boolean open;
    private Delay delay;
    private float closeDoorY;
    private Renderer renderer;
    private Wall wall;
    private float originalY; // 初始時的下死點
    
    public Door(float x, float y, int width, int height, String name) {
        super("rect", x, y, width, height);
        this.setType("Door");
        this.name = name;
        this.open = false;
        this.delay = new Delay(5);
        this.delay.start();
        this.closeDoorY = this.y;
        this.renderer = r(ImagePath.DOOR[0]);
        this.wall = new Wall("rect", (int)this.x, (int)this.y, (int)super.width(), (int)super.height());
        this.originalY = this.y;
    }
    
    public float getOriginalY(){
        return this.originalY;
    }
    
    public boolean isOpen(){
        return this.open;
    }
    
    public void open(){
        Global.log("door been open");
        this.open = true;
    }
    
    public void close(){
        Global.log("door been close");
        this.open = false;
    }
    
    public Wall getInvisibleWall(){
        return this.wall;
    }
    
    public void deleteInvisibleWall(){ // TODO work around  因為刪不掉所以直接移出視窗外，哎呀.....
        this.wall.setXY(-100, -100);
    }
    
    @Override
    public void update(){
        if(this.open && this.delay.isTrig() && this.closeDoorY - this.height() < this.y){
//            this.offset(0, -2);
            this.offset(0, -20);
        }
        if(!this.open && this.delay.isTrig() && this.closeDoorY > this.y){
            this.offset(0, 2);
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        this.renderer.paint(g, (int) super.x, (int) super.y, (int) super.getX() + (int) this.getGraph().width(), (int) super.getY() + (int) this.getGraph().height());
    }
    
    public String getName(){
        return this.name;
    }
    
    public void getName(String name){
        this.name = name;
    }
}
