/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import controllers.ImagePath;
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

    private int mapQty;
    private Maps maps;
    private int mapLength;
    private ArrayList<Map> mapPool;
    private ArrayList<Float[]> buildingContainsXY;

    public MapGenerator(int mapQty, Maps maps) {
        this.mapQty = mapQty;
        this.mapLength = (int) Math.sqrt(mapQty); // 全地圖的地圖邊長為總數開根號
        // 地圖設計為 3x3 or 4x4 這樣的形式所以不能開根號的數字報錯
//        if (!(this.mapLength % 1 == 0) && !(this.mapLength >= 3)) {
//            throw new IllegalArgumentException("地圖不符合規定 預期為可被開根號的數且大於 9，如 9 16");
//        }
        this.maps = maps;
        this.mapPool = new ArrayList<Map>();
        this.buildingContainsXY = new ArrayList<Float[]>();
    }

    private void getBuildingContainsXY() {
        float x1;
        float x2;
        float y1;
        float y2;
        for (int i = 0; i < this.maps.getMaps().size(); i++) {
            Map map = this.maps.getMaps().get(i);
            for (int ba = 0; ba < map.getBarriers().size(); ba++) {
                Barrier barrier = map.getBarriers().get(ba);
                x1 = barrier.getX();
                y1 = barrier.getY();
                x2 = barrier.getX() + barrier.getCollider().width();
                y2 = barrier.getY() + barrier.getCollider().height();
                this.buildingContainsXY.add(new Float[]{x1, y1, x2, y2});
            }
            for (int bu = 0; bu < map.getBuildings().size(); bu++) {
                Building building = map.getBuildings().get(bu);
                x1 = building.getX();
                y1 = building.getY();
                x2 = building.getX() + building.getCollider().width();
                y2 = building.getY() + building.getCollider().height();
                this.buildingContainsXY.add(new Float[]{x1, y1, x2, y2});
            }
        }
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
                newMap.setY((float) Global.MAP_HEIGHT * y);
                this.maps.add(newMap);
            }
        }
        updateAllBarriersBuildingsXY();
        getBuildingContainsXY(); // 取得所有物件座標存於 buildingContainsXY
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
                newMap.setY((float) Global.MAP_HEIGHT * y);
                this.maps.add(newMap);
            }
        }
        updateAllBarriersBuildingsXY();
        getBuildingContainsXY(); // 取得所有物件座標存於 buildingContainsXY
    }

    public void genSevenMaps() {
        this.mapPool.add(pattern_5(new Map(ImagePath.BACKGROUND[Global.random(0, ImagePath.BACKGROUND.length - 1)], 0, 0, Global.MAP_WIDTH, Global.MAP_HEIGHT)));
        for (int i = 0; i < Global.MAP_QTY - 1; i++) {
            this.mapPool.add(pattern_5(new Map(ImagePath.BACKGROUND[Global.random(0, ImagePath.BACKGROUND.length - 1)], 0, 0, Global.MAP_WIDTH, Global.MAP_HEIGHT)));
        }
       for (int i = 0; i < this.mapPool.size(); i++) {
            // 固定使用第一組背景圖
            this.mapPool.get(i).setX((float) Global.MAP_WIDTH * i);
            this.maps.add(this.mapPool.get(i));
        }
        updateAllBarriersBuildingsXY();
    }

    private void updateAllBarriersBuildingsXY() {
        for (int i = 0; i < this.maps.getMaps().size(); i++) {
            this.maps.get(i).updateAllXY();
        }
    }

    private void genSingleMapIntoPool(boolean randomBackGround) {
        if (randomBackGround) {
            // 預設 x, y 座標都為 0 需於加入時被另外設定
            Map newMap = new Map(ImagePath.BACKGROUND[Global.random(0, ImagePath.BACKGROUND.length - 1)], 0, 0, Global.MAP_WIDTH, Global.MAP_HEIGHT);
            // 新增元素(障礙物、建築物等等)進該地圖中 start
            int mapType = Global.random(4, 5);
            switch (mapType) {
                case 1:
                    pattern_1(newMap);
                    break;
                case 2:
                    pattern_2(newMap);
                    break;
                case 3:
                    pattern_3(newMap);
                    break;
                case 4:
                    pattern_4(newMap);
                    break;
                default:
                    pattern_5(newMap);
                    break;
            }
            // 新增元素(障礙物、建築物等等)進該地圖中 end
            this.mapPool.add(newMap);
        } else {
            // 固定用第一張背景圖
            Map newMap = new Map(ImagePath.BACKGROUND[0], 0, 0, Global.MAP_WIDTH, Global.MAP_HEIGHT);
            // 新增元素(障礙物、建築物等等)進該地圖中 start
            pattern_5(newMap);
            // 新增元素(障礙物、建築物等等)進該地圖中 end
            this.mapPool.add(newMap);
        }
    }

    private void pattern_1(Map newMap) { // 十字街道加障礙物
//        int tmp = 5;
//        newMap.getBuildings().add(new Building("rect", 0f, 0f, Global.SCREEN_X / tmp, Global.SCREEN_Y / tmp, ImagePath.BUILDING, 0));
//        newMap.getBuildings().add(new Building("rect", (Global.SCREEN_X / 3 * 2), 0f, Global.SCREEN_X / tmp, Global.SCREEN_Y / tmp, ImagePath.BUILDING, 0));
//        newMap.getBuildings().add(new Building("rect", 0f, (Global.SCREEN_Y / 3 * 2), Global.SCREEN_X / tmp, Global.SCREEN_Y / tmp, ImagePath.BUILDING, 0));
//        newMap.getBuildings().add(new Building("rect", (Global.SCREEN_X / 3 * 2), (Global.SCREEN_Y / 3 * 2), Global.SCREEN_X / tmp, Global.SCREEN_Y / tmp, ImagePath.BUILDING, 0));
//        Barrier firstBarrier = new Barrier("circle", Global.SCREEN_X / 3 + 50, Global.SCREEN_Y / 3 + 50, 64, 64, ImagePath.BARRIER, 0);
//        newMap.getBarriers().add(firstBarrier);
//        newMap.getBarriers().add(new Barrier("circle", (int) firstBarrier.getX() + 60, (int) firstBarrier.getY() + 60, 64, 64, ImagePath.BARRIER, 0));
//        newMap.getBarriers().add(new Barrier("circle", (int) firstBarrier.getX() + 120, (int) firstBarrier.getY() + 120, 64, 64, ImagePath.BARRIER, 0));
//        newMap.getBarriers().add(new Barrier("circle", (int) firstBarrier.getX() + 180, (int) firstBarrier.getY() + 180, 64, 64, ImagePath.BARRIER, 0));
    }

    private void pattern_2(Map newMap) { // T 型街道
//        newMap.getBuildings().add(new Building("rect", 0f, 0f, Global.SCREEN_X, Global.SCREEN_Y / 3, ImagePath.BUILDING, 0));
//        newMap.getBuildings().add(new Building("rect", 0f, (Global.SCREEN_Y / 3 * 2), Global.SCREEN_X / 3, Global.SCREEN_Y / 3, ImagePath.BUILDING, 0));
//        newMap.getBuildings().add(new Building("rect", (Global.SCREEN_X / 3 * 2), (Global.SCREEN_Y / 3 * 2), Global.SCREEN_X / 3, Global.SCREEN_Y / 3, ImagePath.BUILDING, 0));
    }

    private void pattern_3(Map newMap) { // 凸 型街道
//        newMap.getBuildings().add(new Building("rect", 0f, 0f, Global.SCREEN_X / 3, Global.SCREEN_Y / 3, ImagePath.BUILDING, 0));
//        newMap.getBuildings().add(new Building("rect", (Global.SCREEN_X / 3 * 2), 0f, Global.SCREEN_X / 3, Global.SCREEN_Y / 3, ImagePath.BUILDING, 0));
//        newMap.getBuildings().add(new Building("rect", 0f, (Global.SCREEN_Y / 3 * 2), Global.SCREEN_X, (Global.SCREEN_Y / 3), ImagePath.BUILDING, 0));
    }

    private void pattern_4(Map newMap) { // 十字街道
//        newMap.getBuildings().add(new Building("rect", 0f, 0f, Global.SCREEN_X / 3, Global.SCREEN_Y / 3, ImagePath.BUILDING, 0));
//        newMap.getBuildings().add(new Building("rect", (Global.SCREEN_X / 3 * 2), 0f, Global.SCREEN_X / 3, Global.SCREEN_Y / 3, ImagePath.BUILDING, 0));
//        newMap.getBuildings().add(new Building("rect", 0f, (Global.SCREEN_Y / 3 * 2), Global.SCREEN_X / 3, Global.SCREEN_Y / 3, ImagePath.BUILDING, 0));
//        newMap.getBuildings().add(new Building("rect", (Global.SCREEN_X / 3 * 2), (Global.SCREEN_Y / 3 * 2), Global.SCREEN_X / 3, Global.SCREEN_Y / 3, ImagePath.BUILDING, 0));
    }

    private Map pattern_5(Map newMap) { // 大房間 右門
        float sizeRate = 6f / 6f; // 目前先不調整比例
        newMap.getBuildings().add(new Building("rect", 0f, 0f, (int) newMap.getGraph().width(), (int) newMap.getGraph().height(), ImagePath.BUILDING, 0,
                new String[]{"right"}));
        return newMap;
    }

    private Map pattern_6(Map newMap) { // 大房間 沒有門
        float sizeRate = 6f / 6f; // 目前先不調整比例
        newMap.getBuildings().add(new Building("rect", 0f, 0f, (int) newMap.getGraph().width(), (int) newMap.getGraph().height(), ImagePath.BUILDING, 0,
                new String[]{}));
        return newMap;
    }
}
