/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shootinggame;

import javax.swing.JFrame;
import static util.Global.LIMIT_DELTA_TIME;
import static util.Global.MILLISEC_PER_UPDATE;
import static util.Global.SCREEN_X;
import static util.Global.SCREEN_Y;

/**
 *
 * @author Cloud-Razer
 */
public class ShootingGame {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // 資料刷新時間
        JFrame f = new JFrame();
        GameJPanel jp = new GameJPanel();
        f.setSize(SCREEN_X, SCREEN_Y);
//        f.add(jp);
//        Scanner sc = new Scanner(System.in);
//        sc.nextLine();
        f.setTitle("Shooting Game");
        f.add(jp);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
        long startTime = System.currentTimeMillis();
        long passedUpdated = 0;
        int paintTimes = 0;
        long timer = System.currentTimeMillis();
        long lastRepaintTime = System.currentTimeMillis();
        long lastUpdated = 0;
        while(true){ // 60FPS
            long currentTime = System.currentTimeMillis(); // 系統當前時間
            long totalTime = currentTime - startTime; // 從開始到現在經過的時間
            long targetTotalUpdated = totalTime / MILLISEC_PER_UPDATE; // 開始到現在應該要更新的次數
            while(passedUpdated < targetTotalUpdated){ // 如果當前經過的次數小於實際應該要更新邏輯的次數
                //  udpate 更新邏輯追上當前次數
                jp.update();
                passedUpdated++;
            }
            if(currentTime - timer >= 1000){
                System.out.println("FPS:" + paintTimes);
                paintTimes = 0;
                timer = currentTime;
            }
            // 如果時間還沒超過限制的速率 LIMIT_DELTA_TIME，則不印，否則會印過快
            if(LIMIT_DELTA_TIME <= currentTime - lastRepaintTime){
                lastRepaintTime = currentTime;
                f.repaint();
                paintTimes++;
            }

        }
       
        
    }
    
}
