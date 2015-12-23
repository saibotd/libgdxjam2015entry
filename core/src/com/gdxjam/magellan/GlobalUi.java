package com.gdxjam.magellan;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.utils.Align;
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
    private Label valueResource1;
    private Label valueResource2;
    private Label valueResource3;
    private Label valueYear;
    private Label valueCredits;
    private Label valuePopulation;
    private Label valueDrones;

    private Sprite topBarBg;

    public GlobalUi(MagellanGame _game) {
        skin = MagellanGame.assets.get("skin/uiskin.json", Skin.class);
        game = _game;
        batch = new SpriteBatch();
        viewport = new FitViewport(1280, 720);
        stage = new Stage(viewport);

        bgTexture = MagellanGame.assets.get("bg.png", Texture.class);
        bgTexture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);

        createTopBar();

    }

    public void createTopBar() {
        topBarBg = new Sprite(MagellanGame.assets.get("topbarBg.png", Texture.class));
        topBarBg.setPosition(0, 720 - 60);
        topBarBg.setSize(1280, 60);


        HorizontalGroup topBar = new HorizontalGroup();

        topBar.align(Align.right);
        stage.addActor(topBar);


        HorizontalGroup groupResources = new HorizontalGroup();
        groupResources.space(15);
        groupResources.setPosition(0, 720 - 60);
        groupResources.setSize(640, 60);
        groupResources.pad(20);
        stage.addActor(groupResources);

        HorizontalGroup groupStats = new HorizontalGroup();
        groupStats.space(15);
        groupStats.setPosition(640, 720 - 60);
        groupStats.setSize(640, 60);
        groupStats.pad(20);
        stage.addActor(groupStats);



        HorizontalGroup groupResource1 = new HorizontalGroup();
        groupResource1.bottom();
        groupResource1.space(5);
        groupResources.addActor(groupResource1);
        Label labelResource1 = new Label(Statics.resource1 + ":", skin);
        valueResource1 = new Label("0", skin, "value");
        groupResource1.addActor(labelResource1);
        groupResource1.addActor(valueResource1);

        HorizontalGroup groupResource2 = new HorizontalGroup();
        groupResource2.bottom();
        groupResource2.space(5);
        groupResources.addActor(groupResource2);
        Label labelResource2 = new Label(Statics.resource2 + ":", skin);
        valueResource2 = new Label("0", skin, "value");
        groupResource2.addActor(labelResource2);
        groupResource2.addActor(valueResource2);

        HorizontalGroup groupResource3 = new HorizontalGroup();
        groupResource3.bottom();
        groupResource3.space(5);
        groupResources.addActor(groupResource3);
        Label labelResource3 = new Label(Statics.resource3 + ":", skin);
        valueResource3 = new Label("0", skin, "value");
        groupResource3.addActor(labelResource3);
        groupResource3.addActor(valueResource3);




        HorizontalGroup groupYear = new HorizontalGroup();
        groupYear.bottom();
        groupYear.space(5);
        groupStats.addActor(groupYear);
        Label labelYear = new Label("Year:", skin);
        valueYear = new Label("0", skin, "value");
        groupYear.addActor(labelYear);
        groupYear.addActor(valueYear);

        HorizontalGroup groupCredits = new HorizontalGroup();
        groupCredits.bottom();
        groupCredits.space(5);
        groupStats.addActor(groupCredits);
        Label labelCredits = new Label("Credits:", skin);
        valueCredits = new Label("0", skin, "value");
        groupCredits.addActor(labelCredits);
        groupCredits.addActor(valueCredits);

        HorizontalGroup groupPopulation = new HorizontalGroup();
        groupPopulation.bottom();
        groupPopulation.space(5);
        groupStats.addActor(groupPopulation);
        Label labelPopulation = new Label("Population:", skin);
        valuePopulation = new Label("0", skin, "value");
        groupPopulation.addActor(labelPopulation);
        groupPopulation.addActor(valuePopulation);

        HorizontalGroup groupDrones = new HorizontalGroup();
        groupDrones.bottom();
        groupDrones.space(5);
        groupStats.addActor(groupDrones);
        Label labelDrones = new Label("Drones:", skin);
        valueDrones = new Label("0", skin, "value");
        groupDrones.addActor(labelDrones);
        groupDrones.addActor(valueDrones);

    }

    public void updateStats() {
        valueResource1.setText(game.gameState.RESSOURCE1 + "");
        valueResource2.setText(game.gameState.RESSOURCE2 + "");
        valueResource3.setText(game.gameState.RESSOURCE3 + "");
        valueYear.setText(game.gameState.YEAR + "");
        valueCredits.setText(game.gameState.CREDITS + "");
        valuePopulation.setText(game.gameState.POPULATION + "");
        valueDrones.setText(game.gameState.DRONES + "");
    }



    public void renderBG(float delta) {

        viewport.apply();
        batch.begin();

        batch.draw(bgTexture, 0, 0, 1280, 720, 0, 0, 2, 2);

        batch.end();

    }

    public void renderOverlays(float delta) {

        updateStats();

        viewport.apply();
        batch.begin();

        topBarBg.draw(batch);

        batch.end();

        stage.draw();
    }


    public void dispose() {
        batch.dispose();
    }

}
