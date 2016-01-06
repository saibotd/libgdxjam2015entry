package com.gdxjam.magellan.gameobj;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
public class Shop extends GameObj implements IDrawableWindow, IDrawableMap, IInteractable {

    private Sprite mapSprite;
    private Array<ShopItem> inventory = new Array<ShopItem>();
    private Label info;
    private int lastSelectedIndex;

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
            inventory.add(new ShopItemDroneRoutine(DroneRoutine.ROUTINES.ATTACKING, 400));
        if(!MagellanGame.gameState.UNLOCKED_ROUTINES.contains(DroneRoutine.ROUTINES.ADVSCOUTING, false))
            inventory.add(new ShopItemDroneRoutine(DroneRoutine.ROUTINES.ADVSCOUTING, 600));

        inventory.add(new ShopItemUpgrade(400, ShopItemUpgrade.upgradeType.ATTACK));
        inventory.add(new ShopItemUpgrade(400, ShopItemUpgrade.upgradeType.HEALTH));
        if(MagellanGame.instance.universe == null || MagellanGame.instance.universe.playerShip.health < .5)
            inventory.add(new ShopItemUpgrade(400, ShopItemUpgrade.upgradeType.SHIELD));
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
        Window window = MagellanGame.instance.windowScreen.getWindow("Buy");
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
                    MagellanGame.soundFx.buy_sell.play(0.7f);
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
            }
        });

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


    private void selectItem(int index) {
        ShopItem item = inventory.get(index);
        info.setText(item.description + "\nPrice: " + item.price);
        lastSelectedIndex = index;
    }


    @Override
    public void prepareRenderingOnMap() {
        mapSprite = new Sprite(MagellanGame.assets.get("map_shop.png", Texture.class));
        mapSprite.setSize(14, 14);
        mapSprite.setPosition(sector.position.x - 20, sector.position.y + 20);
    }

    @Override
    public void renderOnMap(SpriteBatch batch, float delta) {
        super.render(delta);
        mapSprite.draw(batch);
    }

    @Override
    public String getTitle() {
        return "Trading Post";
    }

    @Override
    public String getInfo() {
        return null;
    }

    @Override
    public Actor getActor() {
        Image image = new Image(MagellanGame.assets.get("map_shop.png", Texture.class));
        return image;
    }
}
