/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameobj;

import java.awt.Graphics;
import util.Delay;
import util.Global;
import util.Move;
import util.Point;

/**
 *
 * @author Cloud-Razer
 */
public class Map extends GameObject {

    private Renderer renderer;

    private float width;
    private float height;
    
    public Map(String src, float x, float y, int width, int height) {
        super("rect", x, y, width - 2, height - 2, width - 2, height - 2);
        this.width = width;
        this.height = height;
        this.renderer = r(src);
    }

    public static Renderer r(String src) {
        return new Renderer(new int[]{0}, 0, src);
    }

    @Override
    public void setDir(int dir) {
//        this.renderer.setDir(dir);
    }

    @Override
    public void update() {
    }

    @Override
    public void paintComponent(Graphics g) {
        this.renderer.paint(g, (int)super.getX(), (int)super.getY(), (int)this.width, (int)this.height);
    }

}
