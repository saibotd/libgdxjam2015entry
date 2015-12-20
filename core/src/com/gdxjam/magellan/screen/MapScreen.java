package com.gdxjam.magellan.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.environment.SpotLight;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.gdxjam.magellan.GameObj;
import com.gdxjam.magellan.MagellanGame;
import com.gdxjam.magellan.Sector;
import com.gdxjam.magellan.Universe;

/**
 * Created by lolcorner on 19.12.2015.
 */
public class MapScreen  implements Screen, InputProcessor {
    private final MagellanGame game;
    private final FitViewport viewport;
    private final Sprite pixel;
    private final Sprite dot;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private float zoom = 1;
    private float panX;
    private float panY;
    private Array<Array<Texture>> mapTextures = new Array();
    private int pmSize = 1024;
    private Vector3 touch = new Vector3();
    private Circle touchCircle = new Circle(0,0,30);
    private Universe universe;
    private Vector2 tmp1;

    public MapScreen(MagellanGame magellanGame){
        game = magellanGame;
        universe = game.universe;
        camera = new OrthographicCamera();
        batch = new SpriteBatch();
        Pixmap.setFilter(Pixmap.Filter.BiLinear);
        viewport = new FitViewport(1280, 720, camera);
        camera.position.x = universe.playerShip.sector.position.x - camera.viewportWidth/2;
        camera.position.y = universe.playerShip.sector.position.y - camera.viewportHeight/2;
        viewport.apply();
        pixel = new Sprite(MagellanGame.assets.get("pixel.png", Texture.class));
        dot = new Sprite(MagellanGame.assets.get("dot.png", Texture.class));
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(this);
    }

    public void update() {
        if(universe.updated) {
            universe.updated = false;
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.zoom = zoom;
        camera.position.x += panX;
        camera.position.y += panY;
        camera.update();

        batch.setProjectionMatrix(camera.combined);

        batch.begin();

        for(Sector sector : universe.sectors){
            //if(!sector.discovered) continue;
            for(Sector _sector : sector.connectedSectors){
                tmp1 = sector.position.cpy().sub(_sector.position);
                if(sector == universe.playerShip.sector)
                    pixel.setColor(Color.WHITE);
                else
                    pixel.setColor(Color.CYAN);
                pixel.setSize(tmp1.len(), 1);
                pixel.setOrigin(0,.5f);
                pixel.setPosition(_sector.position.x-.5f, _sector.position.y-.5f);
                pixel.setRotation(tmp1.angle());
                pixel.draw(batch);
                dot.setColor(Color.CYAN);
                dot.setSize(10,10);
                dot.setPosition(_sector.position.x - 5, _sector.position.y - 5);
                dot.draw(batch);
            }
        }
        for(Sector sector : universe.sectors){
            //if(!sector.discovered) continue;
            dot.setColor(Color.CYAN);
            dot.setSize(10,10);
            dot.setPosition(sector.position.x - 5, sector.position.y - 5);
            dot.draw(batch);
            for(GameObj gameObj:sector.gameObjs){
                dot.setColor(gameObj.colorOnMap);
                dot.setSize(gameObj.sizeOnMap,gameObj.sizeOnMap);
                dot.setPosition(sector.position.x - gameObj.sizeOnMap/2, sector.position.y - gameObj.sizeOnMap/2);
                dot.draw(batch);
            }
        }
        batch.end();

        update();
    }

    public void resize(int w, int h) {
        viewport.update(w,h);
    }

    public void pause() {

    }

    public void resume() {

    }

    @Override
    public void hide() {

    }

    public void dispose() {
        batch.dispose();
    }

    public boolean keyDown(int keyCode) {
        switch (keyCode){
            case Input.Keys.W:
                panY = 10*camera.zoom;
                break;
            case Input.Keys.A:
                panX = -10*camera.zoom;
                break;
            case Input.Keys.S:
                panY = -10*camera.zoom;
                break;
            case Input.Keys.D:
                panX = 10*camera.zoom;
                break;
        }
        return false;
    }

    public boolean keyUp(int keyCode) {

        switch (keyCode){
            case Input.Keys.W:
                panY = 0;
                break;
            case Input.Keys.A:
                panX = 0;
                break;
            case Input.Keys.S:
                panY = 0;
                break;
            case Input.Keys.D:
                panX = 0;
                break;
        }
        return false;
    }

    public boolean keyTyped(char c) {
        return false;
    }

    public boolean touchDown(int x, int y, int pointer, int button) {
        camera.unproject(touch.set(x, y, zoom));
        touchCircle.setPosition(touch.x, touch.y);
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
        panX = panY = 0;
        return false;
    }

    public boolean touchDragged(int x, int y, int i) {
        camera.unproject(touch.set(x, y, zoom));
        panX += touchCircle.x - touch.x;
        panY += touchCircle.y - touch.y;
        touchCircle.x = touch.x;
        touchCircle.y = touch.y;
        return false;
    }

    public boolean mouseMoved(int x, int y) {
        return false;
    }

    public boolean scrolled(int i) {
        zoom += i*.5;
        if(zoom < 1) zoom = 1;
        return false;
    }

}
