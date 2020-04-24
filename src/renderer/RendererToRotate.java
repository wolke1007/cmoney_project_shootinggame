/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package renderer;

import controllers.ImageResourceController;
import gameobj.GameObject;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import util.Global;

/**
 *
 * @author Cloud-Razer
 */
public class RendererToRotate {
    
    private ArrayList<BufferedImage> img;
    private int state;//目前狀態

    private GameObject obj;
    private double angle;
    private ImageResourceController irc;
    
    public RendererToRotate(String[] path, GameObject obj, double angle) {
        this.img = new ArrayList<>();
        this.irc = ImageResourceController.getInstance();
        for (int i = 0; i < path.length; i++) {
            this.img.add(this.irc.tryGetImage(path[i]));
        }
        this.obj = obj;
        setAngle(angle);
        setState(0);
    }

    public void setImage(String[] path) {
        for (int i = 0; i < path.length; i++) {
            this.img.set(i, this.irc.tryGetImage(path[i]));
        }
    }

    //圖片資訊
    public void setAngle(double angle) {
        this.angle = angle;
    }
    
    public void setState(int state) {
        this.state = state;
    }
    
    private float getImgX() {
        return this.obj.getX() - Global.viewX;
    }
    
    private float getImgY() {
        return this.obj.getY() - Global.viewY;
    }
    
    private double getImgCenterX() {
        return getImgX() + this.img.get(getState()).getWidth() / 2f;
    }
    
    private double getImgCenterY() {
        return getImgY() + this.img.get(getState()).getHeight() / 2f;
    }
    
    public double getAngle() {
        return this.angle;
    }
    
    public int getState() {
        return this.state;
    }

    //圖片資訊end
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        AffineTransform oldXForm = g2d.getTransform();
        g2d.rotate(Math.toRadians(getAngle()), getImgCenterX(), getImgCenterY());
        g2d.drawImage(this.img.get(getState()), (int) getImgX(), (int) getImgY(), this.img.get(getState()).getWidth(), this.img.get(getState()).getHeight(), null);
        g2d.setTransform(oldXForm);
    }
}
