package com.cc.mavis.Sprites.Enemy;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.cc.mavis.MavisAdventure;
import com.cc.mavis.Screens.GameScreen;

public class Monster extends Enemy{
    private float stateTime;
    private float chargeCooldown;
    private boolean inCoolDownNow;
    private boolean alreadyDetected;
    private float angle;
    public boolean detectMavis;
    public Monster(GameScreen screen, MapObject object) {
        super(screen, object);
        stateTime =0;
        setBounds(getX(), getY(), 32/MavisAdventure.PPM, 32/MavisAdventure.PPM);
        detectMavis = false;
        setRegion(new TextureRegion(MavisAdventure.manager.atlas.findRegion("monster")));
        setToDestroy = false;
        destroyed = false;
    }
    @Override
    protected void defineEnemy() {
        BodyDef bdef = new BodyDef();
        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.position.set((getX()+radius)/MavisAdventure.PPM, (getY() +radius)/MavisAdventure.PPM);
        b2body = world.createBody(bdef);
        circleShape.setRadius(radius/MavisAdventure.PPM);
        FixtureDef fdef = new FixtureDef();
        fdef.filter.categoryBits = MavisAdventure.ENEMY_BIT;
        fdef.filter.maskBits = MavisAdventure.MAVIS_BIT | MavisAdventure.GROUND_BIT | MavisAdventure.BULLET_BIT | MavisAdventure.BORDER_BIT;
        fdef.shape = circleShape;
        b2body.createFixture(fdef).setUserData(this);
    }

    @Override
    public void update(float dt) {
        stateTime+=dt;
        if(setToDestroy && !destroyed)
        {
            world.destroyBody(b2body);
            destroyed = true;
            stateTime = 0;
        }
        else if(!destroyed)
        {
            if(detectMavis)
            {
                if(!alreadyDetected)
                {
                    alreadyDetected = true;
                    stateTime = 0;
                }
                if(stateTime>1)
                    setToDestroy = true;

                  b2body.setLinearVelocity(new Vector2((float) (3 * Math.cos(Math.toRadians(angle))), (float) (3 * Math.sin(Math.toRadians(angle)))));
            }
            else
                b2body.setLinearVelocity(velocity);
            if(b2body.getLinearVelocity().x>0 && !isFlipX())
                flip(true,false);
            if(b2body.getLinearVelocity().x<0 && isFlipX())
                flip(true,false);
            setPosition(b2body.getPosition().x - getWidth()/2, b2body.getPosition().y - getHeight()/2);
        }
        else
            setRegion(dieAnimation.getKeyFrame(stateTime,false));
    }
    public void charge(float x, float y)
    {
        angle = new Vector2(x - getX(),y-getY()).angle();
        detectMavis = true;
    }

    public boolean cooldown(float dt)
    {
        inCoolDownNow=true;
        chargeCooldown+=dt;
        return (chargeCooldown<0.5f );
    }
    public boolean getInCoolDownNow()
    {
        return inCoolDownNow;
    }
    public void draw(Batch batch)
    {
        if(!destroyed ||stateTime<1.2 )
        {
           super.draw(batch);
        }
    }

}
