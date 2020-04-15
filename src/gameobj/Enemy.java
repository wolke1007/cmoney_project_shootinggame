/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameobj;

import renderer.RendererToRotate;
import graph.Graph;
import graph.Rect;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.LinkedList;
import util.Angle;
import util.AverageSpeed;
import util.Delay;
import util.Global;
import util.VectorCollision;

/**
 *
 * @author F-NB
 */
public class Enemy extends GameObject {

    private RendererToRotate renderer;//旋轉圖渲染器
    //血量控制
    private GameObject target;
    private Delay targetHp;
    //血量控制end
    private ArrayList<GameObject> allObjects;

    //敵人對目標的移動控制
    private Delay moveDelay;
    private float moveSpeed;
    private float actMoveSpeed;
    //敵人對目標的移動控制end

    //移動分段
    private Angle angle;
    private AverageSpeed averageSpeed;
    private VectorCollision vectorMove;
    //移動分段end

    public Enemy(String colliderType, float x, float y, float hp, GameObject target, int moveSpeed, String[] path) {
        super(colliderType, x, y, Global.UNIT_X, Global.UNIT_Y, Global.UNIT_X, Global.UNIT_Y);
        setTarget(target);
        setHpPoint(hp);
        setAngle();
        this.renderer = new RendererToRotate(path, this, getAngle());
        setMoveSpeedDetail(moveSpeed);//裡面有 扣血 & 移動 的時間設定
        this.averageSpeed = new AverageSpeed(this.getCenterX(), this.getCenterY(), this.target.getCenterX(), this.target.getCenterY(), 55, true);
        super.paintPriority = 1;
        this.vectorMove = new VectorCollision(this, 0, 0, null);
        this.vectorMove.setMultiple(4f);
        this.vectorMove.setDivisor(5);
        this.setType("Enemy");
    }

    //目標資料
    public void setTarget(GameObject target) {
        this.target = target;
    }

    public GameObject getTarget() {
        return this.target;
    }
    //目標資料end

    //角度計算
    public void setAngle() {
        if (this.angle == null) {
            this.angle = new Angle(this.getCenterX(), this.getCenterY(), getTarget().getCenterX(), getTarget().getCenterY());
            return;
        }
        this.angle.setCenterX(this.getCenterX());
        this.angle.setCenterY(this.getCenterY());
        this.angle.setGoalCenterX(getTarget().getCenterX());
        this.angle.setGoalCenterY(getTarget().getCenterY());
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
        this.targetHp = new Delay(10);
        this.targetHp.start();
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

    public void setAllObject(ArrayList<GameObject> list) {
        this.allObjects = list;
        this.vectorMove.setAllObjects(this.allObjects);
    }

    private void setAverageSpeed() {
        this.averageSpeed.setCenterX(this.getCenterX());
        this.averageSpeed.setCenterY(this.getCenterY());
        this.averageSpeed.setGoalCenterX(this.target.getCenterX());
        this.averageSpeed.setGoalCenterY(this.target.getCenterY());
    }

    public void move() {
        if (!this.getCollider().intersects(this.target.getCollider())) {
            this.setAngle();
            this.renderer.setAngle(this.getAngle());
            setAverageSpeed();
            if (this.targetHp.isTrig()) {
                this.vectorMove.setIsHurt(true);
            }//移動前 扣血 啟動
            this.vectorMove.newOffset(this.averageSpeed.offsetDX(), this.averageSpeed.offsetDY());
            this.vectorMove.setIsHurt(false);
            
        }
    }

    @Override
    public void update() {
        if (this.getHp() >= 1) {
            if (this.moveDelay.isTrig()) {
                move();
            }
        } else {
            this.setXY(-50, -50);
        }       
    }

    @Override
    public void setDir(int dir) {
    }//方向用不到

    @Override
    public void paintComponent(Graphics g) {
        this.renderer.paint(g);
        g.fillRect((int) (this.getX() - Global.viewX), (int) (this.getY() - Global.viewY) - 8, (int) this.width(), 4);
        g.setColor(Color.RED);
        g.fillRect((int) (this.getX() - Global.viewX), (int) (this.getY() - Global.viewY) - 8, (int) this.getHpBarWidth(), 4);
        g.setColor(Color.BLACK);
    }

}
