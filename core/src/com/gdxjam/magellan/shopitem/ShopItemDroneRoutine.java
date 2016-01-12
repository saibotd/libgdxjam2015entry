package com.gdxjam.magellan.shopitem;

import com.gdxjam.magellan.MagellanGame;
import com.gdxjam.magellan.drones.DroneRoutine;
import com.gdxjam.magellan.ships.PlayerShip;

/**
 * Created by lolcorner on 31.12.2015.
 * Helper class for encapsulating items for the shops
 */
public class ShopItemDroneRoutine extends ShopItem {
    private DroneRoutine.ROUTINES routine;

    public ShopItemDroneRoutine(DroneRoutine.ROUTINES _routine, int price){
        super("Drone Routine " + _routine.toString(), getInfo(_routine), price);
        routine = _routine;
    }
    public void buy(PlayerShip buyer){
        MagellanGame.gameState.UNLOCKED_ROUTINES.add(routine);
    }

    public static String getInfo(DroneRoutine.ROUTINES _routine) {
        final String info;
        switch (_routine) {
            case MINING: info = "This routine unlocks the\nDrone's mining equipment."; break;
            case SCOUTING: info = "This routine unlocks the\nDrone's thruster."; break;
            case ATTACKING: info = "This routine unlocks the\nDrone's cannon!"; break;
            case ADVSCOUTING: info = "This routine offers\na smarter algorithm\nfor scouting."; break;
            case FOLLOWING: info = "This routine programs\nthe drone to follow\nyour ship."; break;
            case REPAIRING: info = "This routine programs\nthe drone to repair\nyour ship."; break;
            default: info = "";
        }
        return info;
    }
}
