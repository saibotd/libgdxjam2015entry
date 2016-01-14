package com.gdxjam.magellan.shopitem;

import com.gdxjam.magellan.ships.PlayerShip;

/**
 * Created by lolcorner on 31.12.2015.
 * Helper class for encapsulating items for the shops
 */
public class ShopItemDrone extends ShopItem {
    private Integer level;

    public ShopItemDrone(int level){
        super("Drone Level " + level, "This drone can handle\nup to " + level + " subroutines", level * level * 1000);
        this.level = level;
    }
    public void buy(PlayerShip buyer){
        buyer.drones.add(level);
    }
}
