package com.cc.mavis.Sprites.OtherObjects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.cc.mavis.MavisAdventure;
import com.cc.mavis.Screens.GameScreen;

public class Key extends Sprite {
    private World world;
    private GameScreen screen;
    public Body b2body;
    public Vector2 velocity;
    private Polygon polygon;

    public Key(GameScreen screen, Polygon polygon)
    {
        this.polygon = polygon;
        this.world = screen.getWorld();
        this.screen = screen;
        setPosition((polygon.getX()) /MavisAdventure.PPM,(polygon.getY() )/MavisAdventure.PPM);
        createKey();
        velocity= new Vector2(0,0.08f);
        setRegion(new TextureRegion(screen.getAtlas().findRegion("key")));
        setBounds(getX(), getY(), 32/MavisAdventure.PPM, 32/MavisAdventure.PPM);
    }

    private void createKey()
    {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(),getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);
        FixtureDef fdef = new FixtureDef();
        fdef.density = 0;
        PolygonShape shape = new PolygonShape();
        for(int x=0;x<polygon.getVertices().length;x++)
        {
            polygon.getVertices()[x] /= MavisAdventure.PPM;
        }
        shape.set(polygon.getVertices());
        fdef.filter.categoryBits = MavisAdventure.KEY_BIT;
        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);
    }

    public void update(float dt)
    {
            b2body.setLinearVelocity(velocity );
            setPosition(b2body.getPosition().x - getWidth()/2, b2body.getPosition().y - getHeight()/2);
    }
    public void draw(Batch batch){
        super.draw(batch);
    }

    public void openBlock(){
        if(MavisAdventure.sfxCheck)
            MavisAdventure.manager.keylock_combined.play();
        screen.getCreator().getBLocks().get(screen.getCreator().getKey().indexOf(this,true)).destroyBlock();
    }

    public void pushed (float x,float y)
    {
        if(x>0 )
            b2body.applyLinearImpulse(new Vector2(2.5f,0),b2body.getWorldCenter(),true);
        else if(x<0)
            b2body.applyLinearImpulse(new Vector2(-2.5f,0),b2body.getWorldCenter(),true);

        if(x==0 && y>0)
            b2body.applyLinearImpulse(new Vector2(0,2.5f),b2body.getWorldCenter(),true);
    }

}
