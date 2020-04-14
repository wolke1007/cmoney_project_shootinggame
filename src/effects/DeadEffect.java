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
public class DeadEffect implements Effect {

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

    public DeadEffect(int width, int height, Actor actor) {
        this.width = width;
        this.height = height;
        this.run = false;
        this.actor = actor;
        this.handRenderer = new Renderer();
        this.bloodRenderer = new Renderer();
        this.dragDistance = 0;
        this.delay = new Delay(1);
    }

    @Override
    public boolean getRun() {
        return this.run;
    }

    @Override
    public void update() {
        if (this.actor.getHp() <= 0f) {
            this.run = true;
        } else {
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
        if (this.delay.isTrig() && this.width < 500) {
            this.x1 = (int) this.actor.getX() - this.width / 2;
            this.y1 = (int) this.actor.getY() - this.height / 2;
            this.x2 = (int) this.actor.getX() + this.width / 2;
            this.y2 = (int) this.actor.getY() + this.height / 2;
            this.width += 60;
            this.height += 50;
        } // 手掌從小變大
        if (this.width >= 500 && this.dragDistance < 600) {
            this.x1 = (int) this.actor.getX() - this.width / 2;
            this.y1 = (int) this.actor.getY() - this.height / 2 + this.dragDistance;
            this.x2 = (int) this.actor.getX() + this.width / 2;
            this.y2 = (int) this.actor.getY() + this.height / 2 + this.dragDistance;
            this.dragDistance = this.dragDistance + 4;
        } // 手掌往下拖
        if (this.width >= 500) {
            this.bloodRenderer.paint(g, this.x1, (int) this.actor.getY() - this.height / 2, 
                    this.x2, (int) this.actor.getY() + this.dragDistance, 
                    0, 0, 287, this.dragDistance);
        } // 血跡
        this.handRenderer.paint(g, this.x1, this.y1, this.x2, this.y2);
    }
}
