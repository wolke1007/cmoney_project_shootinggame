/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import gameobj.GameObject;
import graph.Rect;

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

    public Point getDestination(boolean reverse) { // 此移動可以穿牆，目前只有地圖使用 ，上下左右邏輯完全與角色移動顛倒
        int dir = movingDir();
        int speed = 10; // 一次走幾個 pixel，越少看起來越滑順但走越慢
        Point ret = null;
        switch (dir) {
            case Global.DOWN: // go up
                ret = reverse ? new Point(0, -speed) : new Point(0, speed);
                break;
            case Global.UP: // go down
                ret = reverse ? new Point(0, speed) : new Point(0, -speed);
                break;
            case Global.RIGHT: // go left
                ret = reverse ? new Point(-speed, 0) : new Point(speed, 0);
                break;
            case Global.LEFT: // go right
                ret = reverse ? new Point(speed, 0) : new Point(-speed, 0);
                break;
            case Global.DOWN_RIGHT: // go up-left
                ret = reverse ? new Point(-speed, -speed) : new Point(speed, speed);
                break;
            case Global.DOWN_LEFT: // go up-right
                ret = reverse ? new Point(speed, -speed) : new Point(-speed, speed);
                break;
            case Global.UP_RIGHT: // go down-left
                ret = reverse ? new Point(-speed, speed) : new Point(speed, -speed);
                break;
            case Global.UP_LEFT: // go down-right
                ret = reverse ? new Point(speed, speed) : new Point(-speed, -speed);
                break;
        }
        return ret;
    } // 此移動可以穿牆，目前只有地圖使用，上下左右邏輯完全與角色移動顛倒

    public void moving(Point point) {
        this.obj.offset(point);
    }

    public Point correctedDest(Point point) {
        int x = 0;
        int y = 0;
        Global.log("point.getX(): " + point.getX());
        Global.log("point.getY(): " + point.getY());
        Global.log("this.obj.getX(): " + this.obj.getX());
        Global.log("this.obj.getY(): " + this.obj.getY());
        Global.log("Global.mapEdgeRight: " + Global.mapEdgeRight);
        Global.log("Global.mapEdgeLeft: " + Global.mapEdgeLeft);
        Global.log("Global.mapEdgeUp: " + Global.mapEdgeUp);
        Global.log("Global.mapEdgeDown: " + Global.mapEdgeDown);
        if (point.getX() + this.obj.getGraph().right() >= Global.mapEdgeRight) {
            x = Global.mapEdgeRight - this.obj.getGraph().right() - 2; //  造成在牆壁邊抖動，且隨機會人卡進牆中
        }
        if (point.getX() + this.obj.getGraph().left() <= Global.mapEdgeLeft) {
            x = Global.mapEdgeLeft - this.obj.getGraph().left() - 2; //  造成在牆壁邊抖動，且隨機會人卡進牆中
        }
        if (point.getY() + this.obj.getGraph().top() <= Global.mapEdgeUp) {
            y = Global.mapEdgeUp - this.obj.getGraph().top() - 2;  //  造成在牆壁邊抖動，且隨機會人卡進牆中
        }
        Global.log("this.obj.getGraph().bottom() " + this.obj.getGraph().bottom());
        if (point.getY() + this.obj.getGraph().bottom() >= Global.mapEdgeDown) {
            y = Global.mapEdgeDown - this.obj.getGraph().bottom() - 2; //  造成在牆壁邊抖動，且隨機會人卡進牆中
        }
        if(x == 0 && y == 0){
            Global.log("move return null");
            return null;
        }
        return new Point(x, y);
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
