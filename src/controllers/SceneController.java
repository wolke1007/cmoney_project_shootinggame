/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;
import java.awt.Graphics;
import scenes.Scene;
/**
 *
 * @author Cloud-Razer
 */
public class SceneController {
    
    private Scene currentScene;
    
    public void changeScene(Scene scene){
        if(currentScene != null){
            currentScene.sceneEnd();
        }
        currentScene = scene;
        currentScene.sceneBegin();
    }
    
    public void sceneUpdate(){
        currentScene.sceneUpdate();
    }
    
    public void paint(Graphics g){
        currentScene.paint(g);
    }
}
