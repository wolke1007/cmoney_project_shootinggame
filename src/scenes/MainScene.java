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
import event.DialogEvent;
import event.EnterBuildingEvent;
import event.Event;
import event.KillAllEnemyEvent;
import gameobj.Actor;
import gameobj.Barrier;
import gameobj.Boss.Boss;
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
import textbar.TextBar;
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
    private Boss boss;
    private Barrier bossBarrier;
    private boolean gameOver;
    private boolean nameTyped;
    private String name;
    private int top; // 多少名次內可以進排行榜
    private TextBar textBar; // 讀稿機
    private boolean grenadeReady;
    private Barrier box;

    public MainScene(SceneController sceneController) {
        super(sceneController);
        this.mouseState = false;
        this.ammoState = false;
        this.grenadeReady = true;
        this.allObjects = new ArrayList<GameObject>();
        this.hpFrameRenderer = new Renderer(0, new int[0], 0, ImagePath.HP[0]);
        this.hpRenderer = new Renderer(0, new int[0], 0, ImagePath.HP[2]); // HP 第三張圖是 debug 用
        allDelayControl();
        this.name = "";
        this.top = 5;
    }

    private void allDelayControl() {
        this.stateChage = new Delay(30);
        this.stateChage.start();
        this.enemyAudio = new Delay(180);
        this.enemyAudio.start();
    }

    @Override
    public void sceneBegin() {
        // 開始背景音樂
        this.gameOver = false;
        this.nameTyped = false;
        this.ammos = new ArrayList<>();
        this.enemys = new ArrayList<>();
        this.actor = new Actor("circle", (float) Global.DEFAULT_ACTOR_X, (float) Global.DEFAULT_ACTOR_Y, 60, ImagePath.ACTOR1);
        this.allObjects.add(this.actor); // 讓 allObjects 的第一個物件為 actor
        int mapLength = Global.MAP_QTY;
        this.maps = new Maps(0f, 0f, mapLength * Global.MAP_WIDTH, Global.MAP_HEIGHT, mapLength * Global.MAP_WIDTH, Global.MAP_HEIGHT);
        this.allObjects.add(maps); // 讓 allObjects 的第二個物件為 maps
        Global.mapEdgeUp = (int) this.maps.getCollider().top();
        Global.mapEdgeDown = (int) this.maps.getCollider().bottom();
        Global.mapEdgeLeft = (int) this.maps.getCollider().left();
        Global.mapEdgeRight = (int) this.maps.getCollider().right();
        MapGenerator mg = new MapGenerator(Global.MAP_QTY, this.maps);
        mg.genSevenMaps(); // 產生 7 個橫向地圖
        addAllMapsToAllObjects();
        this.view = new View(60, Global.VIEW_WIDTH, Global.VIEW_HEIGHT, this.maps.getMaps().get(0));
        this.actor.setAllObjects(this.allObjects);
        this.scoreCal = ScoreCalculator.getInstance();
        this.gameOverEffect = new DeadEffect(200, 200, this.actor);
        this.events = new ArrayList<Event>();
        this.textBar = new TextBar(0, (int) this.view.getY() - 7 + Global.HP_HEIGHT + 5, Global.SCREEN_X, 40);
        this.box = new Barrier("rect", this.maps.getMaps().get(1).getCenterX(), this.maps.getMaps().get(1).getCenterY(), 150, 150, ImagePath.BARRIER, 1);
        this.allObjects.add(this.box);
        eventSetup();
        this.scoreCal.gameStart();
    }

    private void setNextEvent() {
        for (int i = 0; i < this.events.size() - 1; i++) {
            this.events.get(i).setNext(this.events.get(i + 1));
            this.events.get(i).setSerialNo(i);
            this.events.get(i + 1).setSerialNo(i + 1);
        }
    }

    private void eventSetup() {
        // --------------- 新增 Event start --------------- 
        // 第一張地圖
        String[] scripts = {"身為一名基地工程師",
            "一次在基地睡醒後發現基地所有人都消失了",
            "基地的緊急備用燈光處於開啟狀態",
            "你判斷鍋爐核心區應該有問題因此前去查看",
            "但你發現遇到的可不是什麼工程問題......"
        };
        this.textBar.addScript(scripts);
        this.events.add(new DialogEvent(this.textBar, null)); // 0 // 開門
        // 第二張地圖
        this.textBar.play();
        this.events.add(new EnterBuildingEvent(new GameObject[]{this.actor, this.maps.getMaps().get(1).getBuildings().get(0)}, null)); // 1 // 加入對話
        this.events.add(new DialogEvent(this.textBar, null)); // 2 // 將箱子 remove 並產出怪物1
        this.events.add(new KillAllEnemyEvent(this.allObjects, null)); // 3 // 加入對話
        this.events.add(new DialogEvent(this.textBar, null)); // 4 // 開門
        // 第三張地圖
        this.events.add(new EnterBuildingEvent(new GameObject[]{this.actor, this.maps.getMaps().get(2).getBuildings().get(0)}, null)); // 5 // 加入對話
        this.events.add(new DialogEvent(this.textBar, null)); // 6 // 於房間最右側產出怪物2  // 加入對話
        this.events.add(new DialogEvent(this.textBar, null)); // 7 //  加入對話
        this.events.add(new KillAllEnemyEvent(this.allObjects, null)); // 8 // 不做事
        this.events.add(new DialogEvent(this.textBar, null)); // 9 //開門
        // 第四張地圖
        this.events.add(new EnterBuildingEvent(new GameObject[]{this.actor, this.maps.getMaps().get(3).getBuildings().get(0)}, null)); // 10 // 玩家往前走，關門，生 BOSS，同時切 BOSS 戰鬥音樂
        this.events.add(new KillAllEnemyEvent(this.allObjects, null)); // 11 // 停止計時，或許需要輸入名字 //  加入對話
        this.events.add(new DialogEvent(this.textBar, null)); // 12 // gameover
        // ---------------  新增 Event end --------------- 
        setNextEvent();
        this.currentEvent = this.events.get(0);
    }

    private void afterEvent(Event event) {
        if (!event.isTrig()) {
            return;
        }
        Map map;
        String[] scripts;
        switch (event.getSerialNo()) {
            case 0:
                this.maps.getMaps().get(0).getBuildings().get(0).open("right"); // 開啟地圖 0 的門
                map = null;
                break;
            case 1:
                // 加入對話
                this.view.setFocus(this.maps.getMaps().get(1));
                scripts = new String[]{"嗯? 這箱子之前是放在這邊的嘛?"};
                this.textBar.addScript(scripts);
                this.textBar.play();
                break;
            case 2:
                // 將箱子 remove 並產出怪物1
                remove(this.box);
                this.box = null;
                map = this.maps.getMaps().get(1);
                //remove 箱子
                genEnemies((int) map.getCenterX(), (int) map.getCenterY(), (int) map.getCenterX() + 150, (int) map.getCenterY() + 150, 5, 1);
                map = null;
                break;
            case 3:
                scripts = new String[]{"剛剛那些怪物到底是...", "有幾個怪物還穿著基地工作服"};
                this.textBar.addScript(scripts);
                break;
            case 4:
                // 事件 2 觸發後做的事情
                this.maps.getMaps().get(1).getBuildings().get(0).open("right"); // 開啟地圖 1 的門
                break;
            case 5:
                this.view.setFocus(this.maps.getMaps().get(2));
                scripts = new String[]{"「聽到快步衝刺的聲音」"};
                this.textBar.addScript(scripts);
                this.textBar.play();
                break;
            case 6:
                map = this.maps.getMaps().get(2);
                genEnemies((int) map.getCenterX(), (int) map.getCenterY(), (int) map.getCenterX() + 150, (int) map.getCenterY() + 150, 5, 2);
                map = null;
                scripts = new String[]{"好像是不同的怪物，跑得更快了"};
                this.textBar.addScript(scripts);
                this.textBar.play();
                break;
            case 7:

                break;
            case 8:
                scripts = new String[]{"「你聽到下一間房間傳來低吼聲」"};
                this.textBar.addScript(scripts);
                this.textBar.play();
                break;
            case 9:
                this.maps.getMaps().get(2).getBuildings().get(0).open("right"); // 開啟地圖 2 的門
                break;
            case 10:
                this.view.setFocus(this.maps.getMaps().get(3));
                // 控制玩家往前走，關門，生 BOSS，同時切 BOSS 戰鬥音樂
                // 控制玩家往前走 start

                // 控制玩家往前走 end
                // 生 BOSS start
                this.boss = new Boss("rect", this.maps.getMaps().get(3).getCenterX() - 336f, 50f, this.actor, 60);
                this.allObjects.add(this.boss);
                this.boss.setAllObject(this.allObjects);
        //        this.bossBarrier = new Barrier("rect", this.boss.getX() - 40, this.boss.getY() - 20, (int) this.boss.width() + 80, (int) this.boss.height() + 150);
                //        this.allObjects.add(this.bossBarrier);
                this.boss.setStartAttack(true);
                this.boss.setStartPaint(true);
                // 生 BOSS end
                break;
            case 11:
                // 停止計時
                this.scoreCal.gameOver();
                if(this.actor.getHp() == 100){
                    scripts = new String[]{"有一件事我必需說",
                        "我的薪水很高  真的很高",
                        "而且高的不得了"};
                }else{
                    scripts = new String[]{"「因為戰鬥過程被怪物咬傷，主角意識逐漸模糊",
                        "醒來時已是怪物的樣貌",
                        "但卻沒有辦法控制自己的行動",
                        "此時念頭只有一個...」",
                        "「剷除入侵者!!!」"};
                }
                this.textBar.addScript(scripts);
                this.textBar.play();
                break;
            case 12:
                // GG，設定為 true 會在 paint 那邊觸發輸入名字(如果需要的話)
                this.gameOver = true;
                break;
        }
        Global.log("event " + event.getSerialNo() + " trigger, event type:" + event.getClass().getName());
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

    public void genEnemies(int x1, int y1, int x2, int y2, int qty, int type) { // 於指定區域生成敵人
        for (int i = 0; i < qty; i++) {
            float x;
            float y;
            float width = Global.UNIT_X;
            float height = Global.UNIT_Y;
            do {
                x = Global.random(x1, x2);
                y = Global.random(y1, y2);
            } while (!this.maps.canDeploy(x, y, width, height));
            Enemy enemy;
            switch (type) {
                case 1:
                    // 普通怪
                    enemy = new Enemy("circle", x, y, 5,
                            this.actor, 1);
                    break;
                case 2:
                    // 衝刺怪
                    enemy = new Enemy("circle", x, y, 5,
                            this.actor, 2);
                    break;
                default:
                    // 隨機生成
                    enemy = new Enemy("circle", x, y, 5,
                            this.actor, Global.random(1, 2));
                    break;
            }
            this.enemys.add(enemy);
            this.allObjects.add(enemy);
            enemy.setAllObject(this.allObjects);
        }
    } // 於指定區域生成敵人

    private void inputName(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));
        g.drawString("Enter Your English Name: " + this.name, Global.SCREEN_X / 2 - 300, Global.SCREEN_Y / 2);
        g.setColor(Color.BLACK);
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
            if (this.view.isCollision(this.allObjects.get(i).getGraph())) {
                if (!(this.view.stillSeeing(this.allObjects.get(i)))) {
                    this.view.saw(this.allObjects.get(i));
                }
            } else {
                this.view.removeSeen(this.allObjects.get(i));
            }
        }
        zombieFootStepAudio();
        // 角色死亡後的行為  start  // 若不想切回主畫面則註解這一段
        if (this.actor.getHp() <= actorDeadThreshold) {
            this.gameOver = true;
            this.gameOverEffect.update();
        }
        // 角色死亡後的行為 end
        if (this.gameOver) {
            if (!this.scoreCal.isOnTop(this.top)) {
                MainScene.super.sceneController.changeScene(new StartMenuScene(MainScene.super.sceneController));
            }
            if (this.nameTyped) {
                MainScene.super.sceneController.changeScene(new StartMenuScene(MainScene.super.sceneController));
            }
        }
        // Event 控制 start
        if (currentEvent == null) {
            return; // 如果再也沒有事件，則直接跳出判斷
        }
        this.currentEvent.update();
        afterEvent(this.currentEvent);
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
            AudioResourceController.getInstance().play(AudioPath.ZOMBIE_STEP_MOVE);
        }
    } //zombie foot step audio

    public void enemyUpdate() { //敵人測試更新中
        for (int i = 0; i < this.enemys.size(); i++) {
            if (this.enemys.get(i).getHp() <= 1) {
                remove(this.enemys.get(i));
                this.enemys.remove(this.enemys.get(i)); // 真實的刪除
                i--;
            }
        }
        if (this.boss != null && this.boss.getIsDead()) {
            Global.log("remove boss");
            remove(this.boss);
            this.boss = null;
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
                AudioResourceController.getInstance().play(AudioPath.AMMO_GUN_FIRE);
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

    private void paintTime(Graphics g) {
        g.setFont(new Font("TimesRoman", Font.PLAIN, 40));
        g.setColor(Color.WHITE);
        g.drawString(String.valueOf("Time: " + this.scoreCal.getCurrentTime() / 1000 / 60 + "\""
                + this.scoreCal.getCurrentTime() / 1000 % 60 + "\"" + this.scoreCal.getCurrentTime() % 1000 / 100),
                Global.HP_FRAME_WIDTH + 10, 30);
        g.setColor(Color.BLACK);
    } // 分數顯示

    @Override
    public void sceneEnd() {
        // 停止背景音樂
        Global.log("main scene end");
        Global.viewX = 0f; // 將 view 給 reset 回最左上角，不然後面印出來的圖片會偏掉
        Global.viewY = 0f;
        this.scoreCal.addInHistoryIfOnTop(this.top, this.name); // 如果分數有進前幾名，則新增至排行榜中
        this.scoreCal.reset();
    }

    @Override
    public void paint(Graphics g) {
        this.view.paint(g);
        paintHPbar(g);
        paintSmallMap(g);
        paintTime(g);
        if (this.gameOverEffect.getRun()) {
            this.gameOverEffect.paint(g);
        }
        if (this.gameOver && this.scoreCal.isOnTop(this.top)) { // 有在排名內才會要求輸入名字
            if (!this.scoreCal.isStopTiming() && this.gameOver) {
                this.scoreCal.gameOver(); // 停止計時
            }
            inputName(g);
        }
        if (this.textBar.isPlaying()) {
            this.textBar.paint(g);
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
            if (gameOver) {
                return;
            }
            actorMoveRule(commandCode);
            ammoModeChange(commandCode);
        }

        @Override
        public void keyReleased(int commandCode, long trigTime) {
            stopRule(commandCode);
            if (gameOver) {
                if (commandCode == Global.KEY_ENTER) {
                    nameTyped = true;
                }
                if (!nameTyped && commandCode == Global.KEY_BACK_SPACE && name.length() > 0) {
                    name = name.substring(0, name.length() - 1);
                } else if (!nameTyped && commandCode != Global.KEY_BACK_SPACE) {
                    name += (char) commandCode;
                }
                return;
            }
            switch (commandCode) {
                case Global.KEY_SPACE:
                    MainScene.this.stateChage.start();
                    MainScene.this.actor.getRenderer().setState(0);
                    MainScene.this.ammoState = true;
                    MainScene.this.grenadeReady = true;
                    break;
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
                    if (MainScene.this.grenadeReady) {
                        AudioResourceController.getInstance().play(AudioPath.AMMO_GRENADE_READY);
                        MainScene.this.grenadeReady = false;
                    }
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
