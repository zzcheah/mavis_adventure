package com.cc.mavis.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.cc.mavis.MavisAdventure;
import com.cc.mavis.ParallaxBackground.ParallaxBackground;

public class MainMenu extends AbstractMenu {

    public MainMenu(final MavisAdventure game) {
        super(game);

        //parallax bg
        ParallaxBackground pBG = new ParallaxBackground(rm.parallaxDefaultPack);

        Table table = new Table();
        table.center();
        table.setFillParent(true);

        Label gameTitle = new Label("Mavis: Treasure Seeker", littlebird84);
        Label startGameLabel = new Label("Start Game", impactlabel64);
        startGameLabel.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen(game));
                dispose();
            }

        });

        Label selectStageLabel = new Label("Select Stage", impactlabel64);
        selectStageLabel.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new SelectStageScreen(game));
                dispose();
            }
        });

        Label settingLabel = new Label("Setting", impactlabel64);
        settingLabel.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new SettingScreen(game));
                dispose();
            }
        });

        Label exitGameLabel = new Label("Exit Game", impactlabel64);
        exitGameLabel.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
                dispose();
            }
        });

        table.add(gameTitle).expandX().padBottom(30f);
        table.row();
        table.add(startGameLabel).expandX().padTop(60f);
        table.row();
        table.add(selectStageLabel).expandX().padTop(60f);
        table.row();
        table.add(settingLabel).expandX().padTop(60f);
        table.row();
        table.add(exitGameLabel).expandX().padTop(60f);

        stage.addActor(pBG);
        stage.addActor(table);
    }

}
