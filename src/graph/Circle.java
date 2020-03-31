/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graph;

import util.Global;

/**
 *
 * @author F-NB
 */
public class Circle extends Graph {

    private double r;
    private int left;
    private int top;
    private int right;
    private int bottom;

    public Circle(int left, int top, int right, int bottom, float r) {
        super(left, top, right, bottom);
        setR(r);
    }

    public void setR(float r) {
        this.r = r;
        super.x = left;
        super.y = top;
    }

    public float r() {
        return this.r;
    }

    @Override
    public boolean intersects(Graph a, Graph b) {
        if (b instanceof Rect) {
            return a.intersects(b.left(), b.top(), b.right(), b.bottom());
        }
        Circle tmp = (Circle) b;
        return a.intersects(tmp.centerX(), tmp.centerY(), tmp.r());
    }

    @Override
    public boolean intersects(float x, float y, float r) {
        float dx = Math.abs(super.centerX() - x);
        float dy = Math.abs(super.centerY() - y);
        double distance = Math.sqrt(dx * dx + dy * dy);
        Global.log("!!" + distance);
        if (distance < (this.r + r)) {
            return true;
        }
        return false;
    }

    public boolean intersectsOfCircle(Circle a, Circle b) {
        return a.intersectsOfCircle(b.exactCenterX(), b.exactCenterY(), b.r());
    }//圓形相交

    @Override
    public boolean intersects(Graph target) {
        if (target instanceof Circle) {
            Circle tmp = (Circle) target;
            return intersects(tmp.centerX(), tmp.centerY(), tmp.r());
        }
        return intersects(target.left(), target.top(), target.right(), target.bottom());
    }

    @Override
    public boolean intersects(float left, float top, float right, float bottom) {
        if (super.centerX() > left && super.centerX() < right && super.centerY() > top && super.centerY() < bottom) {
            return true;
        }
        if (super.centerX() < left || super.centerX() > right) {
            float dx;
            if (super.centerX() < left) {
                dx = Math.abs(super.centerX() - left);
            } else {
                dx = Math.abs(super.centerX() - right);
            }
            float tmp;
            if (super.centerY() < top) {
                tmp = top;
            } else if (super.centerY() > bottom) {
                tmp = bottom;
            } else {
                tmp = super.centerY();
            }
            float dy = Math.abs(super.centerY() - tmp);
            double distance = Math.sqrt(dx * dx + dy * dy);
            if (distance < r()) {
                return true;
            }
        } else if (super.centerY() < top || super.centerY() > bottom) {
            float tmp;
            if (super.centerX() < left) {
                tmp = left;
            } else if (super.centerX() > right) {
                tmp = right;
            } else {
                tmp = super.centerX();
            }
            float dy;
            if (super.centerY() < top) {
                dy = Math.abs(super.centerY() - top);
            } else {
                dy = Math.abs(super.centerY() - bottom);
            }
            float dx = Math.abs(super.centerX() - tmp);
            double distance = Math.sqrt(dx * dx + dy * dy);
            if (distance < r()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean sideScreenEdgeCheck(String side) {
        switch (side) {
            case "up":
                return this.top < 0 ? true : false;
            case "down":
                return this.bottom > Global.SCREEN_Y ? true : false;
            case "left":
                return this.left < 0 ? true : false;
            case "right":
                return this.right > Global.SCREEN_X ? true : false;
            default:
                return false;
        }
    }

}
