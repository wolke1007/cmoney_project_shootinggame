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
    private static final String LOADINGPAGE = ImagePath.ROOT + "/loadingPage";
    private static final String AMMO = ImagePath.ROOT + "/ammo";
    // 圖片庫    
//    public static final String[] BACKGROUND = {ImagePath.ROOT + "/background1.png",
//        ImagePath.ROOT + "/background2.png",
//        ImagePath.ROOT + "/background3.png"};
    public static final String[] BACKGROUND = {ImagePath.ROOT + "/background1.png"};
    public static final String[] DOOR = {ImagePath.ROOT + "/originalDoor.png"};
    public static final String[] LOADING_PAGE = {
        ImagePath.LOADINGPAGE + "/loadingPage_1.png",
        ImagePath.LOADINGPAGE + "/loadingPage_2.png",
        ImagePath.LOADINGPAGE + "/loadingPage_3.png",
        ImagePath.LOADINGPAGE + "/loadingPage_4.png",
        ImagePath.LOADINGPAGE + "/loadingPage_5.png",
        ImagePath.LOADINGPAGE + "/loadingPage_6.png",
        ImagePath.LOADINGPAGE + "/loadingPage_7.png",
        ImagePath.LOADINGPAGE + "/loadingPage_8.png",
        ImagePath.LOADINGPAGE + "/loadingPage_9.png",
        ImagePath.LOADINGPAGE + "/loadingPage_10.png",
        ImagePath.LOADINGPAGE + "/loadingPage_11.png",
        ImagePath.LOADINGPAGE + "/loadingPage_12.png"};
    public static final String[] BARRIER = {ImagePath.ROOT + "/barrier.png", ImagePath.ROOT + "/box.png"};
    public static final String[] ACTOR1 = {ImagePath.ROOT + "/selfActor.png", ImagePath.ROOT + "/selfActor_grenade.png"};
    public static final String[] BULLET = {ImagePath.ROOT + "/fire_start.png", ImagePath.ROOT + "/bullet_fire_small.png"};
    public static final String[] GRENADE = {ImagePath.ROOT + "/grenade_1.png"};
    public static final String BOOM_CONTINUE = ImagePath.ROOT + "/boom_continue.png";
    public static final String SHADOW = ImagePath.ROOT + "/shadow.png";
    public static final String[] ZOMBIE_NORMAL = {
        ImagePath.ROOT + "/zombie/zombie_ (1).png",
        ImagePath.ROOT + "/zombie/zombie_ (2).png",
        ImagePath.ROOT + "/zombie/zombie_ (3).png",
        ImagePath.ROOT + "/zombie/zombie_ (4).png",
        ImagePath.ROOT + "/zombie/zombie_ (5).png",
        ImagePath.ROOT + "/zombie/zombie_ (6).png",
        ImagePath.ROOT + "/zombie/zombie_ (7).png",
        ImagePath.ROOT + "/zombie/zombie_ (8).png",
        ImagePath.ROOT + "/zombie/zombie_ (9).png",
        ImagePath.ROOT + "/zombie/zombie_ (10).png",
        ImagePath.ROOT + "/zombie/zombie_ (11).png",
        ImagePath.ROOT + "/zombie/zombie_ (12).png",
        ImagePath.ROOT + "/zombie/zombie_ (13).png",
        ImagePath.ROOT + "/zombie/zombie_ (14).png",
        ImagePath.ROOT + "/zombie/zombie_ (15).png",
        ImagePath.ROOT + "/zombie/zombie_ (16).png",
        ImagePath.ROOT + "/zombie/zombie_ (17).png",
        ImagePath.ROOT + "/zombie/zombie_ (18).png",
        ImagePath.ROOT + "/zombie/zombie_ (19).png",
        ImagePath.ROOT + "/zombie/zombie_ (20).png",
        ImagePath.ROOT + "/zombie/zombie_ (21).png",
        ImagePath.ROOT + "/zombie/zombie_ (22).png",
        ImagePath.ROOT + "/zombie/zombie_ (23).png",
        ImagePath.ROOT + "/zombie/zombie_ (24).png",
        ImagePath.ROOT + "/zombie/zombie_ (25).png",
        ImagePath.ROOT + "/zombie/zombie_ (26).png",
        ImagePath.ROOT + "/zombie/zombie_ (27).png",
        ImagePath.ROOT + "/zombie/zombie_ (28).png",
        ImagePath.ROOT + "/zombie/zombie_ (29).png",
        ImagePath.ROOT + "/zombie/zombie_ (30).png",
        ImagePath.ROOT + "/zombie/zombie_ (31).png",
        ImagePath.ROOT + "/zombieDead/zombieDead_ (1).png",
        ImagePath.ROOT + "/zombieDead/zombieDead_ (2).png",
        ImagePath.ROOT + "/zombieDead/zombieDead_ (3).png",
        ImagePath.ROOT + "/zombieDead/zombieDead_ (4).png",
        ImagePath.ROOT + "/zombieDead/zombieDead_ (5).png",
        ImagePath.ROOT + "/zombieDead/zombieDead_ (6).png",
        ImagePath.ROOT + "/zombieDead/zombieDead_ (7).png",
        ImagePath.ROOT + "/zombieDead/zombieDead_ (8).png",
        ImagePath.ROOT + "/zombieDead/zombieDead_ (9).png",
        ImagePath.ROOT + "/zombieDead/zombieDead_ (10).png",
        ImagePath.ROOT + "/zombieDead/zombieDead_ (11).png",
        ImagePath.ROOT + "/zombieDead/zombieDead_ (12).png",
        ImagePath.ROOT + "/zombieDead/zombieDead_ (13).png",
        ImagePath.ROOT + "/zombieDead/zombieDead_ (14).png",
        ImagePath.ROOT + "/zombieDead/zombieDead_ (15).png",
        ImagePath.ROOT + "/zombieDead/zombieDead_ (16).png",
        ImagePath.ROOT + "/zombieDead/zombieDead_ (17).png",
        ImagePath.ROOT + "/zombieDead/zombieDead_ (18).png",
        ImagePath.ROOT + "/zombieDead/zombieDead_ (19).png",
        ImagePath.ROOT + "/zombieDead/zombieDead_ (20).png",};
    public static final String[] ZOMBIE_MONSTER = {
        ImagePath.ROOT + "/monster/monster_ (1).png",
        ImagePath.ROOT + "/monster/monster_ (2).png",
        ImagePath.ROOT + "/monster/monster_ (3).png",
        ImagePath.ROOT + "/monster/monster_ (4).png",
        ImagePath.ROOT + "/monster/monster_ (5).png",
        ImagePath.ROOT + "/monster/monster_ (6).png",
        ImagePath.ROOT + "/monster/monster_ (7).png",
        ImagePath.ROOT + "/monster/monster_ (8).png",
        ImagePath.ROOT + "/monster/monster_ (9).png",
        ImagePath.ROOT + "/monster/monster_ (10).png",
        ImagePath.ROOT + "/monster/monster_ (11).png",
        ImagePath.ROOT + "/monster/monster_ (12).png",
        ImagePath.ROOT + "/monster/monster_ (13).png",
        ImagePath.ROOT + "/monster/monster_ (14).png",
        ImagePath.ROOT + "/monster/monster_ (15).png",
        ImagePath.ROOT + "/monster/monster_ (16).png",
        ImagePath.ROOT + "/monster/monster_ (17).png",
        ImagePath.ROOT + "/monster/monster_ (18).png",
        ImagePath.ROOT + "/monster/monster_ (19).png",
        ImagePath.ROOT + "/monster/monster_ (20).png",
        ImagePath.ROOT + "/monster/monster_ (21).png",
        ImagePath.ROOT + "/monster/monster_ (22).png",
        ImagePath.ROOT + "/monster/monster_ (23).png",
        ImagePath.ROOT + "/monster/monster_ (24).png",
        ImagePath.ROOT + "/monster/monster_ (25).png",
        ImagePath.ROOT + "/monsterDead/zombieDead_ (1).png",
        ImagePath.ROOT + "/monsterDead/zombieDead_ (2).png",
        ImagePath.ROOT + "/monsterDead/zombieDead_ (3).png",
        ImagePath.ROOT + "/monsterDead/zombieDead_ (4).png",
        ImagePath.ROOT + "/monsterDead/zombieDead_ (5).png",
        ImagePath.ROOT + "/monsterDead/zombieDead_ (6).png",
        ImagePath.ROOT + "/monsterDead/zombieDead_ (7).png",
        ImagePath.ROOT + "/monsterDead/zombieDead_ (8).png",
        ImagePath.ROOT + "/monsterDead/zombieDead_ (9).png",
        ImagePath.ROOT + "/monsterDead/zombieDead_ (10).png",
        ImagePath.ROOT + "/monsterDead/zombieDead_ (11).png",
        ImagePath.ROOT + "/monsterDead/zombieDead_ (12).png",
        ImagePath.ROOT + "/monsterDead/zombieDead_ (13).png",
        ImagePath.ROOT + "/monsterDead/zombieDead_ (14).png",
        ImagePath.ROOT + "/monsterDead/zombieDead_ (15).png",
        ImagePath.ROOT + "/monsterDead/zombieDead_ (16).png",
        ImagePath.ROOT + "/monsterDead/zombieDead_ (17).png",
        ImagePath.ROOT + "/monsterDead/zombieDead_ (18).png",
        ImagePath.ROOT + "/monsterDead/zombieDead_ (19).png"
    };
    public static final String[] HP = {ImagePath.ROOT + "/HP_frame.png", ImagePath.ROOT + "/HP.png", ImagePath.ROOT + "/HP_debug.png"};
    public static final String[] BUILDING = {ImagePath.ROOT + "/originalRoom.png"};
    public static final String[] WELCOME_PAGE = {ImagePath.ROOT + "/title.png",
        ImagePath.ROOT + "/press_1.png", ImagePath.ROOT + "/press_2.png", ImagePath.ROOT + "/press_3.png"};
    public static final String[] PLAY_AGAIN = {ImagePath.ROOT + "/title.png",
        ImagePath.ROOT + "/playAgain_1.png", ImagePath.ROOT + "/playAgain_2.png", ImagePath.ROOT + "/playAgain_3.png"};
    public static final String[] MENU_PAGE = {
        ImagePath.ROOT + "/rank_1.png", ImagePath.ROOT + "/rank_2.png", ImagePath.ROOT + "/rank_3.png",
        ImagePath.ROOT + "/start_1.png", ImagePath.ROOT + "/start_2.png", ImagePath.ROOT + "/start_3.png",
        ImagePath.ROOT + "/intro_1.png", ImagePath.ROOT + "/intro_2.png", ImagePath.ROOT + "/intro_3.png"};
    public static final String[] INTRO_PAGE = {ImagePath.ROOT + "/keyBoradOperationInfo.png"};
    public static final String[] RANK_PAGE = {ImagePath.ROOT + "/rank.png"};
    public static final String[] INFO_PAGE = {ImagePath.ROOT + "/infoStory.png",
        ImagePath.ROOT + "/skip_1.png", ImagePath.ROOT + "/skip_2.png", ImagePath.ROOT + "/skip_3.png"};
    public static final String[] COMMON_BUTTON = {ImagePath.ROOT + "/return_1.png", ImagePath.ROOT + "/return_2.png", ImagePath.ROOT + "/return_3.png"};
    public static final String[] COMMON_BACKGROUND = {ImagePath.ROOT + "/homePage.png", ImagePath.ROOT + "/helpPage_backShadow.png", ImagePath.ROOT + "/operationBackShadow.png"};
    public static final String[] BLOOD = {ImagePath.ROOT + "/low_hp_1.png",
        ImagePath.ROOT + "/take_damage.png",
        ImagePath.ROOT + "/hand.png",
        ImagePath.ROOT + "/blood_drag.png",
        ImagePath.ROOT + "/low_hp.png"};
    public static final String BOSS = ImagePath.ROOT + "/boss/boss_1.png";
    public static final String BOSS_RIGHT_HAND = ImagePath.ROOT + "/boss/boos_hand_right_origine.png";
    public static final String BOSS_LEFT_HAND = ImagePath.ROOT + "/boss/boos_hand_left_origine.png";
    public static final String BOSS_BOOM_CONTINUE = ImagePath.ROOT + "/boss/boss_boom.png";
    public static final String BOSS_END_BOMB = ImagePath.ROOT + "/boss/boss_end_bomb.png";
    public static final String[] BOSS_HEAD = {
        ImagePath.ROOT + "/boss/boss_head.png",
        ImagePath.ROOT + "/boss/boss_head.png",
        ImagePath.ROOT + "/boss/boss_head.png"};
    public static final String[] TEACHER_HEAD = {
        ImagePath.ROOT + "/boss/teacherHead.png",
        ImagePath.ROOT + "/boss/teacherHead.png",
        ImagePath.ROOT + "/boss/teacherHead.png"};
    public static final String[] BOSS_HEAD_FIRE = {
        ImagePath.ROOT + "/boss/boss_head_fire_1.png",
        ImagePath.ROOT + "/boss/boss_head_fire_2.png",
        ImagePath.ROOT + "/boss/boss_head_fire_3.png"};
    public static final String[] TEACHER_HEAD_FIRE = {
        ImagePath.ROOT + "/boss/teacherHead_1.png",
        ImagePath.ROOT + "/boss/teacherHead_2.png",
        ImagePath.ROOT + "/boss/teacherHead_3.png"};
    public static final String[] BOSS_ATTACK_LEFTHAND = {
        ImagePath.ROOT + "/boss/boos_hand_left.png",
        ImagePath.ROOT + "/boss/boos_hand_left.png",
        ImagePath.ROOT + "/boss/boos_hand_left.png",
        ImagePath.ROOT + "/boss/boos_hand_left_1.png",
        ImagePath.ROOT + "/boss/boos_hand_left_2.png",
        ImagePath.ROOT + "/boss/boos_hand_left_3.png",
        ImagePath.ROOT + "/boss/boos_hand_left_2.png"};
    public static final String[] BOSS_ATTACK_RIGHTHAND = {
        ImagePath.ROOT + "/boss/boos_hand_right.png",
        ImagePath.ROOT + "/boss/boos_hand_right.png",
        ImagePath.ROOT + "/boss/boos_hand_right.png",
        ImagePath.ROOT + "/boss/boos_hand_right_1.png",
        ImagePath.ROOT + "/boss/boos_hand_right_2.png",
        ImagePath.ROOT + "/boss/boos_hand_right_3.png",
        ImagePath.ROOT + "/boss/boos_hand_right_2.png"};
    public static final String[] BOSS_ATTACK_FIREBALL = {
        ImagePath.ROOT + "/boss/boss_fireBall_1.png",
        ImagePath.ROOT + "/boss/boss_fireBall_2.png",
        ImagePath.ROOT + "/boss/boss_fireBall_3.png",
        ImagePath.ROOT + "/boss/boss_fireBall_4.png",
        ImagePath.ROOT + "/boss/boss_fireBall_5.png",
        ImagePath.ROOT + "/boss/boss_fireBall_6.png",
        ImagePath.ROOT + "/boss/boss_fireBall_7.png"};
    public static final String[] BOSS_DARK_BARRIER = {ImagePath.ROOT + "/boss/bossDarkBarrier.png"};
    public static final String[] ENDING = {ImagePath.ROOT + "/bad_ending.png", ImagePath.ROOT + "/good_ending.png"};
    public static final String[] AMMO_PISTOL = {ImagePath.AMMO + "/ammoVirtual_(1).png", ImagePath.AMMO + "/ammo_(1).png"};
    public static final String[] AMMO_RIFLE = {ImagePath.AMMO + "/ammoVirtual_(2).png", ImagePath.AMMO + "/ammo_(2).png"};
    public static final String[] AMMO_GRENADE = {ImagePath.AMMO + "/ammoVirtual_(3).png", ImagePath.AMMO + "/ammo_(3).png"};
}
