/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameobj;

import java.awt.Graphics;
import util.Angle;
import util.AverageSpeed;
import util.Delay;
import util.Global;

/**
 *
 * @author F-NB
 */
public class Enemy extends GameObject {

    private RendererToRotate renderer;//旋轉圖渲染器
    private int hp;//hp的總量

    //敵人對主角的移動控制
    private Delay moveDelay;
    private float moveSpeed;
    private float actMoveSpeed;
    //敵人對主角的移動控制end

    //移動分段
    private Angle angle;
    private AverageSpeed averageSpeed;
    //移動分段end

    public Enemy(String colliderType, float x, float y, float goalCenterX, float goalCenterY, int moveSpeed, String[] path) {
        super(colliderType, x, y, Global.UNIT_X, Global.UNIT_Y, Global.UNIT_X, Global.UNIT_Y);
        setAngle(super.getCenterX(), super.getCenterY(), goalCenterX, goalCenterY);//goalCenterPoint => 拿取目標的 point
        
    }

    //角度計算
    public void setAngle(float centerX, float centerY, float goalCenterX, float goalCenterY) {
        if (this.angle == null) {
            this.angle = new Angle(centerX, centerY, goalCenterX, goalCenterY);
            return;
        }
        this.angle.setCenterX(centerX);
        this.angle.setCenterY(centerY);
        this.angle.setGoalCenterX(goalCenterX);
        this.angle.setGoalCenterY(goalCenterY);
    }

    public double getAngle() {
        return this.angle.getAngle();
    }
    //角度計算end

    @Override
    public void update() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setDir(int dir) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void paintComponent(Graphics g) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
