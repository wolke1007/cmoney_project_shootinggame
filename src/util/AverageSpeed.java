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
public class AverageSpeed {

    private double centerX;
    private double centerY;
    private double goalCenterX;
    private double goalCenterY;

    private boolean isFixedLength;//固定長度 
    private Slope slope;

    private double moveSpeed;
    private double reMoveSpeed;
    private double length;//定值分割長度

    public AverageSpeed(double centerX, double centerY, double goalCenterX, double goalCenterY, double moveSpeed, boolean isFixedLength) {
        setCenterX(centerX);
        setCenterY(centerY);
        setGoalCenterX(goalCenterX);
        setGoalCenterY(goalCenterY);
        isFixedLength(true);
        setLength(100d);
        SlopeValue();
        setMoveSpeed(moveSpeed);
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

    public void isFixedLength(boolean isFixedLength) {
        this.isFixedLength = isFixedLength;
    }

    private void SlopeValue() {
        this.slope = new Slope(getCenterX(), getCenterY(), getGoalCenterX(), getGoalCenterY());
    }

    private double getSlope() {
        return this.slope.getSlope();
    }

    public void setLength(double length) {
        this.length = length;
    }

    public double getLength() {
        return this.length;
    }

    public void setMoveSpeed(double moveSpeed) {
        this.moveSpeed = limitRange(moveSpeed);
        this.reMoveSpeed = 60 - this.moveSpeed;
    }

    private double limitRange(double moveSpeed) {
        if (moveSpeed > 59) {
            return moveSpeed = 59;
        } else if (moveSpeed < 0) {
            return moveSpeed = 0;
        }
        return moveSpeed;
    }

    public double offsetDX() {
        if (this.isFixedLength) {
            double degree = Math.atan(Math.abs(getSlope()));
            double dx = this.length * Math.cos(degree);
            return ((this.getGoalCenterX() > this.getCenterX()) ? 1 : -1) * dx / this.reMoveSpeed;
        }
        return (this.goalCenterX - this.centerX) / this.reMoveSpeed;
    }

    public double offsetDY() {
        if (this.isFixedLength) {
            double degree = Math.atan(Math.abs(getSlope()));
            double dy = this.length * Math.sin(degree);
            return ((this.centerY < this.goalCenterY) ? -1 : 1) * dy / this.reMoveSpeed;
        }
        return (this.goalCenterX - this.centerX) / this.reMoveSpeed;
    }
}
