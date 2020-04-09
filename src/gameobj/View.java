/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameobj;

import controllers.ImagePath;
import java.awt.Graphics;
import java.util.LinkedList;
import util.Delay;
import util.Global;
import util.Move;
import util.Point;

/**
 *
 * @author Cloud-Razer
 */
public class View extends GameObject {

    private Delay moveDelay;

    private float moveSpeed; // per frame
    private float actMoveSpeed;

    private float width;
    private float height;

    private Move movement;
    private int moveDistance;
    private LinkedList<GameObject> sawObjects;
    private GameObject focusOn;

    public View(int moveSpeed, int width, int height, GameObject focusOn) {
        super("rect", focusOn.x - (Global.VIEW_WIDTH / 2 - focusOn.width() / 2),
                focusOn.y + (focusOn.height() / 2) - (Global.VIEW_HEIGHT / 2),
                width, height, width, height);
        Global.log("rec x" + super.x + " ,y" + super.y + " ,width:" + width + " ,height:" + height);
        this.width = width;
        this.height = height;
        setViewMoveSpeedDetail(moveSpeed);
        movement = new Move(this);
        sawObjects = new LinkedList<GameObject>();
        this.moveDistance = 10;
        this.focusOn = focusOn;
    }

    public void saw(GameObject obj) {
        this.sawObjects.add(obj);
    }

    public LinkedList getSaw() {
        return this.sawObjects;
    }

    public void removeSeen(GameObject obj) {
        for (int i = 0; i < this.sawObjects.size(); i++) {
            if (this.sawObjects.get(i) == obj) {
                this.sawObjects.remove(i);
                return;
            }
        }
    }

    public boolean stillSeeing(GameObject obj) {
        for (int i = 0; i < this.sawObjects.size(); i++) {
            if (this.sawObjects.get(i) == obj) {
                return true;
            }
        }
        return false;
    }

    public Move movement() {
        return this.movement;
    }

    @Override
    public void setDir(int dir) {
    }

    public void setStand(boolean isStand) {
    }

    private void setViewMoveSpeedDetail(float moveSpeed) {
        this.moveSpeed = limitRange(moveSpeed);
        this.actMoveSpeed = 60 - this.moveSpeed;
        this.moveDelay = new Delay(this.actMoveSpeed);
        this.moveDelay.start();
    }//初始化用

    private float limitRange(float range) {
        if (range < 0) {
            return 0;
        } else if (range > 60) {
            return 60;
        }
        return range;
    }//限制範圍 0-60

    @Override
    public void update() {
        float x = focusOn.x - this.width / 2;
        float y = focusOn.y - this.height / 2;
        if(x >= 0 && x + Global.VIEW_WIDTH <= (Global.MAP_WIDTH * Math.sqrt(Global.MAP_QTY))){
            super.offsetX(x);
            Global.viewX = super.x;
        }
        if (y>=0 && y + Global.VIEW_HEIGHT <= (Global.MAP_HEIGHT * Math.sqrt(Global.MAP_QTY))){
            super.offsetY(y);
            Global.viewY = super.y;
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        // 為了解決 paint 順序問題，將所有 GameObject 建立一個 paintPriority 屬性，數字越大越先畫(圖層越下層)，數字最大為 10
        for (int p = 10; p >= 0; p--) {
            for (int i = 0; i < this.sawObjects.size(); i++) {
                GameObject paintObj = this.sawObjects.get(i);
                if (paintObj.paintPriority == p) {
                    paintObj.paint(g);
                }
            }
        }
    }

}
