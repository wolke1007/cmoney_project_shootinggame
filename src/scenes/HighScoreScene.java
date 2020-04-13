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
public class HighScoreScene extends Scene {

    private Renderer renderer;
    private ScoreCalculator scoreCal;
    private ArrayList<Record> scoreList;
    private int printLimit;
    private final String[] gameMode = {"endless", "campaign", "saving"};
    Button backBtn;
    Button endlessBtn;
    Button campaignBtn;
    Button savingBtn;
    String scoreType;

    public HighScoreScene(SceneController sceneController) {
        super(sceneController);
        this.renderer = new Renderer();
        this.printLimit = 5; // 要顯示幾行歷史紀錄
        this.backBtn = new BackButton();
        this.campaignBtn = new CampaignButton();
        this.savingBtn = new SavingButton();
        this.endlessBtn = new EndlessButton();
        this.scoreType = "null";
    }

    public abstract class Button {

        public int top;
        public int bottom;
        public int left;
        public int right;
    }

    public class BackButton extends Button {

        public BackButton() {
            super.left = 30;
            super.top = Global.SCREEN_Y - 90;
            super.right = super.left + 100;
            super.bottom = super.top + 80;
        }
    }

    public class EndlessButton extends Button {

        public EndlessButton() {
            int width = 150;
            super.left = (Global.SCREEN_X * 1 / 3) - (Global.SCREEN_X * 1 / 3) / 2 - width / 2;
            super.top = 100;
            super.right = super.left + width;
            super.bottom = super.top + 80;
        }
    }

    public class CampaignButton extends Button {

        public CampaignButton() {
            int width = 150;
            super.left = (Global.SCREEN_X * 2 / 3) - (Global.SCREEN_X * 1 / 3) / 2 - width / 2;
            super.top = 100;
            super.right = super.left + 100;
            super.bottom = super.top + 80;
        }
    }

    public class SavingButton extends Button {

        public SavingButton() {
            int width = 150;
            super.left = (Global.SCREEN_X * 3 / 3) - (Global.SCREEN_X * 1 / 3) / 2 - width / 2;
            super.top = 100;
            super.right = super.left + 100;
            super.bottom = super.top + 80;
        }
    }

    @Override
    public void sceneBegin() {
        // 播放背景音樂
        this.scoreCal = ScoreCalculator.getInstance();
        this.scoreList = this.scoreCal.getHistory(this.gameMode[0]);
        Global.log("this.scoreList: " + this.scoreList);
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
    
    private void paintEndlessScore(Graphics g){
        int textGap = 60;
        g.setColor(Color.WHITE);
        g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30)); 
        for(int i = 0; i < HighScoreScene.this.scoreList.size(); i++){
            if(i < HighScoreScene.this.printLimit){
                Record record = HighScoreScene.this.scoreList.get(i);
                g.drawString(Integer.toString(i + 1) + ".", endlessBtn.left - 50, endlessBtn.bottom + 30 * (i + 2) + textGap);
                g.drawString(record.getName(), endlessBtn.left, endlessBtn.bottom + 30 * (i + 2) + textGap);
                g.drawString(Integer.toString(record.getScore()), endlessBtn.left + 400, endlessBtn.bottom + 30 * (i + 2) + textGap);
                g.drawString(record.getDate().toString(), endlessBtn.left + 800, endlessBtn.bottom + 30 * (i + 2) + textGap);
            }
        }
        g.setColor(Color.BLACK);
    }

    @Override
    public void paint(Graphics g) {
        this.renderer.setImage(ImagePath.HIGH_SCORE[0]); // 背景圖
        this.renderer.paint(g, 0, 0, Global.SCREEN_X, Global.SCREEN_Y);

        this.renderer.setImage(ImagePath.COMMON_BUTTON[0]);
        if (cursorInBtn(this.backBtn)) {
            this.renderer.paint(g, this.backBtn.left, this.backBtn.top, this.backBtn.right, this.backBtn.bottom); // 歷史紀錄按鈕
        } else {
            this.renderer.paint(g, this.backBtn.left + 10, this.backBtn.top - 10, this.backBtn.right + 10, this.backBtn.bottom - 10); // 歷史紀錄按鈕
        }

        this.renderer.setImage(ImagePath.HIGH_SCORE[1]);
        if (cursorInBtn(this.endlessBtn)) {
            this.renderer.paint(g, this.endlessBtn.left, this.endlessBtn.top, this.endlessBtn.right, this.endlessBtn.bottom); // Endless mode 按鈕
        } else {
            this.renderer.paint(g, this.endlessBtn.left + 10, this.endlessBtn.top - 10, this.endlessBtn.right + 10, this.endlessBtn.bottom - 10); // Endless mode 按鈕
        }

        this.renderer.setImage(ImagePath.HIGH_SCORE[2]);
        if (cursorInBtn(this.campaignBtn)) {
            this.renderer.paint(g, this.campaignBtn.left, this.campaignBtn.top, this.campaignBtn.right, this.campaignBtn.bottom); // Campaign mode 按鈕
        } else {
            this.renderer.paint(g, this.campaignBtn.left + 10, this.campaignBtn.top - 10, this.campaignBtn.right + 10, this.campaignBtn.bottom - 10); // Campaign mode 按鈕
        }

        this.renderer.setImage(ImagePath.HIGH_SCORE[3]);
        if (cursorInBtn(this.savingBtn)) {
            this.renderer.paint(g, this.savingBtn.left, this.savingBtn.top, this.savingBtn.right, this.savingBtn.bottom); // Saving mode 按鈕
        } else {
            this.renderer.paint(g, this.savingBtn.left + 10, this.savingBtn.top - 10, this.savingBtn.right + 10, this.savingBtn.bottom - 10); // Saving mode 按鈕
        }
        g.setColor(Color.WHITE);
        g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30)); 
        g.drawString("Name", this.endlessBtn.left, this.endlessBtn.bottom + 50);
        g.drawString("Score", this.endlessBtn.left + 400, this.endlessBtn.bottom + 50);
        g.drawString("Date", this.endlessBtn.left + 800, this.endlessBtn.bottom + 50);
        g.setColor(Color.BLACK);
        if(this.scoreList == null){
            return;
        }
        if(this.scoreType.equals(HighScoreScene.this.gameMode[0])){
            paintEndlessScore(g);
        }
        if(this.scoreType.equals(HighScoreScene.this.gameMode[1])){
            
        }
        if(this.scoreType.equals(HighScoreScene.this.gameMode[2])){
            
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
                if (cursorInBtn(new BackButton())) {
                    // Enter main scene
                    HighScoreScene.super.sceneController.changeScene(new StartMenuScene(HighScoreScene.super.sceneController));
                }
                if (cursorInBtn(new EndlessButton())) {
                    // Enter score history scene
                    HighScoreScene.this.scoreType = HighScoreScene.this.gameMode[0];
                }
                if (cursorInBtn(new CampaignButton())) {
                    // Enter score history scene
                    HighScoreScene.this.scoreType = HighScoreScene.this.gameMode[1];
                }
                if (cursorInBtn(new SavingButton())) {
                    // Enter score history scene
                    HighScoreScene.this.scoreType = HighScoreScene.this.gameMode[2];
                }
            }
        }

    }

}
