/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameobj.Boss;

import controllers.ImagePath;
import gameobj.GameObject;
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

    private Renderer renderer;

    //頭
    private BossAttack bossHead;
    private Delay boosHeadDelay;
    private int moveCount;
    private int change;
    //左手
    private BossAttack bossLeftHand;
    //右手
    private BossAttack bossRightHand;
    //口 火球
    private BossAttack bossMouthFire;
    //處發間隔控制
    private Delay nextTrig;
    private int stateMode;

    public Boss(String colliderType, float x, float y, GameObject target, int moveSpeed) {
        super(colliderType, x, y, Global.UNIT_MIN * 56, Global.UNIT_MIN * 25 + 6, Global.UNIT_MIN * 56, Global.UNIT_MIN * 25 + 6);
        this.bossHead = new BossAttack("circle", this.getCenterX() - 226, this.getCenterY() - 82, target, 60, ImagePath.BOSS_HEAD, 452, 259);
        this.bossLeftHand = new BossAttack("circle", this.getCenterX() - 290, this.getCenterY() + 113, target, 60, ImagePath.BOSS_ATTACK_LEFTHAND, Global.UNIT_MIN * 13, Global.UNIT_MIN * 13);
        this.bossRightHand = new BossAttack("circle", this.getCenterX() + 130, this.getCenterY() + 115, target, 60, ImagePath.BOSS_ATTACK_RIGHTHAND, Global.UNIT_MIN * 13, Global.UNIT_MIN * 13);
        this.bossMouthFire = new BossAttack("circle", this.getCenterX(), this.getCenterY(), target, 60, ImagePath.BOSS_ATTACK_MOUTH, Global.UNIT_MIN * 13, Global.UNIT_MIN * 13);
        this.bossLeftHand.setXY(-10000, -10000);
        this.bossRightHand.setXY(-10000, 10000);
        this.renderer = new Renderer();
        this.renderer.setImage(ImagePath.BOSS);
        delayDetail();
        super.paintPriority = 0;
        setType("Boss");
    }

    private void delayDetail() {
        this.boosHeadDelay = new Delay(8);
        this.boosHeadDelay.start();
        this.moveCount = 0;
        this.change = -1;
        //觸發下一個攻擊間隔
        this.nextTrig = new Delay(200);//下一次攻擊的觸發
        this.nextTrig.start();
        this.stateMode = 0;
    }

    public void setAllObject(ArrayList<GameObject> list) {
        this.bossHead.setAllObject(list);
        this.bossLeftHand.setAllObject(list);
        this.bossRightHand.setAllObject(list);
        this.bossMouthFire.setAllObject(list);
    }

    public void bossHeadUpdate() {
        if (this.boosHeadDelay.isTrig()) {
            if (this.moveCount++ % 5 == 0) {
                this.change *= -1;
            }
            this.bossHead.offset(0, this.change);
        }
        this.bossHead.update();
    }

    public void boosLeftHandUpdate() {
        this.bossLeftHand.update();
    }

    public void boosRightHaneUpdate() {
        this.bossRightHand.update();
    }

    @Override
    public void update() {
        bossHeadUpdate();
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
                    break;
            }
        }
        if (this.nextTrig.isTrig()) {
            System.out.println("true");
        }
        if (this.nextTrig.isTrig() && this.bossRightHand.getX() != -10000) {
            this.bossRightHand.setIsMove(true);
        }
        if (this.nextTrig.isTrig() && this.bossLeftHand.getX() != -10000) {
            this.bossLeftHand.setIsMove(true);
        }
        boosLeftHandUpdate();
        boosRightHaneUpdate();
    }

    @Override
    public void setDir(int dir) {
    }

    @Override
    public void paintComponent(Graphics g) {
        if (this.bossRightHand.getAllObject() != null && this.bossRightHand.getEffectCount() < 63) {
            this.bossRightHand.paint(g);
        }
        if (this.bossLeftHand.getAllObject() != null) {
            this.bossLeftHand.paint(g);
        }
        this.renderer.paint(g, (int) this.getX(), (int) this.getY(), (int) (this.getX() + this.width()), (int) (this.getY() + this.height()));
        if (this.bossHead.getAllObject() != null) {
            this.bossHead.paint(g);
        }
    }

}
