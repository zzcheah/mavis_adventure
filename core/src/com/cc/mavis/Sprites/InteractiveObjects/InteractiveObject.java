package com.cc.mavis.Sprites.InteractiveObjects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.cc.mavis.MavisAdventure;
import com.cc.mavis.Screens.GameScreen;

public abstract class InteractiveObject extends Sprite {

    protected World world;
    protected Rectangle rectangle;
    protected Body body;
    Fixture fixture;
    protected GameScreen screen;
    protected  boolean destroyed;

    InteractiveObject(GameScreen screen, Rectangle rectangle)
    {
        this.screen = screen;
        this.world = screen.getWorld();
        this.rectangle = rectangle;
        destroyed =false;
        setPosition((rectangle.getX()) / MavisAdventure.PPM, (rectangle.getY() )/ MavisAdventure.PPM);
        setBounds(getX(), getY(), rectangle.getWidth() / MavisAdventure.PPM, rectangle.getHeight() / MavisAdventure.PPM);
        System.out.println(getX());

        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        bdef.position.set(getX(),getY());
        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set((rectangle.getX()+rectangle.getWidth()/2) / MavisAdventure.PPM, (rectangle.getY()+rectangle.getHeight()/2) / MavisAdventure.PPM);
        body = world.createBody(bdef);

        shape.setAsBox(rectangle.getWidth()/2 / MavisAdventure.PPM,rectangle.getHeight()/2 / MavisAdventure.PPM);
        fdef.shape = shape;
        fixture = body.createFixture(fdef);
    }


    void setCategoryFilter(short filterBit)
    {
        Filter filter = new Filter();
        filter.categoryBits = filterBit;
        fixture.setFilterData(filter);
    }

    public boolean isDestroyed()
    {
        return destroyed;
    }
}
