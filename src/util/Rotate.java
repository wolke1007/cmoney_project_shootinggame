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
    public BufferedImage rotateImg;

    public Rotate(BufferedImage img, double angle) {
        setImg(img);
        setAngle(angle);
        setRotateImg();
    }
    private void setRotateImg(){
        this.rotateImg = getRotateImage(this.img,this.angle);
    }
    private void setImg(BufferedImage img){
        this.img = img;
    }
    private void setAngle(double angle){
        this.angle = angle;
    }
    

    private BufferedImage getRotateImage(BufferedImage img, double angle) {
        double rads = Math.toRadians(angle);
        double sin = Math.abs(Math.sin(rads));
        double cos = Math.abs(Math.cos(rads));
        int w = img.getWidth();
        int h = img.getHeight();
        int newWidth = (int) (Math.floor(w * cos + h * sin));
        int newHeight = (int) (Math.floor(h * cos + w * sin));
        
        BufferedImage rotated = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = rotated.createGraphics();//
        AffineTransform at = new AffineTransform();
        at.translate((newWidth - w) / 2, (newHeight - h) / 2);

        int x = w / 2;
        int y = h / 2;

        at.rotate(rads, x, y);
        g2d.setTransform(at);
        g2d.drawImage(img, null, 0, 0);
        
        g2d.dispose();
        return rotated;
    }
    public void paintComponent(Graphics g){
        Graphics2D g2d = (Graphics2D) g.create();
        int x = (this.img.getWidth() - rotateImg.getWidth()) / 2; 
        int y = (this.img.getHeight() - rotateImg.getHeight()) / 2; 
        g2d.drawImage(this.rotateImg, null, x, y);
    }
}
