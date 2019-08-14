package com.cc.mavis.Sprites.Mavis;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.cc.mavis.MavisAdventure;
import com.cc.mavis.Screens.GameScreen;
import java.util.Random;

public class DyingCircle extends Sprite {

    private World world;
    private Animation<TextureRegion> bubbleAnimation;
    private float stateTime;
    private boolean destroyed;
    private  boolean setToDestroyed;
    private Body b2body;
    private double degree;
    private float speed;
    private int radius;


    DyingCircle(GameScreen screen, float x, float y, int number)
    {
        this.world = screen.getWorld();
        Array<TextureRegion> frames = new Array<TextureRegion>();
        float frameRate;
        if(number == 0)
        {
            frames.add(new TextureRegion(MavisAdventure.manager.bubblesAtlas.findRegion("bubble1")));
            frames.add(new TextureRegion(MavisAdventure.manager.bubblesAtlas.findRegion("bubble2")));
            frames.add(new TextureRegion(MavisAdventure.manager.bubblesAtlas.findRegion("bubble3")));
            frames.add(new TextureRegion(MavisAdventure.manager.bubblesAtlas.findRegion("bubble4")));
            setBounds(x,y,10/MavisAdventure.PPM, 10/ MavisAdventure.PPM);
            frameRate = 0.2f;
        }
        else
        {
            frames.add(new TextureRegion(MavisAdventure.manager.bubblesAtlas.findRegion("circle1")));
            frames.add(new TextureRegion(MavisAdventure.manager.bubblesAtlas.findRegion("circle2")));
            frames.add(new TextureRegion(MavisAdventure.manager.bubblesAtlas.findRegion("circle3")));
            frames.add(new TextureRegion(MavisAdventure.manager.bubblesAtlas.findRegion("circle4")));
            setBounds(x,y,30/MavisAdventure.PPM, 30/ MavisAdventure.PPM);
            frameRate = 0.09f;
        }
        bubbleAnimation = new Animation<TextureRegion>(frameRate, frames);
        setRegion(bubbleAnimation.getKeyFrame(0));
        radius = 5;
        if(number == 0)
        {
            speed = 2f;
            radius = 2;
        }
        else if(number>=1 && number<=8)
        {
           speed = 0.5f;
         }
        else if(number>= 9 && number <=16)
        {
            speed =0.5f * 1.1f;
        }
        else
        {
            speed = 0.5f * 1.3f;
        }
        switch(number)
        {
            case 0:
            {
                Random r = new Random();
                degree = 0 + r.nextDouble() * 359 ; // randomNumber = min + r.nextDouble() * (max - min);
                break;
            }
            case 1: { degree = 0;break; }
            case 2: { degree = 45;break; }
            case 3: { degree = 90;break; }
            case 4: { degree = 135;break; }
            case 5: { degree = 180;break; }
            case 6: { degree = 225;break; }
            case 7: { degree = 270;break; }
            case 8: { degree = 315;break; }

            case 9: { degree = 22.5;break; }
            case 10: { degree = 67.5;break; }
            case 11: { degree = 112.5;break; }
            case 12: { degree = 157.5;break; }
            case 13: { degree = 202.5;break; }
            case 14: { degree = 247.5;break; }
            case 15: { degree = 292.5;break; }
            case 16: { degree = 337.5;break; }

            case 17: { degree = 11.25;break; }
            case 18: { degree = 33.75;break; }
            case 19: { degree = 56.25;break; }
            case 20: { degree = 78.75;break; }
            case 21: { degree = 101.25;break; }
            case 22: { degree = 123.75;break; }
            case 23: { degree = 146.25;break; }
            case 24: { degree = 168.75;break; }
            case 25: { degree = 191.25;break; }
            case 26: { degree = 213.75;break; }
            case 27: { degree = 236.25;break; }
            case 28: { degree = 258.75;break; }
            case 29: { degree = 281.25;break; }
            case 30: { degree = 303.75;break; }
            case 31: { degree = 326.25;break; }
            case 32: { degree = 348.75;break; }
        }
        defineDyingCircles();
    }

    private void defineDyingCircles()
    {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY() );
        bdef.type = BodyDef.BodyType.KinematicBody;
        if(!world.isLocked())
            b2body = world.createBody(bdef);
        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(radius/MavisAdventure.PPM);
        fdef.filter.categoryBits = MavisAdventure.NOTHING_BIT;
        fdef.shape = shape;
        fdef.friction = 0;
        b2body.createFixture(fdef).setUserData(this);
        b2body.setLinearVelocity(new Vector2((float) (speed * Math.cos(Math.toRadians(degree))), (float) (speed * Math.sin(Math.toRadians(degree)))));
    }

    public void update(float dt)
    {
        stateTime+=dt;
        setRegion(bubbleAnimation.getKeyFrame(stateTime,true));
        setPosition(b2body.getPosition().x - getWidth()/2 , b2body.getPosition().y - getHeight()/2 );
        if((stateTime>3 || setToDestroyed) && !destroyed)
        {
            world.destroyBody(b2body);
            destroyed = true;
        }
    }

    public void setToDestroy()
    {
        setToDestroyed = true;
    }
    public boolean isDestroyed()
    {
        return destroyed;
    }
}
