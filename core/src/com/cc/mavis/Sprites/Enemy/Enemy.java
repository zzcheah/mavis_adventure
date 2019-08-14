package com.cc.mavis.Sprites.Enemy;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.cc.mavis.MavisAdventure;
import com.cc.mavis.Screens.GameScreen;

public abstract class Enemy extends Sprite {
    final Animation<TextureRegion> dieAnimation;
    protected World world;
    protected GameScreen screen;
    protected MapObject object;
    public MapProperties properties;
    protected boolean setToDestroy;
    protected boolean destroyed;
    public Body b2body;
    public Vector2 velocity;
    float radius;
    CircleShape circleShape;
    Enemy(GameScreen screen, MapObject object) {
        this.world = screen.getWorld();
        this.screen = screen;
        this.object = object;
        properties = object.getProperties();
        Ellipse ellipse = ((EllipseMapObject)object).getEllipse();
        this.radius = (float)(ellipse.circumference()/2/Math.PI);
        circleShape = new CircleShape();
        Array<TextureRegion> dieFrames = new Array<TextureRegion>();
        for(int i=0;i<12;i++)
            dieFrames.add(new TextureRegion(MavisAdventure.manager.atlas.findRegion("ashes"),i*60,0,60,60));
        dieAnimation = new Animation<TextureRegion>(0.1f, dieFrames);
        setPosition(ellipse.x,ellipse.y);
        defineEnemy();
        velocity= new Vector2(0,0.5f);
    }

    protected  abstract  void defineEnemy();
    public abstract void update(float dt);
    public void killedByBullet() {
        if(MavisAdventure.sfxCheck)
            MavisAdventure.manager.enemy_dead.play();
        setToDestroy=true;
    }
    public void reverseVelocity(boolean x, boolean y) {
        if(x)
            velocity.x = - velocity.x;
        if(y)
            velocity.y = -velocity.y;
    }
}
