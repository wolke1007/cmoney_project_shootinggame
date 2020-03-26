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

    private int delayFrame;
    private int counter;
    private boolean isPause;

    public Delay(int delayFrame) {
        this.delayFrame = delayFrame;
        this.counter = 0;
        this.isPause = true;
    }

    public void setDelayFrame(int delayFrame) {
        this.delayFrame = delayFrame;
    }

    public void start() {
        this.isPause = false;
    }

    public void stop() {
        pause();
        this.counter = delayFrame;
    }

    public void restart() {
        stop();
        start();
    }

    public void pause() {
        this.isPause = true;
    }

    public boolean isTrig() {
        if (!this.isPause && this.counter++ == this.delayFrame) {
            this.counter = 0;
            return true;
        }
        return false;
    }

}
