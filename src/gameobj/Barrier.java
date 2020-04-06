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
public class Barrier extends GameObject {

    private Renderer renderer;
    private float inMapX;
    private float inMapY;

    public Barrier(String colliderType, float x, float y, int width, int height, String[] path) {//src => Global.Barrier
        super(colliderType, x, y, width, height, width, height);
        this.renderer = new Renderer(new int[0], 0, path[0]);
        super.paintPriority = 1;
    }

    @Override
    public void setX(float x) {
        super.setX(x);
    }

    @Override
    public void setY(float y) {
        super.setY(y);
    }

    //位置資訊start
    public float centerX() {
        return super.getCenterX();
    }

    public float centerY() {
        return super.getCenterY();
    }
    //位置資訊end
    
    @Override
    public void setDir(int dir) {
    }
    
    @Override
    public void update() {
    }

    @Override
    public void paintComponent(Graphics g) {
        this.renderer.paint(g, (int)super.x, (int)super.y, (int)super.width(), (int)super.height());
    }

}

