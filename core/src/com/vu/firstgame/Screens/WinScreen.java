package com.vu.firstgame.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.vu.firstgame.FirstGame;

/**
 * Created by Vu on 1/5/2017.
 */

public class WinScreen implements Screen {
    private Viewport viewport;
    private Stage stage;

    private Game game;

    public WinScreen(Game game) {
        this.game = game;
        viewport = new FitViewport(FirstGame.V_WIDTH, FirstGame.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, ((FirstGame) game).batch);

        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.WHITE);
        Table table = new Table();
        table.center();
        table.setFillParent(true);

        Label winLabel = new Label("YOU WON", font);
        Label playAgainLabel = new Label("CLICK TO PLAY AGAIN", font);

        table.add(winLabel).expandX();
        table.row();
        table.add(playAgainLabel).expandX().padTop(10f);

        stage.addActor(table);
        FirstGame.manager.get("sounds/yay.wav", Sound.class).play();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        if (Gdx.input.justTouched()) {
            game.setScreen(new PlayScreen((FirstGame) game));
            dispose();
        }
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

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
        stage.dispose();
    }
}
