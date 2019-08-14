package com.cc.mavis.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.cc.mavis.MavisAdventure;
import com.cc.mavis.ParallaxBackground.ParallaxBackground;
import com.cc.mavis.Sprites.Fire.BigFire;
import com.cc.mavis.Sprites.OtherObjects.Bullet;
import com.cc.mavis.Sprites.Enemy.Enemy;
import com.cc.mavis.Sprites.Enemy.Ghost;
import com.cc.mavis.Sprites.Enemy.Monster;
import com.cc.mavis.Sprites.InteractiveObjects.AntiHasteRune;
import com.cc.mavis.Sprites.InteractiveObjects.Beer;
import com.cc.mavis.Sprites.InteractiveObjects.Gems;
import com.cc.mavis.Sprites.InteractiveObjects.InviRune;
import com.cc.mavis.Sprites.InteractiveObjects.Love;
import com.cc.mavis.Sprites.InteractiveObjects.Treasure;
import com.cc.mavis.Sprites.OtherObjects.Key;
import com.cc.mavis.Sprites.Mavis.Mavis;
import com.cc.mavis.Sprites.Blocks.RotatingBlock;
import com.cc.mavis.Sprites.Fire.SmallFire;
import com.cc.mavis.Sprites.Blocks.VanishableBLock;
import com.cc.mavis.Tools.B2WorldCreator;
import com.cc.mavis.Tools.Controller;
import com.cc.mavis.Tools.GameworldContactListener;
import com.cc.mavis.Tools.ResourceManager;
import com.cc.mavis.Tools.TopDisplay;

public class GameScreen implements Screen {

    //variables
    private final Stage stage;
    private final ParallaxBackground pBG;
    private TextureAtlas atlas;

    private MavisAdventure game;
    private int stageNumber;
    private int level;
    private float gameTimer;
    public float antiHasteTimer;
    public boolean antiHaste;
    public int gemCount;


    //camera and viewport
    private OrthographicCamera gamecam;
    private boolean camUp;
    private final float defaultCamSpeed;
    private float camSpeed;
    private Viewport gamePort;

    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    //Box2d variables
    private World world;
    private Box2DDebugRenderer b2dr;
    private B2WorldCreator creator;

    //HUD
    private TopDisplay display;
    private Controller controller;

    //mavis
    private Mavis mavis;

    public GameScreen(MavisAdventure game){

        ResourceManager rm = MavisAdventure.manager;
        atlas = rm.atlas;
        level=1;
        camUp=false;
        gameTimer =-1;
        antiHasteTimer=0;
        gemCount=0;
        defaultCamSpeed=0.4f;
        camSpeed = defaultCamSpeed;

        this.game = game;
        gamecam = new OrthographicCamera();
        gamePort = new FitViewport(MavisAdventure.V_WIDTH/MavisAdventure.PPM,MavisAdventure.V_HEIGHT/MavisAdventure.PPM,gamecam);

        //stage for parallax BG
        FitViewport viewport = new FitViewport(1920, 1080, new OrthographicCamera());
        stage = new Stage(viewport,game.batch);

        //Tiled map variables
        TmxMapLoader mapLoader = new TmxMapLoader();
        stageNumber = MavisAdventure.stageNum;

        switch (stageNumber){
            case 1:
                map = mapLoader.load("stages/untitled1.tmx");
                pBG=new ParallaxBackground(rm.parallaxNaturePack);
                break;
            case 2:
                map = mapLoader.load("stages/untitled2.tmx");
                pBG=new ParallaxBackground(rm.parallaxForestPack);
                break;
            case 3:
                map = mapLoader.load("stages/untitled3.tmx");
                pBG=new ParallaxBackground(rm.parallaxDefaultPack);
                break;
            default:
                map = mapLoader.load("stages/untitled4.tmx");
                pBG=new ParallaxBackground(rm.parallaxCoolPack);
                break;
        }

        pBG.setSpeed(0,level);
        stage.addActor(pBG);

        renderer = new OrthogonalTiledMapRenderer(map,1 /MavisAdventure.PPM);

        gamecam.position.set(gamePort.getWorldWidth()/2, gamePort.getWorldHeight()/2,0);

        world = new World(new Vector2(0,-5),true);
        b2dr =  new Box2DDebugRenderer();
        creator = new B2WorldCreator(this);

        mavis = new Mavis(this);
        display = new TopDisplay(game.batch,mavis,this);
        controller = new Controller(game.batch,mavis,this);
        world.setContactListener(new GameworldContactListener(controller));

    }

    private void handleInput(){

        //control mavis movement
        if((controller.isRightPressed() || Gdx.input.isKeyPressed(Input.Keys.RIGHT) )||(controller.isLeftPressed() ||Gdx.input.isKeyPressed(Input.Keys.LEFT))) {

            if(mavis.drunk){
                if((controller.isLeftPressed() || Gdx.input.isKeyPressed(Input.Keys.LEFT))&& mavis.b2body.getLinearVelocity().x<=1 )
                    mavis.b2body.applyLinearImpulse(new Vector2(0.1f,0),mavis.b2body.getWorldCenter(),true);

                if((controller.isRightPressed() || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) && mavis.b2body.getLinearVelocity().x>=-1)
                    mavis.b2body.applyLinearImpulse(new Vector2(-0.1f,0),mavis.b2body.getWorldCenter(),true);
            }
            else {
                if((controller.isRightPressed() || Gdx.input.isKeyPressed(Input.Keys.RIGHT))&& mavis.b2body.getLinearVelocity().x<=1 )
                    mavis.b2body.applyLinearImpulse(new Vector2(0.1f,0),mavis.b2body.getWorldCenter(),true);

                if((controller.isLeftPressed() || Gdx.input.isKeyPressed(Input.Keys.LEFT)) && mavis.b2body.getLinearVelocity().x>=-1)
                    mavis.b2body.applyLinearImpulse(new Vector2(-0.1f,0),mavis.b2body.getWorldCenter(),true);
            }

        }
        else
            mavis.b2body.setLinearVelocity(new Vector2(0,mavis.b2body.getLinearVelocity().y));

        if(controller.isUpPressed() || Gdx.input.isKeyJustPressed(Input.Keys.UP)){
            if(MavisAdventure.sfxCheck)
                MavisAdventure.manager.wing_flap.play();
            mavis.b2body.setLinearVelocity(new Vector2(mavis.b2body.getLinearVelocity().x,1.5f));
            controller.setUpPressedFalse();
        }

        if(controller.isDownPressed() ||Gdx.input.isKeyPressed(Input.Keys.DOWN))
            mavis.b2body.setLinearVelocity(new Vector2(mavis.b2body.getLinearVelocity().x,-3));
        else
        if(mavis.b2body.getLinearVelocity().y<-1)
            mavis.b2body.setLinearVelocity(new Vector2(mavis.b2body.getLinearVelocity().x,-1));


        //restrict mavis from moving outside the camera
        if(camUp){
            if(mavis.b2body.getPosition().x>gamecam.position.x+MavisAdventure.V_WIDTH/2/MavisAdventure.PPM)
                mavis.b2body.setTransform(new Vector2(gamecam.position.x+MavisAdventure.V_WIDTH/2/MavisAdventure.PPM, mavis.b2body.getPosition().y), mavis.b2body.getAngle());
            else if(mavis.b2body.getPosition().x<gamecam.position.x-MavisAdventure.V_WIDTH/2/MavisAdventure.PPM)
                mavis.b2body.setTransform(new Vector2(gamecam.position.x-MavisAdventure.V_WIDTH/2/MavisAdventure.PPM, mavis.b2body.getPosition().y), mavis.b2body.getAngle());

        }
        else{
            if(level==1||level==3){
                if(mavis.b2body.getPosition().x>gamecam.position.x+MavisAdventure.V_WIDTH/2/MavisAdventure.PPM)
                    mavis.b2body.setTransform(new Vector2(gamecam.position.x+MavisAdventure.V_WIDTH/2/MavisAdventure.PPM, mavis.b2body.getPosition().y), mavis.b2body.getAngle());
            }
            else {
                if (mavis.b2body.getPosition().x<gamecam.position.x-MavisAdventure.V_WIDTH/2/MavisAdventure.PPM)
                    mavis.b2body.setTransform(new Vector2(gamecam.position.x-MavisAdventure.V_WIDTH/2/MavisAdventure.PPM, mavis.b2body.getPosition().y), mavis.b2body.getAngle());
            }
        }
    }

    public void update(float dt){

        gameTimer +=dt;

        if(gameTimer<3){
            if(gameTimer<0)
                display.readyLabel.setText("READY!");
            else
                display.readyLabel.setText(Integer.toString(Math.round(3-gameTimer)));
            dt=0;
        }
        else {
            pBG.setSpeed(0.1f,level);
            handleInput();
            display.readyLabel.setText(null);
        }

        if(mavis.currentState!=Mavis.State.DEAD) {
            if (mavis.drunk) {
                mavis.drunkTimer += dt;
                if (mavis.drunkTimer > 6)
                    mavis.goSober();
            }

            if (mavis.invisible) {
                mavis.inviTimer += dt;
                if (mavis.inviTimer > 5)
                    mavis.goVisible();
            }

            if (mavis.immune) {
                mavis.immuneTimer += dt;
                if (mavis.immuneTimer > 1.8f)
                    mavis.goMortal();
                else if (((int) (mavis.immuneTimer * 5) % 2) == 0)
                    mavis.setAlpha(0.5f);
                else mavis.setAlpha(1);
            }

            if (antiHaste) {
                camSpeed = 0.08f;
                antiHasteTimer += dt;
                if (antiHasteTimer > 8) {
                    antiHasteTimer = 0;
                    antiHaste = false;
                    camSpeed = defaultCamSpeed;
                }
            }
        }

        world.step(1/60f,6,2);

        for(Bullet bullet :creator.getBullets())
            bullet.update(dt);
        for(Key key :creator.getKey())
            key.update(dt);
        for(VanishableBLock block:creator.getBLocks())
            block.update(dt);
        for(RotatingBlock rblock:creator.getRotatingBLocks())
            rblock.update(dt);
        for(Enemy enemy:creator.getEnemy())
        {
            enemy.update(dt);

            if(level%2!=0){
                if((enemy instanceof Monster && !((Monster)enemy).detectMavis && (enemy.getX()< mavis.getX()+ 70 /MavisAdventure.PPM  || ((Monster)enemy).getInCoolDownNow() ))&&enemy.properties.get("level",int.class)==level)
                {
                    if(((Monster) enemy).cooldown(dt))
                        ((Monster)enemy).b2body.setAwake(false);
                    else
                        ((Monster)enemy).charge(mavis.getX(),mavis.getY());

                }
                else if((enemy instanceof Ghost && (enemy.getX()< mavis.getX()+50/MavisAdventure.PPM||((Ghost)enemy).getInCoolDownNow()))&&enemy.properties.get("level",int.class)==level)
                {
                    if(((Ghost) enemy).cooldown(dt))
                        ((Ghost) enemy).b2body.setAwake(false);
                    else
                        ((Ghost)enemy).charge(mavis.getX(),mavis.getY());

                }
            }
            else if(level%2==0){
                if((enemy instanceof Monster && !((Monster)enemy).detectMavis && (enemy.getX()> mavis.getX()- 70 /MavisAdventure.PPM  || ((Monster)enemy).getInCoolDownNow() ))&&enemy.properties.get("level",int.class)==level)
                {
                    if(((Monster) enemy).cooldown(dt))
                        ((Monster)enemy).b2body.setAwake(false);
                    else
                        ((Monster)enemy).charge(mavis.getX(),mavis.getY());

                }
                else if((enemy instanceof Ghost && (enemy.getX()> mavis.getX()-70/MavisAdventure.PPM||((Ghost)enemy).getInCoolDownNow()))&&enemy.properties.get("level",int.class)==level)
                {
                    if(((Ghost) enemy).cooldown(dt))
                        ((Ghost) enemy).b2body.setAwake(false);
                    else
                        ((Ghost)enemy).charge(mavis.getX(),mavis.getY());

                }
            }

        }
        for(SmallFire sfire:creator.getSmallFire())
            sfire.update(dt);
        for(BigFire bfire:creator.getBigFire())
            bfire.update(dt);

        display.update(dt);
        controller.update();

        updateCam(dt);
        gamecam.update();

        mavis.update(dt);

        renderer.setView(gamecam);


    }

    private void updateCam(float dt) {

        if(mavis.currentState != Mavis.State.DEAD)
        {
            switch (level){
                case 1:
                    if(gamecam.position.x<14)
                        gamecam.position.x += camSpeed * dt;
                    else{
                        gamecam.position.x = 14;
                        pBG.setSpeed(0,level);
                        if(stageNumber!=1) {
                            camUp=true;
                            pBG.setSpeed(0,level);
                            gamecam.position.y += camSpeed * dt /2;
                            if (gamecam.position.y > 3.12) {
                                level++;
                                camUp=false;
                                gamecam.position.y = 3.12f;
                            }
                        }
                    }
                    break;
                case 2:
                    if(gamecam.position.x>2)
                        gamecam.position.x -= camSpeed * dt;
                    else{
                        gamecam.position.x = 2;
                        pBG.setSpeed(0,level);
                        if(stageNumber!=2){
                            camUp=true;
                            pBG.setSpeed(0,level);
                            gamecam.position.y += camSpeed * dt/2;
                            if(gamecam.position.y > 5.2) {
                                level++;
                                camUp=false;
                                gamecam.position.y = 5.2f;
                            }
                        }
                    }
                    break;
                case 3:
                    if(gamecam.position.x<14)
                        gamecam.position.x += camSpeed * dt;
                    else{
                        gamecam.position.x = 14;
                        pBG.setSpeed(0,level);
                        if(stageNumber!=3) {
                            camUp=true;
                            pBG.setSpeed(0,level);
                            gamecam.position.y += camSpeed * dt/2;
                            if (gamecam.position.y > 7.28) {
                                level++;
                                camUp=false;
                                gamecam.position.y = 7.28f;
                            }
                        }
                    }
                    break;
                default:
                    if(gamecam.position.x>2)
                        gamecam.position.x -= camSpeed * dt;
                    else{
                        gamecam.position.x = 2;
                        pBG.setSpeed(0,level);
                        if(stageNumber!=4){
                            camUp=true;
                            pBG.setSpeed(0,level);
                            gamecam.position.y += camSpeed * dt/2;
                            if(gamecam.position.y > 9.36) {
                                level++;
                                camUp=false;
                                gamecam.position.y = 9.36f;
                            }
                        }
                    }
                    break;
            }
        }


    }

    @Override
    public void render(float dt) {

        update(dt);

        //clear screen
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //stage
        if(mavis.currentState==Mavis.State.DEAD)
            pBG.setSpeed(0,level);
        stage.draw();

        //map render
        renderer.render();

        //box2 physic box
//        b2dr.render(world, gamecam.combined);

        //game camera
        game.batch.setProjectionMatrix(gamecam.combined);

        //batch draw
        game.batch.begin();

        for(Gems gem : creator.getGems())
            gem.draw(game.batch);
        for(Treasure treasure : creator.getTreasures())
            treasure.draw(game.batch);
        for(AntiHasteRune rune : creator.getAntiHasteRunes())
            rune.draw(game.batch);
        for(InviRune rune : creator.getInviRunes())
            rune.draw(game.batch);
        for(Beer beer : creator.getBeers())
            beer.draw(game.batch);
        for(Love love : creator.getLoves())
            love.draw(game.batch);

        for(Bullet bullet :creator.getBullets())
            bullet.draw(game.batch);
        for(Key key :creator.getKey())
            key.draw(game.batch);
        for(VanishableBLock block:creator.getBLocks())
            block.draw(game.batch);
        for(RotatingBlock rblock:creator.getRotatingBLocks())
            rblock.draw(game.batch);
        for(Enemy enemy:creator.getEnemy())
            enemy.draw(game.batch);
        for(SmallFire sfire:creator.getSmallFire())
            sfire.draw(game.batch);
        for(BigFire bfire:creator.getBigFire())
            bfire.draw(game.batch);
        mavis.draw(game.batch);

        game.batch.end();
        display.stage.draw();
        controller.draw();

        if(mavis.currentState==Mavis.State.DEAD&& mavis.getDeadStateTime()> (mavis.hitByJohnCena? 7 : 2 )){
            game.setScreen(new MavisDeadScreen(game, TopDisplay.getScore()));
            this.dispose();
        }

        if(mavis.won){
            MavisAdventure.stageNum++;
            if(MavisAdventure.stageNum<5){
                if(MavisAdventure.prefs.getInteger("highestStage")<MavisAdventure.stageNum)
                    MavisAdventure.prefs.putInteger("highestStage",MavisAdventure.stageNum);
                game.setScreen(new PassStageScreen(game,this));
            }
            else{
                MavisAdventure.stageNum=4;
                game.setScreen(new EndGameScreen(game));
            }
            this.dispose();
        }
    }

    @Override
    public void show() {
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
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
        MavisAdventure.prefs.flush();
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        display.dispose();
    }

    public World getWorld() {
        return world;
    }

    public TiledMap getMap() {
        return map;
    }

    public TextureAtlas getAtlas() { return  atlas; }

    public OrthographicCamera getGamecam() {
        return gamecam;
    }

    public int getLevel() { return level; }

    public B2WorldCreator getCreator(){ return creator;}

}
