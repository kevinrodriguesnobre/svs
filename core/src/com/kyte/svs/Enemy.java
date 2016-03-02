package com.kyte.svs;

import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.ai.utils.Location;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.ai.pfa.DefaultConnection;
import com.badlogic.gdx.ai.pfa.DefaultGraphPath;
import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.ai.pfa.Heuristic;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/**
 * Created by test on 02.03.2016.
 */
public class Enemy extends Character{
    public Enemy()
    {
        super(new TextureRegion(new Texture("Gegner/Gegner.Alien.png"), 32, 32));
    }

    public void move(Player target){
        if(getX() < target.getX()){
            setX(getX() + 1);
        }else{
            setX(getX() - 1);
        }
        if(getY() < target.getY()){
            setY(getY() + 1);
        }else{
            setY(getY() - 1);
        }
    }
}
