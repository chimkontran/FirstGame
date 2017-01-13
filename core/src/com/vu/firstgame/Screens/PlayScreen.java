package com.vu.firstgame.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.vu.firstgame.Controller;
import com.vu.firstgame.FirstGame;
import com.vu.firstgame.Items.Apple;
import com.vu.firstgame.Items.Item;
import com.vu.firstgame.Items.ItemDefinition;
import com.vu.firstgame.Items.Trash;
import com.vu.firstgame.Scenes.Hud;
import com.vu.firstgame.Sprites.Enemy;
import com.vu.firstgame.Sprites.Mainchar;
import com.vu.firstgame.Tools.B2WorldCreator;
import com.vu.firstgame.Tools.WorldContactListener;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by catkutatca12 on 12/17/2016.
 */

public class PlayScreen implements Screen {

    public FirstGame game;
    private TextureAtlas atlas;
    Texture texture;
    public static OrthographicCamera gamecam;
    private Viewport gameport;
    private Hud hud;
    public static float velocity = 0.1f;

    // Tiled map variables
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    // Box2d variables
    private World world;
    private Box2DDebugRenderer b2dr;
    private B2WorldCreator creator;

    // Sprites
    private Mainchar player;

    // Music
    private Music music;

    Controller controller;
    private Array<Item> items;
    private LinkedBlockingQueue<ItemDefinition> itemToSpawn;

    public PlayScreen(FirstGame game) {

        controller = new Controller();
        atlas = new TextureAtlas("Mario_and_Enemies.pack");

        this.game = game;
        texture = new Texture("cate.jpg");
        // Camera follow MainCharacter
        gamecam = new OrthographicCamera();
        // Maintain virtual aspect ratio despite screen size
        gameport = new FitViewport(FirstGame.V_WIDTH / FirstGame.PPM, FirstGame.V_HEIGHT / FirstGame.PPM, gamecam);
        hud = new Hud(game.batch);

        mapLoader = new TmxMapLoader();
        map = mapLoader.load("level1.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / FirstGame.PPM);

        // Set game Camera to center at the start
        gamecam.position.set(gameport.getWorldWidth() / 2,
                gameport.getWorldHeight() / 2, 0);

        // Create Box2D world
        world = new World(new Vector2(0, -10), true);
        // Draw debug lines of box2d world
        b2dr = new Box2DDebugRenderer();

        creator = new B2WorldCreator(this);

        // Create player (Main character)
        player = new Mainchar(this);

        world.setContactListener(new WorldContactListener());

        music = FirstGame.manager.get("sounds/numberone.ogg", Music.class);
        music.setLooping(true);
        music.play();

        items = new Array<Item>();
        itemToSpawn = new LinkedBlockingQueue<ItemDefinition>();

    }

    public void spawnItem(ItemDefinition itemDefinition) {
        itemToSpawn.add(itemDefinition);
    }

    public void handleSpawningItems() {
        if (!itemToSpawn.isEmpty()) {
            ItemDefinition itemDefinition = itemToSpawn.poll();
            if (itemDefinition.type == Apple.class) {
                items.add(new Apple(this, itemDefinition.position.x, itemDefinition.position.y));
            } else if (itemDefinition.type == Trash.class) {
                items.add(new Trash(this, itemDefinition.position.x, itemDefinition.position.y));
            }
        }
    }

    public TextureAtlas getAtlas() {
        return atlas;
    }

    @Override
    public void show() {

    }

    public void handleInput(float dt) {
        if (player.currentState != Mainchar.State.WIN) {
            if (player.currentState != Mainchar.State.DEAD) {
                jumping();

//                if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.b2body.getLinearVelocity().x <= 1.5)
                if (controller.isRightPressed() && player.b2body.getLinearVelocity().x <= 1.5)
                    player.b2body.applyLinearImpulse(new Vector2(velocity, 0), player.b2body.getWorldCenter(), true);

//                if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.b2body.getLinearVelocity().x >= -1.5)
                if (controller.isLeftPressed() && player.b2body.getLinearVelocity().x >= -1.5)
                    player.b2body.applyLinearImpulse(new Vector2(-velocity, 0), player.b2body.getWorldCenter(), true);
            }
        }
    }

    public void jumping()
    {
//        if(Gdx.input.isKeyJustPressed(Input.Keys.UP) &&
//                (player.getState() == Mainchar.State.STANDING || player.getState() == Mainchar.State.RUNNING)){
        if (controller.isUpPressed() &&
                (player.getState() == Mainchar.State.STANDING || player.getState() == Mainchar.State.RUNNING)){
            player.b2body.applyLinearImpulse(new Vector2(0, 4f), player.b2body.getWorldCenter(), true);
        }
    }

    public void update(float dt) {
        // Handle user input
        handleInput(dt);
        handleSpawningItems();

        // Timestep, velocity, position
        world.step(1 / 60f, 6, 2);

        player.update(dt);
        for (Enemy enemy : creator.getGumgums()) {
            enemy.update(dt);
            if (enemy.getX() < player.getX() + 224 / FirstGame.PPM)
                enemy.b2body.setActive(true);
        }

        for (Item item : items)
            item.update(dt);


        hud.update(dt);

        // attach gamecam to player.x
        if (player.currentState != Mainchar.State.DEAD) {
            gamecam.position.x = player.b2body.getPosition().x;
        }

        // Update gamecam
        gamecam.update();
        renderer.setView(gamecam);
    }

    @Override
    public void render(float delta) {
        update(delta);

        // Game screen = black
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Render game Map
        renderer.render();
        // Render Box2D Debug
        b2dr.render(world, gamecam.combined);
        // render controller
        controller.draw();

        game.batch.setProjectionMatrix(gamecam.combined);
        game.batch.begin();
        player.draw(game.batch);

        for (Enemy enemy : creator.getGumgums())
            enemy.draw(game.batch);
        for (Item item : items)
            item.draw(game.batch);

        game.batch.end();

        // Draw what the HUD camera sees
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();

        if (gameOver()) {
            game.setScreen(new GameOverScreen(game));
            dispose();
        }

        if (gameWin()) {
            game.setScreen(new WinScreen(game));
            dispose();
        }
    }

    public boolean gameOver() {
        if (player.currentState == Mainchar.State.DEAD && player.getStateTimer() > 3) {
            return true;
        }
        return false;
    }

    public boolean gameWin() {
        if (player.currentState == Mainchar.State.WIN && player.getStateTimer() > 1) {
            return true;
        }
        return false;
    }

    @Override
    public void resize(int width, int height) {
        gameport.update(width, height);
        controller.resize(width, height);
    }

    public TiledMap getMap() {
        return map;
    }

    public World getWorld() {
        return world;
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        hud.dispose();
    }
}
