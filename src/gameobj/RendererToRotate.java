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
import java.util.ArrayList;
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

//    private BufferedImage img;//要被旋轉的圖
    private ArrayList<BufferedImage> img;
//    private BufferedImage img2;//要被旋轉的圖
    private int state;//目前狀態

    private float imgX;
    private float imgY;
    private float goalCenterX;
    private float goalCenterY;
    private Rotate rotate;
    private Angle angle;

    public RendererToRotate(String[] path, float imgX, float imgY, float goalCenterX, float goalCenterY) {
        this.img = new ArrayList<>();
        try {
//            this.img = ImageIO.read(getClass().getResource(ptah));
            for (int i = 0; i < path.length; i++) {
                this.img.add(ImageIO.read(getClass().getResource(path[i])));
            }
        } catch (IOException ex) {
        }
        this.state = 0;
        setAngle(imgX + this.img.get(this.state).getWidth() / 2f, imgY + this.img.get(this.state).getHeight() / 2f, goalCenterX, goalCenterY);
        setRotate(this.state);
    }

    public void setState(int state) {
        this.state = state;
        this.rotate.setImg(this.img.get(state));
    }

    public int getState() {
        return this.state;
    }

    private void setAngle(float imgCenterX, float imgCenterY, float goalCenterX, float goalCenterY) {
        this.angle = new Angle(imgCenterX, imgCenterY, goalCenterX, goalCenterY);
    }

    private void setRotate(int state) {
        this.rotate = new Rotate(this.img.get(state), this.angle.getAngle(), (int) this.imgX, (int) this.imgY);
    }//初始化用

    public void setX(float imgX) {
        this.imgX = imgX;
    }

    public void setY(float imgY) {
        this.imgY = imgY;
    }

    public void setGoalCenterX(float goalCenterX) {
        this.goalCenterX = goalCenterX;
    }

    public void setGoalCenterY(float goalCenterY) {
        this.goalCenterY = goalCenterY;
    }

    public void update() {
        this.angle.setCenterX(this.imgX + this.img.get(this.state).getWidth() / 2f);
        this.angle.setCenterY(this.imgY + this.img.get(this.state).getHeight() / 2f);
        this.angle.setGoalCenterX(this.goalCenterX);
        this.angle.setGoalCenterY(this.goalCenterY);
        this.rotate.setX((int) this.imgX);
        this.rotate.setY((int) this.imgY);
        this.rotate.setAngle(this.angle.getAngle());
    }

    public void paint(Graphics g) {
        this.rotate.paintComponent(g);
    }

}
