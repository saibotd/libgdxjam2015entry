package com.gdxjam.magellan;

/**
 * Created by lolcorner on 19.12.2015.
 */
public class MovingGameObj extends GameObj {
    public void moveTo(Node node) {
        this.node.gameObjs.removeValue(this, true);
        this.node = node;
        node.gameObjs.add(this);
    }
}
