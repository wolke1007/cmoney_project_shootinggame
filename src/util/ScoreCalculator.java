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
    private int totalScore;
    private ArrayList<Record> endlessGameScore;
    private ArrayList<Record> compaignGameScore;
    private ArrayList<Record> savingGameScore;
    private String currentGameMode;
    private static FileInputStream fis;

    private static ScoreCalculator scoreCal;

    private ScoreCalculator() {
        this.endlessGameScore = new ArrayList<Record>();
        this.compaignGameScore = new ArrayList<Record>();
        this.savingGameScore = new ArrayList<Record>();
        this.currentGameMode = null;
    }

    public void setGameMode(String mode) {
        this.currentGameMode = mode;
        this.totalScore = 0; // 當重新設定遊戲模式時重新計算分數應該挺合理
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

    public void addScore() {
        this.totalScore += ScoreCalculator.scoreType[0];
    }

    public int getCurrentScore() {
        return this.totalScore;
    }

    private void sort(ArrayList<Record> list) {
        for (int f = 0; f < list.size() - 1; f++) {
            for(int s = f + 1; s < list.size(); s++){
                if (list.get(f).getScore() < list.get(s).getScore()){
                    Record tmp;
                    tmp = list.get(f);
                    list.remove(f);
                    list.add(s, tmp);
                }
            }
        }
    }

    private int inHistoryPostion(int inTopNum, int score, ArrayList<Record> list) {
        sort(list);
        if(list.size() == 0){
            return 0;
        }
        for (int i = 0; i < list.size(); i++) {
            if (score > list.get(i).getScore() && inTopNum > 0) {
                return i;
            }
            inTopNum--;
        }
        return -1;
    }

    public void addInHistoryIfInTop(int top) {
        if (this.currentGameMode == null) {
            return;
        }
        Scanner sc = new Scanner(System.in);
        switch (this.currentGameMode) {
            case "endless":
                if (inHistoryPostion(top, this.totalScore, this.endlessGameScore) != -1) {
                    System.out.print("NEW HIGH SCORE!! PLEASE ENTER YOUR NAME: ");
                    Record newRecord = new Record(this.totalScore, sc.nextLine());
                    this.endlessGameScore.add(newRecord);
                    this.totalScore = 0; // reset score
                    this.currentGameMode = null; // reset game mode
                    sort(this.endlessGameScore);
                    writeFile();
                }
                break;
            case "campaign":
                break;
            case "saving":
                break;
        }
    }
    
    private void writeFile(){
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
    
    public ArrayList<Record> getHistory(String gameMode){
        switch(gameMode){
            case "endless":
                sort(this.endlessGameScore);
                return this.endlessGameScore;
            case "campaign":
                return this.compaignGameScore;
            case "saving":
                return this.savingGameScore;
            default:
                return null;
        }
    }
}
