package com.vu.firstgame.Sprites;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.vu.firstgame.FirstGame;
import com.vu.firstgame.Items.ItemDefinition;
import com.vu.firstgame.Items.Trash;
import com.vu.firstgame.Scenes.Hud;
import com.vu.firstgame.Screens.PlayScreen;

/**
 * Created by Vu on 12/28/2016.
 */

public class Gumgum extends Enemy {
    private float stateTime;
    private Animation walkAnimation;
    private Array<TextureRegion> frames;
    private boolean setToDestroy;
    private boolean destroyed;
    int count = 0;

    public Gumgum(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        frames = new Array<TextureRegion>();
        for (int i = 0; i < 2; i++)
            frames.add(new TextureRegion(screen.getAtlas().findRegion("gum_gum"), i * 16, 0, 16, 16));
        walkAnimation = new Animation(0.4f, frames);
        stateTime = 0;
        setBounds(getX(), getY(), 16 / FirstGame.PPM, 16 / FirstGame.PPM);
        setToDestroy = false;
        destroyed = false;
//        throwTrash();

    }

    public void update(float dt) {
        stateTime += dt;
        throwTrash();
        if (setToDestroy && !destroyed) {
            world.destroyBody(b2body);
            destroyed = true;
            setRegion(new TextureRegion(screen.getAtlas().findRegion("gum_gum"), 32, 0, 16, 16));
            stateTime = 0;
        } else if (!destroyed) {
            b2body.setLinearVelocity(velocity);
            setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
            setRegion((TextureRegion) walkAnimation.getKeyFrame(stateTime, true));
        }
    }

    @Override
    protected void defineEnemy() {
        // Body for Gumgum
        BodyDef bdef = new BodyDef();
//        bdef.position.set(32 / FirstGame.PPM, 32 / FirstGame.PPM);
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(7 / FirstGame.PPM);

        fdef.filter.categoryBits = FirstGame.ENEMY_BIT;
        fdef.filter.maskBits = FirstGame.GROUND_BIT |
                FirstGame.COIN_BIT | FirstGame.BRICK_BIT |
                FirstGame.ENEMY_BIT | FirstGame.OBJECT_BIT |
                FirstGame.MAINCHAR_BIT;

        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);

        // Create head for Gumgum
        PolygonShape head = new PolygonShape();
        Vector2[] vertice = new Vector2[4];
        vertice[0] = new Vector2(-5, 8).scl(1 / FirstGame.PPM);
        vertice[1] = new Vector2(5, 8).scl(1 / FirstGame.PPM);
        vertice[2] = new Vector2(-3, 3).scl(1 / FirstGame.PPM);
        vertice[3] = new Vector2(3, 3).scl(1 / FirstGame.PPM);
        head.set(vertice);

        fdef.shape = head;
        head.setRadius(4 / FirstGame.PPM);
        fdef.restitution = 0.5f;
        fdef.filter.categoryBits = FirstGame.ENEMY_HEAD_BIT;
        fdef.filter.maskBits = FirstGame.GROUND_BIT |
                FirstGame.COIN_BIT | FirstGame.BRICK_BIT |
                FirstGame.ENEMY_BIT | FirstGame.OBJECT_BIT |
                FirstGame.MAINCHAR_BIT;
        b2body.createFixture(fdef).setUserData(this);

    }

    // Gumgum dissappear when stomped
    public void draw(Batch batch){
        if (!destroyed || stateTime < 1)
            super.draw(batch);
    }

    public void throwTrash(){
//        Timer timer = new Timer();
//        Timer.Task task = new Timer.Task() {
//            @Override
//            public void run() {
//                screen.spawnItem(new ItemDefinition(new Vector2(b2body.getPosition().x,
//                        b2body.getPosition().y + 16 / FirstGame.PPM), Trash.class));
//            }
//        };
//        timer.scheduleTask(task, 10, 10, 1);
//        timer.start();
        if (count < 401) {count++;};

        if (count == 400 || count == 800) {screen.spawnItem(new ItemDefinition(new Vector2(b2body.getPosition().x,
                        b2body.getPosition().y + 16 / FirstGame.PPM), Trash.class));}
    }

    @Override
    public void hitOnHead() {
        FirstGame.manager.get("sounds/splat.wav", Sound.class).play();
        Hud.addScore(500);
        setToDestroy = true;

        if(setToDestroy = true) {
            screen.spawnItem(new ItemDefinition(new Vector2(b2body.getPosition().x,
                    b2body.getPosition().y + 16 / FirstGame.PPM), Trash.class));
        }
//        System.out.println(b2body.getPosition());
    }
}
