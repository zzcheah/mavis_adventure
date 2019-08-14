package com.cc.mavis.Tools;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.cc.mavis.MavisAdventure;
import com.cc.mavis.Sprites.OtherObjects.Bullet;
import com.cc.mavis.Sprites.Enemy.Enemy;
import com.cc.mavis.Sprites.Enemy.Stone;
import com.cc.mavis.Sprites.InteractiveObjects.AntiHasteRune;
import com.cc.mavis.Sprites.InteractiveObjects.Beer;
import com.cc.mavis.Sprites.InteractiveObjects.Gems;
import com.cc.mavis.Sprites.InteractiveObjects.InviRune;
import com.cc.mavis.Sprites.InteractiveObjects.Love;
import com.cc.mavis.Sprites.InteractiveObjects.Treasure;
import com.cc.mavis.Sprites.OtherObjects.Key;
import com.cc.mavis.Sprites.Mavis.Mavis;

public class GameworldContactListener implements ContactListener {

    private Controller controller;

    public GameworldContactListener(Controller controller){
        this.controller = controller;
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        switch (cDef) {
            case MavisAdventure.MAVIS_BIT | MavisAdventure.TREASURE_BIT:
                if (fixA.getFilterData().categoryBits == MavisAdventure.MAVIS_BIT)
                    ((Treasure) fixB.getUserData()).collected();
                else
                    ((Treasure) fixA.getUserData()).collected();
                break;

            case MavisAdventure.MAVIS_BIT | MavisAdventure.GEM_BIT:
                if (fixA.getFilterData().categoryBits == MavisAdventure.MAVIS_BIT)
                    ((Gems) fixB.getUserData()).collected();
                else
                    ((Gems) fixA.getUserData()).collected();
                break;

            case MavisAdventure.MAVIS_BIT | MavisAdventure.BEER_BIT:
                if (fixA.getFilterData().categoryBits == MavisAdventure.MAVIS_BIT)
                    ((Beer) fixB.getUserData()).collected((Mavis) fixA.getUserData());
                else
                    ((Beer) fixA.getUserData()).collected((Mavis) fixB.getUserData());
                break;

            case MavisAdventure.MAVIS_BIT | MavisAdventure.LOVE_BIT:
                if (fixA.getFilterData().categoryBits == MavisAdventure.MAVIS_BIT)
                    ((Love) fixB.getUserData()).collected((Mavis) fixA.getUserData());
                else
                    ((Love) fixA.getUserData()).collected((Mavis) fixB.getUserData());
                break;

            case MavisAdventure.MAVIS_BIT | MavisAdventure.INVIRUNE_BIT:
                if (fixA.getFilterData().categoryBits == MavisAdventure.MAVIS_BIT)
                    ((InviRune) fixB.getUserData()).collected(controller);
                else
                    ((InviRune) fixA.getUserData()).collected(controller);
                break;

            case MavisAdventure.MAVIS_BIT | MavisAdventure.ANTIHASTERUNE_BIT:
                if (fixA.getFilterData().categoryBits == MavisAdventure.MAVIS_BIT)
                    ((AntiHasteRune) fixB.getUserData()).collected(controller);
                else
                    ((AntiHasteRune) fixA.getUserData()).collected(controller);
                break;

            case MavisAdventure.KEY_BIT | MavisAdventure.LOCK_BIT:
                if (fixA.getFilterData().categoryBits == MavisAdventure.KEY_BIT)
                    ((Key) fixA.getUserData()).openBlock();
                else
                    ((Key) fixB.getUserData()).openBlock();
                break;

            case MavisAdventure.MAVIS_BIT | MavisAdventure.ENEMY_BIT:
                if (fixA.getFilterData().categoryBits == MavisAdventure.MAVIS_BIT)
                    ((Mavis) fixA.getUserData()).die(fixB.getUserData() instanceof Stone);
                else
                    ((Mavis) fixB.getUserData()).die(fixA.getUserData() instanceof Stone);
                break;

            case MavisAdventure.MAVIS_BIT | MavisAdventure.SHARP_BIT:
                if (fixA.getFilterData().categoryBits == MavisAdventure.MAVIS_BIT)
                    ((Mavis) fixA.getUserData()).die(false);
                else
                    ((Mavis) fixB.getUserData()).die(false);
                break;

            case MavisAdventure.ENEMY_BIT | MavisAdventure.GROUND_BIT:
            case MavisAdventure.ENEMY_BIT | MavisAdventure.BORDER_BIT:
                if(fixA.getFilterData().categoryBits == MavisAdventure.ENEMY_BIT) {
                    if(fixA.getUserData() instanceof Stone &&  (((Stone)fixA.getUserData()).getX()<0.0067 || ((Stone)fixA.getUserData()).getX()>15.3 ))
                        ((Enemy)fixA.getUserData()).reverseVelocity(true,false);
                    else
                        ((Enemy)fixA.getUserData()).reverseVelocity(false,true);
                }

                else {
                    if(fixB.getUserData() instanceof Stone &&  (((Stone)fixB.getUserData()).getX()<0.0067 || ((Stone)fixB.getUserData()).getX()>15.3 ))
                        ((Enemy)fixB.getUserData()).reverseVelocity(true,false);
                    else
                        ((Enemy)fixB.getUserData()).reverseVelocity(false,true);
                }
                break;

            case MavisAdventure.MAVIS_BIT | MavisAdventure.BULLET_BIT:
                if(fixA.getFilterData().categoryBits == MavisAdventure.BULLET_BIT)
                    ((Bullet)fixA.getUserData()).triggerBullet();
                else
                    ((Bullet)fixB.getUserData()).triggerBullet();
                break;

            case MavisAdventure.ENEMY_BIT | MavisAdventure.BULLET_BIT:
                if(fixA.getFilterData().categoryBits == MavisAdventure.ENEMY_BIT)
                    ((Enemy)fixA.getUserData()).killedByBullet();
                else
                    ((Enemy)fixB.getUserData()).killedByBullet();
                break;

            case MavisAdventure.MAVIS_BIT | MavisAdventure.DOOR_BIT:
                if (fixA.getFilterData().categoryBits == MavisAdventure.MAVIS_BIT)
                    ((Mavis) fixA.getUserData()).won=true;
                else
                    ((Mavis) fixB.getUserData()).won=true;
                break;

            case MavisAdventure.MAVIS_BIT | MavisAdventure.KEY_BIT:
                if (fixA.getFilterData().categoryBits == MavisAdventure.KEY_BIT)
                    ((Key) fixA.getUserData()).pushed(((Mavis)fixB.getUserData()).b2body.getLinearVelocity().x, ((Mavis)fixB.getUserData()).b2body.getLinearVelocity().y);
                else
                    ((Key) fixB.getUserData()).pushed(((Mavis)fixA.getUserData()).b2body.getLinearVelocity().x, ((Mavis)fixA.getUserData()).b2body.getLinearVelocity().y);
                break;

        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
