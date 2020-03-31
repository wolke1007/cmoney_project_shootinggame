/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scenes;

import controllers.SceneController;
import gameobj.Actor;
import gameobj.GameObject;
import gameobj.Map;
import gameobj.Maps;
import gameobj.TestObj;
import gameobj.View;
import java.awt.Graphics;
//import java.awt.event.KeyEvent;
import util.Delay;
import util.Global;
import util.CommandSolver;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 *
 * @author Cloud-Razer
 */
public class MainScene extends Scene {

    Actor actor;
    TestObj testobj;
    Map mapLeftUp;
    Map mapRightUp;
    Map mapLeftDown;
    Map mapRightDown;
    Map[] viewMaps;
    Delay delay;
    Delay changeSceneDelay;
    int mapLength;
    Maps maps;
    Map[][] allMaps;
    View view;
    boolean actorEdgeTouched;
    boolean viewEdgeTouched;

    public MainScene(SceneController sceneController) {
        super(sceneController);
        this.viewMaps = new Map[6]; // 預計一個畫面最多看見 4 張地圖
        this.mapLength = (int) Math.sqrt(Global.MAP_QTY); // 全地圖的地圖邊長為總數開根號
        this.maps = new Maps(0, 0, this.mapLength * Global.MAP_WIDTH, this.mapLength * Global.MAP_HEIGHT, mapLength * Global.MAP_WIDTH, this.mapLength * Global.MAP_HEIGHT);
Global.log("debug map_w:" + Global.MAP_WIDTH); // 這邊不做 debug log 則改動 Global 的數值也沒有辦法改變........ NetBeans 的 bug
        Global.log("debug map_h:" + Global.MAP_HEIGHT); // 這邊不做 debug log 則改動 Global 的數值也沒有辦法改變........ NetBeans 的 bug
//        Global.log("this.mapLength: " + this.mapLength);
//        this.allMaps = new Map[this.mapLength][this.mapLength];
    }

    private void settingMaps(int width, int height) {
        // 這邊希望地圖數能為 3x3 or 4x4 這樣的形式
        int map_x = width;
        int map_y = height;
        if (this.mapLength % 1 == 0 && this.mapLength >= 3) {
            Global.log("地圖數量: " + this.mapLength + "x" + this.mapLength);
            // 不同位置的地圖使用不同的圖片，之後需做成從地圖池中取隨機 pattern 來用
            for (int x = 0; x < this.mapLength; x++) {
                for (int y = 0; y < this.mapLength; y++) {
                    int whichMap = x;
                    switch (whichMap) {
                        case 0:
                            maps.add(new Map(Global.BACKGROUND_1, map_x * y, map_y * x, width, height));
                            break;
                        case 1:
                            maps.add(new Map(Global.BACKGROUND_2, map_x * y, map_y * x, width, height));
                            break;
                        case 2:
                            maps.add(new Map(Global.BACKGROUND_3, map_x * y, map_y * x, width, height));
                            break;
                    }
                }
            }
            //  設定每張地圖的鄰居地圖是誰 //TODO exception here
            for (int i = 0; i < this.maps.getMaps().size(); i++) {
                Global.log("" + i);
                maps.get(i).setUpMap(maps.get(i - this.mapLength));
                maps.get(i).setDownMap(maps.get(i + this.mapLength));
                maps.get(i).setLeftMap(maps.get(i - 1));
                maps.get(i).setRightMap(maps.get(i + 1));
            }
            // 設定當前要 paint 出來的四張地圖，目前預設視窗最多會出現就 4 張地圖
            // 預設為設定左上這張地圖，並將玩家設定至此地圖
            this.mapLeftUp = this.maps.get(0);
            this.mapRightUp = this.mapLeftUp.getRightMap();
            this.mapLeftDown = this.mapLeftUp.getDownMap();
            this.mapRightDown = this.mapLeftDown.getRightMap();
//            this.viewMaps[0] = this.mapLeftUp;
//            this.viewMaps[1] = this.mapRightUp;
//            this.viewMaps[2] = this.mapLeftDown;
//            this.viewMaps[3] = this.mapRightDown;
//            this.viewMaps[4] = this.mapRightUp.getRightMap(); // DEBUG
//            this.viewMaps[5] = this.mapRightDown.getRightMap(); // DEBUG
            Global.mapEdgeUp = this.maps.get(0).getY();
            Global.mapEdgeDown = this.maps.get(this.maps.getMaps().size() - 1).getY() + Global.MAP_HEIGHT;
            Global.mapEdgeLeft = this.maps.get(0).getX();
            Global.mapEdgeRight = this.maps.get(this.maps.getMaps().size() - 1).getX() + Global.MAP_WIDTH;
            
//            Global.log(""+Global.mapEdgeUp);
//            Global.log(""+Global.mapEdgeDown);
//            Global.log(""+Global.mapEdgeLeft);
//            Global.log(""+Global.mapEdgeRight);
//            Global.actor_x = this.allMaps[0][0].getCenterX();
//            Global.actor_y = this.allMaps[0][0].getCenterY();
//            // 關掉地圖
//            this.viewMaps[0] = null;
//            this.viewMaps[1] = null;
//            this.viewMaps[2] = null;
//            this.viewMaps[3] = null;
        } else {
            Global.log("地圖不符合規定 預期為可被開根號的數且大於 9，如 9 16");
        }
    }

    @Override
    public void sceneBegin() {
        Global.log("actor_x: " + Global.DEFAULT_ACTOR_X);
        Global.log("actor_y: " + Global.DEFAULT_ACTOR_Y);
        this.actor = new Actor(Global.STEPS_WALK_NORMAL, Global.DEFAULT_ACTOR_X, Global.DEFAULT_ACTOR_Y, 60, Global.ACTOR, this.viewMaps);
        Global.log("debug w:" + Global.VIEW_WIDTH); // 這邊不做 debug log 則改動 Global 的數值也沒有辦法改變........ NetBeans 的 bug
        Global.log("debug h:" + Global.VIEW_HEIGHT); // 這邊不做 debug log 則改動 Global 的數值也沒有辦法改變........ NetBeans 的 bug
        Global.log("debug map_w:" + Global.MAP_WIDTH); // 這邊不做 debug log 則改動 Global 的數值也沒有辦法改變........ NetBeans 的 bug
        Global.log("debug map_h:" + Global.MAP_HEIGHT); // 這邊不做 debug log 則改動 Global 的數值也沒有辦法改變........ NetBeans 的 bug
        this.view = new View(this.actor.getX() - (Global.VIEW_WIDTH / 2 - Global.UNIT_X / 2),
                this.actor.getY() + (Global.UNIT_Y / 2) - (Global.VIEW_HEIGHT / 2),
                60, Global.VIEW_WIDTH, Global.VIEW_HEIGHT);
        settingMaps(Global.MAP_WIDTH, Global.MAP_HEIGHT);
        this.delay = new Delay(1);
        this.delay.start();
//        changeSceneDelay = new Delay(180);
//        changeSceneDelay.start();
    }

    private void allMapsUpdate() {
        this.maps.update();
        Global.mapEdgeUp = (int)(this.maps.get(0).getGraph().top());
        Global.mapEdgeDown = (int)(this.maps.get(this.maps.getMaps().size() - 1).getGraph().top());
        Global.mapEdgeLeft = (int)(this.maps.get(0).getGraph().left());
        Global.mapEdgeRight = (int)(this.maps.get(this.maps.getMaps().size() - 1).getGraph().right());
    }
    
    @Override
    public void sceneUpdate() {
        this.actor.update();
        this.view.update();
        allMapsUpdate();
    }

    @Override
    public void sceneEnd() {
        Global.log("main scene end");
    }

    @Override
    public void paint(Graphics g) {
        this.maps.paint(g);
        this.actor.paint(g);
        this.view.paint(g);
    }

    @Override
    public CommandSolver.KeyListener getKeyListener() {
        return new MyKeyListener();
    }

    @Override
    public CommandSolver.MouseCommandListener getMouseListener() {
        return new MyMouseListener();
    }

    public class MyKeyListener implements CommandSolver.KeyListener {

        @Override
        public void keyPressed(int commandCode, long trigTime) {
            Global.log("viewEdgeTouched: " + viewEdgeTouched);
            Global.log("actorEdgeTouched: " + actorEdgeTouched);
            // TODO 如果超出邊界要讓角色退回去
            if (!viewEdgeTouched) {
                //  如果 view 沒有碰到地圖邊際 // 這部分目前沒問題
                Global.log("map move");
                mapMoveRule(commandCode);
            } else {
                //  如果 view 有碰到地圖邊際 // TODO 目前 map 會繼續移動
                Global.log("view and actor move");
                // 手動讓地圖停下來
                maps.setStand(true);
                actorMoveRule(commandCode);
            }

//                Global.log("move 2");
//                moveRule_2(commandCode);
        }

        @Override
        public void keyReleased(int commandCode, long trigTime) {
            stopRule(commandCode);
        }

        private void setDirAndPressedStatus(GameObject obj, int dir, boolean status) {
            obj.setDir(dir);
            obj.setMovementPressedStatus(dir, status);
        }

        private void allMapSetDirAndPressedStatus(int dir, boolean status) {
            maps.setDir(dir);
            maps.setMovementPressedStatus(dir, status);
        }

        private void viewMoveRule(int commandCode) { // 當角色的視野沒碰到牆壁時移動邏輯
            view.setStand(true);
            switch (commandCode) {
                case Global.UP:
                    setDirAndPressedStatus(view, Global.UP, true);
                    break;
                case Global.DOWN:
                    setDirAndPressedStatus(view, Global.DOWN, true);
                    break;
                case Global.LEFT:
                    setDirAndPressedStatus(view, Global.LEFT, true);
                    break;
                case Global.RIGHT:
                    setDirAndPressedStatus(view, Global.RIGHT, true);
                    break;
            }
        } // 當角色的視野沒碰到牆壁時移動邏輯

        private void actorMoveRule(int commandCode) { // 當角色的視野沒碰到牆壁時移動邏輯
            actor.setStand(false);
            switch (commandCode) {
                case Global.UP:
                    setDirAndPressedStatus(actor, Global.UP, true);
                    break;
                case Global.DOWN:
                    setDirAndPressedStatus(actor, Global.DOWN, true);
                    break;
                case Global.LEFT:
                    setDirAndPressedStatus(actor, Global.LEFT, true);
                    break;
                case Global.RIGHT:
                    setDirAndPressedStatus(actor, Global.RIGHT, true);
                    break;
            }
        } // 當角色的視野沒碰到牆壁時移動邏輯

        private void mapMoveRule(int commandCode) { // 當角色的視野碰到牆壁時移動邏輯
            //TODO 若 view 碰到牆壁而停止角色繼續前進，但角色如果回頭時 view 必須等角色回到正中心後才能跟著移動
            maps.setStand(false);
            switch (commandCode) {
                case Global.UP:
                    if (!(actor.getX() < Global.mapEdgeUp)) {
                        Global.log("actor.getX()" + actor.getX());
                        Global.log("Global.mapEdgeUp" + Global.mapEdgeUp);
                        allMapSetDirAndPressedStatus(Global.UP, true);
                    }
                    break;
                case Global.DOWN:
                    allMapSetDirAndPressedStatus(Global.DOWN, true);
                    break;
                case Global.LEFT:
                    allMapSetDirAndPressedStatus(Global.LEFT, true);
                    break;
                case Global.RIGHT:
                    allMapSetDirAndPressedStatus(Global.RIGHT, true);
                    break;
            }
        } // 當角色的視野碰到牆壁時移動邏輯

        private void stopRule(int commandCode) {
            actor.setStand(true);
            view.setStand(true);
            maps.setStand(true);
            switch (commandCode) {
                case Global.UP:
                    setDirAndPressedStatus(actor, Global.UP, false);
                    setDirAndPressedStatus(view, Global.UP, false);
                    allMapSetDirAndPressedStatus(Global.UP, false);
                    break;
                case Global.DOWN:
                    setDirAndPressedStatus(actor, Global.DOWN, false);
                    setDirAndPressedStatus(view, Global.DOWN, false);
                    allMapSetDirAndPressedStatus(Global.DOWN, false);
                    break;
                case Global.LEFT:
                    setDirAndPressedStatus(actor, Global.LEFT, false);
                    setDirAndPressedStatus(view, Global.LEFT, false);
                    allMapSetDirAndPressedStatus(Global.LEFT, false);
                    break;
                case Global.RIGHT:
                    setDirAndPressedStatus(actor, Global.RIGHT, false);
                    setDirAndPressedStatus(view, Global.RIGHT, false);
                    allMapSetDirAndPressedStatus(Global.RIGHT, false);
                    break;
            }
        }

        @Override
        public void keyTyped(char c, long trigTime) {
        }

    }

    public static class MyMouseListener implements CommandSolver.MouseCommandListener {

        @Override
        public void mouseTrig(MouseEvent e, CommandSolver.MouseState state, long trigTime) {
//            System.out.println("mouse state:" + state);
        }

    }
}
