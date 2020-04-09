/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scenes;

import controllers.ImagePath;
import controllers.SceneController;
import gameobj.Renderer;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import util.CommandSolver;
import util.Global;

/**
 *
 * @author Cloud-Razer
 */
public class HighScoreScene extends Scene {

    private Renderer backgroundRenderer;
    private Renderer backBtnRenderer;

    public HighScoreScene(SceneController sceneController) {
        super(sceneController);
        this.backgroundRenderer = new Renderer(new int[]{0}, 0, ImagePath.HIGH_SCORE[0]);
        this.backBtnRenderer = new Renderer(new int[]{0}, 0, ImagePath.COMMON_BUTTON[0]);
    }

    public abstract class Button {
        public int top;
        public int bottom;
        public int left;
        public int right;
    }

    public class BackButton extends Button {
        public BackButton(){
            super.left = 30;
            super.top = Global.SCREEN_Y - 90;
            super.right = 167;
            super.bottom = Global.SCREEN_Y - 30;
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
        btn = new BackButton();
        if (cursorInBtn(btn)) {
            this.backBtnRenderer.paint(g, btn.left, btn.top, btn.right, btn.bottom); // 歷史紀錄按鈕
        } else {
            this.backBtnRenderer.paint(g, btn.left + 10, btn.top - 10, btn.right + 10, btn.bottom - 10); // 歷史紀錄按鈕
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
            if (state == CommandSolver.MouseState.PRESSED) {
                if(cursorInBtn(new BackButton())){
                    // Enter main scene
                    HighScoreScene.super.sceneController.changeScene(new StartMenuScene(HighScoreScene.super.sceneController));
                }
                if(cursorInBtn(new BackButton())){
                    // Enter score history scene
                }
            }
        }

    }

}
