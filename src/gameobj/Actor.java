/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameobj;

import renderer.RendererToRotate;
import controllers.ImagePath;
import graph.Rect;
import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;
import renderer.Renderer;
import util.Angle;
import util.Delay;
import util.Global;
import util.Move;
import util.Point;

/**
 *
 * @author Cloud-Razer
 */
public class Actor extends GameObject {

    private int dir;
    private RendererToRotate rotateRenderer;
    private Renderer renderer;
    private Angle angle;
    private boolean isStand;
    private View view;
    private LinkedList<GameObject> allObjects;
    private float hp;
    private float hpBarWidth;
    private float dividend;
    private LinkedList<Effect> effects;

    private Delay moveDelay;

    private float moveSpeed; // per frame
    private float actMoveSpeed;

    private Move movement;

    private int moveDistance;
    
    private abstract class Effect{
        public String imagePath = "";
        public int x;
        public int y;
        public int width;
        public int height;
        public boolean run = false;
        public abstract void update();
        public abstract void paint(Graphics g);
    }
    
    private class LowHpEffect extends Effect{

        public LowHpEffect(int x, int y, int width, int height) {
            this.imagePath = ImagePath.BLOOD[0];
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.run = false;
        }
        
        @Override
        public void update(){
            if(Actor.this.hp <= 99){
                this.x = (int)Global.viewX;
                this.y = (int)Global.viewY;
                this.run = true;
            }else{
                this.run = false;
            }
            
        }
        
        @Override
        public void paint(Graphics g){
            Actor.this.renderer.setImage(this.imagePath);
            Actor.this.renderer.paint(g, this.x, this.y, this.x + this.width, this.y + this.height);
        }
    }
    
    private class DeadEffect extends Effect{

        public DeadEffect(int x, int y, int width, int height) {
            this.imagePath = ImagePath.BLOOD[0];
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.run = false;
        }
        
        @Override
        public void update(){
            if(Actor.this.hp <= 99){
                this.x = (int)Actor.this.x;
                this.y = (int)Actor.this.y;
                this.run = true;
            }else{
                this.run = false;
            }
            
        }
        
        @Override
        public void paint(Graphics g){
            Actor.this.renderer.setImage(this.imagePath);
            Actor.this.renderer.paint(g, this.x, this.y, this.x + this.width, this.y + this.height);
        }
    }
    

    public Actor(String colliderType, float x, float y, int moveSpeed, String[] path) {//src => Global.ACTOR
        super(colliderType, x, y, Global.UNIT_X, Global.UNIT_Y, Global.UNIT_X, Global.UNIT_Y);
        setAngle();
        this.rotateRenderer = new RendererToRotate(path, this, getAngle());
        this.renderer = new Renderer();
        this.isStand = true;
        setActorMoveSpeedDetail(moveSpeed);
        movement = new Move(this);
        this.moveDistance = 10;
        super.paintPriority = 0;
        setHpPoint(100);
        this.effects = new LinkedList();
        this.effects.add(new LowHpEffect((int)this.x, (int)this.y, Global.SCREEN_X, Global.SCREEN_Y));
    }//多載 建構子 當前版本

    private void setHpPoint(float dividend) {
        this.hpBarWidth = this.width();
        this.dividend = this.hpBarWidth / dividend;
    }

    public boolean subtractHp() {
        this.hpBarWidth -= this.dividend;
        return true;
    }

    public boolean increaseHp() {
        this.hpBarWidth += this.dividend;
        return true;
    }

    public float getHp() {
        this.hp = this.hpBarWidth / this.dividend;
        return this.hp;
    }

    //角度計算
    public void setAngle() {
        if (this.angle == null) {
            this.angle = new Angle(this.getCenterX(), this.getCenterY(), Global.mapMouseX, Global.mapMouseY);
            return;
        }
        this.angle.setCenterX(this.getCenterX());
        this.angle.setCenterY(this.getCenterY());
        this.angle.setGoalCenterX(Global.mapMouseX);
        this.angle.setGoalCenterY(Global.mapMouseY);
    }

    public double getAngle() {
        return this.angle.getAngle();
    }
    //角度計算end

    //角色移動相關資訊
    private void setActorMoveSpeedDetail(float moveSpeed) {
        this.moveSpeed = limitRange(moveSpeed);
        this.actMoveSpeed = 60 - this.moveSpeed;
        this.moveDelay = new Delay(this.actMoveSpeed);
        this.moveDelay.start();
    }//初始化用

    private float limitRange(float range) {
        if (range < 0) {
            return 0;
        } else if (range > 60) {
            return 60;
        }
        return range;
    }//限制範圍 0-60

    public void setMoveSpeed(float moveSpeed) {
        this.moveSpeed = limitRange(moveSpeed);
        this.actMoveSpeed = 60 - this.moveSpeed;
        this.moveDelay.setDelayFrame(this.actMoveSpeed);
    }//修改角色移動速度

    public float getMoveSpeed() {
        return this.moveSpeed;
    }//取得目前的速度設定
    //角色移動相關資訊end

    public void setStand(boolean isStand) {
        this.isStand = isStand;
        if (this.isStand) {
            this.moveDelay.stop();
        } else {
            this.moveDelay.start();
        }
    }

    @Override
    public void setDir(int dir) {
        this.dir = dir;
    }
    
    public void setAllObjects(LinkedList<GameObject> list) {
        this.allObjects = list;
    }

    public void setMovementPressedStatus(int dir, boolean status) {
        this.movement.setPressedStatus(dir, status);
    }

    public void updateRendererAngle() {
        this.setAngle();
        this.rotateRenderer.setAngle(this.getAngle());
    }
    
    private void updateEffects(){
        for(int i = 0; i < this.effects.size(); i++){
            this.effects.get(i).update();
        }
    }
    
    private void paintEffects(Graphics g){
        for(int i = 0; i < this.effects.size(); i++){
            if(this.effects.get(i).run){
                this.effects.get(i).paint(g);
            }
        }
    }

    @Override
    public void update() {
        if (!this.isStand && this.moveDelay.isTrig()) {
            move();
        }
        // 需要先移動再 RenderToRotate 避免 Actor 的圖片跟不上碰撞框的移動
        // 以下一定要更新
        updateRendererAngle();
        updateEffects();
    }

    private void move() {
        this.movement.moving(this.moveDistance, this.allObjects);
    }

    @Override
    public void paintComponent(Graphics g) {
        g.drawLine(-10000, (int) (this.getCollider().centerY() - Global.viewY), 10000, (int) (this.getCollider().centerY() - Global.viewY));
        g.drawLine((int) (this.getCollider().centerX() - Global.viewX), -10000, (int) (this.getCollider().centerX() - Global.viewX), 10000);
        this.rotateRenderer.paint(g);
        paintEffects(g);
    }

}
