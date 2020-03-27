/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameobj;

import graph.Rect;
import java.awt.Color;
import java.awt.Graphics;
import util.Delay;
import util.Global;

/**
 *
 * @author Cloud-Razer
 */
public class Actor extends GameObject {

    private int dir;
    private RendererToRotate renderer;
    private boolean isStand;
    private View view;

    private Delay moveDelay;

    private int moveSpeed; // per frame
    private int actMoveSpeed;

    private int width;
    private int height;

    public Actor(int[] steps, int x, int y, int moveSpeed, String src, View view) {//src => Global.ACTOR
        super(x, y, Global.UNIT_X, Global.UNIT_Y, Global.UNIT_X, Global.UNIT_Y);
        setWidth(Global.UNIT_X);
        setHeight(Global.UNIT_Y);

        this.renderer = new RendererToRotate(src, super.getX(), super.getY(), Global.mouseX, Global.mouseY);
        this.isStand = true;
        setView(view);
        setActorMoveSpeedDetail(moveSpeed);
    }//多載 建構子 當前版本

    public void setView(View view) {
        this.view = view;
    }

    public void setActX(int x) {
        super.setX(x);
    }

    public void setActY(int y) {
        super.setY(y);
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    private void setActorMoveSpeedDetail(int moveSpeed) {
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

    public void setMoveSpeed(int moveSpeed) {
        this.moveSpeed = limitRange(moveSpeed);
        this.actMoveSpeed = 60 - this.moveSpeed;
        this.moveDelay.setDelayFrame(this.actMoveSpeed);
    }//修改角色移動速度

    public int getMoveSpeed() {
        return this.moveSpeed;
    }//取得目前的速度設定

    public void setStand(boolean isStand) {
        this.isStand = isStand;
        if (this.isStand) {
            this.moveDelay.stop();
        } else {
            this.moveDelay.start();
        }
    }

    public void setDir(int dir) {
        this.dir = dir;
        this.view.setDir(dir);
    }

    @Override
    public void update() {
        this.renderer.setCenterX(super.getX());
        this.renderer.setCenterY(super.getY());
        this.renderer.setGoalCenterX(Global.mouseX);
        this.renderer.setGoalCenterY(Global.mouseY);
        renderer.update();
        //以上一定要更新
        
        if (!this.isStand && this.moveDelay.isTrig()) {
            move();
            // 利用 setX setY 讓 debug 的方形碰撞偵測框跟著 Actor 實體移動
            super.offsetX(super.getX() + this.width / 2);
            super.offsetY(super.getY() + this.height / 2);
        }
    }

    private void move() {
        int speed = 4;
        Rect act = this.rect;
        Rect view = this.view.rect;
        switch (this.dir) {
            case Global.UP:
                if (!act.screenEdgeCheck("up")) {
                    super.setY(super.getY() - Global.UNIT_Y / speed);
                }
                if (!view.screenEdgeCheck("up")) {
                    this.view.move(this.dir);
                }
                break;
            case Global.DOWN:
                if (!act.screenEdgeCheck("down")) {
                    super.setY(super.getY() - Global.UNIT_Y / speed);
                }
                if (!view.screenEdgeCheck("down")) {
                    this.view.move(this.dir);
                }
                break;
            case Global.LEFT:
                if (!act.screenEdgeCheck("left")) {
                    super.setY(super.getY() - Global.UNIT_Y / speed);
                }
                if (!view.screenEdgeCheck("left")) {
                    this.view.move(this.dir);
                }
                break;
            case Global.RIGHT:
                if (!act.screenEdgeCheck("right")) {
                    super.setY(super.getY() - Global.UNIT_Y / speed);
                }
                if (!view.screenEdgeCheck("right")) {
                    this.view.move(this.dir);
                }
                break;
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        this.renderer.paint(g, super.getX(), super.getY(), this.width, this.height);
    }

}
