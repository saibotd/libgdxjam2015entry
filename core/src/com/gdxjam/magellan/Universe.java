package com.gdxjam.magellan;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.gdxjam.magellan.ships.AiShipFighter;
import com.gdxjam.magellan.ships.AiShipSettler;
import com.gdxjam.magellan.ships.PlayerShip;

public class Universe {
    public PlayerShip playerShip;
    public Array<Sector> sectors;
    public int size = 4000;
    private MagellanGame game;
    public Universe(MagellanGame game){
        this.game = game;
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
        playerShip = new PlayerShip(bottomLeft, game);
        for(int i = 0; i < 100; i++){
            new AiShipFighter(topRight, game);
        }
        for(int i = 0; i < 20; i++){
            new AiShipSettler(topRight, game);
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

    public Array<GameObj> getGameObjs(Class objType) {
        Array<GameObj> result = new Array();
        for(Sector sector : sectors){
            for (GameObj gameObj: sector.gameObjs) {
                if (objType.isAssignableFrom(gameObj.getClass())) {
                    result.add(gameObj);
                }
            }
        }
        return result;
    }


    public void tick() {

        game.gameState.progressYear();

        for(int i = 0; i < sectors.size; i++){
            for(int j = 0; j < sectors.get(i).gameObjs.size; j++){
                sectors.get(i).gameObjs.get(j).tick();
            }
        }

        game.gameState.updatePopulationCount();
        game.gameState.getPlanetIncome();

    }

    public void addRandomSector(){
        int x = (int) Math.round(Math.random() * size);
        int y = (int) Math.round(Math.random() * size);
        Sector newSector = new Sector(x,y);
        if(addSector(newSector)) {
            if (Math.random() < .3)
                new MetroidField(newSector, game);
            if(Math.random() < .2)
                new Planet(newSector, game);
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
