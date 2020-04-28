/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Cloud-Razer
 */
public class ScoreCalculator implements Serializable {

    private static final long serialVersionUID = 1L; // 序列化版本控制
    public static final int[] scoreType = new int[]{100};
    private long startTime;
    private long endTime;
    private long currentTime;
    private boolean gameOver;
    private int fullHP;
    private int endHP;
    
    private ArrayList<Record> compaignGameScore;
    private ArrayList<Record> savingGameScore;
    private static FileInputStream fis;

    private static ScoreCalculator scoreCal;

    private ScoreCalculator() {
        this.startTime = 0;
        this.endTime = 0;
        this.currentTime = 0;
        this.compaignGameScore = new ArrayList<Record>();
        this.savingGameScore = new ArrayList<Record>();
        this.gameOver = false;
        this.fullHP = 100;
    }
    
    public void gameStart() {
//        this.startTime = System.currentTimeMillis();
//        this.currentTime = this.endTime - this.startTime;
    }
    
    public boolean isGameOver(){
        return this.gameOver;
    }

    public void gameOver(){
        this.gameOver = true;
    }
    
    public void reset(){
//        this.startTime = 0;
//        this.endTime = 0;
//        this.currentTime = 0;
        this.gameOver = false;
    }
    
    public long getCurrentTime(){
//        if(!this.gameOver){
//            this.endTime = System.currentTimeMillis();
//        }
//        this.currentTime = this.endTime - this.startTime;
        return this.currentTime;
    }

    public static ScoreCalculator getInstance() {
        if (scoreCal == null) {
            //  讀取歷史紀錄 start
            try {
                fis = new FileInputStream("score.ser");
                ObjectInputStream ois = new ObjectInputStream(fis);
                scoreCal = (ScoreCalculator) ois.readObject();
                fis.close();
                ois.close();
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
                scoreCal = new ScoreCalculator(); // 沒有找到舊的紀錄，則 new 一個新的物件
                return scoreCal;
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            //  讀取歷史紀錄 end
        }
        return scoreCal;
    }

    public int calculateScore(float HP) {
//        return (int)((Global.PIVOT_TIME / (this.endTime - this.startTime)) * 1000L); // 概念上是超過當初設定的 pivotTime 則分數是 0 分
        return (int)(HP / 100f * 1000f);
        // 乘以 1000 只是為了讓數字看起來比較好看XD
    }

    private void sort(ArrayList<Record> list) {
        for (int f = 0; f < list.size() - 1; f++) {
            for (int s = f + 1; s < list.size(); s++) {
                if (list.get(f).getScore() < list.get(s).getScore()) {
                    Record tmp;
                    tmp = list.get(f);
                    list.remove(f);
                    list.add(s, tmp);
                }
            }
        }
    }

    private int inHistoryPostion(int inTopNum, int score, ArrayList<Record> list) {
        if(score <= 0){
            return -1;
        }
        sort(list);
        if (list.size() == 0) {
            return 0;
        }
        int i = 0;
        for (; i < list.size(); i++) {
            if (score > list.get(i).getScore() && i <= inTopNum) {
                return i;
            }
        }
        if (i <= inTopNum) {
            return i;
        }
        return -1;
    }
    
    public boolean isOnTop(int top, float HP){
        if (inHistoryPostion(top, calculateScore(HP), this.compaignGameScore) != -1 && inHistoryPostion(top, calculateScore(HP), this.compaignGameScore) <= top) {
            return true;
        }
        return false;
    }

    public void addInHistory(int top, String name, float HP) {
        Global.log("enter endless addInHistoryIfInTop");
//        if(isOnTop(top, HP)){
        // 不判斷條件全部加入
            Record newRecord = new Record(calculateScore(HP), name);
            this.compaignGameScore.add(newRecord);
            reset(); // reset score
            sort(this.compaignGameScore);
            writeFile();
            return;
//        }
//        Global.log("not on top " + top);
    }

    private void writeFile() {
        //  寫入歷史紀錄 start
        FileOutputStream fos;
        try {
            fos = new FileOutputStream("score.ser");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            // 轉成序列化格式輸出
            oos.writeObject(this);
            Global.log("score file wrote");
            fos.close();
            oos.close();
            Global.log("score file write close");
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        //  寫入歷史紀錄 end
    }

    public ArrayList<Record> getHistory(String gameMode) {
        switch (gameMode) {
            case "endless":
                sort(this.compaignGameScore);
                return this.compaignGameScore;
            case "campaign":
                sort(this.compaignGameScore);
                return this.compaignGameScore;
            case "saving":
                return this.savingGameScore;
            default:
                return null;
        }
    }
}
