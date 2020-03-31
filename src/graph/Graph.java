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

    protected int x;
    protected int y;
    protected double r;
    protected int left;
    protected int top;
    protected int right;
    protected int bottom;

//    public abstract boolean intersects(int left, int top, int right, int bottom);
    public abstract boolean intersects(Graph graph);

    public abstract boolean intersects(int left, int top, int right, int bottom);

    public abstract boolean screenEdgeCheck();
    
    public abstract boolean sideScreenEdgeCheck(String side);

    public abstract int centerX();

    public abstract int centerY();

    public abstract double exactCenterX();

    public abstract double exactCenterY();

    public abstract void offset(int dx, int dy);

    public abstract int left();

    public abstract void setLeft(int left);

    public abstract int top();

    public abstract void setTop(int top);

    public abstract int right();

    public abstract void setRight(int right);

    public abstract int bottom();

    public abstract boolean intersects(Graph a, Graph b);

    public abstract boolean intersects(float left, float top, float right, float bottom);

    public abstract int width();

    public abstract int height();

}
