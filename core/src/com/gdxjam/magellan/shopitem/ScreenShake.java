package com.gdxjam.magellan.shopitem;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector3;

import java.util.Random;

public class ScreenShake {
    private static boolean shakeScreen = false;
    private static Random rad = new Random();
    private static int shakes = 0;
    private static int nShakes = 0;
    private static Vector3 camPos;
    private static float velocity = 1;

    private static boolean shake2 = false;

    public static void update(Camera camera) {
        if (shakeScreen) {
            float n1 = rad.nextInt(2) == 0 ? rad.nextFloat() * velocity : rad.nextFloat() * velocity * -1;
            float n2 = rad.nextInt(2) == 0 ? rad.nextFloat() * velocity : rad.nextFloat() * velocity * -1;

            camera.translate(n2, n1, 0);

            if (shake2) {
                shake2 = false;
                camera.position.set(camPos);
            } else
                shake2 = true;

            shakes++;

            if (shakes > nShakes) {
                shakeScreen = false;
                nShakes = 0;
                shakes = 0;
                camera.position.set(camPos);

            }

        } else {

        }
    }

    public static void shakeScreen(int nshakes, Vector3 camPoss, float f) {
        if (shakeScreen) {
            if (nshakes < nShakes) nshakes = nShakes;
            if (f < velocity) f = velocity;
        }
        nShakes = nshakes;
        shakeScreen = true;
        camPos = camPoss;
        velocity = f;
    }

}