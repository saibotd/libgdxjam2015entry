package com.gdxjam.magellan;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.gdxjam.magellan.gameobj.*;
import com.gdxjam.magellan.ships.AiShipFighter;
import com.gdxjam.magellan.ships.AiShipSettler;
import com.gdxjam.magellan.ships.AiShipSmallFighter;
import com.gdxjam.magellan.ships.PlayerShip;

public class Universe {
    private final Planet enemyPlanet;
    public PlayerShip playerShip;
    public Array<Sector> sectors;
    public int size = 3000;
    private MagellanGame game;
    public Sector bottomLeft;
    public Sector topRight;
    public Universe(MagellanGame game){
        this.game = game;
        sectors = new Array();
        for(int i = 0; i < size/2; i++){
            addRandomSector();
        }
        connectSectors();
        bottomLeft = sectors.random();
        topRight = sectors.random();
        for(Sector sector : sectors){
            if(sector.position.x < bottomLeft.position.x && sector.position.y < bottomLeft.position.y){
                bottomLeft = sector;
            }
            if(sector.position.x > topRight.position.x && sector.position.y > topRight.position.y){
                topRight = sector;
            }
        }
        bottomLeft.gameObjs.clear();
        playerShip = new PlayerShip(bottomLeft);
        new Shop(bottomLeft);
        topRight.gameObjs.clear();
        enemyPlanet = new EnemyHomePlanet(topRight);
        enemyPlanet.faction = GameObj.Factions.SAATOO;
        enemyPlanet.addResources(1,550);
        enemyPlanet.addResources(2,550);
        enemyPlanet.addResources(3,550);
        enemyPlanet.population = 2000;

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
                if (objType == gameObj.getClass()) {
                    result.add(gameObj);
                }
            }
        }
        return result;
    }


    public void tick() {

        MagellanGame.gameState.progressYear();

        Array<GameObj> gameObjs = new Array<GameObj>();

        for(int i = 0; i < sectors.size; i++){
            for(int j = 0; j < sectors.get(i).gameObjs.size; j++){
                gameObjs.add(sectors.get(i).gameObjs.get(j));
            }
        }

        for(int i = 0; i < gameObjs.size; i++){
            gameObjs.get(i).passiveTick();
        }

        for(int i = 0; i < gameObjs.size; i++){
            gameObjs.get(i).activeTick();
        }

        MagellanGame.gameState.updatePopulationCount();
        MagellanGame.gameState.getPlanetIncome();
        MagellanGame.gameState.updateNumberOfDrones();




        if(enemyPlanet.faction == GameObj.Factions.SAATOO) {

            if (MagellanGame.gameState.YEARS_PASSED == 1) {

                for (int i = 0; i < 3; i++) {
                    AiShipFighter staticFighter = new AiShipFighter(topRight);
                    staticFighter.notMoving = true;
                    staticFighter.prepareRenderingOnMap();
                }
                addEnemies(20, 5, 20);
            }

            addEnemies(MathUtils.random(-5,1), MathUtils.random(-10,1), MathUtils.random(-15,1));
        }

        if(!MagellanGame.instance.universe.playerShip.isAlive()){
            MagellanGame.instance.showTitleScreen();
            MagellanGame.instance.restartGame();
        }

    }

    public void addRandomSector(){
        int x = (int) Math.round(Math.random() * size);
        int y = (int) Math.round(Math.random() * size);
        Sector newSector = new Sector(x,y);
        if(addSector(newSector)) {
            if(Math.random() < .2) {
                new Planet(newSector);
                newSector.hasPlanet = true;
            }
            if (Math.random() < .3) {
                new MeteoroidField(newSector);
            }
            if (Math.random() < .05) {
                new Shop(newSector);
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


    public void addEnemies(int numberFighters, int numberSettlers, int numberSmallFighters) {
        for(int i = 0; i < numberFighters; i++){
            final AiShipFighter fighter = new AiShipFighter(topRight);
            fighter.prepareRenderingOnMap();
        }
        for(int i = 0; i < numberSettlers; i++){
            final AiShipSettler settler = new AiShipSettler(topRight);
            settler.prepareRenderingOnMap();
        }
        Array<Sector> undiscoveredSectors = new Array<Sector>();
        for (Sector sector:sectors) {
            if (!sector.discovered && sector.position.x > 200 && sector.position.y > 200) {
                undiscoveredSectors.add(sector);
            }
        }
        if (undiscoveredSectors.size > 0) {
            for (int i = 0; i < numberSmallFighters; i++) {
                final AiShipSmallFighter smallFighter = new AiShipSmallFighter(undiscoveredSectors.random());
                smallFighter.prepareRenderingOnMap();
            }
        }
    }


}
