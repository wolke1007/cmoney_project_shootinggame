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

    private double centerX;
    private double centerY;
    private double goalCenterX;
    private double goalCenterY;
    private double angle;

    public Angle(double centerX, double centerY, double goalCenterX, double goalCenterY) {
        setCenterX(centerX);
        setCenterY(centerY);
        setGoalCenterX(goalCenterX);
        setGoalCenterY(goalCenterY);
        this.angle = 0d;
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

    public double getAngle() {
        angleReATan();
        return this.angle;
    }

    private void angleReATan() {
        double dx = getGoalCenterX() - getCenterX();
        double dy = getCenterY() - getGoalCenterY();
        this.angle = Math.atan(Math.abs(dx) / Math.abs(dy)) / Global.PI * 180d;
        if (dx < 0 && dy > 0) {
            this.angle = 360 - this.angle;
        }
        if (dx > 0 && dy < 0) {
            this.angle = 180 - this.angle;
        }
        if (dx < 0 && dy < 0) {
            this.angle = 180 + this.angle;
        }
    }
}
