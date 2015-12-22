package com.gdxjam.magellan;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;

/**
 * Created by Felix on 22.12.2015.
 */
public class GlobalUi {

    public MagellanGame game;
    public SpriteBatch batch;
    public FitViewport viewport;
    public Skin skin;
    public Stage stage;
    private Texture bgTexture;

    public GlobalUi(MagellanGame _game) {
        skin = MagellanGame.assets.get("skin/uiskin.json", Skin.class);
        game = _game;
        batch = new SpriteBatch();
        viewport = new FitViewport(1280, 720);
        stage = new Stage(viewport);

        bgTexture = MagellanGame.assets.get("bg.png", Texture.class);
        bgTexture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);



    }

    public void renderBG(float delta) {

        viewport.apply();
        batch.begin();

        batch.draw(bgTexture, 0, 0, 1280, 720, 0, 0, 2, 2);

        batch.end();

    }

    public void renderOverlays(float delta) {

    }

    private void update(float delta) {

    }

    public void dispose() {
        batch.dispose();
    }

}
