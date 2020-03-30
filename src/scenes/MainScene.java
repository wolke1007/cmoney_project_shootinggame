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
import gameobj.TestObj;
import gameobj.View;
import java.awt.Graphics;
//import java.awt.event.KeyEvent;
import util.Delay;
import util.Global;
import util.CommandSolver;
import java.awt.event.MouseEvent;

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
    Map[][] allMaps;
    View view;
    boolean actorEdgeTouched;
    boolean viewEdgeTouched;

    public MainScene(SceneController sceneController) {
        super(sceneController);
        this.viewMaps = new Map[6]; // 預計一個畫面最多看見 4 張地圖
        this.mapLength = (int) Math.sqrt(Global.MAP_QTY); // 全地圖的地圖邊長為總數開根號
        Global.log("this.mapLength: " + this.mapLength);
        this.allMaps = new Map[this.mapLength][this.mapLength];
    }

    private void settingMaps(int width, int height) {
        // 這邊希望地圖數能為 3x3 or 4x4 這樣的形式
        int map_x = width;
        int map_y = height;
        if (this.mapLength % 1 == 0 && this.mapLength >= 3) {
            Global.log("地圖數量: " + this.mapLength + "x" + this.mapLength);
            // 不同位置的地圖使用不同的圖片，之後需做成從地圖池中取隨機 pattern 來用
            for (int x = 0; x < this.allMaps.length; x++) {
                for (int y = 0; y < this.allMaps[x].length; y++) {
                    int whichMap = x;
                    switch (whichMap) {
                        case 0:
                            this.allMaps[x][y] = new Map(Global.BACKGROUND_1, map_x * y, map_y * x, width, height);
                            break;
                        case 1:
                            this.allMaps[x][y] = new Map(Global.BACKGROUND_2, map_x * y, map_y * x, width, height);
                            break;
                        case 2:
                            this.allMaps[x][y] = new Map(Global.BACKGROUND_3, map_x * y, map_y * x, width, height);
                            break;
                    }
                }
            }
            //  設定每張地圖的鄰居地圖是誰
            for (int x = 0; x < this.allMaps.length; x++) {
                for (int y = 0; y < this.allMaps[x].length; y++) {
                    if (x == 0) {
                        this.allMaps[x][y].setUpMap(null);
                    } else {
                        this.allMaps[x][y].setUpMap(this.allMaps[x - 1][y]);
                    }
                    if (x == this.allMaps[x].length - 1) {
                        this.allMaps[x][y].setDownMap(null);
                    } else {
                        this.allMaps[x][y].setDownMap(this.allMaps[x + 1][y]);
                    }
                    if (y == 0) {
                        this.allMaps[x][y].setLeftMap(null);
                    } else {
                        this.allMaps[x][y].setLeftMap(allMaps[x][y - 1]);
                    }
                    if (y == this.allMaps[x].length - 1) {
                        this.allMaps[x][y].setRightMap(null);
                    } else {
                        this.allMaps[x][y].setRightMap(this.allMaps[x][y + 1]);
                    }
                }
            }
            // 設定當前要 paint 出來的四張地圖，目前預設視窗最多會出現就 4 張地圖
            // 預設為設定左上這張地圖，並將玩家設定至此地圖
            this.mapLeftUp = this.allMaps[0][0];
            this.mapRightUp = this.mapLeftUp.getRightMap();
            this.mapLeftDown = this.mapLeftUp.getDownMap();
            this.mapRightDown = this.mapLeftDown.getRightMap();
            this.viewMaps[0] = this.mapLeftUp;
            this.viewMaps[1] = this.mapRightUp;
            this.viewMaps[2] = this.mapLeftDown;
            this.viewMaps[3] = this.mapRightDown;
            this.viewMaps[4] = this.mapRightUp.getRightMap(); // DEBUG
            this.viewMaps[5] = this.mapRightDown.getRightMap(); // DEBUG
            Global.mapEdgeUp = this.allMaps[0][0].getY();
            Global.mapEdgeDown = this.allMaps[this.allMaps.length - 1][0].getY() + Global.MAP_HEIGHT;
            Global.mapEdgeLeft = this.allMaps[0][0].getX();
            Global.mapEdgeRight = this.allMaps[0][this.allMaps.length - 1].getX() + Global.MAP_WIDTH;
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
        Global.log("actor_x: " + Global.actor_x);
        Global.log("actor_y: " + Global.actor_y);
        this.actor = new Actor(Global.STEPS_WALK_NORMAL, Global.actor_x, Global.actor_y, 60, Global.ACTOR, this.viewMaps);
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

    @Override
    public void sceneUpdate() {
        this.actor.update();
        this.view.update();
        allMapsUpdate();
        this.viewEdgeTouched = view.getCollider().screenEdgeCheck();
        this.actorEdgeTouched = actor.getCollider().screenEdgeCheck();
    }

    private void allMapsUpdate() {
        for (int i = 0; i < allMaps.length; i++) {
            for (int j = 0; j < allMaps[i].length; j++) {
                allMaps[i][j].update();
            }
        }
        Global.mapEdgeUp = this.allMaps[0][0].getY();
        Global.mapEdgeDown = this.allMaps[this.allMaps.length - 1][0].getY() + Global.MAP_HEIGHT;
        Global.mapEdgeLeft = this.allMaps[0][0].getX();
        Global.mapEdgeRight = this.allMaps[0][this.allMaps.length - 1].getX() + Global.MAP_WIDTH;
    }

    @Override
    public void sceneEnd() {
        Global.log("main scene end");
    }

    @Override
    public void paint(Graphics g) {
        for (int i = 0; i < this.viewMaps.length; i++) {
            if (this.viewMaps[i] != null) {
                this.viewMaps[i].paint(g);
            }
        }
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
                //  如果 view 沒有碰到地圖邊際
                Global.log("map move");
                mapMoveRule(commandCode);
            } else {
                //  如果 view 有碰到地圖邊際
                if (!actorEdgeTouched) {
                    //  但 actor 沒碰到地圖邊際
                    Global.log("actor move");
                    actorMoveRule(commandCode);
                }

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
            for (int i = 0; i < allMaps.length; i++) {
                for (int j = 0; j < allMaps[i].length; j++) {
                    setDirAndPressedStatus(allMaps[i][j], dir, status);
                }
            }
        }

        private void viewMoveRule(int commandCode) { // 當角色的視野沒碰到牆壁時移動邏輯
//            if (view.getCollider().sideScreenEdgeCheck("left") && actor.getCenterX() < view.getCenterX()){
//                view.setStand(true);
//            }
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
//            if (view.getCollider().sideScreenEdgeCheck("left") && actor.getCenterX() < view.getCenterX()){
//                view.setStand(true);
//            }
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
//            view.setStand(false);
//            actor.setStand(false);
            for (int i = 0; i < allMaps.length; i++) {
                for (int j = 0; j < allMaps[i].length; j++) {
                    allMaps[i][j].setStand(false);
                }
            }
            switch (commandCode) {
                case Global.UP:
                    allMapSetDirAndPressedStatus(Global.UP, true);
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
            for (int i = 0; i < allMaps.length; i++) {
                for (int j = 0; j < allMaps[i].length; j++) {
                    allMaps[i][j].setStand(true);
                }
            }
            switch (commandCode) {
                case Global.UP:
                    setDirAndPressedStatus(actor, Global.UP, false);
                    setDirAndPressedStatus(view, Global.UP, false);
                    for (int i = 0; i < allMaps.length; i++) {
                        for (int j = 0; j < allMaps[i].length; j++) {
                            setDirAndPressedStatus(allMaps[i][j], Global.UP, false);
                        }
                    }
                    break;
                case Global.DOWN:
                    setDirAndPressedStatus(actor, Global.DOWN, false);
                    setDirAndPressedStatus(view, Global.DOWN, false);
                    for (int i = 0; i < allMaps.length; i++) {
                        for (int j = 0; j < allMaps[i].length; j++) {
                            setDirAndPressedStatus(allMaps[i][j], Global.DOWN, false);
                        }
                    }
                    break;
                case Global.LEFT:
                    setDirAndPressedStatus(actor, Global.LEFT, false);
                    setDirAndPressedStatus(view, Global.LEFT, false);
                    for (int i = 0; i < allMaps.length; i++) {
                        for (int j = 0; j < allMaps[i].length; j++) {
                            setDirAndPressedStatus(allMaps[i][j], Global.LEFT, false);
                        }
                    }
                    break;
                case Global.RIGHT:
                    setDirAndPressedStatus(actor, Global.RIGHT, false);
                    setDirAndPressedStatus(view, Global.RIGHT, false);
                    for (int i = 0; i < allMaps.length; i++) {
                        for (int j = 0; j < allMaps[i].length; j++) {
                            setDirAndPressedStatus(allMaps[i][j], Global.RIGHT, false);
                        }
                    }
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
