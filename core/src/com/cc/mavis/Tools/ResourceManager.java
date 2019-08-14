package com.cc.mavis.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class ResourceManager {

    private AssetManager assetManager;

    //atlas for all sprites
    public TextureAtlas atlas;
    public TextureAtlas bubblesAtlas;
    public TextureAtlas fireAtlas;

    //other object
    public final TextureRegion emptyStar;
    public final TextureRegion filledStar;
    public final TextureRegion checkedCB;
    public final TextureRegion uncheckCB;
    final TextureRegion emptyButton;
    final TextureRegion inviButton;
    final TextureRegion antiHasteButton;
    public final TextureRegion backButton;
    final TextureRegion drunkBuff;
    final TextureRegion inviBuff;
    final TextureRegion antiHasteBuff;

    //sounds
    Sound antiHaste;
    public Sound break_love;
    public Sound bullet;
    public Sound drunk;
    public Sound enemy_dead;
    public Sound fire_sound;
    public Sound get_gem;
    public Sound get_love;
    public Sound getsomething;
    public Sound getsomething2;
    public Sound invi_rune;
    public Music john_cena;
    public Sound keylock_combined;
    public Sound mavisDie;
    public Sound wing_flap;

    //parallax BG
    public Array<Texture> parallaxNaturePack;
    public Array<Texture> parallaxDefaultPack;
    public Array<Texture> parallaxDesertPack;
    public Array<Texture> parallaxForestPack;
    public Array<Texture> parallaxCoolPack;

    //fonts
    public final BitmapFont roboto64;
    public final BitmapFont greenpil;
    public final BitmapFont impactlabel64;
    private final BitmapFont littlebird64;
    public final BitmapFont littlebird84;
    private final BitmapFont zenga64;

    public ResourceManager(){
        assetManager = new AssetManager();
        //load assets
        assetManager.load("atlas/font_atlas.pack",TextureAtlas.class);
        assetManager.load("atlas/textures.pack",TextureAtlas.class);
        assetManager.load("atlas/bubbles_atlas.pack",TextureAtlas.class);
        assetManager.load("atlas/fire_atlas.pack",TextureAtlas.class);
        assetManager.load("SFX/antiHaste.wav",Sound.class);
        assetManager.load("SFX/break_love.mp3",Sound.class);
        assetManager.load("SFX/bullet.wav",Sound.class);
        assetManager.load("SFX/drunk.mp3",Sound.class);
        assetManager.load("SFX/enemy_dead.mp3",Sound.class);
        assetManager.load("SFX/fire.wav",Sound.class);
        assetManager.load("SFX/get_gem.wav",Sound.class);
        assetManager.load("SFX/get_love.mp3",Sound.class);
        assetManager.load("SFX/getsomething.wav",Sound.class);
        assetManager.load("SFX/getsomething2.wav",Sound.class);
        assetManager.load("SFX/invi_rune.mp3",Sound.class);
        assetManager.load("SFX/john_cena.mp3",Sound.class);
        assetManager.load("SFX/keylock_combined.wav",Sound.class);
        assetManager.load("SFX/mavisDie.wav",Sound.class);
        assetManager.load("SFX/wing_flap.wav",Sound.class);

        assetManager.finishLoading();

        //declare objects
        atlas = assetManager.get("atlas/textures.pack",TextureAtlas.class);
        bubblesAtlas = assetManager.get("atlas/bubbles_atlas.pack",TextureAtlas.class);
        fireAtlas = assetManager.get("atlas/fire_atlas.pack",TextureAtlas.class);


        TextureAtlas font_atlas = assetManager.get("atlas/font_atlas.pack",TextureAtlas.class);
        roboto64 = new BitmapFont(Gdx.files.internal("fonts/roboto64.fnt"),font_atlas.findRegion("roboto64"),false);
        greenpil = new BitmapFont(Gdx.files.internal("fonts/greenpil.fnt"),font_atlas.findRegion("greenpil"),false);
        impactlabel64 = new BitmapFont(Gdx.files.internal("fonts/impactlabel64.fnt"),font_atlas.findRegion("impactlabel64"),false);
        littlebird64 = new BitmapFont(Gdx.files.internal("fonts/littlebird64.fnt"),font_atlas.findRegion("littlebird64"),false);
        littlebird84 = new BitmapFont(Gdx.files.internal("fonts/littlebird84.fnt"),font_atlas.findRegion("littlebird84"),false);
        zenga64 = new BitmapFont(Gdx.files.internal("fonts/zenga64.fnt"),font_atlas.findRegion("zenga64"),false);

        roboto64.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        greenpil.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        impactlabel64.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        littlebird84.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        littlebird64.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        zenga64.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        emptyStar = new TextureRegion(atlas.findRegion("empty_star"));
        filledStar = new TextureRegion(atlas.findRegion("filled_star"));
        checkedCB = new TextureRegion(atlas.findRegion("checked"));
        uncheckCB = new TextureRegion(atlas.findRegion("unchecked"));
        backButton = new TextureRegion(atlas.findRegion("back_button"));
        emptyButton = new TextureRegion(atlas.findRegion("empty_button"));
        inviButton = new TextureRegion(atlas.findRegion("invi_button"));
        antiHasteButton = new TextureRegion(atlas.findRegion("anti_haste_button"));
        drunkBuff = new TextureRegion(atlas.findRegion("drunk_buff"));
        inviBuff = new TextureRegion(atlas.findRegion("invi_buff"));
        antiHasteBuff = new TextureRegion(atlas.findRegion("anti_haste_buff"));

        //Parallax BG
        parallaxNaturePack = new Array<Texture>();
        parallaxDefaultPack = new Array<Texture>();
        parallaxDesertPack = new Array<Texture>();
        parallaxForestPack = new Array<Texture>();
        parallaxCoolPack = new Array<Texture>();

        for(int i = 1; i <=11;i++){
            parallaxNaturePack.add(new Texture(Gdx.files.internal("parallaxBG/naturePack/img"+i+".png")));
            parallaxNaturePack.get(parallaxNaturePack.size-1).setWrap(Texture.TextureWrap.MirroredRepeat, Texture.TextureWrap.MirroredRepeat);
        }
        for(int i = 1; i <=6;i++){
            parallaxDefaultPack.add(new Texture(Gdx.files.internal("parallaxBG/defaultPack/img"+i+".png")));
            parallaxDefaultPack.get(parallaxDefaultPack.size-1).setWrap(Texture.TextureWrap.MirroredRepeat, Texture.TextureWrap.MirroredRepeat);
        }
        for(int i = 1; i <=5;i++){
            parallaxDesertPack.add(new Texture(Gdx.files.internal("parallaxBG/desertPack/img"+i+".png")));
            parallaxDesertPack.get(parallaxDesertPack.size-1).setWrap(Texture.TextureWrap.MirroredRepeat, Texture.TextureWrap.MirroredRepeat);
        }
        for(int i = 1; i <=5;i++){
            parallaxForestPack.add(new Texture(Gdx.files.internal("parallaxBG/forestPack/img"+i+".png")));
            parallaxForestPack.get(parallaxForestPack.size-1).setWrap(Texture.TextureWrap.MirroredRepeat, Texture.TextureWrap.MirroredRepeat);
        }
        for(int i = 1; i <=7;i++){
            parallaxCoolPack.add(new Texture(Gdx.files.internal("parallaxBG/coolPack/img"+i+".png")));
            parallaxCoolPack.get(parallaxCoolPack.size-1).setWrap(Texture.TextureWrap.MirroredRepeat, Texture.TextureWrap.MirroredRepeat);
        }

        antiHaste = assetManager.get("SFX/antiHaste.wav", Sound.class);
        break_love = assetManager.get("SFX/break_love.mp3", Sound.class);
        bullet = assetManager.get("SFX/bullet.wav", Sound.class);
        drunk = assetManager.get("SFX/drunk.mp3", Sound.class);
        enemy_dead = assetManager.get("SFX/enemy_dead.mp3", Sound.class);
        fire_sound = assetManager.get("SFX/fire.wav", Sound.class);
        get_gem = assetManager.get("SFX/get_gem.wav", Sound.class);
        get_love = assetManager.get("SFX/get_love.mp3", Sound.class);
        getsomething = assetManager.get("SFX/getsomething.wav", Sound.class);
        getsomething2 = assetManager.get("SFX/getsomething2.wav", Sound.class);
        invi_rune = assetManager.get("SFX/invi_rune.mp3", Sound.class);
        john_cena = Gdx.audio.newMusic(Gdx.files.internal("SFX/john_cena.mp3"));
        keylock_combined = assetManager.get("SFX/keylock_combined.wav", Sound.class);
        mavisDie = assetManager.get("SFX/mavisDie.wav", Sound.class);
        wing_flap = assetManager.get("SFX/wing_flap.wav", Sound.class);

    }

    public void dispose(){
        assetManager.dispose();
        atlas.dispose();
        roboto64.dispose();
        greenpil.dispose();
        littlebird64.dispose();
        littlebird84.dispose();
        zenga64.dispose();
        impactlabel64.dispose();
    }
}
