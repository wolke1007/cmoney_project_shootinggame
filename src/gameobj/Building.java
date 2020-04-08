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
public class Building extends GameObject {

    private Renderer renderer;
    private float inMapX;
    private float inMapY;

    public Building(String colliderType, float x, float y, int width, int height, String[] path, int imgIndex) {//src => Global.Barrier
        super(colliderType, x, y, width, height, width, height);
        this.renderer = new Renderer(new int[0], 0, path[imgIndex]);
        super.paintPriority =2;
    }

    @Override
    public void setDir(int dir) {
    }

    @Override
    public void update() {
    }

    @Override
    public void paintComponent(Graphics g) {
        this.renderer.paint(g, (int)super.x, (int)super.y, (int) super.getX() + (int) super.width(), (int) super.getY() + (int) super.height(), 0, 0 , 353, 239);
    }

}
