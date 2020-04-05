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
    public static final String[] BACKGROUND = {"/resources/background1.png", 
                                               "/resources/background2.png", 
                                               "/resources/background3.png"};

    public static final String ACTOR = ImagePath.ROOT + "/Actor_sample.png";
    public static final String[] ACTOR1 = {ImagePath.ROOT + "/Actor_sample.png",ImagePath.ROOT + "/Actor_sample2.png"};
    public static final String[] BULLET = {ImagePath.ROOT+"/bullet.png"};
}
