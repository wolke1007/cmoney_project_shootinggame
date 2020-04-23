/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameobj.Boss;

import controllers.ImagePath;
import gameobj.GameObject;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import renderer.Renderer;
import renderer.RendererToRotate;
import util.Delay;
import util.Global;

/**
 *
 * @author F-NB
 */
public class Boss extends GameObject {

    private boolean startAttack;//開始攻擊
    private boolean startPaint;//開始畫
    //身體
    private Renderer renderer;
    //手
    private Renderer rendererOfRightHand;
    private Renderer rendererOfLeftHand;
    //頭
    private BossAttack bossHead;
    private Delay boosHeadDelay;
    private int moveCount;
    private int change;
    //左手
    private BossAttack bossLeftHand;
    //右手
    private BossAttack bossRightHand;
    //火球
    private BossAttack bossFire;
    //處發間隔控制
    private Delay nextTrig;
    private int stateMode;
    private int moveDistance;
    //boss end
    private Renderer rendererOfBossEnd;
    private Delay bossEndDelay;
    private int bossEndCount;

    public Boss(String colliderType, float x, float y, GameObject target, int moveSpeed) {
        super(colliderType, x, y, Global.UNIT_MIN * 56, Global.UNIT_MIN * 25 + 6, Global.UNIT_MIN * 56, Global.UNIT_MIN * 25 + 6);
        this.bossHead = new BossAttack("circle", this.getCenterX() - 226, this.getCenterY() - 82, target, 60, ImagePath.BOSS_HEAD, 452, 259);
        this.bossLeftHand = new BossAttack("circle", this.getCenterX() - 290, this.getCenterY() + 113, target, 60, ImagePath.BOSS_ATTACK_LEFTHAND, Global.UNIT_MIN * 13, Global.UNIT_MIN * 13);
        this.bossRightHand = new BossAttack("circle", this.getCenterX() + 130, this.getCenterY() + 115, target, 60, ImagePath.BOSS_ATTACK_RIGHTHAND, Global.UNIT_MIN * 13, Global.UNIT_MIN * 13);
        this.bossFire = new BossAttack("circle", this.getCenterX() - 78, this.getCenterY() + 50, target, 60, ImagePath.BOSS_ATTACK_FIREBALL, Global.UNIT_MIN * 13, Global.UNIT_MIN * 13);
        this.bossLeftHand.setXY(-10000, -10000);
        this.bossRightHand.setXY(-10000, -10000);
        this.bossFire.setXY(-10000, -10000);
        this.moveDistance = 0;
        bossRenderer();
        this.setHpPoint(100);
        delayDetail();
        setStartAttack(false);
        setStartPaint(false);
        super.paintPriority = 0;
        setType("Boss");
    }

    private void bossRenderer() {
        this.renderer = new Renderer();
        this.renderer.setImage(ImagePath.BOSS);
        this.rendererOfRightHand = new Renderer();
        this.rendererOfRightHand.setImage(ImagePath.BOSS_RIGHT_HAND);
        this.rendererOfLeftHand = new Renderer();
        this.rendererOfLeftHand.setImage(ImagePath.BOSS_LEFT_HAND);
        this.rendererOfBossEnd = new Renderer();
        this.rendererOfBossEnd.setImage(ImagePath.BOSS_END_BOMB);
    }

    private void delayDetail() {
        this.boosHeadDelay = new Delay(8);
        this.boosHeadDelay.start();
        this.moveCount = 0;
        this.change = -1;
        //觸發下一個攻擊間隔
        this.nextTrig = new Delay(100);//下一次攻擊的觸發
        this.nextTrig.stop();
        this.stateMode = 0;
        this.bossEndDelay = new Delay(8);
        this.bossEndDelay.stop();
        this.bossEndCount = 0;
    }

    public void setAllObject(ArrayList<GameObject> list) {
        this.bossHead.setAllObject(list);
        this.bossLeftHand.setAllObject(list);
        this.bossRightHand.setAllObject(list);
        this.bossFire.setAllObject(list);
    }

    public void bossHeadUpdate() {
        if (this.boosHeadDelay.isTrig()) {
            if (this.moveCount++ % 5 == 0) {
                this.change *= -1;
            }
            this.moveDistance += this.change;
            this.bossHead.offset(0, this.change);
        }
        this.bossHead.update();
    }

    public void boosLeftHandUpdate() {
        if (this.nextTrig.isTrig() && this.bossLeftHand.getX() != -10000) {
            this.bossLeftHand.setIsMove(true);
        }
        this.bossLeftHand.update();
    }

    public void boosRightHaneUpdate() {
        if (this.nextTrig.isTrig() && this.bossRightHand.getX() != -10000) {
            this.bossRightHand.setIsMove(true);
        }
        this.bossRightHand.update();
    }

    public void bossFireUpdate() {
        if (this.nextTrig.isTrig() && this.bossFire.getX() != -10000) {
            this.bossHead.getRenderer().setImage(ImagePath.BOSS_HEAD);
            this.bossFire.setIsMove(true);
        }
        this.bossFire.update();
    }

    public void setStartAttack(boolean startAttack) {
        this.startAttack = startAttack;
    }

    public void setStartPaint(boolean startPaint) {
        this.startPaint = startPaint;
    }

    public boolean getStartAttack() {
        return this.startAttack;
    }

    @Override
    public void update() {
        if (this.getHp() <= 0) {
            this.bossFire.setXY(-10000, -10000);
            this.bossHead.setXY(-10000, -10000);
            this.bossLeftHand.setXY(-10000, -10000);
            this.bossRightHand.setXY(-10000, -10000);
            this.setStartAttack(false);
            this.setStartPaint(false);
            this.bossEndDelay.start();
            if (this.bossEndDelay.isTrig()) {
                if (this.bossEndCount++ > 12) {
                    this.setXY(-10000, -10000);
                }
            }
            return;
        }
        bossHeadUpdate();
        if (this.startAttack) {
            this.nextTrig.start();
            if (this.nextTrig.isTrig()) {
                switch (this.stateMode++ % 3) {
                    case 0:
                        if (this.bossRightHand.getX() == -10000) {
                            this.bossRightHand.setNewStart();
                        }
                        break;
                    case 1:
                        if (this.bossLeftHand.getX() == -10000) {
                            this.bossLeftHand.setNewStart();
                        }
                        break;
                    case 2:
                        if (this.bossFire.getX() == -10000) {
                            this.bossFire.setNewStart();
                            this.bossHead.getRenderer().setImage(ImagePath.BOSS_HEAD_FIRE);
                        }
                        break;
                }
            }
            boosRightHaneUpdate();
            boosLeftHandUpdate();
            bossFireUpdate();
        }
    }

    @Override
    public void setDir(int dir) {
    }

    @Override
    public void paintComponent(Graphics g) {
        if (this.startPaint) {
            bossHandAttackPaint(g);
            bossHandPaint(g);
            bossPaint(g);
            bossHeadAttackPaint(g);
            bossFireAttackPaint(g);
            bossRedPoint(g);
        }
        if (this.getHp() <= 0 && this.bossEndCount < 12) {
            this.rendererOfBossEnd.paint(g,
                    (int) this.getX(),
                    (int) this.getY(),
                    (int) (this.getX() + this.width()),
                    (int) (this.getY() + this.height()),
                    (this.bossEndCount % 3) * 375,
                    (this.bossEndCount / 3) * 229,
                    (this.bossEndCount % 3) * 375 + 375,
                    (this.bossEndCount / 3) * 229 + 229);
        }

    }

    private void bossHandAttackPaint(Graphics g) {
        if (this.bossRightHand.getAllObject() != null) {
            this.bossRightHand.paint(g);
        }
        if (this.bossLeftHand.getAllObject() != null) {
            this.bossLeftHand.paint(g);
        }
    }

    private void bossHandPaint(Graphics g) {
        if (this.bossRightHand.getX() == -10000) {
            this.rendererOfRightHand.paint(g, (int) this.getCenterX() + 130, (int) this.getCenterY() + 90 + this.moveDistance, (int) this.getCenterX() + 130 + 156, (int) this.getCenterY() + 90 + 156 + this.moveDistance);
        }
        if (this.bossLeftHand.getX() == -10000) {
            this.rendererOfLeftHand.paint(g, (int) this.getCenterX() - 290, (int) this.getCenterY() + 92 + this.moveDistance, (int) this.getCenterX() - 290 + 156, (int) this.getCenterY() + 92 + 156 + this.moveDistance);
        }
    }

    private void bossPaint(Graphics g) {
        this.renderer.paint(g, (int) this.getX(), (int) this.getY(), (int) (this.getX() + this.width()), (int) (this.getY() + this.height()));
    }

    private void bossHeadAttackPaint(Graphics g) {
        if (this.bossHead.getAllObject() != null) {
            this.bossHead.paint(g);
        }
    }

    private void bossFireAttackPaint(Graphics g) {
        if (this.bossFire.getAllObject() != null) {
            this.bossFire.paint(g);
        }
    }

    private void bossRedPoint(Graphics g) {
        g.fillRect((int) (this.getX() - Global.viewX), (int) (this.getY() - Global.viewY) - 8, (int) this.getGraph().width(), 10);
        g.setColor(Color.RED);
        g.fillRect((int) (this.getX() - Global.viewX), (int) (this.getY() - Global.viewY) - 8, (int) this.getHpBarWidth(), 10);
        g.setColor(Color.BLACK);
    }

}
