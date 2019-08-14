package com.cc.mavis.Sprites.Fire;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.cc.mavis.MavisAdventure;
import com.cc.mavis.Screens.GameScreen;

public class BigFire extends Sprite {

    private World world;
    private GameScreen screen;
    public Body b2body;
    public Vector2 velocity;
    private float stateTime;
    private Rectangle rectangle;
    private Animation<TextureRegion> bigFireAnimation;

    public BigFire(GameScreen screen, Rectangle rectangle)
    {
        this.rectangle = rectangle;
        this.world = screen.getWorld();
        this.screen = screen;
        setPosition((rectangle.getX()+rectangle.getWidth()/2) / MavisAdventure.PPM, (rectangle.getY() +rectangle.getHeight()/2)/ MavisAdventure.PPM);
        this.rectangle.height = rectangle.getHeight();
        this.rectangle.width = rectangle.getWidth();
        defineBigFire();
        stateTime = 0;
        setBounds(getX(), getY(), rectangle.getWidth() / MavisAdventure.PPM, rectangle.getHeight() / MavisAdventure.PPM);
        Array<TextureRegion> frames = new Array<TextureRegion>();
        frames.add(new TextureRegion(MavisAdventure.manager.fireAtlas.findRegion("fire4")));
        frames.add(new TextureRegion(MavisAdventure.manager.fireAtlas.findRegion("fire5")));
        frames.add(new TextureRegion(MavisAdventure.manager.fireAtlas.findRegion("fire6")));
        bigFireAnimation = new Animation<TextureRegion>(0.1f, frames);
        setRegion(bigFireAnimation.getKeyFrame(0));
    }
    private void defineBigFire()
    {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(),getY());
        bdef.type = BodyDef.BodyType.StaticBody;
        b2body = world.createBody(bdef);
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(rectangle.getWidth()/2/MavisAdventure.PPM, rectangle.getHeight()/2/MavisAdventure.PPM);
        fdef.filter.categoryBits = MavisAdventure.ENEMY_BIT;
        fdef.shape = shape;
        fdef.restitution = 1.5f;
        b2body.createFixture(fdef).setUserData(this);
        b2body.setActive(false);
    }

    public void update(float dt)
    {
            if(stateTime>2)
            {
                stateTime = 0;
                b2body.setActive(false);
                screen.getCreator().getSmallFire().get(screen.getCreator().getBigFire().indexOf(this,true)).b2body.setActive(true);
            }
            if(b2body.isActive())
            {
                stateTime +=dt;
                setRegion(bigFireAnimation.getKeyFrame(stateTime,true));
                setPosition(b2body.getPosition().x - getWidth()/2, b2body.getPosition().y - getHeight()/2);
            }
    }
    public void draw(Batch batch)
    {
        if(b2body.isActive())
            super.draw(batch);
    }

}
