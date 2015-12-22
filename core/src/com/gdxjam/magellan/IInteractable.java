package com.gdxjam.magellan;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;

/**
 * Created by lolcorner on 20.12.2015.
 */
public interface IInteractable {
    ObjectMap<String, Interaction> getInteractions(final GameObj with);
    interface Interaction {
        void interact(double arg1);
        void interact(String arg1);
    }
}
