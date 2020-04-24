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
        ImagePath.ROOT + "/zombie/zombie_ (31).png",};
    public static final String[] ZOMBIE_SHOCK = {
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
        ImagePath.ROOT + "/monster/monster_ (25).png"};
    public static final String[] HP = {ImagePath.ROOT + "/HP_frame.png", ImagePath.ROOT + "/HP.png", ImagePath.ROOT + "/HP_debug.png"};
    public static final String[] BUILDING = {ImagePath.ROOT + "/room.png"};
    public static final String[] START_MENU = {ImagePath.ROOT + "/startmenu.jpeg",
        ImagePath.ROOT + "/playgame.png",
        ImagePath.ROOT + "/score_history.png"};
    public static final String[] HIGH_SCORE = {ImagePath.ROOT + "/high_score.png", ImagePath.ROOT + "/endless.png", ImagePath.ROOT + "/compaign.png", ImagePath.ROOT + "/saving.png"};
    public static final String[] COMMON_BUTTON = {ImagePath.ROOT + "/back.png"};
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
