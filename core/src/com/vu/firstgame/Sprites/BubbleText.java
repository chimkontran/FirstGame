package com.vu.firstgame.Sprites;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.vu.firstgame.Scenes.Hud;

/**
 * Created by Vu on 1/12/2017.
 */

public class BubbleText extends Sprite {

    int count = 0;
    float x, y;
    double bubbleWidth = 40;
    double bubbleHeight = 20;

    String text1 = "GREETING! WELCOME TO THE GAME";
    String text2 = "THE GOAL IS TO COLLECT TRASH";
    String text3 = "THE MORE YOU HAVE, THE SLOWER YOU MOVE";
    String text4 = "PUT ALL TRASH AWAY BY TOUCHING GREEN BINS";
    String text5 = "JUMPING ON ENEMY HEAD TO KILL THEM";
    String text6 = "PICK UP THEIR TRASH AND PUT THEM AWAY";

//    Array<String> texts = {text1, text2, text3, text4, text5, text6};
    String[] texts = {text1, text2, text3, text4, text5, text6};
    Label label = new Label("", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
    public void attach (final Sprite sprite){
        x = sprite.getOriginX() + 80;
        y = (sprite.getY()+(float)0.45)*100;
        if (count < texts.length*150 + 1) {
            count += 1;
        }

        for (int i = 0; i < texts.length; i++) {
            label.setPosition(x, y);
            if (count == i*150)
            label.setText(texts[i]);
            label.setColor(Color.BLACK);
            label.setFontScale((float) 0.6, (float) 0.6);

            Hud.stage.addActor(label);

            if (count == texts.length*150) {
                Hud.stage.getActors().removeValue(label, true);
                count = 0;
                break;
            }
        }
    }
}
