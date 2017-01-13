package com.vu.firstgame.Items;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.vu.firstgame.FirstGame;
import com.vu.firstgame.Scenes.Hud;
import com.vu.firstgame.Screens.PlayScreen;
import com.vu.firstgame.Sprites.Mainchar;

/**
 * Created by Vu on 1/5/2017.
 */

public class Trash extends Item {

    public Trash(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        setRegion(screen.getAtlas().findRegion("apple"), 0, 0, 16, 16);
        velocity = new Vector2(0, 0);
    }

    @Override
    public void defineItem() {
        BodyDef bdef = new BodyDef();
//        bdef.position.set(32 / FirstGame.PPM, 32 / FirstGame.PPM);
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6 / FirstGame.PPM);
        fdef.filter.categoryBits = FirstGame.ITEM_BIT;
        fdef.filter.maskBits = FirstGame.MAINCHAR_BIT |
                FirstGame.GROUND_BIT |  FirstGame.OBJECT_BIT |
                FirstGame.COIN_BIT | FirstGame.BRICK_BIT;

        fdef.shape = shape;
        body.createFixture(fdef).setUserData(this);
    }

    @Override
    public void use(Mainchar mainchar) {
        FirstGame.manager.get("sounds/yay.wav", Sound.class).play();
        destroy();
        Hud.addTrash(1);
    }

    @Override
    public void update(float dt) {
        super.update(dt);
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
        velocity.y = body.getLinearVelocity().y;
        body.setLinearVelocity(velocity);
    }
}
