package com.gdxjam.magellan.screen;

import com.badlogic.gdx.Gdx;
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

    public WindowScreen(MagellanGame game) {
        super(game);
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
                        show();
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

    public void render(float delta){
        super.render(delta);
        renderUi(delta);
        ScreenShake.update(stage.getCamera());
    }
}
