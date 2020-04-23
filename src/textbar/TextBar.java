/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textbar;

import controllers.ImagePath;
import java.awt.Graphics;
import java.util.ArrayList;
import renderer.Renderer;

/**
 *
 * @author Cloud-Razer
 */
public class TextBar {
    private ArrayList<String> list;
    private int index;
    private Renderer renderer;
    private int x;
    private int y;
    private int width;
    private int height;
    
    public TextBar(int x, int y, int width, int height){
        this.list = new ArrayList<String>();
        this.index = 0;
        this.renderer = new Renderer();
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    
    public boolean ready(){
        if(this.index < this.list.size()){
            return true;
        }else{
            return false;
        }
    }
    
    public int getIndex(){
        return this.index;
    }
    
    public void reset(){
        this.index = 0;
    }
    
    public void addScript(String[] script){
        for(int i = 0; i < script.length; i++){
            this.list.add(script[i]);
        }
    }
    
    public String play(){
        return this.list.get(this.index++);
    }
    
    public void paint(Graphics g){
        g.drawRect(this.x, this.y, (this.x + this.width), (this.y + this.height));
    }
    
}
