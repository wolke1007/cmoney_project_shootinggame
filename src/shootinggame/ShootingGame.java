/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shootinggame;

import java.awt.event.KeyEvent;
import util.Global;
import javax.swing.JFrame;
import util.GameKernel;
import util.CommandSolver;

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
            {KeyEvent.VK_A, 'A'}, {KeyEvent.VK_B, 'B'}, {KeyEvent.VK_C, 'C'},
            {KeyEvent.VK_D, 'D'}, {KeyEvent.VK_E, 'E'}, {KeyEvent.VK_F, 'F'},
            {KeyEvent.VK_G, 'G'}, {KeyEvent.VK_H, 'H'}, {KeyEvent.VK_I, 'I'},
            {KeyEvent.VK_J, 'J'}, {KeyEvent.VK_K, 'K'}, {KeyEvent.VK_L, 'L'},
            {KeyEvent.VK_M, 'M'}, {KeyEvent.VK_N, 'N'}, {KeyEvent.VK_O, 'O'},
            {KeyEvent.VK_P, 'P'}, {KeyEvent.VK_Q, 'Q'}, {KeyEvent.VK_R, 'R'},
            {KeyEvent.VK_S, 'S'}, {KeyEvent.VK_T, 'T'}, {KeyEvent.VK_U, 'U'},
            {KeyEvent.VK_V, 'V'}, {KeyEvent.VK_W, 'W'}, {KeyEvent.VK_X, 'X'},
            {KeyEvent.VK_Y, 'Y'}, {KeyEvent.VK_Z, 'Z'},
            {KeyEvent.VK_0, 48}, {KeyEvent.VK_1, 49}, {KeyEvent.VK_2, 50},
            {KeyEvent.VK_3, 51}, {KeyEvent.VK_4, 52}, {KeyEvent.VK_5, 53},
            {KeyEvent.VK_6, 54}, {KeyEvent.VK_7, 55}, {KeyEvent.VK_8, 56},
            {KeyEvent.VK_9, 57},
            {KeyEvent.VK_SPACE, Global.KEY_SPACE},
            {KeyEvent.VK_ENTER, Global.KEY_ENTER},
            {KeyEvent.VK_BACK_SPACE, Global.KEY_BACK_SPACE},
            {KeyEvent.VK_CONTROL, Global.KEY_CONTROL},
            {KeyEvent.VK_LEFT, 37}, {KeyEvent.VK_UP, 38},
            {KeyEvent.VK_RIGHT, 39}, {KeyEvent.VK_DOWN, 40}
        };
        GameKernel gk = new GameKernel.Builder(gi, Global.LIMIT_DELTA_TIME, Global.MILLISEC_PER_UPDATE)
                .initListener(commands)
                .enableMouseTrack(gi)
                .enableKeyboardTrack(gi)
                .mouseForcedRelease()
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
