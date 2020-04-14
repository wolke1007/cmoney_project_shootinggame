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
//        this.goal = new JabStep(this.obj.getCollider(), 0, 0, null);
        this.vectorMove = new VectorCollision(this.obj, 0, 0, null);
    }

    public void moving(int distance, LinkedList<GameObject> list) {
        int dir = movingDir();
//        int distance = 1; // 一次走幾個 pixel，越少看起來越滑順但走越慢
        switch (dir) {
            case Global.UP: // go up
//                goal.newSet(this.obj.getCollider(), 0, -distance, list);
//                this.obj.offset(0, goal.getDY());
                this.vectorMove.newSet(0, -distance, list);
                break;
            case Global.DOWN: //  go down
//                goal.newSet(this.obj.getCollider(), 0, distance, list);
//                this.obj.offset(0, goal.getDY());
                this.vectorMove.newSet(0, distance, list);
                break;
            case Global.LEFT: // go left
//                goal.newSet(this.obj.getCollider(), -distance, 0, list);
//                this.obj.offset(goal.getDX(), 0);
                this.vectorMove.newSet(-distance, 0, list);
                break;
            case Global.RIGHT: // go right
//                goal.newSet(this.obj.getCollider(), distance, 0, list);
//                this.obj.offset(goal.getDX(), 0);
                this.vectorMove.newSet(distance, 0, list);
                break;
            case Global.UP_LEFT: // go up-left
//                goal.newSet(this.obj.getCollider(), -distance, -distance, list);
//                this.obj.offset(goal.getDX(), goal.getDY());
                this.vectorMove.newSet(-distance, -distance, list);
                break;
            case Global.UP_RIGHT: // go up-right
//                goal.newSet(this.obj.getCollider(), distance, -distance, list);
//                this.obj.offset(goal.getDX(), goal.getDY());
                this.vectorMove.newSet(distance, -distance, list);
                break;
            case Global.DOWN_LEFT: //  go down-left
//                goal.newSet(this.obj.getCollider(), -distance, distance, list);
//                this.obj.offset(goal.getDX(), goal.getDY());
                this.vectorMove.newSet(-distance, distance, list);
                break;
            case Global.DOWN_RIGHT: // go down-right
//                goal.newSet(this.obj.getCollider(), distance, distance, list);
//                this.obj.offset(goal.getDX(), goal.getDY());
                this.vectorMove.newSet(distance, distance, list);
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
