/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

/**
 *
 * @author Cloud-Razer
 */
public class Global {
    // Debug Mode
    public static final boolean IS_DEBUG = true;
    public static void log(String str){
        if(IS_DEBUG){
            System.out.println(str);
        }
    }
    // 視窗大小
    public static final int FRAME_X = 1000;
    public static final int FRAME_Y = 600;
    public static final int SCREEN_X = FRAME_X - 8 - 8;
    public static final int SCREEN_Y = FRAME_Y - 31 - 8;
    // 資料刷新時間
    public static final int UPDATE_TIMES_PER_SEC = 60;// 每秒更新60次遊戲邏輯
    public static final int MILLISEC_PER_UPDATE = 1000 / UPDATE_TIMES_PER_SEC;// 每一次要花費的毫秒數
    // 畫面更新時間
    public static final int FRAME_LIMIT = 60;
    public static final int LIMIT_DELTA_TIME = 1000 / FRAME_LIMIT;
    // 遊戲單位大小
    public static final int UNIT_X = 32;
    public static final int UNIT_Y = 32;
    // 方向
    public static final int DOWN = 0;
    public static final int LEFT = 1;
    public static final int RIGHT = 2;
    public static final int UP = 3;
    // 步伐
    public static final int[] STEPS_WALK_NORMAL = {0, 1, 2, 1};
    public static final int[] STEPS_WALK_SHORT = {0, 2};

    public static boolean random(int rate) {
        return random(1, 100) <= rate;
    }
    //////yrt
    public static int random(int min, int max) {
        return (int) (Math.random() * (max - min + 1) + min);
    }
}
