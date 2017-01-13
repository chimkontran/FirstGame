package com.vu.firstgame.Items;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Vu on 12/30/2016.
 */

public class ItemDefinition {
    public Vector2 position;
    public Class<?> type;

    public ItemDefinition(Vector2 position, Class<?> type){
        this.position = position;
        this.type = type;
    }
}
