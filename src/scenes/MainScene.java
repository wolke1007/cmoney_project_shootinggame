/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scenes;

import controllers.SceneController;
import gameobj.TestObj;
import java.awt.Graphics;
import util.Delay;

/**
 *
 * @author Cloud-Razer
 */
public class MainScene extends Scene{

    TestObj obj;
    TestObj obj2;
    Delay delay;
    
    public MainScene(SceneController sceneController) {
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

}
