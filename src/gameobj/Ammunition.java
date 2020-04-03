/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameobj;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import util.AverageSpeed;
import util.Delay;
import util.Global;

/**
 *
 * @author F-NB
 */
public class Ammunition extends GameObject {

    private RendererToRotate renderer;//旋轉方向設定
    private boolean isShootOut;//是否射擊的狀態 Origine true 創建時就是要射出
    private boolean isPaint;//是否畫出 Origine true 創建時就要射出

    private Delay moveDelay;
    private float moveSpeed;
    private float actMoveSpeed;

    private AverageSpeed averageSpeed;

    private float width;
    private float height;

    public Ammunition(String colliderType, float x, float y, int moveSpeed, String[] path) {
        super(colliderType, x, y, Global.UNIT_X / 2, Global.UNIT_Y / 2, Global.UNIT_X / 2, Global.UNIT_Y / 2);
        setWidth(Global.UNIT_X / 2);
        setHeight(Global.UNIT_Y / 2);
        this.renderer = new RendererToRotate(path, super.getX(), super.getY(), Global.mouseX, Global.mouseY);
        setMoveSpeedDetail(moveSpeed);//初始化移動應為最大值，暫時不該限制delay
        setAerageSpeed(super.getCenterX(), super.getCenterY(), Global.mouseX, Global.mouseY, 30, true);//先切割30等分的距離值
        setIsShootOut(true);
        setIsPaint(true);
//        System.out.println("Ammunition");
    }

    //圖片資料
    @Override
    public void setX(float x) {
        super.setX(x);
    }

    @Override
    public void setY(float y) {
        super.setY(y);
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public void setHeight(float height) {
        this.height = height;
    }
    //圖片資料end

    //狀態控制
    public void setIsShootOut(boolean isShootOut) {
        this.isShootOut = isShootOut;
    }

    public boolean getIsShootOut() {
        return this.isShootOut;
    }

    public void setIsPaint(boolean isPaint) {
        this.isPaint = isPaint;
    }

    public boolean getIsPaint() {
        return this.isPaint;
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

    //設定物體移動距離
    private void setAerageSpeed(float centerX, float centerY, float goalCenterX, float goalCenterY, float moveSpeed, boolean isFixedLength) {
        this.averageSpeed = new AverageSpeed(centerX, centerY, goalCenterX, goalCenterY, moveSpeed, isFixedLength);
    }
    //設定物體移動距離end

    //再一次 開始 設定
    public boolean setNewStart(float centerX, float centerY) {
        if (getIsShootOut()) {
            setAerageSpeed(centerX, centerY);
            float x = centerX - super.width() / 2;
            float y = centerY - super.height() / 2;
            setRenderer(x, y);
            super.setXY(x, y);
            return true;
        }
        return false;
    }

    private boolean setAerageSpeed(float centerX, float centerY) {
        if (getIsShootOut()) {
            this.averageSpeed.setCenterX(centerX);//被給予Actor的centerX
            this.averageSpeed.setCenterY(centerY);//被給予Actor的centerY
            this.averageSpeed.setGoalCenterX(Global.mouseX);//直接拿目標的x
            this.averageSpeed.setGoalCenterY(Global.mouseY);//直接拿目標的y
            return true;
        }
        return false;
    }

    private boolean setRenderer(float x, float y) {
        if (getIsShootOut()) {
            this.renderer.setX(x);//被給予Actor的x + 16
            this.renderer.setY(y);//被給予Actor的y + 16
            this.renderer.setGoalCenterX(Global.mouseX);//直接拿目標的x
            this.renderer.setGoalCenterY(Global.mouseY);//直接拿目標的y
            return true;
        }
        return false;
    }
    //再一次 開始 設定end

    //物體資料
    public float centerX() {
        return super.getCenterX();
    }

    public float centerY() {
        return super.getCenterY();
    }

    //物體資料end

    @Override
    public void update() {
        if (getIsShootOut() || getIsPaint()) {//如果是 射擊出去的狀態 或 可以被畫出的狀態 就移動
            super.offset((float) this.averageSpeed.offsetDX(), (float) this.averageSpeed.offsetDY());
            this.renderer.update();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        if (getIsPaint()) {//可以被畫出
            this.renderer.paint(g);
        }
    }

    //以下 暫時先不設計 / 用不到
    @Override
    public void setDir(int dir) {
    }//方向用不到

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

}
