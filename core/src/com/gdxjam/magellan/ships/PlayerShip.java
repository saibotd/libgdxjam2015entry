package com.gdxjam.magellan.ships;

import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.OrderedMap;
import com.gdxjam.magellan.*;
import com.gdxjam.magellan.drones.Drone;
import com.gdxjam.magellan.gameobj.GameObj;
import com.gdxjam.magellan.gameobj.IInteractable;
import com.gdxjam.magellan.shopitem.ShopItem;
import com.gdxjam.magellan.tweening.SpriteAccessor;

/**
 * Created by lolcorner on 20.12.2015.
 */
public class PlayerShip extends Ship implements IInteractable {

    public int HUMANS = 10000;
    public Array<Integer> drones = new Array();
    public Array<ShopItem> inventory;
    public int mineResourcesPerTick = 10;

    public PlayerShip(Sector sector) {
        super(sector);
        faction = Factions.PLAYER;
        health = 10;
        inventory = new Array<ShopItem>();
        setSectorsDiscovered();
        drones.add(4);
        drones.add(4);
        drones.add(4);
        drones.add(4);
        drones.add(4);
        drones.add(4);
        drones.add(4);
        drones.add(4);
        drones.add(4);
        drones.add(4);
        drones.add(4);
        drones.add(4);
        drones.add(4);
        drones.add(4);
        drones.add(4);
        drones.add(4);
        drones.add(4);
        drones.add(4);
        drones.add(4);
        drones.add(4);
        drones.add(4);
    }

    public void moveTo(Sector sector) {
        super.moveTo(sector);
        setSectorsDiscovered();
        tweenManager.killAll();
        Timeline.createSequence()
                .push(Tween.to(this.spriteVessel, SpriteAccessor.ROTATION, 0.3f).target((float)Math.atan2(sector.position.y - lastSector.position.y, sector.position.x - lastSector.position.x)*180f/(float)Math.PI-90f))
                .push(Tween.to(this.spriteVessel, SpriteAccessor.POSITION_XY, 0.5f).target(parkingPosition.x, parkingPosition.y).ease(TweenEquations.easeInOutQuint))
                .push(Tween.to(this.spriteVessel, SpriteAccessor.ROTATION, 1f).target(parkingAngle).ease(TweenEquations.easeInOutCubic))
        .start(tweenManager);
        MagellanGame.soundFx.ship_jump.play(0.3f);
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
        Image image = new Image(MagellanGame.assets.get("sectorview_ship.png", Texture.class));
        return image;
    }

    @Override
    public void prepareRenderingOnMap() {
        //spriteDot = new Sprite(MagellanGame.assets.get("circle.png", Texture.class));
        //spriteDot.setSize(24,24);
        //spriteDot.setColor(Color.YELLOW);

        spriteVessel = new Sprite(MagellanGame.assets.get("map_playership.png", Texture.class));
        spriteVessel.setSize(20, 20);
        spriteVessel.setOriginCenter();

        sectorSlot = 0;
        getParkingPosition();

        spriteVessel.setPosition(parkingPosition.x, parkingPosition.y);
        spriteVessel.setRotation(parkingAngle);
    }

    @Override
    public void renderOnMap(SpriteBatch batch, float delta) {
        super.render(delta);
        //spriteDot.setPosition(sector.position.x - spriteDot.getWidth()/2, sector.position.y - spriteDot.getHeight()/2);
        //spriteDot.draw(batch);

        spriteVessel.draw(batch);
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
        s += "\nHealth: " + getHealth();
        s += "\nAttack: " + attack;
        s += "\nShield: " + Math.round(shield * 100) + "%";
        s += "\nFrozen Humans: " + HUMANS;
        s += "\nDrones: " + drones.toString(", ");
        s += "\nEquipment: " + inventory.toString(", ");
        return s;
    }
}
