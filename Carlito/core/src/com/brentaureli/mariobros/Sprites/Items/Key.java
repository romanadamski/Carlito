package com.brentaureli.mariobros.Sprites.Items;

import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.brentaureli.mariobros.MarioBros;
import com.brentaureli.mariobros.Screens.PlayScreen;
import com.brentaureli.mariobros.Sprites.Mario;

/**
 * Created by romek95a on 19.06.2018.
 */

public class Key extends Item {

    public Key(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        setRegion(screen.getAtlasKey().findRegion("Key"), 0,0,16, 16);
        velocity=new Vector2(0,0);
        setBounds(getX(), getY(), 16/MarioBros.PPM, 16/MarioBros.PPM);
    }

    @Override
    public void defineItem() {
        BodyDef bdef=new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type=BodyDef.BodyType.DynamicBody;
        body=world.createBody(bdef);

        FixtureDef fdef=new FixtureDef();
        CircleShape shape=new CircleShape();
        shape.setRadius(6/ MarioBros.PPM);
        fdef.filter.categoryBits=MarioBros.ITEM_BIT;
        fdef.filter.maskBits=MarioBros.MARIO_BIT |
                MarioBros.OBJECT_BIT |
                MarioBros.GROUND_BIT |
                MarioBros.COIN_BIT |
                MarioBros.BRICK_BIT;

        fdef.shape=shape;
        body.createFixture(fdef).setUserData(this);
    }

    @Override
    public void use(Mario mario) {
        destroy();
        mario.isFree=true;
    }

    @Override
    public void update(float dt) {
        super.update(dt);
        setPosition(getX(), getY());
        body.setLinearVelocity(velocity);

    }
}
