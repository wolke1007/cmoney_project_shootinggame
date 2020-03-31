/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameobj;

import java.awt.Graphics;
import java.util.ArrayList;
import util.Delay;
import util.Global;
import util.Move;

/**
 *
 * @author Cloud-Razer
 */
public class Maps extends GameObject {
    private ArrayList<Map> maps;
    private Move movement;
//    private Delay moveDelay;
    private boolean isStand;
//    private int moveSpeed = 60; // per frame
    
    public Maps(int x, int y, int width, int height, int colliderWidth, int colliderHeight){
        super("rect", x, y, width, height, colliderWidth, colliderHeight);
        this.maps = new ArrayList<Map>();
        this.isStand = true;
        movement = new Move(this);
//        this.moveDelay = new Delay(60 - this.moveSpeed);
//        this.moveDelay.start();
    }
    
    public void add(Map map){
        this.maps.add(map);
    }
    
    public Map get(int index){
        try{
            return this.maps.get(index);
        }catch(IndexOutOfBoundsException exception){
            return null;
        }
    }
    
    public ArrayList getMaps(){
        return this.maps;
    }
    
    public void update(){
        for(int i = 0; i < this.maps.size(); i++){
            this.maps.get(i).update();
        }
        if(this.maps.get(0).getX() - this.maps.get(1).getX() != -Global.MAP_WIDTH){
                Global.log("===========1===========BUGGGGGGGGGG" + (this.maps.get(0).getX() - this.maps.get(1).getX()));
        }
        if(this.maps.get(1).getX() - this.maps.get(2).getX() != -Global.MAP_WIDTH){
                Global.log("===========2===========BUGGGGGGGGGG: " + (this.maps.get(1).getX() - this.maps.get(2).getX()));
        }
    }
    
    public void paint(Graphics g){
        for(int i = 0; i < this.maps.size(); i++){
            if(this.maps.get(0).getX() - this.maps.get(1).getX() != -Global.MAP_WIDTH){
                Global.log("============3==========BUGGGGGGGGGG" + (this.maps.get(0).getX() - this.maps.get(1).getX()));
            }
            if(this.maps.get(1).getX() - this.maps.get(2).getX() != -Global.MAP_WIDTH){
                Global.log("=============4=========BUGGGGGGGGGG" + (this.maps.get(1).getX() - this.maps.get(2).getX()));
            }
            this.maps.get(i).paint(g);
        }
    }
    
    public void setStand(boolean status){
        for(int i = 0; i < this.maps.size(); i++){
            this.maps.get(i).setStand(status);
        }
    }
    
    public void setDir(int dir){
        for(int i = 0; i < this.maps.size(); i++){
            this.maps.get(i).setDir(dir);
        }
    }
    
    public void setMovementPressedStatus(int dir, boolean status){
        for(int i = 0; i < this.maps.size(); i++){
            this.maps.get(i).setMovementPressedStatus(dir, status);
        }
    }
    
    public void move(){
        for(int i = 0; i < this.maps.size(); i++){
            this.maps.get(i).move();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        paint(g);
    }
    
}