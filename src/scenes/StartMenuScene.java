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
import util.Global;

/**
 *
 * @author Cloud-Razer
 */
public class StartMenuScene extends Scene {

    private Renderer backgroundRenderer;
    private Renderer backgroundRenderer2;
    private Renderer rankBtnRenderer;
    private Renderer startBtnRenderer;
    private Renderer introBtnRenderer;
    private Button rankBtn;
    private Button startBtn;
    private Button introBtn;
    private int buttonWidth;
    private int buttonHeight;
    private int buttonGap;

    public StartMenuScene(SceneController sceneController) {
        super(sceneController);
        this.backgroundRenderer = new Renderer(new int[]{0}, 0, ImagePath.COMMON_BACKGROUND[0]);
        this.backgroundRenderer2 = new Renderer(new int[]{0}, 0, ImagePath.COMMON_BACKGROUND[1]);
        this.rankBtnRenderer = new Renderer(new int[]{0}, 0, ImagePath.MENU_PAGE[0]);
        this.startBtnRenderer = new Renderer(new int[]{0}, 0, ImagePath.MENU_PAGE[3]);
        this.introBtnRenderer = new Renderer(new int[]{0}, 0, ImagePath.MENU_PAGE[6]);
        this.buttonWidth = 300;
        this.buttonHeight = this.buttonWidth / 2;
        this.buttonGap = 60;
        this.rankBtn = new rankButton();
        this.startBtn = new startButton();
        this.introBtn = new introButton();
    }

    public int getBtnGap() {
        return this.buttonGap;
    }

    public abstract class Button {

        public int top;
        public int bottom;
        public int left;
        public int right;
        public int upDownPosition = 230;
    }

    public class rankButton extends Button {

        public rankButton() {
            
            super.left = (Global.SCREEN_X - (StartMenuScene.this.buttonWidth * 3 + StartMenuScene.this.buttonGap * 2)) / 2;
            super.top = upDownPosition;
            super.right = left + StartMenuScene.this.buttonWidth;
            super.bottom = top + StartMenuScene.this.buttonHeight;
        }
    }

    public class startButton extends Button {

        public startButton() {
            super.left = StartMenuScene.this.rankBtn.right + StartMenuScene.this.buttonGap;
            super.top = upDownPosition;
            super.right = left + StartMenuScene.this.buttonWidth;
            super.bottom = top + StartMenuScene.this.buttonHeight;
        }
    }

    public class introButton extends Button {

        public introButton() {
            super.left = StartMenuScene.this.startBtn.right + StartMenuScene.this.buttonGap;
            super.top = upDownPosition;
            super.right = left + StartMenuScene.this.buttonWidth;
            super.bottom = top + StartMenuScene.this.buttonHeight;
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
        int secondEdge = 20;
        this.backgroundRenderer.paint(g, 0, 0, Global.SCREEN_X, Global.SCREEN_Y); // 背景圖
        this.backgroundRenderer2.paint(g, secondEdge, secondEdge, Global.SCREEN_X - secondEdge, Global.SCREEN_Y - secondEdge); // 背景圖
        if (cursorInBtn(this.startBtn)) {
            this.startBtnRenderer.paint(g, this.startBtn.left + 10, this.startBtn.top + 10, this.startBtn.right + 10, this.startBtn.bottom + 10); // 開始按鈕
        } else {
            this.startBtnRenderer.paint(g, this.startBtn.left, this.startBtn.top, this.startBtn.right, this.startBtn.bottom); // 開始按鈕
        }
        if (cursorInBtn(this.rankBtn)) {
            this.rankBtnRenderer.paint(g, this.rankBtn.left + 10, this.rankBtn.top + 10, this.rankBtn.right + 10, this.rankBtn.bottom + 10); // 歷史紀錄按鈕
        } else {
            this.rankBtnRenderer.paint(g, this.rankBtn.left, this.rankBtn.top, this.rankBtn.right, this.rankBtn.bottom); // 歷史紀錄按鈕
        }
        if (cursorInBtn(this.introBtn)) {
            this.introBtnRenderer.paint(g, this.introBtn.left + 10, this.introBtn.top + 10, this.introBtn.right + 10, this.introBtn.bottom + 10); // 歷史紀錄按鈕
        } else {
            this.introBtnRenderer.paint(g, this.introBtn.left, this.introBtn.top, this.introBtn.right, this.introBtn.bottom); // 歷史紀錄按鈕
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
                if (cursorInBtn(new startButton())) {
                    // Enter main scene
                    StartMenuScene.super.sceneController.changeScene(new MainScene(StartMenuScene.super.sceneController));
                }
                if (cursorInBtn(new rankButton())) {
                    // Enter score history scene
                    StartMenuScene.super.sceneController.changeScene(new RankScene(StartMenuScene.super.sceneController));
                }
                if (cursorInBtn(new introButton())) {
                    // Enter score history scene
                    StartMenuScene.super.sceneController.changeScene(new IntroScene(StartMenuScene.super.sceneController));
                }
            }
        }

    }

}
