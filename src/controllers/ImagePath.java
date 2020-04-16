/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import util.Global;

/**
 *
 * @author Cloud
 */
public class ImagePath {
    
    private static final String ROOT = "/resources";
    
    // 圖片庫    
//    public static final String[] BACKGROUND = {ImagePath.ROOT + "/background1.png",
//        ImagePath.ROOT + "/background2.png",
//        ImagePath.ROOT + "/background3.png"};
    public static final String[] BACKGROUND = {ImagePath.ROOT + "/map.png"};

    public static final String[] BARRIER = {ImagePath.ROOT + "/barrier.png"};
    public static final String[] ACTOR1 = {ImagePath.ROOT + "/selfActor.png", ImagePath.ROOT + "/clawsmark.png"};
    public static final String[] BULLET = {ImagePath.ROOT + "/bullet_fire_small.png"};
    public static final String[] GUN = {ImagePath.ROOT + "/handGun.png"};
    public static final String SHADOW = ImagePath.ROOT + "/shadow.png";
    public static final String[] ZOMBIE_NORMAL = {ImagePath.ROOT + "/zombie_1.png", ImagePath.ROOT + "/zombie_2.png", ImagePath.ROOT + "/zombie_3.png"};
    public static final String[] ZOMBIE_SHOCK = {ImagePath.ROOT + "/shock_monster.png"};
    public static final String[] HP = {ImagePath.ROOT + "/HP_frame.png", ImagePath.ROOT + "/HP.png", ImagePath.ROOT + "/HP_debug.png"};
    public static final String[] BUILDING = {ImagePath.ROOT + "/room.png"};
    public static final String[] START_MENU = {ImagePath.ROOT + "/startmenu.jpeg",
        ImagePath.ROOT + "/playgame.png",
        ImagePath.ROOT + "/score_history.png"};
    public static final String[] HIGH_SCORE = {ImagePath.ROOT + "/high_score.png", ImagePath.ROOT + "/endless.png", ImagePath.ROOT + "/compaign.png", ImagePath.ROOT + "/saving.png"};
    public static final String[] COMMON_BUTTON = {ImagePath.ROOT + "/back.png"};
    public static final String[] BLOOD = {ImagePath.ROOT + "/low_hp.png",
        ImagePath.ROOT + "/take_damage.png",
        ImagePath.ROOT + "/hand.png",
        ImagePath.ROOT + "/blood_drag.png"};
}
