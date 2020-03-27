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

    private int centerX;
    private int centerY;
    private int goalCenterX;
    private int goalCenterY;
    private double angle;

    public Angle(int centerX, int centerY, int goalCenterX, int goalCenterY) {
        setCenterX(centerX);
        setCenterY(centerY);
        setGoalCenterX(goalCenterX);
        setGoalCenterY(goalCenterY);
        this.angle = 0d;
    }

    public void setCenterX(int centerX) {
        this.centerX = centerX;
    }

    public void setCenterY(int centerY) {
        this.centerY = centerY;
    }

    public void setGoalCenterX(int goalCenterX) {
        this.goalCenterX = goalCenterX;
    }

    public void setGoalCenterY(int goalCenterY) {
        this.goalCenterY = goalCenterY;
    }

    private int getCenterX() {
        return this.centerX;
    }

    private int getCenterY() {
        return this.centerY;
    }

    private int getGoalCenterX() {
        return this.goalCenterX;
    }

    private int getGoalCenterY() {
        return this.goalCenterY;
    }

    public double getAngle() {
        angleReATan();
        return this.angle;
    }

    private void angleReATan() {
        double dx = getGoalCenterX() - getCenterX();
        double dy = getCenterY() - getGoalCenterY();
        this.angle = Math.atan(Math.abs(dx) / Math.abs(dy)) / Global.PI * 180;
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
