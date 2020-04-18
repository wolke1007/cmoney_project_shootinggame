/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameobj.enemy;

import gameobj.GameObject;
import java.awt.Graphics;
import java.util.ArrayList;
import util.Angle;
import util.Angle;
import util.Delay;
import util.Delay;

/**
 *
 * @author F-NB
 */
public abstract class MoveMode {

    private GameObject self;
    private GameObject target;

    //角度計算
    private Angle angle;
    //角度計算

    //自己對目標的移動控制
    private Delay moveDelay;
    private float moveSpeed;
    private float actMoveSpeed;
    //自己對目標的移動控制end

    public MoveMode(GameObject self, GameObject target, int moveSpeed) {
        setSelf(self);
        setTarget(target);
        setAngle();
        setMoveSpeedDetail(moveSpeed);
    }

    public void setSelf(GameObject self) {
        this.self = self;
    }

    public GameObject getSelf() {
        return this.self;
    }

    public void setTarget(GameObject target) {
        this.target = target;
    }

    public GameObject getTarget() {
        return this.target;
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
            this.angle = new Angle(this.self.getCenterX(), this.self.getCenterY(), getTarget().getCenterX(), getTarget().getCenterY());
            return;
        }
        this.angle.setCenterX(this.self.getCenterX());
        this.angle.setCenterY(this.self.getCenterY());
        this.angle.setGoalCenterX(getTarget().getCenterX());
        this.angle.setGoalCenterY(getTarget().getCenterY());
    }

    public double getAngle() {
        return this.angle.getAngle();
    }

    //角度計算end

    public abstract void setAllObject(ArrayList<GameObject> list);

    public abstract void update();

    public abstract void paintComponent(Graphics g);
}
