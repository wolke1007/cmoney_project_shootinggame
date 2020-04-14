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
    private float hp;//hp的總量
    private float hpBarWidth;
    private float dividend;
    private GameObject target;
    private Delay targetHp;
    //血量控制end
    private LinkedList<GameObject> allObjects;

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
        setMoveSpeedDetail(moveSpeed);
        this.averageSpeed = new AverageSpeed(this.getCenterX(), this.getCenterY(), this.target.getCenterX(), this.target.getCenterY(), 55, true);
        super.paintPriority = 1;
        this.vectorMove = new VectorCollision(this, 0, 0, null);
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
        this.targetHp = new Delay(60);
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

    public void setAllObject(LinkedList<GameObject> list) {
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
            attackTarget();//待修改 暫時扣不到血
            this.vectorMove.newSet(this.averageSpeed.offsetDX(), this.averageSpeed.offsetDY(), this.allObjects);
//            this.offset(this.averageSpeed.offsetDX(), this.averageSpeed.offsetDY());
        } else {

        }
//        GameObject another;
//        for (int i = 0; i < this.allObjects.size(); i++) {
//            another = this.allObjects.get(i);
//            boolean escape = false;
//            if (another == this) {//跳過自己 不判斷
//                continue;
//            }
//            for (int z = 0; z < Global.EXCLUDE.length; z++) {//排除型別的判斷 //目前 不和"小地圖"判斷 
//                if (another.getType().equals(Global.EXCLUDE[z])) {
//                    escape = true;
//                }
//            }
//            if (escape) {
//                continue;
//            }
//            for (int z = 0; z < Global.INNER.length; z++) {//判斷為在圖形內的 // 目前 Maps 判斷
//                if (another.getType().equals(Global.INNER[z])
//                        && this.getCollider().innerCollisionToCollision(another.getCollider())) {
//                    this.offset(this.getCollider().getDx(), this.getCollider().getDy());
//                    return;
//                }
//            }
//            for (int z = 0; z < Global.INNER.length; z++) {//判斷圖形為各自獨立的個體 // 除了以上的都需要判斷
//                if (!(another.getType().equals(Global.INNER[z]))
//                        && this.getCollider().intersects(another.getCollider())) {
//                    attackTarget();
//                    this.offset(this.getCollider().getDx() * 3, this.getCollider().getDy() * 3);
//                    return;
//                }
//            }
//        }
    }

    public void attackTarget() {
        if (this.targetHp.isTrig()// 每 1 秒觸發一次
                && this.target instanceof Actor
                && this.getCollider().intersects(this.target.getCollider())) {
            Actor tmp = (Actor) this.target;
            tmp.subtractHp();
        }
    }

    @Override
    public void update() {
        if (this.hp >= 1) {
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
        g.fillRect((int) (this.getX() - Global.viewX), (int) (this.getY() - Global.viewY) - 8, (int) this.hpBarWidth, 4);
        g.setColor(Color.BLACK);
    }

}
