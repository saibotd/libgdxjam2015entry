package com.gdxjam.magellan;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

/**
 * Created by lolcorner on 22.12.2015.
 */
public class MetroidField extends GameObj implements IDrawableMap, IDrawableWindow {

    public int ressource; // 1 - 3
    public int ressourcePerTick;
    private Sprite mapSprite;
    private Sprite sprite;

    public MetroidField(Sector sector) {
        super(sector);
        ressource = MathUtils.random(1, 3);
        ressourcePerTick = MathUtils.random(10, 100);
    }

    @Override
    public void prepareRenderingOnMap() {
        mapSprite = new Sprite(MagellanGame.assets.get("circle.png", Texture.class));
        mapSprite.setSize(30,30);
        switch (ressource){
            case 1:
                mapSprite.setColor(Color.BLUE);
                break;
            case 2:
                mapSprite.setColor(Color.PURPLE);
                break;
            case 3:
                mapSprite.setColor(Color.OLIVE);
                break;
        }
    }

    @Override
    public void renderOnMap(SpriteBatch batch, float delta) {
        mapSprite.setPosition(sector.position.x - mapSprite.getWidth()/2, sector.position.y - mapSprite.getHeight()/2);
        mapSprite.draw(batch);
    }

    @Override
    public void prepareRendering() {
        sprite = new Sprite(MagellanGame.assets.get("circle.png", Texture.class));
        sprite.setPosition(300,300);
        sprite.setSize(200,200);
        switch (ressource){
            case 1:
                sprite.setColor(Color.BLUE);
                break;
            case 2:
                sprite.setColor(Color.PURPLE);
                break;
            case 3:
                sprite.setColor(Color.OLIVE);
                break;
        }
    }

    @Override
    public void render(SpriteBatch batch, float delta) {
        sprite.draw(batch);
    }
}
