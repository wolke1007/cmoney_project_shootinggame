/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package effects;

import java.awt.Graphics;

/**
 *
 * @author Cloud-Razer
 */
public interface Effect {

    public abstract void update();

    public abstract void setImg(String src);

    public abstract void paint(Graphics g);
    
    public abstract boolean getRun();
}
