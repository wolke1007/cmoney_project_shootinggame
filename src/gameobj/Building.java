/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameobj;

import java.awt.Graphics;
import java.util.ArrayList;
import renderer.Renderer;

/**
 *
 * @author Cloud-Razer
 */
public class Building extends GameObject {

    private Renderer renderer;
    private float inMapX;
    private float inMapY;
    private ArrayList<GameObject> walls;
    private int height;
    private int width;

    public Building(String colliderType, float x, float y, int width, int height, String[] path, int imgIndex) {//src => Global.Barrier
        super(colliderType, x, y, width, height, 0, 0);
        this.renderer = new Renderer(new int[0], 0, path[imgIndex]);
        super.paintPriority = 2;
        setType("Building");
        this.walls = new ArrayList<GameObject>();
        this.width = width;
        this.height = height;
    }
    
    public ArrayList<GameObject> getWalls(){
        return this.walls;
    }
    
    public void genWalls(){ // 設定好該地圖 XY 後才建立牆壁，由 MapGenerator 呼叫
        int doorHeight = 100;
        int wallX = (int) x;
        int wallY = (int) y;
        int wallHeight = (height - doorHeight) / 2;
        int wallWidth = 5; //牆壁寬度
        this.walls.add(new Wall(wallX, wallY, wallWidth, wallHeight)); // 左上牆壁
        this.walls.add(new Wall(wallX, wallY, this.width, wallWidth)); // 上方牆壁
        this.walls.add(new Wall(wallX + this.width - wallWidth, wallY, wallWidth, wallHeight)); // 右上牆壁
        this.walls.add(new Wall(wallX, wallY + wallHeight + doorHeight, wallWidth, wallHeight)); // 左下牆壁
        this.walls.add(new Wall(wallX, wallY + this.height - wallWidth, this.width, wallWidth)); // 下方牆壁
        this.walls.add(new Wall(wallX + this.width - wallWidth, wallY + wallHeight + doorHeight, wallWidth, wallHeight)); // 右下牆壁
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
