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

    public static void log(String str) {
        if (IS_DEBUG) {
            System.out.println(str);
        }
    }
    // 視窗大小
    public static final int FRAME_X = 1000;
    public static final int FRAME_Y = 600;
    public static final int SCREEN_X = FRAME_X - 8 - 8;
    public static final int SCREEN_Y = FRAME_Y - 31 - 8;
    // 角色視野大小
    public static final int VIEW_SIZE = 500;
    // 資料刷新時間
    public static final int UPDATE_TIMES_PER_SEC = 60;// 每秒更新60次遊戲邏輯
    public static final int MILLISEC_PER_UPDATE = 1000 / UPDATE_TIMES_PER_SEC;// 每一次要花費的毫秒數
    // 畫面更新時間
    public static final int FRAME_LIMIT = 60;
    public static final int LIMIT_DELTA_TIME = 1000 / FRAME_LIMIT;
    // 遊戲單位大小
    public static final int UNIT_X = 32;
    public static final int UNIT_Y = 32;
    // 角色
    public static final int ACTOR_X = FRAME_X / 2 - UNIT_X /2;
    public static final int ACTOR_Y = FRAME_Y / 2 - UNIT_Y /2;    
    // 方向
    public static final int UP = 0;
    public static final int DOWN = 1;
    public static final int LEFT = 2;
    public static final int RIGHT = 3;
    // 步伐
    public static final int[] STEPS_WALK_NORMAL = {0, 1, 2, 1};
    public static final int[] STEPS_WALK_SHORT = {0, 2};
    // 圖片庫    
    public static final String BACKGROUND_1 = "/resources/background1.png";
    public static final String BACKGROUND_2 = "/resources/background2.png";
    public static final String BACKGROUND_3 = "/resources/background3.png";
    public static final String ACTOR = "/resources/Actor.png";
    //角度需求
    public static final float PI = 3.14f;
    // 地圖數量
    public static final float MAP_QTY = 9;
 
    public static boolean random(int rate) {
        return random(1, 100) <= rate;
    }

    public static int random(int min, int max) {
        return (int) (Math.random() * (max - min + 1) + min);
    }
}
