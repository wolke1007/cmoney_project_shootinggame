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
    public static final String[] BACKGROUND = {ImagePath.ROOT + "/background1.png",
        ImagePath.ROOT + "/background2.png",
        ImagePath.ROOT + "/background3.png"};

    public static final String ACTOR = ImagePath.ROOT + "/Actor_sample.png";
    public static final String[] BARRIER = {ImagePath.ROOT + "/barrier.png"};
    public static final String[] ACTOR1 = {ImagePath.ROOT + "/Actor_new12.png",ImagePath.ROOT + "/Actor_sample2.png"};
    public static final String[] BULLET = {ImagePath.ROOT+"/bullet_fire_small.png"};
    public static final String[] ENEMY = {ImagePath.ROOT+"/Actor_new13.png"};
    public static final String[] HP = {ImagePath.ROOT+"/HP_frame.png", ImagePath.ROOT+"/HP.png", ImagePath.ROOT+"/HP_debug.png"};
    public static final String[] BUILDING = {ImagePath.ROOT+"/roof1.jpg"};
}
