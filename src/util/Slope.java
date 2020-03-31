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
public class Slope {//斜率計算

    private float centerX;
    private float centerY;
    private float goalCenterX;
    private float goalCenterY;
    private double slope;

    public Slope(float centerX, float centerY, float goalCenterX, float goalCenterY) {
        setCenterX(centerX);
        setCenterY(centerY);
        setGoalCenterX(goalCenterX);
        setGoalCenterY(goalCenterY);
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

    public double getSlope() {
        slopeValue();
        return this.slope;
    }

    private void slopeValue() {
        float dx = getGoalCenterX() - getCenterX();
        float dy = getCenterY() - getGoalCenterY();
        this.slope = dy / dx;
        if (dx < 0 && dy < 0) {
            this.slope *= -1;
        }
        if (dx < 0 && dy < 0) {
            this.slope *= -1;
        }
    }
}
