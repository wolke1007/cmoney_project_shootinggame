/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameobj;

import java.awt.Graphics;
import java.util.ArrayList;
import renderer.Renderer;
import util.Global;

/**
 *
 * @author Cloud-Razer
 */
public class Building extends GameObject {

    private Renderer renderer;
    private float inMapX;
    private float inMapY;
    private ArrayList<Wall> walls;
    private ArrayList<Door> doors;
    private int height;
    private int width;
    private String[] doorSides;

    public Building(String colliderType, float x, float y, int width, int height, String[] path, int imgIndex, String[] doorSides) {//src => Global.Barrier
        super(colliderType, x, y, width, height, 0, 0);
        this.renderer = new Renderer(new int[0], 0, path[imgIndex]);
        super.paintPriority = 2;
        setType("Building");
        this.walls = new ArrayList<Wall>();
        this.doors = new ArrayList<Door>();
        this.width = width;
        this.height = height;
        this.doorSides = doorSides;
    }
    
    public ArrayList<Wall> getWalls(){
        return this.walls;
    }
    
    public ArrayList<Door> getDoors(){
        return this.doors;
    }
    
    public void genWalls(){ // 設定好該地圖 XY 後才建立牆壁，由 MapGenerator 呼叫
        int doorHeight = Global.DOOR_LENGTH;
        int wallX = (int) x;
        int wallY = (int) y;
        int wallHeight = (height - doorHeight) / 2;
        int wallWidth = Global.WALL_THICK; //牆壁寬度
        this.walls.add(new Wall(wallX, wallY, wallWidth, wallHeight)); // 左上牆壁
        this.walls.add(new Wall(wallX, wallY, this.width, wallWidth)); // 上方牆壁
        this.walls.add(new Wall(wallX + this.width - wallWidth, wallY, wallWidth, wallHeight)); // 右上牆壁
        this.walls.add(new Wall(wallX, wallY + wallHeight + doorHeight, wallWidth, wallHeight)); // 左下牆壁
        this.walls.add(new Wall(wallX, wallY + this.height - wallWidth, this.width, wallWidth)); // 下方牆壁
        this.walls.add(new Wall(wallX + this.width - wallWidth, wallY + wallHeight + doorHeight, wallWidth, wallHeight)); // 右下牆壁
    }
    
    public void genDoors(){
        int leftDoorX = (int)this.walls.get(0).getCollider().left(); // 第 1 面牆固定是左上的牆壁
        int leftDoorY = (int)this.walls.get(0).getCollider().bottom(); 
        int rightDoorX = (int)this.walls.get(2).getCollider().left(); // 第 3 面牆固定是右上的牆壁
        int rightDoorY = (int)this.walls.get(2).getCollider().bottom(); 
        int doorWidth = Global.WALL_THICK; //與牆壁寬度相同
        int doorHeight = Global.DOOR_LENGTH;
        for(int i = 0; i < this.doorSides.length; i++){
            switch(this.doorSides[i]){
                case "left":
                    this.doors.add(new Door(leftDoorX, leftDoorY, doorWidth, doorHeight, "left")); // 左邊門
                    break;
                case "right":
                    this.doors.add(new Door(rightDoorX, rightDoorY, doorWidth, doorHeight, "right")); // 右邊門
                    break;
            }
        }
    }
    
    public void close(String side){
        int leftDoorX = (int)this.walls.get(0).getCollider().left(); // 第 1 面牆固定是左上的牆壁
        int leftDoorY = (int)this.walls.get(0).getCollider().bottom(); 
        int rightDoorX = (int)this.walls.get(2).getCollider().left(); // 第 3 面牆固定是右上的牆壁
        int rightDoorY = (int)this.walls.get(2).getCollider().bottom(); 
        int doorWidth = Global.WALL_THICK; //與牆壁寬度相同
        int doorHeight = Global.DOOR_LENGTH;
        for(int d = 0; d < this.doors.size(); d++){
            if(this.doors.get(d).name.equals(side)){
                return; // 已經有這扇門了
            }
        }
        switch(side){
            case "left":
                this.doors.add(new Door(leftDoorX, leftDoorY, doorWidth, doorHeight, "left")); // 左邊門
                break;
            case "right":
                this.doors.add(new Door(leftDoorX, leftDoorY, doorWidth, doorHeight, "left")); // 左邊門
                break;
        }
    }
    
    public void open(String side){
        for(int d = 0; d < this.doors.size(); d++){
            if(this.doors.get(d).name.equals(side)){
                this.doors.remove(d);
            }
        }
    }

    @Override
    public void setDir(int dir) {
    }

    @Override
    public void update() {
    }

    @Override
    public void paintComponent(Graphics g) {
        this.renderer.paint(g, (int) super.x, (int) super.y, (int) super.getX() + (int) this.getGraph().width(), (int) super.getY() + (int) this.getGraph().height());
    }

}
