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
import util.Global;

/**
 *
 * @author F-NB
 */
public class Enemy extends GameObject {

    private MoveMode moveMode;
    private GameObject target;

    public Enemy(String colliderType, float x, float y, float hp, GameObject target, int kind) {
        super(colliderType, x, y, Global.UNIT_MIN * 8, Global.UNIT_MIN * 8, Global.UNIT_MIN * 4, Global.UNIT_MIN * 4);
        this.getCollider().setCenter(this.getCollider().width() / 2, this.getCollider().height() / 2);
        setHpPoint(hp);
        this.setType("Enemy");
        setTarget(target);
        selectKind(kind);
        super.paintPriority = 1;
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
                this.moveMode = new ZombieNormal(this, getTarget(), 59, ImagePath.ZOMBIE_NORMAL);
                break;
            case 2:
                this.moveMode = new ZombieShock(this, getTarget(), 60, ImagePath.ZOMBIE_SHOCK);
                break;
        }
    }

    public void setAllObject(ArrayList<GameObject> list) {
        this.moveMode.setAllObject(list);
    }

    @Override
    public void update() {
        this.moveMode.update();
    }

    @Override
    public void paintComponent(Graphics g) {
        this.moveMode.paintComponent(g);
        g.fillRect((int) (this.getX() - Global.viewX), (int) (this.getY() - Global.viewY) - 8, (int) this.getGraph().width(), 4);
        g.setColor(Color.RED);
        g.fillRect((int) (this.getX() - Global.viewX), (int) (this.getY() - Global.viewY) - 8, (int) this.getHpBarWidth(), 4);
        g.setColor(Color.BLACK);
    }

    @Override
    public void setDir(int dir) {
    }//方向用不到

}
