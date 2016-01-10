package com.gdxjam.magellan.screen;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.gdxjam.magellan.MagellanColors;
import com.gdxjam.magellan.MagellanGame;
import com.gdxjam.magellan.Statics;

/**
 * Created by lolcorner on 09.01.2016.
 */
public class TitleScreen extends BaseScreen {
    private final Sprite bg;
    private final Sprite title;

    public TitleScreen(final MagellanGame game) {
        super(game);
        stage.clear();
        createStarfield();
        bg = new Sprite(MagellanGame.assets.get("sectorview_planet_"+ MathUtils.random(1,4)+".png", Texture.class));
        bg.setSize(600,600);
        bg.setColor(MagellanColors.PLANET_2);
        bg.setPosition(1280-bg.getWidth(),720-bg.getHeight());
        title = new Sprite(MagellanGame.assets.get("title.png", Texture.class));
        title.setSize(721*0.7f, 317*.7f);
        title.setPosition((1280 - title.getWidth())/2, 350);
        String infoText = "Created for libGDXJam 2015/16";
        infoText += "\nIdea and programming by Felix Schittig and Tobias Duehr";
        infoText += "\nArtwork by Kilian Wilde";
        infoText += "\nMusic by Mark Sparling";
        Label info = new Label(infoText, skin);
        info.setAlignment(Align.center);
        info.setWidth(1280);
        info.setPosition(0, 100);
        stage.addActor(info);
        VerticalGroup mainMenu = new VerticalGroup();

        mainMenu.pad(20);
        mainMenu.space(10);
        mainMenu.setWidth(200);
        mainMenu.setPosition(1280/2-100,300);

        TextButton btnStart = new TextButton("Start game", skin);
        btnStart.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.showMapScreen();
                startBGM();
            }
        });

        TextButton btnStory = new TextButton("Story", skin);

        mainMenu.addActor(btnStart);
        mainMenu.addActor(btnStory);
        stage.addActor(mainMenu);
    }
    public void show(){
        super.show();
        startBGM(MagellanGame.assets.get("bgm3.mp3", Music.class));
    }

    public void render(float delta){
        renderBG();
        batch.begin();
        starfield.draw(batch);
        bg.draw(batch);
        title.draw(batch);
        batch.end();
        stage.draw();
    }
}
