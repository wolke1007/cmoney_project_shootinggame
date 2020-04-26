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
public class Wall extends Barrier {

    public Wall(String colliderType, float x, float y, int width, int height) {
        super(colliderType, x, y, width, height);
        this.setType("Wall");
    }

    @Override
    public void paintComponent(Graphics g) {
    }
}
