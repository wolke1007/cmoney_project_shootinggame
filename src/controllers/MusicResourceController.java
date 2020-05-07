/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.applet.Applet;
import java.applet.AudioClip;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import util.Global;

/**
 *
 * @author F-NB
 */
public class MusicResourceController {

    private static MusicResourceController mrc;
    private Map<String, AudioClip> musicPairs;

    private MusicResourceController() {
        this.musicPairs = new HashMap<>();
    }

    public static MusicResourceController getInstance() {
        if (MusicResourceController.mrc == null) {
            MusicResourceController.mrc = new MusicResourceController();
        }
        return MusicResourceController.mrc;
    }

    public AudioClip tryGetMusic(String path) {
        if (this.musicPairs.containsKey(path)) {
            return this.musicPairs.get(path);
        }
        return addMusic(path);
    }

    private AudioClip addMusic(String path) {
        try {
            if (Global.IS_DEBUG) {
                System.out.println("load music from: " + path);
            }
            AudioClip music = Applet.newAudioClip(getClass().getResource(path));
            this.musicPairs.put(path, music);
            return music;
        } catch (Exception e) {
        }
        return null;
    }

}
