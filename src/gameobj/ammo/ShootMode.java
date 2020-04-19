/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameobj.ammo;

import gameobj.GameObject;
import java.awt.Graphics;
import java.util.ArrayList;
import util.Angle;
import util.Delay;
import util.Global;

/**
 *
 * @author F-NB
 */
public abstract class ShootMode {

    private GameObject start;

    //角度計算
    private Angle angle;
    //角度計算end

    //自己對目標的移動控制
    private Delay moveDelay;
    private float moveSpeed;
    private float actMoveSpeed;
    //自己對目標的移動控制end

    private String type;

    public ShootMode(GameObject start, float moveSpeed) {
        setStart(start);
        setAngle();
        setMoveSpeedDetail(moveSpeed);
        setType("");
    }

    public void setStart(GameObject start) {
        this.start = start;
    }

    public GameObject getStart() {
        return this.start;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }

    //移動delay控制
    private void setMoveSpeedDetail(float moveSpeed) {
        this.moveSpeed = limitRange(moveSpeed);
        this.actMoveSpeed = 60 - this.moveSpeed;
        this.moveDelay = new Delay(this.actMoveSpeed);
        this.moveDelay.start();
    }

    private float limitRange(float range) {
        if (range < 0) {
            return 0;
        } else if (range > 60) {
            return 60;
        }
        return range;
    }

    public void setMoveSpeed(float moveSpeed) {
        this.moveSpeed = limitRange(moveSpeed);
        this.actMoveSpeed = this.moveSpeed;
        this.moveDelay.setDelayFrame(this.actMoveSpeed);
    }

    public float getMoveSpeed() {
        return this.moveSpeed;
    }

    public Delay getMoveDelay() {
        return this.moveDelay;
    }
    //移動delay控制end

    //角度計算
    public void setAngle() {
        if (this.angle == null) {
            this.angle = new Angle(this.start.getCenterX(), this.start.getCenterY(), Global.mapMouseX, Global.mapMouseY);
            return;
        }
        this.angle.setCenterX(this.start.getCenterX());
        this.angle.setCenterY(this.start.getCenterY());
        this.angle.setGoalCenterX(Global.mapMouseX);
        this.angle.setGoalCenterY(Global.mapMouseY);
    }

    public double getAngle() {
        return this.angle.getAngle();
    }

    //角度計算end

    public abstract void setNewStart();

    public abstract void setAllObject(ArrayList<GameObject> list);

    public abstract boolean update();

    public abstract void paintComponent(Graphics g);

}
