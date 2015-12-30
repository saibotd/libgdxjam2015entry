package com.gdxjam.magellan;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.ObjectMap;
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

    public Planet(Sector sector) {
        super(sector);
        visualType = MathUtils.random(1,2);
        switch (MathUtils.random(4)) {

            case 0: color = MagellanColors.PLANET_1; break;
            case 1: color = MagellanColors.PLANET_2; break;
            case 2: color = MagellanColors.PLANET_3; break;
            case 3: color = MagellanColors.PLANET_4; break;
            case 4: color = MagellanColors.PLANET_5; break;
            default: color = MagellanColors.PLANET_1; break;
        }
    }

    @Override
    public Actor getActor() {
        Image image = new Image(MagellanGame.assets.get("map_planet_"+visualType+".png", Texture.class));
        image.setColor(color);
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
    public void receiveDamage(int damage) {
        population -= damage;
        if(population <= 0){
            population = 0;
            faction = Factions.NEUTRAL;
        }
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

    public void tick(){
        if(population <= 1) population = 0;
        else{
            population += MathUtils.clamp(Math.round(population/MathUtils.random(200,500)), 1, population);
        }
    }

    public void claim(Ship ship){
        this.faction = ship.faction;
    }

    public void populate(Ship ship, int humans){
        if(ship instanceof PlayerShip){
            faction = ship.faction;
            humans = MathUtils.clamp(humans, 0, ((PlayerShip) ship).HUMANS);
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

    public int creditsByTick(){
        return Math.round(population/100);
    }

    @Override
    public ObjectMap<String, Interaction> getInteractions(final GameObj with) {
        ObjectMap<String, Interaction> interactions = new ObjectMap();
        if (faction == Factions.NEUTRAL) {
            interactions.put("claim", new Interaction() {
                @Override
                public void interact() {
                    claim((Ship) with);
                }
            });
        }
        if (faction == with.faction) {
            interactions.put("populate", new Interaction() {
                @Override
                public void interact() {
                    // TODO: Ask for how many
                    populate((Ship) with, 1000);
                }
            });
        }
        if (faction == with.faction && population > 0) {
            interactions.put("board humans", new Interaction() {
                @Override
                public void interact() {
                    // TODO: Ask for how many
                    boardHumans((Ship) with, 100);
                }
            });
        }
        return interactions;
    }

    @Override
    public String getInfo() {
        String s = "Faction: " + faction.toString();
        s += "\nPopulation: " + population;
        s += "\nCredits production: " + creditsByTick();
        return s;
    }
}
