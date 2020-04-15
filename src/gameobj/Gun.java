/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameobj;

import controllers.ImagePath;
import java.awt.Graphics;
import renderer.Renderer;
import renderer.RendererToRotate;
import util.Delay;
import util.Global;

/**
 *
 * @author F-NB
 */
public class Gun extends GameObject {

    private RendererToRotate renderer;
    private Renderer rendererShadow;//畫影子

    //浮動控制
    private Delay moveDelay;
    private float moveSpeed;
    private float actMoveSpeed;
    private int moveMent;//移動量
    private int change;
    private float angle;
    private float shadowWidth;
    //浮動控制end

    private int bulletNum;//給予的子彈數量

    public Gun(String colliderType, float x, float y, GameObject target, String type, String[] path) {
        super(colliderType, x, y, Global.UNIT_MIN * 4, Global.UNIT_MIN * 4, Global.UNIT_MIN * 4, Global.UNIT_MIN * 4);
        this.renderer = new RendererToRotate(path, this, 0);//讓圖片固定在 0 度
        this.rendererShadow = new Renderer();
        this.rendererShadow.setImage(ImagePath.SHADOW);
        this.setMoveSpeedDetail(55);
        this.setType(type);//告知是甚麼槍
        this.bulletNum = Global.random(10, 20);
        this.moveMent = 1;
        this.change = -1;
        this.angle = 0;
        this.shadowWidth = 0;
    }

    //狀態控制 //目的為了升降的效果
    private void setMoveSpeedDetail(float moveSpeed) {
        this.moveSpeed = limitRange(moveSpeed);
        this.actMoveSpeed = 60 - this.moveSpeed;
        this.moveDelay = new Delay(this.actMoveSpeed);
        this.moveDelay.start();
    }

    private float limitRange(float range) {
        if (range < 0) {
            return 0;
        } else if (range > 60) {
            return 60;
        }
        return range;
    }
    //狀態控制end

    public int getBulletNum() {
        return this.bulletNum;
    }//給予角色的子彈數量

    private void move() {
        if (this.angle == 361) {
            this.angle = 0;
        }
        if (this.moveDelay.isTrig()) {
            if (this.moveMent % 10 == 0) {
                this.change *= -1;
            }
            this.renderer.setAngle(this.angle);
            this.offset(0, this.change);
            this.moveMent++;
            this.angle += 0.5f;
            this.shadowWidth += 0.3f * this.change;
        }
    }
    //狀態控制end

    @Override
    public void update() {
        this.move();
    }

    @Override
    public void paintComponent(Graphics g) {
        this.renderer.paint(g);
        int a = 32;
        this.rendererShadow.paint(g, (int) (this.getX() - this.shadowWidth),
                (int) (this.getY() + a - this.shadowWidth / 0.3),
                (int) (this.getX() + a + this.shadowWidth),
                (int) (this.getY() + a + 5 - this.shadowWidth / 0.3));
    }

    @Override
    public void setDir(int dir) {
    }//用不到

}
