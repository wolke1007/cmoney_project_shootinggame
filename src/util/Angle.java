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
    private int mouseX;
    private int mouseY;
    private double angle;

    public Angle(int centerX, int centerY, int mouseX, int mouseY) {
        setCenterX(centerX);
        setCenterY(centerY);
        setMouseX(mouseX);
        setMouseY(mouseY);
        this.angle = 0d;
    }

    private void setCenterX(int centerX) {
        this.centerX = centerX;
    }

    private void setCenterY(int centerY) {
        this.centerY = centerY;
    }

    private void setMouseX(int mouseX) {
        this.mouseX = mouseX;
    }

    private void setMouseY(int mouseY) {
        this.mouseY = mouseY;
    }

    private int getCenterX() {
        return this.centerX;
    }

    private int getCenterY() {
        return this.centerY;
    }

    private int getMouseX() {
        return this.mouseX;
    }

    private int getMouseY() {
        return this.mouseY;
    }
    
    public double getAngle(){
        return this.angle;
    }

    public void AngleReatan() {
        double dx = this.mouseX - this.centerX;
        double dy = this.mouseY - this.centerY;
        this.angle = Math.atan(dx / dy) / Global.PI * 180;
    }

    public void getAngleATan() {
        double dx = this.mouseX - this.centerX;
        double dy = this.mouseY - this.centerY;
        this.angle = Math.atan(dy / dx) / Global.PI * 180;
    }
}
