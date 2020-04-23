/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textbar;

import controllers.ImagePath;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import renderer.Renderer;
import util.Delay;
import util.Global;

/**
 *
 * @author Cloud-Razer
 */
public class TextBar {
    private ArrayList<String> list;
    private Renderer renderer;
    private int x;
    private int y;
    private int width;
    private int height;
    private String textContent;
    private Delay delay;
    private boolean play;
    private int index;
    
    public TextBar(int x, int y, int width, int height){
        this.list = new ArrayList<String>();
        this.renderer = new Renderer();
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.textContent = "";
        this.delay = new Delay(240);
        this.delay.start();
        this.play = false;
        this.index = 0;
    }
    
    public boolean ready(){
        if(this.list.size() > 0){
            return true;
        }else{
            return false;
        }
    }
    
    public void addScript(String[] script){
        for(int i = 0; i < script.length; i++){
            this.list.add(script[i]);
        }
        Global.log("add script done");
    }
    
    public void play(){
        this.play = this.ready() ? true : false;
    }
    
    public boolean isPlaying(){
        return this.play;
    }
    
    public void paint(Graphics g){
        if(this.play && ready()){
            g.setColor(Color.green);
            g.drawRect(this.x, this.y, this.width, this.height);
            // 如果有稿可以印就會印出來
            g.setColor(Color.white);
            g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));
            Global.log("this.list.get(0):" + this.list.get(0));
            g.drawString(this.list.get(0), this.x, this.y + 30);
            if(this.delay.isTrig()){
                this.list.remove(0);
            }
        }
        g.setColor(Color.black);
    }
    
}
