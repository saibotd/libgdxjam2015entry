package com.gdxjam.magellan.screen;

import com.gdxjam.magellan.GameObj;
import com.gdxjam.magellan.IDrawable;
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
            if(gameObj instanceof IDrawable){
                ((IDrawable) gameObj).prepareRendering();
            }
        }
    }

    public void render(float delta){
        super.render(delta);

        batch.begin();
        for(GameObj gameObj : game.universe.playerShip.sector.gameObjs){
            if(gameObj instanceof IDrawable){
                ((IDrawable) gameObj).render(batch, delta);
            }
        }
        batch.end();

        stage.draw();
    }
}
