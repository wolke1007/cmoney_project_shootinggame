/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Cloud
 */
public class Record implements Serializable {
    
    private static final long serialVersionUID = 1L; // 序列化版本控制
    private int score;
    private String name;
    private Date date;

    public Record(int score, String name) {
        this.score = score;
        this.name = name;
        this.date = new Date();
    }

    public int getScore(){
        return this.score;
    }

    public String getName(){
        return this.name;
    }

    public Date getDate(){
        return this.date;
    }
}
