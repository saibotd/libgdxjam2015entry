package com.gdxjam.magellan;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;

/**
 * Created by lolcorner on 20.12.2015.
 */
public interface IInteractable {
    ObjectMap<String, Interaction> getInteractions(final GameObj with);
    String getInfo();
    interface Interaction {
        void interact();
    }
}
