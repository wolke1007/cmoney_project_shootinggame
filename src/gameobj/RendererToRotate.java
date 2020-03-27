/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameobj;

import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import util.Angle;
import util.Delay;
import util.Global;
import util.Rotate;

/**
 *
 * @author Cloud-Razer
 */
public class RendererToRotate {

    private BufferedImage img;//要被旋轉的圖

    private int imgX;
    private int imgY;
    private int goalCenterX;
    private int goalCenterY;
    private Rotate rotate;
    private Angle angle;

    public RendererToRotate(String ptah, int imgX, int imgY, int goalCenterX, int goalCenterY) {
        try {
            img = ImageIO.read(getClass().getResource(ptah));
        } catch (IOException ex) {
        }
        setAngle(imgX + this.img.getWidth() / 2, imgY + this.img.getHeight() / 2, goalCenterX, goalCenterY);
        setRotate();
    }

    private void setAngle(int imgCenterX, int imgCenterY, int goalCenterX, int goalCenterY) {
        this.angle = new Angle(imgCenterX, imgCenterY, goalCenterX, goalCenterY);
    }

    private void setRotate() {
        this.rotate = new Rotate(this.img, this.angle.getAngle(), this.imgX, this.imgY);
    }

    public void setCenterX(int imgX) {
        this.imgX = imgX;
    }

    public void setCenterY(int imgY) {
        this.imgY = imgY;
    }

    public void setGoalCenterX(int goalCenterX) {
        this.goalCenterX = goalCenterX;
    }

    public void setGoalCenterY(int goalCenterY) {
        this.goalCenterY = goalCenterY;
    }
    public void update(){
        this.angle.setCenterX(this.imgX + this.img.getWidth() / 2);
        this.angle.setCenterY(this.imgY + this.img.getHeight() / 2);
        this.angle.setGoalCenterX(this.goalCenterX);
        this.angle.setGoalCenterY(this.goalCenterY);
        this.rotate.setX(this.imgX);
        this.rotate.setY(this.imgY);
        this.rotate.setAngle(this.angle.getAngle());
    }
    public void paint(Graphics g, int x, int y, int w, int h) {
//        this.rotate = new Rotate(this.img, this.angle.getAngle(), this.imgX, this.imgY);
        this.rotate.paintComponent(g);
    }

}
