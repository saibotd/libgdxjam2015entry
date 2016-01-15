package com.gdxjam.magellan;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.gdxjam.magellan.drones.DroneRoutine;
import com.gdxjam.magellan.gameobj.GameObj;
import com.gdxjam.magellan.gameobj.Planet;

/**
 * Created by Felix on 23.12.2015.
 */
public class GameState {

    private MagellanGame game;

    public int DRONES = 0;
    public int RESOURCE1 = 0;
    public int RESOURCE2 = 0;
    public int RESOURCE3 = 0;
    public int CREDITS = 0;
    public int CREDITS_PER_TICK = 0;
    public int YEAR = 3056;
    public int YEARS_PASSED = 0;
    public int POPULATION = 0;
    public Array<DroneRoutine.ROUTINES> UNLOCKED_ROUTINES = new Array<DroneRoutine.ROUTINES>();

    public GameState(MagellanGame game) {
        this.game = game;
    }

    public void progressYear() {
        YEAR++;
        YEARS_PASSED++;
        updateNumberOfDrones();

    }

    public void updateNumberOfDrones(){
        DRONES = game.universe.playerShip.drones.size;
    }

    public void getPlanetIncome() {
        CREDITS_PER_TICK = 0;
        for (GameObj gameObj:game.universe.getGameObjs(Planet.class)) {
            Planet planet = (Planet)gameObj;
            if (planet.faction == GameObj.Factions.PLAYER) {
                CREDITS_PER_TICK += planet.creditsByTick();
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

    // SPEND RESOURCE AND RETURN HOW MANY CAN BE SPENT
    public int spendResource(int resourcetype, int amount) {

        switch (resourcetype) {
            case 1:
                amount = MathUtils.clamp(amount, 0, RESOURCE1);
                RESOURCE1 -= amount;
                break;
            case 2:
                amount = MathUtils.clamp(amount, 0, RESOURCE2);
                RESOURCE2 -= amount;
                break;
            case 3:
                amount = MathUtils.clamp(amount, 0, RESOURCE3);
                RESOURCE3 -= amount;
                break;
        }

        return amount;
    }
}
