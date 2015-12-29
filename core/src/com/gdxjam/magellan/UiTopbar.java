package com.gdxjam.magellan;

import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;

/**
 * Created by Felix on 22.12.2015.
 */
public class UiTopbar {

    public MagellanGame game;
    public FitViewport viewport;
    public Skin skin;
    public Stage stage;
    private Label valueResource1;
    private Label valueResource2;
    private Label valueResource3;
    private Label valueYear;
    private Label valueCredits;
    private Label valuePopulation;
    private Label valueDrones;
    private Label valueHealth;

    private Sprite topBarBg;

    public UiTopbar(MagellanGame game, Stage stage) {

        this.game = game;
        this.stage = stage;
        skin = MagellanGame.assets.get("skin/uiskin.json", Skin.class);



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


        valueResource1 = new Label("0", skin, "value");
        valueResource2 = new Label("0", skin, "value");
        valueResource3 = new Label("0", skin, "value");
        groupResources.addActor(resourceLabel(1, valueResource1));
        groupResources.addActor(resourceLabel(2, valueResource2));
        groupResources.addActor(resourceLabel(3, valueResource3));

        valueYear = new Label("0", skin, "value");
        valueCredits = new Label("0", skin, "value");
        valuePopulation = new Label("0", skin, "value");
        valueDrones = new Label("0", skin, "value");
        valueHealth = new Label("0", skin, "value");
        groupStats.addActor(simpleLabel("Year", valueYear));
        groupStats.addActor(simpleLabel("Credits", valueCredits));
        groupStats.addActor(simpleLabel("Population", valuePopulation));
        groupStats.addActor(simpleLabel("Drones", valueDrones));
        groupStats.addActor(simpleLabel("Health", valueHealth));


    }

    public void updateStats() {
        valueResource1.setText(MagellanGame.gameState.RESOURCE1 + "");
        valueResource2.setText(MagellanGame.gameState.RESOURCE2 + "");
        valueResource3.setText(MagellanGame.gameState.RESOURCE3 + "");
        valueYear.setText(MagellanGame.gameState.YEAR + "");
        valueCredits.setText(MagellanGame.gameState.CREDITS + "");
        valuePopulation.setText(MagellanGame.gameState.POPULATION + "");
        valueDrones.setText(MagellanGame.gameState.DRONES + "");
        valueHealth.setText(game.universe.playerShip.health + "");
    }




    public void dispose() {

    }


    public HorizontalGroup resourceLabel(int resourceNum, Label valueField) {

        Color color;
        String label;
        switch (resourceNum) {
            case 1:
                color = MagellanColors.RESOURCE_1;
                label = Statics.resource1;
                break;
            case 2:
                color = MagellanColors.RESOURCE_2;
                label = Statics.resource2;
                break;
            case 3:
                color = MagellanColors.RESOURCE_3;
                label = Statics.resource3;
                break;
            default:
                color = Color.WHITE;
                label = "";
                valueField = valueResource1;
        }

        HorizontalGroup groupResource = new HorizontalGroup();
        groupResource.bottom();
        groupResource.space(5);
        groupResource.setHeight(50);

        Image dot = new Image(MagellanGame.assets.get("dot.png", Texture.class));
        dot.setColor(color);

        Container<Image> dotContainer = new Container<Image>();
        dotContainer.size(13,13);
        dotContainer.padBottom(3);
        dotContainer.setActor(dot);

        Label labelResource = new Label(label + ":", skin);

        groupResource.addActor(dotContainer);
        groupResource.addActor(labelResource);
        groupResource.addActor(valueField);

        return groupResource;
    }


    public HorizontalGroup simpleLabel(String label, Label valueField) {


        HorizontalGroup groupResource = new HorizontalGroup();
        groupResource.bottom();
        groupResource.space(5);
        groupResource.setHeight(50);

        Label labelResource = new Label(label + ":", skin);

        groupResource.addActor(labelResource);
        groupResource.addActor(valueField);

        return groupResource;
    }

    public void renderBg(float delta, SpriteBatch batch) {
        batch.begin();
        topBarBg.draw(batch);
        batch.end();
    }

}
