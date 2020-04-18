/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameobj.ammo;

import gameobj.GameObject;
import java.awt.Graphics;
import java.util.ArrayList;
import renderer.RendererToRotate;
import util.AverageSpeed;
import util.VectorCollision;

/**
 *
 * @author F-NB
 */
public class Grenade extends ShootMode {

    private RendererToRotate renderer;//旋轉圖渲染器
    private ArrayList<GameObject> allObjects;
    private GameObject self;

    private AverageSpeed averageSpeed;
    private int count;//初始位置的狀態設定
    private VectorCollision vecterMove;

    public Grenade(GameObject self, GameObject start, float moveSpeed, String[] path) {
        super(start, moveSpeed);
        setSelf(self);
    }
    public void setVectorMove() {
        this.vecterMove = new VectorCollision(getSelf(), 0, 0, new String[]{"Map", "Ammo", "Actor"}, new String[]{"Maps"});
        this.vecterMove.setIsBackMove(false);
    }
    public void setSelf(GameObject self) {
        this.self = self;
    }
    public GameObject getSelf() {
        return this.self;
    }

    @Override
    public void setAllObject(ArrayList<GameObject> list) {
    }

    @Override
    public boolean update() {
        return true;
    }

    @Override
    public void paintComponent(Graphics g) {
    }

}
