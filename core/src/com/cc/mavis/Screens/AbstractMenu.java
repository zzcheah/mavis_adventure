package com.cc.mavis.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.cc.mavis.MavisAdventure;
import com.cc.mavis.Tools.ResourceManager;

public abstract class AbstractMenu implements Screen {

    //variables
    protected MavisAdventure game;
    protected Stage stage;
    private Viewport gamePort;
    protected ResourceManager rm;

    Label.LabelStyle greenpil;
    Label.LabelStyle roboto64;
    Label.LabelStyle littlebird84;
    Label.LabelStyle impactlabel64;
    protected TextureAtlas atlas;

    Image backButton;

    AbstractMenu(MavisAdventure game){

        this.game = game;
        rm = MavisAdventure.manager;
        atlas = rm.atlas;
        gamePort = new FitViewport(2077, 1080, new OrthographicCamera());
        stage = new Stage(gamePort, game.batch);
        Gdx.input.setInputProcessor(stage);
        Gdx.input.setCatchBackKey(true);

        greenpil = new Label.LabelStyle(rm.greenpil,Color.BLACK);
        roboto64 = new Label.LabelStyle(rm.roboto64,Color.BLACK);
        littlebird84 = new Label.LabelStyle(rm.littlebird84,Color.BLACK);
        impactlabel64 = new Label.LabelStyle(rm.impactlabel64,Color.BLACK);

        backButton = new Image(rm.backButton);
        backButton.setPosition(1900,900);
    }

    @Override
    public void render(float dt) {
        //clear screen and stage act
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
    }

    @Override
    public void show() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
        MavisAdventure.prefs.flush();
    }

}
