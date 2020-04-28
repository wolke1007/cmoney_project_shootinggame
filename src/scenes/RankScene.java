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
import java.awt.Color;
import java.awt.Font;
import renderer.Renderer;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import util.CommandSolver;
import util.Delay;
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
    private ScoreCalculator scoreCal;
    private ArrayList<Record> scoreList;
    private String scoreType;
    private int printLimit;
    private int rankContentTop;
    private int rankContentLeft;
    private int rankContentBottom;
    private int rankContentRight;
    private SimpleDateFormat sdf;
    private boolean isOnBtn;
    private Delay changeImage;
    private int changeCount;

    public RankScene(SceneController sceneController) {
        super(sceneController);
        this.backgroundRenderer = new Renderer(new int[]{0}, 0, ImagePath.COMMON_BACKGROUND[0]);
        this.backgroundRenderer2 = new Renderer(new int[]{0}, 0, ImagePath.COMMON_BACKGROUND[1]);
        this.rankRenderer = new Renderer(new int[]{0}, 0, ImagePath.RANK_PAGE[0]);
        this.returnBtnRenderer = new Renderer(new int[]{0}, 0, ImagePath.COMMON_BUTTON[0]);
        this.returnBtn = new returnButton();
        this.scoreType = "campaign";
        this.printLimit = 5; // 要顯示幾行歷史紀錄
        this.rankContentTop = (int) (Global.SCREEN_Y - Global.SCREEN_Y * ratio(45));
        this.rankContentLeft = (int) (Global.SCREEN_X - Global.SCREEN_X * ratio(45));
        this.rankContentBottom = (int) (Global.SCREEN_Y - Global.SCREEN_Y * ratio(12));
        this.rankContentRight = (int) (Global.SCREEN_X - Global.SCREEN_X * ratio(5));
        this.sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
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

    public class returnButton extends Button {

        public returnButton() {
            int height = 117;
            int width = 328;
            int upDownposition = -5;
            super.left = Global.SCREEN_X - width + upDownposition;
            super.top = Global.SCREEN_Y - height + upDownposition;
            super.right = super.left + width + upDownposition;
            super.bottom = super.top + height + upDownposition;
        }
    }

    @Override
    public void sceneBegin() {
        // 播放背景音樂
        this.scoreCal = ScoreCalculator.getInstance();
        this.scoreList = this.scoreCal.getHistory(this.scoreType);
        Global.log("this.scoreList: " + this.scoreList);
    }

    @Override
    public void sceneUpdate() {
        if (cursorInBtn(this.returnBtn)) {
            this.returnBtnRenderer.setImage(ImagePath.COMMON_BUTTON[2]);
        } else if (this.changeImage.isTrig()) {
            if (this.changeCount++ % 2 == 0) {
                this.returnBtnRenderer.setImage(ImagePath.COMMON_BUTTON[0]);
            } else {
                this.returnBtnRenderer.setImage(ImagePath.COMMON_BUTTON[1]);
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

    private float ratio(float num) {
        return num / 50f;
    }

    private void paintEndlessScore(Graphics g) {
        int textGap = 60;
        int categoryGap1 = (int) ((this.rankContentRight - this.rankContentLeft) * ratio(19));
        int categoryGap2 = (int) ((this.rankContentRight - this.rankContentLeft) * ratio(16) + 40 * ratio(16));
        int leftOffset = (int) ((this.rankContentRight - this.rankContentLeft) * ratio(5));
        int topOffset = (int) ((this.rankContentBottom - this.rankContentTop) * ratio(20));
        g.setColor(Color.BLACK);
        g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));
        for (int i = 0; i < RankScene.this.scoreList.size(); i++) {
            if (i < RankScene.this.printLimit) {
                Record record = RankScene.this.scoreList.get(i);
                g.drawString(Integer.toString(i + 1) + ".", this.rankContentLeft + leftOffset, this.rankContentTop + topOffset + i * textGap);
                g.drawString(record.getName(), this.rankContentLeft + leftOffset + textGap, this.rankContentTop + topOffset + i * textGap);
                g.drawString(Integer.toString(record.getScore()), this.rankContentLeft + leftOffset + categoryGap1, this.rankContentTop + topOffset + i * textGap);
                g.drawString(this.sdf.format(record.getDate()),
                        this.rankContentLeft + leftOffset + (2 * categoryGap2), this.rankContentTop + topOffset + i * textGap);
            }
        }
        g.setColor(Color.BLACK);
    }

    @Override
    public void paint(Graphics g) {
        this.backgroundRenderer.paint(g, 0, 0, Global.SCREEN_X, Global.SCREEN_Y); // 背景圖
        this.backgroundRenderer2.paint(g, (int) (Global.SCREEN_X - Global.SCREEN_X * ratio(49)), (int) (Global.SCREEN_Y - Global.SCREEN_Y * ratio(49)), (int) (Global.SCREEN_X - Global.SCREEN_X * ratio(1)), (int) (Global.SCREEN_Y - Global.SCREEN_Y * ratio(1))); // 背景圖
        this.rankRenderer.paint(g, this.rankContentLeft, this.rankContentTop, this.rankContentRight, this.rankContentBottom);
//        if (cursorInBtn(this.returnBtn)) {
//            this.returnBtnRenderer.paint(g, this.returnBtn.left + 10, this.returnBtn.top + 10, this.returnBtn.right + 10, this.returnBtn.bottom + 10); // 開始按鈕
//        } else {
        this.returnBtnRenderer.paint(g, this.returnBtn.left, this.returnBtn.top, this.returnBtn.right, this.returnBtn.bottom); // 開始按鈕
//        }
        paintEndlessScore(g);
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
