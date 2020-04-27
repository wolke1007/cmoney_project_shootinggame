/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.applet.Applet;
import java.applet.AudioClip;
import java.util.ArrayList;
import util.Global;

/**
 *
 * @author F-NB
 */
public class MusicResourceController {

    private static class KeyPair {

        private String path;
        private AudioClip music;

        public KeyPair(String path, AudioClip music) {
            this.path = path;
            this.music = music;
        }
    }

    private static MusicResourceController mrc;
    private ArrayList<KeyPair> musicPairs;

    private MusicResourceController() {
        this.musicPairs = new ArrayList<>();
    }

    public static MusicResourceController getInstance() {
        if (MusicResourceController.mrc == null) {
            MusicResourceController.mrc = new MusicResourceController();
        }
        return MusicResourceController.mrc;
    }
    
    public AudioClip tryGetMusic(String path){
        KeyPair pair = findKeyPair(path);
         if (pair == null) {
            return addMusic(path);
        }
         return pair.music;
    }
    
    private AudioClip addMusic(String path) {
        try {
            if (Global.IS_DEBUG) {
                System.out.println("load img from: " + path);
            }
            AudioClip music = Applet.newAudioClip(getClass().getResource(path));
            this.musicPairs.add(new KeyPair(path, music));
            return music;
        } catch (Exception e) {
        }
        return null;
    }
    
     private KeyPair findKeyPair(String path) {
        for (int i = 0; i < musicPairs.size(); i++) {
            KeyPair pair = musicPairs.get(i);
            if (pair.path.equals(path)) {
                return pair;
            }
        }
        return null;
    }
}
