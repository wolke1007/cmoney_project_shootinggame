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
public class Circle {

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
    }

    public static Circle genWithCenter(int x, int y, int width, int height) {
        int left = x - width / 2;
        int right = left + width;
        int top = y - height / 2;
        int bottom = top + height;
        double r = width / 2;
        return new Circle(left, top, right, bottom, r);
    }

    public boolean intersectsOfCircle(double x, double y, double r) {
        double dx = Math.abs(this.ExactCenterX() - x);
        double dy = Math.abs(this.ExactCenterY() - y);
        double distance = Math.sqrt(dx * dx + dy * dy);
        if (distance > this.r + r) {
            return false;
        }
        return true;
    }//圓形相交

    public boolean intersectsOfCircle(Circle a, Circle b) {
        return a.intersectsOfCircle(b.ExactCenterX(), b.ExactCenterY(), b.r());
    }//圓形相交

    public boolean intersectsOfRectangle(int left, int top, int right, int bottom) {
        if (this.ExactCenterX() > left && this.ExactCenterX() < right && this.ExactCenterY() > top && this.ExactCenterY() < bottom) {
            return true;
        }
        if (this.ExactCenterX() < left) {
            double dx = Math.abs(this.ExactCenterX() - left);
            double dy1 = Math.abs(this.ExactCenterY() - top);
            double dy2 = Math.abs(this.ExactCenterY() - bottom);
            double distance1 = Math.sqrt(dx * dx + dy1 * dy1);
            double distance2 = Math.sqrt(dx * dx + dy2 * dy2);
            if (distance1 < this.r() || distance2 < this.r()) {
                return true;
            }
        }
        if (this.ExactCenterX() > right) {
            double dx = Math.abs(this.ExactCenterX() - right);
            double dy1 = Math.abs(this.ExactCenterY() - top);
            double dy2 = Math.abs(this.ExactCenterY() - bottom);
            double distance1 = Math.sqrt(dx * dx + dy1 * dy1);
            double distance2 = Math.sqrt(dx * dx + dy2 * dy2);
            if (distance1 < this.r() || distance2 < this.r()) {
                return true;
            }
        }
        if (this.ExactCenterY() < top) {
            double dx1 = Math.abs(this.ExactCenterX() - left);
            double dx2 = Math.abs(this.ExactCenterX() - right);
            double dy = Math.abs(this.ExactCenterY() - top);
            double distance1 = Math.sqrt(dx1 * dx1 + dy * dy);
            double distance2 = Math.sqrt(dx2 * dx2 + dy * dy);
            if (distance1 < this.r() || distance2 < this.r()) {
                return true;
            }
        }
        if (this.ExactCenterY() > bottom) {
            double dx1 = Math.abs(this.ExactCenterX() - left);
            double dx2 = Math.abs(this.ExactCenterX() - right);
            double dy = Math.abs(this.ExactCenterY() - bottom);
            double distance1 = Math.sqrt(dx1 * dx1 + dy * dy);
            double distance2 = Math.sqrt(dx2 * dx2 + dy * dy);
            if (distance1 < this.r() || distance2 < this.r()) {
                return true;
            }
        }
        return false;
    }//圓形與矩形相交

    public boolean intersectsOfRectangle(Circle a, Rect b) {
        return a.intersectsOfRectangle(b.left(), b.top(), b.right(), b.bottom());
    }//圓形與矩形相交

    public int CenterX() {
        return (left + right) / 2;
    }

    public int CenterY() {
        return (top + bottom) / 2;
    }

    public double ExactCenterX() {
        return (left + right) / 2;
    }

    public double ExactCenterY() {
        return (top + bottom) / 2;
    }

    public void offset(int dx, int dy) {
        this.left += dx;
        this.right += dx;
        this.top += dy;
        this.bottom += dy;
    }

    public int left() {
        return left;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public int top() {
        return top;
    }

    public void setTop(int top) {
        this.top = top;
    }

    public int right() {
        return right;
    }

    public void setRight(int right) {
        this.right = right;
    }

    public int bottom() {
        return bottom;
    }

    public void setBottom(int bottom) {
        this.bottom = bottom;
    }

    public void setR(double r) {
        this.r = r;
    }

    public double r() {
        return this.r;
    }

    public int width() {
        return this.right - this.left;
    }

    public int height() {
        return this.bottom - this.top;
    }
}
