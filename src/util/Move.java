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
        int speed = 4;
        switch (dir) {
            case 1: // go up
                if (!this.obj.getRect().screenEdgeCheck("up")) {
                    Global.log("obj y " + this.obj.getY());
                    this.obj.setY(this.obj.getY() - Global.UNIT_Y / speed);
                    this.obj.offset(this.obj.getX(), this.obj.getY());
                }
                break;
            case 2: // go down
                if (!this.obj.getRect().screenEdgeCheck("down")) {
                    this.obj.setY(this.obj.getY() + Global.UNIT_Y / speed);
                    this.obj.offset(this.obj.getX(), this.obj.getY() + Global.UNIT_Y / speed);
                }
                break;
            case 4: // go left
                if (!this.obj.getRect().screenEdgeCheck("left")) {
                    this.obj.setX(this.obj.getX() - Global.UNIT_X / speed);
                    this.obj.offset(this.obj.getX() - Global.UNIT_X / speed, this.obj.getY());
                }
                break;
            case 7: // go right
                if (!this.obj.getRect().screenEdgeCheck("right")) {
                    this.obj.setX(this.obj.getX() + Global.UNIT_X / speed);
                    this.obj.offset(this.obj.getX() + Global.UNIT_X / speed, this.obj.getY());
                }
                break;
        }
        //        switch (this.dir) {
//            case Global.UP: // go up
//                moveFourWay(this.dir);
//                break;
//            case Global.DOWN: // go down
//                moveFourWay(this.dir);
//                break;
//            case Global.LEFT: // go left
//                moveFourWay(this.dir);
//                break;
//            case Global.RIGHT: // go right
//                moveFourWay(this.dir);
//                break;
//            case Global.UP_LEFT: // go up-left
//                if(act.screenEdgeCheck("up") || act.screenEdgeCheck("left")){
//                    if(!act.screenEdgeCheck("up")){ moveFourWay(1); }
//                    if(!act.screenEdgeCheck("left")){ moveFourWay(4); }
//                }else{
//                    this.offset(this.x -= Global.UNIT_X / speed, this.y -= Global.UNIT_Y / speed);
//                }
//                if(!view.screenEdgeCheck("up")){
//                    this.view.move(this.dir);
//                }
//                break;
//            case Global.UP_RIGHT: // go up-right
//                if(act.screenEdgeCheck("up") || act.screenEdgeCheck("right")){
//                    if(!act.screenEdgeCheck("up")){ moveFourWay(1); }
//                    if(!act.screenEdgeCheck("right")){ moveFourWay(7); }
//                }else{
//                    this.offset(this.x += Global.UNIT_X / speed, this.y += Global.UNIT_Y / speed);
//                }
//                if(!view.screenEdgeCheck("up")){
//                    this.view.move(this.dir);
//                }
//                break;
//            case Global.DOWN_LEFT: // go down-left
//                if(act.screenEdgeCheck("up") || act.screenEdgeCheck("right")){
//                    if(!act.screenEdgeCheck("up")){ moveFourWay(1); }
//                    if(!act.screenEdgeCheck("right")){ moveFourWay(7); }
//                }else{
//                    this.offset(this.x += Global.UNIT_X / speed, this.y += Global.UNIT_Y / speed);
//                }
//                if(!view.screenEdgeCheck("up")){
//                    this.view.move(this.dir);
//                }
//                break;
//            case Global.DOWN_RIGHT: // go down-right
//                if(act.screenEdgeCheck("up") || act.screenEdgeCheck("right")){
//                    if(!act.screenEdgeCheck("up")){ moveFourWay(1); }
//                    if(!act.screenEdgeCheck("right")){ moveFourWay(7); }
//                }else{
//                    this.offset(this.x += Global.UNIT_X / speed, this.y += Global.UNIT_Y / speed);
//                }
//                if(!view.screenEdgeCheck("up")){
//                    this.view.move(this.dir);
//                }
//                break;
//        }
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
