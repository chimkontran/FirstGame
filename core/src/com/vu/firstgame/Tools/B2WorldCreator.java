package com.vu.firstgame.Tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.vu.firstgame.FirstGame;
import com.vu.firstgame.Screens.PlayScreen;
import com.vu.firstgame.Sprites.Bin;
import com.vu.firstgame.Sprites.Brick;
import com.vu.firstgame.Sprites.Coin;
import com.vu.firstgame.Sprites.Gumgum;
import com.vu.firstgame.Sprites.Spike;

/**
 * Created by catkutatca12 on 12/19/2016.
 */

public class B2WorldCreator {
    private Array<Gumgum> gumgums;

    public B2WorldCreator(PlayScreen screen) {
        World world = screen.getWorld();
        TiledMap map = screen.getMap();
// Create Body/Fixture
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        // Create GROUND body/fixtures //
        for (MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / FirstGame.PPM,
                    (rect.getY() + rect.getHeight() / 2) / FirstGame.PPM);
//            bdef.position.set((rect.getX() + rect.getWidth() / 2) ,
//                    (rect.getY() + rect.getHeight() / 2) );

            body = world.createBody(bdef);
            shape.setAsBox((rect.getWidth() / 2) / FirstGame.PPM, (rect.getHeight() / 2) / FirstGame.PPM);
//            shape.setAsBox(rect.getWidth() / 2, rect.getHeight() / 2);
            fdef.shape = shape;
            body.createFixture(fdef);
        }
        // Create MUSHROOM body/fixtures //
        for (MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / FirstGame.PPM,
                    (rect.getY() + rect.getHeight() / 2) / FirstGame.PPM);

            body = world.createBody(bdef);

            shape.setAsBox((rect.getWidth() / 2) / FirstGame.PPM, (rect.getHeight() / 2) / FirstGame.PPM);
            fdef.shape = shape;
            fdef.filter.categoryBits = FirstGame.OBJECT_BIT;
            body.createFixture(fdef);
        }
        // Create BRICK body/fixtures //
        for (MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)) {

            new Brick(screen, object);
        }

        // Create SPIKEs
        for (MapObject object : map.getLayers().get(8).getObjects().getByType(RectangleMapObject.class)) {
            new Spike(screen, object);
        }

        // Create BINs
        for (MapObject object : map.getLayers().get(9).getObjects().getByType(RectangleMapObject.class)) {
            new Bin(screen, object);
        }

        // Create COIN body/fixtures //
        for (MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)) {

            new Coin(screen, object);
        }

        // Create GUMGUMs
        gumgums = new Array<Gumgum>();
        for (MapObject object : map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            gumgums.add(new Gumgum(screen, rect.getX() / FirstGame.PPM, rect.getY() / FirstGame.PPM));
        }


    }

    public Array<Gumgum> getGumgums() {
        return gumgums;
    }
}
