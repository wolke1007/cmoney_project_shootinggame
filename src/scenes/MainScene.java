/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scenes;

import controllers.SceneController;
import gameobj.Actor;
import gameobj.TestObj;
import java.awt.Graphics;
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
    Actor act1;
    Delay delay;
    Delay changeSceneDelay;

    public MainScene(SceneController sceneController) {
        super(sceneController);
    }

    @Override
    public void sceneBegin() {
        act1 = new Actor(1, Global.STEPS_WALK_NORMAL, 60, 60);
        obj = new TestObj(1, 1, 50, 50, 130, 130, 50, 50);
        obj2 = new TestObj(0, 0, 350, 350, 150, 150, 150, 150);
        delay = new Delay(5);
        delay.start();
//        changeSceneDelay = new Delay(180);
//        changeSceneDelay.start();
    }

    @Override
    public void sceneUpdate() {
        if(delay.isTrig()){
            obj2.update();
            obj.update();
            if(obj.isCollision(obj2)){
            }
        }
//        if(changeSceneDelay.isTrig()){
//            sceneController.changeScene(new SecondScene(sceneController));
//        }
    }

    @Override
    public void sceneEnd() {
        System.out.println("main scene end");
    }

    @Override
    public void paint(Graphics g) {
        act1.paint(g);
        obj.paint(g);
        obj2.paint(g);
    }

    @Override
    public CommandSolver.KeyListener getKeyListener() {
        return new MyKeyListener();
    }

    @Override
    public CommandSolver.MouseCommandListener getMouseListener() {
        return new MyMouseListener();
    }
    
    public static class MyKeyListener implements CommandSolver.KeyListener{

        @Override
        public void keyPressed(int commandCode, long trigTime) {
            System.out.println("!");
        }

        @Override
        public void keyReleased(int commandCode, long trigTime) {
            System.out.println("?");
        }

        @Override
        public void keyTyped(char c, long trigTime) {
        }
        
    }
    
    public static class MyMouseListener implements CommandSolver.MouseCommandListener{

        @Override
        public void mouseTrig(MouseEvent e, CommandSolver.MouseState state, long trigTime) {
        }

    }
}
