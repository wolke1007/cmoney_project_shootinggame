/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameobj;

import java.awt.Graphics;
import java.util.ArrayList;
import util.Global;

/**
 *
 * @author Cloud-Razer
 */
public class Maps extends GameObject {
    private ArrayList<Map> maps;
    
    public Maps(int x, int y, int width, int height, int colliderWidth, int colliderHeight){
        super("rect", x, y, width, height, colliderWidth, colliderHeight);
        this.maps = new ArrayList<Map>();
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
    }
    
    public void paint(Graphics g){
        for(int i = 0; i < this.maps.size(); i++){
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
