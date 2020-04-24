/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graph;

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

    private float dx;
    private float dy;

    private String colliderType;

    public Graph(float left, float top, float right, float bottom, String type) {
        setLeft(left);
        setTop(top);
        setRight(right);
        setBottom(bottom);
        setWidth();
        setHeight();
        setDx(0);
        setDy(0);
        this.colliderType = type;
    }

    public void setCenter(float offsetX, float offsetY) {
        this.left = left() + offsetX;
        this.right = this.left + width();
        this.top = top() + offsetY;
        this.bottom = bottom() + offsetY;
    }

    public String getColliderType() {
        return this.colliderType;
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

    public void setDx(float dx) {
        this.dx = dx;
    }

    public void setDy(float dy) {
        this.dy = dy;
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

    public float getDx() {
        return this.dx;
    }

    public float getDy() {
        return this.dy;
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

    public abstract boolean innerCollisionToCollision(Graph graph);

    public abstract boolean innerCollisionToCollision(Graph a, Graph b);

    public abstract boolean innerCollisionToCollision(float left, float top, float right, float bottom);

    public abstract boolean innerCollisionToCollision(float x, float y, float r);

}
