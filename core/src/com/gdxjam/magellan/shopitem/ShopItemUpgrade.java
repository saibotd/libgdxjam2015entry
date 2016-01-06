package com.gdxjam.magellan.shopitem;

import com.badlogic.gdx.math.MathUtils;
import com.gdxjam.magellan.MagellanGame;
import com.gdxjam.magellan.ships.PlayerShip;

/**
 * Created by lolcorner on 31.12.2015.
 * Helper class for encapsulating items for the shops
 */
public class ShopItemUpgrade extends ShopItem {
    private final upgradeType type;
    public enum upgradeType{ATTACK, HEALTH, SHIELD};

    public ShopItemUpgrade(int _price, upgradeType type){
        super("SHIP UPGRADE " + type, "+1 TO YOUR " + type, _price);
        this.type = type;
    }
    public void buy(PlayerShip buyer){
        switch (type){
            case ATTACK:
                buyer.attack++;
                break;
            case HEALTH:
                buyer.health++;
                break;
            case SHIELD:
                buyer.shield += .025f;
                break;
        }
        buyer.shield = MathUtils.clamp(buyer.shield, .1f, .5f);
    }
}
