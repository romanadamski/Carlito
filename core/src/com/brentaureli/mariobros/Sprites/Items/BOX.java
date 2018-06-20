package com.brentaureli.mariobros.Sprites.Items;

import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.brentaureli.mariobros.MarioBros;
import com.brentaureli.mariobros.Screens.PlayScreen;
import com.brentaureli.mariobros.Sprites.Mario;

/**
 * Created by jaro a roman dziwkarz on 19.06.2018.
 */

public class BOX extends Item {

    public BOX(PlayScreen screen, float x, float y) {
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
        PolygonShape shape = new PolygonShape();

        shape.setAsBox(6 / MarioBros.PPM, 6 / MarioBros.PPM);

        fdef.shape = shape;
        body.createFixture(fdef);

    }

    @Override
    public void use(Mario mario) {
    }

    @Override
    public void update(float dt) {
        super.update(dt);
        setPosition(getX(), getY());
        body.setLinearVelocity(velocity);

    }
}
