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

/**
 *
 * @author Cloud-Razer
 */
public abstract class GameObject {

    private Graph collider;
    protected Graph graph;
    private int x;
    private int y;

    public GameObject(String colliderType, int x, int y, int width, int height, int colliderWidth, int colliderHeight) {
        this.x = x;
        this.y = y;
        switch (colliderType) {
            case "circle":
                this.graph = Circle.genWithCenter(x, y, width, height);
                this.collider = Circle.genWithCenter(x, y, width, height);
                Global.log("create circle");
                break;
            case "rect":
                this.graph = Rect.genWithCenter(x, y, width, height);
                this.collider = Rect.genWithCenter(x, y, width, height);
                Global.log("create rect");
                break;
        }
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getCenterX() {
        return this.graph.centerX();
    }

    public int getCenterY() {
        return this.graph.centerY();
    }

    public int width() {
        return this.graph.width();
    }

    public int height() {
        return this.graph.height();
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void offset(int dx, int dy) {
        this.x += dx;
        this.y += dy;
        Global.log("dx:" + dx);
        Global.log("dy:" + dy);
        this.graph.offset(dx, dy);
        this.collider.offset(dx, dy);
    }

    public void offsetX(int x) {
        this.graph.offset(x - this.graph.centerX(), 0);
        this.collider.offset(x - this.collider.centerX(), 0);
    }

    public Graph getGraph() {
        return this.graph;
    }

    public Graph getCollider() {
        return this.collider;
    }

    public void offsetY(int y) {
        this.graph.offset(0, y - this.graph.centerY());
        this.collider.offset(0, y - this.collider.centerY());
    }

    public boolean isCollision(GameObject obj) {
        if (this.collider == null || obj.collider == null) {
            return false;
        }
        return Rect.intersects(this.collider, obj.collider);
    }

    public void paint(Graphics g) {
        paintComponent(g);
        if (this.graph != null && Global.IS_DEBUG && this.graph instanceof Circle) {
            g.setColor(Color.RED);
            g.drawOval(this.graph.left(), this.graph.top(), this.graph.width(), this.graph.height());
            g.setColor(Color.BLUE);
            g.drawOval(this.collider.left(), this.collider.top(), this.collider.width(), this.collider.height());
            g.setColor(Color.BLACK);
        }
        if (this.graph != null && Global.IS_DEBUG && this.graph instanceof Rect) {
            g.setColor(Color.RED);
            g.drawRect(this.graph.left(), this.graph.top(), this.graph.width(), this.graph.height());
            g.setColor(Color.BLUE);
            g.drawRect(this.collider.left(), this.collider.top(), this.collider.width(), this.collider.height());
            g.setColor(Color.BLACK);
        }
    }

    public abstract void update();

    public abstract void paintComponent(Graphics g);
}
