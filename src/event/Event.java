/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package event;

import gameobj.Building;
import gameobj.GameObject;
import java.util.ArrayList;

/**
 *
 * @author Cloud-Razer
 */
public interface Event {

    public abstract void setKeyObj(GameObject[] obj);
    
    public abstract void setKeyObj(ArrayList<GameObject> objs);
    
    public abstract void update();

    public abstract boolean isTrig();

    public abstract void setTrig(boolean status);
    
    public abstract Event getNext();
    
    public abstract Event setNext(Event event);
    
    public abstract void setSerialNo(int serial);

    public abstract int getSerialNo();
}
