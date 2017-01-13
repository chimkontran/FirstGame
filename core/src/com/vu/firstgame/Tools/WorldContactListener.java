package com.vu.firstgame.Tools;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.vu.firstgame.FirstGame;
import com.vu.firstgame.Items.Item;
import com.vu.firstgame.Sprites.Enemy;
import com.vu.firstgame.Sprites.InteractiveTileObject;
import com.vu.firstgame.Sprites.Mainchar;

/**
 * Created by catkutatca12 on 12/20/2016.
 */

public class WorldContactListener implements ContactListener {

    @Override
    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        // cDef = collision definition
        int cDef = fixtureA.getFilterData().categoryBits | fixtureB.getFilterData().categoryBits;

        switch (cDef) {
            // Player HEAD contacts brick/coin
            case FirstGame.MAINCHAR_HEAD_BIT | FirstGame.BRICK_BIT:
            case FirstGame.MAINCHAR_HEAD_BIT | FirstGame.COIN_BIT:
//                Mainchar.State.JUMPING;
                if (fixtureA.getFilterData().categoryBits == FirstGame.MAINCHAR_HEAD_BIT)
                    ((InteractiveTileObject) fixtureB.getUserData()).onHeadHit((Mainchar) fixtureA.getUserData());
                else
                    ((InteractiveTileObject) fixtureA.getUserData()).onHeadHit((Mainchar) fixtureB.getUserData());
                break;
            // Player contacts Enemy HEAD
            case FirstGame.ENEMY_HEAD_BIT | FirstGame.MAINCHAR_BIT:
                if (fixtureA.getFilterData().categoryBits == FirstGame.ENEMY_HEAD_BIT)
                    ((Enemy) fixtureA.getUserData()).hitOnHead();
                else
                    ((Enemy) fixtureB.getUserData()).hitOnHead();
                break;
            // Enemy contacts Object
            case FirstGame.ENEMY_HEAD_BIT | FirstGame.OBJECT_BIT:
                if (fixtureA.getFilterData().categoryBits == FirstGame.ENEMY_HEAD_BIT)
                    ((Enemy) fixtureA.getUserData()).reverseVelocity(true, false);
                else
                    ((Enemy) fixtureB.getUserData()).reverseVelocity(true, false);
                break;
            case FirstGame.ENEMY_BIT | FirstGame.OBJECT_BIT:
                if (fixtureA.getFilterData().categoryBits == FirstGame.ENEMY_BIT)
                    ((Enemy) fixtureA.getUserData()).reverseVelocity(true, false);
                else
                    ((Enemy) fixtureB.getUserData()).reverseVelocity(true, false);
                break;
            // Enemy contacts Ground
            case FirstGame.ENEMY_HEAD_BIT | FirstGame.GROUND_BIT:
                if (fixtureA.getFilterData().categoryBits == FirstGame.ENEMY_HEAD_BIT)
                    ((Enemy) fixtureA.getUserData()).reverseVelocity(true, false);
                else
                    ((Enemy) fixtureB.getUserData()).reverseVelocity(true, false);
                break;
            case FirstGame.ENEMY_BIT | FirstGame.GROUND_BIT:
                if (fixtureA.getFilterData().categoryBits == FirstGame.ENEMY_BIT)
                    ((Enemy) fixtureA.getUserData()).reverseVelocity(true, false);
                else
                    ((Enemy) fixtureB.getUserData()).reverseVelocity(true, false);
                break;

            // Player contacts Enemy / Spike
            case FirstGame.MAINCHAR_BIT | FirstGame.SPIKE_BIT:
            case FirstGame.MAINCHAR_BIT | FirstGame.ENEMY_BIT:
                if (fixtureA.getFilterData().categoryBits == FirstGame.MAINCHAR_BIT)
                    ((Mainchar) fixtureA.getUserData()).hit();
                else
                    ((Mainchar) fixtureB.getUserData()).hit();
                break;
            // Enemy contacts Enemy
            case FirstGame.ENEMY_HEAD_BIT | FirstGame.ENEMY_HEAD_BIT:
            case FirstGame.ENEMY_BIT | FirstGame.ENEMY_BIT:
                ((Enemy) fixtureA.getUserData()).reverseVelocity(true, false);
                ((Enemy) fixtureB.getUserData()).reverseVelocity(true, false);
                break;
            // Item contacts Ground
            case FirstGame.ITEM_BIT | FirstGame.GROUND_BIT:
                if (fixtureA.getFilterData().categoryBits == FirstGame.ITEM_BIT)
                    ((Item) fixtureA.getUserData()).reverseVelocity(true, false);
                else
                    ((Item) fixtureB.getUserData()).reverseVelocity(true, false);
                break;
            // check item collide mainchar if get fixtureA = item then
            // use that item on fixtureB (mainchar) or vice versa
            case FirstGame.ITEM_BIT | FirstGame.MAINCHAR_HEAD_BIT:
            case FirstGame.ITEM_BIT | FirstGame.MAINCHAR_BIT:
                if (fixtureA.getFilterData().categoryBits == FirstGame.ITEM_BIT)
                    ((Item) fixtureA.getUserData()).use((Mainchar) fixtureB.getUserData());
                else
                    ((Item) fixtureB.getUserData()).use((Mainchar) fixtureA.getUserData());
                break;
            // character touch BIN and set TRASH to 0
            case FirstGame.MAINCHAR_BIT | FirstGame.BIN_BIT:
                if (fixtureA.getFilterData().categoryBits == FirstGame.MAINCHAR_BIT)
                    ((Mainchar) fixtureA.getUserData()).throwTrash();
                else
                    ((Mainchar) fixtureB.getUserData()).throwTrash();
                break;
        }
    }

    @Override
    public void endContact(Contact contact) {
//        Gdx.app.log("End contact", "");

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
