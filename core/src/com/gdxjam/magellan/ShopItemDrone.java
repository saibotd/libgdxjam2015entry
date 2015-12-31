package com.gdxjam.magellan;

import com.gdxjam.magellan.ships.PlayerShip;

/**
 * Created by lolcorner on 31.12.2015.
 * Helper class for encapsulating items for the shops
 */
public class ShopItemDrone extends ShopItem {
    private Integer level;

    public ShopItemDrone(int level){
        super("drone lvl " + level, "this drone can handle up to " + level + " subroutines", level * 1000);
        this.level = level;
    }
    public void buy(PlayerShip buyer){
        buyer.drones.add(level);
    }
}
