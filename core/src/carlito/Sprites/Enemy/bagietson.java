package carlito.Sprites.Enemy;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;

import carlito.CarlitoEscape;
import carlito.Screens.PlayScreen;
import carlito.Sprites.Carlito;

/**
 * Created by jarekciotajebanaszmata on 8/27/15.
 */
public class bagietson extends Enemy {

    private float stateTime;
    private Animation walkAnimation;
    private Array<TextureRegion> frames;
    private Animation walkAnimation2;

    private boolean destroyed;
    private boolean runningright;
    float angle;
    int i=0;

    public bagietson(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        frames = new Array<TextureRegion>();

        for(int i = 0; i < 3; i++)
            frames.add(new TextureRegion(screen.getAtlas().findRegion("Bagieta" ), i * 16, 10, 16, 36));

        walkAnimation = new Animation(0.2f, frames);


        stateTime = 0;
        setBounds(0, 0, 16 / CarlitoEscape.PPM, 45 / CarlitoEscape.PPM);
        setToDestroy = false;
        destroyed = false;
        angle = 0;
    }

    @Override
    protected void defineEnemy() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);



        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        PolygonShape square=new PolygonShape();
        shape.setRadius(6 / CarlitoEscape.PPM);
        fdef.filter.categoryBits = CarlitoEscape.ENEMY_BIT;
        fdef.filter.maskBits = CarlitoEscape.GROUND_BIT |
                CarlitoEscape.COIN_BIT |
                CarlitoEscape.BRICK_BIT |
                CarlitoEscape.ENEMY_BIT |
                CarlitoEscape.OBJECT_BIT |
                CarlitoEscape.MARIO_BIT;

        square.setAsBox(6/ CarlitoEscape.PPM,6/ CarlitoEscape.PPM);
        fdef.shape=square;
        b2body.createFixture(fdef).setUserData(this);

        /*
        b2body.createFixture(fdef).setUserData(this);
        */
        fdef.shape = shape;
        shape.setPosition(new Vector2(0/ CarlitoEscape.PPM, -13 / CarlitoEscape.PPM));
        b2body.createFixture(fdef).setUserData(this);

        PolygonShape head = new PolygonShape();

        Vector2[] vertice = new Vector2[4];
        vertice[0] = new Vector2(-5, 10).scl(1 / CarlitoEscape.PPM);
        vertice[1] = new Vector2(5, 10).scl(1 / CarlitoEscape.PPM);
        vertice[2] = new Vector2(-5, 4).scl(1 / CarlitoEscape.PPM);
        vertice[3] = new Vector2(5, 4).scl(1 / CarlitoEscape.PPM);
        head.set(vertice);

        fdef.shape = head;
        fdef.restitution = 0.5f;
        fdef.isSensor = true;
        fdef.filter.categoryBits = CarlitoEscape.ENEMY_HEAD_BIT;
        b2body.createFixture(fdef).setUserData(this);

    }
    public void draw(Batch batch){
        if(!destroyed || stateTime < 1)
            super.draw(batch);
    }
    @Override
    public void update(float dt) {

        TextureRegion region;

        stateTime += dt;
        i++;
        if(setToDestroy && !destroyed){
            world.destroyBody(b2body);
            destroyed = true;

            setRegion(new TextureRegion(screen.getAtlas().findRegion("Bagieta"), 48, 12, 16, 36));
            stateTime = 0;
        }
        else if(!destroyed) {
            b2body.setLinearVelocity(velocity);
            setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
            region=walkAnimation.getKeyFrame(stateTime, true);
            setRegion(region);

            if(i==150) {
               reverseVelocity(true,false);
               i=0;
            }
            if((b2body.getLinearVelocity().x < 0 || !runningright) && !region.isFlipX()){
                region.flip(true, false);
                runningright = false;
            }

            else if((b2body.getLinearVelocity().x > 0 || runningright) && region.isFlipX()){
                region.flip(true, false);
                runningright = true;
            }
        }
    }

    @Override
    public void hitOnHead(Carlito carlito) {
        if(!carlito.isDead()) {
            setToDestroy = true;
            if(carlito.b2body.getLinearVelocity().y<0)
                carlito.jumpEnemy();
        }

    }

    @Override
    public void hitByEnemy(Enemy enemy) {
          reverseVelocity(true, false);
    }

}
