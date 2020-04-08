/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import gameobj.Actor;
import gameobj.Ammo;
import gameobj.Enemy;
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
public class JabStep {//試探碰撞

    private Graph self;
    private float dx;
    private float dy;
    private LinkedList<GameObject> allObject;
    private float divisor;//切割的等分

    public JabStep(Graph self, float dx, float dy, LinkedList<GameObject> allObject) {
        setSelf(self);
        setDXY(dx, dy);
        setAllObject(allObject);
        this.divisor = 100;
    }

    public void setSelf(Graph self) {
        if (this.self == null) {
            if (self instanceof Circle) {
                Circle tmp = (Circle) self;
                this.self = new Circle(tmp.left(), tmp.top(), tmp.right(), tmp.bottom(), tmp.r());
                return;
            }
            this.self = new Rect(self.left(), self.top(), self.right(), self.bottom());
            return;
        }
        this.self.setLeft(self.left());
        this.self.setTop(self.top());
        this.self.setRight(self.right());
        this.self.setBottom(self.bottom());
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

    public void setAllObject(LinkedList<GameObject> allObject) {
        this.allObject = allObject;
    }

    public void newSet(Graph self, float dx, float dy, LinkedList<GameObject> allObject) {
        setSelf(self);
        setDXY(dx, dy);
        setAllObject(allObject);
    }

    public float getDX() {
        float tmp = this.dx / this.divisor;
        float go = 0;
        Graph other;
        for (float i = 0; i < this.divisor; i++) {
            this.self.offset(tmp, 0);
            for (int k = 0; k < this.allObject.size(); k++) {
                if (this.allObject.get(k) instanceof Map
                        || this.allObject.get(k) instanceof Ammo
                        || this.allObject.get(k) instanceof Actor || this.allObject.get(k) instanceof Enemy) {
                    continue;
                }
                other = this.allObject.get(k).getCollider();
                if (!(this.allObject.get(k) instanceof Maps)
                        && self.intersects(other)) {
                    return go;
                } else if (this.allObject.get(k) instanceof Maps
                        && self.innerCollisionToCollision(other)) {
                    return go;
                }
            }
            go += tmp;
        }
        return go;
    }

    public float getDY() {
        float tmp = this.dy / this.divisor;
        float go = 0;
        Graph other;
        for (float i = 0; i < this.divisor; i++) {
            this.self.offset(0, tmp);
            for (int k = 0; k < this.allObject.size(); k++) {
                if (this.allObject.get(k) instanceof Map
                        || this.allObject.get(k) instanceof Ammo
                        || this.allObject.get(k) instanceof Actor|| this.allObject.get(k) instanceof Enemy) {
                    continue;
                }
                other = this.allObject.get(k).getCollider();
                if (!(this.allObject.get(k) instanceof Maps)
                        && self.intersects(other)) {
                    return go;
                } else if (this.allObject.get(k) instanceof Maps
                        && self.innerCollisionToCollision(other)) {
                    return go;
                }
            }
            go += tmp;
        }
        return go;
    }
}
