package com.cc.mavis.Screens;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.cc.mavis.MavisAdventure;
import com.cc.mavis.ParallaxBackground.ParallaxBackground;

class SelectStageScreen extends AbstractMenu {

    SelectStageScreen(final MavisAdventure game) {
        super(game);

        //parallax bg
        ParallaxBackground pBG = new ParallaxBackground(rm.parallaxNaturePack);

        Table table = new Table();
        table.center();
        table.setFillParent(true);

        Label gameTitle = new Label("Mavis: Treasure Seeker", littlebird84);

        Array<Label> stageLabels = new Array<Label>();

        for(int i=0;i<4; i++){
            Label label = new Label("Stage "+(i+1),impactlabel64);
            final int finalI = i;
            label.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    MavisAdventure.stageNum= finalI +1;
                    game.setScreen(new GameScreen(game));
                    dispose();
                }
            });
            if(i+1>MavisAdventure.prefs.getInteger("highestStage"))
                label.setVisible(false);
            stageLabels.add(label);
        }

        table.add(gameTitle).expandX().padBottom(20f);
        table.row();
        for(Label label: stageLabels){
            table.add(label).expandX().padTop(60f);
            table.row();
        }

        backButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainMenu(game));
            }
        });


        stage.addActor(pBG);
        stage.addActor(table);
        stage.addActor(backButton);
    }

}
