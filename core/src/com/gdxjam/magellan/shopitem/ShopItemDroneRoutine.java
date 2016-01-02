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
        /*
        Todo: Descriptions for routines
         */
        super("drone Routine " + _routine.toString(), _routine.toString(), price);
        routine = _routine;
    }
    public void buy(PlayerShip buyer){
        MagellanGame.gameState.UNLOCKED_ROUTINES.add(routine);
    }
}
