package com.gdxjam.magellan.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.gdxjam.magellan.*;
import com.gdxjam.magellan.drones.Drone;
import com.gdxjam.magellan.ships.AiShip;
import com.gdxjam.magellan.ships.PlayerShip;
import com.gdxjam.magellan.ships.Ship;

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
        playerOnScreen.setPosition(-30, 600);
        planetOnScreen.setPosition(800, 600);
        shopOnScreen.setPosition(700, 600);
        resourcesOnScreen.setPosition(400, 720);

        mainContainer.addActor(dronesOnScreen);
        mainContainer.addActor(shipsOnScreen);
        mainContainer.addActor(planetOnScreen);
        mainContainer.addActor(resourcesOnScreen);
        mainContainer.addActor(shopOnScreen);
        mainContainer.addActor(playerOnScreen);
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

    public void showInteractionWindow(final IDrawableWindow gameObj){

        Window window = getWindow(gameObj.getTitle());

        VerticalGroup windowContent = new VerticalGroup();
        Label info = new Label(gameObj.getInfo(), skin);
        HorizontalGroup menu = new HorizontalGroup();
        menu.padTop(20);
        final WindowScreen screen = this;
        if(gameObj instanceof IInteractable) {
            final IInteractable interactable = (IInteractable) gameObj;
            for (final String key : interactable.getInteractions(game.universe.playerShip).keys()) {
                TextButton button = new TextButton(key, skin);
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
        if(gameObj instanceof IDestroyable && ((GameObj)gameObj).submenuOpen == "") {
            final IDestroyable destroyable = (IDestroyable) gameObj;
            TextButton button = new TextButton("ATTACK", skin);
            button.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    closeWindow();
                    new Battle(screen, game.universe.playerShip, destroyable);
                }
            });
            menu.addActor(button);
        }
        windowContent.addActor(info);
        windowContent.addActor(menu);
        window.add(windowContent);
    }



    /*
    private void setupInterfaceMenus(){
        TextButton btnReleaseDrone = new TextButton("RELEASE DRONE", skin);
        btnReleaseDrone.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                game.universe.playerShip.releaseDrone(game.universe.playerShip.drones.random());
                setupInterfaceMenus();
            }
        });
        if(game.universe.playerShip.drones.size > 0)
            interactionsMenu.addActor(btnReleaseDrone);
        for(GameObj gameObj : game.universe.playerShip.sector.gameObjs){
            if(gameObj != game.universe.playerShip) {
                VerticalGroup menu = new VerticalGroup();
                if (gameObj instanceof IDrawableWindow) {
                    final IDrawableWindow drawableGameObj = (IDrawableWindow) gameObj;
                    drawableGameObj.prepareRendering();
                    Label title = new Label(drawableGameObj.getTitle(), skin);
                    Label info = new Label(drawableGameObj.getInfo(), skin);
                    menu.addActor(title);
                    menu.addActor(info);
                }
                if (gameObj instanceof IInteractable) {
                    final IInteractable interactableGameObj = (IInteractable) gameObj;
                    if(interactableGameObj.getInteractions(game.universe.playerShip) != null) {
                        for (final String key : interactableGameObj.getInteractions(game.universe.playerShip).keys()) {
                            TextButton button = new TextButton(key, skin);
                            button.addListener(new ChangeListener() {
                                @Override
                                public void changed(ChangeEvent event, Actor actor) {
                                    interactableGameObj.getInteractions(game.universe.playerShip).get(key).interact();
                                    setupInterfaceMenus();
                                }
                            });
                            menu.addActor(button);
                        }
                    }
                }
                if (gameObj instanceof IDestroyable) {
                    final IDestroyable destroyableGameObj = (IDestroyable) gameObj;
                    TextButton button = new TextButton("attack", skin);
                    button.addListener(new ChangeListener() {
                        @Override
                        public void changed(ChangeEvent event, Actor actor) {
                            game.universe.playerShip.shootAt(destroyableGameObj);
                            setupInterfaceMenus();
                        }
                    });
                    menu.addActor(button);
                }
                interactionsMenu.addActor(menu);
            }
        }
    }
    */

    public void render(float delta){
        super.render(delta);
        renderUi(delta);
    }
}
