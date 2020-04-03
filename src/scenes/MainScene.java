/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scenes;

import controllers.SceneController;
import gameobj.Actor;
import gameobj.Ammo;
import gameobj.GameObject;
import gameobj.Map;
import gameobj.Maps;
//import gameobj.TestObj;
import gameobj.View;
import java.awt.Graphics;
//import java.awt.event.KeyEvent;
import util.Delay;
import util.Global;
import util.CommandSolver;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 *
 * @author Cloud-Razer
 */
public class MainScene extends Scene {

    Actor actor;
    private ArrayList<Ammo> ammunition;
//    TestObj testobj;
    Map mapLeftUp;
    Map mapRightUp;
    Map mapLeftDown;
    Map mapRightDown;
    Delay delay;
    Delay changeSceneDelay;
    int mapLength;
    Maps maps;
    View view;
    boolean actorEdgeTouched;
    boolean viewEdgeTouched;
    LinkedList<GameObject> allObjects;

    public MainScene(SceneController sceneController) {
        super(sceneController);
        this.mapLength = (int) Math.sqrt(Global.MAP_QTY); // 全地圖的地圖邊長為總數開根號
        this.maps = new Maps(0f, 0f, this.mapLength * Global.MAP_WIDTH, this.mapLength * Global.MAP_HEIGHT, mapLength * Global.MAP_WIDTH, this.mapLength * Global.MAP_HEIGHT);
        Global.log("debug map_w:" + Global.MAP_WIDTH); // 這邊不做 debug log 則改動 Global 的數值也沒有辦法改變........ NetBeans 的 bug
        Global.log("debug map_h:" + Global.MAP_HEIGHT); // 這邊不做 debug log 則改動 Global 的數值也沒有辦法改變........ NetBeans 的 bug
        this.allObjects = new LinkedList<GameObject>();
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
                            this.maps.add(new Map(Global.BACKGROUND_1, (float) map_x * y, (float) map_y * x, width, height));
                            break;
                        case 1:
                            this.maps.add(new Map(Global.BACKGROUND_2, (float) map_x * y, (float) map_y * x, width, height));
                            break;
                        case 2:
                            this.maps.add(new Map(Global.BACKGROUND_3, (float) map_x * y, (float) map_y * x, width, height));
                            break;
                    }
                }
            }
//            //  設定每張地圖的鄰居地圖是誰 //TODO delete
            for (int i = 0; i < this.maps.getMaps().size(); i++) {
                Global.log("" + i);
                this.allObjects.add(this.maps.get(i));
            }
            // 設定當前要 paint 出來的四張地圖，目前預設視窗最多會出現就 4 張地圖
            // 預設為設定左上這張地圖，並將玩家設定至此地圖
//            this.mapLeftUp = this.maps.get(0);
//            this.mapRightUp = this.mapLeftUp.getRightMap();
//            this.mapLeftDown = this.mapLeftUp.getDownMap();
//            this.mapRightDown = this.mapLeftDown.getRightMap();
//            this.viewMaps[0] = this.mapLeftUp;
//            this.viewMaps[1] = this.mapRightUp;
//            this.viewMaps[2] = this.mapLeftDown;
//            this.viewMaps[3] = this.mapRightDown;
//            this.viewMaps[4] = this.mapRightUp.getRightMap(); // DEBUG
//            this.viewMaps[5] = this.mapRightDown.getRightMap(); // DEBUG
//            Global.mapEdgeUp = (int) (this.maps.get(0).getGraph().top());
//            Global.mapEdgeDown = (int) (this.maps.get(this.maps.getMaps().size() - 1).getGraph().bottom());
//            Global.mapEdgeLeft = (int) (this.maps.get(0).getGraph().left());
//            Global.mapEdgeRight = (int) (this.maps.get(this.maps.getMaps().size() - 1).getGraph().right());
//            Global.log("DEBUG2 maps.get(0).getGraph().top():" + this.maps.get(0).getGraph().top());
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
//        this.ammunition = new ArrayList();
        this.actor = new Actor("rect", (float) Global.DEFAULT_ACTOR_X, (float) Global.DEFAULT_ACTOR_Y, 60, Global.ACTOR1);
        this.view = new View(60, Global.VIEW_WIDTH, Global.VIEW_HEIGHT, this.actor);
        settingMaps(Global.MAP_WIDTH, Global.MAP_HEIGHT);
        this.allObjects.add(this.actor);
    }

    private void allMapsUpdate() {
        this.maps.update();
        Global.mapEdgeUp = (int) (this.maps.get(0).getGraph().top());
        Global.mapEdgeDown = (int) (this.maps.get(this.maps.getMaps().size() - 1).getGraph().bottom());
        Global.mapEdgeLeft = (int) (this.maps.get(0).getGraph().left());
        Global.mapEdgeRight = (int) (this.maps.get(this.maps.getMaps().size() - 1).getGraph().right());
    }

    @Override
    public void sceneUpdate() {
        this.view.update();
        for(int i = 0; i < this.allObjects.size(); i++){
            this.allObjects.get(i).update();
            if(this.view.isCollision(this.allObjects.get(i))){
                if (!(this.view.stillSeeing(this.allObjects.get(i)))){
                    this.view.saw(this.allObjects.get(i));
                }
            }else{
                this.view.removeSeen(this.allObjects.get(i));
            }
        }
        allMapsUpdate();
        Global.log("allObjects.size(): " + this.allObjects.size());
        Global.log("this.view.getSaw().size(): " + this.view.getSaw().size());
//        if (Global.mouseState == 1) {
//            this.ammunition.add(new Ammo("circle", this.actor.centerX() - Global.UNIT_X / 4, this.actor.centerY() - Global.UNIT_Y / 4, 60, Global.BULLET));
//        }
//        for (int i = 0; i < this.ammunition.size(); i++) {
//            this.ammunition.get(i).update();
//        }
    }

    @Override
    public void sceneEnd() {
        Global.log("main scene end");
    }

    @Override
    public void paint(Graphics g) {
        this.view.paint(g); // 只有在 view 裡面的要畫出來
//        for (int i = 0; i < this.ammunition.size(); i++) { // 這部分之後要用加進 view.sawObjects 的方式做
//            this.ammunition.get(i).paint(g);
//        }
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
            actorMoveRule(commandCode);
        }

        @Override
        public void keyReleased(int commandCode, long trigTime) {
            stopRule(commandCode);
        }

        private void setDirAndPressedStatus(GameObject obj, int dir, boolean status) {
            obj.setStand(false);
            obj.setDir(dir);
            obj.setMovementPressedStatus(dir, status);
        }
        
        private void actorMoveRule(int commandCode) { // 當角色的視野沒碰到牆壁時移動邏輯
            actor.setStand(false);
            switch (commandCode) {
                case Global.UP:
                    if (!(actor.getCollider().top() < Global.mapEdgeUp)) {
                        Global.log("actor move");
                        setDirAndPressedStatus(actor, Global.UP, true);
                    } else {
                        stopRule(commandCode, actor);
                    }
                    break;
                case Global.DOWN:
                    if (!(actor.getCollider().bottom() > Global.mapEdgeDown)) {
                        Global.log("actor move");
                        setDirAndPressedStatus(actor, Global.DOWN, true);
                    } else {
                        stopRule(commandCode, actor);
                    }
                    break;
                case Global.LEFT:
                    if (!(actor.getCollider().left() < Global.mapEdgeLeft)) {
                        Global.log("actor move");
                        setDirAndPressedStatus(actor, Global.LEFT, true);
                    } else {
                        stopRule(commandCode, actor);
                    }
                    break;
                case Global.RIGHT:
                    if (!(actor.getCollider().right() > Global.mapEdgeRight)) {
                        Global.log("actor move");
                        setDirAndPressedStatus(actor, Global.RIGHT, true);
                    } else {
                        stopRule(commandCode, actor);
                    }
                    break;
            }
        } // 當角色的視野沒碰到牆壁時移動邏輯

        // 地圖不應該動 XD
        private void mapMoveRule(int commandCode) { // 當角色的視野碰到牆壁時移動邏輯
            switch (commandCode) {
                case Global.UP:
                    if (!(view.getCollider().top() < Global.mapEdgeUp)) {
                        if (actor.getCenterY() > view.getCenterY()) {
                            setDirAndPressedStatus(actor, Global.UP, true);
                        } else {
                            stopRule(commandCode, actor);
                            setDirAndPressedStatus(maps, Global.UP, true);
                        }
                    } else {
                        stopRule(commandCode, maps);
                        if (!(actor.getCollider().top() < Global.mapEdgeUp)) {
                            setDirAndPressedStatus(actor, Global.UP, true);
                        } else {
                            stopRule(commandCode);
                        }
                    }
                    break;
                case Global.DOWN:
                    if (!(view.getCollider().bottom() > Global.mapEdgeDown)) {
                        if (actor.getCenterY() < view.getCenterY()) {
                            setDirAndPressedStatus(actor, Global.DOWN, true);
                        } else {
                            stopRule(commandCode, actor);
                            setDirAndPressedStatus(maps, Global.DOWN, true);
                        }
                    } else {
                        stopRule(commandCode, maps);
                        if (!(actor.getCollider().bottom() > Global.mapEdgeDown)) {
                            setDirAndPressedStatus(actor, Global.DOWN, true);
                        } else {
                            stopRule(commandCode);
                        }
                    }
                    break;
                case Global.LEFT:
                    if (!(view.getCollider().left() < Global.mapEdgeLeft)) {
                        if (actor.getCenterX() > view.getCenterX()) {
                            setDirAndPressedStatus(actor, Global.LEFT, true);
                        } else {
                            stopRule(commandCode, actor);
                            setDirAndPressedStatus(maps, Global.LEFT, true);
                        }
                    } else {
                        stopRule(commandCode, maps);
                        if (!(actor.getCollider().left() < Global.mapEdgeLeft)) {
                            setDirAndPressedStatus(actor, Global.LEFT, true);
                        } else {
                            stopRule(commandCode);
                        }
                    }
                    break;
                case Global.RIGHT:
                    if (!(view.getCollider().right() > Global.mapEdgeRight)) {
                        if (actor.getCenterX() < view.getCenterX()) {
                            setDirAndPressedStatus(actor, Global.RIGHT, true);
                        } else {
                            stopRule(commandCode, actor);
                            setDirAndPressedStatus(maps, Global.RIGHT, true);
                        }
                    } else {
                        stopRule(commandCode, maps);
                        if (!(actor.getCollider().right() > Global.mapEdgeRight)) {
                            setDirAndPressedStatus(actor, Global.RIGHT, true);
                        } else {
                            stopRule(commandCode);
                        }
                    }
                    break;
            }
        } // 當角色的視野碰到牆壁時移動邏輯

        private void stopRule(int commandCode, GameObject target) {
            target.setStand(true);
            switch (commandCode) {
                case Global.UP:
                    setDirAndPressedStatus(target, Global.UP, false);
                    break;
                case Global.DOWN:
                    setDirAndPressedStatus(target, Global.DOWN, false);
                    break;
                case Global.LEFT:
                    setDirAndPressedStatus(target, Global.LEFT, false);
                    break;
                case Global.RIGHT:
                    setDirAndPressedStatus(target, Global.RIGHT, false);
                    break;
            }
        }

        private void stopRule(int commandCode) {
            actor.setStand(true);
            view.setStand(true);
            maps.setStand(true);
            switch (commandCode) {
                case Global.UP:
                    setDirAndPressedStatus(actor, Global.UP, false);
                    setDirAndPressedStatus(view, Global.UP, false);
                    setDirAndPressedStatus(maps, Global.UP, false);
                    break;
                case Global.DOWN:
                    setDirAndPressedStatus(actor, Global.DOWN, false);
                    setDirAndPressedStatus(view, Global.DOWN, false);
                    setDirAndPressedStatus(maps, Global.DOWN, false);
                    break;
                case Global.LEFT:
                    setDirAndPressedStatus(actor, Global.LEFT, false);
                    setDirAndPressedStatus(view, Global.LEFT, false);
                    setDirAndPressedStatus(maps, Global.LEFT, false);
                    break;
                case Global.RIGHT:
                    setDirAndPressedStatus(actor, Global.RIGHT, false);
                    setDirAndPressedStatus(view, Global.RIGHT, false);
                    setDirAndPressedStatus(maps, Global.RIGHT, false);
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
