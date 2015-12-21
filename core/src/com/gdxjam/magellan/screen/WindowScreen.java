package com.gdxjam.magellan.screen;

import com.gdxjam.magellan.GameObj;
import com.gdxjam.magellan.IDrawableWindow;
import com.gdxjam.magellan.MagellanGame;

/**
 * Created by lolcorner on 20.12.2015.
 */
public class WindowScreen extends BaseScreen {
    public WindowScreen(MagellanGame game) {
        super(game);
    }

    public void show(){
        for(GameObj gameObj : game.universe.playerShip.sector.gameObjs){
            if(gameObj instanceof IDrawableWindow){
                ((IDrawableWindow) gameObj).prepareRendering();
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
