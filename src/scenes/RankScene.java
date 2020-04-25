/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scenes;

import controllers.ImagePath;
import controllers.SceneController;
import java.awt.Color;
import java.awt.Font;
import renderer.Renderer;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import util.CommandSolver;
import util.Global;
import util.Record;
import util.ScoreCalculator;

/**
 *
 * @author Cloud-Razer
 */
public class RankScene extends Scene {

    private Renderer backgroundRenderer;
    private Renderer backgroundRenderer2;
    private Renderer shadowRenderer;
    private Renderer rankRenderer;
    private Renderer returnBtnRenderer;
    private Button returnBtn;

    public RankScene(SceneController sceneController) {
        super(sceneController);
        this.backgroundRenderer = new Renderer(new int[]{0}, 0, ImagePath.COMMON_BACKGROUND[0]);
        this.backgroundRenderer2 = new Renderer(new int[]{0}, 0, ImagePath.COMMON_BACKGROUND[1]);
        this.rankRenderer = new Renderer(new int[]{0}, 0, ImagePath.RANK_PAGE[0]);
        this.returnBtnRenderer = new Renderer(new int[]{0}, 0, ImagePath.COMMON_BUTTON[0]);
        this.returnBtn = new returnButton();
    }

    public abstract class Button {

        public int top;
        public int bottom;
        public int left;
        public int right;
    }

    public class returnButton extends Button {

        public returnButton() {
            int height = 130;
            int width  = 250;
            int upDownposition = -5;
            super.left = Global.SCREEN_X - width;
            super.top = Global.SCREEN_Y - height + upDownposition;
            super.right = super.left + width;
            super.bottom = super.top + height + upDownposition;
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
    
    private float ratio(float num){
        return num / 50f;
    }

    @Override
    public void paint(Graphics g) {
        this.backgroundRenderer.paint(g, 0, 0, Global.SCREEN_X, Global.SCREEN_Y); // 背景圖
        Global.log(""+ratio(30));
        this.backgroundRenderer2.paint(g, (int)(Global.SCREEN_X - Global.SCREEN_X * ratio(49))
                , (int)(Global.SCREEN_Y - Global.SCREEN_Y * ratio(49))
                , (int)(Global.SCREEN_X - Global.SCREEN_X * ratio(1))
                , (int)(Global.SCREEN_Y - Global.SCREEN_Y * ratio(1))); // 背景圖
        this.rankRenderer.paint(g, (int)(Global.SCREEN_X - Global.SCREEN_X * ratio(45))
                , (int)(Global.SCREEN_Y - Global.SCREEN_Y * ratio(45))
                , (int)(Global.SCREEN_X - Global.SCREEN_X * ratio(5))
                , (int)(Global.SCREEN_Y - Global.SCREEN_Y * ratio(12))); // 背景圖
        if (cursorInBtn(this.returnBtn)) {
            this.returnBtnRenderer.paint(g, this.returnBtn.left + 10, this.returnBtn.top + 10, this.returnBtn.right + 10, this.returnBtn.bottom + 10); // 開始按鈕
        } else {
            this.returnBtnRenderer.paint(g, this.returnBtn.left, this.returnBtn.top, this.returnBtn.right, this.returnBtn.bottom); // 開始按鈕
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
                if (cursorInBtn(new returnButton())) {
                    // Enter main scene
                    RankScene.super.sceneController.changeScene(new StartMenuScene(RankScene.super.sceneController));
                }
            }
        }

    }

}