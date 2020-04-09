/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import gameobj.GameObject;
import graph.Circle;
import graph.Graph;
import graph.Rect;
import java.util.LinkedList;

/**
 *
 * @author F-NB
 */
public class ElasticCollision {

    private Graph self;//想要移動的碰撞體
    private float dx;//給予 x 的移動向量
    private float dy;//給予 y 的移動向量
    private LinkedList<GameObject> allObjects;
    
    private Angle angle;
    private float divisor;//細分預判的等份數 //暫時不一定用到

    public ElasticCollision(Graph self, float dx, float dy, LinkedList<GameObject> allObjects) {
        setSelf(self);
        setDXY(dx, dy);
        setAllObjects(allObjects);
        this.setDivisor(100);
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

    public void setAllObjects(LinkedList<GameObject> allObjects) {
        this.allObjects = allObjects;
    }

    public void setDivisor(float divisor) {
        this.divisor = divisor;
    }

    public void newSet(Graph self, float dx, float dy, LinkedList<GameObject> allObjects) {
        setSelf(self);
        setDXY(dx, dy);
        setAllObjects(allObjects);
    }

    private void offsetDX() {
        float tmp = this.dx / this.divisor;//等分的距離
        float go = 0;
        for(int i = 0; i < this.divisor; i++){
            
        }
    }
}
