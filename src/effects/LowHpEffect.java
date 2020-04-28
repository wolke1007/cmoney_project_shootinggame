/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package effects;

import controllers.AudioPath;
import controllers.AudioResourceController;
import controllers.ImagePath;
import controllers.MusicResourceController;
import gameobj.Actor;
import java.awt.Graphics;
import renderer.Renderer;
import util.Delay;
import util.Global;

/**
 *
 * @author Cloud-Razer
 */
public class LowHpEffect implements Effect {

    private String imagePath;
    private int x;
    private int y;
    private int width;
    private int height;
    private boolean run;
    private Actor actor;
    private Renderer renderer;
    private Delay heartBeats;
    private int heartBeatsCount;

    public LowHpEffect(int x, int y, int width, int height, Actor actor) {
        this.imagePath = ImagePath.BLOOD[0];
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.run = false;
        this.actor = actor;
        this.renderer = new Renderer();
        this.heartBeats = new Delay(60);
        this.heartBeats.stop();
        this.heartBeatsCount = 0;
    }

    @Override
    public boolean getRun() {
        return this.run;
    }

    @Override
    public void update() {
        if (this.actor.getHp() <= 0) {
            this.heartBeats.stop();
            this.imagePath = ImagePath.BLOOD[0];
        }else if (this.actor.getHp() <= 30f) {
            this.x = (int) Global.viewX;
            this.y = (int) Global.viewY;
            this.run = true;
            this.heartBeats.start();
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
        this.renderer.setImage(this.imagePath);
        this.renderer.paint(g, this.x, this.y, this.x + this.width, this.y + this.height);
        if (this.heartBeats.isTrig()) {
            if (this.heartBeatsCount++ % 2 == 0) {
                this.heartBeats.setCounter(this.heartBeats.getDelayFrame() / 2);
                this.imagePath = ImagePath.BLOOD[4];
                MusicResourceController.getInstance().tryGetMusic(AudioPath.ACTOR_HEART_BEATS).play();
            } else {
                this.imagePath = ImagePath.BLOOD[0];
            }
        }
    }
}
