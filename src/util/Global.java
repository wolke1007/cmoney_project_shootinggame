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
    public static final boolean IS_DEBUG = false;

    public static void log(String str) {
        if (IS_DEBUG) {
            System.out.println(str);
        }
    }
    // 視窗大小
    public static final int EDGE = 8;
    public static final int FRAME_X = 1600;
    public static final int FRAME_Y = 900;
    public static final int SCREEN_X = FRAME_X - EDGE - EDGE;
    public static final int SCREEN_Y = FRAME_Y - 31 - EDGE;
    // 角色視野大小
    public static final int VIEW_WIDTH = SCREEN_X;
    public static final int VIEW_HEIGHT = SCREEN_Y;
    // 地圖大小
    public static final int MAP_WIDTH = SCREEN_X;
    public static final int MAP_HEIGHT = SCREEN_Y;
    // 資料刷新時間
    public static final int UPDATE_TIMES_PER_SEC = 60;// 每秒更新60次遊戲邏輯
    public static final int MILLISEC_PER_UPDATE = 1000000000 / UPDATE_TIMES_PER_SEC;// 每一次要花費的毫秒數
    // 畫面更新時間
    public static final int FRAME_LIMIT = 60;
    public static final int LIMIT_DELTA_TIME = 1000000000 / FRAME_LIMIT;
    // 遊戲單位大小
    public static final int UNIT_X = 64;
    public static final int UNIT_Y = 64;
    private static final int HALF = 2;
    public static final int UNIT_MIN = 24 / Global.HALF;//子彈 像素
    // 地圖數量
    public static final int MAP_QTY = 6;
    // 角色於地圖位置
//    public static final int DEFAULT_ACTOR_X = (int) (FRAME_X / 3 - (UNIT_X * 2 / 3));
//    public static final int DEFAULT_ACTOR_Y = (int) (FRAME_Y / 3 - (UNIT_Y * 2 / 3));
    public static final int DEFAULT_ACTOR_X = (int) (FRAME_X / 2) - (UNIT_X / 2);
    public static final int DEFAULT_ACTOR_Y = (int) (FRAME_Y / 2) - (UNIT_Y) + 15;
    // 方向 
    public static final int UP = 87;
    public static final int DOWN = 83;
    public static final int LEFT = 65;
    public static final int RIGHT = 68;
    public static final int UP_RIGHT = UP + RIGHT;
    public static final int DOWN_RIGHT = DOWN + RIGHT;
    public static final int UP_LEFT = UP + LEFT;
    public static final int DOWN_LEFT = DOWN + LEFT;
    //槍的模式
    public static final int KEY_1 = 49;
    public static final int KEY_2 = 50;
    public static final int KEY_SPACE = 0;
    public static final int KEY_ENTER = -1;
    public static final int KEY_BACK_SPACE = -2;
    //操作
    public static final int KEY_CONTROL = -3;
    // 步伐
    public static final int[] STEPS_WALK_NORMAL = {0, 1, 2, 1};
    public static final int[] STEPS_WALK_SHORT = {0, 2};
    public static final int[] STEPS_WALK_ACTOR = null;
    //敵人上限
    public static final int ENEMY_LIMIT = 1;
    //碰撞判斷    
    public static final String[] EXCLUDE = {"Map", "Ammo"};
    public static final String[] INNER = {"Maps"};
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
    //敵人進入view聲效控制
    public static boolean enemyAudio;
    // HP 圖案資訊
    public static final int HP_FRAME_WIDTH = 199;
    public static final int HP_FRAME_HEIGHT = 31;
    public static final int HP_FRAME_IMG_W = 628;
    public static final int HP_FRAME_IMG_H = 146;
    public static final int HP_WIDTH = 176;
    public static final int HP_HEIGHT = 30;
    public static final int HP_IMG_W = 555;
    public static final int HP_IMG_H = 74;
    // 牆壁寬度
    public static final int WALL_THICK = 30;
    // 門高度
    public static final int DOOR_LENGTH = 150;
    // 預設遊戲勝利時間
    public static final float PIVOT_TIME = 30000; // 基準時間，單位為毫秒，例: 如果預計玩家 2 分鐘內能跑完全程，則設為 2 x 60 x 1000 = 120000

    public static boolean random(int rate) {
        return random(1, 100) <= rate;
    }

    public static int random(int min, int max) {
        return (int) (Math.random() * (max - min + 1) + min);
    }
}
