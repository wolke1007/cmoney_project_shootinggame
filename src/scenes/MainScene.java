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
import java.awt.event.KeyEvent;
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
    Map map_LeftUp;
    Map map_RightUp;
    Map map_LeftDown;
    Map map_RightDown;
    Map[] viewMaps;
    Delay delay;
    Delay changeSceneDelay;
    int mapLength;
    Map[][] allMaps;
    View view;

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
            this.map_LeftUp = this.allMaps[0][0];
            this.map_RightUp = this.map_LeftUp.getRightMap();
            this.map_LeftDown = this.map_LeftUp.getDownMap();
            this.map_RightDown = this.map_LeftDown.getRightMap();
            this.viewMaps[0] = this.map_LeftUp;
            this.viewMaps[1] = this.map_RightUp;
            this.viewMaps[2] = this.map_LeftDown;
            this.viewMaps[3] = this.map_RightDown;
            this.viewMaps[4] = this.map_RightUp.getRightMap(); // DEBUG
            this.viewMaps[5] = this.map_RightDown.getRightMap(); // DEBUG

//            Global.ACTOR_X = this.allMaps[0][0].getCenterX();
//            Global.ACTOR_Y = this.allMaps[0][0].getCenterY();
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
        this.actor = new Actor(Global.STEPS_WALK_NORMAL, Global.ACTOR_X, Global.ACTOR_Y, 60, Global.ACTOR, this.viewMaps);
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
        // 平常是地圖在移動，而當地圖移動到最後邊際碰到了 View 的時候則改成動角色
        if (this.delay.isTrig() && !this.view.isMeetMapEdge()) {
//            Global.log("this.view.isMeetMapEdge()" + this.view.isMeetMapEdge());
            this.actor.update();
            this.view.update();
        } else {
            allMapsUpdate();
        }
    }

    private void allMapsUpdate() {
        for (int i = 0; i < allMaps.length; i++) {
            for (int j = 0; j < allMaps[i].length; j++) {
                allMaps[i][j].update();
            }
        }
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
            actor.setStand(false);
            view.setStand(false);
            switch (commandCode) {
                case Global.UP:
                    setDirAndPressedStatus(actor, Global.UP, true);
                    setDirAndPressedStatus(view, Global.UP, true);
                    break;
                case Global.DOWN:
                    setDirAndPressedStatus(actor, Global.DOWN, true);
                    setDirAndPressedStatus(view, Global.DOWN, true);
                    break;
                case Global.LEFT:
                    setDirAndPressedStatus(actor, Global.LEFT, true);
                    setDirAndPressedStatus(view, Global.LEFT, true);
                    break;
                case Global.RIGHT:
                    setDirAndPressedStatus(actor, Global.RIGHT, true);
                    setDirAndPressedStatus(view, Global.RIGHT, true);
                    break;
            }
        }

        @Override
        public void keyReleased(int commandCode, long trigTime) {
            actor.setStand(true);
            view.setStand(true);
            switch (commandCode) {
                case Global.UP:
                    setDirAndPressedStatus(actor, Global.UP, false);
                    setDirAndPressedStatus(view, Global.UP, false);
                    break;
                case Global.DOWN:
                    setDirAndPressedStatus(actor, Global.DOWN, false);
                    setDirAndPressedStatus(view, Global.DOWN, false);
                    break;
                case Global.LEFT:
                    setDirAndPressedStatus(actor, Global.LEFT, false);
                    setDirAndPressedStatus(view, Global.LEFT, false);
                    break;
                case Global.RIGHT:
                    setDirAndPressedStatus(actor, Global.RIGHT, false);
                    setDirAndPressedStatus(view, Global.RIGHT, false);
                    break;
            }
        }
        
        private void setDirAndPressedStatus(GameObject obj, int dir, boolean status){
            obj.setDir(dir);
            obj.setMovementPressedStatus(dir, status);
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
