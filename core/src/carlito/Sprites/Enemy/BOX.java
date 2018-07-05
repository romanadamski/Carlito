package carlito.Sprites.Enemy;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

import carlito.CarlitoEscape;
import carlito.Screens.PlayScreen;

/**
 * Created by jaro a roman dziwkarz on 19.06.2018.
 */

public class BOX extends objects {

    private TextureRegion stand;

    public BOX(PlayScreen screen, float x, float y){
        super(screen, x, y);

        stand=new TextureRegion(screen.getAtlasKey().findRegion("sk1"), 0,0,32, 32);

        setBounds(0, 0, 32 / CarlitoEscape.PPM, 32 / CarlitoEscape.PPM);

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
        shape.setAsBox(16/ CarlitoEscape.PPM,16/ CarlitoEscape.PPM);
        fdef.filter.categoryBits= CarlitoEscape.BRICK_BIT;
        fdef.filter.maskBits= CarlitoEscape.MARIO_BIT |
                CarlitoEscape.OBJECT_BIT |
                CarlitoEscape.GROUND_BIT |
                CarlitoEscape.COIN_BIT |
                CarlitoEscape.BRICK_BIT;

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
