/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import gameobj.GameObject;
import graph.Graph;
import graph.Rect;
import java.util.ArrayList;

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
    private JabStep goal;
    private Graph selfCollider;
    //測試用ElasticCollision
    private VectorCollision vectorMove;

    public Move(GameObject obj) {
        this.upPressed = false;
        this.downPressed = false;
        this.leftPressed = false;
        this.rightPressed = false;
        this.obj = obj;
        this.vectorMove = new VectorCollision(this.obj, 0, 0);
    }

    public void setAllObjects(ArrayList<GameObject> list) {
        this.vectorMove.setAllObjects(list);
    }

    public void moving(int distance) {
        int dir = movingDir();
//        int distance = 1; // 一次走幾個 pixel，越少看起來越滑順但走越慢
        switch (dir) {
            case Global.UP: // go up
                this.vectorMove.newOffset(0, -distance);
                break;
            case Global.DOWN: //  go down
                this.vectorMove.newOffset(0, distance);
                break;
            case Global.LEFT: // go left
                this.vectorMove.newOffset(-distance, 0);
                break;
            case Global.RIGHT: // go right
                this.vectorMove.newOffset(distance, 0);
                break;
            case Global.UP_LEFT: // go up-left
                this.vectorMove.newOffset(-distance, -distance);
                break;
            case Global.UP_RIGHT: // go up-right
                this.vectorMove.newOffset(distance, -distance);
                break;
            case Global.DOWN_LEFT: //  go down-left
                this.vectorMove.newOffset(-distance, distance);
                break;
            case Global.DOWN_RIGHT: // go down-right
                this.vectorMove.newOffset(distance, distance);
                break;
        }
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
