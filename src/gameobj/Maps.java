/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameobj;

import java.awt.Graphics;
import java.util.ArrayList;
import util.Global;

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

    public ArrayList<Map> getMaps() {
        return this.maps;
    }

    public boolean canDeploy(float x, float y, float width, float height) {
        for (int i = 0; i < this.maps.size(); i++) {
            ArrayList<Barrier> barriers = this.maps.get(i).getBarriers();
            ArrayList<Building> buildings = this.maps.get(i).getBuildings();
            for (int index = 0; index < barriers.size(); index++) {
                if (barriers.get(index).getCollider().intersects(x, y, x + width, y + height)) {
                    return false;
                }
            }
            for (int index = 0; index < buildings.size(); index++) {
                if (buildings.get(index).getCollider().intersects(x, y, x + width, y + height)) {
                    return false;
                }
                ArrayList<Wall> walls = this.maps.get(i).getBuildings().get(index).getWalls();
                for(int w =0 ; w < walls.size(); w++){
                    if(walls.get(index).getCollider().intersects(x, y, x + width, y + height)){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    @Override
    public void update() {
        if(this.maps.size() <= 1){
            return;
        }
        if (this.maps.get(0).getX() - this.maps.get(1).getX() != -Global.MAP_WIDTH) {
            Global.log("========= BUG OCCUR! PLEASE DELETE BUILD FOLDER AND TRY COMPILE AGAIN =======" + (this.maps.get(0).getX() - this.maps.get(1).getX()));
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
