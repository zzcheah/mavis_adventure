package com.cc.mavis.Screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.cc.mavis.MavisAdventure;
import com.cc.mavis.Tools.TopDisplay;

class PassStageScreen extends AbstractMenu {

    PassStageScreen(final MavisAdventure game, GameScreen screen) {
        super(game);

        greenpil = new Label.LabelStyle(rm.greenpil,Color.WHITE);
        roboto64 = new Label.LabelStyle(rm.roboto64,Color.WHITE);

        Table table = new Table();
        table.center();
        table.setFillParent(true);

        final Label congratLabel = new Label("you passed this stage", greenpil);
        Label nextStageLabel = new Label("Goto next Stage", roboto64);
        nextStageLabel.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(MavisAdventure.stageNum<5){
                    game.setScreen(new GameScreen(game));
                    dispose();
                }
                dispose();
            }
        });
        Label mainMenuLabel = new Label("Back to Main Menu", roboto64);
        mainMenuLabel.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainMenu(game));
                dispose();
            }
        });

        Image image1, image2,image3;

        if(TopDisplay.getScore()/100==screen.gemCount){
            image1= new Image(rm.filledStar);
            image2= new Image(rm.filledStar);
            image3= new Image(rm.filledStar);
        }
        else if(TopDisplay.getScore()/screen.gemCount>67){
            image1= new Image(rm.filledStar);
            image2= new Image(rm.filledStar);
            image3= new Image(rm.emptyStar);
        }
        else if(TopDisplay.getScore()/screen.gemCount>33){
            image1= new Image(rm.filledStar);
            image2= new Image(rm.emptyStar);
            image3= new Image(rm.emptyStar);
        }
        else{
            image1= new Image(rm.emptyStar);
            image2= new Image(rm.emptyStar);
            image3= new Image(rm.emptyStar);
        }

        table.add(congratLabel).expandX().padBottom(20).colspan(3);
        table.row();
        table.add(image1).padTop(20).align(Align.right).padLeft(700);
        table.add(image2).padTop(20);
        table.add(image3).padTop(20).align(Align.left).padRight(700);
        table.row();
        table.add(nextStageLabel).expandX().padTop(40).colspan(3);
        table.row();
        table.add(mainMenuLabel).expandX().padTop(20).colspan(3);
        table.row();

        stage.addActor(table);

    }

}
