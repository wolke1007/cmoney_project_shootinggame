/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package event;

import gameobj.Building;
import gameobj.GameObject;
import java.util.ArrayList;
import util.Global;

/**
 *
 * @author Cloud-Razer
 */
public class EnterBuildingEvent implements Event {

    private boolean trig;
    private GameObject[] keyObjs;
    private Event nextEvent;
    private int serialNo;
    
    public EnterBuildingEvent(GameObject[] objs, Event nextEvent){
        this.trig = false;
        this.keyObjs = objs;
        this.nextEvent = nextEvent;
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
    public void setKeyObj(GameObject[] objs) {
        // 判斷的主要物件
        this.keyObjs = objs;
    }

    @Override
    public void update() {
        // 檢查有沒有事件有沒有被完成
        GameObject actor = null;
        GameObject targetRoom = null;
        for(int i = 0; i < this.keyObjs.length; i++){
            if(this.keyObjs[i].getType().equals("Actor")){
                actor = this.keyObjs[i];
            }
            if(this.keyObjs[i].getType().equals("Building")){
                targetRoom = this.keyObjs[i];
            }
        }
        if(targetRoom == null){
            Global.log("!!!!!!!!!!!BUG: targetRoom IS NULL!!!!!!!!!!!"); // 若沒有 room 實體被傳進來則噴 bug log
            return; 
        }
        if(actor == null){
            Global.log("!!!!!!!!!!!BUG: actor IS NULL!!!!!!!!!!!"); // 若沒有 room 實體被傳進來則噴 bug log
            return; 
        }
        if(actor.getCollider().intersects(targetRoom.getGraph())){
            // actor 走進建築物的 "圖片" 裡(而不是碰撞機)就該要觸發任務
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
    public void setKeyObj(ArrayList<GameObject> objs) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
