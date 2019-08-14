package com.cc.mavis.Sprites.Mavis;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.cc.mavis.MavisAdventure;
import com.cc.mavis.Screens.GameScreen;

public class Mavis extends Sprite {



    public enum State { DEFAULT, JUMPING ,DEAD }
    public State currentState;
    private State previousState;
    private float stateTimer;
    public boolean won;
    public boolean drunk;
    public boolean immune;
    public float drunkTimer;
    public boolean invisible;
    public boolean hitByJohnCena;
    public float inviTimer;
    public float immuneTimer;
    public World world;
    public GameScreen screen;
    public Body b2body;
    private TextureRegion mavisDefault;
    private TextureRegion mavisJump;
    private boolean movingRight;
    private boolean setToDestroy;
    private boolean destroyed;
    private Array<DyingCircle> dyingCircles;
    private Array<DyingCircle> smallFastCircles;
    private Array<Life> life;
    private float deadStateTime;
    private Vector2 deathPosition;
    private boolean secondRoundCircle;
    private boolean thirdRoundCircle;
    private int shakescreen;
    private boolean shaked;
    public boolean camKill;
    private Filter immuneFilter;
    private Filter normalFilter;
    private Filter inviFilter;
    private short normalMask;


    public Mavis(GameScreen screen){

        this.world = screen.getWorld();
        this.screen = screen;
        currentState = State.DEFAULT;
        previousState = State.DEFAULT;
        stateTimer = 0;
        won=false;
        drunk=false;
        immune=false;
        invisible=false;
        inviTimer=0;
        immuneTimer=0;
        drunkTimer=0;
        movingRight = true;
        hitByJohnCena=false;
        camKill=false;
        shakescreen = 0;

        //sprite texture
        mavisDefault = new TextureRegion(screen.getAtlas().findRegion("mavis"),0,0,16,16);
        mavisJump = new TextureRegion(screen.getAtlas().findRegion("mavis"),16,0,16,16);

        dyingCircles = new Array<DyingCircle>();
        smallFastCircles = new Array<DyingCircle>();
        life = new Array<Life>();

        normalMask = MavisAdventure.GROUND_BIT
                | MavisAdventure.GEM_BIT
                | MavisAdventure.KEY_BIT
                | MavisAdventure.LOCK_BIT
                | MavisAdventure.SHARP_BIT
                | MavisAdventure.ANTIHASTERUNE_BIT
                | MavisAdventure.INVIRUNE_BIT
                | MavisAdventure.ENEMY_BIT
                | MavisAdventure.BULLET_BIT
                | MavisAdventure.BORDER_BIT
                | MavisAdventure.DOOR_BIT
                | MavisAdventure.LOVE_BIT
                | MavisAdventure.KEY_BIT
                | MavisAdventure.BEER_BIT
                | MavisAdventure.TREASURE_BIT;

        short inviMask = MavisAdventure.BORDER_BIT
                | MavisAdventure.ANTIHASTERUNE_BIT
                | MavisAdventure.INVIRUNE_BIT
                | MavisAdventure.GEM_BIT
                | MavisAdventure.BULLET_BIT
                | MavisAdventure.LOVE_BIT
                | MavisAdventure.KEY_BIT
                | MavisAdventure.DOOR_BIT
                | MavisAdventure.TREASURE_BIT;


        short immuneMask = MavisAdventure.BORDER_BIT
                | MavisAdventure.GROUND_BIT
                | MavisAdventure.ANTIHASTERUNE_BIT
                | MavisAdventure.INVIRUNE_BIT
                | MavisAdventure.GEM_BIT
                | MavisAdventure.LOVE_BIT
                | MavisAdventure.BULLET_BIT
                | MavisAdventure.KEY_BIT
                | MavisAdventure.DOOR_BIT
                | MavisAdventure.TREASURE_BIT;


        inviFilter = new Filter();
        inviFilter.maskBits = inviMask;
        inviFilter.categoryBits=MavisAdventure.MAVIS_BIT;
        normalFilter = new Filter();
        normalFilter.maskBits = normalMask;
        normalFilter.categoryBits=MavisAdventure.MAVIS_BIT;
        immuneFilter = new Filter();
        immuneFilter.maskBits = immuneMask;
        immuneFilter.categoryBits=MavisAdventure.MAVIS_BIT;

        defineMavis();
        setBounds(0,0,16/MavisAdventure.PPM,16/MavisAdventure.PPM);
        setRegion(mavisDefault);
    }

    private void defineMavis() {
        //body definition
        BodyDef bdef = new BodyDef();
        bdef.position.set(108/MavisAdventure.PPM,32/MavisAdventure.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);
        FixtureDef fdef = new FixtureDef();
        //body fixture
        CircleShape shape = new CircleShape();
        shape.setRadius(6/MavisAdventure.PPM);
        fdef.shape= shape;
        fdef.filter.maskBits = normalMask;
        fdef.filter.categoryBits = MavisAdventure.MAVIS_BIT;
        b2body.createFixture(fdef).setUserData(this);
        addLife();
    }

    public void update(float dt){

        if(setToDestroy && ! destroyed)
        {
            deadStateTime+=dt;
            if(MavisAdventure.sfxCheck)
                MavisAdventure.manager.mavisDie.play();
            currentState = State.DEAD;
            deathPosition = b2body.getPosition();
            createDyingCircles(1,8);
            createSmallFastCircles();
            b2body.setActive(false);
            destroyed = true;
        }
        else if(!destroyed)
        {
            for(Life temp : life)
            {
                if(life.indexOf(temp,true) == 0)
                {
                    temp.update(dt, b2body.getPosition().x, b2body.getPosition().y, isFlipX());
                }

                else
                {
                    temp.update(dt, life.get(life.indexOf(temp,true)-1).getCurrentMovementX(), life.get(life.indexOf(temp,true)-1).getCurrentMovementY(), isFlipX());
                }

                if(temp.isDestroyed())
                    life.removeValue(temp,true);
            }
            setPosition(b2body.getPosition().x-getWidth()/2, b2body.getPosition().y - getHeight()/2);
            setRegion(getFrame(dt));
        }
        else
        {
            deadStateTime += dt;

            if(shakescreen<10)
            {
                screen.getGamecam().position.x += (shaked) ? 0.02 : -0.02;
                screen.getGamecam().position.y += (shaked) ? 0.02 : -0.02;
                shaked = !shaked;
                shakescreen++;
            }

            if(deadStateTime<0.7)
                createSmallFastCircles();

            if(deadStateTime >= 0.2 && !secondRoundCircle)
            {
                createDyingCircles(9,16);
                secondRoundCircle = true;
            }
            else if(deadStateTime >= 0.5 && !thirdRoundCircle)
            {
                createDyingCircles(17,32);
                thirdRoundCircle = true;
            }

            for(DyingCircle temp : dyingCircles)
            {
                temp.update(dt);
                if(temp.isDestroyed())
                    dyingCircles.removeValue(temp,true);
            }

            for(DyingCircle temp : smallFastCircles)
            {
                temp.update(dt);
                if(temp.isDestroyed())
                    smallFastCircles.removeValue(temp,true);
            }
        }

    }

    private TextureRegion getFrame(float dt) {

        TextureRegion region;
        currentState=getState();
        switch (currentState){
            case JUMPING:
                region = mavisJump;
                break;
            case DEAD:
            default:
                region = mavisDefault;
                break;
        }

        //flip sprite based on direction
        if((b2body.getLinearVelocity().x<0|| !movingRight) && !region.isFlipX()){
            region.flip(true,false);
            movingRight = false;
        }
        else if((b2body.getLinearVelocity().x> 0||movingRight) && region.isFlipX()){
            region.flip(true,false);
            movingRight = true;
        }
        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        previousState = currentState;
        return region;
    }

    private State getState() {
        if(hitByJohnCena)
            return State.DEAD;
        //check if mavis died
        switch (screen.getLevel()){
            case 1:
            case 3:
                if(b2body.getPosition().x +0.1f<= screen.getGamecam().position.x-MavisAdventure.V_WIDTH/2/MavisAdventure.PPM) {
                    camKill=true;
                    die(true);
                    return State.DEAD;
                }
                break;
            case 2:
            default:
                if(b2body.getPosition().x - 0.1f >= screen.getGamecam().position.x+MavisAdventure.V_WIDTH/2/MavisAdventure.PPM){
                    camKill=true;
                    die(true);
                    return State.DEAD;
                }
                break;
        }

        if(b2body.getPosition().y <= screen.getGamecam().position.y -MavisAdventure.V_HEIGHT/2/MavisAdventure.PPM){
            camKill=true;
            die(true);
            return State.DEAD;
        }

        if(b2body.getLinearVelocity().y>0){
            return State.JUMPING;
        }
        else return State.DEFAULT;

    }

    public void die(boolean instantKill) {
        if(life.size != 0 && !instantKill)
        {
            life.pop();
            if(MavisAdventure.sfxCheck)
                MavisAdventure.manager.break_love.play();
            goImmune();
        }
        else
        {
            if(instantKill){
                if(!camKill)
                    hitByJohnCena=true;
                if(MavisAdventure.sfxCheck&&hitByJohnCena)
                    MavisAdventure.manager.john_cena.play();
            }
            setToDestroy = true;
        }
    }


    private void createDyingCircles(int start, int end) {
        for(int i=start;i<=end ; i++)
        {
            dyingCircles.add(new DyingCircle(screen, deathPosition.x, deathPosition.y, i));
        }
    }

    private void createSmallFastCircles() {
        smallFastCircles.add(new DyingCircle(screen, deathPosition.x, deathPosition.y, 0));
    }

    public float getDeadStateTime()
    {
        return deadStateTime;
    }

    public void draw(Batch batch) {
        if(!destroyed)
            super.draw(batch);
        for(DyingCircle temp : dyingCircles)
            temp.draw(batch);
        for(DyingCircle temp2 : smallFastCircles)
            temp2.draw(batch);
        for(Life temp3 : life)
            temp3.draw(batch);
    }

    public void addLife(){
        if(life.size==0)
            life.add(new Life( b2body.getPosition().x-10/MavisAdventure.PPM, b2body.getPosition().y - 20/MavisAdventure.PPM, true));
        else
            life.add(new Life( life.get(life.size-1).getX(), life.get(life.size-1).getY() , false));
    }

    public void goInvi(){
        if(MavisAdventure.sfxCheck)
            MavisAdventure.manager.invi_rune.play();
        invisible=true;
        inviTimer=0;
        b2body.getFixtureList().get(0).setFilterData(inviFilter);
        this.setAlpha(0.5f);
    }

    public void goDrunk() {
        if(MavisAdventure.sfxCheck)
            MavisAdventure.manager.drunk.play();
        this.setColor(Color.CORAL);
        drunk=true;
        drunkTimer=0;
    }

    public void goVisible() {
        this.setAlpha(1);
        b2body.getFixtureList().get(0).setFilterData(normalFilter);
        inviTimer=0;
        invisible=false;
    }

    public void goSober() {
        this.setColor(Color.WHITE);
        drunkTimer=0;
        drunk=false;
    }

    private void goImmune() {
        immune = true;
        immuneTimer=0;
        b2body.getFixtureList().get(0).setFilterData(immuneFilter);
        setAlpha(0.5f);
    }

    public void goMortal() {
        setAlpha(1);
        immuneTimer=0;
        immune=false;
        b2body.getFixtureList().get(0).setFilterData(normalFilter);
    }


}
