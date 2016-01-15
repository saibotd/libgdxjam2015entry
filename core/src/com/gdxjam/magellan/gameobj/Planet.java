package com.gdxjam.magellan.gameobj;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.OrderedMap;
import com.gdxjam.magellan.*;
import com.gdxjam.magellan.drones.Drone;
import com.gdxjam.magellan.ships.AiShipSettler;
import com.gdxjam.magellan.ships.PlayerShip;
import com.gdxjam.magellan.ships.Ship;


/**
 * Created by lolcorner on 19.12.2015.
 */
public class Planet extends GameObj implements IDrawableMap, IDestroyable, IInteractable {
    private Sprite mapSprite;
    private Sprite mapClaimedSprite;
    public int population = 0;
    public Color color;
    public int visualType;
    public int resource1;
    public int resource2;
    public int resource3;
    public int minResourcesForSettling = 100;
    private boolean poplimitMessageShown = false;

    public Planet(Sector sector) {
        super(sector);
        visualType = MathUtils.random(1,4);
        switch (MathUtils.random(4)) {
            case 0: color = MagellanColors.PLANET_1; break;
            case 1: color = MagellanColors.PLANET_2; break;
            case 2: color = MagellanColors.PLANET_3; break;
            case 3: color = MagellanColors.PLANET_4; break;
            case 4: color = MagellanColors.PLANET_5; break;
            default: color = MagellanColors.PLANET_1; break;
        }
        resource1 = MathUtils.random(50,200);
        resource2 = MathUtils.random(50,200);
        resource3 = MathUtils.random(50,200);
    }

    @Override
    public Actor getActor() {
        Image image = new Image(MagellanGame.assets.get("sectorview_planet_"+visualType+".png", Texture.class));
        image.setColor(color);
        image.setFillParent(true);
        return image;
    }

    @Override
    public String getTitle() {
        return "PLANET";
    }

    @Override
    public void prepareRenderingOnMap() {
        mapSprite = new Sprite(MagellanGame.assets.get("map_planet_"+visualType+".png", Texture.class));
        mapSprite.setColor(color);
        mapSprite.setSize(30,30);

        mapClaimedSprite = new Sprite(MagellanGame.assets.get("map_planet_claimed.png", Texture.class));
        mapClaimedSprite.setSize(15, 20);
    }

    @Override
    public void renderOnMap(SpriteBatch batch, float delta) {
        mapSprite.setPosition(sector.position.x - mapSprite.getWidth()/2, sector.position.y - mapSprite.getHeight()/2);
        mapSprite.draw(batch);


        if (faction == Factions.PLAYER) {
            mapClaimedSprite.setColor(MagellanColors.FACTION_PLAYER);
        }
        if (faction == Factions.ENEMY) {
            mapClaimedSprite.setColor(MagellanColors.FACTION_ENEMY);
        }
        if (faction == Factions.PLAYER || faction == Factions.ENEMY) {
            mapClaimedSprite.setPosition(sector.position.x, sector.position.y + mapSprite.getHeight()/4);
            mapClaimedSprite.draw(batch);
        }
    }

    @Override
    public boolean receiveDamage(int damage) {
        population -= damage;
        if(population <= 0){
            population = 0;
            if (faction == Factions.PLAYER) {
                MagellanGame.instance.mapScreen.log.addEntry("The population of a planet has ben eradicated.", sector);
            }
            faction = Factions.NEUTRAL;
        }
        return true;
    }

    @Override
    public boolean isAlive() {
        return population > 0;
    }

    @Override
    public void destroy() {

    }

    @Override
    public void dispose() {

    }

    @Override
    public int getHealth() {
        return population;
    }

    @Override
    public float getShield() {
        return 0;
    }

    public void passiveTick(){
        growPopulation();
        if (faction == Factions.PLAYER && population > 0 && population == getPopulationLimit() && !poplimitMessageShown) {
            poplimitMessageShown = true;
            MagellanGame.instance.mapScreen.log.addEntry("Planet has reached it's population limit.", sector);
        }
    }

    public void claim(Ship ship){
        this.faction = ship.faction;
        if(faction == Factions.PLAYER){
            MagellanGame.instance.mapScreen.log.addEntry("Planet claimed (A:"+resource1+" P:"+resource2+" E:"+resource3+")", sector);
        }
    }

    public void populate(Ship ship, int humans){
        if(ship instanceof PlayerShip){
            faction = ship.faction;
            humans = MathUtils.clamp(humans, 0, getPopulationLimit());
            humans = MathUtils.clamp(humans, 0, ((PlayerShip) ship).HUMANS);
            if (humans > 0) {
                MagellanGame.soundFx.population.play(0.6f);
            }
            population += humans;
            ((PlayerShip) ship).HUMANS -= humans;
        }
        if(ship instanceof AiShipSettler){
            faction = ship.faction;
            population += 500;
        }
        MagellanGame.gameState.updatePopulationCount();
    }

    public void boardHumans(Ship ship, int humans){
        if(ship instanceof PlayerShip){
            faction = ship.faction;
            humans = MathUtils.clamp(humans, 0, population);
            population -= humans;
            ((PlayerShip) ship).HUMANS += humans;
        }
        MagellanGame.gameState.updatePopulationCount();
    }

    public void growPopulation() {
        if (population <= 1) {
            population = 0;
        } else {
            int popGrowth = MathUtils.floor(population * 0.05f);
            population = MathUtils.clamp(population + popGrowth, 0, getPopulationLimit());
        }
    }

    public int getPopulationLimit() {
        if (!isHabitable()) {
            return 0;
        } else {
            return resource1 * 10 + resource2 * 10 + resource3 * 10;
        }
    }

    public int creditsByTick(){
        return Math.round(population/30);
    }

    @Override
    public OrderedMap<String, Interaction> getInteractions(final GameObj with) {
        OrderedMap<String, Interaction> interactions = new OrderedMap();

        if (submenuOpen == "") {
            if (faction == Factions.NEUTRAL) {
                interactions.put("claim", new Interaction() {
                    @Override
                    public void interact() {
                        claim((Ship) with);
                        showInteractionWindow();
                    }
                });
            }
            if (faction == with.faction && isHabitable()) {
                interactions.put("populate", new Interaction() {
                    @Override
                    public void interact() {
                        // TODO: Ask for how many
                        populate((Ship) with, 1000);
                        closeWindow();
                    }
                });
            }
            if (faction == with.faction && population > 0) {
                interactions.put("board humans", new Interaction() {
                    @Override
                    public void interact() {
                        // TODO: Ask for how many
                        boardHumans((Ship) with, 100);
                        closeWindow();
                    }
                });
            }
            if (faction == with.faction) {
                interactions.put("Upgrade planet", new Interaction() {
                    @Override
                    public void interact() {
                        submenuOpen = "upgrade";
                        showInteractionWindow();
                    }
                });
            }
        }

        if (submenuOpen == "upgrade") {
            interactions.put("Add 10 " + Statics.resource1, new Interaction() {
                @Override
                public void interact() {
                    addResources(1, MagellanGame.gameState.spendResource(1, 10));
                    showInteractionWindow();
                }
            });
            interactions.put("Add 10 " + Statics.resource2, new Interaction() {
                @Override
                public void interact() {
                    addResources(2, MagellanGame.gameState.spendResource(2, 10));
                    showInteractionWindow();
                }
            });
            interactions.put("Add 10 " + Statics.resource3, new Interaction() {
                @Override
                public void interact() {
                    addResources(3, MagellanGame.gameState.spendResource(3, 10));
                    showInteractionWindow();
                }
            });
        }
        return interactions;
    }

    public boolean isHabitable() {
        if (resource1 >= minResourcesForSettling && resource2 >= minResourcesForSettling && resource3 >= minResourcesForSettling) {
            return true;
        }
        return false;
    }
    @Override
    public String getInfo() {
        String s = "Faction: " + faction.toString();
        s += "\nPopulation: " + population + " / " + getPopulationLimit();
        if (faction == Factions.PLAYER) {
            s += "\nCredits production: " + creditsByTick();
        }
        s += "\n";
        s += "\n"+ Statics.resource1 + ": " + resource1;
        s += "\n"+ Statics.resource2 + ": " + resource2;
        s += "\n"+ Statics.resource3 + ": " + resource3;
        s += "\n";

        if (faction == Factions.NEUTRAL || (faction == Factions.PLAYER && population == 0)) {
            if (isHabitable()) {
                s += "\nThis Planet should support human life.";
            } else {
                s += "\nThis Planet is inhabitable right now.";
                s += "\n(Need " + minResourcesForSettling + " of each resource for settling here)";
            }
        }


        return s;
    }

    public void addResources(int resourcetype, int amount) {

        if (amount > 0) poplimitMessageShown = false;
        switch (resourcetype) {
            case 1:
                resource1 += amount;
                break;
            case 2:
                resource2 += amount;
                break;
            case 3:
                resource3 += amount;
                break;
        }
    }
}
