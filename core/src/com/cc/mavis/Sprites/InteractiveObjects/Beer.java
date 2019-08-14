package com.cc.mavis.Sprites.InteractiveObjects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.cc.mavis.MavisAdventure;
import com.cc.mavis.Screens.GameScreen;
import com.cc.mavis.Sprites.Mavis.Mavis;

public class Beer extends InteractiveObject {

    public Beer(GameScreen screen, Rectangle rectangle) {
        super(screen, rectangle);
        fixture.setUserData(this);
        fixture.setSensor(true);
        setCategoryFilter(MavisAdventure.BEER_BIT);
        setRegion(new TextureRegion(screen.getAtlas().findRegion("beer")));
    }

    public void collected(Mavis mavis) {
        destroyed = true;
        mavis.goDrunk();
        setCategoryFilter(MavisAdventure.NOTHING_BIT);
    }

    public void update(float dt)
    {
        if(destroyed)
            world.destroyBody(body);
    }

    public void draw(Batch batch)
    {
        if(!destroyed)
        {
            super.draw(batch);
        }
    }

}
