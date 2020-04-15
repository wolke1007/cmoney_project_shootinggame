/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graph;

/**
 *
 * @author F-NB
 */
public class Circle extends Graph {

    private float r;

    public Circle(float left, float top, float right, float bottom, float r) {
        super(left, top, right, bottom, "circle");
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
        if (b.getColliderType().equals("rect")) {
            return a.intersects(b.left(), b.top(), b.right(), b.bottom());
        }
        Circle tmp = (Circle) b;
        return a.intersects(tmp.centerX(), tmp.centerY(), tmp.r());
    }

    @Override
    public boolean intersects(float x, float y, float r) { // 1ms
        float dx = super.centerX() - x;
        float dy = super.centerY() - y;
        double distance = Math.sqrt(dx * dx + dy * dy);
        if (distance < (this.r + r)) {
            super.setDx((dx < 0) ? -1 : 1);
            super.setDy((dy < 0) ? -1 : 1);
            return true;
        }
        super.setDx(0);
        super.setDy(0);
        return false;
    }

    @Override
    public boolean intersects(Graph target) {
        if (target.getColliderType().equals("circle")) {
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
                dx = super.centerX() - left;
            } else {
                dx = super.centerX() - right;
            }
            float tmp;
            if (super.centerY() < top) {
                tmp = top;
            } else if (super.centerY() > bottom) {
                tmp = bottom;
            } else {
                tmp = super.centerY();
            }
            float dy = super.centerY() - tmp;
            double distance = Math.sqrt(dx * dx + dy * dy);
            if (distance < r()) {
                super.setDx((dx < 0) ? -1 : 1);
                super.setDy(0);
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
                dy = super.centerY() - top;
            } else {
                dy = super.centerY() - bottom;
            }
            float dx = super.centerX() - tmp;
            double distance = Math.sqrt(dx * dx + dy * dy);
            if (distance < r()) {
                super.setDx(0);
                super.setDy((dy < 0) ? -1 : 1);
                return true;
            }
        }
        super.setDx(0);
        super.setDy(0);
        return false;
    }

    @Override
    public boolean innerCollisionToCollision(Graph target) {
        if (target.getColliderType().equals("circle")) {
            Circle tmp = (Circle) target;
            return innerCollisionToCollision(tmp.centerX(), tmp.centerY(), tmp.r());
        }
        return innerCollisionToCollision(target.left(), target.top(), target.right(), target.bottom());
    }

    @Override
    public boolean innerCollisionToCollision(Graph a, Graph b) {
        if (b.getColliderType().equals("rect")) {
            return a.innerCollisionToCollision(b.left(), b.top(), b.right(), b.bottom());
        }
        Circle tmp = (Circle) b;
        return a.innerCollisionToCollision(tmp.centerX(), tmp.centerY(), tmp.r());
    }

    @Override
    public boolean innerCollisionToCollision(float left, float top, float right, float bottom) {
        if (super.left() <= left || super.right() >= right || super.top() <= top || super.bottom() >= bottom) {
            if (super.left() <= left) {
                super.setDx(1);
            } else if (super.right() >= right) {
                super.setDx(-1);
            } else {
                super.setDx(0);
            }
            if (super.top() <= top) {
                super.setDy(1);
            } else if (super.bottom() >= bottom) {
                super.setDy(-1);
            } else {
                super.setDy(0);
            }
            return true;
        }
        super.setDx(0);
        super.setDy(0);
        return false;
    }

    @Override
    public boolean innerCollisionToCollision(float x, float y, float r) {
        float dx = super.centerX() - x;
        float dy = super.centerY() - y;
        double distance = Math.sqrt(dx * dx + dy * dy);
        if (distance + r() >= r) {
            super.setDx((dx < 0) ? 1 : -1);
            super.setDy((dy < 0) ? 1 : -1);
            return true;
        }
        super.setDx(0);
        super.setDy(0);
        return false;
    }

}
