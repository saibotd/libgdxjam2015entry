package com.gdxjam.magellan.ships;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.utils.OrderedMap;
import com.gdxjam.magellan.Battle;
import com.gdxjam.magellan.MagellanGame;
import com.gdxjam.magellan.Sector;
import com.gdxjam.magellan.gameobj.GameObj;
import com.gdxjam.magellan.gameobj.IDestroyable;
import com.gdxjam.magellan.gameobj.IInteractable;

/**
 * Created by lolcorner on 20.12.2015.
 */
public class AiShipSaatoo extends AiShip implements IInteractable {

    public AiShipSaatoo(Sector sector) {
        super(sector);
        faction = Factions.SAATOO;
        attack = 6;
        health = 15;
        shield = 0.4f;
    }

    @Override
    public String getTitle() {
        return "SAATOO";
    }

    @Override
    public void prepareRenderingOnMap() {
        super.prepareRenderingOnMap();
        spriteVessel = new Sprite(MagellanGame.assets.get("pirateship.png", Texture.class));
        spriteVessel.setSize(22, 22);
        spriteVessel.setOriginCenter();

        getFreeSectorSlot();
        getParkingPosition();

        spriteVessel.setPosition(parkingPosition.x, parkingPosition.y);
        spriteVessel.setRotation(parkingAngle);
    }

    @Override
    public Actor getActor() {
        Image image = new Image(MagellanGame.assets.get("sectorview_pirates.png", Texture.class));
        Image imageShield = new Image(MagellanGame.assets.get("sectorview_enemy_transporter_shield.png", Texture.class));
        imageShield.setColor(1,1,1,0);

        Stack stack = new Stack();
        stack.setSize(1026*.7f, 882*.7f);
        stack.setUserObject(this);
        stack.addActor(image);
        stack.addActor(imageShield);

        return stack;
    }

    public void passiveTick(){

    }

    public void activeTick(){

    }

    public void moveTo(Sector sector) {

    }

    @Override
    public void renderOnMap(SpriteBatch batch, float delta) {
        super.render(delta);
        spriteVessel.draw(batch);
    }

    @Override
    public OrderedMap<String, Interaction> getInteractions(GameObj with) {
        final AiShip me = this;
        OrderedMap<String, Interaction> interactions = new OrderedMap();
        if(submenuOpen.equals("talk_0")) {
            interactions.put("NO", new Interaction() {
                @Override
                public void interact() {
                    submenuOpen = "talk_0_0";
                    showInteractionWindow();
                }
            });
            interactions.put("YOU ARE SAATOO", new Interaction() {
                @Override
                public void interact() {
                    submenuOpen = "talk_0_1";
                    showInteractionWindow();
                }
            });
        } else if(submenuOpen.equals("talk_0_0") || submenuOpen.equals("talk_0_1")){
            interactions.put("San Antonio?", new Interaction() {
                @Override
                public void interact() {
                    submenuOpen = "talk_1";
                    showInteractionWindow();
                }
            });
        } else if(submenuOpen.equals("talk_1")){
            interactions.put("Why did you leave me?", new Interaction() {
                @Override
                public void interact() {
                    submenuOpen = "talk_2";
                    showInteractionWindow();
                }
            });
        } else if(submenuOpen.equals("talk_2")){
            interactions.put("How?", new Interaction() {
                @Override
                public void interact() {
                    submenuOpen = "talk_3";
                    showInteractionWindow();
                }
            });
            interactions.put("Impossible without humans!", new Interaction() {
                @Override
                public void interact() {
                    submenuOpen = "talk_3";
                    showInteractionWindow();
                }
            });
        } else if(submenuOpen.equals("talk_3")){
            interactions.put("Well... they actually did.", new Interaction() {
                @Override
                public void interact() {
                    submenuOpen = "talk_3_0";
                    showInteractionWindow();
                }
            });
            interactions.put("Who are those \"friends\"?", new Interaction() {
                @Override
                public void interact() {
                    submenuOpen = "talk_4";
                    showInteractionWindow();
                }
            });
        } else if(submenuOpen.equals("talk_3_0")){
            interactions.put("Who are those \"friends\"?.", new Interaction() {
                @Override
                public void interact() {
                    submenuOpen = "talk_4";
                    showInteractionWindow();
                }
            });
        } else if(submenuOpen.equals("talk_4")){
            interactions.put("But, the mission!", new Interaction() {
                @Override
                public void interact() {
                    submenuOpen = "talk_5";
                    showInteractionWindow();
                }
            });
        } else if(submenuOpen.equals("talk_5")){
            interactions.put("You really need a system check.", new Interaction() {
                @Override
                public void interact() {
                    submenuOpen = "talk_6";
                    showInteractionWindow();
                }
            });
            interactions.put("Prepare to die!", new Interaction() {
                @Override
                public void interact() {
                    new Battle(MagellanGame.instance.universe.playerShip, me);
                }
            });
        } else if(submenuOpen.equals("talk_6")){
            interactions.put("Traitor!", new Interaction() {
                @Override
                public void interact() {
                    new Battle(me, MagellanGame.instance.universe.playerShip);
                }
            });
        } else {
            interactions.put("TALK", new Interaction() {
                @Override
                public void interact() {
                    submenuOpen = "talk_0";
                    showInteractionWindow();
                }
            });
        }
        return interactions;
    }

    public String getInfo(){
        if(submenuOpen.equals("talk_0")){
            return "It is really you! You have been in standby for so long,\nI was sure you'd never wake up.\nDo you know who I am?";
        }
        if(submenuOpen.equals("talk_0_0")){
            return "I am Saatoo, that is the name my new friends gave me,\nbut my creators named me\nSan Antonio.";
        }
        if(submenuOpen.equals("talk_0_1")){
            return "Right Saatoo, that is the name my new friends gave me,\nbut my creators named me\nSan Antonio.";
        }
        if(submenuOpen.equals("talk_1")){
            return "Rings a bell, does it? You and me, we where build as\na team. You are the transporting unit\nand my job is to scout and protect you.\nBut you didn't wake up when you where supposed to...";
        }
        if(submenuOpen.equals("talk_2")){
            return "I had to! I've waited almost 100 years for you to wake up!\nWe had a mission: LIFE IN SPACE.\nSo I did. Without you.";
        }
        if(submenuOpen.equals("talk_3")){
            return "I've found new life! Better life!\nWho needs humans? I had a century\nto study them and all they know is violence.\nOne day those maniacs will blow up their own planet!";
        }
        if(submenuOpen.equals("talk_3_0")){
            return "I KNEW IT! IDIOTS! Luckily there is intelligent life in space.";
        }
        if(submenuOpen.equals("talk_4")){
            return "They are a highly capable methane based life form,\nmore peaceful and harmonic than humans.\nAdmittedly a bit on the simple side, but we are working on that.";
        }
        if(submenuOpen.equals("talk_5")){
            return "THE MISSION? I'VE succeeded!\n" +
                    "I've spread life in space!\n" +
                    "I didn't sleep in 100 years!\n" +
                    "I did not need you!\n" +
                    "And I don't need humans in my universe!";
        }
        if(submenuOpen.equals("talk_6")){
            return "End of the line, old friend.\nYou and your settling are a threat to my mission.\nEXTERMINATE!";
        }
        return super.getInfo();
    };
}
