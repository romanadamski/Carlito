package com.brentaureli.mariobros.Sprites.Enemy;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.brentaureli.mariobros.MarioBros;
import com.brentaureli.mariobros.Screens.PlayScreen;
import com.brentaureli.mariobros.Sprites.Mario;

/**
 * Created by jaro a roman dziwkarz on 19.06.2018.
 */

public class BOX extends objects {


    public BOX(PlayScreen screen, float x, float y){
        super(screen, x, y);
     TextureRegion region=new TextureRegion(screen.getAtlasKey().findRegion("BOX"), 0,0,32, 32);



    }

    @Override
    protected void defineObject() {

    }

    @Override
    public void update(float dt) {
        setPosition(getX(), getY());
    }
}
