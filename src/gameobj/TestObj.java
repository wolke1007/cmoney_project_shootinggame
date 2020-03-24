/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameobj;

/**
 *
 * @author Cloud-Razer
 */
import java.awt.Color;
import java.awt.Graphics;

public class TestObj extends GameObject{

    private int a;
    private int speed;
    
    public TestObj(int a, int speed, int x, int y, int width, int height, int colliderWidth, int colliderHeight) {
        super(x, y, width, height, colliderWidth, colliderHeight);
        this.a = a;
        this.speed = speed;
    }

    @Override
    public void paintComponent(Graphics g) {
        g.setColor(Color.GRAY);
        g.fillOval(rect.left(), rect.top(), rect.width(), rect.height());
        g.setColor(Color.BLACK);
    }
    
    public int getSpeed(){
        return speed;
    }

    @Override
    public void update() {
        speed += a;
        offset(speed, speed);
    }

}
