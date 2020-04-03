/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameobj;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import util.Delay;
import util.Global;

/**
 *
 * @author Cloud-Razer
 */
public class Renderer {

//    public static final int[] STEPS_WALK_NORMAL = {0, 1, 2, 1};
//    public static final int[] STEPS_WALK_SHORT = {0, 2};
    private BufferedImage img; // 角色行走圖
    private int characterIndex; // 角色編號
    private int dir; // 角色面對方向

    // 動作控制
    private int currentStep = 1;
    private int stepIndex = 0;
    private int[] steps; // 一定要先被定義
    // 動作控制 end

    // 動畫Delay
//    private Delay delay;
    // 動畫Delay end
    
    private BufferedImage testImg;
    
    public Renderer(int characterIndex, int[] steps, int delay, String src) {
        try {
            img = ImageIO.read(getClass().getResource(src));
            this.testImg = ImageIO.read(getClass().getResource("/resources/Actor_sample.png"));
        } catch (IOException ex) {
        }
        this.characterIndex = 0; // 不會變動  一定要先定義
        this.steps = steps;

        this.dir = 0;
        this.currentStep = 0;
        this.stepIndex = 0;

//        this.delay = new Delay(delay);
//        this.delay.start();
    }//老師版本 (未來可以改圖及呈現方式)

    public Renderer(int[] steps, int delay, String src) {
        try {
            img = ImageIO.read(getClass().getResource(src));
        } catch (IOException ex) {
        }
        this.steps = steps;//預留步伐接口 //暫時不用
        setDir(0);//待修改
//        setRenderDelay(delay);
    }//多載 建構子 當前版本

    public void update() {
//        if (delay.isTrig()) {
//            stepIndex = (stepIndex + 1) % steps.length;
//            currentStep = steps[stepIndex];
//        }
    }

//    private void setRenderDelay(float delay) {
//        this.delay = new Delay(delay);
//        this.delay.start();
//    }

    public void setDelay(int delay) {
//        this.delay.setDelayFrame(delay);
    }

    public void setDir(int dir) {
        this.dir = dir;
    }//待修改

    public void pause() {
//        this.delay.pause();
    }

    public void start() {
//        this.delay.start();
    }
//    private int testAngle = 0;
    public void paint(Graphics g, int x, int y, int w, int h) {
        g.drawImage(img, x - Global.viewX, y - Global.viewY, x + w, y + h,
                (Global.UNIT_X * currentStep) + (characterIndex % 4) * (Global.UNIT_X * 3),
                (Global.UNIT_Y * dir) + (characterIndex / 4) * (Global.UNIT_Y * 4),
                (Global.UNIT_X + Global.UNIT_X * currentStep) + (characterIndex % 4) * (Global.UNIT_X * 3),
                (Global.UNIT_Y + Global.UNIT_Y * dir) + (characterIndex / 4) * (Global.UNIT_Y * 4), null);
    }
}
