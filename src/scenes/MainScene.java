/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scenes;

import controllers.AudioPath;
import controllers.AudioResourceController;
import controllers.ImagePath;
import controllers.SceneController;
import effects.DeadEffect;
import effects.Effect;
import event.EnterBuildingEvent;
import event.Event;
import event.KillAllEnemyEvent;
import gameobj.Actor;
import gameobj.Door;
import gameobj.ammo.Ammo;
import gameobj.enemy.Enemy;
import gameobj.GameObject;
import gameobj.item.Item;
import gameobj.Map;
import gameobj.Maps;
import renderer.Renderer;
import gameobj.View;
import gameobj.Wall;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import util.Delay;
import util.Global;
import util.CommandSolver;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
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
    private ArrayList<GameObject> allObjects;
    private Renderer hpFrameRenderer;
    private Renderer hpRenderer;
    private ScoreCalculator scoreCal;
    private Effect gameOverEffect;
    private Delay stateChage;
    private boolean mouseState;//滑鼠狀態
    private boolean ammoState;//Ammo切換
    private Delay enemyAudio;
    private final int actorDeadThreshold = 0; // 角色死亡應該要是多少血，通常應該是 0
    private Event currentEvent;
    private ArrayList<Event> events;
    private boolean gameOver;
    private boolean enterPressed;
    private String name;

    public MainScene(SceneController sceneController) {
        super(sceneController);
        this.mouseState = false;
        this.ammoState = false;
        this.allObjects = new ArrayList<GameObject>();
        this.hpFrameRenderer = new Renderer(0, new int[0], 0, ImagePath.HP[0]);
        this.hpRenderer = new Renderer(0, new int[0], 0, ImagePath.HP[2]); // HP 第三張圖是 debug 用
        allDelayControl();
        this.name = "";
    }

    private void allDelayControl() {
        this.stateChage = new Delay(30);
        this.stateChage.start();
        this.enemyAudio = new Delay(60);
        this.enemyAudio.start();
    }

    @Override
    public void sceneBegin() {
        // 開始背景音樂
        this.gameOver = false;
        this.enterPressed = false;
        this.ammos = new ArrayList<>();
        this.enemys = new ArrayList<>();
        this.actor = new Actor("circle", (float) Global.DEFAULT_ACTOR_X, (float) Global.DEFAULT_ACTOR_Y, 60, ImagePath.ACTOR1);
        this.view = new View(60, Global.VIEW_WIDTH, Global.VIEW_HEIGHT, this.actor);
        int mapLength = Global.MAP_QTY;
        this.maps = new Maps(0f, 0f, mapLength * Global.MAP_WIDTH, Global.MAP_HEIGHT, mapLength * Global.MAP_WIDTH, Global.MAP_HEIGHT);
        Global.mapEdgeUp = (int) this.maps.getCollider().top();
        Global.mapEdgeDown = (int) this.maps.getCollider().bottom();
        Global.mapEdgeLeft = (int) this.maps.getCollider().left();
        Global.mapEdgeRight = (int) this.maps.getCollider().right();
        MapGenerator mg = new MapGenerator(Global.MAP_QTY, this.maps);
//        mg.genSequenceMap();  // 產生一樣的地圖
//        mg.genRandomMap(); // 產生隨機地圖
        mg.genSevenMaps(); // 產生 7 個橫向地圖
        for (int i = 0; i < this.maps.getMaps().size(); i++) { // DEBUG
            Global.log("map" + i + " x:" + this.maps.getMaps().get(i).getX() + " y:" + this.maps.getMaps().get(i).getY());
        }
        this.allObjects.add(this.actor); // 讓 allObjects 的第一個物件為 actor
        this.allObjects.add(maps); // 讓 allObjects 的第二個物件為 maps
        addAllMapsToAllObjects();
        this.actor.setAllObjects(this.allObjects);
        this.scoreCal = ScoreCalculator.getInstance();
        this.scoreCal.setGameMode("endless"); // 設定此場景遊戲模式
        this.gameOverEffect = new DeadEffect(200, 200, this.actor);
        Global.log("scene begin allObject size: " + this.allObjects.size());
        this.events = new ArrayList<Event>();
        // 新增 Event start
        Global.log("maps size: " + this.maps.getMaps().size());
        Global.log("building size: " + this.maps.getMaps().get(0).getBuildings().size());
        this.events.add(new EnterBuildingEvent(new GameObject[]{this.actor, this.maps.getMaps().get(1).getBuildings().get(0)}, null));
//        this.events.add(new EnterBuildingEvent(new GameObject[]{this.actor, this.maps.getMaps().get(2).getBuildings().get(0)}, null));
        this.events.add(new KillAllEnemyEvent(this.allObjects, null));

        // 新增 Event end
        setNextEvent();
        this.currentEvent = this.events.get(0);
        genEnemies(100, 100, 600, 600, 5); //DEBUG 用
    }

    private void setNextEvent() {
        for (int i = 0; i < this.events.size() - 1; i++) {
            this.events.get(i).setNext(this.events.get(i + 1));
            this.events.get(i).setSerialNo(i);
            this.events.get(i + 1).setSerialNo(i + 1);
        }
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
                ArrayList<Wall> walls = map.getBuildings().get(j).getWalls();
                for (int w = 0; w < walls.size(); w++) {
                    this.allObjects.add(walls.get(w));
                }
                ArrayList<Door> doors = map.getBuildings().get(j).getDoors();
                for (int d = 0; d < doors.size(); d++) {
                    this.allObjects.add(doors.get(d));
                }
            }
        }
    }

    public void genEnemies(int x1, int y1, int x2, int y2, int qty) { // 於指定區域生成敵人
        for (int i = 0; i < qty; i++) {
            float x;
            float y;
            float width = Global.UNIT_X;
            float height = Global.UNIT_Y;
            do {
                x = Global.random(x1, x2);
                y = Global.random(y1, y2);
            } while (!this.maps.canDeploy(x, y, width, height));
            Enemy enemy = new Enemy("circle", x, y, 5,
                    this.actor, Global.random(1, 2));
            this.enemys.add(enemy);
            this.allObjects.add(enemy);
            enemy.setAllObject(this.allObjects);
        }
    } // 於指定區域生成敵人

    @Override
    public void sceneUpdate() {
        this.view.update();
        Global.mapMouseX = Global.mouseX + Global.viewX;
        Global.mapMouseY = Global.mouseY + Global.viewY;
        ammoUpdate();//Ammo必須比敵人早更新
        enemyUpdate();
        for (int i = 0; i < this.allObjects.size(); i++) {
            this.allObjects.get(i).update();
            if (this.view.isCollision(this.allObjects.get(i).getGraph())) {
                if (!(this.view.stillSeeing(this.allObjects.get(i)))) {
                    this.view.saw(this.allObjects.get(i));
                }
            } else {
                this.view.removeSeen(this.allObjects.get(i));
            }
        }
        zombieFootStepAudio();
        // 腳色死亡後的行為  start  // 若不想切回主畫面則註解這一段
        if (this.actor.getHp() <= actorDeadThreshold) {
            this.gameOver = true;
            this.gameOverEffect.update();
            if (this.enterPressed) {
                this.scoreCal.addInHistoryIfInTop(5);
                MainScene.super.sceneController.changeScene(new StartMenuScene(MainScene.super.sceneController));
            }
        }
        // 腳色死亡後的行為 end
        // Event 控制 start
        if (currentEvent == null) {
            return; // 如果再也沒有事件，則直接跳出判斷
        }
        this.currentEvent.update();
        switch (this.currentEvent.getSerialNo()) {
            case 0:
                if (this.currentEvent.isTrig()) {
                    // 事件 1 觸發後做的事情
                    Door door = this.maps.getMaps().get(1).getBuildings().get(0).open("right");
                    remove(door);
                    Global.log("map 1 door open");
                }
                break;
            case 1:
                if (this.currentEvent.isTrig()) {
                    // 事件 2 觸發後做的事情
                    Door door = this.maps.getMaps().get(2).getBuildings().get(0).open("right");
                    remove(door);
                    Global.log("map 2 door open");
                }
                break;
            // 後面以此類推
        }
        if (this.currentEvent.isTrig()) {
            this.currentEvent.setTrig(false); // 關閉 trig 並置換 event 成下一個
            this.currentEvent = this.currentEvent.getNext();
        }
        // Event 控制 end
    }

    public void zombieFootStepAudio() { //zombie foot step audio
        for (int i = 0; i < this.view.getSaw().size(); i++) {
            if (this.view.getSaw().get(i).getType().equals("Enemy")) {
                Global.enemyAudio = true;
                break;
            }
            Global.enemyAudio = false;
        }
        if (Global.enemyAudio && this.enemyAudio.isTrig()) {
            AudioResourceController.getInstance().play(AudioPath.ZOMBIE_STEP_MOVE[0]);
        }
    } //zombie foot step audio

    public void enemyUpdate() { //敵人測試更新中
        for (int i = 0; i < this.enemys.size(); i++) {
            if (this.enemys.get(i).getHp() <= 1) {
                remove(this.enemys.get(i));
                this.enemys.remove(this.enemys.get(i)); // 真實的刪除
                this.scoreCal.addScore(); // 計算分數
                i--;
            }
        }
    } //敵人測試更新中

    public void remove(GameObject obj) { // 從 allObjects 與 view 中刪除
        this.allObjects.remove(obj);
        this.view.removeSeen(obj);
    } // 從 allObjects 與 view 中刪除

    public ArrayList<GameObject> getEnemy() {
        ArrayList<GameObject> allEnemy = new ArrayList<GameObject>();
        for (int i = 0; i < this.allObjects.size(); i++) {
            if (this.allObjects.get(i).getType().equals("Enemy")) {
                allEnemy.add(this.allObjects.get(i));
            }
        }
        return allEnemy;
    }

    public void ammoUpdate() { //子彈測試更新中
        if (this.ammoState) {
            boolean create = true;
            if (this.ammos == null) {
                Ammo ammo = new Ammo("circle", this.actor.getCenterX() - Global.UNIT_MIN, this.actor.getCenterY() - Global.UNIT_MIN, this.actor, 2);
                this.ammos.add(ammo);
                this.allObjects.add(ammo);
                ammo.setAllObjects(this.allObjects);
            } else {
                for (int i = 0; i < this.ammos.size(); i++) {
                    if (this.ammos.get(i).getIsShootOut() == false) {
                        if (this.ammos.get(i).getShootMode().getType().equals("Bullet")) {
                            this.ammos.get(i).selectKind(2);
                            this.ammos.get(i).setAllObjects(this.allObjects);
                        }
                        this.ammos.get(i).setIsShootOut(create);
                        this.ammos.get(i).setNewStart();
                        create = false;
                        break;
                    } else {
                        continue;
                    }
                }
                if (create) {
                    Ammo ammo = new Ammo("circle", this.actor.getCenterX() - Global.UNIT_MIN, this.actor.getCenterY() - Global.UNIT_MIN, this.actor, 2);
                    this.ammos.add(ammo);
                    this.allObjects.add(ammo);
                    ammo.setAllObjects(this.allObjects);
                }
            }
            this.ammoState = false;
        }
        if (this.mouseState) {
            if (this.stateChage.isTrig()) {
                boolean create = true;
                if (this.ammos == null) {
                    Ammo ammo = new Ammo("circle", this.actor.getCenterX() - Global.UNIT_MIN, this.actor.getCenterY() - Global.UNIT_MIN, this.actor, 1);
                    this.ammos.add(ammo);
                    this.allObjects.add(ammo);
                    ammo.setAllObjects(this.allObjects);
                } else {
                    for (int i = 0; i < this.ammos.size(); i++) {
                        if (this.ammos.get(i).getIsShootOut() == false) {
                            if (this.ammos.get(i).getShootMode().getType().equals("Grenade")) {
                                this.ammos.get(i).selectKind(1);
                                this.ammos.get(i).setAllObjects(this.allObjects);
                            }
                            this.ammos.get(i).setIsShootOut(create);
                            this.ammos.get(i).setNewStart();
                            create = false;
                            break;
                        } else {
                            continue;
                        }
                    }
                    if (create) {
                        Ammo ammo = new Ammo("circle", this.actor.getCenterX() - Global.UNIT_MIN, this.actor.getCenterY() - Global.UNIT_MIN, this.actor, 1);
                        this.ammos.add(ammo);
                        this.allObjects.add(ammo);
                        ammo.setAllObjects(this.allObjects);
                    }
                }
            }
        }
//        System.out.println("ammo size: "+this.ammos.size());
    } //子彈測試更新中

    private void paintSmallMap(Graphics g) { // 右上角小地圖
        int smallMapWidth = 200;
        int smallMapHeight = 200;
        int unitWidth = 5;
        int unitHeight = 5;
        double mapWidthRatio = smallMapWidth / (double) (Global.MAP_WIDTH * Global.MAP_QTY);
        double mapHeightRatio = smallMapHeight / (double) (Global.MAP_HEIGHT - Global.MAP_HEIGHT / 10);
        int smallMapX = Global.SCREEN_X - smallMapWidth;
        g.setColor(Color.GREEN);
        g.drawRect(smallMapX, 0, smallMapWidth, smallMapHeight); // 小地圖外框
        g.setColor(Color.GREEN);
        double actorOnSmallMapX = smallMapX + Math.ceil(this.actor.getX() * mapWidthRatio);
        actorOnSmallMapX = actorOnSmallMapX + unitWidth >= Global.SCREEN_X ? Global.SCREEN_X - unitWidth : actorOnSmallMapX;
        int actorOnSmallMapY = (int) Math.ceil(((double) this.actor.getY() * mapHeightRatio));
        actorOnSmallMapY = actorOnSmallMapY + unitHeight >= smallMapHeight ? smallMapHeight - unitHeight - 1 : actorOnSmallMapY;
        g.drawRect((int) actorOnSmallMapX, actorOnSmallMapY, unitWidth, unitHeight); // 角色
        // 畫敵人 start
        for (int i = 0; i < this.allObjects.size(); i++) {
            if (this.allObjects.get(i).getType().equals("Enemy")) {
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

    } // 右上角小地圖

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
    } // 角色 HP bar

    private void paintScore(Graphics g) {
        g.setFont(new Font("TimesRoman", Font.PLAIN, 40));
        g.setColor(Color.white);
        g.drawString(String.valueOf("Score: " + this.scoreCal.getCurrentScore()), Global.HP_FRAME_WIDTH + 10, 30);
        g.setColor(Color.black);
    } // 分數顯示

    @Override
    public void sceneEnd() {
        // 停止背景音樂
        Global.log("main scene end");
        Global.viewX = 0f; // 將 view 給 reset 回最左上角，不然後面印出來的圖片會偏掉
        Global.viewY = 0f;
    }

    private void inputName(Graphics g) {
        int textGap = 60;
        g.setColor(Color.WHITE);
        g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));
        g.drawString("Enter your english name: " + this.name, Global.SCREEN_X / 2 - 200, Global.SCREEN_Y / 2);
//            g.drawString(record.getName(), endlessBtn.left, endlessBtn.bottom + 30 * (i + 2) + textGap);
//            g.drawString(Integer.toString(record.getScore()), endlessBtn.left + 400, endlessBtn.bottom + 30 * (i + 2) + textGap);
//            g.drawString(record.getDate().toString(), endlessBtn.left + 800, endlessBtn.bottom + 30 * (i + 2) + textGap);
        g.setColor(Color.BLACK);
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
        if (this.gameOver) {
            inputName(g);
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
            ammoModeChange(commandCode);
        }

        @Override
        public void keyReleased(int commandCode, long trigTime) {
            stopRule(commandCode);
            switch (commandCode) {
                case Global.KEY_SPACE:
                    MainScene.this.stateChage.start();
                    MainScene.this.actor.getRenderer().setState(0);
                    MainScene.this.ammoState = true;
                    break;
            }
            if (gameOver) {
                if (commandCode == Global.KEY_ENTER) {
                    enterPressed = true;
                }
                if (!enterPressed && commandCode == Global.KEY_BACK_SPACE && name.length() > 0) {
                    name = name.substring(0, name.length() - 1);
                } else if (!enterPressed && commandCode != Global.KEY_BACK_SPACE) {
                    name += (char) commandCode;
                }
            }
        }

        private void setDirAndPressedStatus(Actor actor, int dir, boolean status) {
            actor.setStand(false);
            actor.setDir(dir);
            actor.setMovementPressedStatus(dir, status);
        }

        private void actorMoveRule(int commandCode) { // 當角色的視野沒碰到牆壁時移動邏輯
            actor.setStand(false);
            if (actor.getHp() <= actorDeadThreshold) {
                return;
            }
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

        private void ammoModeChange(int commandCode) {
            switch (commandCode) {
                case Global.KEY_1:
                    if (MainScene.this.stateChage.getDelayFrame() == 5) {
                        MainScene.this.stateChage.setDelayFrame(30);
                        MainScene.this.stateChage.start();
                        MainScene.this.stateChage.click();
                    }
                    break;
                case Global.KEY_2:
                    if (MainScene.this.stateChage.getDelayFrame() == 30) {
                        MainScene.this.stateChage.setDelayFrame(5);
                        MainScene.this.stateChage.start();
                        MainScene.this.stateChage.click();
                    }
                    break;
                case Global.KEY_SPACE:
                    MainScene.this.stateChage.stop();
                    MainScene.this.actor.getRenderer().setState(1);
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
            if (e.getButton() == MouseEvent.BUTTON1) {
                if (state == CommandSolver.MouseState.PRESSED) {
                    MainScene.this.mouseState = true;
                    MainScene.this.stateChage.click();
                } else if (state == CommandSolver.MouseState.DRAGGED) {
                    MainScene.this.mouseState = true;
                } else if (state == CommandSolver.MouseState.CLICKED || state == CommandSolver.MouseState.MOVED || state == CommandSolver.MouseState.RELEASED) {
                    MainScene.this.mouseState = false;
                }
            }
        }
    }
}
