/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameobj;

import java.awt.Graphics;
import java.util.ArrayList;
import util.Delay;
import util.Global;
import util.Move;

/**
 *
 * @author Cloud-Razer
 */
public class Maps extends GameObject {

    private ArrayList<Map> maps;

    public Maps(float x, float y, int width, int height, int colliderWidth, int colliderHeight) {
        super("rect", x, y, width, height, colliderWidth, colliderHeight);
        this.maps = new ArrayList<Map>();
        setType("Maps");
    }

    public void add(Map map) {
        this.maps.add(map);
    }

    public Map get(int index) {
        try {
            return this.maps.get(index);
        } catch (IndexOutOfBoundsException exception) {
            return null;
        }
    }

    public ArrayList getMaps() {
        return this.maps;
    }

    @Override
    public void update() {
        if (this.maps.get(0).getX() - this.maps.get(1).getX() != -Global.MAP_WIDTH) {
            Global.log("========= BUG OCCUR! PLEASE DELETE BUILD FOLDER AND TRY COMPILE AGAIN =======" + (this.maps.get(0).getX() - this.maps.get(1).getX()));
        }
        if (this.maps.get(1).getX() - this.maps.get(2).getX() != -Global.MAP_WIDTH) {
            Global.log("========= BUG OCCUR! PLEASE DELETE BUILD FOLDER AND TRY COMPILE AGAIN =======" + (this.maps.get(1).getX() - this.maps.get(2).getX()));
        }
    }

    @Override
    public void setDir(int dir) {
    }

    @Override
    public void paint(Graphics g) {
    }

    @Override
    public void paintComponent(Graphics g) {
    }

}
