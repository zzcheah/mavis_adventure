package com.cc.mavis.Tools;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.cc.mavis.MavisAdventure;
import com.cc.mavis.Screens.GameScreen;
import com.cc.mavis.Sprites.Mavis.Mavis;

import java.util.Locale;

public class TopDisplay implements Disposable {

    //variables
    public Stage stage;
    private Mavis mavis;
    private GameScreen screen;

    private Integer worldTimer;
    private float timeCount;
    private static Integer score;
    private boolean timeUp;

    private Label countdownLabel;
    private static Label scoreLabel;
    public Label readyLabel;

    private Image drunkImage;
    private Image inviImage;
    private Image antiHasteImage;

    public TopDisplay(SpriteBatch sb, final Mavis mavis, final GameScreen screen) {

        ResourceManager rm = MavisAdventure.manager;
        this.mavis = mavis;
        this.screen = screen;
        worldTimer = 300;
        timeCount = 0;
        score = 0;

        Viewport viewport = new FitViewport(2077, 1080, new OrthographicCamera());
        stage = new Stage(viewport,sb);

        Table table = new Table();
        table.top();
        table.setFillParent(true);
        countdownLabel = new Label(String.format(Locale.getDefault(),"%03d", worldTimer), new Label.LabelStyle(rm.roboto64,Color.FIREBRICK));
        scoreLabel = new Label(String.format(Locale.getDefault(),"%06d", score), new Label.LabelStyle(rm.roboto64,Color.FIREBRICK));
        Label timeLabel = new Label("TIME", new Label.LabelStyle(rm.roboto64, Color.FIREBRICK));
        Label levelLabel = new Label(Integer.toString(MavisAdventure.stageNum), new Label.LabelStyle(rm.roboto64, Color.FIREBRICK));
        Label stageLabel = new Label("STAGE", new Label.LabelStyle(rm.roboto64, Color.FIREBRICK));
        Label gemLabel = new Label("GEM", new Label.LabelStyle(rm.roboto64, Color.FIREBRICK));
        readyLabel = new Label("READY",new Label.LabelStyle(rm.roboto64,Color.FIREBRICK));

        drunkImage = new Image(rm.drunkBuff);
        inviImage = new Image(rm.inviBuff);
        antiHasteImage = new Image(rm.antiHasteBuff);

        table.add(gemLabel).expandX().padTop(70);
        table.add(stageLabel).expandX().padTop(70);
        table.add(timeLabel).expandX().padTop(70);
        table.row();
        table.add(scoreLabel).expandX();
        table.add(levelLabel).expandX();
        table.add(countdownLabel).expandX();
        table.row();
        table.add(readyLabel).colspan(3).padTop(300).expandX().padLeft(75);
        table.row();
        table.add(drunkImage).align(Align.right).padTop(250).padLeft(800);
        table.add(inviImage).padTop(250).spaceLeft(0).spaceRight(0);
        table.add(antiHasteImage).align(Align.left).padTop(250).padRight(800);
        stage.addActor(table);
    }

    public void update(float dt) {

        if(mavis.drunk)
            drunkImage.setVisible(true);
        else
            drunkImage.setVisible(false);

        if(mavis.invisible)
            inviImage.setVisible(true);
        else
            inviImage.setVisible(false);

        if(screen.antiHaste)
            antiHasteImage.setVisible(true);
        else
            antiHasteImage.setVisible(false);

        if(worldTimer>0){
            timeCount += dt;
            if(timeCount>=1){
                worldTimer--;
                countdownLabel.setText(String.format(Locale.getDefault(),"%03d", worldTimer));
                timeCount = 0;
            }
        }
        else if(!timeUp){
            timeUp=true;
            mavis.camKill=true;
            mavis.die(true);
        }

    }

    public static void addScore(int value) {
        score += value;
        scoreLabel.setText(String.format(Locale.getDefault(),"%06d", score));
    }

    public static int getScore()
    {
        return score;
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

}

