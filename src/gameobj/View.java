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
    private Renderer hpFrameRenderer;
    private Renderer hpRenderer;

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
        this.hpFrameRenderer = new Renderer(0, new int[0], 0, ImagePath.HP[0]);
        this.hpRenderer = new Renderer(0, new int[0], 0, ImagePath.HP[2]); // HP 第三張圖是 debug 用
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
        super.offsetX(focusOn.x - this.width / 2);
        super.offsetY(focusOn.y - this.height / 2);
        Global.viewX = super.x;
        Global.viewY = super.y;
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
                if (paintObj instanceof Actor) {
                    if (((Actor) paintObj).getHp() >= 0) {
                        int hpFrameX = (int) super.x;
                        int hpFrameY = (int) super.y;
                        float hpRate = ((Actor) paintObj).getHP() / 100f;
                        this.hpFrameRenderer.paint(g, hpFrameX, hpFrameY, hpFrameX + Global.HP_FRAME_WIDTH, hpFrameY + Global.HP_FRAME_HEIGHT, 0, 0, Global.HP_FRAME_IMG_W, Global.HP_FRAME_IMG_H);
                        this.hpRenderer.paint(g,
                                hpFrameX + 12, hpFrameY + 8,
                                (int)(hpFrameX + 12 + (Global.HP_WIDTH * hpRate)), hpFrameY -7 + Global.HP_HEIGHT,
                                0, 0,
                                (int)(Global.HP_IMG_W * hpRate), Global.HP_IMG_H);
                    }
                }
            }
        }
    }

}
