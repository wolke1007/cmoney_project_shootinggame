/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameobj.ammo;

import gameobj.GameObject;
import graph.Graph;
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
public class Bullet extends ShootMode {

    private RendererToRotate renderer;//旋轉圖渲染器
    private Delay rendererEffectDlay;
    private GameObject self;

    private AverageSpeed averageSpeed;
    private int count;//初始位置的狀態設定
    private VectorCollision vecterMove;

    public Bullet(GameObject self, GameObject start, float moveSpeed, String[] path) {
        super(start, moveSpeed);
        setSelf(self);
        setVectorMove();
        this.renderer = new RendererToRotate(path, self, getAngle());
        this.rendererEffectDlay = new Delay(60);
        this.rendererEffectDlay.start();
        this.averageSpeed = new AverageSpeed(self.getCenterX(), self.getCenterY(), Global.mapMouseX, Global.mapMouseY, 97, true);//子彈的移動速度
        setCount(0);
        setType("Bullet");
    }

    public void setVectorMove() {
        this.vecterMove = new VectorCollision(getSelf(), 0, 0,
                new String[]{"Map", "Ammo", "Actor"}, Global.INNER);
        this.vecterMove.setIsBackMove(false);
        this.vecterMove.setDivisor(5);
    }

    public void setSelf(GameObject self) {
        this.self = self;
    }

    public GameObject getSelf() {
        return this.self;
    }

    public void setCount(int count) {
        this.count = count;
    }

    private int getCount() {
        return this.count;
    }

    //再一次 開始
    @Override
    public void setNewStart() {
        setCount(0);
        setAerageSpeed();
        setAngle();
        this.renderer.setAngle(getAngle());
        float x = getStart().getCenterX() - getSelf().width() / 2f;
        float y = getStart().getCenterY() - getSelf().height() / 2f;
        getSelf().setXY(x, y);
    }

    private void setAerageSpeed() {
        this.averageSpeed.setCenterX(getStart().getCenterX());//被給予start的centerX
        this.averageSpeed.setCenterY(getStart().getCenterY());//被給予start的centerY
        this.averageSpeed.setGoalCenterX(Global.mapMouseX);//直接拿目標的x
        this.averageSpeed.setGoalCenterY(Global.mapMouseY);//直接拿目標的y
        return;
    }//再一次 開始 設定end

    @Override
    public void setAllObject(ArrayList<GameObject> list) {
        this.vecterMove.setAllObjects(list);
    }

    @Override
    public boolean update() {
        if (this.getMoveDelay().isTrig()) {
            float dx = this.averageSpeed.offsetDX();
            float dy = this.averageSpeed.offsetDY();
            float startMoveSpeed = this.averageSpeed.getReMoveSpeed();
            this.vecterMove.setHurtPoint(1);
            if (getCount() == 0) {
                this.renderer.setState(0);
                float d = 2f;
                this.vecterMove.newOffset(dx * startMoveSpeed / d,
                        dy * startMoveSpeed / d);
                setCount(1);
                this.rendererEffectDlay.click();
            } else {
                if (this.rendererEffectDlay.isTrig()) {
                    this.renderer.setState(1);
                }
                this.vecterMove.newOffset(dx, dy);
            }
            if (this.vecterMove.getIsCollision()) {
                this.vecterMove.setHurtPoint(0);
                getSelf().setXY(-1000, -1000);
                return false;
            }
            this.vecterMove.setHurtPoint(0);
        }
        return true;
    }

    @Override
    public void paintComponent(Graphics g) {
        this.renderer.paint(g);
    }

}
