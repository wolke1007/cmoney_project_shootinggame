/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package event;

import gameobj.GameObject;

/**
 *
 * @author Cloud-Razer
 */
public interface Event {

    public abstract void setKeyObj(GameObject[] obj);
    
    public abstract void update();

    public abstract boolean isTrig();
    
    public abstract Event next();

}
