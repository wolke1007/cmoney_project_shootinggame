/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

/**
 *
 * @author Cloud-Razer
 */
public class Delay {

    private float delayFrame;
    private float counter;
    private boolean isPause;

    public Delay(float delayFrame) {
        this.delayFrame = delayFrame;
        this.counter = this.delayFrame;
        this.isPause = true;
    }
    
    public void cleanCounter(){
        this.counter = 0;
    }
    
    public void setDelayFrame(float delayFrame) {
        this.delayFrame = delayFrame;
    }

    public void start() {
        this.isPause = false;
    }

    public void stop() {
        pause();
        this.counter = this.delayFrame;
    }

    public void restart() {
        stop();
        start();
    }

    public void pause() {
        this.isPause = true;
    }
    public void click(){
        this.counter = this.delayFrame;
    }
    public float getDelayFrame(){
        return this.delayFrame;
    }

    public boolean isTrig() {
        if (!this.isPause && this.counter++ == this.delayFrame) {
            this.counter = 0;
            return true;
        }
        return false;
    }

}
