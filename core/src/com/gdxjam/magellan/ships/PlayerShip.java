package com.gdxjam.magellan.ships;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.OrderedMap;
import com.gdxjam.magellan.*;
import com.gdxjam.magellan.drones.Drone;
import com.gdxjam.magellan.gameobj.GameObj;
import com.gdxjam.magellan.gameobj.IDestroyable;
import com.gdxjam.magellan.gameobj.IInteractable;
import com.gdxjam.magellan.shopitem.ShopItem;

/**
 * Created by lolcorner on 20.12.2015.
 */
public class PlayerShip extends Ship implements IInteractable {

    public int maxHealth;
    public int HUMANS = 10000;
    public Array<Integer> drones = new Array();
    public Array<ShopItem> inventory;
    public int mineResourcesPerTick = 10;
    public ParticleEffect trail;

    public PlayerShip(Sector sector) {
        super(sector);
        faction = Factions.PLAYER;
        maxHealth = health = 15;
        attack = 4;
        shield = 0.15f;
        inventory = new Array<ShopItem>();
        setSectorsDiscovered();
        drones.add(1);
    }

    public int shootAt(IDestroyable target){
        MagellanGame.gameState.AI_HOSTILITY = 5;
        return super.shootAt(target);
    }

    public void moveTo(Sector sector) {
        super.moveTo(sector);
        setSectorsDiscovered();
        MagellanGame.soundFx.shipJump.play(0.3f);
        Vector2 particlePosition = lastParkingPosition.cpy().lerp(parkingPosition, 0.10f);
        for (ParticleEmitter em : trail.getEmitters()) {
            em.setPosition(particlePosition.x + spriteVessel.getWidth()/2, particlePosition.y + spriteVessel.getHeight()/2);
            em.getAngle().setHigh(flightAngle - 90 - 10, flightAngle - 90 + 10);
            em.getAngle().setLow(flightAngle - 90);
        }
        trail.start();
    }

    @Override
    public void destroy() {
        MagellanGame.soundFx.doomed.play();
        dispose();
    }

    public void releaseDrone(int level){
        drones.removeValue(level, false);
        Drone drone = new Drone(this.sector, level);
        drone.faction = faction;
        MagellanGame.gameState.updateNumberOfDrones();
    }

    private void setSectorsDiscovered() {
        sector.visited = true;
        sector.discovered = true;
        for(Sector _sector : sector.connectedSectors){
            _sector.discovered = true;
        }
    }

    @Override
    public Actor getActor() {
        Stack stack = new Stack();
        stack.addActor(new Image(MagellanGame.assets.get("sectorview_ship.png", Texture.class)));
        stack.addActor(new Image(MagellanGame.assets.get("sectorview_ship_shield.png", Texture.class)));
        stack.getChildren().get(1).setColor(1,1,1,0);
        return stack;
    }

    @Override
    public void prepareRenderingOnMap() {
        super.prepareRenderingOnMap();

        spriteVessel = new Sprite(MagellanGame.assets.get("map_playership.png", Texture.class));
        spriteVessel.setSize(20, 20);
        spriteVessel.setOriginCenter();

        sectorSlot = 1;
        getParkingPosition();

        spriteVessel.setPosition(parkingPosition.x, parkingPosition.y);
        spriteVessel.setRotation(parkingAngle);

        trail = new ParticleEffect();
        trail.load(Gdx.files.internal("ship_trail.p"),Gdx.files.internal(""));
        trail.setPosition(parkingPosition.x, parkingPosition.y);
        trail.scaleEffect(0.3f);
    }

    @Override
    public void renderOnMap(SpriteBatch batch, float delta) {
        super.render(delta);

        spriteVessel.draw(batch);

        trail.draw(batch, delta);
        //if (trail.isComplete()) trail.reset();
    }

    @Override
    public OrderedMap<String, Interaction> getInteractions(GameObj with) {
        OrderedMap<String, Interaction> interactions = new OrderedMap();
        if (submenuOpen == "") {
            if (drones.size > 0) {
                interactions.put("release drone", new Interaction() {
                    @Override
                    public void interact() {
                        submenuOpen = "releasedrone";
                        showInteractionWindow();
                    }
                });
            }
        }
        if (submenuOpen == "releasedrone") {
            if (drones.contains(1,true)) {
                interactions.put("Level 1", new Interaction() {
                    @Override
                    public void interact() {
                        releaseDrone(1);
                        closeWindow();
                    }
                });
            }
            if (drones.contains(2,true)) {
                interactions.put("Level 2", new Interaction() {
                    @Override
                    public void interact() {
                        releaseDrone(2);
                        closeWindow();
                    }
                });
            }
            if (drones.contains(3,true)) {
                interactions.put("Level 3", new Interaction() {
                    @Override
                    public void interact() {
                        releaseDrone(3);
                        closeWindow();
                    }
                });
            }
            if (drones.contains(4,true)) {
                interactions.put("Level 4", new Interaction() {
                    @Override
                    public void interact() {
                        releaseDrone(4);
                        closeWindow();
                    }
                });
            }
            if (drones.contains(5,true)) {
                interactions.put("Level 5", new Interaction() {
                    @Override
                    public void interact() {
                        releaseDrone(5);
                        closeWindow();
                    }
                });
            }
        }

        return interactions;
    }

    @Override
    public String getTitle() {
        return "The Trinidad";
    }

    @Override
    public String getInfo() {
        String s = "Your ship.";
        s += "\nHealth: " + getHealth() + "/" + maxHealth;
        s += "\nAttack: " + attack;
        s += "\nShield: " + Math.round(shield * 100) + "%";
        s += "\nFrozen Humans: " + HUMANS;
        s += "\nDrones: " + drones.toString(", ");
        s += "\nEquipment: " + inventory.toString(", ");
        return s;
    }

    public void heal(float powerLevel) {
        health = (int) MathUtils.clamp(health + powerLevel, 0, maxHealth);
    }
}
