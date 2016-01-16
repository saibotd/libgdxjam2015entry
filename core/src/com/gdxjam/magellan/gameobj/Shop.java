package com.gdxjam.magellan.gameobj;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.OrderedMap;
import com.gdxjam.magellan.MagellanGame;
import com.gdxjam.magellan.Sector;
import com.gdxjam.magellan.drones.DroneRoutine;
import com.gdxjam.magellan.shopitem.ShopItem;
import com.gdxjam.magellan.shopitem.ShopItemDrone;
import com.gdxjam.magellan.shopitem.ShopItemDroneRoutine;
import com.gdxjam.magellan.shopitem.ShopItemUpgrade;

/**
 * Created by Felix on 29.12.2015.
 */
public class Shop extends MovingGameObj implements IDrawableWindow, IDrawableMap, IInteractable {

    private Array<ShopItem> inventory = new Array<ShopItem>();
    private Label info;
    private int lastSelectedIndex;
    private boolean droneBought = false;

    public Shop(Sector sector) {
        super(sector);
        lastSelectedIndex = -1;
    }

    private void fillInventory(){
        inventory.clear();
        inventory.add(new ShopItemDrone(1));
        inventory.add(new ShopItemDrone(2));
        inventory.add(new ShopItemDrone(3));
        inventory.add(new ShopItemDrone(4));
        inventory.add(new ShopItemDrone(5));



        if(!MagellanGame.gameState.UNLOCKED_ROUTINES.contains(DroneRoutine.ROUTINES.ATTACKING, false))
            inventory.add(new ShopItemDroneRoutine(DroneRoutine.ROUTINES.ATTACKING, 6000));
        if(!MagellanGame.gameState.UNLOCKED_ROUTINES.contains(DroneRoutine.ROUTINES.ADVSCOUTING, false))
            inventory.add(new ShopItemDroneRoutine(DroneRoutine.ROUTINES.ADVSCOUTING, 1200));
        if(!MagellanGame.gameState.UNLOCKED_ROUTINES.contains(DroneRoutine.ROUTINES.FOLLOWING, false))
            inventory.add(new ShopItemDroneRoutine(DroneRoutine.ROUTINES.FOLLOWING, 4000));

        inventory.add(new ShopItemUpgrade(MagellanGame.instance.universe.playerShip.attack * 2 * 430, ShopItemUpgrade.upgradeType.ATTACK));
        inventory.add(new ShopItemUpgrade(MagellanGame.instance.universe.playerShip.maxHealth * 2 * 320, ShopItemUpgrade.upgradeType.HEALTH));
        if(MagellanGame.instance.universe == null || MagellanGame.instance.universe.playerShip.shield < .5)
            inventory.add(new ShopItemUpgrade((int) ((MagellanGame.instance.universe.playerShip.shield * 10 + 1) * 3500), ShopItemUpgrade.upgradeType.SHIELD));
    }

    @Override
    public OrderedMap<String, Interaction> getInteractions(GameObj with) {
        OrderedMap<String, Interaction> interactions = new OrderedMap<String, Interaction>();
        interactions.put("Buy", new Interaction() {
            @Override
            public void interact() {
                showInventoryWindow();
            }
        });
        /*
        interactions.put("Sell", new Interaction() {
            @Override
            public void interact() {
                showInteractionWindow();
            }
        });
        */
        return interactions;
    }

    public void showInventoryWindow(){
        fillInventory();
        MagellanGame.instance.windowScreen.closeWindow();
        Window window = MagellanGame.instance.windowScreen.getWindowWithoutClose("Buy");
        Skin skin = MagellanGame.instance.windowScreen.skin;
        VerticalGroup windowContent = new VerticalGroup();
        HorizontalGroup listAndInfo = new HorizontalGroup();
        listAndInfo.space(20);
        HorizontalGroup menu = new HorizontalGroup();
        menu.padTop(20);
        final List list = new List(skin);
        info = new Label("", skin, "inlay");

        ScrollPane scrollPane = new ScrollPane(list);

        TextButton buyButton = new TextButton("Buy", skin);
        TextButton doneButton = new TextButton("Done", skin);
        listAndInfo.addActor(scrollPane);
        listAndInfo.addActor(info);
        info.setFillParent(true);
        //scrollPane.setFillParent(true);
        Array<String> listItems = new Array<String>();
        for(ShopItem item:inventory){
            listItems.add(item.title);
        }
        list.setItems(listItems);
        list.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                selectItem(list.getSelectedIndex());
            }
        });
        buyButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ShopItem item = inventory.get(list.getSelectedIndex());
                if(item.price <= MagellanGame.gameState.CREDITS) {
                    MagellanGame.gameState.CREDITS -= item.price;
                    item.buy(MagellanGame.instance.universe.playerShip);
                    MagellanGame.soundFx.buy.play(0.7f);
                    droneBought = item instanceof ShopItemDrone;
                } else {
                    MagellanGame.soundFx.nope.play(0.7f);
                }
                MagellanGame.gameState.updateNumberOfDrones();
                showInventoryWindow();
            }
        });
        doneButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                MagellanGame.instance.windowScreen.closeWindow();
                if(!MagellanGame.gameState.DRONE_INFO_SHOWN && droneBought){
                    String s = "Drones can be deployed by clicking on your ship";
                    s += "\non the left side. After deploying a drone,";
                    s += "\nbe sure to click on it and setup routines.";
                    s += "\ndepending on the level, a drone can hold up to 5";
                    s += "\nof them. If you setup less routines than maximum";
                    s += "\nthe set up routines become more powerful.";
                    MagellanGame.instance.windowScreen.getWindow("INFO", s);
                    MagellanGame.gameState.DRONE_INFO_SHOWN = true;
                }
            }
        });
        lastSelectedIndex = MathUtils.clamp(lastSelectedIndex, 0, inventory.size-1);
        if(lastSelectedIndex != -1){
            list.setSelectedIndex(lastSelectedIndex);
            selectItem(lastSelectedIndex);
        } else{
            list.setSelectedIndex(0);
            selectItem(0);
        }

        menu.addActor(buyButton);
        menu.addActor(doneButton);
        windowContent.addActor(listAndInfo);
        windowContent.addActor(menu);
        window.add(windowContent);
    }

    @Override
    public void moveTo(Sector sector) {

    }

    private void selectItem(int index) {
        ShopItem item = inventory.get(index);
        info.setText(item.description + "\n\nPrice: " + item.price);
        lastSelectedIndex = index;
    }


    @Override
    public void prepareRenderingOnMap() {
        spriteVessel = new Sprite(MagellanGame.assets.get("shop.png", Texture.class));
        spriteVessel.setSize(16, 16);
        spriteVessel.setOriginCenter();

        sectorSlot = 0;
        getParkingPosition();

        spriteVessel.setPosition(parkingPosition.x, parkingPosition.y);
        spriteVessel.setRotation(parkingAngle);
    }

    @Override
    public Actor getActor() {
        Image image = new Image(MagellanGame.assets.get("sectorview_shop.png", Texture.class));
        //image.setScale(.4f);
        return image;
    }


    @Override
    public void renderOnMap(SpriteBatch batch, float delta) {
        super.render(delta);
        spriteVessel.draw(batch);
    }

    @Override
    public String getTitle() {
        return "Trading Post";
    }

    @Override
    public String getInfo() {
        String s = "INTERCOM: Hello good friend! We have drones, upgrades,\nanything you need!\nIf you have the credits, that is.";
        if(MagellanGame.gameState.CREDITS == 0){
            s = "INTERCOM: Hey there! Weird, our scanners pick up 0 credits on your ship\nand no, we won't accept frozen lifeforms as a substitute!";
            s += "\nHow about this:\nYou populate some planets with those creatures\nand they will generate credits for you.";
            s += "\nYou bring those credits to our trading outposts\nand we sell you cool stuff! Deal? Deal!";
        }
        return s;
    }


}
