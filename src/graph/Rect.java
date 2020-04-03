/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graph;

import util.Global;

/**
 *
 * @author Cloud-Razer
 */
public class Rect extends Graph {

    public Rect(float left, float top, float right, float bottom) {
        super(left, top, right, bottom);
    }

    @Override
    public boolean intersects(Graph a, Graph b) {
        if (b instanceof Circle) {
            Circle tmp = (Circle) b;
            return a.intersects(tmp.centerX(), tmp.centerY(), tmp.r());
        }
        return a.intersects(b.left(), b.top(), b.right(), b.bottom());
    }

    @Override
    public boolean intersects(float x, float y, float r) {
        if (x > left() && x < right() && y > top() && y < bottom()) {
            return true;
        }
        if (x < left() || x > right()) {
            float dx;
            if (x < left()) {
                dx = Math.abs(x - left());
            } else {
                dx = Math.abs(x - right());
            }
            float tmp;
            if (y < top()) {
                tmp = top();
            } else if (y > bottom()) {
                tmp = bottom();
            } else {
                tmp = y;
            }
            float dy = Math.abs(y - tmp);
            double distance = Math.sqrt(dx * dx + dy * dy);
            if (distance < r) {
                return true;
            }
        } else if (y < top() || y > bottom()) {
            float tmp;
            if (x < left()) {
                tmp = left();
            } else if (x > right()) {
                tmp = right();
            } else {
                tmp = x;
            }
            float dx = Math.abs(x - tmp);
            float dy;
            if (y < top()) {
                dy = Math.abs(y - top());
            } else {
                dy = Math.abs(y - bottom());
            }
            double distance = Math.sqrt(dx * dx + dy * dy);
            if (distance < r) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean intersects(Graph target) {
        if (target instanceof Rect) {
            return intersects(target.left(), target.top(), target.right(), target.bottom());
        } else {
            Circle tmp = (Circle) target;
            return intersects(tmp.centerX(), tmp.centerY(), tmp.r());
        }
    }

    @Override
    public boolean intersects(float left, float top, float right, float bottom) {
        if (this.left() > right) {
            return false;
        }
        if (this.right() < left) {
            return false;
        }
        if (this.top() > bottom) {
            return false;
        }
        if (this.bottom() < top) {
            return false;
        }
        return true;
    }

    @Override
    public boolean innerCollisionToCollision(Graph target) {
        if (target instanceof Rect) {
            return innerCollisionToCollision(target.left(), target.top(), target.right(), target.bottom());
        } else {
            Circle tmp = (Circle) target;
            return innerCollisionToCollision(tmp.centerX(), tmp.centerY(), tmp.r());
        }
    }

    @Override
    public boolean innerCollisionToCollision(Graph a, Graph b) {
        if (b instanceof Circle) {
            Circle tmp = (Circle) b;
            return a.innerCollisionToCollision(tmp.centerX(), tmp.centerY(), tmp.r());
        }
        return a.innerCollisionToCollision(b.left(), b.top(), b.right(), b.bottom());
    }

    @Override
    public boolean innerCollisionToCollision(float left, float top, float right, float bottom) {
        if (super.left() <= left || super.right() >= right || super.top() <= top || super.bottom() >= bottom) {
            return true;
        }
        return false;
    }

    @Override
    public boolean innerCollisionToCollision(float x, float y, float r) {
        float dx = Math.abs(super.right() - x);
        if (Math.abs(super.left() - x) > Math.abs(super.right() - x)) {
            dx = Math.abs(super.left() - x);
        }
        float dy = Math.abs(super.bottom() - y);
        if (Math.abs(super.top() - y) > Math.abs(super.bottom() - y)) {
            dy = Math.abs(super.top() - y);
        }
        double distance = Math.sqrt(dx * dx + dy * dy);
        if (distance > r) {
            return true;
        }
        return false;
    }

}
