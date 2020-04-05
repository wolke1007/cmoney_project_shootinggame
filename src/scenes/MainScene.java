/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scenes;

import controllers.ImagePath;
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
import util.map.MapGenerator;

/**
 *
 * @author Cloud-Razer
 */
public class MainScene extends Scene {

    Actor actor;
    private ArrayList<Ammo> ammo;
    Delay delay;
    Delay changeSceneDelay;
    Maps maps;
    View view;
    boolean actorEdgeTouched;
    boolean viewEdgeTouched;
    LinkedList<GameObject> allObjects;

    public MainScene(SceneController sceneController) {
        super(sceneController);
        this.allObjects = new LinkedList<GameObject>();
    }

    @Override
    public void sceneBegin() {
        this.ammo = new ArrayList();
        this.actor = new Actor("circle", (float) Global.DEFAULT_ACTOR_X, (float) Global.DEFAULT_ACTOR_Y, 60, ImagePath.ACTOR1);
        this.view = new View(60, Global.VIEW_WIDTH, Global.VIEW_HEIGHT, this.actor);
        int mapLength = (int) Math.sqrt(Global.MAP_QTY);
        this.maps = new Maps(0f, 0f, mapLength * Global.MAP_WIDTH, mapLength * Global.MAP_HEIGHT, mapLength * Global.MAP_WIDTH, mapLength * Global.MAP_HEIGHT);
        Global.mapEdgeUp = (int) this.maps.getCollider().top();
        Global.mapEdgeDown = (int) this.maps.getCollider().bottom();
        Global.mapEdgeLeft = (int) this.maps.getCollider().left();
        Global.mapEdgeRight = (int) this.maps.getCollider().right();
        MapGenerator mg = new MapGenerator(Global.MAP_QTY, this.maps);
//        mg.genSequenceMap();
        mg.genRandomMap();
        addAllMapsToAllObjects();
        this.allObjects.add(this.actor);
    }

    private void addAllMapsToAllObjects() {
        for (int i = 0; i < this.maps.getMaps().size(); i++) {
            this.allObjects.add(this.maps.get(i));
        }
    }

    @Override
    public void sceneUpdate() {
        this.view.update();
        Global.mapMouseX = Global.mouseX + Global.viewX;
        Global.mapMouseY = Global.mouseY + Global.viewY;
        //this.ammo測試範圍
        if (Global.mouseState == 1) {
            boolean create = true;
            if (this.ammo == null) {
                Ammo ammo = new Ammo("circle", this.actor.centerX() - Global.UNIT_X / 4, this.actor.centerY() - Global.UNIT_Y / 4, 60, ImagePath.BULLET);
                this.ammo.add(ammo);
                this.allObjects.add(ammo);
            } else {
                for (int i = 0; i < this.ammo.size(); i++) {
                    if (this.ammo.get(i).getIsShootOut() == false) {
                        this.ammo.get(i).setIsShootOut(create);
                        this.ammo.get(i).setNewStart(this.actor.centerX(), this.actor.centerY());
                        create = false;
                        break;
                    } else {
                        continue;
                    }
                }
                if (create) {
                    Ammo ammo = new Ammo("circle", this.actor.centerX() - Global.UNIT_X / 4, this.actor.centerY() - Global.UNIT_Y / 4, 60, ImagePath.BULLET);
                    this.ammo.add(ammo);
                    this.allObjects.add(ammo);
                }
            }
            Global.mouseState++;
        }
//        System.out.println(this.ammo.size());
        //this.ammo測試範圍end
        for (int i = 0; i < this.ammo.size(); i++) {
            this.ammo.get(i).update();
        }
        for (int i = 0; i < this.allObjects.size(); i++) {
            this.allObjects.get(i).update();
            if (this.view.isCollision(this.allObjects.get(i))) {
                if (!(this.view.stillSeeing(this.allObjects.get(i)))) {
                    this.view.saw(this.allObjects.get(i));
                }
            } else {
                this.view.removeSeen(this.allObjects.get(i));
            }
        }
    }

    @Override
    public void sceneEnd() {
        Global.log("main scene end");
    }

    @Override
    public void paint(Graphics g) {
        this.view.paint(g); // 只有出現在 view sawObjects 裡面的要畫出來
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

        private void setDirAndPressedStatus(Actor actor, int dir, boolean status) {
            actor.setStand(false);
            actor.setDir(dir);
            actor.setMovementPressedStatus(dir, status);
        }

        private void actorMoveRule(int commandCode) { // 當角色的視野沒碰到牆壁時移動邏輯
            actor.setStand(false);
            switch (commandCode) {
                case Global.UP:
                    if (!(actor.getCollider().top() < Global.mapEdgeUp)) {
                        Global.log("actor move");
                        setDirAndPressedStatus(actor, Global.UP, true);
                    } else {
                        stopRule(commandCode);
                    }
                    break;
                case Global.DOWN:
                    if (!(actor.getCollider().bottom() > Global.mapEdgeDown)) {
                        Global.log("actor move");
                        setDirAndPressedStatus(actor, Global.DOWN, true);
                    } else {
                        stopRule(commandCode);
                    }
                    break;
                case Global.LEFT:
                    if (!(actor.getCollider().left() < Global.mapEdgeLeft)) {
                        Global.log("actor move");
                        setDirAndPressedStatus(actor, Global.LEFT, true);
                    } else {
                        stopRule(commandCode);
                    }
                    break;
                case Global.RIGHT:
                    if (!(actor.getCollider().right() > Global.mapEdgeRight)) {
                        Global.log("actor move");
                        setDirAndPressedStatus(actor, Global.RIGHT, true);
                    } else {
                        stopRule(commandCode);
                    }
                    break;
            }
        } // 當角色的視野沒碰到牆壁時移動邏輯

        private void stopRule(int commandCode) {
            actor.setStand(true);
            switch (commandCode) {
                case Global.UP:
                    setDirAndPressedStatus(actor, Global.UP, false);
                    break;
                case Global.DOWN:
                    setDirAndPressedStatus(actor, Global.DOWN, false);
                    break;
                case Global.LEFT:
                    setDirAndPressedStatus(actor, Global.LEFT, false);
                    break;
                case Global.RIGHT:
                    setDirAndPressedStatus(actor, Global.RIGHT, false);
                    break;
            }
        }

        @Override
        public void keyTyped(char c, long trigTime) {
        }

    }

    public class MyMouseListener implements CommandSolver.MouseCommandListener {

        @Override
        public void mouseTrig(MouseEvent e, CommandSolver.MouseState state, long trigTime) {
//            System.out.println("mouse state:" + state);
            if (state == CommandSolver.MouseState.PRESSED) {
                Global.mouseState = 1;
            } else {// if (state == CommandSolver.MouseState.CLICKED) {
                Global.mouseState = 0;
            }
        }

    }
}
