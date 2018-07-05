package carlito.Sprites.Items;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import carlito.MarioBros;
import carlito.Screens.PlayScreen;
import carlito.Sprites.Mario;

/**
 * Created by romek95a on 19.06.2018.
 */

public class Key extends Item {

        TextureRegion key;
    public Key(PlayScreen screen, float x, float y) {
        super(screen, x, y);

        key=new TextureRegion(screen.getAtlasKey().findRegion("Key"), 0,0,16, 16);
        setBounds(getX(), getY(), 16/MarioBros.PPM, 16/MarioBros.PPM);
    }

    @Override
    public void defineItem() {
        BodyDef bdef=new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type=BodyDef.BodyType.StaticBody;
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
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
        setRegion(key);

    }
}
