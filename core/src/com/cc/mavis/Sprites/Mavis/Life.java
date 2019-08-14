package com.cc.mavis.Sprites.Mavis;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.cc.mavis.MavisAdventure;

public class Life extends Sprite {
    private Animation<TextureRegion> lifeAnimation;
    private float stateTime;
    private boolean destroyed;
    private  boolean setToDestroyed;



    private float[] movementX;
    private float[] movementY;




    Life(float x, float y, boolean first)
    {
        Array<TextureRegion> frames = new Array<TextureRegion>();
        for(int i=0; i<14;i++)
            frames.add(new TextureRegion(MavisAdventure.manager.atlas.findRegion("love20"),i*20,0,20,20));
        lifeAnimation = new Animation<TextureRegion>(0.1f, frames);
        setBounds(x , y,10/MavisAdventure.PPM, 10/ MavisAdventure.PPM);
        setRegion(lifeAnimation.getKeyFrame(0));
        movementX = new float[first ? 3 : 7];
        movementY = new float[first ? 5 : 7];
    }



    public void update(float dt, float x, float y, boolean left)
    {

        for(int i=0;i<movementX.length;i++)
        {
            if(i == movementX.length-1)
                movementX[i] = x;
            else
                movementX[i] = movementX[i+1];
        }

        for(int i=0;i<movementY.length;i++)
        {
            if(i == movementY.length-1)
                movementY[i] = y;
            else
                movementY[i] = movementY[i+1];
        }


        stateTime+=dt;
        setRegion(lifeAnimation.getKeyFrame(stateTime,true));

        if(!left)
              setPosition( movementX[0] - getWidth()*1.5f, movementY[0] - getHeight()*1.2f   );
        else
            setPosition(movementX[0] + getWidth()/2, movementY[0] - getHeight()*1.2f   );


        if( setToDestroyed && !destroyed)
        {
            destroyed = true;
        }

    }

    float getCurrentMovementX()
    {
        return  (movementX[0] );
    }

    float getCurrentMovementY()
    {
        return (movementY[0]  );
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
