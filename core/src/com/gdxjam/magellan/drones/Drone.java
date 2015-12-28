package com.gdxjam.magellan.drones;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.gdxjam.magellan.*;
import com.gdxjam.magellan.ships.PlayerShip;
import com.gdxjam.magellan.ships.Ship;

/**
 * Created by saibotd on 26.12.15.
 */
public class Drone extends MovingGameObj implements IDestroyable, IDrawableMap, IInteractable, IArmed {
    private int maxNumberOfRoutines;
    private int health = 25;
    public static int price = 1000;
    private Array<DroneRoutine> routines = new Array();
    private Sprite spriteDot;

    public Drone(Sector sector, int level) {
        super(sector);
        // The level of a drone decides how many routines it can handle
        // If not all routines are set, the routines become more powerful
        maxNumberOfRoutines = level;
        prepareRenderingOnMap();
    }

    public void addRoutine(DroneRoutine routine){
        if(routines.size < maxNumberOfRoutines)
            routines.add(routine);
        for(DroneRoutine _routine : routines){
            _routine.setPowerLevel((float) maxNumberOfRoutines / routines.size);
        }
    }

    public void tick(){
        for(DroneRoutine routine : routines){
            routine.tick();
        }
    }

    @Override
    public void receiveDamage(int damage) {
        health -= damage;
        if(health <= 0) destroy();
        for(DroneRoutine routine : routines){
            routine.receiveDamage(damage);
        }
    }

    @Override
    public boolean isAlive() {
        return health <= 0;
    }

    @Override
    public void destroy() {
        dispose();
        routines.clear();
    }

    @Override
    public void dispose() {

    }

    @Override
    public String getTitle() {
        return "DRONE";
    }

    @Override
    public void prepareRenderingOnMap() {
        spriteDot = new Sprite(MagellanGame.assets.get("drone_default.png", Texture.class));
        spriteDot.setSize(20,30);
    }

    @Override
    public void renderOnMap(SpriteBatch batch, float delta) {
        spriteDot.setPosition(sector.position.x - spriteDot.getWidth()/2, sector.position.y - spriteDot.getHeight());
        spriteDot.draw(batch);
    }

    @Override
    public void prepareRendering() {

    }

    @Override
    public void render(SpriteBatch batch, float delta) {

    }

    @Override
    public ObjectMap<String, Interaction> getInteractions(GameObj with) {
        for(DroneRoutine routine : routines){
            routine.getInteractions(with);
        }
        return null;
    }

    @Override
    public String getInfo() {
        return null;
    }

    @Override
    public void shootAt(IDestroyable target) {
        for(DroneRoutine routine : routines){
            routine.shootAt(target);
        }
    }
}
