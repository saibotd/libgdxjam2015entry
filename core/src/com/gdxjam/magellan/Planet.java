package com.gdxjam.magellan;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;



/**
 * Created by lolcorner on 19.12.2015.
 */
public class Planet extends GameObj implements IDrawableMap, IDrawableWindow, IDestroyable, IInteractable {
    private Sprite sprite;
    private Sprite mapSprite;
    private ObjectMap<String, Interaction> interactions;
    public int population = 0;

    public Planet(Sector sector) {
        super(sector);
        interactions = new ObjectMap();
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
    }

    @Override
    public ObjectMap<String, Interaction> getInteractions(final GameObj with) {
        if(faction == Factions.NEUTRAL || faction == with.faction) {
            interactions.put("populate", new Interaction() {
                @Override
                public void interact(double arg1) {
                    populate((Ship) with, (int) arg1);
                }
                @Override
                public void interact(String arg1) {}
            });
        }
        if(faction == with.faction && population > 0){

        }
        return interactions;
    }
}
