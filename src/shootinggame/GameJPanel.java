/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shootinggame;

import controllers.SceneController;
import java.awt.Graphics;
import scenes.MainScene;
import util.Global;

/**
 *
 * @author Cloud-Razer
 */
public class GameJPanel extends javax.swing.JPanel {
    
    private SceneController sceneController;
    
    public GameJPanel() {
        sceneController = new SceneController();
        sceneController.changeScene(new MainScene(sceneController));
    }
    
    public void update() {
        sceneController.sceneUpdate();
    }
    
    @Override
    public void paintComponent(Graphics g) {
        sceneController.paint(g);
    }
}

