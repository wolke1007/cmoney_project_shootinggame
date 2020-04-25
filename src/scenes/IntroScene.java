/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scenes;

import controllers.ImagePath;
import controllers.SceneController;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import renderer.Renderer;
import util.CommandSolver;
import util.Delay;
import util.Global;

/**
 *
 * @author user1
 */
public class IntroScene extends Scene {

    private Renderer backgroundRenderer;
    private Renderer titleRenderer;
    private Renderer pressBtnRenderer;
    private Renderer recordBtnRenderer;
    private Button pressBtn;
    private Button highScoreBtn;

    public IntroScene(SceneController sceneController) {
        super(sceneController);
        this.backgroundRenderer = new Renderer(new int[]{0}, 0, ImagePath.COMMON_BACKGROUND[0]);
        this.titleRenderer = new Renderer(new int[]{0}, 0, ImagePath.WELCOME_PAGE[0]);
        this.pressBtnRenderer = new Renderer(new int[]{0}, 0, ImagePath.WELCOME_PAGE[1]);
        this.pressBtn = new pressButton();
    }

    public abstract class Button {

        public int top;
        public int bottom;
        public int left;
        public int right;
    }

    public class pressButton extends Button {

        public pressButton() {
            int height = 80;
            int width  = 150;
            int upDownposition = -20;
            super.left = Global.SCREEN_X / 2 - width;
            super.top = Global.SCREEN_Y / 2 - height + upDownposition;
            super.right = Global.SCREEN_X / 2 + width;
            super.bottom = Global.SCREEN_Y / 2 + height + upDownposition;
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
        int titleHeight = 200;
        this.backgroundRenderer.paint(g, 0, 0, Global.SCREEN_X, Global.SCREEN_Y); // 背景圖
        this.titleRenderer.paint(g, (Global.SCREEN_X - Global.SCREEN_X * 19 / 20)
                , 10, (Global.SCREEN_X - Global.SCREEN_X * 1 / 20), 10 + titleHeight); // 背景圖
        if (cursorInBtn(this.pressBtn)) {
            this.pressBtnRenderer.paint(g, this.pressBtn.left + 10, this.pressBtn.top + 10, this.pressBtn.right + 10, this.pressBtn.bottom + 10); // 開始按鈕
        } else {
            this.pressBtnRenderer.paint(g, this.pressBtn.left, this.pressBtn.top, this.pressBtn.right, this.pressBtn.bottom); // 開始按鈕
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
                if (cursorInBtn(new pressButton())) {
                    // Enter main scene
                    IntroScene.super.sceneController.changeScene(new StartMenuScene(IntroScene.super.sceneController));
                }
            }
        }

    }

}
