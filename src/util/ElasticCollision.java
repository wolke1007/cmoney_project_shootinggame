/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import gameobj.GameObject;
import gameobj.Map;
import gameobj.Maps;
import graph.Circle;
import graph.Graph;
import graph.Rect;
import java.util.LinkedList;

/**
 *
 * @author F-NB
 */
public class ElasticCollision {

    private GameObject self;//想要移動的本體
    private Graph selfCollider;//想要移動的碰撞機
    private float dx;//給予 x 的移動向量
    private float dy;//給予 y 的移動向量
    private LinkedList<GameObject> allObjects;
    private float offsetDX;
    private float offsetDY;

    private float divisor;//細分預判的等份數 //暫時不一定用到

    public ElasticCollision(GameObject self, float dx, float dy, LinkedList<GameObject> allObjects) {
        setSelfCollider(self);
        setDXY(dx, dy);
        setAllObjects(allObjects);
        this.setDivisor(100);
    }

    public void setSelfCollider(GameObject self) {
        Graph collider = self.getCollider();
        if (this.self == null && this.selfCollider == null) {
            this.self = self;
            if (collider instanceof Circle) {
                Circle tmp = (Circle) collider;
                this.selfCollider = new Circle(tmp.left(), tmp.top(), tmp.right(), tmp.bottom(), tmp.r());
                return;
            }
            this.selfCollider = new Rect(collider.left(), collider.top(), collider.right(), collider.bottom());
            return;
        }
        this.selfCollider.setLeft(collider.left());
        this.selfCollider.setTop(collider.top());
        this.selfCollider.setRight(collider.right());
        this.selfCollider.setBottom(collider.bottom());
    }

    public void setDX(float dx) {
        this.dx = dx;
    }

    public void setDY(float dy) {
        this.dy = dy;
    }

    public void setDXY(float dx, float dy) {
        setDX(dx);
        setDY(dy);
    }

    public void setAllObjects(LinkedList<GameObject> allObjects) {
        this.allObjects = allObjects;
    }

    public void setDivisor(float divisor) {
        this.divisor = divisor;
    }

    public void newSet(float dx, float dy, LinkedList<GameObject> allObjects) {
        setSelfCollider(this.self);
        setDXY(dx, dy);
        setAllObjects(allObjects);
    }

    public float offsetDX() {
        if (this.offsetDY() == 0) {
            return this.offsetDX;
        }
        float tmp = this.dx / this.divisor;//等分的距離
        float go = 0;
        GameObject another;
        for (int i = 0; i < this.divisor; i++) {
            this.selfCollider.offset(tmp, 0);
            for (int k = 0; k < this.allObjects.size(); k++) {
                another = this.allObjects.get(k);
                if (another instanceof Map || another == this.self) {//跳過小地圖 & 跳過自己
                    continue;
                } else if (!(another instanceof Maps)
                        && this.selfCollider.intersects(another.getCollider())) {
                    if (go == 0) {
                        vectorY(another, tmp);
                    }
                    break;
                } else if (another instanceof Maps
                        && this.selfCollider.innerCollisionToCollision(another.getCollider())) {
                    break;
                }
            }
            go += tmp;
        }
        return go;
    }

    private void vectorY(GameObject another, float tmp) {//計算向量 Y 的移動
        Angle angle = new Angle(this.self.getCenterX(), this.self.getCenterY(), another.getCenterX(), another.getCenterY());
        this.offsetDY = (float) -Math.sin(angle.getNormalAngle());
    }

    public float offsetDY() {
        if (this.offsetDX() == 0) {
            return this.offsetDY;
        }
        float tmp = this.dy / this.divisor;//等分的距離
        float go = 0;
        GameObject another;
        for (int i = 0; i < this.divisor; i++) {
            this.selfCollider.offset(0, tmp);
            for (int k = 0; k < this.allObjects.size(); k++) {
                another = this.allObjects.get(k);
                if (another instanceof Map || another == this.self) {//跳過小地圖 & 跳過自己
                    continue;
                } else if (!(another instanceof Maps)
                        && this.selfCollider.intersects(another.getCollider())) {
                    if (go == 0) {
                        vectorX(another, tmp);
                    }
                    break;
                } else if (another instanceof Maps
                        && this.selfCollider.innerCollisionToCollision(another.getCollider())) {
                    break;
                }
            }
            go += tmp;
        }
        return go;
    }

    private void vectorX(GameObject another, float tmp) {
        Angle angle = new Angle(this.self.getCenterX(), this.self.getCenterY(), another.getCenterX(), another.getCenterY());
        this.offsetDX = (float) Math.cos(angle.getNormalAngle());
    }
}
