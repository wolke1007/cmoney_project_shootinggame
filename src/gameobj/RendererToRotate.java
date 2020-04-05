/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameobj;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import util.Global;

/**
 *
 * @author Cloud-Razer
 */
public class RendererToRotate {

    private ArrayList<BufferedImage> img;
    private int state;//目前狀態

    private float imgX;
    private float imgY;
    private double angle;

    public RendererToRotate(String[] path, float imgX, float imgY, double angle) {
        this.img = new ArrayList<>();
        try {
            for (int i = 0; i < path.length; i++) {
                this.img.add(ImageIO.read(getClass().getResource(path[i])));
            }
        } catch (IOException ex) {
        }
        setX(imgX);
        setY(imgY);
        setAngle(angle);
        setState(0);
    }

    //圖片資訊
    public void setX(float imgX) {
        this.imgX = imgX;
    }
    public void setY(float imgY) {
        this.imgY = imgY;
    }
    public void setAngle(double angle) {
        this.angle = angle;
    }
    public void offset(float dx, float dy) {
        setX(this.imgX + dx);
        setY(this.imgY + dy);
    }
    public void setState(int state) {
        this.state = state;
    }
    private float getImgX() {
        return this.imgX - Global.viewX;
    }
    private float getImgY() {
        return this.imgY - Global.viewY;
    }
    private float getImgCenterX() {
        return getImgX() + this.img.get(getState()).getWidth() / 2f;
    }
    private float getImgCenterY() {
        return getImgY() + this.img.get(getState()).getHeight() / 2f;
    }
    private double getAngle() {
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
