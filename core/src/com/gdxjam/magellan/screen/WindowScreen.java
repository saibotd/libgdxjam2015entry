package com.gdxjam.magellan.screen;

import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Interpolation;
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
import com.gdxjam.magellan.tweening.ActorAccessor;
import com.gdxjam.magellan.tweening.SpriteAccessor;

/**
 * Created by lolcorner on 20.12.2015.
 */
public class WindowScreen extends BaseScreen {
    private final VerticalGroup dronesOnScreen;
    private final VerticalGroup shipsOnScreen;
    private final Container<Actor> planetOnScreen;
    private final Container<Actor> resourcesOnScreen;
    private final Container<Actor> playerOnScreen;
    private final VerticalGroup shopOnScreen;
    private Sprite starfield;
    private Sector lastShownSector;

    public WindowScreen(MagellanGame game) {
        super(game);

        starfield = createStarfield();
        starfield.setSize(1280,720);

        dronesOnScreen = new VerticalGroup();
        shipsOnScreen = new VerticalGroup();
        playerOnScreen = new Container<Actor>();
        planetOnScreen = new Container<Actor>();
        shopOnScreen = new VerticalGroup();
        resourcesOnScreen = new Container<Actor>();

        dronesOnScreen.setPosition(100, 720);
        shipsOnScreen.setPosition(200, 720);
        playerOnScreen.setPosition(-600, -110);
        playerOnScreen.setSize(assetToGameSize(2523), assetToGameSize(2064));
        planetOnScreen.setPosition(1280 - assetToGameSize(1386), 720 - assetToGameSize(1677));
        planetOnScreen.setSize(assetToGameSize(1386), assetToGameSize(1677));
        shopOnScreen.setPosition(700, 600);
        resourcesOnScreen.setPosition(1280 - assetToGameSize(2341), 0);
        resourcesOnScreen.setSize(assetToGameSize(2341), assetToGameSize(1318));

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
        playerOnScreen.setPosition(-500, -110);
        tweenManager.killAll();
        if (lastShownSector != MagellanGame.instance.universe.playerShip.sector) {
            Tween.to(playerOnScreen, ActorAccessor.POSITION_XY, 0.8f).target(-300, -100).ease(TweenEquations.easeOutCubic).start(tweenManager);
        } else {
            playerOnScreen.setPosition(-300, -100);
        }
        Tween.to(playerOnScreen, ActorAccessor.POSITION_Y,5f).target(-80).ease(TweenEquations.easeInOutCubic).repeatYoyo(-1,0f).delay(1f).start(tweenManager);
        Tween.to(playerOnScreen, ActorAccessor.POSITION_X,7f).target(-290).ease(TweenEquations.easeInOutCubic).repeatYoyo(-1,0f).delay(1f).start(tweenManager);
        lastShownSector = game.universe.playerShip.sector;
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
                    planetOnScreen.setActor(actor);
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
                    playerOnScreen.setActor(actor);
                }
                if(gameObj instanceof AiShip) {
                    shipsOnScreen.setWidth(actor.getWidth());
                    shipsOnScreen.addActor(actor);
                }
                if(gameObj instanceof MeteoroidField) {
                    resourcesOnScreen.setActor(actor);
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

    public int assetToGameSize(int size) {
        return size/3;
    }
}
