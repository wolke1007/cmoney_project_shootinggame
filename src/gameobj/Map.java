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
public class Map extends GameObject {

    private Renderer renderer;
    private ArrayList<Barrier> barriers;
    private ArrayList<Building> buildings;

    private float width;
    private float height;

    public Map(String src, float x, float y, int width, int height) {
        super("rect", x, y, width - 2, height - 2, width - 2, height - 2);
        this.width = width;
        this.height = height;
        this.renderer = r(src);
        this.barriers = new ArrayList<Barrier>();
        this.buildings = new ArrayList<Building>();
    }

    public static Renderer r(String src) {
        return new Renderer(new int[]{0}, 0, src);
    }

    public ArrayList<Barrier> getBarriers() {
        return this.barriers;
    }
    
    public ArrayList<Building> getBuildings() {
        return this.buildings;
    }

    public void updateAllBarriersBuildingsXY() {
        for (int i = 0; i < this.barriers.size(); i++) {
            Barrier b = this.barriers.get(i);
            b.setX(super.x + b.x);
            b.setY(super.y + b.y);
            Global.log("super.x : " + super.x);
            Global.log("b.x: " + b.x);
        }
        for (int i = 0; i < this.buildings.size(); i++) {
            Building b = this.buildings.get(i);
            b.setX(super.x + b.x);
            b.setY(super.y + b.y);
            Global.log("super.x : " + super.x);
            Global.log("b.x: " + b.x);
        }
    }

    @Override
    public void setDir(int dir) {
    }

    @Override
    public void update() {
    }

    @Override
    public void paintComponent(Graphics g) {
        this.renderer.paint(g, (int) super.getX(), (int) super.getY(), (int) super.getX() + (int) this.width, (int) super.getY() + (int) this.height, 0, 0, 1280, 720);
    }

}
