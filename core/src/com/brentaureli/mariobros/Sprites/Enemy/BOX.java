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

    private TextureRegion stand;

    public BOX(PlayScreen screen, float x, float y){
        super(screen, x, y);

        stand=new TextureRegion(screen.getAtlasKey().findRegion("sk1"), 0,0,32, 32);

        setBounds(0, 0, 32 / MarioBros.PPM, 32 / MarioBros.PPM);

    }

    @Override
    protected void defineObject() {
        BodyDef bdef=new BodyDef();
        bdef.position.set(getX(), getY());

        bdef.type=BodyDef.BodyType.DynamicBody;

        b2body=world.createBody(bdef);

        b2body.setActive(false);
        b2body.setSleepingAllowed(true);
        b2body.setGravityScale(2);

        FixtureDef fdef=new FixtureDef();
        PolygonShape shape=new PolygonShape();
        shape.setAsBox(16/MarioBros.PPM,16/MarioBros.PPM);
        fdef.filter.categoryBits=MarioBros.BRICK_BIT;
        fdef.filter.maskBits=MarioBros.MARIO_BIT |
                MarioBros.OBJECT_BIT |
                MarioBros.GROUND_BIT |
                MarioBros.COIN_BIT |
                MarioBros.BRICK_BIT;

        fdef.shape=shape;
        b2body.createFixture(fdef).setUserData(this);

    }
    public void draw(Batch batch){
            super.draw(batch);
    }


    @Override
    public void update(float dt) {
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        setRegion(stand);
    }
}
