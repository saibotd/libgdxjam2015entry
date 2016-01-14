package com.gdxjam.magellan;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Timer;
import com.gdxjam.magellan.gameobj.GameObj;
import com.gdxjam.magellan.gameobj.IArmed;
import com.gdxjam.magellan.gameobj.IDestroyable;
import com.gdxjam.magellan.gameobj.IDrawableWindow;
import com.gdxjam.magellan.screen.BaseScreen;
import com.gdxjam.magellan.screen.WindowScreen;
import com.gdxjam.magellan.ships.PlayerShip;
import com.gdxjam.magellan.shopitem.ScreenShake;

/**
 * Created by saibotd on 29.12.15.
 */
public class Battle implements Disposable{
    private WindowScreen screen;
    private IDestroyable offensive;
    private IDestroyable defensive;

    public Battle(IDestroyable playerOne, IDestroyable playerTwo){
        this.screen = MagellanGame.instance.windowScreen;
        offensive = playerOne;
        defensive = playerTwo;
        if(((GameObj) offensive).sector != ((GameObj) defensive).sector){
            dispose();
            return;
        }
        Gdx.app.log("BATTLE", offensive.toString() + " VS " + defensive.toString());
        if(defensive instanceof PlayerShip){
            MagellanGame.instance.showWindowScreen();
            screen.startBGM(MagellanGame.assets.get("battle.mp3", Music.class));
            showAlertWindow();
        }
        if(offensive instanceof PlayerShip){
            screen.startBGM(MagellanGame.assets.get("battle.mp3", Music.class));
            playerTurn();
        } else {
            turn();
        }
    }

    private boolean isPlayerBattle(){
        return offensive instanceof PlayerShip || defensive instanceof PlayerShip;
    }

    public void turn(){
        Gdx.app.log("BATTLE", offensive.toString() + " ATTACKS " + defensive.toString());
        if(((GameObj) offensive).sector != ((GameObj) defensive).sector){
            dispose();
            return;
        }

        screen.closeWindow();

        float panShoot = (offensive instanceof PlayerShip) ? -0.8f : 0.8f;
        final float panImpact = (offensive instanceof PlayerShip) ? 0.8f : -0.8f;

        if(isPlayerBattle())
            MagellanGame.soundFx.weaponFire.random().play(0.8f,1,panShoot);


        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                IArmed armedOffensive = (IArmed) offensive;
                int i = armedOffensive.shootAt(defensive);
                Gdx.app.log("BATTLE", offensive.toString() + " ATTACKS FOR " + i);
                Gdx.app.log("BATTLE", defensive.toString() + " HEALTH AT " + defensive.getHealth());
                if(defensive instanceof PlayerShip){
                    MagellanGame.instance.windowScreen.shake(i);
                }
                if(isPlayerBattle()){
                    if (i == -1) {
                        MagellanGame.instance.windowScreen.showShield(defensive);
                        MagellanGame.soundFx.shield.random().play(0.7f,1,panImpact);
                    } else {
                        MagellanGame.instance.windowScreen.showDamage(defensive, i);
                        MagellanGame.soundFx.explosions.random().play(1,1,panImpact);
                    }


                }
            }
        }, 0.7f);




        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                if(offensive.isAlive() && defensive.isAlive()){
                    IDestroyable _offensive = offensive;
                    offensive = defensive;
                    defensive = _offensive;
                    if(offensive instanceof PlayerShip){
                        Gdx.app.log("HERE", "HERE");
                        screen.closeWindow();
                        playerTurn();
                        return;
                    } else
                        turn();
                } else {
                    if(!offensive.isAlive()) offensive.destroy();
                    if(!defensive.isAlive()) defensive.destroy();
                    Gdx.app.log("BATTLE", "OVER");
                    dispose();
                }
                if(offensive instanceof PlayerShip){
                    screen.drawSurroundings();
                }
            }
        }, 1.6f);


    }

    public void playerTurn(){
        //screen.closeWindow();
        Gdx.app.log("playerTurn", "1");
        Window window = screen.getWindow("Battle");
        VerticalGroup windowContent = new VerticalGroup();
        HorizontalGroup menu = new HorizontalGroup();
        HorizontalGroup info = new HorizontalGroup();
        info.space(20);
        menu.padTop(20);
        menu.space(6);
        menu.fill();

        String textLeft = "Your ship\nHealth: "+ offensive.getHealth();
        textLeft += "\nShield: " + Math.round(offensive.getShield() * 100) + "%";
        textLeft += "\nAttack: " + ((IArmed)offensive).getAttack();

        String textRight = "Enemy\nHealth: "+ defensive.getHealth();
        textRight += "\nShield: " + Math.round(defensive.getShield() * 100) + "%";
        if(defensive instanceof IArmed)
            textRight += "\nAttack: " + ((IArmed)defensive).getAttack();

        info.addActor(new Label(textLeft, screen.skin, "window"));
        info.addActor(new Label(textRight, screen.skin, "window"));
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
        Gdx.app.log("playerTurn", "2");
        menu.addActor(buttonAttack);
        menu.addActor(buttonFlee);
        windowContent.addActor(info);
        windowContent.addActor(menu);
        window.add(windowContent);
    }

    private void showAlertWindow() {
        screen.closeWindow();
        Window window = screen.getWindow("You are under Attack!");
        VerticalGroup windowContent = new VerticalGroup();
        HorizontalGroup menu = new HorizontalGroup();
        TextButton buttonOK = new TextButton("OK", screen.skin);
        buttonOK.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                playerTurn();
            }
        });
        menu.addActor(buttonOK);
        windowContent.addActor(menu);
        window.add(windowContent);
    }

    private void showOutcomeWindow() {
        screen.closeWindow();
        Window window = screen.getWindow("Battle outcome");
        VerticalGroup windowContent = new VerticalGroup();
        HorizontalGroup menu = new HorizontalGroup();
        TextButton buttonOK = new TextButton("OK", screen.skin);
        buttonOK.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                screen.closeWindow();
                screen.drawSurroundings();
            }
        });
        menu.addActor(buttonOK);
        windowContent.addActor(menu);
        window.add(windowContent);
    }

    @Override
    public void dispose() {
        Gdx.app.log("BATTLE", "DISPOSE");
        if(isPlayerBattle()){
            screen.drawSurroundings();
            showOutcomeWindow();
            screen.startBGM();
        }
        offensive = null;
        defensive = null;
    }
}
