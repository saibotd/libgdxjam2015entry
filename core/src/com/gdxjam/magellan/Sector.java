package com.gdxjam.magellan;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.gdxjam.magellan.gameobj.GameObj;

public class Sector {
    public Vector2 position;
    public Array<Sector> connectedSectors;
    public Circle circleConnect;
    public Circle circleAlone;
    public Array<GameObj> gameObjs = new Array();
    public boolean discovered = false;
    public boolean visited = false;
    public boolean hasPlanet = false;

    public Sector(int x, int y){
        position = new Vector2(x,y);
        circleConnect = new Circle(position, 200);
        circleAlone = new Circle(position, 64);
        connectedSectors = new Array();
    }

    public void addConnection(Sector sector) {
        if(sector == this) return;
        if(connectedSectors.contains(sector, true)) return;
        connectedSectors.add(sector);
        sector.addSingleConnection(this);
    }

    private void addSingleConnection(Sector sector) {
        if(sector == this) return;
        if(connectedSectors.contains(sector, true)) return;
        connectedSectors.add(sector);
    }

    public void addConnections(Array<Sector> sectors) {
        for(Sector sector: sectors){
            addConnection(sector);
        }
    }
}

