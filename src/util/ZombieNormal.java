/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import gameobj.GameObject;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import renderer.RendererToRotate;

/**
 *
 * @author F-NB
 */
public class ZombieNormal extends MoveMode {

    public final int[] normalStep = {0, 1, 2, 1};

    //圖片
    private RendererToRotate renderer;//旋轉圖渲染器
    private Delay imageDelay;
    private int imageState;
    //圖片end

    //目標的血量控制
    private Delay targetHp;
    //目標的血量控制

    //移動分段
    private AverageSpeed averageSpeed;
    private VectorCollision vectorMove;
    //移動分段

    public ZombieNormal(GameObject self, GameObject target, int moveSpeed, String[] path) {
        super(self, target, moveSpeed);
        this.renderer = new RendererToRotate(path, self, getAngle());
        setAverageSpeed();
        setVectorMove();
        setMoveSpeedDetail();
        this.imageState = 0;
    }

    @Override
    public void setAllObject(ArrayList<GameObject> list) {
        this.vectorMove.setAllObjects(list);
    }

    private void setAverageSpeed() {
        if (this.averageSpeed == null) {
            this.averageSpeed = new AverageSpeed(getSelf().getCenterX(), getSelf().getCenterY(), getTarget().getCenterX(), getTarget().getCenterY(), 50, true);
        }
        this.averageSpeed.setCenterX(getSelf().getCenterX());
        this.averageSpeed.setCenterY(getSelf().getCenterY());
        this.averageSpeed.setGoalCenterX(getTarget().getCenterX());
        this.averageSpeed.setGoalCenterY(getTarget().getCenterY());
    }

    private void setVectorMove() {
        this.vectorMove = new VectorCollision(getSelf(), 0, 0);
        this.vectorMove.setMultiple(1f);
        this.vectorMove.setDivisor(5);
    }

    private void setMoveSpeedDetail() {
        this.targetHp = new Delay(30);
        this.targetHp.start();
        this.imageDelay = new Delay(10);
        this.imageDelay.start();
    }

    private void move() {
        if (this.getMoveDelay().isTrig()) {
            this.setAngle();
            this.renderer.setAngle(this.getAngle());
            this.setAverageSpeed();
            if (this.imageDelay.isTrig()) {
                this.renderer.setState(this.normalStep[this.imageState++ % 4]);
            }
            if (this.targetHp.isTrig()) {
                this.vectorMove.setIsHurt(true);
            }
            this.vectorMove.newOffset(this.averageSpeed.offsetDX(), this.averageSpeed.offsetDY());
            this.vectorMove.setIsHurt(false);
        }
    }

    @Override
    public void update() {
        if (getSelf().getHp() >= 1) {
            move();
        } else {
            getSelf().setXY(-100, -100);
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        this.renderer.paint(g);
        g.fillRect((int) (getSelf().getX() - Global.viewX), (int) (getSelf().getY() - Global.viewY) - 8, (int) getSelf().width(), 4);
        g.setColor(Color.RED);
        g.fillRect((int) (getSelf().getX() - Global.viewX), (int) (getSelf().getY() - Global.viewY) - 8, (int) getSelf().getHpBarWidth(), 4);
        g.setColor(Color.BLACK);
    }

}