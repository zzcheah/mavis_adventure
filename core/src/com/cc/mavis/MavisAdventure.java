package com.cc.mavis;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.cc.mavis.Screens.MainMenu;
import com.cc.mavis.Tools.ResourceManager;

public class MavisAdventure extends Game {

	//resolution & ppm
	public static final int V_WIDTH = 400;
	public static final int V_HEIGHT = 208;
	public static final float PPM = 100;

	//collision bits
    public static final short NOTHING_BIT = 0;
    public static final short MAVIS_BIT = 1;
    public static final short GEM_BIT = 2;
    public static final short GROUND_BIT = 4;
    public static final short LOCK_BIT = 8;
    public static final short TREASURE_BIT =16;
    public static final short KEY_BIT =32;
    public static final short SHARP_BIT = 64;
	public static final short BEER_BIT = 128; //HARAM
    public static final short INVIRUNE_BIT = 256 ;
    public static final short ANTIHASTERUNE_BIT = 512 ;
	public static final short ENEMY_BIT =1024;
	public static final short BULLET_BIT = 2048;
	public static final short BORDER_BIT = 4096;
	public static final short DOOR_BIT = 8192;
    public static final short LOVE_BIT = 16384 ;

    //variables
    public static Preferences prefs;
    public SpriteBatch batch;
    public static ResourceManager manager;
    public static Music bgm;
    public static boolean bgmCheck;
    public static boolean sfxCheck;
    public static int stageNum;

	@Override
	public void create () {
	    bgmCheck = true;
	    sfxCheck = true;
	    prefs = Gdx.app.getPreferences("My Preferences");
        System.out.println(prefs.getInteger("highestStage")+"HAHA1");
	    if(prefs.getInteger("highestStage")<1||prefs.getInteger("highestStage")>5){
            prefs.putInteger("highestStage",1);
            prefs.flush();
        }

	    stageNum = prefs.getInteger("highestStage");

		batch = new SpriteBatch();
        bgm = Gdx.audio.newMusic(Gdx.files.internal("SFX/bgm.mp3"));
        bgm.setLooping(true);
        bgm.play();
		manager = new ResourceManager();
        setScreen(new MainMenu(this));
    }

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
	    super.dispose();
		batch.dispose();
		manager.dispose();
		bgm.dispose();
	}

}
