package com.cc.mavis.Sprites.InteractiveObjects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.cc.mavis.MavisAdventure;
import com.cc.mavis.Screens.GameScreen;
import com.cc.mavis.Tools.Controller;

public class AntiHasteRune extends InteractiveObject {
    public AntiHasteRune(GameScreen screen, Rectangle rectangle) {
        super(screen, rectangle);
        fixture.setUserData(this);
        fixture.setSensor(true);
        setCategoryFilter(MavisAdventure.ANTIHASTERUNE_BIT);
        TextureRegion picture = new TextureRegion(screen.getAtlas().findRegion("anti_haste_rune"));
        setRegion(picture);
    }

    public void collected(Controller controller) {
        if(MavisAdventure.sfxCheck)
            MavisAdventure.manager.getsomething2.play();
        destroyed = true;
        controller.addRune("antiHasteRune");
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
