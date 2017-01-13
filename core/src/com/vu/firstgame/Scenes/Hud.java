package com.vu.firstgame.Scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.vu.firstgame.FirstGame;
import com.vu.firstgame.Screens.PlayScreen;

/**
 * Created by catkutatca12 on 12/17/2016.
 */

public class Hud implements Disposable{
    public static Stage stage;
    public static Viewport viewport;

    private Integer worldTimer;
    private static Integer score;
    private static Integer trash;
    private float timeCount;

    Label countdownLabel;
    static Label scoreLabel;
    static Label trashLabel;
    Label trashCountLabel;
    Label timeLabel;
    Label levelLabel;
    Label worldLabel;
    Label firstgameLabel;

    public Hud(SpriteBatch spriteBatch) {
        worldTimer = 300;
        timeCount = 0;
        score = 0;
        trash = 0;
        viewport = new FitViewport(FirstGame.V_WIDTH, FirstGame.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, spriteBatch);

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        countdownLabel = new Label(String.format("%03d", worldTimer), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        scoreLabel = new Label(String.format("%06d", score), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        trashCountLabel = new Label("TRASH :", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        trashLabel = new Label(String.format("%06d", trash), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        timeLabel = new Label("TIME", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        levelLabel = new Label("LEVEL 1", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        worldLabel = new Label("WORLD", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        firstgameLabel = new Label("FIRST GAME", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        table.add(firstgameLabel).expandX().padTop(10);
        table.add(worldLabel).expandX().padTop(10);
        table.add(timeLabel).expandX().padTop(10);
        table.add(trashCountLabel).padTop(10);
        table.row();
        table.add(scoreLabel).expandX();
        table.add(levelLabel).expandX();
        table.add(countdownLabel).expandX();
        table.add(trashLabel).expandX();

        stage.addActor(table);
    }

    // Count down the time
    public void update(float dt){
        timeCount += dt;
        if (timeCount >= 1){
            worldTimer --;
            countdownLabel.setText(String.format("%03d", worldTimer));
            timeCount = 0;
        }
    }

    public static void addScore(int value){
        score += value;
        scoreLabel.setText(String.format("%06d", score));
    }

    public static void addTrash(int value){
        trash += value;
        trashLabel.setText(String.format("%06d", trash));
        switch (trash){
            case 1: {
                PlayScreen.velocity = PlayScreen.velocity - 0.015f;
                System.out.println("A");
                break;
            }
            case 2: {
                PlayScreen.velocity = PlayScreen.velocity - 0.015f;
                System.out.println("B");
                break;
            }
            case 3: {
                PlayScreen.velocity = PlayScreen.velocity - 0.015f;
                System.out.println("C");
                break;
            }
            case 4: {
                PlayScreen.velocity = PlayScreen.velocity - 0.015f;
                System.out.println("D");
                break;
            }
            case 5: {
                PlayScreen.velocity = PlayScreen.velocity - 0.01f;
                System.out.println("E");
                break;
            }
        }
    }

    public static void clearTrash(){
        addScore(trash*1000);
        trash = 0;
        PlayScreen.velocity = 0.1f;
        trashLabel.setText(String.format("%06d", trash));
        System.out.println(score);
        return;
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
