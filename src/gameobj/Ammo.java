/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameobj;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
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

    //子彈移動控制
    private Delay moveDelay;
    private float moveSpeed;
    private float actMoveSpeed;
    //子彈移動控制end

    //移動分段
    private Angle angle;
    private AverageSpeed averageSpeed;
    private int count = 0;
    //移動分段end

    public Ammo(String colliderType, float x, float y, int moveSpeed, String[] path) {
        super(colliderType, x, y, Global.UNIT_X / 2, Global.UNIT_Y / 2, Global.UNIT_X / 2, Global.UNIT_Y / 2);
        setAngle(super.getCenterX(), super.getCenterY());
        this.renderer = new RendererToRotate(path, super.getX(), super.getY(), getAngle());
        setMoveSpeedDetail(moveSpeed);//初始化移動應為最大值，暫時不該限制delay
        this.averageSpeed = new AverageSpeed(super.getCenterX(), super.getCenterY(), Global.mapMouseX, Global.mapMouseY, 10, true);//30為子彈的移動距離值
        setIsShootOut(true);
        super.paintPriority = 1; // 畫圖順序僅次於主角，此順序可討論
//        System.out.println("Ammunition");
    }

    //圖片資料
    @Override
    public void setX(float x) {
        super.setX(x);
        if (this.renderer != null) {
            this.renderer.setX(x);
        }
    }

    @Override
    public void setY(float y) {
        super.setY(y);
        if (this.renderer != null) {
            this.renderer.setY(y);
        }
    }

    @Override
    public void setXY(float x, float y) {
        setX(x);
        setY(y);
    }
    //圖片資料end

    //角度計算
    public void setAngle(float centerX, float centerY) {
        if (this.angle == null) {
            this.angle = new Angle(centerX, centerY, Global.mapMouseX, Global.mapMouseY);
            return;
        }
        this.angle.setCenterX(centerX);
        this.angle.setCenterY(centerY);
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
            return range = 0;
        } else if (range > 60) {
            return range = 60;
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
    public boolean setNewStart(float centerX, float centerY) {
        if (getIsShootOut()) {
            setAerageSpeed(centerX, centerY);
            setAngle(centerX, centerY);
            this.renderer.setAngle(getAngle());
            float x = centerX - super.width() / 2;
            float y = centerY - super.height() / 2;
            setXY(x, y);
            return true;
        }
        return false;
    }

    private boolean setAerageSpeed(float centerX, float centerY) {
        if (getIsShootOut()) {
            this.averageSpeed.setCenterX(centerX);//被給予Actor的centerX
            this.averageSpeed.setCenterY(centerY);//被給予Actor的centerY
            this.averageSpeed.setGoalCenterX(Global.mapMouseX);//直接拿目標的x
            this.averageSpeed.setGoalCenterY(Global.mapMouseY);//直接拿目標的y
            return true;
        }
        return false;
    }

    //再一次 開始 設定end
    @Override
    public void offset(float dx, float dy) {
        super.offset(dx, dy);
        this.renderer.offset(dx, dy);
    }

    @Override
    public void update() {
        if (getIsShootOut()) {//如果是 射擊出去的狀態 或 可以被畫出的狀態 就移動
            if (this.count == 0) {
                int d = 20;
                this.offset(d * (float) this.averageSpeed.offsetDX(), d * (float) this.averageSpeed.offsetDY());
                this.count = 1;
            } else {
                this.offset((float) this.averageSpeed.offsetDX(), (float) this.averageSpeed.offsetDY());
            }

        }
        if (getCollider().left() < Global.mapEdgeLeft || this.getCollider().right() > Global.mapEdgeRight || this.getCollider().top() < Global.mapEdgeUp || this.getCollider().bottom() > Global.mapEdgeDown) {
            setIsShootOut(false);
            this.setXY(-50, -50);
            this.renderer.setX(-50);
            this.renderer.setY(-50);
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

