/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shootinggame;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import util.Global;

/**
 *
 * @author Cloud-Razer
 */
public class GameJPanel {
    private class MyKeyListener implements KeyListener {
        private boolean upPress = false;
        private boolean downPress = false;
        private boolean leftPress = false;
        private boolean rightPress = false;
        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {
            actor.setStand(false);
            switch (e.getKeyCode()) {
                case KeyEvent.VK_A:
                    actor.setDir(Global.LEFT);
                    break;
                case KeyEvent.VK_W:
                    actor.setDir(Global.UP);
                    break;
                case KeyEvent.VK_S:
                    actor.setDir(Global.DOWN);
                    break;
                case KeyEvent.VK_D:
                    actor.setDir(Global.RIGHT);
                    break;
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            
            actor.setStand(true);
            pressCnt = 0;
        }
    }

    private Actor actor;

    public GameJPanel() {
        actor = new Actor(4,
                Renderer.STEPS_WALK_NORMAL,
                50,
                50);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyListener());
    }

    public void update() {
        actor.update();
    }

    @Override
    public void paintComponent(Graphics g) {
        actor.paint(g);
    }

}
