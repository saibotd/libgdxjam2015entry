package com.gdxjam.magellan;

import com.badlogic.gdx.Gdx;

/**
 * Created by saibotd on 29.12.15.
 */
public class Battle {
    private IDestroyable offensive;
    private IDestroyable defensive;

    public Battle(IDestroyable playerOne, IDestroyable playerTwo){
        offensive = playerOne;
        defensive = playerTwo;
        Gdx.app.log("BATTLE", offensive.toString() + " VS " + defensive.toString());
        turn();
    }

    public void turn(){
        if(offensive instanceof IArmed){
            IArmed armedOffensive = (IArmed) offensive;
            Gdx.app.log("BATTLE", offensive.toString() + " ATTACKS FOR " + armedOffensive.shootAt(defensive));
        }
        if(offensive.isAlive() && defensive.isAlive()){
            offensive = defensive;
            defensive = offensive;
            turn();
        }
    }
}
