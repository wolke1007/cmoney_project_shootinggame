/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

/**
 *
 * @author Cloud-Razer
 */
public class ScoreCalculator {
    
    public static final int[] scoreType = new int[] {100};
    private int totalScore;

    public void addScore(){
        this.totalScore += ScoreCalculator.scoreType[0];
    }
    
    public int getTotalScore(){
        return this.totalScore;
    }
}
