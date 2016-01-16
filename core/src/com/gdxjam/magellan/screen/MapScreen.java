package com.gdxjam.magellan.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gdxjam.magellan.*;
import com.gdxjam.magellan.gameobj.GameObj;
import com.gdxjam.magellan.gameobj.IDrawableMap;
import com.gdxjam.magellan.ships.AiShipFighter;
import com.gdxjam.magellan.ships.AiShipSettler;
import com.gdxjam.magellan.ships.Ship;

/**
 * Created by lolcorner on 19.12.2015.
 */
public class MapScreen extends BaseScreen {
    private final Sprite pixel;
    private final Sprite dot;
    private final Sprite sectorNormal;
    private final Sprite sectorNotVisited;
    private final Viewport mapViewport;
    public Log log;
    private float zoom = 1;
    private Vector3 touch = new Vector3();
    private Circle touchCircle = new Circle(0,0,30);
    private boolean doMousePan = false;
    private Vector2 dragStartMousePos = new Vector2();
    private Vector2 dragStartCameraPos = new Vector2();
    private Vector2 mousePos = new Vector2();
    private float keyboardPanX;
    private float keyboardPanY;
    private Universe universe;
    private Vector2 tmp1;
    public OrthographicCamera camera;
    public SpriteBatch mapBatch;
    private Rectangle cameraFrame = new Rectangle();
    private float cameraFramePadding = 200;
    private Vector2 starfieldScroll = new Vector2();
    float lineWidth = 2;
    private Sector sectorToFocusOn;

    public MapScreen(MagellanGame game){
        super(game);
        universe = game.universe;
        mapBatch = new SpriteBatch();
        pixel = new Sprite(MagellanGame.assets.get("pixel.png", Texture.class));
        dot = new Sprite(MagellanGame.assets.get("dot.png", Texture.class));
        sectorNormal = new Sprite(MagellanGame.assets.get("map_sector.png", Texture.class));
        sectorNotVisited = new Sprite(MagellanGame.assets.get("map_sector_notvisited.png", Texture.class));
        sectorNormal.setSize(10,10);
        //sectorNormal.setColor(Color.BLACK);
        sectorNotVisited.setSize(20,20);
        camera = new OrthographicCamera();
        mapViewport = new FitViewport(1280, 720, camera);
        camera.position.x = universe.playerShip.sector.position.x;
        camera.position.y = universe.playerShip.sector.position.y;
        HorizontalGroup logGroup = new HorizontalGroup();
        logGroup.setSize(400, 200);
        logGroup.setPosition(1280-410, 5);
        VerticalGroup logTarget = new VerticalGroup();
        ScrollPane logScroll = new ScrollPane(logTarget, skin, "log");
        logGroup.addActor(logScroll);
        logScroll.setFillParent(true);
        logTarget.fill();
        logTarget.space(5);
        stage.addActor(logGroup);
        log = new Log(logTarget);
        for(Sector sector : universe.sectors){
            for(GameObj gameObj:sector.gameObjs){
                if(gameObj instanceof IDrawableMap) {
                    ((IDrawableMap) gameObj).prepareRenderingOnMap();
                }
            }
        }
        createStarfield();
        starfield.getTexture().setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);

        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                MagellanGame.instance.mapScreen.log.addEntry("");
                MagellanGame.instance.mapScreen.log.addEntry("");
                MagellanGame.instance.mapScreen.log.addEntry("");
                MagellanGame.instance.mapScreen.log.addEntry("");
                MagellanGame.instance.mapScreen.log.addEntry("");
                MagellanGame.instance.mapScreen.log.addEntry("");
                MagellanGame.instance.mapScreen.log.addEntry("");
                MagellanGame.instance.mapScreen.log.addEntry("");
                MagellanGame.instance.mapScreen.log.addEntry("");
                MagellanGame.instance.mapScreen.log.addEntry("");
            }
        }, 1);
        btnMap.remove();
    }


    @Override
    public void render(float delta) {
        super.render(delta);

        batch.begin();

        starfieldScroll.x = camera.position.x / 800;
        starfieldScroll.y = -camera.position.y / 450;
        starfield.setU(starfieldScroll.x);
        starfield.setU2(starfieldScroll.x+1);
        starfield.setV(starfieldScroll.y);
        starfield.setV2(starfieldScroll.y+1);
        starfield.draw(batch);

        batch.end();

        mapViewport.apply();

        camera.update();
        mapBatch.setProjectionMatrix(camera.combined);

        camera.zoom = zoom;
        camera.position.x += keyboardPanX;
        camera.position.y += keyboardPanY;

        if(sectorToFocusOn != null){
            camera.position.lerp(new Vector3(sectorToFocusOn.position.x, sectorToFocusOn.position.y, 0), delta * 4);
            zoom = MathUtils.clamp(zoom-delta * 2, .5f, 5);
            if(sectorToFocusOn.position.dst(camera.position.x, camera.position.y) < .2 && zoom <= 0.5f) sectorToFocusOn = null;
        }

        cameraFrame.set(
                camera.position.x - camera.viewportWidth * zoom / 2 - cameraFramePadding,
                camera.position.y - camera.viewportHeight * zoom / 2 - cameraFramePadding,
                camera.viewportWidth * zoom + (cameraFramePadding*2),
                camera.viewportHeight * zoom + (cameraFramePadding*2)
        );

        if (doMousePan) {
            camera.position.set(dragStartCameraPos.x + (dragStartMousePos.x - mousePos.x) * zoom * viewport.getWorldWidth() / viewport.getScreenWidth(), dragStartCameraPos.y + (mousePos.y - dragStartMousePos.y) * zoom * viewport.getWorldHeight() / viewport.getScreenHeight(), 1);
        }


        mapBatch.begin();

        for(Sector sector : universe.getSectorsInRectangle(cameraFrame)){
            if(!sector.visited && !MagellanGame.DEBUG) continue;
            for(Sector _sector : sector.connectedSectors){

                tmp1 = sector.position.cpy().sub(_sector.position);
                if(sector == universe.playerShip.sector || _sector == universe.playerShip.sector) {
                    pixel.setColor(MagellanColors.MAP_POSSIBLE_MOVEMENT);
                    pixel.setAlpha(1f);
                    pixel.setSize(tmp1.len()+lineWidth/2, lineWidth);
                    pixel.setOrigin(0,lineWidth/2);
                } else {
                    pixel.setColor(Color.WHITE);
                    pixel.setAlpha(0.2f);
                    pixel.setSize(tmp1.len()+lineWidth/2, lineWidth);
                    pixel.setOrigin(0,lineWidth/2);
                }

                pixel.setPosition(_sector.position.x - pixel.getOriginX(), _sector.position.y - pixel.getOriginY());
                pixel.setRotation(tmp1.angle());
                pixel.draw(mapBatch);
            }
        }
        for(Sector sector : universe.getSectorsInRectangle(cameraFrame)){
            if(!sector.discovered && !MagellanGame.DEBUG) continue;
            if (sector.visited) {
                sectorNormal.setPosition(sector.position.x - sectorNormal.getWidth()/2, sector.position.y - sectorNormal.getHeight()/2);
                sectorNormal.draw(mapBatch);
            } else {
                sectorNotVisited.setPosition(sector.position.x - sectorNotVisited.getWidth()/2, sector.position.y - sectorNotVisited.getHeight()/2);
                sectorNotVisited.draw(mapBatch);
            }


            // Fog of war disabled for debugging
            if (sector.visited || MagellanGame.DEBUG) {
                for (GameObj gameObj : sector.gameObjs) {
                    if (gameObj instanceof IDrawableMap) {
                        ((IDrawableMap) gameObj).renderOnMap(mapBatch, delta);
                    }
                }
            }
        }
        mapBatch.end();

        viewport.apply();


        renderUi(delta);
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        mapViewport.update(width, height);
    }

    public boolean keyDown(int keyCode) {
        switch (keyCode){
            case Input.Keys.W:
                keyboardPanY = 10*camera.zoom;
                break;
            case Input.Keys.A:
                keyboardPanX = -10*camera.zoom;
                break;
            case Input.Keys.S:
                keyboardPanY = -10*camera.zoom;
                break;
            case Input.Keys.D:
                keyboardPanX = 10*camera.zoom;
                break;
            case Input.Keys.Z:
                MagellanGame.DEBUG = !MagellanGame.DEBUG;
                break;
        }
        return false;
    }

    public boolean keyUp(int keyCode) {
        super.keyUp(keyCode);
        switch (keyCode){
            case Input.Keys.W:
                keyboardPanY = 0;
                break;
            case Input.Keys.A:
                keyboardPanX = 0;
                break;
            case Input.Keys.S:
                keyboardPanY = 0;
                break;
            case Input.Keys.D:
                keyboardPanX = 0;
                break;
            case Input.Keys.K:
                Ship ship = new AiShipFighter(universe.playerShip.sector);
                ship.prepareRenderingOnMap();
                break;
            case Input.Keys.J:
                Ship ship2 = new AiShipSettler(universe.playerShip.sector);
                ship2.prepareRenderingOnMap();
                break;
            case Input.Keys.L:
                MagellanGame.gameState.CREDITS += 100000;
                MagellanGame.gameState.RESOURCE1 += 1000;
                MagellanGame.gameState.RESOURCE2 += 1000;
                MagellanGame.gameState.RESOURCE3 += 1000;
                universe.playerShip.drones.add(1);
                universe.playerShip.drones.add(2);
                universe.playerShip.drones.add(3);
                universe.playerShip.drones.add(4);
                universe.playerShip.drones.add(5);
                MagellanGame.instance.mapScreen.topbar.updateStats();
                break;
        }
        return false;
    }

    public boolean touchDown(int x, int y, int pointer, int button) {
        sectorToFocusOn = null;
        camera.unproject(touch.set(x, y, zoom));
        touchCircle.setPosition(touch.x, touch.y);
        doMousePan = true;
        dragStartMousePos.set((float)x, (float)y);
        dragStartCameraPos.set(camera.position.x, camera.position.y);
        mousePos.set((float)x, (float)y);
        return false;
    }

    public boolean touchUp(int x, int y, int pointer, int button) {
        doMousePan = false;
        if(dragStartMousePos.dst(mousePos) < 10) {
            if (universe.getSectorsInCircle(touchCircle).size > 0) {
                Sector sector = universe.getSectorsInCircle(touchCircle).get(0);
                if (universe.playerShip.sector.connectedSectors.contains(sector, true)) {
                    universe.playerShip.moveTo(sector);
                    universe.tick();
                }
            }
        }
        dragStartMousePos.set(mousePos);
        return false;
    }

    public boolean touchDragged(int x, int y, int i) {
        mousePos.set((float)x, (float)y);
        return true;
    }

    public boolean scrolled(int i) {
        sectorToFocusOn = null;
        zoom = MathUtils.clamp(zoom + i*.3f, .5f, 2f);
        return false;
    }

    public void focusOnSector(Sector sector) {
        sectorToFocusOn = sector;
    }
}
