package com.gdxjam.magellan.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.gdxjam.magellan.MagellanGame;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Window;

public class HtmlLauncher extends GwtApplication {
        int WIDTH = 1280;
        int HEIGHT = 720;
        @Override
        public GwtApplicationConfiguration getConfig () {
                GwtApplicationConfiguration config = new GwtApplicationConfiguration(Window.getClientWidth(), HEIGHT * Window.getClientHeight()/WIDTH);
                config.antialiasing = true;
                Window.addResizeHandler(new ResizeHandler() {
                        @Override
                        public void onResize(ResizeEvent event) {
                                int width = event.getWidth();
                                int height = HEIGHT * event.getHeight()/WIDTH;
                                Gdx.graphics.setDisplayMode(width, height, false);
                                Gdx.gl.glViewport(0, 0, width, height);
                        }
                });
                return config;
        }

        @Override
        public ApplicationListener getApplicationListener () {
                MagellanGame game = new MagellanGame();
                return game;
        }
}