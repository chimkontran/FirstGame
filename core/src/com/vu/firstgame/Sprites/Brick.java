package com.vu.firstgame.Sprites;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.MapObject;
import com.vu.firstgame.FirstGame;
import com.vu.firstgame.Scenes.Hud;
import com.vu.firstgame.Screens.PlayScreen;

/**
 * Created by catkutatca12 on 12/19/2016.
 */

public class Brick extends InteractiveTileObject {
    public Brick(PlayScreen screen, MapObject object) {
        super(screen, object);
        fixture.setUserData(this);
        setCategoryFilter(FirstGame.BRICK_BIT);
    }

    @Override
    public void onHeadHit(Mainchar mainchar) {
        if (mainchar.isBig()) {
            setCategoryFilter(FirstGame.DESTROYED_BIT);
            getCell().setTile(null);
            Hud.addScore(100);
            FirstGame.manager.get("sounds/break.wav", Sound.class).play();
        }
        else FirstGame.manager.get("sounds/ech.wav", Sound.class).play();

    }
}
