package com.cc.mavis.Sprites.Enemy;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.utils.Array;
import com.cc.mavis.MavisAdventure;
import com.cc.mavis.Screens.GameScreen;

public class Stone extends  Enemy {
    private float stateTime;
    private Animation<TextureRegion> johnCenaAnimation;

    public Stone(GameScreen screen, MapObject object) {
        super(screen, object);
        stateTime =0;
        setBounds(getX(), getY(), 64/MavisAdventure.PPM, 64/MavisAdventure.PPM);
        Array<TextureRegion> frames = new Array<TextureRegion>();
        for(int i=0; i<4;i++)
            frames.add(new TextureRegion(MavisAdventure.manager.atlas.findRegion("john_cena153"),i*153,0,153,153));
        johnCenaAnimation = new Animation<TextureRegion>(0.3f, frames);
        setRegion(johnCenaAnimation.getKeyFrame(0));
        setToDestroy = false;
        destroyed = false;
        velocity = new Vector2(-1f,0.5f);
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
        fdef.filter.maskBits = MavisAdventure.MAVIS_BIT | MavisAdventure.BORDER_BIT;
        fdef.restitution=3;
        fdef.density=999f;
        fdef.shape = circleShape;
        b2body.createFixture(fdef).setUserData(this);
    }

    @Override
    public void update(float dt) {
            stateTime +=dt;
            setRegion(johnCenaAnimation.getKeyFrame(stateTime,true));
            b2body.setLinearVelocity(velocity);
            setPosition(b2body.getPosition().x - getWidth()/2, b2body.getPosition().y - getHeight()/2);

    }
    public void draw(Batch batch)
    {
        if(!destroyed )
        {
            super.draw(batch);
        }
    }
}
