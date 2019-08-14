package com.cc.mavis.Screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.cc.mavis.MavisAdventure;

public class EndGameScreen extends AbstractMenu{
    private float timer;
    private Table table;
    private Label text;
    private Label mainMenuLabel;
    private int sequence;

    EndGameScreen(final MavisAdventure game) {
        super(game);
        timer=-2;
        sequence=0;

        littlebird84 = new Label.LabelStyle(rm.littlebird84,Color.WHITE);


        table = new Table();
        table.center();
        table.setFillParent(true);

        text = new Label("Mavis has found its treasure!\nThanks for playing the game!", littlebird84);
        text.getColor().a = 0.0f;
        text.addAction(Actions.sequence(Actions.fadeIn(2.0f),Actions.delay(2), Actions.fadeOut(2.0f)));

        mainMenuLabel = new Label("Back to Main Menu", littlebird84);
        mainMenuLabel.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainMenu(game));
                dispose();
            }
        });

        table.add(text);
        stage.addActor(table);

    }

    @Override
    public void render(float dt) {
        timer+=dt;
        if(timer>4){
            table.clearChildren();
            sequence++;
            if(sequence<7) {
                switch (sequence) {
                    case 1:
                        text = new Label("Developers: ", littlebird84);
                        break;
                    case 2:
                        text = new Label("Lee Yuan Hooi", littlebird84);
                        break;
                    case 3:
                        text = new Label("Yee Boon How", littlebird84);
                        break;
                    case 4:
                        text = new Label("Wong Chee Cing", littlebird84);
                        break;
                    case 5:
                        text = new Label("Cheah Zhong Zhi", littlebird84);
                        break;
                    case 6:
                        text = new Label("Special thanks to Brent Aureli", littlebird84);
                        break;
                }
                table.add(text);
                text.getColor().a = 0.0f;
                text.addAction(Actions.sequence(Actions.fadeIn(2.0f), Actions.fadeOut(2.0f)));

            }
            else{ //finished credit scene
                table.add(mainMenuLabel);
                mainMenuLabel.addAction(Actions.sequence(Actions.fadeIn(2.0f)));
            }
            timer = 0;
        }


        super.render(dt);
    }

}
