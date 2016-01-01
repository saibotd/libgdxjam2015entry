package com.gdxjam.magellan.drones;

import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.gdxjam.magellan.*;
import com.gdxjam.magellan.screen.BaseScreen;
import com.gdxjam.magellan.ships.PlayerShip;
import com.gdxjam.magellan.ships.Ship;

/**
 * Created by saibotd on 26.12.15.
 */
public class Drone extends MovingGameObj implements IDestroyable, IDrawableMap, IInteractable, IArmed {
    private int maxNumberOfRoutines;
    private int health = 1;
    public static int price = 1000;
    private Array<DroneRoutine> routines = new Array();
    public Vector2 dimensions = new Vector2(280,170);

    public Drone(Sector sector, int level) {
        super(sector);
        // The level of a drone decides how many routines it can handle
        // If not all routines are set, the routines become more powerful
        maxNumberOfRoutines = level;
        prepareRenderingOnMap();
    }

    public void addRoutine(DroneRoutine routine){
        if(routines.size >= maxNumberOfRoutines)
            return;
        if (hasRoutine(routine.getClass()))
            return;

        routines.add(routine);
        for(DroneRoutine _routine : routines){
            _routine.setPowerLevel((float) maxNumberOfRoutines / routines.size);
        }
    }

    @Override
    public void moveTo(Sector sector) {
        super.moveTo(sector);
        tweenManager.killAll();
        Timeline.createSequence()
                .push(Tween.to(this.spriteVessel, SpriteAccessor.ROTATION, 0.3f).target((float)Math.atan2(sector.position.y - lastSector.position.y, sector.position.x - lastSector.position.x)*180f/(float)Math.PI-90f))
                .push(Tween.to(this.spriteVessel, SpriteAccessor.POSITION_XY, 0.5f).target(sector.position.x - 20, sector.position.y - 20).ease(TweenEquations.easeInOutQuint))
                .start(tweenManager);
    }

    public void tick(){
        for(DroneRoutine routine : routines){
            routine.tick();
        }
    }

    @Override
    public void receiveDamage(int damage) {
        health -= damage;
        for(DroneRoutine routine : routines){
            routine.receiveDamage(damage);
        }
        if(health <= 0) destroy();
    }

    @Override
    public boolean isAlive() {
        return health > 0;
    }

    @Override
    public void destroy() {
        dispose();
        routines.clear();
    }

    @Override
    public void dispose() {
        this.sector.gameObjs.removeValue(this, true);
    }

    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public void prepareRenderingOnMap() {
        spriteVessel = new Sprite(MagellanGame.assets.get("drone.png", Texture.class));
        spriteVessel.setSize(28,18);
        spriteVessel.setOriginCenter();
        spriteVessel.setPosition(sector.position.x - 20, sector.position.y - 20);
    }

    @Override
    public void renderOnMap(SpriteBatch batch, float delta) {
        super.render(delta);
        spriteVessel.draw(batch);
        for(DroneRoutine routine : routines){
            routine.render(batch, delta);
        }
    }

    @Override
    public Actor getActor() {
        Group group = new Group();
        Image img = new Image(MagellanGame.assets.get("drone.png", Texture.class));
        img.setSize(dimensions.x, dimensions.y);
        group.setSize(dimensions.x, dimensions.y);
        group.addActor(img);
        for(DroneRoutine routine:routines) {
            Image imgRoutine = new Image(routine.sprite);
            imgRoutine.setSize(dimensions.x, dimensions.y);
            group.setSize(dimensions.x, dimensions.y);
            group.addActor(imgRoutine);
        }
        return group;
    }

    @Override
    public ObjectMap<String, Interaction> getInteractions(GameObj with) {
        final Drone drone = this;
        ObjectMap<String, Interaction> interactions = new ObjectMap();

        if (submenuOpen == "") {
            interactions.put("setup", new Interaction() {
                @Override
                public void interact() {
                    submenuOpen = "setup";
                    showInteractionWindow();
                }
            });
            for (DroneRoutine routine : routines) {
                interactions.putAll(routine.getInteractions(with));
            }

        }

        if (submenuOpen == "setup") {
            interactions.put("Add Scouting Routine", new Interaction() {
                @Override
                public void interact() {
                    addRoutine(new DroneRoutineScouting(drone));
                    showInteractionWindow();
                }
            });
            interactions.put("Add Fighting Routine", new Interaction() {
                @Override
                public void interact() {
                    addRoutine(new DroneRoutineFighting(drone));
                    showInteractionWindow();
                }
            });
            interactions.put("Add Mining Routine", new Interaction() {
                @Override
                public void interact() {
                    addRoutine(new DroneRoutineMining(drone));
                    showInteractionWindow();
                }
            });
            if (routines.size > 0) {
                interactions.put("Clear Routines", new Interaction() {
                    @Override
                    public void interact() {
                        clearRoutines();
                        showInteractionWindow();
                    }
                });
            }
        }

        return interactions;
    }

    @Override
    public String getTitle() {
        return "DRONE";
    }

    @Override
    public String getInfo() {
        String s = "Faction: " + faction.toString();
        s += "\nHealth: " + health;
        s += "\nLevel: " + maxNumberOfRoutines;
        s += "\nRoutines: " + routines.size + "/" + maxNumberOfRoutines;
        for(DroneRoutine routine : routines){
            s += "\n" + routine.routine;
        }
        return s;
    }

    @Override
    public int shootAt(IDestroyable target) {
        for(DroneRoutine routine : routines){
            routine.shootAt(target);
        }
        return 0;
    }

    public boolean hasRoutine(Class routineClass) {
        for (DroneRoutine routine : routines) {
            if (routine.getClass() == routineClass) {
                return true;
            }
        }
        return false;
    }

    public void clearRoutines() {
        routines.clear();
    }
}
