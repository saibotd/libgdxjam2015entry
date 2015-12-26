package com.gdxjam.magellan.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gdxjam.magellan.*;

/**
 * Created by lolcorner on 19.12.2015.
 */
public class MapScreen extends BaseScreen {
    private final Sprite pixel;
    private final Sprite dot;
    private final Viewport mapViewport;
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

    public MapScreen(MagellanGame game){
        super(game);
        universe = game.universe;
        mapBatch = new SpriteBatch();
        pixel = new Sprite(MagellanGame.assets.get("pixel.png", Texture.class));
        dot = new Sprite(MagellanGame.assets.get("dot.png", Texture.class));
        camera = new OrthographicCamera();
        mapViewport = new FitViewport(1280, 720, camera);
        camera.position.x = universe.playerShip.sector.position.x;
        camera.position.y = universe.playerShip.sector.position.y;
        for(Sector sector : universe.sectors){
            for(GameObj gameObj:sector.gameObjs){
                if(gameObj instanceof IDrawableMap) {
                    ((IDrawableMap) gameObj).prepareRenderingOnMap();
                }
            }
        }
    }

    @Override
    public void render(float delta) {
        super.render(delta);



        mapViewport.apply();

        camera.update();
        mapBatch.setProjectionMatrix(camera.combined);

        camera.zoom = zoom;
        camera.position.x += keyboardPanX;
        camera.position.y += keyboardPanY;

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
                    pixel.setColor(MagellanColors.YELLOW);
                    pixel.setSize(tmp1.len()+1f, 2);
                    pixel.setOrigin(0,1f);
                } else {
                    pixel.setColor(Color.WHITE);
                    pixel.setSize(tmp1.len()+1f, 0.6f);
                    pixel.setOrigin(0,0.3f);
                }


                pixel.setPosition(_sector.position.x-1f, _sector.position.y-1f);
                pixel.setRotation(tmp1.angle());
                pixel.draw(mapBatch);

                dot.setColor(Color.CYAN);
                dot.setSize(20,20);
                dot.setPosition(_sector.position.x - 10, _sector.position.y - 10);
                dot.draw(mapBatch);
            }
        }
        for(Sector sector : universe.getSectorsInRectangle(cameraFrame)){
            if(!sector.discovered && !MagellanGame.DEBUG) continue;
            if (sector.visited) {
                dot.setColor(Color.CYAN);
            } else {
                dot.setColor(Color.LIGHT_GRAY);
            }
            dot.setSize(20,20);
            dot.setPosition(sector.position.x - 10, sector.position.y - 10);
            dot.draw(mapBatch);
            if (sector.visited) {
                for (GameObj gameObj : sector.gameObjs) {
                    if (gameObj instanceof IDrawableMap) {
                        ((IDrawableMap) gameObj).renderOnMap(mapBatch, delta);
                    }
                }
            }
        }
        mapBatch.end();

        viewport.apply();

        stage.draw();

        game.ui.renderOverlays(delta);
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
            case Input.Keys.F:
                if(Gdx.graphics.isFullscreen())
                    Gdx.graphics.setDisplayMode(1280, 720, false);
                else
                    Gdx.graphics.setDisplayMode(Gdx.graphics.getDesktopDisplayMode().width, Gdx.graphics.getDesktopDisplayMode().height, true);
                break;
        }
        return false;
    }

    public boolean touchDown(int x, int y, int pointer, int button) {
        camera.unproject(touch.set(x, y, zoom));
        touchCircle.setPosition(touch.x, touch.y);
        doMousePan = true;
        dragStartMousePos.set((float)x, (float)y);
        dragStartCameraPos.set(camera.position.x, camera.position.y);
        mousePos.set((float)x, (float)y);
        if(universe.getSectorsInCircle(touchCircle).size > 0){
            Sector sector = universe.getSectorsInCircle(touchCircle).get(0);
            if(universe.playerShip.sector.connectedSectors.contains(sector, true)){
                universe.playerShip.moveTo(sector);
                universe.tick();
            }
        }
        return false;
    }

    public boolean touchUp(int x, int y, int pointer, int button) {
        doMousePan = false;
        dragStartMousePos.set(mousePos);
        return false;
    }

    public boolean touchDragged(int x, int y, int i) {
        mousePos.set((float)x, (float)y);
        return true;
    }

    public boolean scrolled(int i) {
        zoom = MathUtils.clamp(zoom + i*.3f, .5f, 2f);
        return false;
    }

}
