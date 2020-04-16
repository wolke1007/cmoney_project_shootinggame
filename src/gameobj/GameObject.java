/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameobj;

import graph.Circle;
import graph.Graph;
import graph.Rect;
import java.awt.Color;
import java.awt.Graphics;
import util.Global;

/**
 *
 * @author Cloud-Razer
 */
public abstract class GameObject {

    private Graph collider;
    private Graph graph;
    protected float x;
    protected float y;
    protected int paintPriority;
    private String type;
    //血量
    private float hp;
    private float hpBarWidth;
    private float dividend;

    private int graphWidth;
    private int graphHeight;

    //血量end
    public GameObject(String colliderType, float x, float y, int width, int height, int colliderWidth, int colliderHeight) {
        this.graphWidth = width;
        this.graphHeight = height;
        switch (colliderType) {
            case "circle":
                this.graph = new Circle(x, y, x + width, y + height, width / 2f);
                this.collider = new Circle(x, y, x + colliderWidth, y + colliderHeight, colliderWidth / 2f);
                break;
            case "rect":
                this.graph = new Rect(x, y, x + width, y + height);
                this.collider = new Rect(x, y, x + colliderWidth, y + colliderHeight);
                break;
        }
        setX(x);
        setY(y);
        this.setHpPoint(Integer.MAX_VALUE);
        this.paintPriority = 10; // 數字越大越後面畫，目前設計為 10 最大，同個數字則表示誰先誰後畫都沒差
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }

    public void setHpPoint(float dividend) {
        this.hpBarWidth = this.width();
        this.dividend = this.hpBarWidth / dividend;
    }

    public boolean subtractHp() {
        this.hpBarWidth -= this.dividend;
        return true;
    }

    public boolean increaseHp() {
        this.hpBarWidth += this.dividend;
        return true;
    }

    public float getHpBarWidth() {
        return this.hpBarWidth;
    }

    public float getHp() {
        this.hp = this.hpBarWidth / this.dividend;
        return this.hp;
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    public float getCenterX() {
        return this.collider.centerX();
    }

    public float getCenterY() {
        return this.collider.centerY();
    }

    public float width() {
        return this.collider.width();
    }

    public float height() {
        return this.collider.height();
    }

    public void setX(float x) {
        this.x = x;
        this.graph.setLeft(this.x);
        this.collider.setLeft(this.x);
        this.graph.setRight(this.x + this.graphWidth);
        this.collider.setRight(this.x + width());
    }

    public void setY(float y) {
        this.y = y;
        this.graph.setTop(this.y);
        this.collider.setTop(this.y);
        this.graph.setBottom(this.y + this.graphHeight);
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

    public void offsetX(float x) {
        this.graph.offset(x - this.graph.centerX(), 0);
        this.collider.offset(x - this.collider.centerX(), 0);
        setX(x);
    }

    public void offsetY(float y) {
        this.graph.offset(0, y - this.graph.centerY());
        this.collider.offset(0, y - this.collider.centerY());
        setY(y);
    }

    public Graph getGraph() {
        return this.graph;
    }

    public Graph getCollider() {
        return this.collider;
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
            g.drawOval((int) (this.graph.left() - Global.viewX),
                    (int) (this.graph.top() - Global.viewY),
                    (int) this.graph.width(),
                    (int) this.graph.height());
            g.setColor(Color.BLUE);
            g.drawOval((int) (this.collider.left() - Global.viewX),
                    (int) (this.collider.top() - Global.viewY),
                    (int) this.collider.width(),
                    (int) this.collider.height());
            g.setColor(Color.BLACK);
        }
        if (this.graph != null && Global.IS_DEBUG && this.graph instanceof Rect) {
            g.setColor(Color.RED);
            g.drawRect((int) (this.graph.left() - Global.viewX),
                    (int) (this.graph.top() - Global.viewY),
                    (int) this.graph.width(),
                    (int) this.graph.height());
            g.setColor(Color.BLUE);
            g.drawRect((int) (this.collider.left() - Global.viewX),
                    (int) (this.collider.top() - Global.viewY),
                    (int) this.collider.width(),
                    (int) this.collider.height());
            g.setColor(Color.BLACK);
        }
    }

    public boolean isMeetMapEdge() {
        Global.log("mapEdgeUp" + Global.mapEdgeUp);
        Global.log("mapEdgeDown" + Global.mapEdgeDown);
        Global.log("mapEdgeLeft" + Global.mapEdgeLeft);
        Global.log("mapEdgeRight" + Global.mapEdgeRight);
        return this.graph.intersects(Global.mapEdgeRight, Global.mapEdgeDown, Global.mapEdgeLeft, Global.mapEdgeUp);
    }

    public abstract void update();

    public abstract void setDir(int dir);

    public abstract void paintComponent(Graphics g);

}
