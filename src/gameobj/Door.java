/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameobj;

import java.awt.Graphics;

/**
 *
 * @author Cloud-Razer
 */
public class Door extends Barrier {

    protected String name;
    
    public Door(float x, float y, int width, int height, String name) {
        super("rect", x, y, width, height);
        this.setType("Door");
        this.name = name;
    }

    @Override
    public void paintComponent(Graphics g) {
    }
    
    public String getName(){
        return this.name;
    }
    
    public void getName(String name){
        this.name = name;
    }
}
