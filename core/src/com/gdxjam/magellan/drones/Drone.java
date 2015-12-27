package com.gdxjam.magellan.drones;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.gdxjam.magellan.*;
import com.gdxjam.magellan.ships.PlayerShip;
import com.gdxjam.magellan.ships.Ship;

/**
 * Created by saibotd on 26.12.15.
 */
public class Drone extends MovingGameObj implements IDestroyable, IDrawableMap, IDrawableWindow, IInteractable, IArmed {
    private int maxNumberOfRoutines;
    private int health = 25;
    public static int price = 1000;
    private Array<DroneRoutine> routines = new Array();
    private boolean active = false;
    private Ship ship;

    public Drone(Ship ship, int level) {
        super(ship.sector);
        this.ship = ship;
        faction = ship.faction;
        if(ship instanceof PlayerShip)
            ((PlayerShip) ship).drones.add(this);
        // The level of a drone decides how many routines it can handle
        // If not all routines are set, the routines become more powerful
        maxNumberOfRoutines = level;
    }

    public void release(){
        sector = ship.sector;
        ship = null;
        active = true;
        MagellanGame.gameState.updateNumberOfDrones();
    }

    public void addRoutine(DroneRoutine routine){
        if(routines.size < maxNumberOfRoutines)
            routines.add(routine);
        for(DroneRoutine _routine : routines){
            _routine.setPowerLevel((float) maxNumberOfRoutines / routines.size);
        }
    }

    public void tick(){
        if(!active) return;
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
    public void prepareRenderingOnMap() {

    }

    @Override
    public void renderOnMap(SpriteBatch batch, float delta) {

    }

    @Override
    public void prepareRendering() {

    }

    @Override
    public void render(SpriteBatch batch, float delta) {

    }

    @Override
    public ObjectMap<String, Interaction> getInteractions(GameObj with) {
        if(!active) return null;
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
        if(!active) return;
        for(DroneRoutine routine : routines){
            routine.shootAt(target);
        }
    }
}
