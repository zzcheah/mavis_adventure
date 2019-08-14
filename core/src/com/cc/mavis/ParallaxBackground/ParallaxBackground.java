package com.cc.mavis.ParallaxBackground;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;

public class ParallaxBackground extends Actor {
    private float scroll;
    private Array<Texture> layers;
    private final int LAYER_SPEED_DIFFERENCE = 2;


    private float x,y,width,heigth,scaleX,scaleY;
    private int originX, originY,rotation,srcY;
    private boolean flipX,flipY;

    private float speed,srcX;

    public ParallaxBackground(Array<Texture> textures){
        layers = textures;
        for(int i = 0; i <textures.size;i++){
            layers.get(i).setWrap(Texture.TextureWrap.MirroredRepeat, Texture.TextureWrap.MirroredRepeat);
        }
        scroll = 0;
        speed = 0.1f;

        x = y = originX = originY = rotation = srcY = 0;
        width =  2077;
        heigth = 1080;
        scaleX = scaleY = 1;
        flipX = flipY = false;
    }

    public void setSpeed(float newSpeed,int level){
        if(level%2==0)
            this.speed = -newSpeed;
        else
            this.speed = newSpeed;
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(getColor().r, getColor().g, getColor().b, getColor().a * parentAlpha);
        scroll+=speed;
        for(int i = 0;i<layers.size;i++) {
            srcX = scroll + i*this.LAYER_SPEED_DIFFERENCE *scroll;
            batch.draw(layers.get(i), x, y, originX, originY, width, heigth,scaleX,scaleY,rotation,(int)srcX,srcY,layers.get(i).getWidth(),layers.get(i).getHeight(),flipX,flipY);
        }
    }
}
