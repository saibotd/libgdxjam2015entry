package com.gdxjam.magellan.drones;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.gdxjam.magellan.*;

/**
 * Created by saibotd on 26.12.15.
 */
public class Drone extends MovingGameObj implements IDestroyable, IDrawableMap, IDrawableWindow, IInteractable, IArmed {
    private int maxNumberOfRoutines;
    private int health = 25;
    public static int price = 1000;
    private Array<DroneRoutine> routines;

    public Drone(Sector sector, int level) {
        super(sector);
        // The level of a drone decides how many routines it can handle
        // If not all routines are set, the routines should be more powerful
        maxNumberOfRoutines = level;
    }

    public void addRoutine(DroneRoutine routine){
        if(routines.size < maxNumberOfRoutines)
            routines.add(routine);
        for(DroneRoutine _routine : routines){
            routine.setPowerLevel((float) maxNumberOfRoutines / routines.size);
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
