package com.gdxjam.magellan.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.gdxjam.magellan.MagellanGame;
import com.gdxjam.magellan.UiTopbar;

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
    private static Music bgm;
    public UiTopbar topbar;
    private Texture bgTexture;
    public Container<Window> windowContainer;
    public Table mainContainer;
    public Table sectorContainer;

    public BaseScreen(MagellanGame _game){
        skin = MagellanGame.assets.get("skin/uiskin.json", Skin.class);
        game = _game;
        batch = new SpriteBatch();
        viewport = new FitViewport(1280, 720);
        stage = new Stage(viewport);

        sectorContainer = new Table();
        sectorContainer.setSize(1280,720);
        stage.addActor(sectorContainer);

        mainContainer = new Table();
        mainContainer.setSize(1280,720);
        stage.addActor(mainContainer);

        windowContainer = new Container<Window>();
        windowContainer.setSize(1280, 720);
        stage.addActor(windowContainer);

        HorizontalGroup menu = new HorizontalGroup();
        menu.setPosition(10,20);
        btnWindow = new TextButton("SHOW SURROUNDINGS", skin);
        btnMap = new TextButton("STAR MAP", skin);
        menu.addActor(btnWindow);
        menu.addActor(btnMap);
        stage.addActor(menu);

        bgTexture = MagellanGame.assets.get("bg.png", Texture.class);
        bgTexture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        topbar = new UiTopbar(game, stage);
        btnWindow.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                game.showWindowScreen();
            }
        });
        btnMap.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                game.showMapScreen();
            }
        });

        bgm = MagellanGame.assets.get("bgm0.mp3", Music.class);

    }

    public void startBGM(){
        startBGM(MagellanGame.assets.get("bgm" + MathUtils.random(0,3) + ".mp3", Music.class));
    }

    public void startBGM(Music song){
        if(bgm != null && bgm.isPlaying()) bgm.stop();
        bgm = song;
        bgm.setVolume(.2f);
        bgm.play();
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

    public void renderBG(float delta) {

        viewport.apply();
        batch.begin();
        batch.draw(bgTexture, 0, 0, 1280, 720, 0, 0, 2, 2);
        batch.end();

    }

    public Window getWindow(String title){
        windowContainer.clear();
        Window window = new Window(title, skin);
        window.setMovable(false);
        window.setModal(true);
        window.setWidth(500);
        window.padTop(70);
        window.padLeft(20);
        Image closeButton = new Image(new TextureRegion(MagellanGame.assets.get("skin/uiskin.png", Texture.class), 182, 128, 51, 51));
        closeButton.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                closeWindow();
            }
        });
        Group closeContainer = new Group();
        closeContainer.addActor(closeButton);
        closeButton.setSize(20,20);
        closeContainer.setSize(20,20);
        window.getTitleTable().add(closeContainer);
        windowContainer.setActor(window);

        TextureRegionDrawable btnUp = new TextureRegionDrawable(new TextureRegion(MagellanGame.assets.get("skin/uiskin.png", Texture.class), 182, 128, 51, 51));
        TextureRegionDrawable btnDn = new TextureRegionDrawable(new TextureRegion(MagellanGame.assets.get("skin/uiskin.png", Texture.class), 182, 179, 51, 51));
        return window;
    }

    public void closeWindow() {
        windowContainer.clear();
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderBG(delta);

        update(delta);

        viewport.apply();


    }

    public void renderUi(float delta) {

        topbar.renderBg(delta, batch);
        stage.draw();

    }

    private void update(float delta) {
        if(!bgm.isPlaying())
            startBGM();

        topbar.updateStats();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);


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
