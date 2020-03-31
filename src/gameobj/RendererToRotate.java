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

    private float imgX;
    private float imgY;
    private float goalCenterX;
    private float goalCenterY;
    private Rotate rotate;
    private Angle angle;

    public RendererToRotate(String ptah, float imgX, float imgY, float goalCenterX, float goalCenterY) {
        try {
            img = ImageIO.read(getClass().getResource(ptah));
        } catch (IOException ex) {
        }
        setAngle(imgX + this.img.getWidth() / 2f, imgY + this.img.getHeight() / 2f, goalCenterX, goalCenterY);
        setRotate();
    }

    private void setAngle(float imgCenterX, float imgCenterY, float goalCenterX, float goalCenterY) {
        this.angle = new Angle(imgCenterX, imgCenterY, goalCenterX, goalCenterY);
    }

    private void setRotate() {
        this.rotate = new Rotate(this.img, this.angle.getAngle(), (int)this.imgX, (int)this.imgY);
    }

    public void setCenterX(float imgX) {
        this.imgX = imgX;
    }

    public void setCenterY(float imgY) {
        this.imgY = imgY;
    }

    public void setGoalCenterX(float goalCenterX) {
        this.goalCenterX = goalCenterX;
    }

    public void setGoalCenterY(float goalCenterY) {
        this.goalCenterY = goalCenterY;
    }
    public void update(){
        this.angle.setCenterX(this.imgX + this.img.getWidth() / 2f);
        this.angle.setCenterY(this.imgY + this.img.getHeight() / 2f);
        this.angle.setGoalCenterX(this.goalCenterX);
        this.angle.setGoalCenterY(this.goalCenterY);
        this.rotate.setX((int)this.imgX);
        this.rotate.setY((int)this.imgY);
        this.rotate.setAngle(this.angle.getAngle());
    }
    public void paint(Graphics g) {
        this.rotate.paintComponent(g);
    }

}
