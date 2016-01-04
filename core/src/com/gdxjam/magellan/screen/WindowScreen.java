package com.gdxjam.magellan.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.gdxjam.magellan.*;
import com.gdxjam.magellan.drones.Drone;
import com.gdxjam.magellan.gameobj.*;
import com.gdxjam.magellan.ships.AiShip;
import com.gdxjam.magellan.ships.PlayerShip;
import com.gdxjam.magellan.shopitem.ScreenShake;

/**
 * Created by lolcorner on 20.12.2015.
 */
public class WindowScreen extends BaseScreen {
    private final VerticalGroup dronesOnScreen;
    private final VerticalGroup shipsOnScreen;
    private final VerticalGroup planetOnScreen;
    private final VerticalGroup resourcesOnScreen;
    private final VerticalGroup playerOnScreen;
    private final VerticalGroup shopOnScreen;
    private Sprite starfield;

    public WindowScreen(MagellanGame game) {
        super(game);

        starfield = createStarfield();
        starfield.setSize(1280,720);

        dronesOnScreen = new VerticalGroup();
        shipsOnScreen = new VerticalGroup();
        playerOnScreen = new VerticalGroup();
        planetOnScreen = new VerticalGroup();
        shopOnScreen = new VerticalGroup();
        resourcesOnScreen = new VerticalGroup();

        dronesOnScreen.setPosition(100, 720);
        shipsOnScreen.setPosition(200, 720);
        playerOnScreen.setPosition(-30, 500);
        planetOnScreen.setPosition(800, 600);
        shopOnScreen.setPosition(700, 600);
        resourcesOnScreen.setPosition(400, 720);

        sectorContainer.addActor(dronesOnScreen);
        sectorContainer.addActor(shipsOnScreen);
        sectorContainer.addActor(planetOnScreen);
        sectorContainer.addActor(resourcesOnScreen);
        sectorContainer.addActor(shopOnScreen);
        sectorContainer.addActor(playerOnScreen);
    }

    public void show(){
        super.show();
        closeWindow();
        drawSurroundings();
    }

    public void drawSurroundings(){
        dronesOnScreen.clear();
        resourcesOnScreen.clear();
        playerOnScreen.clear();
        shopOnScreen.clear();
        shipsOnScreen.clear();
        planetOnScreen.clear();
        for(final GameObj gameObj : game.universe.playerShip.sector.gameObjs){
            if(gameObj instanceof IDrawableWindow){
                Actor actor = ((IDrawableWindow) gameObj).getActor();
                if(gameObj instanceof Planet){
                    planetOnScreen.setWidth(actor.getWidth());
                    planetOnScreen.addActor(actor);
                }
                if(gameObj instanceof Drone) {
                    dronesOnScreen.setWidth(actor.getWidth());
                    dronesOnScreen.addActor(actor);
                }
                if(gameObj instanceof Shop) {
                    shopOnScreen.setWidth(actor.getWidth());
                    shopOnScreen.addActor(actor);
                }
                if(gameObj instanceof PlayerShip) {
                    playerOnScreen.setWidth(actor.getWidth());
                    playerOnScreen.addActor(actor);
                }
                if(gameObj instanceof AiShip) {
                    shipsOnScreen.setWidth(actor.getWidth());
                    shipsOnScreen.addActor(actor);
                }
                if(gameObj instanceof MeteoroidField) {
                    resourcesOnScreen.setWidth(actor.getWidth());
                    resourcesOnScreen.addActor(actor);
                }

                if(gameObj instanceof IDrawableWindow) {
                    actor.addListener(new InputListener() {
                        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                            return true;
                        }
                        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                            gameObj.submenuOpen = "";
                            showInteractionWindow((IDrawableWindow) gameObj);
                        }
                    });
                }
            }
        }
    }

    public void shake(int i){
        ScreenShake.shakeScreen(30,stage.getCamera().position.cpy(), 10 * i);
    }

    public void showInteractionWindow(final IDrawableWindow gameObj){

        Window window = getWindow(gameObj.getTitle() + "    ");
        window.getTitleLabel().setEllipsis(false);
        VerticalGroup windowContent = new VerticalGroup();
        windowContent.fill();
        Label info = new Label(gameObj.getInfo(), skin, "window");
        VerticalGroup menu = new VerticalGroup();
        menu.padTop(20);
        menu.space(6);
        menu.fill();
        final WindowScreen screen = this;
        if(gameObj instanceof IInteractable) {
            final IInteractable interactable = (IInteractable) gameObj;
            for (final String key : interactable.getInteractions(game.universe.playerShip).keys()) {
                TextButton button = new TextButton(key, skin, "yellow");
                button.addListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        interactable.getInteractions(game.universe.playerShip).get(key).interact();
                        drawSurroundings();
                    }
                });
                menu.addActor(button);
            }
        }
        if(gameObj instanceof IDestroyable && gameObj != game.universe.playerShip && ((GameObj)gameObj).submenuOpen == "") {
            final IDestroyable destroyable = (IDestroyable) gameObj;
            TextButton button = new TextButton("ATTACK", skin, "red");
            button.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    closeWindow();
                    new Battle(game.universe.playerShip, destroyable);
                }
            });
            menu.addActor(button);
        }
        windowContent.addActor(info);
        windowContent.addActor(menu);
        window.add(windowContent).expandX().fill();
    }

    public Sprite createStarfield() {
        int width = 1280*4;
        int height = 720*4;
        int amount_small = 300;
        int amount_mid = 150;
        int amount_big = 20;

        Pixmap bg = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        bg.setColor(MagellanColors.UNIVERSE_BG);
        bg.fillRectangle(0,0,width,height);

        bg.setColor(Color.WHITE);
        for(int i = 0; i < amount_small; i++) {
            bg.fillCircle(MathUtils.floor(width * MathUtils.random()), MathUtils.floor(height * MathUtils.random()), 1);
        }
        for(int i = 0; i < amount_mid; i++) {
            bg.fillCircle(MathUtils.floor(width * MathUtils.random()), MathUtils.floor(height * MathUtils.random()), 2);
        }
        for(int i = 0; i < amount_big; i++) {
            bg.fillCircle(MathUtils.floor(width * MathUtils.random()), MathUtils.floor(height * MathUtils.random()), 5);
        }
        Texture field = new Texture(bg);
        bg.dispose();

        return new Sprite(field);
    }

    public void render(float delta){
        super.render(delta);
        batch.begin();
        starfield.draw(batch);
        batch.end();
        renderUi(delta);
        ScreenShake.update(stage.getCamera());
    }
}
