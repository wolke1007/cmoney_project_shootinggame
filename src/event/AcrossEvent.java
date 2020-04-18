/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package event;

import gameobj.Actor;
import gameobj.Building;
import gameobj.GameObject;

/**
 *
 * @author Cloud-Razer
 */
public class AcrossEvent implements Event {

    private boolean trig;
    private GameObject[] keyObjs;
    private Event nextEvent;
    
    public AcrossEvent(GameObject[] objs, Event nextEvent){
        // 
        this.trig = false;
        this.keyObjs = objs;
        this.nextEvent = nextEvent;
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
        GameObject room = null;
        for(int i = 0; i < this.keyObjs.length; i++){
            if(this.keyObjs[i] instanceof Actor){
                actor = this.keyObjs[i];
            }
            if(this.keyObjs[i] instanceof Building){
                room = this.keyObjs[i];
            }
        }
        if(actor.getCollider().intersects(room.getCollider())){
            this.trig = true;
        }
    }

    @Override
    public boolean isTrig() {
        return this.trig;
    }

    @Override
    public Event next() {
        return this.nextEvent;
    }
    
}
