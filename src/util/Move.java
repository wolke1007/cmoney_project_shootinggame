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

    public void doMoving() {
        int dir = movingDir();
        int speed = 10; // 一次走幾個 pixel，越少看起來越滑順但走越慢
        switch (dir) {
            case Global.UP: // go up
                if (!this.obj.getCollider().screenEdgeCheck("up")) {
                    this.obj.offset(0, -speed);
                }
                break;
            case Global.DOWN: // go down
                if (!this.obj.getCollider().screenEdgeCheck("down")) {
                    this.obj.offset(0, speed);
                }
                break;
            case Global.LEFT: // go left
                if (!this.obj.getCollider().screenEdgeCheck("left")) {
                    this.obj.offset(-speed, 0);
                }
                break;
            case Global.RIGHT: // go right
                if (!this.obj.getCollider().screenEdgeCheck("right")) {
                    this.obj.offset(speed, 0);
                }
                break;
            case Global.UP_LEFT: // go up-left
                if(this.obj.getCollider().screenEdgeCheck("up") || this.obj.getCollider().screenEdgeCheck("left")){
                    // 如果撞到上面，但依然在往右上走，則應該要往右走
                    if(!this.obj.getCollider().screenEdgeCheck("up")){ this.obj.offset(0, -speed); }
                    if(!this.obj.getCollider().screenEdgeCheck("left")){ this.obj.offset(-speed, 0); }
                }else{
                    Global.log("dir is UP_LEFT:" + Global.UP_LEFT);
                    this.obj.offset(-speed, -speed);
                }
                break;
            case Global.UP_RIGHT: // go up-right
                if(this.obj.getCollider().screenEdgeCheck("up") || this.obj.getCollider().screenEdgeCheck("right")){
                    if(!this.obj.getCollider().screenEdgeCheck("up")){ this.obj.offset(0, -speed); }
                    if(!this.obj.getCollider().screenEdgeCheck("right")){ this.obj.offset(speed, 0); }
                }else{
                    Global.log("dir is UP_RIGHT:" + Global.UP_RIGHT);
                    this.obj.offset(speed, -speed);
                }
                break;
            case Global.DOWN_LEFT: // go down-left
                if(this.obj.getCollider().screenEdgeCheck("down") || this.obj.getCollider().screenEdgeCheck("left")){
                    if(!this.obj.getCollider().screenEdgeCheck("down")){ 
                        this.obj.offset(0, speed); }
                    if(!this.obj.getCollider().screenEdgeCheck("left")){ 
                        this.obj.offset(-speed, 0); }
                }else{
                    Global.log("dir is DOWN_LEFT:" + Global.DOWN_LEFT);
                    this.obj.offset(-speed, speed);
                }
                break;
            case Global.DOWN_RIGHT: // go down-right
                if(this.obj.getCollider().screenEdgeCheck("down") || this.obj.getCollider().screenEdgeCheck("right")){
                    if(!this.obj.getCollider().screenEdgeCheck("down")){ this.obj.offset(0, speed); }
                    if(!this.obj.getCollider().screenEdgeCheck("right")){ this.obj.offset(speed, 0); }
                }else{
                    Global.log("dir is DOWN_RIGHT:" + Global.DOWN_RIGHT);
                    this.obj.offset(speed, speed);
                }
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
        Global.log("dir: " + dir);
        return dir;
    }

}
