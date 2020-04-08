/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import controllers.ImagePath;
import controllers.ImageResourceController;
import gameobj.Barrier;
import gameobj.Building;
import gameobj.Map;
import gameobj.Maps;
import java.util.ArrayList;
import util.Global;

/**
 *
 * @author Cloud-Razer
 */
public class MapGenerator {

//     預期外面用法
//     mg = new MapGenerator(9);
//     mg.genRandomMap()  or  mg.genSequenceMap()
    private int mapQty;
    private Maps maps;
    private int mapLength;
    private ArrayList<Map> mapPool;

    public MapGenerator(int mapQty, Maps maps) {
        this.mapQty = mapQty;
        this.mapLength = (int) Math.sqrt(mapQty); // 全地圖的地圖邊長為總數開根號
        // 地圖設計為 3x3 or 4x4 這樣的形式所以不能開根號的數字報錯
        if (!(this.mapLength % 1 == 0) && !(this.mapLength >= 3)) {
            throw new IllegalArgumentException("地圖不符合規定 預期為可被開根號的數且大於 9，如 9 16");
        }
        this.maps = maps;
        this.mapPool = new ArrayList<Map>();
    }

    public void genRandomMap() {
        for (int i = 0; i < this.mapQty; i++) {
            genSingleMapIntoPool(true);
        }
        int poolIndex = 0;
        for (int y = 0; y < this.mapLength; y++) {
            for (int x = 0; x < this.mapLength; x++) {
                // 固定使用第一組背景圖
                Map newMap = this.mapPool.get(poolIndex++);
                // 設定完 x, y 後再加入，不然預設地圖位置皆為 0, 0
                newMap.setX((float) Global.MAP_WIDTH * x);
                newMap.setY((float) (float) Global.MAP_HEIGHT * y);
                this.maps.add(newMap);
            }
        }
        updateAllBarriersBuildingsXY();
    }

    public void genSequenceMap() {
        for (int i = 0; i < this.mapQty; i++) {
            genSingleMapIntoPool(false);
        }
        int poolIndex = 0;
        for (int y = 0; y < this.mapLength; y++) {
            for (int x = 0; x < this.mapLength; x++) {
                // 固定使用第一組背景圖
                Map newMap = this.mapPool.get(poolIndex++);
                // 設定完 x, y 後再加入，不然預設地圖位置皆為 0, 0
                newMap.setX((float) Global.MAP_WIDTH * x);
                newMap.setY((float) (float) Global.MAP_HEIGHT * y);
                this.maps.add(newMap);
            }
        }
        updateAllBarriersBuildingsXY();
    }

    private void updateAllBarriersBuildingsXY() {
        for (int i = 0; i < this.maps.getMaps().size(); i++) {
            this.maps.get(i).updateAllBarriersBuildingsXY();
        }
    }

    private void genSingleMapIntoPool(boolean randomBackGround) {
        if (randomBackGround) {
            // 預設 x, y 座標都為 0 需於加入時被另外設定
            Map newMap = new Map(ImagePath.BACKGROUND[Global.random(0, ImagePath.BACKGROUND.length - 1)], 0, 0, Global.MAP_WIDTH, Global.MAP_HEIGHT);
            // 新增元素(障礙物、建築物等等)進該地圖中 start
            int mapType = Global.random(1, 6);
            switch(mapType){
                case 1:
                    pattern_1(newMap);
                    break;
                case 2:
                    pattern_2(newMap);
                    break;
                case 3:
                    pattern_3(newMap);
                    break;
                default:
                    pattern_4(newMap);
                    break;
            }
            // 新增元素(障礙物、建築物等等)進該地圖中 end
            this.mapPool.add(newMap);
        } else {
            // 固定用第一張背景圖
            Map newMap = new Map(ImagePath.BACKGROUND[0], 0, 0, Global.MAP_WIDTH, Global.MAP_HEIGHT);
            // 新增元素(障礙物、建築物等等)進該地圖中 start
            pattern_3(newMap);
            // 新增元素(障礙物、建築物等等)進該地圖中 end
            this.mapPool.add(newMap);
        }
    }

    private void pattern_1(Map newMap) { // 十字街道加障礙物
        newMap.getBuildings().add(new Building("rect", 0f, 0f, Global.SCREEN_X / 3, Global.SCREEN_Y / 3, ImagePath.BUILDING, 0));
        newMap.getBuildings().add(new Building("rect", (Global.SCREEN_X / 3 * 2), 0f, Global.SCREEN_X / 3, Global.SCREEN_Y / 3, ImagePath.BUILDING, 0));
        newMap.getBuildings().add(new Building("rect", 0f, (Global.SCREEN_Y / 3 * 2), Global.SCREEN_X / 3, Global.SCREEN_Y / 3, ImagePath.BUILDING, 0));
        newMap.getBuildings().add(new Building("rect", (Global.SCREEN_X / 3 * 2), (Global.SCREEN_Y / 3 * 2), Global.SCREEN_X / 3, Global.SCREEN_Y / 3, ImagePath.BUILDING, 0));
        Barrier firstBarrier = new Barrier("circle", Global.SCREEN_X / 3 + 50, Global.SCREEN_Y / 3 + 50, 64, 64, ImagePath.BARRIER, 0);
        newMap.getBarriers().add(firstBarrier);
        newMap.getBarriers().add(new Barrier("circle", (int) firstBarrier.getX() + 60, (int) firstBarrier.getY() + 60, 64, 64, ImagePath.BARRIER, 0));
        newMap.getBarriers().add(new Barrier("circle", (int) firstBarrier.getX() + 120, (int) firstBarrier.getY() + 120, 64, 64, ImagePath.BARRIER, 0));
        newMap.getBarriers().add(new Barrier("circle", (int) firstBarrier.getX() + 180, (int) firstBarrier.getY() + 180, 64, 64, ImagePath.BARRIER, 0));
    }

    private void pattern_2(Map newMap) { // T 型街道
        newMap.getBuildings().add(new Building("rect", 0f, 0f, Global.SCREEN_X, Global.SCREEN_Y / 3, ImagePath.BUILDING, 0));
        newMap.getBuildings().add(new Building("rect", 0f, (Global.SCREEN_Y / 3 * 2), Global.SCREEN_X / 3, Global.SCREEN_Y / 3, ImagePath.BUILDING, 0));
        newMap.getBuildings().add(new Building("rect", (Global.SCREEN_X / 3 * 2), (Global.SCREEN_Y / 3 * 2), Global.SCREEN_X / 3, Global.SCREEN_Y / 3, ImagePath.BUILDING, 0));
     }

    private void pattern_3(Map newMap) { // 凸 型街道
        newMap.getBuildings().add(new Building("rect", 0f, 0f, Global.SCREEN_X / 3, Global.SCREEN_Y / 3, ImagePath.BUILDING, 0));
        newMap.getBuildings().add(new Building("rect", (Global.SCREEN_X / 3 * 2), 0f, Global.SCREEN_X / 3, Global.SCREEN_Y / 3, ImagePath.BUILDING, 0));
        newMap.getBuildings().add(new Building("rect", 0f, (Global.SCREEN_Y / 3 * 2), Global.SCREEN_X, (Global.SCREEN_Y / 3), ImagePath.BUILDING, 0));
     }
    
    private void pattern_4(Map newMap) { // 十字街道
        newMap.getBuildings().add(new Building("rect", 0f, 0f, Global.SCREEN_X / 3, Global.SCREEN_Y / 3, ImagePath.BUILDING, 0));
        newMap.getBuildings().add(new Building("rect", (Global.SCREEN_X / 3 * 2), 0f, Global.SCREEN_X / 3, Global.SCREEN_Y / 3, ImagePath.BUILDING, 0));
        newMap.getBuildings().add(new Building("rect", 0f, (Global.SCREEN_Y / 3 * 2), Global.SCREEN_X / 3, Global.SCREEN_Y / 3, ImagePath.BUILDING, 0));
        newMap.getBuildings().add(new Building("rect", (Global.SCREEN_X / 3 * 2), (Global.SCREEN_Y / 3 * 2), Global.SCREEN_X / 3, Global.SCREEN_Y / 3, ImagePath.BUILDING, 0));
    }
}
