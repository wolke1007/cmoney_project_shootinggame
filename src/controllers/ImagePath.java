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
    public static final String[] DOOR = {ImagePath.ROOT + "/originalDoor.png"};

    public static final String[] BARRIER = {ImagePath.ROOT + "/barrier.png"};
    public static final String[] ACTOR1 = {ImagePath.ROOT + "/selfActor.png", ImagePath.ROOT + "/selfActor_grenade.png"};
    public static final String[] BULLET = {ImagePath.ROOT + "/fire_start.png", ImagePath.ROOT + "/bullet_fire_small.png"};
    public static final String[] GRENADE = {ImagePath.ROOT + "/grenade_1.png"};
    public static final String BOOM_CONTINUE = ImagePath.ROOT + "/boom_continue.png";
    public static final String SHADOW = ImagePath.ROOT + "/shadow.png";
    public static final String[] ZOMBIE_NORMAL = {ImagePath.ROOT + "/zombie_1.png", ImagePath.ROOT + "/zombie_2.png", ImagePath.ROOT + "/zombie_3.png"};
    public static final String[] ZOMBIE_SHOCK = {ImagePath.ROOT + "/monster_1.png", ImagePath.ROOT + "/monster_2.png", ImagePath.ROOT + "/monster_3.png"};
    public static final String[] HP = {ImagePath.ROOT + "/HP_frame.png", ImagePath.ROOT + "/HP.png", ImagePath.ROOT + "/HP_debug.png"};
    public static final String[] BUILDING = {ImagePath.ROOT + "/originalRoom.png"};
    public static final String[] WELCOME_PAGE = {ImagePath.ROOT + "/title.png",
        ImagePath.ROOT + "/press_1.png", ImagePath.ROOT + "/press_2.png", ImagePath.ROOT + "/press_3.png"};
    public static final String[] MENU_PAGE = {ImagePath.ROOT + "/playgame.png", ImagePath.ROOT + "/score_history.png",
        ImagePath.ROOT + "/rank_1.png", ImagePath.ROOT + "/rank_2.png", ImagePath.ROOT + "/rank_3.png",
        ImagePath.ROOT + "/start_1.png", ImagePath.ROOT + "/start_2.png", ImagePath.ROOT + "/start_3.png",
        ImagePath.ROOT + "/intro_1.png", ImagePath.ROOT + "/intro_2.png", ImagePath.ROOT + "/intro_3.png"};
    public static final String[] INTRO_PAGE = {ImagePath.ROOT + "/operationBackShadow.png", ImagePath.ROOT + "/keyBoradOperationInfo.png"};
    public static final String[] RANK_PAGE = {ImagePath.ROOT + "/rank.png"};
    public static final String[] INFO_PAGE = {ImagePath.ROOT + "/infoStory.png",
        ImagePath.ROOT + "/skip_1.png", ImagePath.ROOT + "/skip_2.png", ImagePath.ROOT + "/skip_3.png"}; 
    public static final String[] COMMON_BUTTON = {ImagePath.ROOT + "/return_1.png", ImagePath.ROOT + "/return_2.png", ImagePath.ROOT + "/return_3.png"};
    public static final String[] COMMON_BACKGROUND = {ImagePath.ROOT + "/homePage.png", ImagePath.ROOT + "/helpPage_backShadow.png"};
    public static final String[] BLOOD = {ImagePath.ROOT + "/low_hp_1.png",
        ImagePath.ROOT + "/take_damage.png",
        ImagePath.ROOT + "/hand.png",
        ImagePath.ROOT + "/blood_drag.png"};
    public static final String BOSS = ImagePath.ROOT + "/boss/boss_1.png";
    public static final String BOSS_RIGHT_HAND = ImagePath.ROOT + "/boss/boos_hand_right_origine.png";
    public static final String BOSS_LEFT_HAND = ImagePath.ROOT + "/boss/boos_hand_left_origine.png";
    public static final String BOSS_BOOM_CONTINUE = ImagePath.ROOT + "/boss/boss_boom.png";
    public static final String BOSS_END_BOMB = ImagePath.ROOT + "/boss/boss_end_bomb.png";
    public static final String[] BOSS_HEAD = {
        ImagePath.ROOT + "/boss/boss_head.png",
        ImagePath.ROOT + "/boss/boss_head.png",
        ImagePath.ROOT + "/boss/boss_head.png"};
    public static final String[] BOSS_HEAD_FIRE = {
        ImagePath.ROOT + "/boss/boss_head_fire_1.png",
        ImagePath.ROOT + "/boss/boss_head_fire_2.png",
        ImagePath.ROOT + "/boss/boss_head_fire_3.png"};
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
}
