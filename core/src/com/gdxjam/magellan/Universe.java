package com.gdxjam.magellan;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class Universe {
    public PlayerShip playerShip;
    public Array<Sector> sectors;
    public int size = 4000;
    public Universe(){
        sectors = new Array();
        for(int i = 0; i < size/2; i++){
            addRandomSector();
        }
        connectSectors();
        Sector bottomLeft = sectors.random();
        Sector topRight = sectors.random();
        for(Sector sector : sectors){
            if(sector.position.x < bottomLeft.position.x && sector.position.y < bottomLeft.position.y){
                bottomLeft = sector;
            }
            if(sector.position.x > topRight.position.x && sector.position.y > topRight.position.y){
                topRight = sector;
            }
        }
        playerShip = new PlayerShip(bottomLeft);
        for(int i = 0; i < 100; i++){
            new AiShipFighter(topRight);
        }
        for(int i = 0; i < 20; i++){
            new AiShipSettler(topRight);
        }
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
        for(int i = 0; i < sectors.size; i++){
            for(int j = 0; j < sectors.get(i).gameObjs.size; j++){
                sectors.get(i).gameObjs.get(j).tick();
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
