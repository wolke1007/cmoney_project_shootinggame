/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameobj.enemy;

import controllers.AudioPath;
import controllers.MusicResourceController;
import controllers.AudioResourceController;
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
public class ZombieNormal extends MoveMode {

    //圖片
    private RendererToRotate renderer;//旋轉圖渲染器
    private Delay imageDelay;
    private int imageState;
    private int deadImage;
    //圖片end

    //目標的血量控制
    private Delay targetHp;
    //目標的血量控制

    //移動分段
    private AverageSpeed averageSpeed;
    private VectorCollision vectorMove;
    //移動分段

    private String deadType;

    public ZombieNormal(GameObject self, GameObject target, int moveSpeed, String[] path) {
        super(self, target, moveSpeed);
        this.renderer = new RendererToRotate(path, self, getAngle());
        setAverageSpeed();
        setVectorMove();
        setMoveSpeedDetail();
        this.imageState = 0;
        this.deadImage = 0;
        this.deadType = "Enemy";
    }

    public String getType() {
        return this.deadType;
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
        this.vectorMove = new VectorCollision(getSelf(), 0, 0, new String[]{"Map", "Boss", "Ammo"}, Global.INNER);
//        this.vectorMove.setMultiple(3f);
        this.vectorMove.setDivisor(5f);
    }

    private void setMoveSpeedDetail() {
        this.targetHp = new Delay(30);
        this.targetHp.start();
        this.imageDelay = new Delay(1);
        this.imageDelay.start();
    }

    private void move() {
        this.setAngle();
        this.renderer.setAngle(this.getAngle());
        this.setAverageSpeed();
        if (this.getMoveDelay().isTrig()) {
            if (this.imageDelay.isTrig()) {
                this.renderer.setState(this.imageState++ % 31);
            }
            if (this.targetHp.isTrig()) {
                this.vectorMove.setHurtPoint(1);
            }
            this.vectorMove.newOffset(this.averageSpeed.offsetDX(), this.averageSpeed.offsetDY());
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
            this.deadType = "Map";
            this.imageDelay.setDelayFrame(4);
            if(this.deadImage == 0){
                MusicResourceController.getInstance().tryGetMusic(AudioPath.ENEMY_DEAD).play();
//                AudioResourceController.getInstance().play(AudioPath.ENEMY_DEAD);
            }
            if (this.imageDelay.isTrig()) {
                this.renderer.setState(this.deadImage++ % 20 + 31);
                if (this.deadImage > 20) {
                    this.setIsRemove(true);
                    getSelf().setXY(-100, -100);
                }
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        this.renderer.paint(g);
    }

}
