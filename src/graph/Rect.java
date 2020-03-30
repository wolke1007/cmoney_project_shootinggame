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

    public Rect(int left, int top, int right, int bottom) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
        super.x = left;
        super.y = top;
    }

    public static Rect genWithCenter(int x, int y, int width, int height) {
        int left = x;
        int right = left + width;
        int top = y;
        int bottom = top + height;
        return new Rect(left, top, right, bottom);
    }

    @Override
    public boolean intersects(Graph target) {
        if (target instanceof Rect) { // TODO這邊只有做與長方形的碰撞，應該要做與圓形的碰撞
            return intersects(target.left, target.top, target.right, target.bottom);
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

    @Override
    public boolean screenEdgeCheck(){
        String[] arr = {"up", "down", "left", "right"};
        for(int i = 0; i < arr.length; i++){
            if (sideScreenEdgeCheck(arr[i])){
                return true;
            }
        }
        return false;
    }
    
//    public static boolean intersects(Rect a, Rect b){
    public static boolean intersects(Graph a, Graph b) {
        return a.intersects(b.left, b.top, b.right, b.bottom);
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
        return (left + right) / 2d;
    }

    @Override
    public double exactCenterY() {
        return (top + bottom) / 2d;
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

    @Override
    public int width() {
        return this.right - this.left;
    }

    @Override
    public int height() {
        return this.bottom - this.top;
    }

}
