/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameobj;

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

    private int dir;
    private boolean isStand;

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
        this.isStand = true;
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
    
    public boolean stillSeeing(GameObject obj){
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
        this.dir = dir;
    }

    public void setStand(boolean isStand) {
        this.isStand = isStand;
    }

    @Override
    public void setMovementPressedStatus(int dir, boolean status) {
        this.movement().setPressedStatus(dir, status);
    }

    private void setViewMoveSpeedDetail(int moveSpeed) {
        this.moveSpeed = limitRange(moveSpeed);
        this.actMoveSpeed = 60 - this.moveSpeed;
        this.moveDelay = new Delay(this.actMoveSpeed);
        this.moveDelay.start();
    }//初始化用

    private int limitRange(int range) {
        if (range < 0) {
            return range = 0;
        } else if (range > 60) {
            return range = 60;
        }
        return range;
    }//限制範圍 0-60

    @Override
    public void update() {
        super.offsetX(focusOn.x - this.width / 2);
        super.offsetY(focusOn.y - this.height / 2);
        Global.viewX = (int) super.x;
        Global.viewY = (int) super.y;
    }

    @Override
    public void paintComponent(Graphics g) {
        // 為了解決 paint 順序問題，將所有 GameObject 建立一個 paintPriority 屬性，數字越大越先畫(圖層越下層)，數字最大為 10
        for(int p = 10; p >= 0; p--){
            for (int i = 0; i < this.sawObjects.size(); i++) {
                if(this.sawObjects.get(i).paintPriority == p){
                    this.sawObjects.get(i).paint(g);
                }
            }
        }
    }

}
