package com.cc.mavis.Sprites.InteractiveObjects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.cc.mavis.MavisAdventure;
import com.cc.mavis.Screens.GameScreen;
import com.cc.mavis.Sprites.Mavis.Mavis;

public class Love extends InteractiveObject {
    public Love(GameScreen screen, Rectangle rectangle) {
        super(screen, rectangle);
        fixture.setUserData(this);
        fixture.setSensor(true);
        setCategoryFilter(MavisAdventure.LOVE_BIT);
        setRegion(new TextureRegion(screen.getAtlas().findRegion("love")));
    }

    public void collected(Mavis mavis) {
        if(MavisAdventure.sfxCheck)
            MavisAdventure.manager.get_love.play();
        destroyed = true;
        mavis.addLife();
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
