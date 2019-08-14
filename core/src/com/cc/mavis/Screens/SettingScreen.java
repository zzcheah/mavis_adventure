package com.cc.mavis.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import com.cc.mavis.MavisAdventure;
import com.cc.mavis.ParallaxBackground.ParallaxBackground;

class SettingScreen extends AbstractMenu{

    //variables
    private Image bgmCheck, sfxCheck;
    private SpriteDrawable unchecked, checked;

    SettingScreen(final MavisAdventure game) {
        super(game);

        ParallaxBackground pBG = new ParallaxBackground(rm.parallaxNaturePack);

        backButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainMenu(game));
            }
        });

        Table table = new Table();
        table.center();
        table.setFillParent(true);

        unchecked = new SpriteDrawable(new Sprite(rm.uncheckCB));
        checked = new SpriteDrawable(new Sprite(rm.checkedCB));

        if(MavisAdventure.bgmCheck)
            bgmCheck = new Image(checked);
        else
            bgmCheck = new Image(unchecked);

        if(MavisAdventure.sfxCheck)
            sfxCheck = new Image(checked);
        else
            sfxCheck = new Image(unchecked);

        bgmCheck.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(MavisAdventure.bgmCheck){
                    bgmCheck.setDrawable(unchecked);
                    MavisAdventure.bgm.stop();
                }
                else {
                    bgmCheck.setDrawable(checked);
                    MavisAdventure.bgm.play();
                }
                MavisAdventure.bgmCheck =!MavisAdventure.bgmCheck;
            }
        });

        sfxCheck.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(MavisAdventure.sfxCheck)
                    sfxCheck.setDrawable(unchecked);
                else
                    sfxCheck.setDrawable(checked);
                MavisAdventure.sfxCheck =!MavisAdventure.sfxCheck;
            }
        });

        final Label settingLabel = new Label("Setting", littlebird84);
        final Label bgmLabel = new Label("Background Music: ", impactlabel64);
        final Label sfxLabel = new Label("Sound Effects: ", impactlabel64);
        Label feedbackLabel = new Label("Feedback", impactlabel64);
        feedbackLabel.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.net.openURI("https://docs.google.com/forms/d/e/1FAIpQLScl1PivsFn0OI5LBsfxdPQ0x8gAUscREyiY8H2eEk40d-SOjA/viewform?usp=pp_url");
            }
        });

        table.add(settingLabel).padBottom(30f);
        table.row();
        table.add(bgmLabel).padTop(50f).align(Align.left);
        table.add(bgmCheck).padTop(50f);
        table.row();
        table.add(sfxLabel).padTop(50f).align(Align.left);
        table.add(sfxCheck).padTop(50f);
        table.row();
        table.add(feedbackLabel).padTop(55f);

        stage.addActor(pBG);
        stage.addActor(table);
        stage.addActor(backButton);

    }

}
