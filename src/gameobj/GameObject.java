/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameobj;

import graph.Rect;
import java.awt.Graphics;
import util.Global;
import java.awt.Color;

/**
 *
 * @author Cloud-Razer
 */
public abstract class GameObject {
    private Rect collider;
    protected Rect rect;
    private int x;
    private int y;
    
    public GameObject(int x, int y, int width, int height, int colliderWidth, int colliderHeight){
        this.rect = Rect.genWithCenter(x, y, width, height);
        this.collider = Rect.genWithCenter(x, y, colliderWidth, colliderHeight);
        this.x = x;
        this.y = y;
    }
    
    public GameObject(int x, int y, int width, int height, boolean isBindCollider){
        this.rect = Rect.genWithCenter(x, y, width, height);
        this.x = x;
        this.y = y;
        if(isBindCollider){
            this.collider = this.rect;
        }else{
            this.collider = new Rect(x, y, width, height);
        }
    }
    public int getX(){
        return this.x;
    }
    public int getY(){
        return this.y;
    }
    public int getCenterX(){
        return this.rect.centerX();
    }
    public int getCenterY(){
        return this.rect.centerY();
    }
    public int width(){
        return this.rect.width();
    }
    public int height(){
        return this.rect.height();
    }
    public void setX(int x){
        this.x = x;
    }
    public void setY(int y){
        this.y = y;
    }
    
    public void offset(int dx, int dy){
        this.rect.offset(dx, dy);
        this.collider.offset(dx, dy);
    }
    
    public void offsetX(int x){
        this.rect.offset(x - this.rect.centerX(), 0);
        this.collider.offset(x - this.collider.centerX(), 0);
    public Rect getRect(){
        return this.rect;
    }
    public void setX(int x){
        this.x = x;
    }
    public void offsetY(int y){
        this.rect.offset(0, y - this.rect.centerY());
        this.collider.offset(0, y - this.collider.centerY());
    }
    
    public boolean isCollision(GameObject obj){
        if(this.collider == null || obj.collider == null){
            return false;
        }
        return Rect.intersects(this.collider, obj.collider);
    }
    
    public void paint(Graphics g){
        paintComponent(g);
        if(Global.IS_DEBUG){
            g.setColor(Color.RED);
            g.drawRect(this.rect.left(), this.rect.top(), this.rect.width(), this.rect.height());
            g.setColor(Color.BLUE);
            g.drawRect(this.collider.left(), this.collider.top(), this.collider.width(), this.collider.height());
            g.setColor(Color.BLACK);
        }
    }
    
    public abstract void update();
    public abstract void paintComponent(Graphics g);
}
