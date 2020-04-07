/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameobj;

import java.awt.Graphics;
import java.util.LinkedList;
import util.Angle;
import util.AverageSpeed;
import util.Delay;
import util.Global;

/**
 *
 * @author F-NB
 */
public class Enemy extends GameObject {

    private RendererToRotate renderer;//旋轉圖渲染器
    private float hp;//hp的總量
    private GameObject goal;
    private LinkedList<GameObject> allObjects;

    //敵人對目標的移動控制
    private Delay moveDelay;
    private float moveSpeed;
    private float actMoveSpeed;
    //敵人對目標的移動控制end

    //移動分段
    private Angle angle;
    private AverageSpeed averageSpeed;
    //移動分段end

    public Enemy(String colliderType, float x, float y, float hp, GameObject goal, int moveSpeed, String[] path) {
        super(colliderType, x, y, Global.UNIT_X, Global.UNIT_Y, Global.UNIT_X, Global.UNIT_Y);
        setGoal(goal);
        setHp(hp);
        setAngle();
        this.renderer = new RendererToRotate(path, this, getAngle());
        setMoveSpeedDetail(moveSpeed);
        this.averageSpeed = new AverageSpeed(this.getCenterX(), this.getCenterY(), this.goal.getCenterX(), this.goal.getCenterY(), 50, true);
        super.paintPriority = 1;
    }

    //自己的資料
    private void setHp(float hp) {
        this.hp = hp;
    }

    public void subtractHp(float hurt) {
        this.hp -= hurt;
    }

    public void increaseHp(float blood) {
        this.hp += blood;
    }

    public float getHp() {
        return this.hp;
    }
    //自己的資料end

    //目標資料
    public void setGoal(GameObject goal) {
        this.goal = goal;
    }

    public GameObject getGoal() {
        return this.goal;
    }
    //目標資料end

    //角度計算
    public void setAngle() {
        if (this.angle == null) {
            this.angle = new Angle(this.getCenterX(), this.getCenterY(), getGoal().getCenterX(), getGoal().getCenterY());
            return;
        }
        this.angle.setCenterX(this.getCenterX());
        this.angle.setCenterY(this.getCenterY());
        this.angle.setGoalCenterX(getGoal().getCenterX());
        this.angle.setGoalCenterY(getGoal().getCenterY());
    }

    public double getAngle() {
        return this.angle.getAngle();
    }
    //角度計算end

    //delay控制
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
    //delay控制end

    public void setAllObject(LinkedList<GameObject> list) {
        this.allObjects = list;
    }

    private void setAverageSpeed() {
        this.averageSpeed.setCenterX(this.getCenterX());
        this.averageSpeed.setCenterY(this.getCenterY());
        this.averageSpeed.setGoalCenterX(this.goal.getCenterX());
        this.averageSpeed.setGoalCenterY(this.goal.getCenterY());
    }

    @Override
    public void update() {
        this.setAngle();
        this.renderer.setAngle(this.getAngle());
        setAverageSpeed();
        this.offset(this.averageSpeed.offsetDX(), this.averageSpeed.offsetDY());
    }

    @Override
    public void setDir(int dir) {
    }//方向用不到

    @Override
    public void paintComponent(Graphics g) {
        this.renderer.paint(g);
    }

}
