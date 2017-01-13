package com.vu.firstgame.Sprites;

import com.badlogic.gdx.maps.MapObject;
import com.vu.firstgame.FirstGame;
import com.vu.firstgame.Screens.PlayScreen;

/**
 * Created by Vu on 1/7/2017.
 */

public class Bin extends InteractiveTileObject {
    public Bin(PlayScreen screen, MapObject object) {
        super(screen, object);
        fixture.setUserData(this);
        setCategoryFilter(FirstGame.BIN_BIT);
    }

    @Override
    public void onHeadHit(Mainchar mainchar) {

    }
}
