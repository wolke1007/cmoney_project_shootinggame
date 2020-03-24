/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scenes;

import controllers.SceneController;
import util.CommandSolver.KeyListener;
import util.CommandSolver.MouseCommandListener;
import java.awt.Graphics;
/**
 *
 * @author Cloud-Razer
 */


public abstract class Scene {
    protected SceneController sceneController;
    
    public Scene(SceneController sceneController){
        this.sceneController = sceneController;
    }
    public abstract void sceneBegin();
    public abstract void sceneUpdate();
    public abstract void sceneEnd();
    public abstract void paint(Graphics g);
    
    public abstract KeyListener getKeyListener();
    public abstract MouseCommandListener getMouseListener();
}
