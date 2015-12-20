package com.gdxjam.magellan.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.gdxjam.magellan.MagellanGame;

/**
 * Created by lolcorner on 20.12.2015.
 */
public class BaseScreen implements Screen, InputProcessor {
    private final TextButton btnWindow;
    private final TextButton btnMap;
    public MagellanGame game;
    public SpriteBatch batch;
    public FitViewport viewport;
    public Skin skin;
    public Stage stage;

    public BaseScreen(MagellanGame _game){
        skin = MagellanGame.assets.get("skin/uiskin.json", Skin.class);
        game = _game;
        batch = new SpriteBatch();
        viewport = new FitViewport(1280, 720);
        stage = new Stage(viewport);
        HorizontalGroup menu = new HorizontalGroup();
        menu.setPosition(10,20);
        btnWindow = new TextButton("SHOW SURROUNDINGS", skin);
        btnMap = new TextButton("STAR MAP", skin);
        menu.addActor(btnWindow);
        menu.addActor(btnMap);
        stage.addActor(menu);

        btnWindow.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                game.showWindowScreen();
            }
        });

        btnMap.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                game.showMapScreen();
            }
        });
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(new InputMultiplexer(this, stage));
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(delta);
    }

    private void update(float delta) {

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
