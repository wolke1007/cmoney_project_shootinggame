/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scenes;

import controllers.SceneController;
import gameobj.TestObj;
import graph.Rect;
import util.CommandSolver;
import util.Delay;
import util.Global;
import java.awt.Color;
import java.awt.Graphics;
import gameobj.Actor;
import gameobj.Renderer;

/**
 *
 * @author user1
 */
public class SecondScene extends Scene{

    TestObj obj;
    TestObj obj2;
    Delay delay;
    
    public SecondScene(SceneController sceneController) {
        super(sceneController);
    }
    
    @Override
    public void sceneBegin() {
        obj = new TestObj(1, 1, 50, 50, 130, 130, 50, 50);
        obj2 = new TestObj(0, 0, 350, 350, 150, 150, 150, 150);
        delay = new Delay(5);
        delay.start();
    }

    @Override
    public void sceneUpdate() {
        if(delay.isTrig()){
            obj2.update();
            obj.update();
            if(obj.isCollision(obj2)){
            }
        }
    }

    @Override
    public void sceneEnd() {
    }

    @Override
    public void paint(Graphics g) {
        obj.paint(g);
        obj2.paint(g);
    }

    @Override
    public CommandSolver.KeyListener getKeyListener() {
        return new MyKeyListener();
    }

    @Override
    public CommandSolver.MouseCommandListener getMouseListener() {
        return null;
    }
    
    public static class MyKeyListener implements CommandSolver.KeyListener{

        @Override
        public void keyPressed(int commandCode, long trigTime) {
            System.out.println("~~~~");
        }

        @Override
        public void keyReleased(int commandCode, long trigTime) {
            System.out.println("1234");
        }

        @Override
        public void keyTyped(char c, long trigTime) {
            
        }
        
    }
}
