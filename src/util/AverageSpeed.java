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

    private float centerX;
    private float centerY;
    private float goalCenterX;
    private float goalCenterY;

    private boolean isFixedLength;//固定長度 
    private Slope slope;

    private float moveSpeed;
    private float reMoveSpeed;
    private float length;//定值分割長度

    public AverageSpeed(float centerX, float centerY, float goalCenterX, float goalCenterY, float moveSpeed, boolean isFixedLength) {
        setCenterX(centerX);
        setCenterY(centerY);
        setGoalCenterX(goalCenterX);
        setGoalCenterY(goalCenterY);
        isFixedLength(isFixedLength);
        setLength(100f);
        SlopeValue();
        setMoveSpeed(moveSpeed);
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

    public void isFixedLength(boolean isFixedLength) {
        this.isFixedLength = isFixedLength;
    }

    private void SlopeValue() {
        if (this.slope == null) {
            this.slope = new Slope(getCenterX(), getCenterY(), getGoalCenterX(), getGoalCenterY());
            return;
        }
        this.slope.setCenterX(getCenterX());
        this.slope.setCenterY(getCenterY());
        this.slope.setGoalCenterX(getGoalCenterX());
        this.slope.setGoalCenterY(getGoalCenterY());
    }

    private double getSlope() {
        return this.slope.getSlope();
    }

    public void setLength(float length) {
        this.length = length;
    }

    public double getLength() {
        return this.length;
    }

    public void setMoveSpeed(float moveSpeed) {
        this.moveSpeed = limitRange(moveSpeed);
        this.reMoveSpeed = 100 - this.moveSpeed;
    }

    public float getReMoveSpeed() {
        return this.reMoveSpeed;
    }

    private float limitRange(float moveSpeed) {
        if (moveSpeed > 99) {
            return moveSpeed = 99;
        } else if (moveSpeed < 0) {
            return moveSpeed = 0;
        }
        return moveSpeed;
    }

    public float offsetDX() {
        if (this.isFixedLength) {
            SlopeValue();
            double degree = Math.atan(Math.abs(getSlope()));
            double dx = this.length * Math.cos(degree);
            return (float) (((this.getGoalCenterX() > this.getCenterX()) ? 1 : -1) * dx) / this.reMoveSpeed;
        }
        return (this.goalCenterX - this.centerX) / this.reMoveSpeed;
    }

    public float offsetDY() {
        if (this.isFixedLength) {
            SlopeValue();
            double degree = Math.atan(Math.abs(getSlope()));
            double dy = this.length * Math.sin(degree);
            return (float) (((this.centerY < this.goalCenterY) ? 1 : -1) * dy) / this.reMoveSpeed;
        }
        return (this.goalCenterY - this.centerY) / this.reMoveSpeed;
    }
}
