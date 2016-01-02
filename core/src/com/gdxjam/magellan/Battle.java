package com.gdxjam.magellan;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Disposable;
import com.gdxjam.magellan.gameobj.IArmed;
import com.gdxjam.magellan.gameobj.IDestroyable;
import com.gdxjam.magellan.screen.BaseScreen;
import com.gdxjam.magellan.ships.PlayerShip;
import com.gdxjam.magellan.shopitem.ScreenShake;

/**
 * Created by saibotd on 29.12.15.
 */
public class Battle implements Disposable{
    private BaseScreen screen;
    private IDestroyable offensive;
    private IDestroyable defensive;

    public Battle(IDestroyable playerOne, IDestroyable playerTwo){
        this.screen = MagellanGame.instance.windowScreen;
        offensive = playerOne;
        defensive = playerTwo;
        Gdx.app.log("BATTLE", offensive.toString() + " VS " + defensive.toString());
        if(defensive instanceof PlayerShip){
            MagellanGame.instance.showWindowScreen();
            screen.startBGM(MagellanGame.assets.get("battle.mp3", Music.class));
        }
        if(offensive instanceof PlayerShip){
            screen.startBGM(MagellanGame.assets.get("battle.mp3", Music.class));
            playerTurn();
        } else {
            turn();
        }
    }

    public void playerTurn(){
        screen.closeWindow();
        Window window = screen.getWindow("Battle");
        VerticalGroup windowContent = new VerticalGroup();
        HorizontalGroup menu = new HorizontalGroup();
        TextButton buttonAttack = new TextButton("ATTACK", screen.skin);
        TextButton buttonFlee = new TextButton("Flee", screen.skin);
        buttonAttack.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                turn();
            }
        });
        buttonFlee.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                dispose();
                screen.game.showMapScreen();
            }
        });
        menu.addActor(buttonAttack);
        menu.addActor(buttonFlee);
        windowContent.addActor(menu);
        window.add(windowContent);
    }

    public void turn(){
        Gdx.app.log("BATTLE", offensive.toString() + " ATTACKS " + defensive.toString());
        if(offensive instanceof IArmed){
            IArmed armedOffensive = (IArmed) offensive;
            int i = armedOffensive.shootAt(defensive);
            Gdx.app.log("BATTLE", offensive.toString() + " ATTACKS FOR " + i);
            Gdx.app.log("BATTLE", defensive.toString() + " HEALTH AT " + defensive.getHealth());
            if(defensive instanceof PlayerShip){
                MagellanGame.instance.windowScreen.shake(i);
            }
        }
        if(offensive.isAlive() && defensive.isAlive()){
            IDestroyable _offensive = offensive;
            IDestroyable _defensive = defensive;
            offensive = _defensive;
            defensive = _offensive;
            if(offensive instanceof PlayerShip)
                playerTurn();
            else
                turn();
        } else {
            if(!offensive.isAlive()) offensive.destroy();
            if(!defensive.isAlive()) defensive.destroy();
            Gdx.app.log("BATTLE", "OVER");
            dispose();
        }
        if(offensive instanceof PlayerShip){
            screen.closeWindow();
            screen.show();
        }
    }

    @Override
    public void dispose() {
        screen.closeWindow();
        screen.startBGM();
    }
}
