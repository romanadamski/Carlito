package com.brentaureli.mariobros.Sprites.Enemy;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.brentaureli.mariobros.Screens.PlayScreen;
import com.brentaureli.mariobros.Sprites.Mario;

public abstract class objects extends Sprite {

    protected World world;
    protected PlayScreen screen;
    public Body b2body;
    public Vector2 velocity;

    public objects(PlayScreen screen, float x, float y){
        this.world = screen.getWorld();
        this.screen = screen;
        setPosition(x, y);
        defineObject();
        velocity = new Vector2(0, 0);
        b2body.setActive(false);
    }

    protected abstract void defineObject();

    public abstract void update(float dt);

    public boolean reverseVelocity(boolean x, boolean y){
        if(x) {
            velocity.x = -velocity.x;
            return true;
        }
        if(y)
            velocity.y = -velocity.y;

        return false;

    }
}
