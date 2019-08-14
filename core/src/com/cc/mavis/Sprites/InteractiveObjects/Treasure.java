package com.cc.mavis.Sprites.InteractiveObjects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.cc.mavis.MavisAdventure;
import com.cc.mavis.Screens.GameScreen;
import com.cc.mavis.Tools.TopDisplay;

public class Treasure extends InteractiveObject{

    public Treasure(GameScreen screen, Rectangle rectangle) {
        super(screen, rectangle);
        fixture.setUserData(this);
        fixture.setSensor(true);

        setCategoryFilter(MavisAdventure.TREASURE_BIT);

        TextureRegion picture = new TextureRegion(screen.getAtlas().findRegion("treasure"));
        setRegion(picture);
    }


    public void collected() {
        if(MavisAdventure.sfxCheck)
            MavisAdventure.manager.get_gem.play();
        destroyed=true;
        TopDisplay.addScore(1000);
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
