/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.map;

import gameobj.Map;
import gameobj.Maps;
import java.util.ArrayList;
import util.Global;

/**
 *
 * @author Cloud-Razer
 */
public class MapGenerator {

    // mg = new MapGenerator(9);
    // mg.genRandomMap()
    // mg.genSequenceMap()

    private int mapQty;
    private Maps maps;
    private boolean random;
    private int mapLength;
    private ArrayList<Map> mapPool;

    public MapGenerator(int mapQty, Maps maps, boolean random) {
        this.mapQty = mapQty;
        this.mapLength = (int) Math.sqrt(mapQty); // 全地圖的地圖邊長為總數開根號
        // 地圖設計為 3x3 or 4x4 這樣的形式所以不能開根號的數字報錯
        if (this.mapLength % 1 == 0 && this.mapLength >= 3) {
           throw new IllegalArgumentException("地圖不符合規定 預期為可被開根號的數且大於 9，如 9 16");
        }
        this.maps = maps;
        this.random = random;
        if (random) {
            genRandomMap();
        } else {
            genSequenceMap();
        }
    }

    private void genRandomMap() {

    }

//    private void genSequenceMap() {
//        for(int i = 0; i < )
//    }
//    
//    private void genSingleMap(){
//        this.mapPool.add(new Map(ImageController.Map.random(), 0, 0, ));
//    }
}
//
//
//// 這邊希望地圖數能為 3x3 or 4x4 這樣的形式
//    int map_x = width;
//    int map_y = height;
//    if (this.mapLength % 1 == 0 && this.mapLength >= 3) {
//        Global.log("地圖數量: " + this.mapLength + "x" + this.mapLength);
//        // 不同位置的地圖使用不同的圖片，之後需做成從地圖池中取隨機 pattern 來用
//        for (int x = 0; x < this.mapLength; x++) {
//            for (int y = 0; y < this.mapLength; y++) {
//                int whichMap = x;
//                switch (whichMap) {
//                    case 0:
//                        this.maps.add(new Map(Global.BACKGROUND_1, (float) map_x * y, (float) map_y * x, width, height));
//                        break;
//                    case 1:
//                        this.maps.add(new Map(Global.BACKGROUND_2, (float) map_x * y, (float) map_y * x, width, height));
//                        break;
//                    case 2:
//                        this.maps.add(new Map(Global.BACKGROUND_3, (float) map_x * y, (float) map_y * x, width, height));
//                        break;
//                }
//            }
//        }
//        for (int i = 0; i < this.maps.getMaps().size(); i++) {
//            // 所有這個場景有用到 GameObject 都必需放進 allObjects 的 LinkedList 中去做碰撞判斷決定要不要畫出來
//            this.allObjects.add(this.maps.get(i));
//        }
//    } else {
//        Global.log("地圖不符合規定 預期為可被開根號的數且大於 9，如 9 16");
//    }