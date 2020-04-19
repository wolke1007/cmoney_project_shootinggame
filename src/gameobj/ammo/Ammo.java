/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameobj.ammo;

import controllers.ImagePath;
import gameobj.GameObject;
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

    private ShootMode shootMode;
    private boolean isShootOut;//是否射擊的狀態 Origine true 創建時就是要射出

    private GameObject start;

    public Ammo(String colliderType, float x, float y, GameObject start, int kind) {
        super(colliderType, x, y, Global.UNIT_MIN * 2, Global.UNIT_MIN * 2, Global.UNIT_MIN * 2, Global.UNIT_MIN * 2);
        setType("Ammo");
        setStart(start);
        selectKind(kind);
        setIsShootOut(true);
        super.paintPriority = 1; // 畫圖順序僅次於主角，此順序可討論
    }

    public void selectKind(int kind) {
        switch (kind) {
            case 1:
                this.shootMode = new Bullet(this, getStart(), 60, ImagePath.BULLET);
                break;
            case 2:
                this.shootMode = new Grenade(this, getStart(), 60, ImagePath.GRENADE);
                break;
        }
    }

    public void setStart(GameObject start) {
        this.start = start;
    }

    public GameObject getStart() {
        return this.start;
    }

    public void setAllObjects(ArrayList<GameObject> list) {
        this.shootMode.setAllObject(list);
    }

    //狀態控制
    public void setIsShootOut(boolean isShootOut) {
        this.isShootOut = isShootOut;
    }

    public boolean getIsShootOut() {
        return this.isShootOut;
    }
    //狀態控制end

    //再一次 開始 設定
    public boolean setNewStart() {
        if (getIsShootOut()) {
            this.shootMode.setNewStart();
            return true;
        }
        return false;
    }

    @Override
    public void update() {
        if (getIsShootOut()) {//如果是 射擊出去的狀態 就移動
            setIsShootOut(this.shootMode.update());
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        this.shootMode.paintComponent(g);
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

