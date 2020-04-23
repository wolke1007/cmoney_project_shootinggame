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
    
    public Door(float x, float y, int width, int height, String name) {
        super("rect", x, y, width, height);
        this.setType("Door");
        this.name = name;
        this.open = false;
        this.delay = new Delay(5);
        this.delay.start();
        this.closeDoorY = this.y;
        this.renderer = r(ImagePath.DOOR[0]);
    }
    
    public void open(){
        this.open = true;
    }
    
    public void close(){
        this.open = false;
    }
    
    @Override
    public void update(){
        if(this.open && this.delay.isTrig() && this.closeDoorY - this.height() < this.y){
            this.offset(0, -2);
            Global.log("y " + y);
        }
        if(!this.open && this.delay.isTrig() && this.closeDoorY > this.y){
            this.offset(0, 2);
            Global.log("y " + y);
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
