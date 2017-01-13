package com.vu.firstgame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.vu.firstgame.Screens.PlayScreen;

public class FirstGame extends Game {
    public static final int V_WIDTH = 400;
    public static final int V_HEIGHT = 208;
    public static final float PPM = 100; // pixel per meter

    // BOX2D collision BIT
    public static final short NOTHING_BIT = 0;
    public static final short GROUND_BIT = 1;
    public static final short MAINCHAR_BIT = 2;
    public static final short BRICK_BIT = 4;
    public static final short COIN_BIT = 8;
    public static final short DESTROYED_BIT = 16;
    public static final short OBJECT_BIT = 32;
    public static final short ENEMY_BIT = 64;
    public static final short ENEMY_HEAD_BIT = 128;
    public static final short ITEM_BIT = 256;
    public static final short MAINCHAR_HEAD_BIT = 512;
    public static final short SPIKE_BIT = 1024;
    public static final short BIN_BIT = 2048;

    public static SpriteBatch batch;
    // Sound
    public static AssetManager manager;

    @Override
    public void create() {
        batch = new SpriteBatch();
        // Sound
        manager = new AssetManager();
        manager.load("sounds/numberone.ogg", Music.class);
        manager.load("sounds/coin.wav", Sound.class);
        manager.load("sounds/ech.wav", Sound.class);
        manager.load("sounds/break.wav", Sound.class);
        manager.load("sounds/splat.wav", Sound.class);
        manager.load("sounds/bite.wav", Sound.class);
        manager.load("sounds/wow.wav", Sound.class);
        manager.load("sounds/scream.wav", Sound.class);
        manager.load("sounds/failed.wav", Sound.class);
        manager.load("sounds/yay.wav", Sound.class);
        manager.finishLoading();

        setScreen(new PlayScreen(this));
    }

    @Override
    public void dispose() {
        super.dispose();
        manager.dispose();
        batch.dispose();
    }

    @Override
    public void render() {
        super.render();
//        manager.update();
    }

}
