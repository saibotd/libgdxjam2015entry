package com.gdxjam.magellan;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.gdxjam.magellan.screen.BaseScreen;
import com.gdxjam.magellan.ships.AiShipFighter;
import com.gdxjam.magellan.ships.PlayerShip;

/**
 * Created by saibotd on 29.12.15.
 */
public class Battle {
    private BaseScreen screen;
    private IDestroyable offensive;
    private IDestroyable defensive;

    public Battle(BaseScreen screen, IDestroyable playerOne, IDestroyable playerTwo){
        this.screen = screen;
        offensive = playerOne;
        defensive = playerTwo;
        Gdx.app.log("BATTLE", offensive.toString() + " VS " + defensive.toString());
        if(screen != null && offensive instanceof PlayerShip)
            playerTurn();
        else
            turn();
    }

    public Battle(IDestroyable playerOne, IDestroyable playerTwo) {
        this(null, playerOne, playerTwo);
    }

    public void playerTurn(){
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
        }
        if(offensive.isAlive() && defensive.isAlive()){
            offensive = defensive;
            defensive = offensive;
            if(screen != null && offensive instanceof PlayerShip)
                playerTurn();
            else
                turn();
        } else {
            if(!offensive.isAlive()) offensive.destroy();
            if(!defensive.isAlive()) defensive.destroy();
            Gdx.app.log("BATTLE", "OVER");
        }
        if(screen != null)
            screen.show();
    }
}
