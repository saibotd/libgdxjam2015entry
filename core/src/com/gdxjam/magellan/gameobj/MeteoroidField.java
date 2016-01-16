package com.gdxjam.magellan.gameobj;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.utils.OrderedMap;
import com.gdxjam.magellan.MagellanColors;
import com.gdxjam.magellan.MagellanGame;
import com.gdxjam.magellan.Sector;
import com.gdxjam.magellan.Statics;
import com.gdxjam.magellan.ships.PlayerShip;

/**
 * Created by lolcorner on 22.12.2015.
 */
public class MeteoroidField extends GameObj implements IDrawableMap, IDrawableWindow, IInteractable {

    public int resource; // 1 - 3
    public int resourceAmount;
    private Sprite mapSprite;

    public MeteoroidField(Sector sector) {
        super(sector);
        resource = MathUtils.random(1, 3);
        resourceAmount = Math.round(sector.position.x * sector.position.y * .05f);
    }

    @Override
    public void prepareRenderingOnMap() {
        if (sector.hasPlanet) {
            mapSprite = new Sprite(MagellanGame.assets.get("map_meteoroids_planetsector.png", Texture.class));
            mapSprite.setSize(45,45);
        } else {
            mapSprite = new Sprite(MagellanGame.assets.get("map_meteoroids_emptysector.png", Texture.class));
            mapSprite.setSize(23,23);
        }

        if (resourceAmount == 0) {
            mapSprite.setColor(Color.LIGHT_GRAY);
            return;
        }
        switch (resource){
            case 1:
                mapSprite.setColor(MagellanColors.RESOURCE_1);
                break;
            case 2:
                mapSprite.setColor(MagellanColors.RESOURCE_2);
                break;
            case 3:
                mapSprite.setColor(MagellanColors.RESOURCE_3);
                break;
        }
    }

    @Override
    public void renderOnMap(SpriteBatch batch, float delta) {
        mapSprite.setPosition(sector.position.x - mapSprite.getWidth()/2, sector.position.y - mapSprite.getHeight()/2);
        mapSprite.draw(batch);
    }

    @Override
    public Actor getActor() {
        Stack group = new Stack();
        Image image = new Image(MagellanGame.assets.get("sectorview_asteroids.png", Texture.class));
        Image image2 = new Image(MagellanGame.assets.get("sectorview_asteroids_resources.png", Texture.class));
        if (resourceAmount == 0) {
            image2.setColor(Color.LIGHT_GRAY);
        } else {
            switch (resource) {
                case 1:
                    image2.setColor(MagellanColors.RESOURCE_1);
                    break;
                case 2:
                    image2.setColor(MagellanColors.RESOURCE_2);
                    break;
                case 3:
                    image2.setColor(MagellanColors.RESOURCE_3);
                    break;
            }
        }
        group.addActor(image);
        group.addActor(image2);
        return group;
    }

    @Override
    public String getTitle() {
        return "Meteoroid field";
    }

    @Override
    public String getInfo() {
        String resourceName = "";
        switch (resource){
            case 1:
                resourceName = Statics.resource1;
                break;
            case 2:
                resourceName = Statics.resource2;
                break;
            case 3:
                resourceName = Statics.resource3;
                break;
        }
        String s = "Faction: " + faction.toString();
        s += "\nResource: " + resourceName;
        s += "\nResource amount: " + resourceAmount;
        return s;
    }

    public int mine(int amount) {
        if (amount > resourceAmount) {
            amount = resourceAmount;
        }
        resourceAmount -= amount;
        if (resourceAmount == 0) {
            prepareRenderingOnMap();
        }
        return amount;
    }

    @Override
    public OrderedMap<String, Interaction> getInteractions(final GameObj with) {
        final MeteoroidField meteoroidField = this;
        OrderedMap<String, Interaction> interactions = new OrderedMap();

        if (resourceAmount > 0 && with.faction == Factions.PLAYER) {
            interactions.put("Mine", new Interaction() {
                @Override
                public void interact() {
                    switch (meteoroidField.resource){
                        case 1:
                            MagellanGame.gameState.RESOURCE1 += meteoroidField.mine(((PlayerShip) with).mineResourcesPerTick);
                            break;
                        case 2:
                            MagellanGame.gameState.RESOURCE2 += meteoroidField.mine(((PlayerShip) with).mineResourcesPerTick);
                            break;
                        case 3:
                            MagellanGame.gameState.RESOURCE3 += meteoroidField.mine(((PlayerShip) with).mineResourcesPerTick);
                            break;

                    }
                    MagellanGame.instance.universe.tick();
                    MagellanGame.soundFx.mine.play(0.4f);
                    showInteractionWindow();

                }
            });
        }


        return interactions;
    }
}
