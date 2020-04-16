/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameobj;

import graph.Graph;
import java.awt.Graphics;
import java.util.ArrayList;
import renderer.RendererToRotate;
import util.Angle;
import util.AverageSpeed;
import util.Delay;
import util.Global;

/**
 *
 * @author F-NB
 */
public class Ammo extends GameObject {

    private RendererToRotate renderer;//旋轉圖渲染器
    private boolean isShootOut;//是否射擊的狀態 Origine true 創建時就是要射出
    private ArrayList<GameObject> allObjects;
    private GameObject start;

    //子彈移動控制
    private Delay moveDelay;
    private float moveSpeed;
    private float actMoveSpeed;
    //子彈移動控制end

    //移動分段
    private Angle angle;
    private AverageSpeed averageSpeed;
    private int count = 0;//初始位置的狀態設定
    //移動分段end

    public Ammo(String colliderType, float x, float y, GameObject start, float moveSpeed, String[] path) {
        super(colliderType, x, y, Global.UNIT_MIN * 2, Global.UNIT_MIN * 2, Global.UNIT_MIN * 2, Global.UNIT_MIN * 2);
        setStart(start);
        setAngle();
        this.renderer = new RendererToRotate(path, this, getAngle());
        setMoveSpeedDetail(moveSpeed);//初始化移動應為最大值，暫時不該限制delay
        this.averageSpeed = new AverageSpeed(this.getCenterX(), this.getCenterY(), Global.mapMouseX, Global.mapMouseY, 95, true);//30為子彈的移動距離值
        setIsShootOut(true);
        super.paintPriority = 1; // 畫圖順序僅次於主角，此順序可討論
        setType("Ammo");
    }

    public void setStart(GameObject start) {
        this.start = start;
    }

    public void setAllObjects(ArrayList<GameObject> list) {
        this.allObjects = list;
    }

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

    //狀態控制
    public void setIsShootOut(boolean isShootOut) {
        this.isShootOut = isShootOut;
        this.count = 0;
    }

    public boolean getIsShootOut() {
        return this.isShootOut;
    }
    //狀態控制end

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
        this.actMoveSpeed = 60 - this.moveSpeed;
        this.moveDelay.setDelayFrame(this.actMoveSpeed);
    }

    public float getMoveSpeed() {
        return this.moveSpeed;
    }
    //delay控制end

    //再一次 開始 設定
    public boolean setNewStart() {
        if (getIsShootOut()) {
            setAerageSpeed();
            setAngle();
            this.renderer.setAngle(getAngle());
            float x = this.start.getCenterX() - this.width() / 2;
            float y = this.start.getCenterY() - this.height() / 2;
            setXY(x, y);
            return true;
        }
        return false;
    }

    private void setAerageSpeed() {
        if (getIsShootOut()) {
            this.averageSpeed.setCenterX(this.start.getCenterX());//被給予start的centerX
            this.averageSpeed.setCenterY(this.start.getCenterY());//被給予start的centerY
            this.averageSpeed.setGoalCenterX(Global.mapMouseX);//直接拿目標的x
            this.averageSpeed.setGoalCenterY(Global.mapMouseY);//直接拿目標的y
            return;
        }
        return;
    }
    //再一次 開始 設定end

    @Override
    public void update() {
        if (getIsShootOut()) {//如果是 射擊出去的狀態 就移動
            float dx = this.averageSpeed.offsetDX();
            float dy = this.averageSpeed.offsetDY();
            float reMoveSpeed = this.averageSpeed.getReMoveSpeed();
            if (this.count == 0) {
                float d = 2.5f;
                this.offset(dx * reMoveSpeed / d,
                        dy * reMoveSpeed / d);
                this.count = 1;
            } else {
                this.offset(dx, dy);
            }
            Graph other;
            for (int i = 0; i < this.allObjects.size(); i++) {
                GameObject obj = this.allObjects.get(i);
                if (obj.getType().equals("Map")
                        || obj.getType().equals("Ammo")
                        || obj.getType().equals("Actor")
                        || obj.getType().equals("Gun")) {
                    continue;
                }
                other = this.allObjects.get(i).getCollider();
                if (!(obj.getType().equals("Maps")) && this.getCollider().intersects(other)) {
                    if (obj.getType().equals("Enemy") && this.getCollider().intersects(other)) {
                        obj.subtractHp();
                    }
                    setIsShootOut(false);
                    this.setXY(-1000, -1000);
                } else if (obj.getType().equals(" Maps") && this.getCollider().innerCollisionToCollision(other)) {
                    setIsShootOut(false);
                    this.setXY(-1000, -1000);
                }
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        this.renderer.paint(g);
    }

    //以下 暫時先不設計 / 用不到
    @Override
    public void setDir(int dir) {
    }//方向用不到

//    @Override
//    public void setMovementPressedStatus(int dir, boolean status) {
//    }//八方向用不到
//
//    @Override
//    public void setStand(boolean isStand) {
//    }
}
    //屬性
//    private boolean isCoolState;//是否為冷卻狀態 Origine false
//    private boolean isChangeImage;//是否換特效 Origine false
//屬性end
//建構子內
//        setIsCoolState(false);
//        setIsChangeImage(false);
//建構子內end
//funtion
//    public void setIsCoolState(boolean isCoolState) {
//        this.isCoolState = isCoolState;
//    }
//
//    public boolean getIsCoolState() {
//        return this.isCoolState;
//    }
//    public void setIsChangeImage(boolean isChangeImage) {
//        this.isChangeImage = isChangeImage;
//    }
//
//    public boolean getIsChangeImage() {
//        return this.isChangeImage;
//    }
//funtion end

