/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameobj;

import java.awt.Graphics;

/**
 *
 * @author F-NB
 */
public class Bullet{
    private RendererToRotate renderer;//旋轉方向設定
    private boolean isShootOut;//是否射擊的狀態 Origine false
//    public Bullet(int x, int y, int width, int height, int colliderWidth, int colliderHeight) {
//        super(x, y, width, height, colliderWidth, colliderHeight);
//    }

    public Bullet(String colliderType, int x, int y, int width, int height, int colliderWidth, int colliderHeight) {
    }

    public void update() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void paintComponent(Graphics g) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
