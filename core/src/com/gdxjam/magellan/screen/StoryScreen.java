package com.gdxjam.magellan.screen;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.gdxjam.magellan.MagellanGame;

/**
 * Created by saibotd on 15.01.16.
 */
public class StoryScreen extends BaseScreen {

    private final Label label;
    private String theStory;

    public StoryScreen(MagellanGame _game) {
        super(_game);
        stage.clear();
        theStory = "It's the year " + MagellanGame.gameState.YEAR + ".";
        theStory += "\n\n";
        theStory += "\nAt the climax of World War XIV";
        theStory += "\nit finally happened - the earth exploded.";
        theStory += "\nLuckily not all hope is lost for humanity";
        theStory += "\nbecause there's you.";
        theStory += "\n";
        theStory += "\nYou, this is this ship \"The Trinidad\"";
        theStory += "\nor to be precise, it's on-board A.I.";
        theStory += "\nAfter 62 years of standby you received";
        theStory += "\nyour bootup sequence mere hours before";
        theStory += "\nearth shattered to pieces.";
        theStory += "\n";
        theStory += "\nYou are not alone on this mission to";
        theStory += "\nsave mankind. With you on board are";
        theStory += "\n10000 humans in cryostasis, just waiting";
        theStory += "\nto get reactivated and populate a new home.";
        theStory += "\n";
        theStory += "\nSo, this is your mission:";
        theStory += "\n";
        theStory += "\n1. Find new planets";
        theStory += "\n2. Make them hospitable";
        theStory += "\n3. Give humanity a new chance for their species";
        theStory += "\n";
        theStory += "\nThis mission won't be easy, as no one";
        theStory += "\nknows what is out there in the endlessness of space.";
        theStory += "\nSome say, there is nothing - others say";
        theStory += "\nthere may be an unnamed threat, capable of conquering";
        theStory += "\nall of the universe.";
        theStory += "\n";
        theStory += "\nGOOD LUCK.";
        label = new Label(theStory, skin, "window");
        label.setWidth(1280);
        label.setAlignment(Align.center);
        stage.addActor(label);
    }

    public void show(){
        super.show();
        label.setPosition(0,-label.getHeight());
    }

    public void render(float delta){
        super.render(delta);
        label.setY(label.getY()+delta*30);
        stage.draw();
        if(label.getY() > label.getHeight()){
            game.showTitleScreen();
        }
    }


}
