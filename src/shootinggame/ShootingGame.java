/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shootinggame;

import util.CommandSolver;
import shootinggame.GameJPanel;
import util.Global;
import java.awt.event.KeyEvent;
import javax.swing.*;

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
        GameJPanel jp = new GameJPanel();
        f.setTitle("Game Test 6th");
        f.setSize(Global.FRAME_X, Global.FRAME_Y);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(jp);
        f.setVisible(true);
        
        CommandSolver cs = new CommandSolver.Builder(jp, Global.MILLISEC_PER_UPDATE,
            new int[][]{
                {KeyEvent.VK_W, Global.UP},
                {KeyEvent.VK_UP, Global.UP},
                {KeyEvent.VK_A, Global.LEFT},
                {KeyEvent.VK_S, Global.DOWN},
                {KeyEvent.VK_D, Global.RIGHT}
            }).enableMouseTrack(jp).enableKeyboardTrack(jp)
                .keyCleanMode()
//                .keyTypedMode().trackChar()
                .gen();
        cs.start();
        
        long startTime = System.currentTimeMillis();
        long passedUpdated = 0;
        long lastRepaintTime = System.currentTimeMillis();
        int paintTimes = 0;
        long timer = System.currentTimeMillis();
        while (true) {
            long currentTime = System.currentTimeMillis();// 系統當前時間
            long totalTime = currentTime - startTime;// 從開始到現在經過的時間
            long targetTotalUpdated = totalTime / Global.MILLISEC_PER_UPDATE;// 開始到現在應該更新的次數
            // input
            // input end
            while (passedUpdated < targetTotalUpdated) {// 如果當前經過的次數小於實際應該要更新的次數
                //update 更新追上當前次數
                cs.update();
                jp.update();
                passedUpdated++;
            }
            if(currentTime - timer >= 1000){
                Global.log("FPS: " + paintTimes);
                paintTimes = 0;
                timer = currentTime;
            }
            
            if (Global.LIMIT_DELTA_TIME <= currentTime - lastRepaintTime) {
                lastRepaintTime = currentTime;
                f.repaint();
                paintTimes++;
            }
        }
    }

}
