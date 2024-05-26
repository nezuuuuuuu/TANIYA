package com.oopfinal.game.sprite.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.oopfinal.game.screens.GameScreen;
import com.oopfinal.game.sprite.bullet.Bullet;
import com.oopfinal.game.sprite.bullet.SayoBullet;

import java.util.ArrayList;

public class Sayo extends Player{
    public Sayo(World world, GameScreen screen) {
        super(world, screen,new TextureAtlas(Gdx.files.internal("atlas/Sayo.pack")).findRegion("SAYO_ALL_ANIMATION"));


    }



  public void handleInput(float dt, ArrayList<Bullet> bullets) {
        this.b2body.setLinearVelocity(0f, this.b2body.getLinearVelocity().y);
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && this.b2body.getLinearVelocity().x <= 2 && canRun) {
            this.b2body.applyLinearImpulse(3f, 0,x,y, true);

        }

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && this.b2body.getLinearVelocity().x >= -2 && canRun) {
            this.b2body.applyLinearImpulse(-3f, 0,x,y, true);
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            if (jumped!=2) {
                this.b2body.applyLinearImpulse(new Vector2(0, 6f), this.b2body.getWorldCenter(), true);
                setJumped();
            }
        }

        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            this.b2body.applyLinearImpulse(new Vector2(0, -0.2f), this.b2body.getWorldCenter(), true);
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            if (!this.isFlipX()) {
                createBullet(x + 0.4f, y, 4f, 0, bullets);
            } else {
                createBullet(x - 0.3f, y, -4f, 0, bullets);
            }
        }

        if (Gdx.input.isKeyPressed(Input.Keys.X)) {
            createBullet(x, y - 0.3f, 0, -9f, bullets);
            createBullet(x, y + 0.3f, 0, 9f, bullets);
            createBullet(x + 0.3f, y, 9f, 0, bullets);
            createBullet(x - 0.3f, y, -9f, 0, bullets);
            createBullet(x - 0.3f, y + 0.3f, -9f, 9f, bullets);
            createBullet(x + 0.3f, y - 0.3f, 9f, -9f, bullets);
            createBullet(x + 0.3f, y + 0.3f, 9f, 9f, bullets);
            createBullet(x - 0.3f, y - 0.3f, -9f, -9f, bullets);
        }


    }
    void createBullet(float x, float y, float impulseX, float impulsey, ArrayList<Bullet> bullets) {
        Bullet b = new SayoBullet(world, screen, x, y,this);
        bullets.add(b);
        b.b2body.applyLinearImpulse(new Vector2(impulseX, impulsey), b.b2body.getWorldCenter(), true);
    }

}
