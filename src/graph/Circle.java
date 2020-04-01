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

    public Circle(float left, float top, float right, float bottom, float r) {
        super(left, top, right, bottom);
        setR(r);
    }

    public void setR(float r) {
        this.r = r;
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
        if (distance < (this.r + r)) {
            return true;
        }
        return false;
    }

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

}
