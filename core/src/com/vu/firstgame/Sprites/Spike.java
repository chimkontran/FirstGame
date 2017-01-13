package com.vu.firstgame.Sprites;

import com.badlogic.gdx.maps.MapObject;
import com.vu.firstgame.FirstGame;
import com.vu.firstgame.Screens.PlayScreen;

/**
 * Created by Vu on 1/5/2017.
 */

public class Spike extends InteractiveTileObject {
    public Spike(PlayScreen screen, MapObject object) {
        super(screen, object);
        fixture.setUserData(this);
        setCategoryFilter(FirstGame.SPIKE_BIT);
    }

    @Override
    public void onHeadHit(Mainchar mainchar) {

    }
}
