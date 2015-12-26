package com.gdxjam.magellan;

/**
 * Created by Felix on 23.12.2015.
 */
public class GameState {

    private MagellanGame game;

    public int DRONES = 0;
    public int RESSOURCE1 = 0;
    public int RESSOURCE2 = 0;
    public int RESSOURCE3 = 0;
    public int CREDITS = 0;
    public int CREDITS_PER_TICK = 0;
    public int YEAR = 3056;
    public int POPULATION = 0;

    public GameState(MagellanGame game) {
        this.game = game;
    }

    public void progressYear() {
        YEAR++;

    }

    public void getPlanetIncome() {
        CREDITS_PER_TICK = 0;
        for (GameObj gameObj:game.universe.getGameObjs(Planet.class)) {
            Planet planet = (Planet)gameObj;
            if (planet.faction == GameObj.Factions.PLAYER) {
                CREDITS_PER_TICK = planet.creditsByTick();
            }
        }
        CREDITS += CREDITS_PER_TICK;
    }
    public void updatePopulationCount() {
        POPULATION = 0;
        for (GameObj gameObj:game.universe.getGameObjs(Planet.class)) {
            Planet planet = (Planet)gameObj;
            if (planet.faction == GameObj.Factions.PLAYER) {
                POPULATION += planet.population;
            }
        }
    }



}
