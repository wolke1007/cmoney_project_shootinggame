/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package event;

import gameobj.GameObject;
import java.util.ArrayList;
import util.Global;

/**
 *
 * @author Cloud-Razer
 */
public class KillAllEnemyEvent implements Event {

    private boolean trig;
    private ArrayList<GameObject> allObjects;
    private Event nextEvent;
    private int serialNo;
    private int used;
    
    public KillAllEnemyEvent(ArrayList<GameObject> objs, Event nextEvent){
        this.trig = false;
        this.allObjects = objs;
        this.nextEvent = nextEvent;
        this.used = 0;
    }
    
    @Override
    public void setSerialNo(int serial){
        this.serialNo = serial;
    }
    
    @Override
    public int getSerialNo(){
        return this.serialNo;
    }
    
    @Override
    public void setKeyObj(ArrayList<GameObject> objs) {
        // 判斷的主要物件
        this.allObjects = objs;
    }

    @Override
    public void update() {
        // 檢查有沒有事件有沒有被完成
        if(this.allObjects == null){
            Global.log("!!!!!!!!!!!BUG: allObjects IS NULL!!!!!!!!!!!"); // 若沒有 room 實體被傳進來則噴 bug log
            return; 
        }
        for(int i = 0; i < this.allObjects.size(); i++){
            if(this.allObjects.get(i).getType().equals("Enemy")){
                return;
            }
        }
        // 地圖中都沒有 Enemy 的時候成立
        this.used++;
        if(this.used == 1){
            this.trig = true;
        }
    }

    @Override
    public boolean isTrig() {
        return this.trig;
    }
    
    @Override
    public void setTrig(boolean status){
        this.trig = status;
    }

    @Override
    public Event getNext() {
        return this.nextEvent;
    }
    
    @Override
    public Event setNext(Event event) {
        return this.nextEvent = event;
    }

    @Override
    public void setKeyObj(GameObject[] obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
