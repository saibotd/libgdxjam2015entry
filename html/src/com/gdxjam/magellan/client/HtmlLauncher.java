package com.gdxjam.magellan.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.gdxjam.magellan.MagellanGame;

public class HtmlLauncher extends GwtApplication {

        @Override
        public GwtApplicationConfiguration getConfig () {
                GwtApplicationConfiguration cfg = new GwtApplicationConfiguration(1280, 720);
                cfg.antialiasing = true;
                return cfg;
        }

        @Override
        public ApplicationListener getApplicationListener () {
                return new MagellanGame();
        }
}