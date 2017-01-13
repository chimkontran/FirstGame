package com.vu.firstgame.Sprites;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.vu.firstgame.FirstGame;
import com.vu.firstgame.Scenes.Hud;
import com.vu.firstgame.Screens.PlayScreen;

/**
 * Created by catkutatca12 on 12/18/2016.
 */

public class Mainchar extends Sprite {

    public enum State {FALLING, JUMPING, STANDING, RUNNING, GROWING, DEAD, WIN}

    public State currentState;
    public State previousState;

    public World world;
    public Body b2body;

    private TextureRegion maincharStand;
    private TextureRegion maincharDead;
    private TextureRegion maincharWin;
    private Animation maincharRun;
    private TextureRegion maincharJump;
    private TextureRegion bigMaincharStand;
    private TextureRegion bigMaincharJump;
    private Animation bigMaincharRun;
    private Animation growMainChar;

    private boolean runningRight;
    public static boolean mainCharIsBig;
    private boolean mainCharIsDead;
    private boolean mainCharIsWin;
    private boolean runGrowAnimation;
    private boolean timeToDefineBigMainChar;
    private boolean timeToRedefineMainChar;
    BubbleText bubbleText = new BubbleText();

    private float stateTimer;

    public Mainchar(PlayScreen screen) {
        // get little mainchar images
        super(screen.getAtlas().findRegion("little_mario"));
        // default values of character
        this.world = screen.getWorld();
        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        runningRight = true;

        Array<TextureRegion> frames = new Array<TextureRegion>();
        // character RUN
        for (int i = 1; i < 4; i++)
            frames.add(new TextureRegion(screen.getAtlas().findRegion("little_mario"), i * 16, 0, 16, 16));
        maincharRun = new Animation(0.1f, frames);
        // clear frames for next sequence
        frames.clear();

        for (int i = 1; i < 4; i++)
            frames.add(new TextureRegion(screen.getAtlas().findRegion("big_mario"), i * 16, 0, 16, 32));
        bigMaincharRun = new Animation(0.1f, frames);
        // clear frames for next sequence
        frames.clear();

        // get animation for growing mainchar med -> big -> med -> big
        frames.add(new TextureRegion(screen.getAtlas().findRegion("big_mario"), 240, 0, 16, 32));
        frames.add(new TextureRegion(screen.getAtlas().findRegion("big_mario"), 0, 0, 16, 32));
        frames.add(new TextureRegion(screen.getAtlas().findRegion("big_mario"), 240, 0, 16, 32));
        frames.add(new TextureRegion(screen.getAtlas().findRegion("big_mario"), 0, 0, 16, 32));
        growMainChar = new Animation(0.2f, frames);


        // character JUMP
        maincharJump = new TextureRegion(screen.getAtlas().findRegion("little_mario"), 80, 0, 16, 16);
        bigMaincharJump = new TextureRegion(screen.getAtlas().findRegion("big_mario"), 80, 0, 16, 32);

        // character STAND
        maincharStand = new TextureRegion(screen.getAtlas().findRegion("little_mario"), 0, 0, 16, 16);
        bigMaincharStand = new TextureRegion(screen.getAtlas().findRegion("big_mario"), 0, 0, 16, 32);

        // character DEAD
        maincharDead = new TextureRegion(screen.getAtlas().findRegion("little_mario"), 96, 0, 16, 16);

        // character WIN
        maincharWin = new TextureRegion(screen.getAtlas().findRegion("little_mario"), 112, 0, 16, 16);

        defineMainChar();
        setBounds(0, 0, 16 / FirstGame.PPM, 16 / FirstGame.PPM);
        setRegion(maincharStand);
    }

    public void update(float dt) {
        if (mainCharIsBig)
            setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2 - 6 / FirstGame.PPM);
        else
            setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        setRegion(getFrame(dt));

        if (timeToDefineBigMainChar)
            defineBigMainChar();
        if (timeToRedefineMainChar)
            redefineMainChar();

        bubbleText.attach(this);
    }

    public TextureRegion getFrame(float dt) {
        currentState = getState();
        TextureRegion region;
        // Depend on STATE get a keyFrame
        switch (currentState) {
            case WIN:
                region = maincharWin;
                break;
            case DEAD:
                region = maincharDead;
                break;
            case GROWING:
                region = (TextureRegion) growMainChar.getKeyFrame(stateTimer);
                if (growMainChar.isAnimationFinished(stateTimer))
                    runGrowAnimation = false;
                break;
            case JUMPING:
                region = mainCharIsBig ? bigMaincharJump : maincharJump;
                bubbleText.attach(this);
                break;
            case RUNNING:
                region = (TextureRegion) (mainCharIsBig ? bigMaincharRun.getKeyFrame(stateTimer, true) : maincharRun.getKeyFrame(stateTimer, true));
                break;
            case FALLING:
            case STANDING:
            default:
                region = mainCharIsBig ? bigMaincharStand : maincharStand;
                break;
        }
        // Face left if run left / Face right if run right
        if ((b2body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()) {
            region.flip(true, false);
            runningRight = false;
        } else if ((b2body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()) {
            region.flip(true, false);
            runningRight = true;
        }
        // Reset Timer
        // Does currrentState = previousState? IF it does, stateTime + dt, else = 0
        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        previousState = currentState;
        return region;
    }

    public State getState() {
        if (mainCharIsWin)
            return State.WIN;
        else if (mainCharIsDead)
            return State.DEAD;
        else if (runGrowAnimation)
            return State.GROWING;
        else if (b2body.getLinearVelocity().y > 0
                || (b2body.getLinearVelocity().y < 0)
                && previousState == State.JUMPING)
            return State.JUMPING;
        else if (b2body.getLinearVelocity().y < 0)
            return State.FALLING;
        else if (b2body.getLinearVelocity().x != 0)
            return State.RUNNING;
        else
            return State.STANDING;
    }

    public boolean isBig() {
        return mainCharIsBig;
    }

    public boolean isDead() {
        return mainCharIsDead;
    }

    public boolean isWin() {
        return mainCharIsWin;
    }

    public float getStateTimer() {
        return stateTimer;
    }


    public void grow() {
        runGrowAnimation = true;
        mainCharIsBig = true;
        timeToDefineBigMainChar = true;
        setBounds(getX(), getY(), getWidth(), getHeight() * 2);
    }

    // character win
    public void win() {
        FirstGame.manager.get("sounds/yay.wav", Sound.class).play();
        mainCharIsWin = true;
        Filter filter = new Filter();
        filter.maskBits = FirstGame.MAINCHAR_BIT;
        for (Fixture fixture : b2body.getFixtureList())
            fixture.setFilterData(filter);
        b2body.applyLinearImpulse(new Vector2(5f,0), b2body.getWorldCenter(),true);
    }

    // character get hit
    public void hit() {
        if (mainCharIsBig) {
            mainCharIsBig = false;
            timeToRedefineMainChar = true;
            setBounds(getX(), getY(), getWidth(), getHeight() / 2);
            FirstGame.manager.get("sounds/scream.wav", Sound.class).play();
        } else {
            FirstGame.manager.get("sounds/scream.wav", Sound.class).play();
//            FirstGame.manager.get("sounds/failed.wav", Sound.class).play();
            mainCharIsDead = true;
            Filter filter = new Filter();
            filter.maskBits = FirstGame.NOTHING_BIT;
            for (Fixture fixture : b2body.getFixtureList())
                fixture.setFilterData(filter);
            b2body.applyLinearImpulse(new Vector2(0, 4f), b2body.getWorldCenter(), true);
        }
    }


    public void throwTrash(){
        Hud.clearTrash();
    }


    public void redefineMainChar() {
        Vector2 position = b2body.getPosition();
        world.destroyBody(b2body);
        // Create character
        BodyDef bdef = new BodyDef();
        bdef.position.set(position);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(7 / FirstGame.PPM);

        fdef.filter.categoryBits = FirstGame.MAINCHAR_BIT;
        fdef.filter.maskBits = FirstGame.GROUND_BIT |
                FirstGame.COIN_BIT | FirstGame.BRICK_BIT |
                FirstGame.ENEMY_BIT | FirstGame.OBJECT_BIT | FirstGame.BIN_BIT |
                FirstGame.ENEMY_HEAD_BIT | FirstGame.SPIKE_BIT | FirstGame.ITEM_BIT;

        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);

        // Create head sensor
        EdgeShape head = new EdgeShape();
        head.set(new Vector2(-2 / FirstGame.PPM, 6 / FirstGame.PPM),
                new Vector2(2 / FirstGame.PPM, 6 / FirstGame.PPM));
        fdef.filter.categoryBits = FirstGame.MAINCHAR_HEAD_BIT;
        fdef.shape = head;
        fdef.isSensor = true;

        b2body.createFixture(fdef).setUserData(this);
        timeToRedefineMainChar = false;
    }

    public void defineBigMainChar() {
        Vector2 currentPosition = b2body.getPosition();
        world.destroyBody(b2body);

        // Create character
        BodyDef bdef = new BodyDef();
        bdef.position.set(currentPosition.add(0, 10 / FirstGame.PPM));
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6 / FirstGame.PPM);

        fdef.filter.categoryBits = FirstGame.MAINCHAR_BIT;
        fdef.filter.maskBits = FirstGame.GROUND_BIT |
                FirstGame.COIN_BIT | FirstGame.BRICK_BIT |
                FirstGame.ENEMY_BIT | FirstGame.OBJECT_BIT | FirstGame.BIN_BIT |
                FirstGame.ENEMY_HEAD_BIT | FirstGame.SPIKE_BIT | FirstGame.ITEM_BIT;

        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);

        shape.setPosition(new Vector2(0, -14 / FirstGame.PPM));
        b2body.createFixture(fdef).setUserData(this);

        // Create head sensor
        EdgeShape head = new EdgeShape();
        head.set(new Vector2(-2 / FirstGame.PPM, 6 / FirstGame.PPM),
                new Vector2(2 / FirstGame.PPM, 6 / FirstGame.PPM));
        fdef.filter.categoryBits = FirstGame.MAINCHAR_HEAD_BIT;
        fdef.shape = head;
        fdef.isSensor = true;

        b2body.createFixture(fdef).setUserData(this);
        timeToDefineBigMainChar = false;
    }

    public void defineMainChar() {
        // Create character
        BodyDef bdef = new BodyDef();
        bdef.position.set(32 / FirstGame.PPM, 32 / FirstGame.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6 / FirstGame.PPM);

        fdef.filter.categoryBits = FirstGame.MAINCHAR_BIT;
        fdef.filter.maskBits = FirstGame.GROUND_BIT |
                FirstGame.COIN_BIT | FirstGame.BRICK_BIT |
                FirstGame.ENEMY_BIT | FirstGame.OBJECT_BIT | FirstGame.BIN_BIT |
                FirstGame.ENEMY_HEAD_BIT | FirstGame.SPIKE_BIT | FirstGame.ITEM_BIT;

        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);

        // Create head sensor
        EdgeShape head = new EdgeShape();
        head.set(new Vector2(-2 / FirstGame.PPM, 6 / FirstGame.PPM),
                new Vector2(2 / FirstGame.PPM, 6 / FirstGame.PPM));
        fdef.filter.categoryBits = FirstGame.MAINCHAR_HEAD_BIT;
        fdef.shape = head;
        fdef.isSensor = true;

        b2body.createFixture(fdef).setUserData(this);
    }
}
