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

    private int left;
    private int top;
    private int right;
    private int bottom;

    @Override
    public boolean intersects(Graph a, Graph b) {
        if (b instanceof Circle) {
            Circle tmp = (Circle) b;
            return a.intersects(tmp.centerX(), tmp.centerY(), tmp.r());
        } else {
            return a.intersects(b.left(), b.top(), b.right(), b.bottom());
        }
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
            return false;
        }
    }

    @Override
    public boolean intersects(int left, int top, int right, int bottom) {
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

    public boolean sideScreenEdgeCheck(String side) {
        // TODO 這邊如果確定實作方式了 Circle 的 screenEdgeCheck 也必須一併修改
        boolean result;
        switch (side) {
            case "up":
                result = this.top < Global.mapEdgeUp ? true : false;
                break;
            case "down":
                result = this.bottom > Global.mapEdgeDown ? true : false;
                break;
            case "left":
                result = (this.left < Global.mapEdgeLeft) ? true : false;
                break;
            case "right":
                result =  this.right > Global.mapEdgeRight? true : false;
                break;
            default:
                result = false;
                break;
        }
        return result;
    }

}
