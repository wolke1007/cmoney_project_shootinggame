/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameobj;

import controllers.ImagePath;
import graph.Rect;
import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;
import util.Angle;
import util.Delay;
import util.Global;
import util.Move;
import util.Point;

/**
 *
 * @author Cloud-Razer
 */
public class Actor extends GameObject {

    private int dir;
    private RendererToRotate renderer;
    private Angle angle;
    private boolean isStand;
    private View view;
    private LinkedList<GameObject> allObjects;
    private float hp;

    private Delay moveDelay;

    private float moveSpeed; // per frame
    private float actMoveSpeed;

    private Move movement;

    private int moveDistance;

    public Actor(String colliderType, float x, float y, int moveSpeed, String[] path) {//src => Global.ACTOR
        super(colliderType, x, y, Global.UNIT_X, Global.UNIT_Y, Global.UNIT_X, Global.UNIT_Y);
        setAngle(super.getCenterX(), super.getCenterY());
        this.renderer = new RendererToRotate(path, this, getAngle());
        this.isStand = true;
        setActorMoveSpeedDetail(moveSpeed);
        movement = new Move(this);
        this.moveDistance = 10;
        super.paintPriority = 0;
        this.hp = 100f;
    }//多載 建構子 當前版本

    //位置資訊
    @Override
    public void setX(float x) {
        super.setX(x);
    }

    @Override
    public void setY(float y) {
        super.setY(y);
    }

    public void setAllObjects(LinkedList<GameObject> list) {
        this.allObjects = list;
    }

    public float centerX() {
        return super.getCenterX();
    }

    private void setMoveDistance(int moveDistance) {
        this.moveDistance = moveDistance;
    }

    private int getMoveDistance() {
        return this.moveDistance;
    }

    public float centerY() {
        return super.getCenterY();
    }
    //位置資訊end
    
    public float getHP(){
        return this.hp;
    }
    
    public void setHP(float hp){
        this.hp = hp;
    }

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

    //角色移動相關資訊
    private void setActorMoveSpeedDetail(float moveSpeed) {
        this.moveSpeed = limitRange(moveSpeed);
        this.actMoveSpeed = 60 - this.moveSpeed;
        this.moveDelay = new Delay(this.actMoveSpeed);
        this.moveDelay.start();
    }//初始化用

    private float limitRange(float range) {
        if (range < 0) {
            return range = 0;
        } else if (range > 60) {
            return range = 60;
        }
        return range;
    }//限制範圍 0-60

    public void setMoveSpeed(float moveSpeed) {
        this.moveSpeed = limitRange(moveSpeed);
        this.actMoveSpeed = 60 - this.moveSpeed;
        this.moveDelay.setDelayFrame(this.actMoveSpeed);
    }//修改角色移動速度

    public float getMoveSpeed() {
        return this.moveSpeed;
    }//取得目前的速度設定
    //角色移動相關資訊end

    public void setStand(boolean isStand) {
        this.isStand = isStand;
        if (this.isStand) {
            this.moveDelay.stop();
        } else {
            this.moveDelay.start();
        }
    }

    @Override
    public void setDir(int dir) {
        this.dir = dir;
    }

    public void setMovementPressedStatus(int dir, boolean status) {
        this.movement.setPressedStatus(dir, status);
    }

    public void updateRendererAngle() {
        this.setAngle(super.getCenterX(), super.getCenterY());
        this.renderer.setAngle(this.getAngle());
    }

    @Override
    public void update() {
        if (!this.isStand && this.moveDelay.isTrig()) {
            move();
        }
        // 需要先移動再 RenderToRotate 避免 Actor 的圖片跟不上碰撞框的移動
        // 以下一定要更新
        updateRendererAngle();
    }

    private void move() {
        this.movement.moving(this.moveDistance, this.allObjects);
    }

    @Override
    public void paintComponent(Graphics g) {
        g.drawLine(-10000, (int) (this.getCollider().centerY() - Global.viewY), 10000, (int) (this.getCollider().centerY() - Global.viewY));
        g.drawLine((int) (this.getCollider().centerX() - Global.viewX), -10000, (int) (this.getCollider().centerX() - Global.viewX), 10000);
        this.renderer.paint(g);
    }

}
