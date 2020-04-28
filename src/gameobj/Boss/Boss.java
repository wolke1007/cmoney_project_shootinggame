/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameobj.Boss;

import controllers.AudioPath;
import controllers.AudioResourceController;
import controllers.ImagePath;
import gameobj.Barrier;
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

    //控制的資訊欄
    private boolean startAttack;//開始攻擊
    private boolean startPaint;//開始畫
    private boolean isDead;//是否死亡
    private boolean callEnemy;//召喚小怪
    //目標
    private GameObject target;
    //boss
    private int countStartAttack;
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
    //阻擋的障礙物
    private Barrier darkBarrier;
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
        this.target = target;
        bossRendererToRotate(this.target);
        bossRenderer();
        this.setHpPoint(500);
        delayDetail();
        setStartAttack(false);
        setStartPaint(false);
        setIsDead(false);
        setCallEnemy(false);
        super.paintPriority = 0;
        setType("Boss");
        this.darkBarrier = new Barrier("rect", this.getX() - 40, this.getY() - 20, 752, 460, ImagePath.BOSS_DARK_BARRIER, 0);
        this.countStartAttack = 0;
    }

    public Barrier getDarkBarrier() {
        return this.darkBarrier;
    }

    private void bossRendererToRotate(GameObject target) {
        this.bossHead = new BossAttack("circle", this.getCenterX() - 226, this.getCenterY() - 82, target, 60, ImagePath.BOSS_HEAD, 452, 259);
        this.bossLeftHand = new BossAttack("circle", this.getCenterX() - 290, this.getCenterY() + 113, target, 60, ImagePath.BOSS_ATTACK_LEFTHAND, Global.UNIT_MIN * 13, Global.UNIT_MIN * 13);
        this.bossRightHand = new BossAttack("circle", this.getCenterX() + 130, this.getCenterY() + 115, target, 60, ImagePath.BOSS_ATTACK_RIGHTHAND, Global.UNIT_MIN * 13, Global.UNIT_MIN * 13);
        this.bossFire = new BossAttack("circle", this.getCenterX() - 78, this.getCenterY() + 50, target, 60, ImagePath.BOSS_ATTACK_FIREBALL, Global.UNIT_MIN * 13, Global.UNIT_MIN * 13);
        this.bossLeftHand.setXY(-10000, -10000);
        this.bossRightHand.setXY(-10000, -10000);
        this.bossFire.setXY(-10000, -10000);
        this.moveDistance = 0;
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
        this.nextTrig = new Delay(200);//下一次攻擊的觸發
        this.nextTrig.stop();
        this.stateMode = 0;
        //boss 死亡後的效果控制
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
        if (this.bossLeftHand.getIsMove() && this.bossLeftHand.getEffectCount() == 0) {
            AudioResourceController.getInstance().play(AudioPath.BOSS_HAND_CONTINUE);
        }
        this.bossLeftHand.update();
    }

    public void boosRightHaneUpdate() {
        if (this.nextTrig.isTrig() && this.bossRightHand.getX() != -10000) {
            this.bossRightHand.setIsMove(true);
        }
        if (this.bossRightHand.getIsMove() && this.bossRightHand.getEffectCount() == 0) {
            AudioResourceController.getInstance().play(AudioPath.BOSS_HAND_CONTINUE);
        }
        this.bossRightHand.update();
    }

    public void bossFireUpdate() {
        if (this.nextTrig.isTrig() && this.bossFire.getX() != -10000) {
            this.bossHead.getRenderer().setImage(ImagePath.BOSS_HEAD);
            this.bossFire.setIsMove(true);
        }
        if (this.bossFire.getIsMove() && this.bossFire.getEffectCount() == 0) {
            AudioResourceController.getInstance().play(AudioPath.BOSS_FIRE_CONTINUE);
        }
        this.bossFire.update();
    }

    public void setStartAttack(boolean startAttack) {
        this.startAttack = startAttack;
    }

    public boolean getStartAttack() {
        return this.startAttack;
    }

    public void setStartPaint(boolean startPaint) {
        this.startPaint = startPaint;
    }

    public boolean getStartPaint() {
        return this.startPaint;
    }

    public void setIsDead(boolean isDead) {
        this.isDead = isDead;
    }

    public boolean getIsDead() {
        return this.isDead;
    }

    public void setCallEnemy(boolean callEnemy) {
        this.callEnemy = callEnemy;
    }

    public boolean getCallEnemy() {
        return this.callEnemy;
    }

    private void bossEnd() {
        this.bossFire.setXY(-10000, -10000);
        this.bossHead.setXY(-10000, -10000);
        this.bossLeftHand.setXY(-10000, -10000);
        this.bossRightHand.setXY(-10000, -10000);
        this.setStartAttack(false);
        this.setStartPaint(false);
        this.setIsDead(true);
        this.bossEndDelay.start();
        if (this.bossEndDelay.isTrig()) {
            if (this.bossEndCount == 0) {
                AudioResourceController.getInstance().play(AudioPath.BOSS_DIE_SOUND);
            }
            if (this.bossEndCount++ > 12) {
                this.setXY(-10000, -10000);
            }
        }
    }

    private void bossAttackSwitch() {
        this.nextTrig.start();
        if (this.nextTrig.isTrig()) {
            switch (this.stateMode++ % 4) {
                case 0:
                    this.setCallEnemy(false);
                    if (this.bossRightHand.getX() == -10000) {
                        this.bossRightHand.setNewStart();
                        AudioResourceController.getInstance().play(AudioPath.BOSS_HAND_MOVE);
                    }
                    break;
                case 1:
                    if (this.bossLeftHand.getX() == -10000) {
                        this.bossLeftHand.setNewStart();
                        AudioResourceController.getInstance().play(AudioPath.BOSS_HAND_MOVE);
                    }
                    break;
                case 2:
                    if (this.bossFire.getX() == -10000) {
                        this.bossFire.setNewStart();
                        this.bossHead.getRenderer().setImage(ImagePath.BOSS_HEAD_FIRE);
                        if (this.nextTrig.getDelayFrame() == 200) {
                            AudioResourceController.getInstance().play(AudioPath.BOSS_FIRE_READY[0]);
                        } else if (this.nextTrig.getDelayFrame() == 100) {
                            AudioResourceController.getInstance().play(AudioPath.BOSS_FIRE_READY[1]);
                        } else if (this.nextTrig.getDelayFrame() == 30) {
                            AudioResourceController.getInstance().play(AudioPath.BOSS_FIRE_READY[2]);
                        }
                    }
                    break;
                case 3:
                    AudioResourceController.getInstance().play(AudioPath.BOSS_CALL_ENEMY);
                    this.setCallEnemy(true);
                    break;
            }
        }
    }

    @Override
    public void update() {
        if (this.getHp() <= 0) {
            bossEnd();
            return;
        }
        if (this.target.getHp() <= 0) {
            this.setStartAttack(false);
            this.bossFire.setXY(-10000, -10000);
            this.bossHead.setXY(-10000, -10000);
            this.bossLeftHand.setXY(-10000, -10000);
            this.bossRightHand.setXY(-10000, -10000);
        }
        if (this.getHpPercent() < 30) {
            this.nextTrig.setDelayFrame(30);
            this.bossRightHand.setMoveMultiple(6.5f);
            this.bossLeftHand.setMoveMultiple(6.5f);
            this.bossFire.setMoveMultiple(6.5f);
        } else if (this.getHpPercent() < 60) {
            this.nextTrig.setDelayFrame(100);
            this.bossRightHand.setMoveMultiple(6f);
            this.bossLeftHand.setMoveMultiple(6f);
            this.bossFire.setMoveMultiple(6f);
        }
        bossHeadUpdate();
        if (this.startAttack && this.countStartAttack++ > 150) {
            bossAttackSwitch();
            boosRightHaneUpdate();
            boosLeftHandUpdate();
            bossFireUpdate();
        } else if (!this.startAttack && this.startPaint) {
            this.setHpPoint(this.getHp());
        }
    }

    @Override
    public void setDir(int dir) {
    }

    @Override
    public void paintComponent(Graphics g) {
        if (this.startPaint) {
            this.darkBarrier.paint(g);
//            g.setColor(Color.BLACK);
//            g.fillOval((int) (this.getGraph().left() - Global.viewX - 40), (int) (this.getGraph().top() - Global.viewY - 20), (int) this.getGraph().width() + 80, (int) this.getGraph().height() + 150);
            bossHandAttackPaint(g);
            bossHandPaint(g);
            bossPaint(g);
            bossHeadAttackPaint(g);
            bossFireAttackPaint(g);
            bossRedPoint(g);
        }
        if (this.getHp() <= 0 && this.bossEndCount < 12) {
            bossEndBombPaint(g);
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
        g.setColor(Color.YELLOW);
        g.fillRect((int) (this.getX() - Global.viewX + 197), (int) (this.getY() - Global.viewY) - 8, 4, 10);
        g.fillRect((int) (this.getX() - Global.viewX + 400), (int) (this.getY() - Global.viewY) - 8, 4, 10);
        g.setColor(Color.BLACK);
    }

    private void bossEndBombPaint(Graphics g) {
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
