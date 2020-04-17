/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package effects;

import controllers.ImagePath;
import gameobj.Actor;
import java.awt.Graphics;
import renderer.Renderer;
import util.Delay;
import util.Global;

/**
 *
 * @author Cloud-Razer
 */
public class DeadEffect implements Effect { // 此效果因時機點特殊，不加入至 Actor 的 Effects 裡面而是獨立 new 在 main scene 中

    private String imagePath;
    private int x1;
    private int y1;
    private int x2;
    private int y2;
    private int width;
    private int height;
    private boolean run;
    private Actor actor;
    private Renderer handRenderer;
    private Renderer bloodRenderer;
    private Delay delay;
    private int dragDistance;
    private final int dragDistanceLimit = 500;
    private final int handSizeLimit = 500;
    private int playTimes;

    public DeadEffect(int width, int height, Actor actor) {
        this.width = width;
        this.height = height;
        this.run = false;
        this.actor = actor;
        this.handRenderer = new Renderer();
        this.bloodRenderer = new Renderer();
        this.dragDistance = 0;
        this.delay = new Delay(1);
        this.playTimes = 0;
    }

    @Override
    public boolean getRun() {
        return this.run;
    }

    @Override
    public void update() {
        if(this.playTimes == 0){
            this.run = true;
        }else{
            this.run = false;
        }
    }

    @Override
    public void setImg(String src) {
        this.imagePath = src;
    }

    @Override
    public void paint(Graphics g) {
        // 播放聲音 : 死亡音效
        this.handRenderer.setImage(ImagePath.BLOOD[2]);
        this.bloodRenderer.setImage(ImagePath.BLOOD[3]);
        this.delay.start();
        if (this.delay.isTrig() && this.width < this.handSizeLimit) {
            this.x1 = (int) this.actor.getX() - this.width / 2;
            this.y1 = (int) this.actor.getY() - this.height / 2;
            this.x2 = (int) this.actor.getX() + this.width / 2;
            this.y2 = (int) this.actor.getY() + this.height / 2;
            this.width += 60;
            this.height += 50;
        } // 手掌從小變大
        if (this.width >= this.handSizeLimit && this.dragDistance < this.dragDistanceLimit) {
            this.x1 = (int) this.actor.getX() - this.width / 2;
            this.y1 = (int) this.actor.getY() - this.height / 2 + this.dragDistance;
            this.x2 = (int) this.actor.getX() + this.width / 2;
            this.y2 = (int) this.actor.getY() + this.height / 2 + this.dragDistance;
            this.dragDistance = this.dragDistance + 4;
        } // 手掌往下拖
        if (this.width >= this.handSizeLimit && this.dragDistance < this.dragDistanceLimit) {
            this.bloodRenderer.paint(g, this.x1, (int) this.actor.getY() - this.height / 2,
                    this.x2, (int) this.actor.getY() + this.dragDistance,
                    0, 0, 287, this.dragDistance);
        } // 血跡
        this.handRenderer.paint(g, this.x1, this.y1, this.x2, this.y2);
        if (this.dragDistance >= this.dragDistanceLimit) {
            this.playTimes++;
            this.run = false;
            Global.log("this.run set to false");
        }
    }
}
