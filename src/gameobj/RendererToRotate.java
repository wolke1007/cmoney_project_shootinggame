/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameobj;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import util.Angle;
import util.Delay;
import util.Global;

/**
 *
 * @author Cloud-Razer
 */
public class RendererToRotate {

    private ArrayList<BufferedImage> img;
    private int state;//目前狀態

    private float imgX;// OK
    private float imgY;// OK
    private float goalCenterX;// OK
    private float goalCenterY;// OK
    private Angle angle;

    public RendererToRotate(String[] path, float imgX, float imgY, float goalCenterX, float goalCenterY) {
        imgX = imgX - Global.viewX + 100;
        imgY = imgY - Global.viewY + 100;
        goalCenterX = goalCenterX - Global.viewX;
        goalCenterY = goalCenterY - Global.viewY;
        this.img = new ArrayList<>();
        try {
            for (int i = 0; i < path.length; i++) {
                this.img.add(ImageIO.read(getClass().getResource(path[i])));
            }
        } catch (IOException ex) {
        }
        setX(imgX);
        setY(imgY);
        setGoalCenterX(goalCenterX);
        setGoalCenterY(goalCenterY);
        setState(0);
        setAngle(imgX + this.img.get(this.state).getWidth() / 2f, imgY + this.img.get(this.state).getHeight() / 2f, goalCenterX, goalCenterY);
    }

    //圖片資訊
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
    public void offset(float dx, float dy) {
        setX(getImgX() + dx);
        setY(getImgY() + dy);
        setGoalCenterX(getGoalCenterX() + dx);
        setGoalCenterY(getGoalCenterY() + dy);
    }
    public void setState(int state) {
        this.state = state;
//        this.rotate.setImg(this.img.get(state)); //不需要再藉由ratate畫圖，rederer本身自己畫
    }
    public float getImgX() {
        return this.imgX;
    }
    public float getImgY() {
        return this.imgY;
    }
    public float getImgCenterX() {
        return getImgX() + this.img.get(getState()).getWidth() / 2f;
    }
    public float getImgCenterY() {
        return getImgY() + this.img.get(getState()).getHeight() / 2f;
    }
    public float getGoalCenterX() {
        return this.goalCenterX;
    }
    public float getGoalCenterY() {
        return this.goalCenterY;
    }
    public int getState() {
        return this.state;
    }
    //圖片資訊end

    private void setAngle(float imgCenterX, float imgCenterY, float goalCenterX, float goalCenterY) {
        this.angle = new Angle(imgCenterX, imgCenterY, goalCenterX, goalCenterY);
    }//初始化用

    private void setAngle() {
        this.angle.setCenterX(getImgCenterX());
        this.angle.setCenterY(getImgCenterY());
        this.angle.setGoalCenterX(getGoalCenterX());
        this.angle.setGoalCenterY(getGoalCenterY());
    }//放入update()裡，要不斷更新目前角度的資訊

    public void update() {
        setAngle();
    }

    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        AffineTransform oldXForm = g2d.getTransform();
        g2d.rotate(Math.toRadians(this.angle.getAngle()), getImgCenterX(), getImgCenterY());
//        g2d.drawImage(this.img.get(getState()), (int) getImgX(), (int) getImgY(), this.img.get(getState()).getWidth(), this.img.get(getState()).getHeight(), null);
        g2d.drawImage(this.img.get(getState()), (int) getImgX(), (int) getImgY(), this.img.get(getState()).getWidth(), this.img.get(getState()).getHeight(), null);
        g2d.setTransform(oldXForm);
    }

}
