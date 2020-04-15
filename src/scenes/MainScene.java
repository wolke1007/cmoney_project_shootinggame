/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scenes;

import controllers.ImagePath;
import controllers.SceneController;
import effects.DeadEffect;
import effects.Effect;
import gameobj.Actor;
import gameobj.Ammo;
import gameobj.Barrier;
import gameobj.Enemy;
import gameobj.GameObject;
import gameobj.Gun;
import gameobj.Map;
import gameobj.Maps;
import renderer.Renderer;
//import gameobj.TestObj;
import gameobj.View;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
//import java.awt.event.KeyEvent;
import util.Delay;
import util.Global;
import util.CommandSolver;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.LinkedList;
import util.MapGenerator;
import util.ScoreCalculator;

/**
 *
 * @author Cloud-Razer
 */
public class MainScene extends Scene {

    private Actor actor;
    private ArrayList<Ammo> ammos;
    private ArrayList<Enemy> enemys;
    private Maps maps;
    private View view;
    private Gun gun;
    private LinkedList<GameObject> allObjects;
    private Renderer hpFrameRenderer;
    private Renderer hpRenderer;
    private ScoreCalculator scoreCal;
    private Effect gameOverEffect;

    public MainScene(SceneController sceneController) {
        super(sceneController);
        this.allObjects = new LinkedList<GameObject>();
        this.hpFrameRenderer = new Renderer(0, new int[0], 0, ImagePath.HP[0]);
        this.hpRenderer = new Renderer(0, new int[0], 0, ImagePath.HP[2]); // HP 第三張圖是 debug 用
    }

    @Override
    public void sceneBegin() {
        // 開始背景音樂
        this.ammos = new ArrayList<>();
        this.enemys = new ArrayList<>();
        this.actor = new Actor("circle", (float) Global.DEFAULT_ACTOR_X, (float) Global.DEFAULT_ACTOR_Y, 60, ImagePath.ACTOR1);
        this.view = new View(60, Global.VIEW_WIDTH, Global.VIEW_HEIGHT, this.actor);
        this.gun = new Gun("circle", 600, 500, this.actor, "Gun", ImagePath.GUN);
        int mapLength = (int) Math.sqrt(Global.MAP_QTY);
        this.maps = new Maps(0f, 0f, mapLength * Global.MAP_WIDTH, mapLength * Global.MAP_HEIGHT, mapLength * Global.MAP_WIDTH, mapLength * Global.MAP_HEIGHT);
        Global.mapEdgeUp = (int) this.maps.getCollider().top();
        Global.mapEdgeDown = (int) this.maps.getCollider().bottom();
        Global.mapEdgeLeft = (int) this.maps.getCollider().left();
        Global.mapEdgeRight = (int) this.maps.getCollider().right();
        MapGenerator mg = new MapGenerator(Global.MAP_QTY, this.maps);
        mg.genSequenceMap();  // 產生一樣的地圖(沒有障礙物的十字路口)
//        mg.genRandomMap(); // 產生隨機地圖
        this.allObjects.add(this.actor); // 讓 allObjects 的第一個物件為 actor
        this.allObjects.add(maps); // 讓 allObjects 的第二個物件為 maps
        addAllMapsToAllObjects();
        this.actor.setAllObjects(this.allObjects);
        this.scoreCal = ScoreCalculator.getInstance();
        this.scoreCal.setGameMode("endless"); // 設定此場景遊戲模式
        this.gameOverEffect = new DeadEffect(200, 200, this.actor);
        this.allObjects.add(this.gun);
    }

    private void addAllMapsToAllObjects() {
        for (int i = 0; i < this.maps.getMaps().size(); i++) {
            Map map = this.maps.get(i);
            this.allObjects.add(map);
            for (int j = 0; j < map.getBarriers().size(); j++) {
                this.allObjects.add(map.getBarriers().get(j));
            }
            for (int j = 0; j < map.getBuildings().size(); j++) {
                this.allObjects.add(map.getBuildings().get(j));
            }
        }
    }

    @Override
    public void sceneUpdate() {
        this.view.update();
        Global.mapMouseX = Global.mouseX + Global.viewX;
        Global.mapMouseY = Global.mouseY + Global.viewY;
        ammoUpdate();//Ammo必須比敵人早更新
        enemyUpdate();
        for (int i = 0; i < this.allObjects.size(); i++) {
            this.allObjects.get(i).update();
            if (this.view.isCollision(this.allObjects.get(i))) {
                if (!(this.view.stillSeeing(this.allObjects.get(i)))) {
                    this.view.saw(this.allObjects.get(i));
                }
            } else {
                this.view.removeSeen(this.allObjects.get(i));
            }
        }
        if (this.actor.getHp() <= 0) { // 腳色死亡後的行為，若不想切回主畫面則註解這一段
            this.gameOverEffect.update();
            if (!this.gameOverEffect.getRun()) {
                this.scoreCal.addInHistoryIfInTop(5);
                MainScene.super.sceneController.changeScene(new StartMenuScene(MainScene.super.sceneController));
            }
        }
    }

    //敵人測試更新中
    public void enemyUpdate() {
        if (this.enemys.size() < Global.ENEMY_LIMIT && Global.random(20)) {
            Enemy enemy = new Enemy("circle", Global.random(Global.mapEdgeLeft,
                    Global.mapEdgeRight),
                    Global.random(Global.mapEdgeUp,
                            Global.mapEdgeDown), 5,
                    this.actor, 58, ImagePath.ENEMY);
            this.enemys.add(enemy);
            this.allObjects.add(enemy);
            enemy.setAllObject(this.allObjects);
        }
        for (int i = 0; i < this.enemys.size(); i++) {
            if (this.enemys.get(i).getHp() <= 1) {
                remove(this.enemys.get(i));
                this.enemys.remove(this.enemys.get(i)); // 真實的刪除
                this.scoreCal.addScore(); // 計算分數
                i--;
            }
        }
    }

    public void remove(GameObject obj) {
        this.allObjects.remove(obj);
        this.view.removeSeen(obj);
    }//不顯示的remove 作為不顯示和判斷用 可以再放計分的地方

    //子彈測試更新中
    public void ammoUpdate() {
        if (Global.mouseState == 1) {
            boolean create = true;
            if (this.ammos == null) {
                Ammo ammo = new Ammo("circle", this.actor.getCenterX() - Global.UNIT_MIN, this.actor.getCenterY() - Global.UNIT_MIN, this.actor, 60, ImagePath.BULLET);
                this.ammos.add(ammo);
                this.allObjects.add(ammo);
                ammo.setAllObjects(this.allObjects);
            } else {
                for (int i = 0; i < this.ammos.size(); i++) {
                    if (this.ammos.get(i).getIsShootOut() == false) {
                        this.ammos.get(i).setIsShootOut(create);
                        this.ammos.get(i).setNewStart();
                        create = false;
                        break;
                    } else {
                        continue;
                    }
                }
                if (create) {
                    Ammo ammo = new Ammo("circle", this.actor.getCenterX() - Global.UNIT_MIN, this.actor.getCenterY() - Global.UNIT_MIN, this.actor, 60, ImagePath.BULLET);
                    this.ammos.add(ammo);
                    this.allObjects.add(ammo);
                    ammo.setAllObjects(this.allObjects);
                }
            }
//            Global.mouseState++;
        }
//        System.out.println(this.ammos.size());
    }

    private void paintSmallMap(Graphics g) {
        int smallMapWidth = 200;
        int smallMapHeight = 200;
        int unitWidth = 5;
        int unitHeight = 5;
        double mapWidthRatio = smallMapWidth / (Global.MAP_WIDTH * Math.sqrt(Global.MAP_QTY));
        double mapHeightRatio = smallMapHeight / (Global.MAP_HEIGHT * Math.sqrt(Global.MAP_QTY));
        int smallMapX = Global.SCREEN_X - smallMapWidth;
        g.setColor(Color.GREEN);
        g.drawRect(smallMapX, 0, smallMapWidth, smallMapHeight); // 小地圖外框
        g.setColor(Color.GREEN);
        int actorOnSmallMapX = smallMapX + (int) Math.ceil((double) this.actor.getX() * mapWidthRatio);
        actorOnSmallMapX = actorOnSmallMapX + unitWidth >= Global.SCREEN_X ? Global.SCREEN_X - unitWidth : actorOnSmallMapX;
        int actorOnSmallMapY = (int) ((double) this.actor.getY() * mapHeightRatio);
        actorOnSmallMapY = actorOnSmallMapY + unitHeight >= smallMapHeight ? smallMapHeight - unitHeight : actorOnSmallMapY;
        g.drawRect(actorOnSmallMapX, actorOnSmallMapY, unitWidth, unitHeight); // 角色
        // 畫敵人 start
        for (int i = 0; i < this.allObjects.size(); i++) {
            if (this.allObjects.get(i) instanceof Enemy) {
                int enemyX = (int) this.allObjects.get(i).getX();
                int enemyY = (int) this.allObjects.get(i).getY();
                g.setColor(Color.CYAN);
                int enemyOnSmallMapX = smallMapX + (int) ((double) enemyX * mapWidthRatio);
                enemyOnSmallMapX = enemyOnSmallMapX + unitWidth >= Global.SCREEN_X ? Global.SCREEN_X - unitWidth : enemyOnSmallMapX;
                int enemyOnSmallMapY = (int) ((double) enemyY * mapHeightRatio);
                enemyOnSmallMapY = enemyOnSmallMapY + unitHeight >= smallMapHeight ? smallMapHeight - unitHeight : enemyOnSmallMapY;
                g.drawRect(enemyOnSmallMapX, enemyOnSmallMapY, unitWidth, unitHeight); // 敵人
            }
        }
        // 畫敵人 end
        g.setColor(Color.BLACK);

    }

    private void paintHPbar(Graphics g) {
        float hp = this.actor.getHp();
        if (this.actor.getHp() >= 0f) {
            int hpFrameX = (int) this.view.getX();
            int hpFrameY = (int) this.view.getY();
            float hpRate = hp / 100f;
            this.hpFrameRenderer.paint(g, hpFrameX, hpFrameY, hpFrameX + Global.HP_FRAME_WIDTH, hpFrameY + Global.HP_FRAME_HEIGHT);
            this.hpRenderer.paint(g,
                    hpFrameX + 12, hpFrameY + 8,
                    (int) (hpFrameX + 12 + (Global.HP_WIDTH * hpRate)), hpFrameY - 7 + Global.HP_HEIGHT,
                    0, 0, (int) (555 * hpRate), 74);
        }
    }

    private void paintScore(Graphics g) {
        g.setFont(new Font("TimesRoman", Font.PLAIN, 40));
        g.drawString(String.valueOf("Score: " + this.scoreCal.getCurrentScore()), Global.HP_FRAME_WIDTH + 10, 30);
    }

    @Override
    public void sceneEnd() {
        // 停止背景音樂
        Global.log("main scene end");
        Global.viewX = 0f; // 將 view 給 reset 回最左上角，不然後面印出來的圖片會偏掉
        Global.viewY = 0f;
    }

    @Override
    public void paint(Graphics g) {
        this.view.paint(g);
        paintHPbar(g);
        paintSmallMap(g);
        paintScore(g);
        if (this.gameOverEffect.getRun()) {
            this.gameOverEffect.paint(g);
        }
    }

    @Override
    public CommandSolver.KeyListener getKeyListener() {
        return new MyKeyListener();
    }

    @Override
    public CommandSolver.MouseCommandListener getMouseListener() {
        return new MyMouseListener();
    }

    public class MyKeyListener implements CommandSolver.KeyListener {

        @Override
        public void keyPressed(int commandCode, long trigTime) {
            actorMoveRule(commandCode);
        }

        @Override
        public void keyReleased(int commandCode, long trigTime) {
            stopRule(commandCode);
        }

        private void setDirAndPressedStatus(Actor actor, int dir, boolean status) {
            actor.setStand(false);
            actor.setDir(dir);
            actor.setMovementPressedStatus(dir, status);
        }

        private void actorMoveRule(int commandCode) { // 當角色的視野沒碰到牆壁時移動邏輯
            actor.setStand(false);
            switch (commandCode) {
                case Global.UP:
                    if (!(actor.getCollider().top() < Global.mapEdgeUp)) {
                        setDirAndPressedStatus(actor, Global.UP, true);
                    } else {
                        stopRule(commandCode);
                    }
                    break;
                case Global.DOWN:
                    if (!(actor.getCollider().bottom() > Global.mapEdgeDown)) {
                        setDirAndPressedStatus(actor, Global.DOWN, true);
                    } else {
                        stopRule(commandCode);
                    }
                    break;
                case Global.LEFT:
                    if (!(actor.getCollider().left() < Global.mapEdgeLeft)) {
                        setDirAndPressedStatus(actor, Global.LEFT, true);
                    } else {
                        stopRule(commandCode);
                    }
                    break;
                case Global.RIGHT:
                    if (!(actor.getCollider().right() > Global.mapEdgeRight)) {
                        setDirAndPressedStatus(actor, Global.RIGHT, true);
                    } else {
                        stopRule(commandCode);
                    }
                    break;
            }
        } // 當角色的視野沒碰到牆壁時移動邏輯

        private void stopRule(int commandCode) {
            actor.setStand(true);
            switch (commandCode) {
                case Global.UP:
                    setDirAndPressedStatus(actor, Global.UP, false);
                    break;
                case Global.DOWN:
                    setDirAndPressedStatus(actor, Global.DOWN, false);
                    break;
                case Global.LEFT:
                    setDirAndPressedStatus(actor, Global.LEFT, false);
                    break;
                case Global.RIGHT:
                    setDirAndPressedStatus(actor, Global.RIGHT, false);
                    break;
            }
        }

        @Override
        public void keyTyped(char c, long trigTime) {
        }

    }

    public class MyMouseListener implements CommandSolver.MouseCommandListener {

        @Override
        public void mouseTrig(MouseEvent e, CommandSolver.MouseState state, long trigTime) {
            if (state == CommandSolver.MouseState.PRESSED || state == CommandSolver.MouseState.DRAGGED) {
                Global.mouseState = 1;
            } else if (state == CommandSolver.MouseState.CLICKED || state == CommandSolver.MouseState.MOVED || state == CommandSolver.MouseState.RELEASED) {
                Global.mouseState = 0;
            }
        }

    }
}
