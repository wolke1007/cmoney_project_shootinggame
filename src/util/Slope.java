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

    private double centerX;
    private double centerY;
    private double goalCenterX;
    private double goalCenterY;
    private double slope;

    public Slope(double centerX, double centerY, double goalCenterX, double goalCenterY) {
        setCenterX(centerX);
        setCenterY(centerY);
        setGoalCenterX(goalCenterX);
        setGoalCenterY(goalCenterY);
    }

    public void setCenterX(double centerX) {
        this.centerX = centerX;
    }

    public void setCenterY(double centerY) {
        this.centerY = centerY;
    }

    public void setGoalCenterX(double goalCenterX) {
        this.goalCenterX = goalCenterX;
    }

    public void setGoalCenterY(double goalCenterY) {
        this.goalCenterY = goalCenterY;
    }

    private double getCenterX() {
        return this.centerX;
    }

    private double getCenterY() {
        return this.centerY;
    }

    private double getGoalCenterX() {
        return this.goalCenterX;
    }

    private double getGoalCenterY() {
        return this.goalCenterY;
    }

    public double getSlope() {
        slopeValue();
        return this.slope;
    }

    private void slopeValue() {
        double dx = getGoalCenterX() - getCenterX();
        double dy = getCenterY() - getGoalCenterY();
        this.slope = dy / dx;
        if (dx < 0 && dy < 0) {
            this.slope *= -1;
        }
        if (dx < 0 && dy < 0) {
            this.slope *= -1;
        }
    }
}
