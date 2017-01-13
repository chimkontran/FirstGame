package com.vu.firstgame.Sprites;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.math.Vector2;
import com.vu.firstgame.FirstGame;
import com.vu.firstgame.Items.Apple;
import com.vu.firstgame.Items.ItemDefinition;
import com.vu.firstgame.Scenes.Hud;
import com.vu.firstgame.Screens.PlayScreen;

/**
 * Created by catkutatca12 on 12/19/2016.
 */

public class Coin extends InteractiveTileObject {
    private static TiledMapTileSet tileSet;
    private final int BLANK_COIN = 28; // blank coin icon ID

    public Coin(PlayScreen screen, MapObject object) {
        super(screen, object);
        tileSet = map.getTileSets().getTileSet("tileset_gutter");
        fixture.setUserData(this);
        setCategoryFilter(FirstGame.COIN_BIT);
    }

    @Override
    public void onHeadHit(Mainchar mainchar) {

        if (getCell().getTile().getId() == BLANK_COIN) {
            FirstGame.manager.get("sounds/ech.wav", Sound.class).play();
        } else {
            if (object.getProperties().containsKey("apple")) {
                screen.spawnItem(new ItemDefinition(new Vector2(body.getPosition().x,
                        body.getPosition().y + 16 / FirstGame.PPM), Apple.class));
                FirstGame.manager.get("sounds/wow.wav", Sound.class).play();
            }
            FirstGame.manager.get("sounds/coin.wav", Sound.class).play();
        }
        Hud.addScore(500);
        getCell().setTile(tileSet.getTile(BLANK_COIN));
    }
}
