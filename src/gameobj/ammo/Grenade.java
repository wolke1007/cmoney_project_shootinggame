/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameobj.ammo;

import controllers.ImagePath;
import gameobj.GameObject;
import java.awt.Graphics;
import java.util.ArrayList;
import renderer.Renderer;
import renderer.RendererToRotate;
import util.AverageSpeed;
import util.Delay;
import util.Global;
import util.VectorCollision;

/**
 *
 * @author F-NB
 */
public class Grenade extends ShootMode {

    private RendererToRotate renderer;//旋轉圖渲染器
    private Renderer rendererEffect;//爆炸特效圖
    private Delay effectDelay;
    private int delayCount;
    private int attackRange;
    private ArrayList<GameObject> allObjects;
    private GameObject self;

    private AverageSpeed averageSpeed;
    private int countdown;//計數
    private int moveDistance;//移動距離倍數 3 -> 4 -> 5 -> 4 -> 3 -> 2 -> 1 -> 0 
    private VectorCollision vecterMove;

    public Grenade(GameObject self, GameObject start, float moveSpeed, String[] path) {
        super(start, moveSpeed);
        setSelf(self);
        setAerageSpeed();
        setVectorMove();
        this.renderer = new RendererToRotate(path, self, getAngle());
        //特效圖 待處理
        this.rendererEffect = new Renderer();
        this.rendererEffect.setImage(ImagePath.BOOM_CONTINUE);
        //特效圖 待處理end
        setCountdown(0);
        setMoveDistance(3);
        setEffectDelay();
        setAttackRange(150);
        setType("Grenade");
    }

    private void setAttackRange(int attackRange) {
        this.attackRange = attackRange;
    }

    public void setVectorMove() {
        this.vecterMove = new VectorCollision(getSelf(), 0, 0,
                new String[]{"Map", "Ammo", "Actor", "Enemy", "Barrier"}, new String[]{"Maps"});
        this.vecterMove.setIsBackMove(false);
        this.vecterMove.setHurtPoint(0);
        this.vecterMove.setDivisor(5);
    }

    public void setSelf(GameObject self) {
        this.self = self;
    }

    public GameObject getSelf() {
        return this.self;
    }

    private void setCountdown(int countdown) {
        this.countdown = countdown;
    }

    private int getCountdown() {
        return this.countdown;
    }

    private void setMoveDistance(int moveDistance) {
        this.moveDistance = moveDistance;
    }

    private void plusMoveDistance() {
        this.moveDistance++;
    }

    private void lessMoveDistance() {
        this.moveDistance--;
    }

    private int getMoveDistance() {
        return this.moveDistance;
    }

    private void setEffectDelay() {
        if (this.effectDelay == null) {
            this.effectDelay = new Delay(2);
            this.effectDelay.stop();
            this.delayCount = 0;
        }
        this.effectDelay.stop();
        this.delayCount = 0;
    }

    //再一次 開始
    @Override
    public void setNewStart() {
        setCountdown(0);
        setMoveDistance(3);
        setAerageSpeed();
        setAngle();
        this.renderer.setAngle(getAngle());
        float x = getStart().getCenterX() - getSelf().width() / 2f;
        float y = getStart().getCenterY() - getSelf().height() / 2f;
        getSelf().setXY(x, y);
        setEffectDelay();
    }

    private void setAerageSpeed() {
        if (this.averageSpeed == null) {
            this.averageSpeed = new AverageSpeed(getSelf().getCenterX(), getSelf().getCenterY(), Global.mapMouseX, Global.mapMouseY, 50, false);
        }
        this.averageSpeed.setCenterX(getStart().getCenterX());//被給予start的centerX
        this.averageSpeed.setCenterY(getStart().getCenterY());//被給予start的centerY
        this.averageSpeed.setGoalCenterX(Global.mapMouseX);//直接拿目標的x
        this.averageSpeed.setGoalCenterY(Global.mapMouseY);//直接拿目標的y
        return;
    }//再一次 開始 設定end

    @Override
    public void setAllObject(ArrayList<GameObject> list) {
        this.allObjects = list;
        this.vecterMove.setAllObjects(list);
    }

    @Override
    public boolean update() {
        if (this.getMoveDelay().isTrig()) {
            float dx = this.averageSpeed.offsetDX();
            float dy = this.averageSpeed.offsetDY();
            this.vecterMove.newOffset(dx * getMoveDistance() * 1.5f, dy * getMoveDistance() * 1.5f);
            if (getCountdown() >= 0 && getCountdown() < 3) {
                this.plusMoveDistance();
            } else if (getCountdown() >= 3) {
                this.lessMoveDistance();
            }
            this.countdown++;
            if (this.vecterMove.getIsCollision() || getMoveDistance() == 0) {//撞上障礙物 或 不能再移動
                if (this.delayCount == 0) {
                    for (int i = 0; i < this.allObjects.size(); i++) {
                        if (Math.sqrt(Math.pow(this.allObjects.get(i).getCenterX() - getSelf().getCenterX(), 2)
                                + Math.pow(this.allObjects.get(i).getCenterY() - getSelf().getCenterY(), 2)) < this.attackRange) {
                            if (this.allObjects.get(i).getType().equals("Actor")) {
                                continue;
                            }
                            this.allObjects.get(i).subtractHp();
                            this.allObjects.get(i).subtractHp();
                            this.allObjects.get(i).subtractHp();
                        }
                    }
                }
                setCountdown(-1);
                setMoveDistance(0);
                this.effectDelay.start();
                if (this.effectDelay.isTrig()) {
                    this.delayCount++;
                }
                if (this.delayCount > 6) {
                    this.getSelf().setXY(-1000, -1000);
                    this.effectDelay.stop();
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void paintComponent(Graphics g) {
        if (this.getCountdown() != -1) {
            this.renderer.paint(g);
        } else {
            this.rendererEffect.paint(g,
                    (int) getSelf().getCenterX() - this.attackRange,
                    (int) getSelf().getCenterY() + this.attackRange,
                    (int) getSelf().getCenterX() + this.attackRange,
                    (int) getSelf().getCenterY() - this.attackRange,
                    (this.delayCount % 5) * 150,
                    (this.delayCount / 5) * 150,
                    (this.delayCount % 5) * 150 + 150,
                    (this.delayCount / 5) * 150 + 150);
        }
    }

}
