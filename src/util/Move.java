/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import gameobj.GameObject;
import graph.Graph;
import graph.Rect;
import java.util.LinkedList;

/**
 *
 * @author Cloud-Razer 此類別定義了 GameObject 的通用八方向移動方法 目前通用的邏輯為移動時如果撞到視窗邊際會不能移動
 */
public class Move {

    private boolean upPressed;
    private boolean downPressed;
    private boolean leftPressed;
    private boolean rightPressed;
    GameObject obj;

    public Move(GameObject obj) {
        this.upPressed = false;
        this.downPressed = false;
        this.leftPressed = false;
        this.rightPressed = false;
        this.obj = obj;
    }

    public void moving(int distance, LinkedList<GameObject> list) {
        // GameObject self, float dx, float dy, LinkedList<GameObject> allObject
        JapStep goal = new JapStep(this.obj, 0, 0, list);
        int dir = movingDir();
//        int distance = 1; // 一次走幾個 pixel，越少看起來越滑順但走越慢
        switch (dir) {
            case Global.UP: // go up
                goal.newSet(this.obj, 0, -distance, list);
                Global.log("goal.getDY(): "+ goal.getDY());
                this.obj.offset(0, goal.getDY());
                break;
            case Global.DOWN: //  go down
                goal.newSet(this.obj, 0, distance, list);
                Global.log("goal.getDY(): "+ goal.getDY());
                this.obj.offset(0, goal.getDY());
                break;
            case Global.LEFT: // go left
                goal.newSet(this.obj, -distance, 0, list);
                this.obj.offset(goal.getDX(), 0);
                break;
            case Global.RIGHT: // go right
                goal.newSet(this.obj, distance, 0, list);
                this.obj.offset(goal.getDX(), 0);
                break;
            case Global.UP_LEFT: // go up-left
                goal.newSet(this.obj, -distance, -distance, list);
                this.obj.offset(goal.getDX(), goal.getDY());
                break;
            case Global.UP_RIGHT: // go up-right
                goal.newSet(this.obj, distance, -distance, list);
                this.obj.offset(goal.getDX(), goal.getDY());
                break;
            case Global.DOWN_LEFT: //  go down-left
                goal.newSet(this.obj, -distance, distance, list);
                this.obj.offset(goal.getDX(), goal.getDY());
                break;
            case Global.DOWN_RIGHT: // go down-right
                goal.newSet(this.obj, distance, distance, list);
                this.obj.offset(goal.getDX(), goal.getDY());
                break;
        }
    }

    public Point correctedDest(Point destPoint) {
        float x = 0;
        float y = 0;
//        Global.log("point.getX(): " + point.getX());
//        Global.log("point.getY(): " + point.getY());
//        Global.log("this.obj.getX(): " + this.obj.getX());
//        Global.log("this.obj.getY(): " + this.obj.getY());
//        Global.log("Global.mapEdgeRight: " + Global.mapEdgeRight);
//        Global.log("Global.mapEdgeLeft: " + Global.mapEdgeLeft);
//        Global.log("Global.mapEdgeUp: " + Global.mapEdgeUp);
//        Global.log("Global.mapEdgeDown: " + Global.mapEdgeDown);
        if (destPoint.getX() + this.obj.getGraph().right() >= Global.mapEdgeRight) {
            x = Global.mapEdgeRight - this.obj.getGraph().right() - 2; //  造成在牆壁邊抖動，且隨機會人卡進牆中
        }
        if (destPoint.getX() + this.obj.getGraph().left() <= Global.mapEdgeLeft) {
            x = Global.mapEdgeLeft - this.obj.getGraph().left() - 2; //  造成在牆壁邊抖動，且隨機會人卡進牆中
        }
        if (destPoint.getY() + this.obj.getGraph().top() <= Global.mapEdgeUp) {
            y = Global.mapEdgeUp - this.obj.getGraph().top() - 2;  //  造成在牆壁邊抖動，且隨機會人卡進牆中
        }
        Global.log("this.obj.getGraph().bottom() " + this.obj.getGraph().bottom());
        if (destPoint.getY() + this.obj.getGraph().bottom() >= Global.mapEdgeDown) {
            y = Global.mapEdgeDown - this.obj.getGraph().bottom() - 2; //  造成在牆壁邊抖動，且隨機會人卡進牆中
        }
        if(x == 0 && y == 0){
            Global.log("move return null");
            return null;
        }
        return new Point((int)x, (int)y);
    }

    public void setPressedStatus(int pressedBtn, boolean status) {
        switch (pressedBtn) {
            case Global.UP:
                upPressed = status;
                break;
            case Global.DOWN:
                downPressed = status;
                break;
            case Global.LEFT:
                leftPressed = status;
                break;
            case Global.RIGHT:
                rightPressed = status;
                break;
        }
    }

    private int movingDir() {
        int dir = 0;
        dir += this.upPressed ? Global.UP : 0;
        dir += this.downPressed ? Global.DOWN : 0;
        dir += this.leftPressed ? Global.LEFT : 0;
        dir += this.rightPressed ? Global.RIGHT : 0;
        return dir;
    }

}
