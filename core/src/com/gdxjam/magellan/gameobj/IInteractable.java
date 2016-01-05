package com.gdxjam.magellan.gameobj;

import com.badlogic.gdx.utils.OrderedMap;

/**
 * Created by lolcorner on 20.12.2015.
 */
public interface IInteractable extends IDrawableWindow {
    OrderedMap<String, Interaction> getInteractions(final GameObj with);
    interface Interaction {
        void interact();
    }

    interface InputInteraction extends Interaction {

    }

    interface SliderInteraction extends Interaction {

    }
}
