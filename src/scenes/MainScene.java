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
    View view;
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
        if (mapLength % 1d == 0d && mapLength >= 3d) {
            Global.log("地圖數量: " + (int) mapLength + "x" + (int) mapLength);
            // 不同位置的地圖使用不同的圖片
            for (int x = 0; x < allMaps.length; x++) {
                for (int y = 0; y < allMaps[x].length; y++) {
                    int whichMap = x;
                    switch(whichMap){
                        case 0:
                            allMaps[x][y] = new Map(Global.BACKGROUND_1, map_x * y, map_y * x, map_x, map_y);
                            break;
                        case 1:
                            allMaps[x][y] = new Map(Global.BACKGROUND_2, map_x * y, map_y * x, map_x, map_y);
                            break;
                        case 2:
                            allMaps[x][y] = new Map(Global.BACKGROUND_3, map_x * y, map_y * x, map_x, map_y);
                            break;
                    }
                    
                }
            }
            //  設定每張地圖的鄰居地圖是誰
            for (int x = 0; x < allMaps.length; x++) {
                for (int y = 0; y < allMaps[x].length; y++) {
                    if (x == 0) {
                        allMaps[x][y].setUpMap(null);
                    } else {
                        allMaps[x][y].setUpMap(allMaps[x - 1][y]);
                    }
                    if (x == allMaps[x].length - 1) {
                        allMaps[x][y].setDownMap(null);
                    } else {
                        allMaps[x][y].setDownMap(allMaps[x + 1][y]);
                    }
                    if (y == 0) {
                        allMaps[x][y].setLeftMap(null);
                    } else {
                        allMaps[x][y].setLeftMap(allMaps[x][y - 1]);
                    }
                    if (y == allMaps[x].length - 1) {
                        allMaps[x][y].setRightMap(null);
                    } else {
                        allMaps[x][y].setRightMap(allMaps[x][y + 1]);
                    }
                }
            }
            // 設定當前要 paint 出來的四張地圖，目前預設視窗最多會出現就 4 張地圖
            this.map_LU = allMaps[0][0];
            this.map_RU = map_LU.getRightMap();
            this.map_LD = map_LU.getDownMap();
            this.map_RD = map_LD.getRightMap();
            viewMaps[0] = map_LU;
            viewMaps[1] = map_RU;
            viewMaps[2] = map_LD;
            viewMaps[3] = map_RD;
        } else {
            Global.log("地圖數量: " + mapLength);
            Global.log("地圖不符合規定 預期為可被開根號的數且大於 9，如 9 16");
        }
    }
    
    @Override
    public void sceneBegin() {
        view = new View(Global.ACTOR_X - (Global.VIEW_SIZE / 2 - 16),
                        Global.ACTOR_Y - (Global.VIEW_SIZE / 2 - 16),
                        Global.VIEW_SIZE, Global.VIEW_SIZE,
                        Global.VIEW_SIZE, Global.VIEW_SIZE);
        actor = new Actor(1, Global.STEPS_WALK_NORMAL, Global.ACTOR_X, Global.ACTOR_Y, view);
        delay = new Delay(1);
        delay.start();
//        changeSceneDelay = new Delay(180);
//        changeSceneDelay.start();
    }

    @Override
    public void sceneUpdate() {
        if (delay.isTrig()) {
            actor.update();
            view.update();
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
        for (int i = 0; i < viewMaps.length; i++) {
            if (viewMaps[i] != null) {
                viewMaps[i].paint(g);
            }
        }
        actor.paint(g);
        view.paint(g);
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
            System.out.println("commandCode:" + commandCode);
            actor.setStand(false);
            switch (commandCode) {
                case 0:
                    actor.setDir(Global.UP);
                    break;
                case 1:
                    actor.setDir(Global.DOWN);
                    break;
                case 2:
                    actor.setDir(Global.LEFT);
                    break;
                case 3:
                    actor.setDir(Global.RIGHT);
                    break;
            }
        }

        @Override
        public void keyReleased(int commandCode, long trigTime) {
            actor.setStand(true);
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
