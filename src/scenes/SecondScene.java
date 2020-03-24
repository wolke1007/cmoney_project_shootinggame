/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scenes;

import controllers.SceneController;
import gameobj.Actor;
import gameobj.Renderer;
import java.awt.Graphics;
import util.Delay;

/**
 *
 * @author Cloud-Razer
 */
public class SecondScene extends Scene{

    Actor actor;
    private Delay delay;

    public SecondScene(SceneController sceneController) {
        super(sceneController);
    }
    
    @Override
    public void sceneBegin() {
        actor = new Actor(5, Renderer.STEPS_WALK_NORMAL, 250, 250);
        delay = new Delay(300);
        delay.start();
    }

    @Override
    public void sceneUpdate() {
        if(delay.isTrig()){
            sceneController.changeScene(new MainScene(sceneController));
        }
        actor.update();
    }

    @Override
    public void sceneEnd() {
        delay.stop();
        delay = null;
    }

    @Override
    public void paint(Graphics g) {
        actor.paint(g);
    }

   
}