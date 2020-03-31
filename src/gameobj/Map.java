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

/**
 *
 * @author Cloud-Razer
 */
public class Map extends GameObject {

    private int dir;
    private Renderer renderer;
    private boolean isStand;

    private Delay moveDelay;
    private int moveSpeed = 59; // per frame

    private int width;
    private int height;
    
    private Map upMap;
    private Map downMap;
    private Map leftMap;
    private Map rightMap;
    
    private Move movement;

    public Map(String src, int x, int y, int width, int height) {
        super("rect", x, y, width - 2, height - 2, width - 2, height - 2);
        this.width = width;
        this.height = height;
        this.renderer = r(src);
        this.isStand = true;
        movement = new Move(this);
        this.moveDelay = new Delay(60 - this.moveSpeed);
        this.moveDelay.start();
    }

    public static Renderer r(String src) {
        return new Renderer(new int[]{0}, 0, src);
    }

    public void setStand(boolean isStand) {
        this.isStand = isStand;
        if (this.isStand) {
            this.moveDelay.stop();
        } else {
            this.moveDelay.start();
        }
    }

    public void setDir(int dir) {
        this.dir = dir;
        this.renderer.setDir(dir);
    }

    public void setUpMap(Map map){
        this.upMap = map;
    }

    public void setDownMap(Map map){
        this.downMap = map;
    }

    public void setLeftMap(Map map){
        this.leftMap = map;
    }

    public void setRightMap(Map map){
        this.rightMap = map;
    }
    
    public Map getUpMap(){
        return this.upMap;
    }
    
    public Map getDownMap(){
        return this.downMap;
    }
    
    public Map getLeftMap(){
        return this.leftMap;
    }
    
    public Map getRightMap(){
        return this.rightMap;
    }

    @Override
    public void update() {
        if (!this.isStand & this.moveDelay.isTrig()) {
            move();
        }
    }

    // TODO 需實作地圖的移動
    private void move() {
        this.movement.movingIgnoreEdge();
    }

    @Override
    public void paintComponent(Graphics g) {
//        setX(this.x + this.width / 2);
//        setY(this.y + this.height / 2);
        this.renderer.paint(g, super.getX(), super.getY(), this.width, this.height);
    }

    @Override
    public void setMovementPressedStatus(int dir, boolean status) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
