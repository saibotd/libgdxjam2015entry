package com.gdxjam.magellan;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Timer;
import com.gdxjam.magellan.gameobj.*;
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
    private int damageDone = 0;

    public Battle(IDestroyable playerOne, IDestroyable playerTwo){
        this.screen = MagellanGame.instance.windowScreen;
        offensive = playerOne;
        defensive = playerTwo;
        if(playerOne.inBattle() || playerTwo.inBattle()){
            return;
        }
        playerOne.setBattle(this);
        playerTwo.setBattle(this);
        if(((GameObj) offensive).sector != ((GameObj) defensive).sector){
            dispose();
            return;
        }
        Gdx.app.log("BATTLE", offensive.toString() + " VS " + defensive.toString());
        if(defensive instanceof PlayerShip){
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    MagellanGame.instance.showWindowScreen();
                    screen.startBGM(MagellanGame.assets.get("battle.mp3", Music.class));
                }
            }, 1);
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
        if (offensive instanceof Planet) {
            dispose();
            return;
        }
        Gdx.app.log("BATTLE", offensive.toString() + " ATTACKS " + defensive.toString());
        if(((GameObj) offensive).sector != ((GameObj) defensive).sector){
            dispose();
            return;
        }

        float panShoot = (offensive instanceof PlayerShip) ? -0.8f : 0.8f;
        final float panImpact = (offensive instanceof PlayerShip) ? 0.8f : -0.8f;

        if(isPlayerBattle()){
            screen.closeWindow();
            if (((IArmed) offensive).getAttack() > 1) {
                MagellanGame.soundFx.weaponFire.random().play(0.8f, 1, panShoot);
            } else {
                MagellanGame.soundFx.weaponFireSmall.random().play(0.8f, 1, panShoot);
            }
        }


        if(isPlayerBattle() && offensive instanceof IArmed) {
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    IArmed armedOffensive = (IArmed) offensive;
                    int i = armedOffensive.shootAt(defensive);
                    damageDone+=i;
                    Gdx.app.log("BATTLE", offensive.toString() + " ATTACKS FOR " + i);
                    Gdx.app.log("BATTLE", defensive.toString() + " HEALTH AT " + defensive.getHealth());
                    if (defensive instanceof PlayerShip) {
                        MagellanGame.instance.windowScreen.shake(i);
                    }
                    if (i == -1) {
                        MagellanGame.instance.windowScreen.showShield(defensive);
                        MagellanGame.soundFx.shield.random().play(0.7f, 1, panImpact);
                    } else {
                        MagellanGame.instance.windowScreen.showDamage(defensive, i);
                        MagellanGame.soundFx.explosions.random().play(1, 1, panImpact);
                    }
                }
            }, 0.7f);
        } else if(offensive instanceof IArmed) {
            IArmed armedOffensive = (IArmed) offensive;
            int i = armedOffensive.shootAt(defensive);
            damageDone+=i;
            Gdx.app.log("BATTLE", offensive.toString() + " ATTACKS FOR " + i);
            Gdx.app.log("BATTLE", defensive.toString() + " HEALTH AT " + defensive.getHealth());
        }

        if(isPlayerBattle()){
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    if (offensive.isAlive() && defensive.isAlive()) {
                        IDestroyable _offensive = offensive;
                        offensive = defensive;
                        defensive = _offensive;
                        if (offensive instanceof PlayerShip) {
                            Gdx.app.log("HERE", "HERE");
                            screen.closeWindow();
                            playerTurn();
                            return;
                        } else
                            turn();
                    } else {
                        if (!defensive.isAlive()) defensive.destroy();
                        Gdx.app.log("BATTLE", "OVER");
                        dispose();
                    }
                    if (offensive instanceof PlayerShip) {
                        screen.drawSurroundings();
                    }
                }
            }, 1.6f);
        } else {
            if (offensive.isAlive() && defensive.isAlive()) {
                IDestroyable _offensive = offensive;
                offensive = defensive;
                defensive = _offensive;
                turn();
            } else {
                if (!defensive.isAlive()) defensive.destroy();
                Gdx.app.log("BATTLE", "OVER");
                dispose();
            }
        }


    }

    public void playerTurn(){
        //screen.closeWindow();
        Gdx.app.log("playerTurn", "1");
        Window window = screen.getWindowWithoutClose("Battle");
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

    private void showOutcomeWindow() {
        if(damageDone <= 0) return;
        screen.closeWindow();
        int credits = MathUtils.random(0, damageDone*250);
        MagellanGame.gameState.CREDITS += credits;
        Window window = screen.getWindow("Battle outcome");
        VerticalGroup windowContent = new VerticalGroup();
        windowContent.space(10);
        Label info = new Label("", screen.skin, "window");
        if(MagellanGame.instance.universe.playerShip.isAlive()){
            Gdx.app.log("defensive", defensive.toString());
            Gdx.app.log("offensive", offensive.toString());
            Planet planet = null;
            if (defensive instanceof Planet) planet = (Planet) defensive;
            if (offensive instanceof Planet) planet = (Planet) offensive;

            if (planet != null && isPlayerBattle()) {
                if (planet.population == 0) {
                    info.setText("Victory! There is no life\nleft on this planet.");
                } else {
                    info.setText("You killed much of whatever was\nliving on that planet but\nthere are still some left!");
                }
            } else {
                info.setText("Victory! As you scan the\nremaining scraps you gather " + credits + " credits!");
            }
        } else {
            info.setText("That's it. Humanity's last chance\nand you blew it. The enemy will\ncapture each of your planets\nand destroy all human life forever.");
        }
        windowContent.addActor(info);
        HorizontalGroup menu = new HorizontalGroup();
        TextButton buttonOK = new TextButton("OK", screen.skin);
        buttonOK.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(!MagellanGame.instance.universe.playerShip.isAlive()){
                    MagellanGame.instance.showTitleScreen();
                    MagellanGame.instance.restartGame();
                }
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
        offensive.setBattle(null);
        defensive.setBattle(null);
        offensive = null;
        defensive = null;
    }
}
