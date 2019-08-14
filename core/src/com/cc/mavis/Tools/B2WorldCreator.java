package com.cc.mavis.Tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.cc.mavis.MavisAdventure;
import com.cc.mavis.Screens.GameScreen;
import com.cc.mavis.Sprites.Fire.BigFire;
import com.cc.mavis.Sprites.OtherObjects.Bullet;
import com.cc.mavis.Sprites.Enemy.Enemy;
import com.cc.mavis.Sprites.Enemy.Ghost;
import com.cc.mavis.Sprites.Enemy.Monster;
import com.cc.mavis.Sprites.Enemy.Stone;
import com.cc.mavis.Sprites.InteractiveObjects.AntiHasteRune;
import com.cc.mavis.Sprites.InteractiveObjects.Beer;
import com.cc.mavis.Sprites.InteractiveObjects.Gems;
import com.cc.mavis.Sprites.InteractiveObjects.InviRune;
import com.cc.mavis.Sprites.InteractiveObjects.Love;
import com.cc.mavis.Sprites.InteractiveObjects.Treasure;
import com.cc.mavis.Sprites.OtherObjects.Key;
import com.cc.mavis.Sprites.Blocks.RotatingBlock;
import com.cc.mavis.Sprites.Fire.SmallFire;
import com.cc.mavis.Sprites.Blocks.VanishableBLock;

public class B2WorldCreator {

    //variables
    private Array<Key> keys;
    private Array<VanishableBLock> vBlocks;
    private Array<RotatingBlock> rBlocks;
    private Array<SmallFire> sFire;
    private Array<BigFire> bFire;

    private Array<Monster> monsters;
    private Array<Ghost> ghosts;
    private Array<Stone> stones;

    private Array<Bullet> bullets;

    private Array<AntiHasteRune> antiHasteRunes;
    private Array<InviRune> inviRunes;
    private Array<Beer> beers;
    private Array<Love> loves;
    private Array<Gems> gems;
    private Array<Treasure> treasures;

    public B2WorldCreator(GameScreen screen){
        World world  = screen.getWorld();
        TiledMap map = screen.getMap();
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        //unbreakable brciks bodies
        for(MapObject object: map.getLayers().get(1).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject)object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth()/2)/MavisAdventure.PPM, (rect.getY() + rect.getHeight()/2)/MavisAdventure.PPM);

            body = world.createBody(bdef);
            shape.setAsBox(rect.getWidth()/2/MavisAdventure.PPM,rect.getHeight()/2/MavisAdventure.PPM);
            fdef.shape = shape;
            fdef.filter.categoryBits = MavisAdventure.BORDER_BIT;
            body.createFixture(fdef);
        }

        //immobile ground bodies
        for(MapObject object: map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject)object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth()/2)/MavisAdventure.PPM, (rect.getY() + rect.getHeight()/2)/MavisAdventure.PPM);

            body = world.createBody(bdef);
            shape.setAsBox(rect.getWidth()/2/MavisAdventure.PPM,rect.getHeight()/2/MavisAdventure.PPM);
            fdef.shape = shape;
            fdef.filter.categoryBits = MavisAdventure.GROUND_BIT;
            body.createFixture(fdef);
        }

        //sharp object
        for(MapObject object: map.getLayers().get(3).getObjects().getByType(PolygonMapObject.class)){
            Polygon polygon = ((PolygonMapObject)object).getPolygon();
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((polygon.getX() )/MavisAdventure.PPM, (polygon.getY() )/MavisAdventure.PPM);
            body = world.createBody(bdef);
            for(int x=0;x<polygon.getVertices().length;x++)
            {
                polygon.getVertices()[x] /= MavisAdventure.PPM;
            }
            shape.set(polygon.getVertices());
            fdef.shape = shape;
            fdef.filter.categoryBits = MavisAdventure.SHARP_BIT;
            fdef.restitution = 2f;
            fdef.filter.maskBits = MavisAdventure.MAVIS_BIT;
            body.createFixture(fdef);
        }

        //beers
        beers = new Array<Beer>();
        for(MapObject object: map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject)object).getRectangle();
            beers.add(new Beer(screen,rect));
        }

        //gem bodies
        gems = new Array<Gems>();
        for(MapObject object: map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject)object).getRectangle();
            gems.add(new Gems(screen,rect));
            screen.gemCount++;
        }

        //invi rune
        inviRunes = new Array<InviRune>();
        for(MapObject object: map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject)object).getRectangle();
            inviRunes.add(new InviRune(screen,rect));
        }

        //antihaste rune
        antiHasteRunes = new Array<AntiHasteRune>();
        for(MapObject object: map.getLayers().get(7).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject)object).getRectangle();
            antiHasteRunes.add(new AntiHasteRune(screen,rect));
        }

        //love
        loves = new Array<Love>();
        for(MapObject object: map.getLayers().get(8).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject)object).getRectangle();
            loves.add(new Love(screen,rect));
        }

        //rotating blocks
        rBlocks = new Array<RotatingBlock>();
        for(MapObject object: map.getLayers().get(9).getObjects().getByType(RectangleMapObject.class)){
            Rectangle polygon = ((RectangleMapObject)object).getRectangle();
            rBlocks.add(new RotatingBlock(screen,polygon));
        }

        //Enemy monster
        monsters = new Array<Monster>();
        for(MapObject object: map.getLayers().get(10).getObjects().getByType(EllipseMapObject.class)){
            monsters.add(new Monster(screen, object));
        }

        //Enemy ghosts
        ghosts = new Array<Ghost>();
        for(MapObject object: map.getLayers().get(11).getObjects().getByType(EllipseMapObject.class)){
            ghosts.add(new Ghost(screen, object));
        }

        //Enemy stones or john cena
        stones = new Array<Stone>();
        for(MapObject object: map.getLayers().get(12).getObjects().getByType(EllipseMapObject.class)){
            stones.add(new Stone(screen, object));
        }

        //keys
        keys = new Array<Key>();
        for(MapObject object: map.getLayers().get(13).getObjects().getByType(PolygonMapObject.class)){
            Polygon polygon = ((PolygonMapObject)object).getPolygon();
            keys.add(new Key(screen,polygon));
        }

        //lock
        for(MapObject object: map.getLayers().get(14).getObjects().getByType(PolygonMapObject.class)){
            Polygon polygon = ((PolygonMapObject)object).getPolygon();
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((polygon.getX() )/MavisAdventure.PPM, (polygon.getY() )/MavisAdventure.PPM);
            body = world.createBody(bdef);
            for(int x=0;x<polygon.getVertices().length;x++)
            {
                polygon.getVertices()[x] /= MavisAdventure.PPM;
            }
            shape.set(polygon.getVertices());
            fdef.shape = shape;
            fdef.filter.categoryBits = MavisAdventure.LOCK_BIT;
            fdef.filter.maskBits = (short)0xFFFF;
            fdef.isSensor= true;
            body.createFixture(fdef);
        }

        //bullets
        bullets = new Array<Bullet>();
        for(MapObject object: map.getLayers().get(15).getObjects().getByType(PolygonMapObject.class)){
            Polygon polygon = ((PolygonMapObject)object).getPolygon();
            bullets.add(new Bullet(screen,polygon));
        }

        //vanishable block
        vBlocks = new Array<VanishableBLock>();
        for(MapObject object: map.getLayers().get(16).getObjects().getByType(RectangleMapObject.class)){
            Rectangle polygon = ((RectangleMapObject)object).getRectangle();
            vBlocks.add(new VanishableBLock(screen,polygon));
        }

        //small fire
        sFire = new Array<SmallFire>();
        for(MapObject object: map.getLayers().get(17).getObjects().getByType(RectangleMapObject.class)){
            sFire.add(new SmallFire(screen,object));
        }

        //big fire
        bFire = new Array<BigFire>();
        for(MapObject object: map.getLayers().get(18).getObjects().getByType(RectangleMapObject.class)){
            Rectangle polygon = ((RectangleMapObject)object).getRectangle();
            bFire.add(new BigFire(screen,polygon));
        }

        //door
        for(MapObject object: map.getLayers().get(19).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject)object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth()/2)/MavisAdventure.PPM, (rect.getY() + rect.getHeight()/2)/MavisAdventure.PPM);

            body = world.createBody(bdef);
            shape.setAsBox(rect.getWidth()/2/MavisAdventure.PPM,rect.getHeight()/2/MavisAdventure.PPM);
            fdef.shape = shape;
            fdef.filter.categoryBits = MavisAdventure.DOOR_BIT;
            body.createFixture(fdef);
        }

        //polybrick
        for(MapObject object: map.getLayers().get(20).getObjects().getByType(PolygonMapObject.class)){
            Polygon polygon = ((PolygonMapObject)object).getPolygon();
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((polygon.getX() )/MavisAdventure.PPM, (polygon.getY() )/MavisAdventure.PPM);
            body = world.createBody(bdef);
            for(int x=0;x<polygon.getVertices().length;x++)
            {
                polygon.getVertices()[x] /= MavisAdventure.PPM;
            }
            shape.set(polygon.getVertices());
            fdef.shape = shape;
            fdef.isSensor=false;
            fdef.filter.categoryBits = MavisAdventure.GROUND_BIT;
            fdef.restitution=0;
            body.createFixture(fdef);
        }


        //tresure
        treasures = new Array<Treasure>();
        for(MapObject object: map.getLayers().get(21).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject)object).getRectangle();
            treasures.add(new Treasure(screen,rect));
        }

    }

    public Array<Bullet> getBullets() {
        Array<Bullet> temp = new Array<Bullet>();
        temp.addAll(bullets);
        return temp;
    }

    public Array<Key> getKey() {
        Array<Key> temp = new Array<Key>();
        temp.addAll(keys);
        return temp;
    }

    public Array<VanishableBLock> getBLocks() {
        Array<VanishableBLock> temp = new Array<VanishableBLock>();
        temp.addAll(vBlocks);
        return temp;
    }

    public Array<RotatingBlock> getRotatingBLocks() {
        Array<RotatingBlock> temp = new Array<RotatingBlock>();
        temp.addAll(rBlocks);
        return temp;
    }

    public Array<Enemy> getEnemy() {
        Array<Enemy> enemies = new Array<Enemy>();
        enemies.addAll(monsters);
        enemies.addAll(ghosts);
        enemies.addAll(stones);
        return enemies;
    }

    public Array<SmallFire> getSmallFire() {
        Array<SmallFire> temp = new Array<SmallFire>();
        temp.addAll(sFire);
        return temp;
    }

    public Array<BigFire> getBigFire() {
        Array<BigFire> temp = new Array<BigFire>();
        temp.addAll(bFire);
        return temp;
    }

    public Array<Treasure> getTreasures() {
        Array<Treasure> temp = new Array<Treasure>();
        temp.addAll(treasures);
        return temp;
    }

    public Array<Gems> getGems() {
        Array<Gems> temp = new Array<Gems>();
        temp.addAll(gems);
        return temp;
    }

    public Array<AntiHasteRune> getAntiHasteRunes() {
        Array<AntiHasteRune> temp = new Array<AntiHasteRune>();
        temp.addAll(antiHasteRunes);
        return temp;
    }

    public Array<InviRune> getInviRunes() {
        Array<InviRune> temp = new Array<InviRune>();
        temp.addAll(inviRunes);
        return temp;
    }

    public Array<Beer> getBeers() {
        Array<Beer> temp = new Array<Beer>();
        temp.addAll(beers);
        return temp;
    }

    public Array<Love> getLoves() {
        Array<Love> temp = new Array<Love>();
        temp.addAll(loves);
        return temp;
    }

}
