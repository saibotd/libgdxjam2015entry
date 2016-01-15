package com.gdxjam.magellan.screen;

import aurelienribon.tweenengine.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.gdxjam.magellan.*;
import com.gdxjam.magellan.drones.Drone;
import com.gdxjam.magellan.gameobj.*;
import com.gdxjam.magellan.ships.AiShip;
import com.gdxjam.magellan.ships.PlayerShip;
import com.gdxjam.magellan.shopitem.ScreenShake;
import com.gdxjam.magellan.tweening.ActorAccessor;
import com.gdxjam.magellan.tweening.SpriteAccessor;

/**
 * Created by lolcorner on 20.12.2015.
 */
public class WindowScreen extends BaseScreen {
    private final HorizontalGroup dronesOnScreen;
    private final Array<Container<Actor>> shipsOnScreen;
    private final Container<Actor> planetOnScreen;
    private final Container<Actor> resourcesOnScreen;
    private final Container<Actor> playerOnScreen;
    private final Container<Actor> shopOnScreen;
    private Sector lastShownSector;
    private Array<ParticleEffect> effects;

    public WindowScreen(MagellanGame game) {
        super(game);

        createStarfield();


        dronesOnScreen = new HorizontalGroup();
        shipsOnScreen = new Array<Container<Actor>>();
        shipsOnScreen.add(new Container<Actor>());
        shipsOnScreen.add(new Container<Actor>());
        shipsOnScreen.add(new Container<Actor>());
        playerOnScreen = new Container<Actor>();
        planetOnScreen = new Container<Actor>();
        shopOnScreen = new Container<Actor>();
        resourcesOnScreen = new Container<Actor>();
        dronesOnScreen.setPosition(500, 200);

        shipsOnScreen.get(0).setPosition(600,200);
        shipsOnScreen.get(1).setPosition(740,100);
        shipsOnScreen.get(2).setPosition(800,400);

        shipsOnScreen.get(0).setSize(assetToGameSize(959),assetToGameSize(649));
        shipsOnScreen.get(1).setSize(assetToGameSize(959),assetToGameSize(649));
        shipsOnScreen.get(2).setSize(assetToGameSize(959),assetToGameSize(649));

        playerOnScreen.setPosition(-600, -110);
        playerOnScreen.setSize(assetToGameSize(2523), assetToGameSize(2064));
        planetOnScreen.setPosition(1280 - assetToGameSize(1386), 720 - assetToGameSize(1677));
        planetOnScreen.setSize(assetToGameSize(1386), assetToGameSize(1677));
        shopOnScreen.setPosition(530, 360);
        shopOnScreen.setSize(assetToGameSize(717), assetToGameSize(790));
        resourcesOnScreen.setPosition(1280 - assetToGameSize(2341), 0);
        resourcesOnScreen.setSize(assetToGameSize(2341), assetToGameSize(1318));

        sectorContainer.addActor(planetOnScreen);
        sectorContainer.addActor(resourcesOnScreen);
        sectorContainer.addActor(dronesOnScreen);
        sectorContainer.addActor(shopOnScreen);
        sectorContainer.addActor(shipsOnScreen.get(0));
        sectorContainer.addActor(shipsOnScreen.get(1));
        sectorContainer.addActor(shipsOnScreen.get(2));
        sectorContainer.addActor(playerOnScreen);

        btnWindow.remove();
    }

    public void show(){
        super.show();
        closeWindow();
        effects = new Array<ParticleEffect>();
        drawSurroundings();
        playerOnScreen.setPosition(-500, -110);
        shipsOnScreen.get(0).setPosition(700,150);
        shipsOnScreen.get(1).setPosition(840,50);
        shipsOnScreen.get(2).setPosition(900,350);
        tweenManager.killAll();
        if (lastShownSector != MagellanGame.instance.universe.playerShip.sector) {
            Tween.to(playerOnScreen, ActorAccessor.POSITION_XY, 0.8f).target(-300, -100).ease(TweenEquations.easeOutCubic).start(tweenManager);
            Tween.to(shipsOnScreen.get(0), ActorAccessor.POSITION_XY, 0.8f).target(600,200).ease(TweenEquations.easeOutCubic).start(tweenManager);
            Tween.to(shipsOnScreen.get(1), ActorAccessor.POSITION_XY, 0.8f).target(740,100).ease(TweenEquations.easeOutCubic).start(tweenManager);
            Tween.to(shipsOnScreen.get(2), ActorAccessor.POSITION_XY, 0.8f).target(800,400).ease(TweenEquations.easeOutCubic).start(tweenManager);
        } else {
            playerOnScreen.setPosition(-300, -100);
            shipsOnScreen.get(0).setPosition(600,200);
            shipsOnScreen.get(1).setPosition(740,100);
            shipsOnScreen.get(2).setPosition(800,400);
        }
        Tween.to(playerOnScreen, ActorAccessor.POSITION_Y,5f).target(-80).ease(TweenEquations.easeInOutCubic).repeatYoyo(-1,0f).delay(1f).start(tweenManager);
        Tween.to(playerOnScreen, ActorAccessor.POSITION_X,7f).target(-290).ease(TweenEquations.easeInOutCubic).repeatYoyo(-1,0f).delay(1f).start(tweenManager);

        Tween.to(shipsOnScreen.get(0), ActorAccessor.POSITION_Y,MathUtils.random(.4f,.6f)).target(215).ease(TweenEquations.easeInOutCubic).repeatYoyo(-1,0f).delay(MathUtils.random(0.8f,1.3f)).start(tweenManager);
        Tween.to(shipsOnScreen.get(1), ActorAccessor.POSITION_Y,MathUtils.random(.4f,.6f)).target(115).ease(TweenEquations.easeInOutCubic).repeatYoyo(-1,0f).delay(MathUtils.random(0.8f,1.3f)).start(tweenManager);
        Tween.to(shipsOnScreen.get(2), ActorAccessor.POSITION_Y,MathUtils.random(.4f,.6f)).target(415).ease(TweenEquations.easeInOutCubic).repeatYoyo(-1,0f).delay(MathUtils.random(0.8f,1.3f)).start(tweenManager);

        lastShownSector = game.universe.playerShip.sector;

    }


    public void drawSurroundings(){
        dronesOnScreen.clear();
        resourcesOnScreen.clear();
        playerOnScreen.clear();
        planetOnScreen.clear();
        shopOnScreen.clear();
        shipsOnScreen.get(0).clear();
        shipsOnScreen.get(1).clear();
        shipsOnScreen.get(2).clear();

        int i = 0;
        for(final GameObj gameObj : game.universe.playerShip.sector.gameObjs){
            if(gameObj instanceof IDrawableWindow){
                Actor actor = ((IDrawableWindow) gameObj).getActor();
                if(gameObj instanceof Planet){
                    planetOnScreen.setActor(actor);
                }
                if(gameObj instanceof Drone) {
                    dronesOnScreen.setWidth(actor.getWidth());
                    dronesOnScreen.addActor(actor);
                }
                if(gameObj instanceof Shop) {
                    shopOnScreen.setActor(actor);
                }
                if(gameObj instanceof PlayerShip) {
                    playerOnScreen.setActor(actor);
                }
                if(gameObj instanceof AiShip) {
                    if(i < 3) {
                        shipsOnScreen.get(i).setSize(actor.getWidth(), actor.getHeight());
                        shipsOnScreen.get(i).setActor(actor);
                        i++;
                    }
                }
                if(gameObj instanceof MeteoroidField) {
                    resourcesOnScreen.setActor(actor);
                }

                if(gameObj instanceof IDrawableWindow) {
                    actor.addListener(new InputListener() {
                        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                            for(GameObj _gameObj : game.universe.playerShip.sector.gameObjs){
                                if(!(gameObj instanceof AiShip) && _gameObj instanceof AiShip){
                                    Window window = getWindow("Alert!", "Beware! Enemy ships in sector!");
                                    return false;
                                }
                            }
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

    public void shake(int i){
        ScreenShake.shakeScreen(30,stage.getCamera().position.cpy(), 10 * i);
    }

    public void showInteractionWindow(final IDrawableWindow gameObj){

        Window window = getWindow(gameObj.getTitle());

        VerticalGroup windowContent = new VerticalGroup();
        windowContent.fill();
        Label info = new Label(gameObj.getInfo(), skin, "window");
        VerticalGroup menu = new VerticalGroup();
        menu.padTop(20);
        menu.space(6);
        menu.fill();
        final WindowScreen screen = this;
        if(gameObj instanceof IInteractable) {
            final IInteractable interactable = (IInteractable) gameObj;
            for (final String key : interactable.getInteractions(game.universe.playerShip).keys()) {
                TextButton button = new TextButton(key, skin, "yellow");
                button.addListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        interactable.getInteractions(game.universe.playerShip).get(key).interact();
                        drawSurroundings();
                    }
                });
                menu.addActor(button);
            }
        }
        if(gameObj instanceof IDestroyable && gameObj != game.universe.playerShip && ((GameObj)gameObj).submenuOpen == "") {
            final IDestroyable destroyable = (IDestroyable) gameObj;
            TextButton button = new TextButton("ATTACK", skin, "red");
            button.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    closeWindow();
                    new Battle(game.universe.playerShip, destroyable);
                }
            });
            menu.addActor(button);
        }
        windowContent.addActor(info);
        windowContent.addActor(menu);
        window.add(windowContent).expandX().fill();
    }



    public void render(float delta){
        super.render(delta);
        batch.begin();
        starfield.draw(batch);
        batch.end();
        renderUi(delta);
        batch.begin();
        for(ParticleEffect pe : effects){
            pe.draw(batch, delta);
            if(pe.isComplete()) effects.removeValue(pe, true);
        }
        batch.end();
        ScreenShake.update(stage.getCamera());
    }

    public int assetToGameSize(int size) {
        return size/3;
    }

    public void showDamage(final IDestroyable target, int damage) {
        ParticleEffect pe = new ParticleEffect();
        pe.load(Gdx.files.internal("explosion"),Gdx.files.internal(""));
        final Label l = new Label("-"+damage, skin, "damage");
        if(target instanceof PlayerShip) {
            l.setPosition(200, 400);
            pe.setPosition(200, 200);
            pe.scaleEffect(damage*2);
        } else {
            l.setPosition(700, 500);
            pe.setPosition(700, 500);
            pe.scaleEffect(2);
            for (Container<Actor> c : shipsOnScreen){
                if(c.getActor() != null
                        && c.getActor().getUserObject() != null
                        && c.getActor().getUserObject() == target){
                    l.setPosition(c.getX() + c.getActor().getWidth()/2, c.getY() + c.getActor().getHeight()/2);
                    pe.setPosition(c.getX() + c.getActor().getWidth()/2, c.getY() + c.getActor().getHeight()/2);
                }
            }
        }
        stage.addActor(l);
        Tween.to(l, ActorAccessor.ALPHA, 0.5f).target(0).ease(TweenEquations.easeOutCubic).delay(0.5f).start(tweenManager);
        Tween.to(l, ActorAccessor.POSITION_Y, 1).target(l.getY()+50).ease(TweenEquations.easeInCubic).setCallback(new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                l.remove();
            }
        }).start(tweenManager);
        effects.add(pe);
        pe.start();
    }

    public void showShield(final IDestroyable target) {
        if (target instanceof PlayerShip) {
            ((Stack) playerOnScreen.getActor()).getChildren().get(1).setColor(1,1,1,1);
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    ((Stack) playerOnScreen.getActor()).getChildren().get(1).setColor(1,1,1,0);
                }
            }, 0.5f);
        } else {
            for (final Container<Actor> c : shipsOnScreen){
                if(c.getActor() != null
                        && c.getActor().getUserObject() != null
                        && c.getActor().getUserObject() == target){
                    ((Stack) c.getActor()).getChildren().get(1).setColor(1,1,1,1);
                    Timer.schedule(new Timer.Task() {
                        @Override
                        public void run() {
                            ((Stack) c.getActor()).getChildren().get(1).setColor(1,1,1,0);
                        }
                    }, 0.5f);
                }
            }
        }
    }
}
