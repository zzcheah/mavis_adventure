package com.cc.mavis.Sprites.OtherObjects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.cc.mavis.MavisAdventure;
import com.cc.mavis.Screens.GameScreen;

public class Bullet extends Sprite {

    private World world;
    public Body b2body;
    public Vector2 velocity;
    private boolean setToDestroy;
    private boolean destroyed;
    private float stateTime;
    private Polygon polygon;
    private boolean triggered;

    public Bullet(GameScreen screen, Polygon polygon)
    {

        this.polygon = polygon;
        this.world = screen.getWorld();
        setPosition((polygon.getX()) /MavisAdventure.PPM,(polygon.getY() )/MavisAdventure.PPM);
        defineBullet();
        velocity= new Vector2(1,0);
        stateTime = 0;
        setRegion(MavisAdventure.manager.atlas.findRegion("bullet"));
        setBounds(getX(), getY(), 32/MavisAdventure.PPM, 17/MavisAdventure.PPM);
    }

    private void defineBullet()
    {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(),getY());
        bdef.type = BodyDef.BodyType.KinematicBody;
        b2body = world.createBody(bdef);
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        for(int x=0;x<polygon.getVertices().length;x++)
        {
            polygon.getVertices()[x] /= MavisAdventure.PPM;
        }
        shape.set(polygon.getVertices());
        fdef.filter.categoryBits = MavisAdventure.BULLET_BIT;
          fdef.filter.maskBits = MavisAdventure.MAVIS_BIT | MavisAdventure.GROUND_BIT| MavisAdventure.ENEMY_BIT;
        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);
    }

    public void update(float dt)
    {
        if(setToDestroy && !destroyed)
        {
            world.destroyBody(b2body);
            destroyed = true;
            stateTime = 0;
        }
        else if(!destroyed)
        {
            if(triggered)
            {
                b2body.setLinearVelocity(velocity );
                stateTime +=dt;
                if(stateTime>3f)
                    setToDestroy=true;
            }
            setPosition(b2body.getPosition().x - getWidth()/2, b2body.getPosition().y - getHeight()/2);
        }


    }
    public void draw(Batch batch)
    {
        if(!destroyed)
         super.draw(batch);
    }

    public void triggerBullet()
    {
        if(MavisAdventure.sfxCheck)
            MavisAdventure.manager.bullet.play();
        triggered = true;
    }
}
