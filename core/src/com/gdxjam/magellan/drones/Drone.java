package com.gdxjam.magellan.drones;

import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.OrderedMap;
import com.gdxjam.magellan.*;
import com.gdxjam.magellan.gameobj.*;
import com.gdxjam.magellan.tweening.SpriteAccessor;

/**
 * Created by saibotd on 26.12.15.
 */
public class Drone extends MovingGameObj implements IDestroyable, IDrawableMap, IInteractable, IArmed {
    private int maxNumberOfRoutines;
    private int health = 1;
    public static int price = 1000;
    private Array<DroneRoutine> routines = new Array();
    public Vector2 dimensions = new Vector2(280,170);
    public Vector2 dimensionsWindow = new Vector2(250,170);
    private Array<DroneRoutine.ROUTINES> listItems;
    private Array<DroneRoutine.ROUTINES> selectedRoutines;
    private List listRight;
    private List listLeft;

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
    }

    public void tick(){
        for(DroneRoutine routine : routines){
            routine.tick();
        }
    }

    @Override
    public boolean receiveDamage(int damage) {
        health -= damage;
        for(DroneRoutine routine : routines){
            routine.receiveDamage(damage);
        }
        if(health <= 0) destroy();
        return true;
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
    public float getShield() {
        return 0;
    }

    @Override
    public void prepareRenderingOnMap() {
        spriteVessel = new Sprite(MagellanGame.assets.get("drone.png", Texture.class));
        spriteVessel.setSize(28,18);
        spriteVessel.setOriginCenter();

        getFreeSectorSlot();
        getParkingPosition();

        spriteVessel.setPosition(parkingPosition.x, parkingPosition.y);
        spriteVessel.setRotation(parkingAngle);
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
        Image img = new Image(MagellanGame.assets.get("sectorview_drone.png", Texture.class));
        img.setSize(dimensionsWindow.x, dimensionsWindow.y);
        group.setSize(dimensionsWindow.x, dimensionsWindow.y);
        group.addActor(img);
        for(DroneRoutine routine:routines) {
            Image imgRoutine = new Image(routine.windowSprite);
            imgRoutine.setSize(dimensionsWindow.x, dimensionsWindow.y);
            group.setSize(dimensionsWindow.x, dimensionsWindow.y);
            group.addActor(imgRoutine);
        }
        return group;
    }

    private void updateLists(){
        listItems = new Array<DroneRoutine.ROUTINES>();
        for(DroneRoutine.ROUTINES  routine: MagellanGame.gameState.UNLOCKED_ROUTINES){
            if(!selectedRoutines.contains(routine, false))
                listItems.add(routine);
        }
        listLeft.setItems(listItems);
        listRight.setItems(selectedRoutines);
    }

    public void showSetupWindow(){
        Window window = MagellanGame.instance.windowScreen.getWindow("SETUP DRONE LVL " + maxNumberOfRoutines);
        //window.setDebug(true, true);
        Skin skin = MagellanGame.instance.windowScreen.skin;
        VerticalGroup windowContent = new VerticalGroup();
        windowContent.fill();
        HorizontalGroup lists = new HorizontalGroup();
        lists.space(20);
        lists.fill();
        //lists.debugAll();
        VerticalGroup leftGroup = new VerticalGroup();
        VerticalGroup rightGroup = new VerticalGroup();
        leftGroup.space(10);
        rightGroup.space(10);
        //leftGroup.debugAll();
        leftGroup.fill();
        rightGroup.fill();
        HorizontalGroup menu = new HorizontalGroup();
        menu.padTop(20);

        listLeft = new List(skin);
        ScrollPane scrollPaneLeft = new ScrollPane(listLeft);

        listRight = new List(skin);
        ScrollPane scrollPaneRight = new ScrollPane(listRight);


        leftGroup.addActor(new Label("Available routines", skin));
        leftGroup.addActor(scrollPaneLeft);
        rightGroup.addActor(new Label("Installed routines", skin));
        rightGroup.addActor(scrollPaneRight);

        TextButton doneButton = new TextButton("Done", skin);
        TextButton addButton = new TextButton("Add routine", skin);
        TextButton removeButton = new TextButton("Remove routine", skin);

        addButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                DroneRoutine.ROUTINES selectedRoutine = (DroneRoutine.ROUTINES) listLeft.getSelected();
                if(selectedRoutine == null || selectedRoutines.size >= maxNumberOfRoutines) return;
                if(!selectedRoutines.contains(selectedRoutine, false))
                    selectedRoutines.add(selectedRoutine);
                updateLists();
            }
        });

        removeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                DroneRoutine.ROUTINES selectedRoutine = (DroneRoutine.ROUTINES) listRight.getSelected();
                if(selectedRoutine == null) return;
                selectedRoutines.removeValue(selectedRoutine, false);
                updateLists();
            }
        });

        final Drone drone = this;

        doneButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                clearRoutines();
                for(DroneRoutine.ROUTINES routine: selectedRoutines){
                    switch (routine){
                        case ATTACKING:
                            addRoutine(new DroneRoutineFighting(drone));
                            break;
                        case SCOUTING:
                            addRoutine(new DroneRoutineScouting(drone));
                            break;
                        case ADVSCOUTING:
                            addRoutine(new DroneRoutineScoutingAdvanced(drone));
                            break;
                        case MINING:
                            addRoutine(new DroneRoutineMining(drone));
                        break;
                        case FOLLOWING:
                            addRoutine(new DroneRoutineFollowing(drone));
                        break;
                        case REPAIRING:
                            addRoutine(new DroneRoutineReparing(drone));
                        break;
                    }
                }
                MagellanGame.instance.windowScreen.closeWindow();
                MagellanGame.instance.windowScreen.drawSurroundings();
                MagellanGame.soundFx.upgrade.play(0.5f);
            }
        });

        selectedRoutines = new Array();
        for(DroneRoutine _routines:routines){
            selectedRoutines.add(_routines.routine);
        }
        updateLists();

        menu.addActor(addButton);
        menu.addActor(removeButton);
        menu.addActor(doneButton);
        lists.addActor(leftGroup);
        lists.addActor(rightGroup);
        windowContent.addActor(lists);
        windowContent.addActor(menu);
        window.add(windowContent);
    }

    @Override
    public OrderedMap<String, Interaction> getInteractions(GameObj with) {
        OrderedMap<String, Interaction> interactions = new OrderedMap();

        interactions.put("setup", new Interaction() {
            @Override
            public void interact() {
                showSetupWindow();
            }
        });
        interactions.put("load into ship", new Interaction() {
            @Override
            public void interact() {
                MagellanGame.instance.universe.playerShip.drones.add(maxNumberOfRoutines);
                MagellanGame.instance.windowScreen.getWindow("Drone", "Drone LVL " + maxNumberOfRoutines + " boarded.");
                dispose();
                MagellanGame.gameState.updateNumberOfDrones();
            }
        });
        for (DroneRoutine routine : routines) {
            interactions.putAll(routine.getInteractions(with));
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
        target.receiveDamage(getAttack());
        return getAttack();
    }

    @Override
    public int getAttack() {
        int attack = 0;
        for(DroneRoutine routine : routines){
            if(routine instanceof DroneRoutineFighting) attack += ((DroneRoutineFighting) routine).getAttack();
        }
        return attack;
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
