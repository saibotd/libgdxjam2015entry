package com.gdxjam.magellan.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.gdxjam.magellan.GameObj;
import com.gdxjam.magellan.IDrawableWindow;
import com.gdxjam.magellan.IInteractable;
import com.gdxjam.magellan.MagellanGame;

/**
 * Created by lolcorner on 20.12.2015.
 */
public class WindowScreen extends BaseScreen {
    private final HorizontalGroup interactionsMenu;

    public WindowScreen(MagellanGame game) {
        super(game);
        interactionsMenu = new HorizontalGroup();
        interactionsMenu.setPosition(400,400);
        stage.addActor(interactionsMenu);
    }

    public void show(){
        super.show();
        setupInterfaceMenus();
    }

    private void setupInterfaceMenus(){
        interactionsMenu.clear();
        for(GameObj gameObj : game.universe.playerShip.sector.gameObjs){
            if(gameObj instanceof IDrawableWindow){
                ((IDrawableWindow) gameObj).prepareRendering();
            }
            if(gameObj instanceof IInteractable){
                final IInteractable interactableGameObj = (IInteractable) gameObj;
                VerticalGroup menu = new VerticalGroup();
                for(final String key : interactableGameObj.getInteractions(game.universe.playerShip).keys()){
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
                Label info = new Label(interactableGameObj.getInfo(), skin);
                menu.addActor(info);
                interactionsMenu.addActor(menu);
            }
        }
    }

    public void render(float delta){
        super.render(delta);

        batch.begin();
        for(GameObj gameObj : game.universe.playerShip.sector.gameObjs){
            if(gameObj instanceof IDrawableWindow){
                ((IDrawableWindow) gameObj).render(batch, delta);
            }
        }
        batch.end();

        stage.draw();
    }
}
