package com.vu.firstgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by Vu on 1/5/2017.
 */

public class Controller {
    Viewport viewport;
    Stage stage;
    boolean leftPressed, rightPressed;
    boolean upPressed = false;
    OrthographicCamera camera;

    public Controller() {
        camera = new OrthographicCamera();
        viewport = new FitViewport(FirstGame.V_WIDTH, FirstGame.V_HEIGHT, camera);
        stage = new Stage(viewport, FirstGame.batch);
        Gdx.input.setInputProcessor(stage);

        // CREATE GO LEFT BUTTON
        Image imgLeft = new Image(new Texture("leftButton.png"));
        imgLeft.setSize(50, 50);
        imgLeft.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                leftPressed = true;
                return true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                leftPressed = false;
            }
        });

        // CREATE GO RIGHT BUTTON
        Image imgRight = new Image(new Texture("rightButton.png"));
        imgRight.setSize(50, 50);
        imgRight.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                rightPressed = true;
                return true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                rightPressed = false;
            }
        });

        // CREATE JUMP BUTTON
        final Image imgJump = new Image(new Texture("jumpButton.png"));
        imgJump.setSize(50, 50);
        imgJump.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                upPressed = true;
                return true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                upPressed = false;
            }
        });

        Table table = new Table();
        table.left().bottom();
        table.add(imgLeft).size(imgLeft.getWidth(), imgLeft.getHeight());
        table.add(imgRight).size(imgLeft.getWidth(), imgLeft.getHeight());
        table.add().padRight((float)200);
        table.right().bottom();
        table.add(imgJump).size(imgLeft.getWidth(), imgLeft.getHeight());

        table.pack();
        stage.addActor(table);
    }

    public void draw(){
        stage.draw();
    }

    public void resize(int width, int height){
        viewport.update(width, height);
    }

    public boolean isUpPressed() {
        return upPressed;
    }

    public void setUpPressed(boolean upPressed) {
        this.upPressed = upPressed;
    }

    public boolean isLeftPressed() {
        return leftPressed;
    }

    public void setLeftPressed(boolean leftPressed) {
        this.leftPressed = leftPressed;
    }

    public boolean isRightPressed() {
        return rightPressed;
    }

    public void setRightPressed(boolean rightPressed) {
        this.rightPressed = rightPressed;
    }
}
