/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

/**
 *
 * @author F-NB
 */
public class Angle {

    private float centerX;
    private float centerY;
    private float goalCenterX;
    private float goalCenterY;
    private double angle;// up degree 0 turnRight add degree
    private double normalAngle;

    public Angle(float centerX, float centerY, float goalCenterX, float goalCenterY) {
        setCenterX(centerX);
        setCenterY(centerY);
        setGoalCenterX(goalCenterX);
        setGoalCenterY(goalCenterY);
        this.angle = 0d;
        this.normalAngle = 0d;
    }

    public void setCenterX(float centerX) {
        this.centerX = centerX;
    }

    public void setCenterY(float centerY) {
        this.centerY = centerY;
    }

    public void setGoalCenterX(float goalCenterX) {
        this.goalCenterX = goalCenterX;
    }

    public void setGoalCenterY(float goalCenterY) {
        this.goalCenterY = goalCenterY;
    }

    private float getCenterX() {
        return this.centerX;
    }

    private float getCenterY() {
        return this.centerY;
    }

    private float getGoalCenterX() {
        return this.goalCenterX;
    }

    private float getGoalCenterY() {
        return this.goalCenterY;
    }

    public double getAngle() {
        angleReATan();
        return this.angle;
    }

    private void angleReATan() {
        float dx = getGoalCenterX() - getCenterX();
        float dy = getCenterY() - getGoalCenterY();
        this.angle = Math.atan(Math.abs(dx) / Math.abs(dy)) / Global.PI * 180d;
        if (dx < 0 && dy > 0) {//第 2 象限
            this.angle = 360 - this.angle;
        } else if (dx < 0 && dy <= 0) {//第 3 象限
            this.angle = 180 + this.angle;
        } else if (dx >= 0 && dy < 0) {//第 4 象限
            this.angle = 180 - this.angle;
        }
    }

    public double getNormalAngle() {
        angleATan();
        return this.normalAngle;
    }

    public void angleATan() {
        float dx = getGoalCenterX() - getCenterX();
        float dy = getCenterY() - getGoalCenterY();
        this.normalAngle = Math.atan(Math.abs(dy) / Math.abs(dx)) / Global.PI * 180d;
        if (dx < 0 && dy < 0) {//第 3 象限
            this.normalAngle = 180 + this.normalAngle;
        } else if (dx < 0 && dy >= 0) {//第 2 象限
            this.normalAngle = 180 - this.normalAngle;
        } else if (dx >= 0 && dy < 0) {//第 4 象限
            this.normalAngle = 360 - this.normalAngle;
        }
    }
}
