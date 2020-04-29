/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameobj.enemy;

import controllers.ImagePath;
import gameobj.GameObject;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import util.Delay;
import util.Global;

/**
 *
 * @author F-NB
 */
public class Enemy extends GameObject {

    private MoveMode moveMode;
    private GameObject target;
    private boolean isHpFull;
    private Delay invincibleState;
    private float hp;
    private boolean isRemove;

    public Enemy(String colliderType, float x, float y, float hp, GameObject target, int kind) {
        super(colliderType, x, y, Global.UNIT_MIN * 8, Global.UNIT_MIN * 8, Global.UNIT_MIN * 4, Global.UNIT_MIN * 4);
        this.getCollider().setCenter(this.getCollider().width() / 2, this.getCollider().height() / 2);
        this.hp = hp;
        setHpPoint(hp);
        this.setType("Enemy");
        setTarget(target);
        selectKind(kind);
        this.isHpFull = true;
        this.invincibleState = new Delay(30);
        this.invincibleState.stop();
        this.invincibleState.cleanCounter();
        this.invincibleState.start();
        this.setIsRemove(false);
        super.paintPriority = 1;
    }

    public void setIsRemove(boolean isRemove) {
        this.isRemove = isRemove;
    }

    public boolean getIsRmove() {
        return this.isRemove;
    }

    public void setTarget(GameObject target) {
        this.target = target;
    }

    public GameObject getTarget() {
        return this.target;
    }

    public void selectKind(int kind) {
        switch (kind) {
            case 1:
                this.moveMode = new ZombieNormal(this, getTarget(), Global.random(59, 60), ImagePath.ZOMBIE_NORMAL);
                break;
            case 2:
                this.moveMode = new ZombieShock(this, getTarget(), 60, ImagePath.ZOMBIE_MONSTER);
                break;
        }
    }

    public void setAllObject(ArrayList<GameObject> list) {
        this.moveMode.setAllObject(list);
    }

    @Override
    public void update() {
        if (this.isHpFull) {
            this.setHpPoint(this.hp);
        }
        if (this.invincibleState.isTrig()) {
            this.isHpFull = false;
        }
        this.moveMode.update();
        this.setType(this.moveMode.getType());
        this.setIsRemove(this.moveMode.getIsRemove());
    }

    @Override
    public void paintComponent(Graphics g) {
        this.moveMode.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect((int) (this.getX() - Global.viewX), (int) (this.getY() - Global.viewY) - 8, (int) this.getGraph().width(), 4);
        g.setColor(Color.RED);
        g.fillRect((int) (this.getX() - Global.viewX), (int) (this.getY() - Global.viewY) - 8, (int) this.getHpBarWidth(), 4);
        g.setColor(Color.BLACK);
    }

    @Override
    public void setDir(int dir) {
    }//方向用不到

}
