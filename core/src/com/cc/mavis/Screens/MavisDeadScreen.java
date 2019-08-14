package com.cc.mavis.Screens;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.cc.mavis.MavisAdventure;
import com.cc.mavis.ParallaxBackground.ParallaxBackground;

class MavisDeadScreen extends AbstractMenu {

    MavisDeadScreen(final MavisAdventure game, int score) {
        super(game);
        //parallax bg
        ParallaxBackground pBG = new ParallaxBackground(rm.parallaxDesertPack);

        Table table = new Table();
        table.center();
        table.setFillParent(true);

        Label deadLabel = new Label("you died", greenpil);
        Label scoreLabel = new Label("Score: "+ Integer.toString(score), impactlabel64);
        Label retryLabel = new Label("Retry", roboto64);
        retryLabel.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen(game));
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

        table.add(deadLabel).expandX().padBottom(20);
        table.row();
        table.add(scoreLabel).expandX().padTop(20).padBottom(20);
        table.row();
        table.add(retryLabel).expandX().padTop(20);
        table.row();
        table.add(mainMenuLabel).expandX().padTop(20);
        table.row();

        stage.addActor(pBG);
        stage.addActor(table);
    }

}
