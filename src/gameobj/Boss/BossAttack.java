/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameobj.Boss;

import controllers.AudioPath;
import controllers.AudioResourceController;
import controllers.ImagePath;
import controllers.MusicResourceController;
import gameobj.GameObject;
import java.awt.Graphics;
import java.util.ArrayList;
import renderer.Renderer;
import renderer.RendererToRotate;
import util.Angle;
import util.AverageSpeed;
import util.Delay;
import util.Global;
import util.VectorCollision;

/**
 *
 * @author F-NB
 */
public class BossAttack extends GameObject {

    private float startX;//已設定
    private float startY;//已設定

    private GameObject target;//已設定
    private ArrayList<GameObject> allObjects;//已設定

    private RendererToRotate renderer;//繪製移動中 的 旋轉圖  //已設定
    private int trigCount;//旋轉特效的切換

    private Renderer effect;//繪製 移動 前/後 的效果  //已設定

    private boolean isMove;//是否移動 false //已設定

    private Delay effectDelay;//換圖的 Delay //已設定
    private int effectCount;//一般特效的切圖切換
    private int attackRange;//攻擊範圍

    private AverageSpeed averageSpeed;
    private VectorCollision vectorMove;
    private Angle angle;

    //自己對目標的移動控制
    private Delay moveDelay;//已設定
    private float moveSpeed;//已設定
    private float actMoveSpeed;//已設定
    private float moveMultiple;//移動倍數
    //自己對目標的移動控制end

    public BossAttack(String colliderType, float x, float y, GameObject target, float moveSpeed, String[] path, int width, int height) {
        super(colliderType, x, y, width, height, width, height);
        this.startX = x;//開始的位置
        this.startY = y;//開始的位置
        this.target = target;//目標
        setIsMove(false);
        setAverageSpeed();
        setVectorMove();
        setMoveMultiple(4);
        this.renderer = new RendererToRotate(path, this, 0);//一開始在0度
        this.effect = new Renderer();
        this.effect.setImage(ImagePath.BOSS_BOOM_CONTINUE);//特效 圖片路徑

        this.trigCount = 0;
        setMoveSpeedDetail(moveSpeed);
        setEffectDelay();
        setAttackRange(width);
        setType("BossAttack");
    }

    public RendererToRotate getRenderer() {
        return this.renderer;
    }

    public void setIsMove(boolean isMove) {
        this.isMove = isMove;
    }//已設定

    public boolean getIsMove() {
        return this.isMove;
    }

    private void setAttackRange(int attackRange) {
        this.attackRange = attackRange;
    }//設定攻擊範圍 //已設定

    private void setAverageSpeed() {
        if (this.averageSpeed == null) {
            this.averageSpeed = new AverageSpeed(this.getCenterX(), this.getCenterY(), this.target.getCenterX(), this.target.getCenterY(), 50, true);
        }
        this.averageSpeed.setCenterX(this.getCenterX());
        this.averageSpeed.setCenterY(this.getCenterY());
        this.averageSpeed.setGoalCenterX(this.target.getCenterX());
        this.averageSpeed.setGoalCenterY(this.target.getCenterY());
    }//已設定

    private void setVectorMove() {
        this.vectorMove = new VectorCollision(this, 0, 0, new String[]{"Map", "Ammo", "Enemy", "Boss", "Barrier", "BossAttack"}, Global.INNER);
        this.vectorMove.setIsBackMove(false);
        this.vectorMove.setDivisor(5f);
    }//已設定

    public void setAngle() {
        if (this.angle == null) {
            this.angle = new Angle(this.getCenterX(), this.getCenterY(), this.target.getCenterX(), this.target.getCenterY());
            return;
        }
        this.angle.setCenterX(this.getCenterX());
        this.angle.setCenterY(this.getCenterY());
        this.angle.setGoalCenterX(this.target.getCenterX());
        this.angle.setGoalCenterY(this.target.getCenterY());
    }//已設定

    public double getAngle() {
        return this.angle.getAngle();
    }//已設定

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
        this.actMoveSpeed = this.moveSpeed;
        this.moveDelay.setDelayFrame(this.actMoveSpeed);
    }

    public float getMoveSpeed() {
        return this.moveSpeed;
    }

    private void setEffectDelay() {
        if (this.effectDelay == null) {
            this.effectDelay = new Delay(5);
        } else {
            this.effectDelay.setDelayFrame(5);
        }
        this.effectDelay.stop();
        this.effectCount = 0;
    }

    public void setNewStart() {
        this.setXY(this.startX, this.startY);//回到原點
        setIsMove(false);
//        setEffectDelay();
        this.vectorMove.setIsCollision(false);
        this.moveDelay.start();
    }

    public void setMoveMultiple(float moveMultiple) {
        this.moveMultiple = moveMultiple;
    }

    public float getMoveMultiple() {
        return this.moveMultiple;
    }

    public void setAllObject(ArrayList<GameObject> list) {
        this.allObjects = list;
        this.vectorMove.setAllObjects(list);
    }

    public ArrayList<GameObject> getAllObject() {
        return this.allObjects;
    }

    public int getEffectCount() {
        return this.effectCount;
    }

    @Override
    public void update() {
        if (this.moveDelay.isTrig()) {//自己本身的移動速度
            if (!this.isMove) {//如果不能移動，隨時面對目標方向
                setAngle();
                setAverageSpeed();
                this.renderer.setAngle(getAngle());//隨時面對目標方向
                this.effectDelay.start();
                if (this.effectDelay.isTrig()) {
                    this.renderer.setState(this.trigCount++ % 3); //特效  0 / 1 / 2  輪流放
                }
            } else {//可以移動的狀況下，就不再旋轉直接移動
                if (!this.vectorMove.getIsCollision()) {//如果沒有撞到，持續移動
                    this.effectDelay.start();
                    float dx = this.averageSpeed.offsetDX();
                    float dy = this.averageSpeed.offsetDY();
                    this.vectorMove.newOffset(dx * this.getMoveMultiple(), dy * this.getMoveMultiple());
                    if (this.effectDelay.isTrig()) {//持續移動的同時也切換特效
                        this.renderer.setState(this.trigCount++ % 4 + 3);//特效 3 / 4 / 5 輪流放    
                    }
                } else {//如果有撞到，先扣血，再放特效
                    if (this.effectCount == 0) {
                        MusicResourceController.getInstance().tryGetMusic(AudioPath.BOSS_ATTACK_BOMB).play();
//                        AudioResourceController.getInstance().play(AudioPath.BOSS_ATTACK_BOMB);
                        this.effectDelay.click();
                        for (int i = 0; i < this.allObjects.size(); i++) {//扣血
                            if (Math.sqrt(Math.pow(this.allObjects.get(i).getCenterX() - this.getCenterX(), 2)
                                    + Math.pow(this.allObjects.get(i).getCenterY() - this.getCenterY(), 2)) < this.attackRange) {
                                if (!this.allObjects.get(i).getType().equals("Actor")) {
                                    continue;
                                }
                                for (int j = 0; j < 5; j++) {
                                    this.allObjects.get(i).subtractHp();
                                }
                            }
                        }//扣血end
                        this.effectDelay.setDelayFrame(1);
                    }
                    if (this.effectDelay.isTrig()) {
                        this.effectCount++;
                        this.effectCount++;
                    }
                    if (this.effectCount > 63) {
                        this.setXY(-10000, -10000);
//                        this.effectDelay.stop();
                        this.moveDelay.stop();
                        setIsMove(false);
                        setEffectDelay();
                    }
                }
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        if (!this.vectorMove.getIsCollision()) {//如果沒有撞到
            this.renderer.paint(g);
        } else {//如果有撞到
            this.effect.paint(g,
                    (int) this.getCenterX() - this.attackRange,
                    (int) this.getCenterY() - this.attackRange,
                    (int) this.getCenterX() + this.attackRange,
                    (int) this.getCenterY() + this.attackRange,
                    (this.effectCount % 9) * 71,
                    (this.effectCount / 9) * 71,
                    (this.effectCount % 9) * 71 + 71,
                    (this.effectCount / 9) * 71 + 71);
        }
    }

    @Override
    public void setDir(int dir) {
    }//用不到
}
