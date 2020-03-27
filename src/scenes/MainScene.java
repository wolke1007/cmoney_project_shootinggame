/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scenes;

import controllers.SceneController;
import gameobj.Actor;
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
    Map map_LU;
    Map map_RU;
    Map map_LD;
    Map map_RD;
    Map[] viewMaps = new Map[4];
    Delay delay;
    Delay changeSceneDelay;
    double mapLength = Math.sqrt(Global.MAP_QTY);
    Map[][] allMaps = new Map[(int) mapLength][(int) mapLength];

    public MainScene(SceneController sceneController) {
        super(sceneController);
        settingMaps(300, 300);
    }

    private void settingMaps(int width, int height){
        // 這邊希望地圖數能為 3x3 or 4x4 這樣的形式
        int map_x = width;
        int map_y = height;
        if (this.mapLength % 1d == 0d && this.mapLength >= 3d) {
            Global.log("地圖數量: " + (int) this.mapLength + "x" + (int) this.mapLength);
            // 不同位置的地圖使用不同的圖片
            for (int x = 0; x < this.allMaps.length; x++) {
                for (int y = 0; y < this.allMaps[x].length; y++) {
                    int whichMap = x;
                    switch(whichMap){
                        case 0:
                            this.allMaps[x][y] = new Map(Global.BACKGROUND_1, map_x * y, map_y * x, map_x, map_y);
                            Global.log("x , y " + map_x * y + " " + map_y * x);
                            break;
                        case 1:
                            this.allMaps[x][y] = new Map(Global.BACKGROUND_2, map_x * y, map_y * x, map_x, map_y);
                            Global.log("x , y " + map_x * y + " " + map_y * x);
                            break;
                        case 2:
                            this.allMaps[x][y] = new Map(Global.BACKGROUND_3, map_x * y, map_y * x, map_x, map_y);
                            Global.log("x , y " + map_x * y + " " + map_y * x);
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
            this.map_LU = this.allMaps[0][0];
            this.map_RU = this.map_LU.getRightMap();
            this.map_LD = this.map_LU.getDownMap();
            this.map_RD = this.map_LD.getRightMap();
            this.viewMaps[0] = this.map_LU;
            this.viewMaps[1] = this.map_RU;
            this.viewMaps[2] = this.map_LD;
            this.viewMaps[3] = this.map_RD;
//            // 關掉地圖
//            this.viewMaps[0] = null;
//            this.viewMaps[1] = null;
//            this.viewMaps[2] = null;
//            this.viewMaps[3] = null;
        } else {
            Global.log("地圖數量: " + this.mapLength);
            Global.log("地圖不符合規定 預期為可被開根號的數且大於 9，如 9 16");
        }
    }
    
    @Override
    public void sceneBegin() {
        this.actor = new Actor(Global.STEPS_WALK_NORMAL, Global.ACTOR_X, Global.ACTOR_Y, 59, Global.ACTOR);
        this.delay = new Delay(1);
        this.delay.start();
//        changeSceneDelay = new Delay(180);
//        changeSceneDelay.start();
    }

    @Override
    public void sceneUpdate() {
        if (delay.isTrig()) {
            actor.update();
            for (int i = 0; i < viewMaps.length; i++) {
                if (viewMaps[i] != null) {
                    viewMaps[i].update();
                }
            }
        }
    }

    @Override
    public void sceneEnd() {
        System.out.println("main scene end");
    }

    @Override
    public void paint(Graphics g) {
        for (int i = 0; i < this.viewMaps.length; i++) {
            if (this.viewMaps[i] != null) {
                this.viewMaps[i].paint(g);
            }
        }
        this.actor.paint(g);
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
            switch (commandCode) {
                case Global.UP:
                    actor.setDir(Global.UP);
                    actor.movement().setPressedStatus(Global.UP, true);
                    break;
                case Global.DOWN:
                    actor.setDir(Global.DOWN);
                    actor.movement().setPressedStatus(Global.DOWN, true);
                    break;
                case Global.LEFT:
                    actor.setDir(Global.LEFT);
                    actor.movement().setPressedStatus(Global.LEFT, true);
                    break;
                case Global.RIGHT:
                    actor.setDir(Global.RIGHT);
                    actor.movement().setPressedStatus(Global.RIGHT, true);
                    break;
            }
        }

        @Override
        public void keyReleased(int commandCode, long trigTime) {
            actor.setStand(true);
            switch (commandCode) {
                case Global.UP:
                    actor.setDir(Global.UP);
                    actor.movement().setPressedStatus(Global.UP, false);
                    break;
                case Global.DOWN:
                    actor.setDir(Global.DOWN);
                    actor.movement().setPressedStatus(Global.DOWN, false);
                    break;
                case Global.LEFT:
                    actor.setDir(Global.LEFT);
                    actor.movement().setPressedStatus(Global.LEFT, false);
                    break;
                case Global.RIGHT:
                    actor.setDir(Global.RIGHT);
                    actor.movement().setPressedStatus(Global.RIGHT, false);
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
