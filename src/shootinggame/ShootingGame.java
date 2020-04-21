/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shootinggame;

import util.CommandSolver;
import util.GameKernel;
import shootinggame.GI;
import util.Global;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
/**
 *
 * @author Cloud-Razer
 */
public class ShootingGame {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        JFrame f = new JFrame();
        GI gi = new GI();
        int[][] commands = new int[][]{
            {KeyEvent.VK_W, Global.UP},
            {KeyEvent.VK_S, Global.DOWN},
            {KeyEvent.VK_A, Global.LEFT},
            {KeyEvent.VK_D, Global.RIGHT},
            {KeyEvent.VK_1, Global.KEY_1},
            {KeyEvent.VK_2, Global.KEY_2},
            {KeyEvent.VK_SPACE, Global.KEY_SPACE},
        };
        GameKernel gk = new GameKernel.Builder(gi, Global.LIMIT_DELTA_TIME, Global.MILLISEC_PER_UPDATE)
                .initListener(commands)
                .enableMouseTrack(gi)
                .enableKeyboardTrack(gi)
//                .keyTypedMode().trackChar()
                .keyCleanMode()
                .gen();

        f.setTitle("Shooting Game");
        f.setSize(Global.FRAME_X, Global.FRAME_Y);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(gk);
        f.setVisible(true);

        gk.run(Global.IS_DEBUG);
    }

}
