package com.brentaureli.mariobros.Sprites;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.brentaureli.mariobros.MarioBros;
import com.brentaureli.mariobros.Scenes.Hud;
import com.brentaureli.mariobros.Screens.PlayScreen;
import com.brentaureli.mariobros.Tools.MyCallbackListener;
import com.brentaureli.mariobros.Sprites.Enemy.Enemy;


/**
 * Created by brentaureli on 8/27/15.
 */

public class Mario extends Sprite {
    public enum State { FALLING, JUMPING, STANDING, RUNNING, DEAD };
    public State currentState;
    public State previousState;

    public World world;
    public Body b2body;

    private TextureRegion marioStand;
    private Animation marioRun;
    private TextureRegion marioJump;
    private TextureRegion marioDead;

    private float stateTimer;
    private boolean runningRight;

    private boolean marioIsDead;
    private PlayScreen screen;
    public boolean isFree=false;
    public boolean IfEnemyIsFree=false;
    public BodyDef bdef = new BodyDef();
    private String skin="KARLITO";
    public Mario(PlayScreen screen, String skin){
        //initialize default values
        this.screen = screen;
        this.world = screen.getWorld();
        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        runningRight = true;

        Array<TextureRegion> frames = new Array<TextureRegion>();

        //get run animation frames and add them to marioRun Animation
        for(int i = 1; i < 4; i++)
            frames.add(new TextureRegion(screen.getAtlas().findRegion(skin), i * 16, 0, 16, 36));
        marioRun = new Animation(0.1f, frames);

        frames.clear();

        //get jump animation frames and add them to marioJump Animation
        marioJump = new TextureRegion(screen.getAtlas().findRegion(skin), 80, 0, 16, 36);
        //create texture region for mario standing
        marioStand = new TextureRegion(screen.getAtlas().findRegion(skin), 0, 0, 16, 36);
        //create dead mario texture region
        marioDead = new TextureRegion(screen.getAtlas().findRegion(skin), 96, 0, 16, 36);

        //define mario in Box2d

        defineMario();


        //set initial values for marios location, width and height. And initial frame as marioStand.
        setBounds(0, 0, 16 / MarioBros.PPM, 36 / MarioBros.PPM);

        setRegion(marioStand);

    }

    public void update(float dt){

       // System.out.println(b2body.getPosition().x);
        if(b2body.getPosition().y>=0){
            Float pom, pom2;
            pom=((MyCallbackListener.receiveWsp-(float)2.25)/(float)32.2343330383) *100;
            //pom=this.b2body.getPosition().y;
            if(pom>100){
                pom=(float)100;
            }
            else if(pom<0){
                pom=(float)0;
            }
            pom2=((b2body.getPosition().x-(float)2.25)/(float)32.2343330383) *100;
            if(pom2>100){
                pom2=(float)100;
            }
            else if(pom2<0){
                pom2=(float)0;
            }
            Hud.addScore(pom);
            Hud.addScore2(pom2);
        }

        if (screen.getHud().isTimeUp() && !isDead()) {
            die();
        }
        if(b2body.getPosition().y<0)
        {
            die();
            bdef.position.set(225/ MarioBros.PPM, 36 / MarioBros.PPM);
        }
        //update our sprite to correspond with the position of our Box2D body

        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        //update sprite with the correct frame depending on marios current action
        setRegion(getFrame(dt));


    }

    public TextureRegion getFrame(float dt){
        //get marios current state. ie. jumping, running, standing...
        currentState = getState();
        TextureRegion region;
        //depending on the state, get corresponding animation keyFrame.
        switch(currentState){
            case DEAD:
                region = marioDead;
                break;
            case JUMPING:
                region = marioJump;
                break;
            case RUNNING:
                region = marioRun.getKeyFrame(stateTimer, true);
                break;
            case FALLING:
            case STANDING:
            default:
                region = marioStand;
                break;
        }

        //if mario is running left and the texture isnt facing left... flip it.
        if((b2body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()){
            region.flip(true, false);
            runningRight = false;
        }

        //if mario is running right and the texture isnt facing right... flip it.
        else if((b2body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()){
            region.flip(true, false);
            runningRight = true;
        }

        //if the current state is the same as the previous state increase the state timer.
        //otherwise the state has changed and we need to reset timer.
        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        //update previous state
        previousState = currentState;
        //return our final adjusted frame
        return region;

    }

    public State getState(){


        if(marioIsDead)
            return State.DEAD;
        else if((b2body.getLinearVelocity().y > 0 && currentState == State.JUMPING) || (b2body.getLinearVelocity().y < 0 && previousState == State.JUMPING))
            return State.JUMPING;
        else if(b2body.getLinearVelocity().y < 0)
            return State.FALLING;

        else if(b2body.getLinearVelocity().x != 0)
            return State.RUNNING;
        else
            return State.STANDING;
    }

    public void die() {

        if (!isDead()) {


            MarioBros.manager.get("audio/music/intro.ogg", Music.class).stop();
            MarioBros.manager.get("audio/music/997.ogg", Music.class).stop();
            MarioBros.manager.get("audio/sounds/mariodie.wav", Sound.class).play();
            marioIsDead = true;
            Filter filter = new Filter();
            filter.maskBits = MarioBros.NOTHING_BIT;

            for (Fixture fixture : b2body.getFixtureList()) {
                fixture.setFilterData(filter);
            }
            if(this.b2body.getLinearVelocity().y<0)
                b2body.applyLinearImpulse(new Vector2(0, 4f), b2body.getWorldCenter(), true);
        }
    }

    public boolean isDead(){
        return marioIsDead;
    }

    public float getStateTimer(){
        return stateTimer;
    }

    public void jump(){
        if ( currentState != State.JUMPING && currentState != State.DEAD) {
            b2body.applyLinearImpulse(new Vector2(0, 4f), b2body.getWorldCenter(), true);
            currentState = State.JUMPING;
        }
    }
    public void jumpEnemy(){
        b2body.applyLinearImpulse(new Vector2(0, 6f), b2body.getWorldCenter(), true);
        currentState = State.JUMPING;
    }
    public void hit(Enemy enemy){
        if(!enemy.setToDestroy) {
            die();
        }
    }
    public BodyDef defineMario(){

        bdef.position.set(225/ MarioBros.PPM, 36 / MarioBros.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6 / MarioBros.PPM);
        fdef.filter.categoryBits = MarioBros.MARIO_BIT;
        fdef.filter.maskBits = MarioBros.GROUND_BIT |
                MarioBros.COIN_BIT |
                MarioBros.BRICK_BIT |
                MarioBros.ENEMY_BIT |
                MarioBros.OBJECT_BIT |
                MarioBros.ENEMY_HEAD_BIT |
                MarioBros.ITEM_BIT;

        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);
        shape.setPosition(new Vector2(0/ MarioBros.PPM, -12 / MarioBros.PPM));
        b2body.createFixture(fdef).setUserData(this);

        EdgeShape head = new EdgeShape();
        head.set(new Vector2(-2 / MarioBros.PPM, 6 / MarioBros.PPM), new Vector2(2 / MarioBros.PPM, 6 / MarioBros.PPM));
        fdef.filter.categoryBits = MarioBros.MARIO_HEAD_BIT;
        fdef.shape = head;
        fdef.isSensor = true;

        b2body.createFixture(fdef).setUserData(this);
        return bdef;
    }

}
