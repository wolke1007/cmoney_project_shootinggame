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

    public LowHpEffect(int x, int y, int width, int height, Actor actor) {
        this.imagePath = ImagePath.BLOOD[0];
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.run = false;
        this.actor = actor;
        this.renderer = new Renderer();
    }

    @Override
    public boolean getRun() {
        return this.run;
    }

    @Override
    public void update() {
        if (this.actor.getHp() <= 15f) {
            this.x = (int) Global.viewX;
            this.y = (int) Global.viewY;
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
        this.renderer.setImage(this.imagePath);
        this.renderer.paint(g, this.x, this.y, this.x + this.width, this.y + this.height);
    }

}
