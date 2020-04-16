/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameobj;

import java.awt.Graphics;

/**
 *
 * @author Cloud-Razer
 */
public class Wall extends Barrier{

    public Wall(float x, float y, int width, int height) {
        super("rect", x, y, width, height);
    }
    
    @Override
    public void paintComponent(Graphics g) {
    }
}
