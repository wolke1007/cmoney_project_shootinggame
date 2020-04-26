/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

/**
 *
 * @author Cloud-Razer
 */
public class AudioPath {

    private static final String ROOT = "/resources/sounds";
    private static final String BGM = AudioPath.ROOT + "/bgm";
    private static final String CHARACTER = "/character";
    private static final String ZOMBIE = AudioPath.ROOT + AudioPath.CHARACTER + "/zombie";
    private static final String BOSS = AudioPath.ROOT + AudioPath.CHARACTER + "/boss";
    private static final String ACTOR = AudioPath.ROOT + AudioPath.CHARACTER + "/actor";
    private static final String AMMO = AudioPath.ACTOR + "/ammo";
    private static final String ZOMBIE_BOSS = AudioPath.ROOT + AudioPath.CHARACTER + "/zombie_boss";
    private static final String COMMON = AudioPath.ROOT + "/common";
    private static final String EFFECT = AudioPath.ROOT + "/effect";

    public static final String START_MENU_SCENE = AudioPath.BGM + "/start_highScore_scene(City_of_Jewels).wav";
    public static final String MAIN_SCENE = BGM + "/main_scene(Inescapable).wav";
    public static final String BOSS_FIGHT = BGM + "/boss_fight(Ghost_Chase_Thriller).wav";
    public static final String[] ACTOR_MOVES = {AudioPath.ACTOR + "/running1.wav"};
    public static final String[] ZOMBIE_MOVES = {AudioPath.ZOMBIE + "/flesh_impact_bullet1.wav",
        AudioPath.ZOMBIE + "/flesh_impact_bullet2.wav",
        AudioPath.ZOMBIE + "/flesh_impact_bullet3.wav",
        AudioPath.ZOMBIE + "/flesh_impact_bullet4.wav",
        AudioPath.ZOMBIE + "/flesh_impact_bullet5.wav",
        AudioPath.ZOMBIE + "/moan01.wav",
        AudioPath.ZOMBIE + "/moan02.wav",
        AudioPath.ZOMBIE + "/moan09.wav",
        AudioPath.ZOMBIE + "/zombie_die(death_45).wav"};
    public static final String ZOMBIE_STEP_MOVE = AudioPath.ZOMBIE + "/zombieFootStep.wav";
    public static final String[] ZOMBIE_BOSS_MOVES = {AudioPath.ZOMBIE_BOSS + "/boss_die(charger_die_01).wav"};
    public static final String[] COMMONS = {AudioPath.COMMON + "/button.wav"};
    public static final String[][] EFFECTS = {{AudioPath.EFFECT + "/attack" + "/bomb.wav",
        AudioPath.EFFECT + "/attack" + "/minigun_fire.wav",
        AudioPath.EFFECT + "/attack" + "/smg_fire_1.wav"},
    {AudioPath.EFFECT + "/dying" + "/heartbeats.wav"},
    {AudioPath.EFFECT + "/speedup" + "/speedup_bgm.wav"}};
    public static final String AMMO_GUN_FIRE = AudioPath.AMMO + "/gunFire.wav";
    public static final String AMMO_GRENADE_READY = AudioPath.AMMO + "/grenadeReady.wav";
    public static final String AMMO_GRENADE_BOMB = AudioPath.AMMO + "/grenadeSoundBomb.wav";
    public static final String[] BOSS_FIRE_READY = {
        AudioPath.BOSS + "/bossFireReady_1.wav",
        AudioPath.BOSS + "/bossFireReady_2.wav",
        AudioPath.BOSS + "/bossFireReady_3.wav",};
    public static final String BOSS_FIRE_CONTINUE = AudioPath.BOSS + "/bossFireContinue.wav";
    public static final String BOSS_HAND_CONTINUE = AudioPath.BOSS + "/bossHandContinue.wav";
    public static final String BOSS_HAND_MOVE = AudioPath.BOSS + "/bossHandMove.wav";
    public static final String BOSS_ATTACK_BOMB = AudioPath.BOSS + "/bossAttackBomb.wav";
}
