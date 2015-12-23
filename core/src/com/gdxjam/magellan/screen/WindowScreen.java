package com.gdxjam.magellan.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
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
        interactionsMenu.setPosition(400,40);
        stage.addActor(interactionsMenu);
    }

    public void show(){
        super.show();
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
                            Gdx.app.log("fsdfs", "fdsf");
                            interactableGameObj.getInteractions(game.universe.playerShip).get(key).interact();
                        }
                    });
                    menu.addActor(button);
                }
                interactionsMenu.addActor(menu);
            }
        }
        stage.act();
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
