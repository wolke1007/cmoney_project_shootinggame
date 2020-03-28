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

    public Circle(int left, int top, int right, int bottom, double r) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
        this.r = r;
        super.x = left;
        super.y = top;
    }

    public static Circle genWithCenter(int x, int y, int width, int height) {
//        int left = x - width / 2;
        int left = x;
        int right = left + width;
//        int top = y - height / 2;
        int top = y;
        int bottom = top + height;
        double r = width / 2;
        return new Circle(left, top, right, bottom, r);
    }

    public boolean intersectsOfCircle(double x, double y, double r) {
        double dx = Math.abs(this.exactCenterX() - x);
        double dy = Math.abs(this.exactCenterY() - y);
        double distance = Math.sqrt(dx * dx + dy * dy);
        Global.log("!!" + distance);
        if (distance < (this.r + r)) {
            return true;
        }
        return false;
    }//圓形相交

    public boolean intersectsOfCircle(Circle a, Circle b) {
        return a.intersectsOfCircle(b.exactCenterX(), b.exactCenterY(), b.r());
    }//圓形相交

    @Override
    public boolean intersects(Graph target) {
        if (target instanceof Circle) {
            Global.log("enter circle intersects");
            return intersectsOfCircle(target.x, target.y, target.r);
        } // 如果碰撞目標物是圓形則回傳與圓形的碰撞
        return intersects(target.left, target.top, target.right, target.bottom);
    }

    @Override
    public boolean intersects(int left, int top, int right, int bottom) { // 原為 intersectsOfRectangle
//    public boolean intersectsOfRectangle(int left, int top, int right, int bottom) {
        if (this.exactCenterX() > left && this.exactCenterX() < right && this.exactCenterY() > top && this.exactCenterY() < bottom) {
            return true;
        }
        if (this.exactCenterX() < left) {
            double dx = Math.abs(this.exactCenterX() - left);
            double dy1 = Math.abs(this.exactCenterY() - top);
            double dy2 = Math.abs(this.exactCenterY() - bottom);
            double distance1 = Math.sqrt(dx * dx + dy1 * dy1);
            double distance2 = Math.sqrt(dx * dx + dy2 * dy2);
            Global.log("!" + distance1);
            Global.log("!" + distance2);
            if (distance1 < this.r() || distance2 < this.r()) {
                return true;
            }
        } else if (this.exactCenterX() > right) {
            double dx = Math.abs(this.exactCenterX() - right);
            double dy1 = Math.abs(this.exactCenterY() - top);
            double dy2 = Math.abs(this.exactCenterY() - bottom);
            double distance1 = Math.sqrt(dx * dx + dy1 * dy1);
            double distance2 = Math.sqrt(dx * dx + dy2 * dy2);
            Global.log("!!" + distance1);
            Global.log("!!" + distance2);
            if (distance1 < this.r() || distance2 < this.r()) {
                return true;
            }
        } else if (this.exactCenterY() < top) {
            double dx1 = Math.abs(this.exactCenterX() - left);
            double dx2 = Math.abs(this.exactCenterX() - right);
            double dy = Math.abs(this.exactCenterY() - top);
            double distance1 = Math.sqrt(dx1 * dx1 + dy * dy);
            double distance2 = Math.sqrt(dx2 * dx2 + dy * dy);
            Global.log("!!!" + distance1);
            Global.log("!!!" + distance2);
            if (distance1 < this.r() || distance2 < this.r()) {
                return true;
            }
        } else if (this.exactCenterY() > bottom) {
            double dx1 = Math.abs(this.exactCenterX() - left);
            double dx2 = Math.abs(this.exactCenterX() - right);
            double dy = Math.abs(this.exactCenterY() - bottom);
            double distance1 = Math.sqrt(dx1 * dx1 + dy * dy);
            double distance2 = Math.sqrt(dx2 * dx2 + dy * dy);
            Global.log("!!!!" + distance1);
            Global.log("!!!!" + distance2);
            if (distance1 < this.r() || distance2 < this.r()) {
                return true;
            }
        }
        return false;
    }//圓形與矩形相交

    @Override
    public boolean screenEdgeCheck(String side) {
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

    @Override
    public int centerX() {
        return (left + right) / 2;
    }

    @Override
    public int centerY() {
        return (top + bottom) / 2;
    }

    @Override
    public double exactCenterX() {
        return (left + right) / 2;
    }

    @Override
    public double exactCenterY() {
        return (top + bottom) / 2;
    }

    @Override
    public void offset(int dx, int dy) {
        this.left += dx;
        this.right += dx;
        this.top += dy;
        this.bottom += dy;
    }

    @Override
    public int left() {
        return left;
    }

    @Override
    public void setLeft(int left) {
        this.left = left;
    }

    @Override
    public int top() {
        return top;
    }

    @Override
    public void setTop(int top) {
        this.top = top;
    }

    @Override
    public int right() {
        return right;
    }

    @Override
    public void setRight(int right) {
        this.right = right;
    }

    @Override
    public int bottom() {
        return bottom;
    }

    @Override
    public void setBottom(int bottom) {
        this.bottom = bottom;
    }

    public void setR(double r) {
        this.r = r;
    }

    public double r() {
        return this.r;
    }

    @Override
    public int width() {
        return this.right - this.left;
    }

    @Override
    public int height() {
        return this.bottom - this.top;
    }

    //直接使用 intersects
//    public boolean intersectsOfRectangle(Circle a, Rect b) {
//        return a.intersects(b.left(), b.top(), b.right(), b.bottom());
//    }//圓形與矩形相交
}
