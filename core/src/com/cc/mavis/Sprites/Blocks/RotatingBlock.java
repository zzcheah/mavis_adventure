package com.cc.mavis.Sprites.Blocks;

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
import com.cc.mavis.MavisAdventure;
import com.cc.mavis.Screens.GameScreen;


public class RotatingBlock extends Sprite {
    private World world;
    public Body b2body;
    public Vector2 velocity;
    private Rectangle rectangle;

    public RotatingBlock(GameScreen screen, Rectangle rectangle) {
        this.rectangle = rectangle;
        this.world = screen.getWorld();
        setPosition((rectangle.getX()+rectangle.getWidth()/2) / MavisAdventure.PPM, (rectangle.getY() +rectangle.getHeight()/2)/ MavisAdventure.PPM);
        setOrigin(rectangle.getWidth()/2/ MavisAdventure.PPM,rectangle.getHeight()/2/ MavisAdventure.PPM);
        createVanishableBLock();
        velocity = new Vector2(0, 0.08f);
        setRegion(new TextureRegion(screen.getAtlas().findRegion("blue_rectangle")));
        setBounds(getX(), getY(), rectangle.getWidth() / MavisAdventure.PPM, rectangle.getHeight() / MavisAdventure.PPM);
    }
    private void createVanishableBLock()
    {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(),getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(rectangle.getWidth()/2/MavisAdventure.PPM, rectangle.getHeight()/2/MavisAdventure.PPM);
        fdef.filter.categoryBits = MavisAdventure.GROUND_BIT;
        fdef.shape = shape;
        fdef.density = 1000;
        b2body.createFixture(fdef).setUserData(this);
    }

    public void update(float dt)
    {
            b2body.setAngularVelocity(-10);
            setRotation((float) (b2body.getAngle() * 180.f / Math.PI));
            b2body.setLinearVelocity(velocity );
            setPosition(b2body.getPosition().x - getWidth()/2, b2body.getPosition().y - getHeight()/2);

    }
    public void draw(Batch batch)
    {
            super.draw(batch);
    }
}
