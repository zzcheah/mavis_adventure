package com.cc.mavis.Tools;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.cc.mavis.MavisAdventure;
import com.cc.mavis.Screens.GameScreen;
import com.cc.mavis.Sprites.Mavis.Mavis;

public class Controller {

    //variables
    private Stage stage;
    private boolean upPressed, downPressed, leftPressed, rightPressed;

    private String rune;

    private SpriteDrawable emptyButton, inviButton, antiHasteButton;
    private Image useRuneImage;

    public Controller(SpriteBatch sb, final Mavis mavis, final GameScreen screen) {

        OrthographicCamera cam = new OrthographicCamera();
        //variables
        Viewport viewport = new FitViewport(2077, 1080, cam);
        stage = new Stage(viewport,sb);
        Gdx.input.setInputProcessor(stage);
        Gdx.input.setCatchBackKey(true);

        rune = "inviRune";
        ResourceManager rm = MavisAdventure.manager;

        Table table = new Table();
        table.setFillParent(true);
        table.center();

        emptyButton = new SpriteDrawable(new Sprite(rm.emptyButton));
        inviButton = new SpriteDrawable(new Sprite(rm.inviButton));
        antiHasteButton = new SpriteDrawable(new Sprite(rm.antiHasteButton));

        useRuneImage = new Image(rm.emptyButton);
        useRuneImage.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(rune.equals("inviRune"))
                    mavis.goInvi();
                else if(rune.equals("antiHasteRune")){
                    if(MavisAdventure.sfxCheck)
                        MavisAdventure.manager.antiHaste.play();
                    screen.antiHaste=true;
                    screen.antiHasteTimer=0;
                }
                rune="";
            }
        });

        Image upImg = new Image(new Texture("controller/flatDark25.png"));
        upImg.setSize(70,70);
        upImg.getColor().a = 0.5f;
        upImg.addListener(new InputListener(){
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


        Image downImg = new Image(new Texture("controller/flatDark26.png"));
        downImg.setSize(70,70);
        downImg.getColor().a = 0.5f;
        downImg.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                downPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                downPressed = false;
            }
        });



        Image rightImg = new Image(new Texture("controller/flatDark24.png"));
        rightImg.setSize(70,70);
        rightImg.getColor().a = 0.5f;
        rightImg.addListener(new InputListener(){
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



        Image leftImg = new Image(new Texture("controller/flatDark23.png"));
        leftImg.setSize(70,70);
        leftImg.getColor().a = 0.5f;
        leftImg.addListener(new InputListener(){
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

        table.pad(500,0,0,0);
        table.add(useRuneImage).align(Align.right).expandX().padRight(33).padBottom(100).colspan(4);
        table.row();
        table.add(leftImg).align(Align.bottomLeft);
        table.add(rightImg).padLeft(100).align(Align.bottomLeft);
        table.add(downImg).expandX().align(Align.right);
        table.add(upImg).padLeft(100).align(Align.bottomRight);

        stage.addActor(table);

    }

    public void update(){
        if(rune.equals("inviRune"))
            useRuneImage.setDrawable(inviButton);
        else if (rune.equals("antiHasteRune"))
            useRuneImage.setDrawable(antiHasteButton);
        else
            useRuneImage.setDrawable(emptyButton);
    }

    public void draw()
    {
        stage.draw();
    }

    public boolean isUpPressed() {
        return upPressed;
    }

    public boolean isDownPressed() {
        return downPressed;
    }

    public boolean isLeftPressed() {
        return leftPressed;
    }

    public boolean isRightPressed() {
        return rightPressed;
    }

    public void setUpPressedFalse()
    {
        upPressed = false;
    }

    public void addRune(String rune) {
        this.rune=rune;
    }
}
