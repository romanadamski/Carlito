package carlito.Tools;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

import carlito.CarlitoEscape;
import carlito.Sprites.Items.Item;
import carlito.Sprites.Carlito;
import carlito.Sprites.TileObjects.InteractiveTileObject;
import carlito.Sprites.Enemy.Enemy;

/**
 * Created by brentaureli on 9/4/15.
 */
public class WorldContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        switch (cDef){
            case CarlitoEscape.MARIO_HEAD_BIT | CarlitoEscape.BRICK_BIT:
            case CarlitoEscape.MARIO_HEAD_BIT | CarlitoEscape.COIN_BIT:
                if(fixA.getFilterData().categoryBits == CarlitoEscape.MARIO_HEAD_BIT)
                    ((InteractiveTileObject) fixB.getUserData()).onHeadHit((Carlito) fixA.getUserData());
                else
                    ((InteractiveTileObject) fixA.getUserData()).onHeadHit((Carlito) fixB.getUserData());
                break;
            case CarlitoEscape.ENEMY_HEAD_BIT | CarlitoEscape.MARIO_BIT:
                if(fixA.getFilterData().categoryBits == CarlitoEscape.ENEMY_HEAD_BIT)
                    ((Enemy)fixA.getUserData()).hitOnHead((Carlito) fixB.getUserData());
                else
                    ((Enemy)fixB.getUserData()).hitOnHead((Carlito) fixA.getUserData());
                break;
            case CarlitoEscape.ENEMY_BIT | CarlitoEscape.OBJECT_BIT:
                if(fixA.getFilterData().categoryBits == CarlitoEscape.ENEMY_BIT)
                    ((Enemy)fixA.getUserData()).reverseVelocity(true, false);
                else
                    ((Enemy)fixB.getUserData()).reverseVelocity(true, false);
                break;
            case CarlitoEscape.MARIO_BIT | CarlitoEscape.ENEMY_BIT:
                if(fixA.getFilterData().categoryBits == CarlitoEscape.MARIO_BIT)
                    ((Carlito) fixA.getUserData()).hit((Enemy)fixB.getUserData());
                else
                    ((Carlito) fixB.getUserData()).hit((Enemy)fixA.getUserData());
                break;
            case CarlitoEscape.ITEM_BIT | CarlitoEscape.OBJECT_BIT:
                if(fixA.getFilterData().categoryBits == CarlitoEscape.ITEM_BIT)
                    ((Item)fixA.getUserData()).reverseVelocity(true, false);
                else
                    ((Item)fixB.getUserData()).reverseVelocity(true, false);
                break;
            case CarlitoEscape.ITEM_BIT | CarlitoEscape.MARIO_BIT:
                if(fixA.getFilterData().categoryBits == CarlitoEscape.ITEM_BIT)
                    ((Item)fixA.getUserData()).use((Carlito) fixB.getUserData());
                else
                    ((Item)fixB.getUserData()).use((Carlito) fixA.getUserData());
                break;
        }
    }

    @Override
    public void endContact(Contact contact) {
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
