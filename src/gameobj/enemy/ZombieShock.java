/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameobj.enemy;

import gameobj.GameObject;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import renderer.RendererToRotate;
import util.AverageSpeed;
import util.Delay;
import util.Global;
import util.VectorCollision;

/**
 *
 * @author F-NB
 */
public class ZombieShock extends MoveMode {

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
    private int delayCount;

    public ZombieShock(GameObject self, GameObject target, int moveSpeed, String[] path) {
        super(self, target, moveSpeed);
        this.renderer = new RendererToRotate(path, self, getAngle());
        setAverageSpeed();
        setVectorMove();
        setMoveSpeedDetail();
        this.delayCount = 0;
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
        this.vectorMove = new VectorCollision(getSelf(), 0, 0, Global.EXCLUDE, Global.INNER);
        this.vectorMove.setMultiple(1f);
        this.vectorMove.setDivisor(5);
    }

    private void setMoveSpeedDetail() {
        this.targetHp = new Delay(30);
        this.targetHp.start();
        this.imageDelay = new Delay(3);
        this.imageDelay.start();
    }

    private void move() {
        if (this.vectorMove.getIsCollision()) {
            this.setAngle();
            this.setAverageSpeed();
            this.renderer.setAngle(this.getAngle());
            this.renderer.setState(1);
        }
        if (this.getMoveDelay().isTrig()) {
            if (this.delayCount++ > 60) {
                this.delayCount = 0;
                this.vectorMove.setIsCollision(false);
            }
            this.vectorMove.setHurtPoint(2);
            if (!this.vectorMove.getIsCollision()) {
                if (this.imageDelay.isTrig()) {
                    this.renderer.setState(Global.STEPS_WALK_NORMAL[this.imageState++ % 4]);
                }
                this.vectorMove.newOffset(this.averageSpeed.offsetDX() * 3, this.averageSpeed.offsetDY() * 3);
            }
            this.vectorMove.setHurtPoint(0);
        }
    }

    @Override
    public void setAllObject(ArrayList<GameObject> list) {
        this.vectorMove.setAllObjects(list);
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
    }

}
