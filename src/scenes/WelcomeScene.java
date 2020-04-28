/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scenes;

import controllers.AudioPath;
import controllers.ImagePath;
import controllers.MusicResourceController;
import controllers.SceneController;
import java.applet.AudioClip;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import renderer.Renderer;
import util.CommandSolver;
import util.Delay;
import util.Global;

/**
 *
 * @author Cloud-Razer
 */
public class WelcomeScene extends Scene {

    private Renderer backgroundRenderer;
    private Renderer titleRenderer;
    private Renderer pressBtnRenderer;
    private Renderer recordBtnRenderer;
    private Button pressBtn;
    private Button highScoreBtn;
    private boolean isOnBtn;
    private Delay changeImage;
    private int changeCount;

    public WelcomeScene(SceneController sceneController) {
        super(sceneController);
        this.backgroundRenderer = new Renderer(new int[]{0}, 0, ImagePath.COMMON_BACKGROUND[0]);
        this.titleRenderer = new Renderer(new int[]{0}, 0, ImagePath.WELCOME_PAGE[0]);
        this.pressBtnRenderer = new Renderer(new int[]{0}, 0, ImagePath.WELCOME_PAGE[1]);
        this.pressBtn = new pressButton();
        MusicResourceController.getInstance().tryGetMusic(AudioPath.START_MUSIC);
        MusicResourceController.getInstance().tryGetMusic(AudioPath.GAME_BEGIN);
        MusicResourceController.getInstance().tryGetMusic(AudioPath.BUTTON_AUDIO);
        this.isOnBtn = true;
        this.changeImage = new Delay(30);
        this.changeImage.start();
        this.changeCount = 0;
    }

    public abstract class Button {

        public int top;
        public int bottom;
        public int left;
        public int right;
    }

    public class pressButton extends Button {

        public pressButton() {
            int height = 117;
            int width = 328;
            int upDownposition = -30;
            super.left = Global.SCREEN_X / 2 - width / 2;
            super.top = Global.SCREEN_Y / 2 - height / 2 + upDownposition;
            super.right = Global.SCREEN_X / 2 + width / 2;
            super.bottom = Global.SCREEN_Y / 2 + height / 2 + upDownposition;
        }
    }

    @Override
    public void sceneBegin() {
        // 播放背景音樂
        MusicResourceController.getInstance().tryGetMusic(AudioPath.START_MUSIC).loop();
    }

    @Override
    public void sceneUpdate() {
        if (cursorInBtn(this.pressBtn)) {
            this.pressBtnRenderer.setImage(ImagePath.WELCOME_PAGE[3]);
        } else if (this.changeImage.isTrig()) {
            if (this.changeCount++ % 2 == 0) {
                this.pressBtnRenderer.setImage(ImagePath.WELCOME_PAGE[1]);
            } else {
                this.pressBtnRenderer.setImage(ImagePath.WELCOME_PAGE[2]);
            }
        }
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
            if (this.isOnBtn) {
                MusicResourceController.getInstance().tryGetMusic(AudioPath.BUTTON_AUDIO).play();
                this.isOnBtn = false;
            }
            return true;
        }
        this.isOnBtn = true;
        return false;
    }

    @Override
    public void paint(Graphics g) {
        int titleHeight = 200;
        this.backgroundRenderer.paint(g, 0, 0, Global.SCREEN_X, Global.SCREEN_Y); // 背景圖
        this.titleRenderer.paint(g, (Global.SCREEN_X - Global.SCREEN_X * 19 / 20), 10, (Global.SCREEN_X - Global.SCREEN_X * 1 / 20), 10 + titleHeight); // 背景圖
        this.pressBtnRenderer.paint(g, this.pressBtn.left, this.pressBtn.top, this.pressBtn.right, this.pressBtn.bottom); // 開始按鈕
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
                    WelcomeScene.super.sceneController.changeScene(new StartMenuScene(WelcomeScene.super.sceneController));
                }
            }
        }

    }

}
