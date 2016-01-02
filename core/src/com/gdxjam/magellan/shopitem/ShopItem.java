package com.gdxjam.magellan.shopitem;

import com.gdxjam.magellan.ships.PlayerShip;

/**
 * Created by lolcorner on 31.12.2015.
 * Helper class for encapsulating items for the shops
 */
public class ShopItem {
    public int price;
    public String title;
    public String description;
    public ShopItem(String _title, String _description, int _price){
        title = _title;
        description = _description;
        price = _price;
    }
    public void buy(PlayerShip buyer){
        buyer.inventory.add(this);
    }
}
