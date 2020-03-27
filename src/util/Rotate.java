/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/**
 *
 * @author F-NB
 */
public class Rotate {

    private BufferedImage img;
    private double angle;
    private int x;
    private int y;

    public Rotate(BufferedImage img, double angle, int x, int y) {
        setImg(img);
        setAngle(angle);
        setX(x);
        setY(y);
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    private void setImg(BufferedImage img) {
        this.img = img;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }
    public void update(){
        
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        AffineTransform oldXForm = g2d.getTransform();
        g2d.rotate(Math.toRadians(this.angle), (this.img.getWidth() / 2) + this.x, (this.img.getHeight() / 2) + this.y);
        g2d.drawImage(this.img, this.x, this.y, this.img.getWidth(), this.img.getHeight(), null);
        g2d.setTransform(oldXForm);
    }
}
