/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import gameobj.GameObject;
import gameobj.Map;
import gameobj.Maps;
import graph.Graph;
import java.util.LinkedList;

/**
 *
 * @author F-NB
 */
public class JapStep {//試探碰撞

    private GameObject self;
    private float dx;
    private float dy;
    private LinkedList<GameObject> allObject;
    private float divisor;//切割的等分

    public JapStep(GameObject self, float dx, float dy, LinkedList<GameObject> allObject) {
        setSelf(self);
        setDXY(dx, dy);
        setAllObject(allObject);
        this.divisor = 10;
    }

    public void setSelf(GameObject self) {
        this.self = self;
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

    public void newSet(GameObject self, float dx, float dy, LinkedList<GameObject> allObject) {
        setSelf(self);
        setDXY(dx, dy);
        setAllObject(allObject);
    }

    public float getDX() {
        float tmp = this.dx / this.divisor;
        float go = 0;
        Graph self = this.self.getCollider();
        Graph other;
        for (float i = 0; i < this.divisor; i++) {
            for (int k = 0; k < this.allObject.size(); k++) {
                if (this.allObject.get(k) instanceof Map) {
                    continue;
                }
                other = this.allObject.get(k).getCollider();
                if (!(this.allObject.get(k) instanceof Maps) && self.intersects(other)) {
                    return go;
                } else if (this.allObject.get(k) instanceof Maps && self.innerCollisionToCollision(other)) {
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
        Graph self = this.self.getCollider();
        Graph other;
        for (float i = 0; i < this.divisor; i++) {
            for (int k = 0; k < this.allObject.size(); k++) {
                if (this.allObject.get(k) instanceof Map) {
                    continue;
                }
                other = this.allObject.get(k).getCollider();
                if (!(this.allObject.get(k) instanceof Maps) && self.intersects(other)) {
                    return go;
                } else if (this.allObject.get(k) instanceof Maps && self.innerCollisionToCollision(other)) {
                    return go;
                }
            }
            go += tmp;
        }
        return go;
    }

}
