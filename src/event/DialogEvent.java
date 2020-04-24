/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package event;

import gameobj.GameObject;
import java.util.ArrayList;
import textbar.TextBar;
import util.Global;

/**
 *
 * @author Cloud-Razer
 */
public class DialogEvent implements Event{
    
    private boolean trig;
    private TextBar textBar;
    private Event nextEvent;
    private int serialNo;
    
    public DialogEvent(TextBar textBar, Event nextEvent){
        this.trig = false;
        this.textBar = textBar;
        if(!this.textBar.ready()){
            throw new IllegalArgumentException("傳入的 textBar 其 ready() 必需為 true(有稿可讀)");
        }
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
        // 此事件不是以 GameObject 為主，所以留空
    }
    
    public void setKeyObj(TextBar textBar) {
        // 判斷的主要物件
        // 此事件不是以 GameObject 為主，所以留空
        this.textBar = textBar;
    }
    
    @Override
    public void setKeyObj(ArrayList<GameObject> objs) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update() {
        // 檢查有沒有事件有沒有被完成
       if(!this.textBar.ready()){
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

}
