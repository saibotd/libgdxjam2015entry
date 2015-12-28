package com.gdxjam.magellan;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.ObjectMap;
import com.gdxjam.magellan.ships.AiShipSettler;
import com.gdxjam.magellan.ships.PlayerShip;
import com.gdxjam.magellan.ships.Ship;


/**
 * Created by lolcorner on 19.12.2015.
 */
public class Planet extends GameObj implements IDrawableMap, IDestroyable, IInteractable {
    private Sprite sprite;
    private Sprite mapSprite;
    public int population = 0;

    public Planet(Sector sector) {
        super(sector);
    }

    @Override
    public void prepareRendering() {
        sprite = new Sprite(MagellanGame.assets.get("dot.png", Texture.class));
    }

    @Override
    public void render(SpriteBatch batch, float delta) {
        sprite.setPosition(1000, 400);
        sprite.setSize(800,800);
        sprite.draw(batch);
    }

    @Override
    public String getTitle() {
        return null;
    }

    @Override
    public void prepareRenderingOnMap() {
        mapSprite = new Sprite(MagellanGame.assets.get("dot.png", Texture.class));
        mapSprite.setSize(24,24);
        mapSprite.setColor(Color.MAGENTA);
    }

    @Override
    public void renderOnMap(SpriteBatch batch, float delta) {
        mapSprite.setPosition(sector.position.x - mapSprite.getWidth()/2, sector.position.y - mapSprite.getHeight()/2);
        mapSprite.draw(batch);
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
        return true;
    }

    @Override
    public void destroy() {

    }

    @Override
    public void dispose() {

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
