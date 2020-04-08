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
    public static final int FRAME_X = 800;
    public static final int FRAME_Y = 700;
    public static final int SCREEN_X = FRAME_X - 8 - 8;
    public static final int SCREEN_Y = FRAME_Y - 31 - 8;
    // 角色視野大小
    public static final int VIEW_WIDTH = SCREEN_X;
    public static final int VIEW_HEIGHT = SCREEN_Y;
    // 地圖大小
    public static final int MAP_WIDTH = SCREEN_X;
    public static final int MAP_HEIGHT = SCREEN_Y;
    // 資料刷新時間
    public static final int UPDATE_TIMES_PER_SEC = 60;// 每秒更新60次遊戲邏輯
    public static final int MILLISEC_PER_UPDATE = 1000 / UPDATE_TIMES_PER_SEC;// 每一次要花費的毫秒數
    // 畫面更新時間
    public static final int FRAME_LIMIT = 60;
    public static final int LIMIT_DELTA_TIME = 1000 / FRAME_LIMIT;
    // 遊戲單位大小
    public static final int UNIT_X = 64;
    public static final int UNIT_Y = 64;
    // 角色於地圖位置
    public static final int DEFAULT_ACTOR_X = FRAME_X / 2 - UNIT_X / 2;
    public static final int DEFAULT_ACTOR_Y = FRAME_Y / 2 - UNIT_Y / 2;
    // 方向
    public static final int UP = 1;
    public static final int DOWN = 2;
    public static final int LEFT = 4;
    public static final int RIGHT = 8;
    public static final int UP_RIGHT = UP + RIGHT;
    public static final int DOWN_RIGHT = DOWN + RIGHT;
    public static final int UP_LEFT = UP + LEFT;
    public static final int DOWN_LEFT = DOWN + LEFT;
    // 步伐
    public static final int[] STEPS_WALK_NORMAL = {0, 1, 2, 1};
    public static final int[] STEPS_WALK_SHORT = {0, 2};
    public static final int[] STEPS_WALK_ACTOR = null;
    // 圖片庫    
//    public static final String BACKGROUND_1 = "/resources/background1.png";
//    public static final String BACKGROUND_2 = "/resources/background2.png";
//    public static final String BACKGROUND_3 = "/resources/background3.png";
//    public static final String ACTOR = "/resources/Actor.png";
//    private static final String ROOT = "/resources";
//    public static final String ACTOR = Global.ROOT + "/Actor_sample.png";
//    public static final String[] ACTOR1 = {Global.ROOT + "/Actor_sample.png",Global.ROOT + "/Actor_sample2.png"};
//    public static final String[] BULLET = {Global.ROOT+"/bullet.png"};
    //角度需求
    public static final float PI = 3.14f;
    // 地圖數量
    public static final int MAP_QTY = 9;
    // 地圖邊界
    public static int mapEdgeUp;
    public static int mapEdgeDown;
    public static int mapEdgeLeft;
    public static int mapEdgeRight;
    //滑鼠當前的座標
    public static int mouseX;
    public static int mouseY;
    // view 當前座標
    public static float viewX;
    public static float viewY;
    //滑鼠當前的相對座標
    public static float mapMouseX;
    public static float mapMouseY;
    //測試用按鍵
    public static int mouseState;
    // HP 圖案資訊
    public static final int hpFrameWidth = 199;
    public static final int hpFrameHeight = 31;
    public static final int hpFrameImgW = 628;
    public static final int hpFrameImgH = 146;
    public static final int hpWidth = 176;
    public static final int hpHeight = 30;
    public static final int hpImgW = 555;
    public static final int hpImgH = 74;
    public static boolean random(int rate) {
        return random(1, 100) <= rate;
    }

    public static int random(int min, int max) {
        return (int) (Math.random() * (max - min + 1) + min);
    }
}
