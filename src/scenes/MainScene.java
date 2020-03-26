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

    TestObj obj;
    TestObj obj2;
    Actor actor;
    Map map;
    View view;
    Delay delay;
    Delay changeSceneDelay;

    public MainScene(SceneController sceneController) {
        super(sceneController);
    }

    @Override
    public void sceneBegin() {
        // view 的 x , y 正式時會是 0, 0，為方便 debug 所以設成其他
        // view 的大小width , height 正式時會是SCREEN_X, SCREEN_Y，為方便 debug 所以設成其他
        view = new View(100, 100, 300, 300, 300, 300);
        actor = new Actor(1, Global.STEPS_WALK_NORMAL, Global.ACTOR_X, Global.ACTOR_Y, view);
        map = new Map(0, 0, 0);
        delay = new Delay(1);
        delay.start();
//        changeSceneDelay = new Delay(180);
//        changeSceneDelay.start();
    }

    @Override
    public void sceneUpdate() {
        if(delay.isTrig()){
//            obj2.update();
//            obj.update();
            actor.update();
            view.update();
            map.update();
        }
    }

    @Override
    public void sceneEnd() {
        System.out.println("main scene end");
    }

    @Override
    public void paint(Graphics g) {
        map.paint(g);
        actor.paint(g);
        view.paint(g);
//        obj.paint(g);
//        obj2.paint(g);
    }

    @Override
    public CommandSolver.KeyListener getKeyListener() {
        return new MyKeyListener();
    }

    @Override
    public CommandSolver.MouseCommandListener getMouseListener() {
        return new MyMouseListener();
    }
    
    public class MyKeyListener implements CommandSolver.KeyListener{

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
    
    public static class MyMouseListener implements CommandSolver.MouseCommandListener{

        @Override
        public void mouseTrig(MouseEvent e, CommandSolver.MouseState state, long trigTime) {
            System.out.println("mouse state:" + state);
        }

    }
}
