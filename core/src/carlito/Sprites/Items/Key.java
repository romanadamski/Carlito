package carlito.Sprites.Items;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import carlito.CarlitoEscape;
import carlito.Screens.PlayScreen;
import carlito.Sprites.Carlito;

/**
 * Created by romek95a on 19.06.2018.
 */

public class Key extends Item {

        TextureRegion key;
    public Key(PlayScreen screen, float x, float y) {
        super(screen, x, y);

        key=new TextureRegion(screen.getAtlasKey().findRegion("Key"), 0,0,16, 16);
        setBounds(getX(), getY(), 16/ CarlitoEscape.PPM, 16/ CarlitoEscape.PPM);
    }

    @Override
    public void defineItem() {
        BodyDef bdef=new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type=BodyDef.BodyType.StaticBody;
        body=world.createBody(bdef);

        FixtureDef fdef=new FixtureDef();
        CircleShape shape=new CircleShape();
        shape.setRadius(6/ CarlitoEscape.PPM);
        fdef.filter.categoryBits= CarlitoEscape.ITEM_BIT;
        fdef.filter.maskBits= CarlitoEscape.MARIO_BIT |
                CarlitoEscape.OBJECT_BIT |
                CarlitoEscape.GROUND_BIT |
                CarlitoEscape.COIN_BIT |
                CarlitoEscape.BRICK_BIT;

        fdef.shape=shape;
        body.createFixture(fdef).setUserData(this);
    }

    @Override
    public void use(Carlito carlito) {
        destroy();
        carlito.isFree=true;
    }

    @Override
    public void update(float dt) {
        super.update(dt);
        setPosition(getX(), getY());
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
        setRegion(key);

    }
}
