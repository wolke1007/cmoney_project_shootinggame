/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameobj;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import renderer.RendererToRotate;
import util.Angle;
import util.AverageSpeed;
import util.Delay;
import util.Global;
import util.MoveMode;
import util.VectorCollision;
import util.ZombieNormal;

/**
 *
 * @author F-NB
 */
public class Enemy extends GameObject {

    private MoveMode moveMode;

    public Enemy(String colliderType, float x, float y, float hp, GameObject target, int moveSpeed, String[] path) {
        super(colliderType, x, y, Global.UNIT_X, Global.UNIT_Y, Global.UNIT_X, Global.UNIT_Y);
        this.moveMode = new ZombieNormal(this, target, moveSpeed, path);
        setHpPoint(hp);
        this.setType("Enemy");
        super.paintPriority = 1;
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
        this.renderer.paint(g);
        g.fillRect((int) (this.getX() - Global.viewX), (int) (this.getY() - Global.viewY) - 8, (int) this.graph.width(), 4);
        g.setColor(Color.RED);
        g.fillRect((int) (this.getX() - Global.viewX), (int) (this.getY() - Global.viewY) - 8, (int) this.getHpBarWidth(), 4);
        g.setColor(Color.BLACK);
    }

    @Override
    public void setDir(int dir) {
    }//方向用不到

}
