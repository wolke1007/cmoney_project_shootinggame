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
public abstract class Graph {

    private float x;
    private float y;
    private float left;
    private float top;
    private float right;
    private float bottom;
    private float width;
    private float hight;

    public Graph(float left, float top, float right, float bottom) {
        setLeft(left);
        setTop(top);
        setRight(right);
        setBottom(bottom);
        setWidth();
        setHeight();
    }

    public void setLeft(float left) {
        this.left = left;
        this.x = this.left;
    }

    public void setTop(float top) {
        this.top = top;
        this.y = this.top;
    }

    public void setRight(float right) {
        this.right = right;
    }

    public void setBottom(float bottom) {
        this.bottom = bottom;
    }

    private void setWidth() {
        this.width = right() - left();
    }

    private void setHeight() {
        this.hight = bottom() - top();
    }

    public float left() {
        return this.left;
    }

    public float top() {
        return this.top;
    }

    public float right() {
        return this.right;
    }

    public float bottom() {
        return this.bottom;
    }

    public float width() {
        return this.width;
    }

    public float height() {
        return this.hight;
    }

    public float centerX() {
        return (left() + right()) / 2;
    }

    public float centerY() {
        return (top() + bottom()) / 2;
    }

    public void offset(float dx, float dy) {
        setLeft(left() + dx);
        setRight(right() + dx);
        setTop(top() + dy);
        setBottom(bottom() + dy);
    }

    public abstract boolean intersects(Graph graph);

    public abstract boolean intersects(Graph a, Graph b);

    public abstract boolean intersects(float left, float top, float right, float bottom);

    public abstract boolean intersects(float x, float y, float r);

}
