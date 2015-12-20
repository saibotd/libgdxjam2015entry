package com.gdxjam.magellan;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class Universe {
    public Array<Sector> sectors;
    public int size = 8192;
    public boolean updated = false;
    public Universe(){
        sectors = new Array();
        for(int i = 0; i < size/2; i++){
            addRandomSector();
        }
        connectSectors();
    }

    public boolean addSector(Sector sector) {
        for(Sector _sector : sectors){
            if(sector.circleAlone.contains(_sector.position)){
                return false;
            }
        }
        sectors.add(sector);
        return true;
    }

    public Array<Sector> getSectorsInCircle(Circle circle) {
        Array<Sector> result = new Array();
        for(Sector sector : sectors){
            if(circle.contains(sector.position)){
                result.add(sector);
            }
        }
        return result;
    }

    public Array<Sector> getSectorsInRectangle(Rectangle rectangle) {
        Array<Sector> result = new Array();
        for(Sector sector : sectors){
            if(rectangle.contains(sector.position)){
                result.add(sector);
            }
        }
        return result;
    }

    public void tick() {
        for(Sector sector : sectors){
            for(GameObj gameObj : sector.gameObjs){
                gameObj.tick();
            }
        }
    }

    public void addRandomSector(){
        int x = (int) Math.round(Math.random() * size);
        int y = (int) Math.round(Math.random() * size);
        Sector newSector = new Sector(x,y);
        if(addSector(newSector)) {
            if (Math.random() < .1) {
                new Planet(newSector);
            }
        }
    }

    public void connectSectors(){
        for(int i = 0; i < sectors.size; i++){
            Sector sector = sectors.get(i);
            if(sector.connectedSectors.size < 2){
                sector.addConnections(getSectorsInCircle(sector.circleConnect));
            }
        }
        for(int i = 0; i < sectors.size; i++){
            Sector sector = sectors.get(i);
            if(sector.connectedSectors.size == 0){
                sectors.removeValue(sector, true);
            }
        }

    }

}
