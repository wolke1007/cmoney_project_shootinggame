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

    private float r;

    public Circle(int left, int top, int right, int bottom, float r) {
        super(left, top, right, bottom);
        setR(r);
    }

//    @Override
    public void setR(float r) {
        this.r = r;
    }

//    @Override
    public float r() {
        return this.r;
    }

    public boolean intersects(Graph a, Graph b) {
        if (b instanceof Rect) {
            return a.intersects(b.left(), b.top(), b.right(), b.bottom());
        }
        Circle tmp = (Circle) b;
        return a.intersects(tmp.centerX(), tmp.centerY(), tmp.r());
    }//圓形相交

    @Override
    public boolean intersects(float x, float y, float r) {
        float dx = Math.abs(super.centerX() - x);
        float dy = Math.abs(super.centerY() - y);
        double distance = Math.sqrt(dx * dx + dy * dy);
        if (distance < (this.r + r)) {
            return true;
        }
        return false;
    }//圓形相交

    @Override
    public boolean intersects(Graph target) {
        if (target instanceof Circle) {
            Circle tmp = (Circle) target;
            return intersects(tmp.centerX(), tmp.centerY(), tmp.r());
        } // 如果碰撞目標物是圓形則回傳與圓形的碰撞
        return intersects(target.left(), target.top(), target.right(), target.bottom());
    }

    @Override
    public boolean intersects(float left, float top, float right, float bottom) { // 原為 intersectsOfRectangle
//    public boolean intersectsOfRectangle(int left, int top, int right, int bottom) {
        if (super.centerX() > left && super.centerX() < right && super.centerY() > top && super.centerY() < bottom) {
            return true;
        }
        if (super.centerX() < left) {
            float dx = Math.abs(super.centerX() - left);
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
        } else if (super.centerX() > right) {
            float dx = Math.abs(super.centerX() - right);
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
        } else if (super.centerY() < top) {
            float tmp;
            if (super.centerX() < left) {
                tmp = left;
            } else if (super.centerX() > right) {
                tmp = right;
            } else {
                tmp = super.centerX();
            }
            float dx = Math.abs(super.centerX() - tmp);
            float dy = Math.abs(super.centerY() - top);
            double distance = Math.sqrt(dx * dx + dy * dy);
            if (distance < r()) {
                return true;
            }
        } else if (super.centerY() > bottom) {
            float tmp;
            if (super.centerX() < left) {
                tmp = left;
            } else if (super.centerX() > right) {
                tmp = right;
            } else {
                tmp = super.centerX();
            }
            float dx = Math.abs(super.centerX() - tmp);
            float dy = Math.abs(super.centerY() - bottom);
            double distance = Math.sqrt(dx * dx + dy * dy);
            if (distance < r()) {
                return true;
            }
        }
        return false;
    }//圓形與矩形相交

    @Override
    public boolean screenEdgeCheck(String side) {
        switch (side) {
            case "up":
                return super.top() < 0 ? true : false;
            case "down":
                return super.bottom() > Global.SCREEN_Y ? true : false;
            case "left":
                return super.left() < 0 ? true : false;
            case "right":
                return super.right() > Global.SCREEN_X ? true : false;
            default:
                return false;
        }
    }

    //直接使用 intersects
//    public boolean intersectsOfRectangle(Circle a, Rect b) {
//        return a.intersects(b.left(), b.top(), b.right(), b.bottom());
//    }//圓形與矩形相交
}
