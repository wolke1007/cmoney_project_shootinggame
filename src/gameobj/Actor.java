/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameobj;

import effects.Effect;
import effects.LowHpEffect;
import java.awt.Graphics;
import java.util.ArrayList;
import renderer.Renderer;
import renderer.RendererToRotate;
import util.Angle;
import util.Delay;
import util.Global;
import util.Move;

/**
 *
 * @author Cloud-Razer
 */
public class Actor extends GameObject {

    private int dir;
    private RendererToRotate rotateRenderer;
    private Renderer renderer;
    private Angle angle;
    private boolean isStand;
    private ArrayList<Effect> effects;

    private Delay moveDelay;

    private float moveSpeed; // per frame
    private float actMoveSpeed;
    private Move movement;

    public Actor(String colliderType, float x, float y, int moveSpeed, String[] path) {//src => Global.ACTOR
        super(colliderType, x, y, Global.UNIT_X, Global.UNIT_Y, Global.UNIT_X, Global.UNIT_Y);
        setAngle();
        this.rotateRenderer = new RendererToRotate(path, this, getAngle());
        this.renderer = new Renderer();
        this.isStand = true;
        setActorMoveSpeedDetail(moveSpeed);
        this.movement = new Move(this);
        super.paintPriority = 0;
        setHpPoint(100);
        setType("Actor");
        this.effects = new ArrayList();
        this.effects.add(new LowHpEffect((int) this.x, (int) this.y, Global.SCREEN_X, Global.SCREEN_Y, this));
    }//多載 建構子 當前版本

    //位置資訊
    public void setAllObjects(ArrayList<GameObject> list) {
        this.movement.setAllObjects(list);
    }

    //位置資訊end
    //角度計算
    public void setAngle() {
        if (this.angle == null) {
            this.angle = new Angle(this.getCenterX(), this.getCenterY(), Global.mapMouseX, Global.mapMouseY);
            return;
        }
        this.angle.setCenterX(this.getCenterX());
        this.angle.setCenterY(this.getCenterY());
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
            return 0;
        } else if (range > 60) {
            return 60;
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

    public RendererToRotate getRenderer() {
        return this.rotateRenderer;
    }
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
        this.setAngle();
        this.rotateRenderer.setAngle(this.getAngle());
    }

    private void updateEffects() {
        for (int i = 0; i < this.effects.size(); i++) {
            this.effects.get(i).update();
        }
    }

    private void paintEffects(Graphics g) {
        for (int i = 0; i < this.effects.size(); i++) {
            if (this.effects.get(i).getRun()) {
                this.effects.get(i).paint(g);
            }
        }
    }

    private void move() {
        this.movement.moving(10);
    }

    @Override
    public void update() {
        if (!this.isStand && this.moveDelay.isTrig()) {
            move();
        }
        // 需要先移動再 RenderToRotate 避免 Actor 的圖片跟不上碰撞框的移動
        // 以下一定要更新
        updateRendererAngle();
        updateEffects();
    }

    @Override
    public void paintComponent(Graphics g) {
        this.rotateRenderer.paint(g);
        paintEffects(g);
    }

}
//////////////////////////////////////////////////////////////
//    private int moveDistance;

//    private int keyModeChange;// 0 徒手, 1 步槍, 2 手榴彈 , 3 迫擊砲
//    private boolean haveRifle;//有步槍
//    private int rifleNum;//步槍彈數量
//    private boolean haveGuava;//有手榴彈
//    private int guavaNum;//手榴彈數量
//    private boolean haveMortar;//有迫擊砲
//    private int mortarNum;//迫擊砲數量
//////////////////////////////////////////////////////////////
//        this.moveDistance = 10;
//////////////////////////////////////////////////////////////
//    public void setHaveRifle(boolean haveRifle) {
//        this.haveRifle = haveRifle;
//    }
//
//    public boolean getHaveRifle() {
//        return this.haveRifle;
//    }
//
//    public void setHaveGuava(boolean haveGuava) {
//        this.haveGuava = haveGuava;
//    }
//
//    public boolean getHaveGuava() {
//        return this.haveGuava;
//    }
//
//    public void setHaveMortar(boolean haveMortar) {
//        this.haveMortar = haveMortar;
//    }
//
//    public boolean getHaveMortar() {
//        return this.haveMortar;
//    }
//    private void setMoveDistance(int moveDistance) {
//        this.moveDistance = moveDistance;
//    }
//
//    private int getMoveDistance() {
//        return this.moveDistance;
//    }
