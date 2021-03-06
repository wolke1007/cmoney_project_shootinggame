/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameobj;

import java.awt.Graphics;
import renderer.Renderer;

/**
 *
 * @author Cloud-Razer
 */
public class Barrier extends GameObject {

    private Renderer renderer;
    private float inMapX;
    private float inMapY;

    public Barrier(String colliderType, float x, float y, int width, int height, String[] path, int imgIndex) {//src => Global.Barrier
        super(colliderType, x, y, width, height, width, height);
        this.renderer = new Renderer(new int[0], 0, path[imgIndex]);
        super.paintPriority = 2;
        setType("Barrier");
    }

    public Barrier(String colliderType, float x, float y, int width, int height) { // 給 Wall 用的建構子，不需要圖片資訊
        super(colliderType, x, y, width, height, width, height);
        super.paintPriority = 2;
        setType("Barrier");
    }

    @Override
    public void setDir(int dir) {
    }

    @Override
    public void update() {
    }

    @Override
    public void paintComponent(Graphics g) {
        if (this.renderer != null) {
            this.renderer.paint(g, (int) super.x, (int) super.y, (int) super.getX() + (int) this.getGraph().width(), (int) super.getY() + (int) this.getGraph().height());
        }
    }

}
