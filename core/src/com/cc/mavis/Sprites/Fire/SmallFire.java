package com.cc.mavis.Sprites.Fire;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
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

public class SmallFire extends Sprite {
    private World world;
    private GameScreen screen;
    public Body b2body;
    public Vector2 velocity;
    private float stateTime;
    private Rectangle rectangle;
    private Animation<TextureRegion> smallFireAnimation;
    private MapProperties properties;

    public SmallFire(GameScreen screen, MapObject object)
    {
        this.rectangle = ((RectangleMapObject)object).getRectangle();
        this.properties = object.getProperties();
        this.world = screen.getWorld();
        this.screen = screen;
        setPosition((rectangle.getX()+rectangle.getWidth()/2) / MavisAdventure.PPM, (rectangle.getY() +rectangle.getHeight()/2)/ MavisAdventure.PPM);
        this.rectangle.height = rectangle.getHeight();
        this.rectangle.width = rectangle.getWidth();
        defineSmallFire();
        stateTime = 0;
        setBounds(getX(), getY(), rectangle.getWidth() / MavisAdventure.PPM, rectangle.getHeight() / MavisAdventure.PPM);
        Array<TextureRegion> frames = new Array<TextureRegion>();
        frames.add(new TextureRegion(MavisAdventure.manager.fireAtlas.findRegion("fire1")));
        frames.add(new TextureRegion(MavisAdventure.manager.fireAtlas.findRegion("fire2")));
        frames.add(new TextureRegion(MavisAdventure.manager.fireAtlas.findRegion("fire3")));
        smallFireAnimation = new Animation<TextureRegion>(0.1f, frames);
        setRegion(smallFireAnimation.getKeyFrame(0));
    }


    private void defineSmallFire()
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
    }
    public void update(float dt)
    {
            if(stateTime>2)
            {
                if(b2body.getPosition().x>screen.getGamecam().position.x-MavisAdventure.V_WIDTH/2/MavisAdventure.PPM&&
                        b2body.getPosition().x<screen.getGamecam().position.x+MavisAdventure.V_WIDTH/2/MavisAdventure.PPM)
                    if(MavisAdventure.sfxCheck&& properties.get("level",int.class)==screen.getLevel())
                        MavisAdventure.manager.fire_sound.play();
                stateTime = 0;
                b2body.setActive(false);
                screen.getCreator().getBigFire().get(screen.getCreator().getSmallFire().indexOf(this,true)).b2body.setActive(true);
            }
            if(b2body.isActive())
            {
                stateTime+=dt;
                setRegion(smallFireAnimation.getKeyFrame(stateTime,true));
                setPosition(b2body.getPosition().x - getWidth()/2, b2body.getPosition().y - getHeight()/2);
            }
    }
    public void draw(Batch batch)
    {
        if(b2body.isActive())
            super.draw(batch);
    }

}
