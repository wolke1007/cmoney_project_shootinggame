/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameobj;

import graph.Circle;
import graph.Graph;
import graph.Rect;
import java.awt.Graphics;
import util.Global;
import java.awt.Color;
import util.Point;

/**
 *
 * @author Cloud-Razer
 */
public abstract class GameObject {

    private Graph collider;
    protected Graph graph;
    private float x;
    private float y;

    public GameObject(String colliderType, float x, float y, int width, int height, int colliderWidth, int colliderHeight) {
        switch (colliderType) {
            case "circle":
                this.graph = new Circle(x, y, x + width, y + height, width / 2f);
                this.collider = new Circle(x, y, x + width, y + height, width / 2f);
                break;
            case "rect":
                this.graph = new Rect(x, y, x + width, y + height);
                this.collider = new Rect(x, y, x + width, y + height);
                break;
        }
        setX(x);
        setY(y);
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    public float getCenterX() {
        return this.graph.centerX();
    }

    public float getCenterY() {
        return this.graph.centerY();
    }

    public float width() {
        return this.graph.width();
    }

    public float height() {
        return this.graph.height();
    }

    public void setX(float x) {
        this.x = x;
        this.graph.setLeft(this.x);
        this.collider.setLeft(this.x);
        this.graph.setRight(this.x + width());
        this.collider.setRight(this.x + width());
    }

    public void setY(float y) {
        this.y = y;
        this.graph.setTop(this.y);
        this.collider.setTop(this.y);
        this.graph.setBottom(this.y + height());
        this.collider.setBottom(this.y + height());
    }

    public void setXY(float x, float y) {
        setX(x);
        setY(y);
    }

    public void offset(float dx, float dy) {
        this.x += dx;
        this.y += dy;
        this.graph.offset(dx, dy);
        this.collider.offset(dx, dy);
    }

    public void offset(Point dest) {
        Global.log("offset x: " + dest.getX());
        Global.log("offset y: " + dest.getY());
        this.x += dest.getX();
        this.y += dest.getY();
        this.graph.offset(dest.getX(), dest.getY());
        this.collider.offset(dest.getX(), dest.getY());
    }

    public void offsetX(float x) {
        this.graph.offset(x - this.graph.centerX(), 0);
        this.collider.offset(x - this.collider.centerX(), 0);
    }

    public Graph getGraph() {
        return this.graph;
    }

    public Graph getCollider() {
        return this.collider;
    }

    public void offsetY(float y) {
        this.graph.offset(0, y - this.graph.centerY());
        this.collider.offset(0, y - this.collider.centerY());
    }

    public boolean isCollision(GameObject obj) {
        if (this.collider == null || obj.collider == null) {
            return false;
        }
        return this.collider.intersects(obj.collider);
    }

    public void paint(Graphics g) {
        paintComponent(g);
        if (this.graph != null && Global.IS_DEBUG && this.graph instanceof Circle) {
            g.setColor(Color.RED);
            g.drawOval((int) this.graph.left(), (int) this.graph.top(), (int) this.graph.width(), (int) this.graph.height());
            g.setColor(Color.BLUE);
            g.drawOval((int) this.collider.left(), (int) this.collider.top(), (int) this.collider.width(), (int) this.collider.height());
            g.setColor(Color.BLACK);
        }
        if (this.graph != null && Global.IS_DEBUG && this.graph instanceof Rect) {
            g.setColor(Color.RED);
            g.drawRect((int) this.graph.left(), (int) this.graph.top(), (int) this.graph.width(), (int) this.graph.height());
            g.setColor(Color.BLUE);
            g.drawRect((int) this.collider.left(), (int) this.collider.top(), (int) this.collider.width(), (int) this.collider.height());
            g.setColor(Color.BLACK);
        }
    }

//    public boolean isMeetMapEdge(){
//        Global.log("mapEdgeUp" + Global.mapEdgeUp);
//        Global.log("mapEdgeDown" + Global.mapEdgeDown);
//        Global.log("mapEdgeLeft" + Global.mapEdgeLeft);
//        Global.log("mapEdgeRight" + Global.mapEdgeRight);
//        return this.graph.intersects(Global.mapEdgeRight, Global.mapEdgeDown, Global.mapEdgeLeft, Global.mapEdgeUp);
//    }
    public abstract void update();

    public abstract void setDir(int dir);

    public abstract void setMovementPressedStatus(int dir, boolean status);

    public abstract void paintComponent(Graphics g);
}
