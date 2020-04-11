/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scenes;

import controllers.ImagePath;
import controllers.SceneController;
import gameobj.Actor;
import renderer.Renderer;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import util.CommandSolver;
import util.Global;

/**
 *
 * @author Cloud-Razer
 */
public class StartMenuScene extends Scene {

    private Renderer backgroundRenderer;
    private Renderer startBtnRenderer;
    private Renderer recordBtnRenderer;

    public StartMenuScene(SceneController sceneController) {
        super(sceneController);
        this.backgroundRenderer = new Renderer(new int[]{0}, 0, ImagePath.START_MENU[0]);
        this.startBtnRenderer = new Renderer(new int[]{0}, 0, ImagePath.START_MENU[1]);
        this.recordBtnRenderer = new Renderer(new int[]{0}, 0, ImagePath.START_MENU[2]);
    }

    public abstract class Button {
        public int top;
        public int bottom;
        public int left;
        public int right;
    }

    public class startButton extends Button {
        public startButton(){
            super.left = Global.SCREEN_X / 2 - 150;
            super.top = Global.SCREEN_Y - 90;
            super.right = Global.SCREEN_X / 2 + 110;
            super.bottom = Global.SCREEN_Y - 40;
        }
    }

    public class scoreButton extends Button {
        public scoreButton(){
            super.left = 30;
            super.top = Global.SCREEN_Y - 90;
            super.right = 167;
            super.bottom = Global.SCREEN_Y - 40;
        }
    }

    @Override
    public void sceneBegin() {
        // 播放背景音樂
    }

    @Override
    public void sceneUpdate() {
    }

    @Override
    public void sceneEnd() {
        // 停止播放背景音樂
    }

    private boolean cursorInBtn(Button btn) {
        int btnTop = btn.top;
        int btnBottom = btn.bottom;
        int btnLeft = btn.left;
        int btnRight = btn.right;
        if (Global.mouseY > btnTop && Global.mouseY < btnBottom && Global.mouseX > btnLeft && Global.mouseX < btnRight) {
            return true;
        }
        return false;
    }

    @Override
    public void paint(Graphics g) {
        this.backgroundRenderer.paint(g, 0, 0, Global.SCREEN_X, Global.SCREEN_Y); // 背景圖
        Button btn;
        btn = new startButton();
        if (cursorInBtn(btn)) {
            this.startBtnRenderer.paint(g, btn.left + 10, btn.top + 10, btn.right + 10, btn.bottom + 10); // 開始按鈕
        } else {
            this.startBtnRenderer.paint(g, btn.left, btn.top, btn.right, btn.bottom); // 開始按鈕
        }
        btn = new scoreButton();
        if (cursorInBtn(btn)) {
            this.recordBtnRenderer.paint(g, btn.left + 10, btn.top + 10, btn.right + 10, btn.bottom + 10); // 歷史紀錄按鈕
        } else {
            this.recordBtnRenderer.paint(g, btn.left, btn.top, btn.right, btn.bottom); // 歷史紀錄按鈕
        }
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
        }

        @Override
        public void keyReleased(int commandCode, long trigTime) {
        }

        @Override
        public void keyTyped(char c, long trigTime) {
        }

    }

    public class MyMouseListener implements CommandSolver.MouseCommandListener {

        @Override
        public void mouseTrig(MouseEvent e, CommandSolver.MouseState state, long trigTime) {
            if (state == CommandSolver.MouseState.PRESSED || state == CommandSolver.MouseState.CLICKED) {
                if(cursorInBtn(new startButton())){
                    // Enter main scene
                    StartMenuScene.super.sceneController.changeScene(new MainScene(StartMenuScene.super.sceneController));
                }
                if(cursorInBtn(new scoreButton())){
                    // Enter score history scene
                    StartMenuScene.super.sceneController.changeScene(new HighScoreScene(StartMenuScene.super.sceneController));
                }
            }
        }

    }

}
