/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameobj.enemy.ammo;

import gameobj.GameObject;
import graph.Graph;
import java.awt.Graphics;
import java.util.ArrayList;
import renderer.RendererToRotate;
import util.AverageSpeed;
import util.Global;

/**
 *
 * @author F-NB
 */
public class Bullet extends ShootMode {

    private RendererToRotate renderer;//旋轉圖渲染器
    private ArrayList<GameObject> allObjects;
    private GameObject self;

    private AverageSpeed averageSpeed;
    private int count;//初始位置的狀態設定

    public Bullet(GameObject self, GameObject start, float moveSpeed, String[] path) {
        super(start, moveSpeed);
        setSelf(self);
        this.renderer = new RendererToRotate(path, self, getAngle());
        this.averageSpeed = new AverageSpeed(self.getCenterX(), self.getCenterY(), Global.mapMouseX, Global.mapMouseY, 95, true);//子彈的移動速度
        setCount(0);
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

    public void setNewStart() {
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
        this.allObjects = list;
    }

    @Override
    public boolean update() {
        float dx = this.averageSpeed.offsetDX();
        float dy = this.averageSpeed.offsetDY();
        float reMoveSpeed = this.averageSpeed.getReMoveSpeed();
        if (getCount() == 0) {
            float d = 2f;
            getSelf().offset(dx * reMoveSpeed / d,
                    dy * reMoveSpeed / d);
            setCount(1);
        } else {
            getSelf().offset(dx, dy);
        }
        Graph other;
        for (int i = 0; i < this.allObjects.size(); i++) {
            GameObject obj = this.allObjects.get(i);
            if (obj.getType().equals("Map")
                    || obj.getType().equals("Ammo")
                    || obj.getType().equals("Actor")
                    || obj.getType().equals("Gun")) {
                continue;
            }
            other = this.allObjects.get(i).getCollider();
            if (!(obj.getType().equals("Maps")) && getSelf().getCollider().intersects(other)) {
                if (obj.getType().equals("Enemy") && getSelf().getCollider().intersects(other)) {
                    obj.subtractHp();
                }
                getSelf().setXY(-1000, -1000);
                return false;
            }
            if (obj.getType().equals(" Maps") && getSelf().getCollider().innerCollisionToCollision(other)) {
                getSelf().setXY(-1000, -1000);
                return false;
            }
        }
        return true;
    }

    @Override
    public void paintComponent(Graphics g) {
        this.renderer.paint(g);
    }

}
