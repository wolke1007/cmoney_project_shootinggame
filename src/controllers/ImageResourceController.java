/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import util.Global;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;

/**
 *
 * @author user1
 */
// 單例模式 Singleton
public class ImageResourceController {

    // 單例

    private static ImageResourceController irc;

    // 內容
    private Map<String, BufferedImage> imgPairs;

    private ImageResourceController() {
        imgPairs = new HashMap<>();
    }

    public static ImageResourceController getInstance() {
        if (irc == null) {
            irc = new ImageResourceController();
        }
        return irc;
    }

    public BufferedImage tryGetImage(String path) {
        if (this.imgPairs.containsKey(path)) {
            return this.imgPairs.get(path);
        }
        return addImage(path);
    }

    private BufferedImage addImage(String path) {
        try {
            if (Global.IS_DEBUG) {
                System.out.println("load img from: " + path);
            }
            BufferedImage img = ImageIO.read(getClass().getResource(path));
            this.imgPairs.put(path, img);
            return img;
        } catch (Exception ex) {
        }
        return null;
    }

}
