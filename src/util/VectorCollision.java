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
import java.util.ArrayList;

/**
 *
 * @author F-NB
 */
public class VectorCollision {

    private GameObject self;//想要移動的本體
    private float dx;//給予 x 的移動向量
    private float dy;//給予 y 的移動向量
    private ArrayList<GameObject> allObjects;
    private int listSize;

    //被取移動距離
    private float multiple;

    private float divisor;//細分預判的等份數 //暫時不一定用到

    private int isHurt;
    private boolean isCollision;

    public VectorCollision(GameObject self, float dx, float dy) {
        setSelf(self);
        setDXY(dx, dy);
        setDivisor(20);
        setMultiple(2f);
        setIsHurt(0);
        setAllObjects(null);
        setIsCollision(false);
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

    public void setAllObjects(ArrayList<GameObject> allObjects) {
        this.allObjects = allObjects;
    }

    public void setDivisor(float divisor) {
        this.divisor = divisor;
    }

    public void setMultiple(float multiple) {
        this.multiple = multiple;
    }

    public void setIsHurt(int isHurt) {
        this.isHurt = isHurt;
    }

    public int getIsHurt() {
        return this.isHurt;
    }

    public void setIsCollision(boolean isCollision) {
        this.isCollision = isCollision;
    }

    public boolean getIsCollision() {
        return this.isCollision;
    }

    public void newOffset(float dx, float dy) {
        setDXY(dx, dy);
        offsetDX();
        offsetDY();
    }

    private void offsetDX() {
        float tmp = this.dx / this.divisor;//等分的距離
        GameObject another;
        for (int i = 0; i < this.divisor; i++) {
            for (int k = 0; k < this.allObjects.size(); k++) {
                another = this.allObjects.get(k);
                boolean escape = false;
                for (int z = 0; z < Global.EXCLUDE.length; z++) {//排除型別的判斷 //目前 不和"小地圖"判斷 
                    if (another.getType().equals(Global.EXCLUDE[z])) {
                        escape = true;
                    }
                }
                if (escape) {
                    continue;
                }
                if (another.getType().equals(this.self.getType())) {//跳過自己 不判斷
                    continue;
                }
                for (int z = 0; z < Global.INNER.length; z++) {//判斷為在圖形內的 // 目前 Maps 判斷
                    if (another.getType().equals(Global.INNER[z])
                            && this.self.getCollider().innerCollisionToCollision(another.getCollider())) {
                        this.self.offset(this.self.getCollider().getDx(), this.self.getCollider().getDy());
                        setIsCollision(true);
                        return;
                    }
                }
                for (int z = 0; z < Global.INNER.length; z++) {
                    if (!(another.getType().equals(Global.INNER[z]))) {
                        if (Math.sqrt(Math.pow(this.allObjects.get(i).getCenterX() - this.self.getCenterX(), 2) + Math.pow(this.allObjects.get(i).getCenterY() - this.self.getCenterY(), 2)) > 300) {
                            continue;
                        }
                    }
                }
                for (int z = 0; z < Global.INNER.length; z++) {//判斷圖形為各自獨立的個體 // 除了以上的都需要判斷
                    if (!(another.getType().equals(Global.INNER[z]))
                            && this.self.getCollider().intersects(another.getCollider())) {
                        this.self.offset(this.self.getCollider().getDx() * this.multiple, this.self.getCollider().getDy() * this.multiple);
                        setIsCollision(true);
                        for (int j = 0; j < getIsHurt(); j++) {
                            another.subtractHp();
                        }
                        return;
                    }
                }
            }
            this.self.offset(tmp, 0);
        }
        setIsCollision(false);
        return;
    }

    private void offsetDY() {
        float tmp = this.dy / this.divisor;//等分的距離
        GameObject another;
        for (int i = 0; i < this.divisor; i++) {
            for (int k = 0; k < this.allObjects.size(); k++) {
                another = this.allObjects.get(k);
                boolean escape = false;
                for (int z = 0; z < Global.EXCLUDE.length; z++) {//排除型別的判斷 //目前 不和"小地圖"判斷 
                    if (another.getType().equals(Global.EXCLUDE[z])) {
                        escape = true;
                    }
                }
                if (escape) {
                    continue;
                }
                if (another.getType().equals(this.self.getType())) {//跳過自己 不判斷
                    continue;
                }
                for (int z = 0; z < Global.INNER.length; z++) {//判斷為在圖形內的 // 目前 Maps 判斷
                    if (another.getType().equals(Global.INNER[z])
                            && this.self.getCollider().innerCollisionToCollision(another.getCollider())) {
                        this.self.offset(this.self.getCollider().getDx(), this.self.getCollider().getDy());
                        setIsCollision(true);
                        return;
                    }
                }
                for (int z = 0; z < Global.INNER.length; z++) {
                    if (!(another.getType().equals(Global.INNER[z]))) {
                        if (Math.sqrt(Math.pow(this.allObjects.get(i).getCenterX() - this.self.getCenterX(), 2) + Math.pow(this.allObjects.get(i).getCenterY() - this.self.getCenterY(), 2)) > 300) {
                            continue;
                        }
                    }
                }
                for (int z = 0; z < Global.INNER.length; z++) {//判斷圖形為各自獨立的個體 // 除了以上的都需要判斷
                    if (!(another.getType().equals(Global.INNER[z]))
                            && this.self.getCollider().intersects(another.getCollider())) {
                        this.self.offset(this.self.getCollider().getDx() * this.multiple, this.self.getCollider().getDy() * this.multiple);
                        setIsCollision(true);
                        for (int j = 0; j < getIsHurt(); j++) {
                            another.subtractHp();
                        }
                        return;
                    }
                }
            }
            this.self.offset(0, tmp);
        }
        setIsCollision(false);
        return;
    }
}
///////////////////////////////////////////////
//offsetDx()向量解
//                        caculateAngle(another);
//                        double positiveVector = this.dx * Math.cosh(this.angle.getNormalAngle() * Global.PI / 180);//之後測試 dx => tmp
//                        System.out.println(this.angle.getNormalAngle());
//                        System.out.println(this.angle.getNormalAngle() * Global.PI / 180);
//                        System.out.println(Math.cosh(this.angle.getNormalAngle() * Global.PI / 180));
//                        System.out.println(positiveVector);
//                        double powR = this.dx * this.dx;
//                        double inverseVector = Math.sqrt(Math.abs(powR - positiveVector * positiveVector));
//                        this.offsetDY = ((Math.sin(this.angle.getNormalAngle() * Global.PI / 180) > 0) ? 1 : -1)
//                                * Math.sqrt(Math.abs(inverseVector * inverseVector - powR));
//                        this.offsetDX = this.dx;
//                        this.self.offset((float) this.offsetDX, (float) this.offsetDY);
//offsetDy()向量解
//                        caculateAngle(another);
//                        double positiveVector = this.dy * Math.sinh(this.angle.getNormalAngle() * Global.PI / 180);
//                        double powR = this.dy * this.dy;
//                        double inverseVector = Math.sqrt(Math.abs(powR - positiveVector * positiveVector));
//                        this.offsetDX = ((Math.cos(this.angle.getNormalAngle() * Global.PI / 180) > 0) ? 1 : -1)
//                                * Math.sqrt(Math.abs(inverseVector * inverseVector - powR));
//                        this.offsetDY = this.dy;
//                        this.self.offset((float) this.offsetDX, (float) this.offsetDY);
