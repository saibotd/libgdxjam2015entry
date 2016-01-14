package com.gdxjam.magellan;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;

/**
 * Created by saibotd on 14.01.16.
 */
public class Log {
    private Group target;
    private Array<LogEntry> log;

    public Log(Group _target){
        target = _target;
        log = new Array<LogEntry>();
    }

    public void addEntry(String s){
        log.add(new LogEntry(s));
        update();
    }

    public void addEntry(String s, Sector sector){
        log.add(new LogEntry(s, sector));
        update();
    }

    public void update(){
        target.clear();
        for(final LogEntry entry:log){
            Label l = new Label(entry.text, MagellanGame.instance.mapScreen.skin);
            if(entry.sector != null){
                l.addListener(new InputListener() {
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        return true;
                    }
                    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                        if(!entry.sector.discovered) return;
                        MagellanGame.instance.mapScreen.focusOnSector(entry.sector);
                    }
                });
            }
            target.addActor(l);
        }
    }

    public void render(float delta){

    }

    private class LogEntry{
        public String text;
        public Sector sector;

        public LogEntry(String s){
            text = MagellanGame.gameState.YEAR + " " + s;
        }

        public LogEntry(String s, Sector _sector){
            text = s;
            sector = _sector;
        }
    }
}
