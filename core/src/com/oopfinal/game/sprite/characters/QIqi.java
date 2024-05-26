package com.oopfinal.game.sprite.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.oopfinal.game.screens.GameScreen;
import com.oopfinal.game.sprite.bullet.Bullet;
import com.oopfinal.game.sprite.bullet.QiqiBullet;

import java.util.ArrayList;

public class QIqi extends Player{
    Bullet teleportBullet;
    boolean teleporting=false;


    public QIqi(World world, GameScreen screen) {
        super(world, screen,new TextureAtlas("C:\\Users\\kylak\\Documents\\Files_Nico\\Object_Oriented_Programming\\OOPFinal\\assets\\maps\\atlas\\Sayo.pack").findRegion("SAYO_ALL_ANIMATION"));



    }



    public void handleInput(float dt, ArrayList<Bullet> bullets) {
        this.b2body.setLinearVelocity(0f, this.b2body.getLinearVelocity().y);
        if (Gdx.input.isKeyPressed(Input.Keys.D) && this.b2body.getLinearVelocity().x <= 2 && canRun) {
            this.b2body.applyLinearImpulse(3f, 0,x,y, true);

        }

        if (Gdx.input.isKeyPressed(Input.Keys.A) && this.b2body.getLinearVelocity().x >= -2 && canRun) {
            this.b2body.applyLinearImpulse(-3f, 0,x,y, true);
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.W)) {
            if (jumped!=2) {
                this.b2body.applyLinearImpulse(new Vector2(0, 6f), this.b2body.getWorldCenter(), true);
                setJumped();
            }
        }

        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            this.b2body.applyLinearImpulse(new Vector2(0, -0.2f), this.b2body.getWorldCenter(), true);
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {

            if (!this.isFlipX()) {
                createBullet(x + 0.6f, y+0.1f, 30f, 0, bullets);
            } else {

                createBullet(x - 0.6f, y+0.1f, -30f, 0, bullets);
            }
        }

        if (Gdx.input.isKeyPressed(Input.Keys.X)) {

            if(teleportBullet==null) {
                if(runningRight){
                    teleportBullet=createTeleportBullet(x + 1f, y, 4f, 0, bullets);

                }else {
                    teleportBullet=createTeleportBullet(x - 1f, y, -4f, 0, bullets);

                }

            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {

                if (teleportBullet != null) {
                    float tx = teleportBullet.getX();
                    float ty = teleportBullet.getY();
                    world.destroyBody(teleportBullet.b2body);
                    bullets.remove(teleportBullet);

                    teleportBullet = null;
                    b2body.setTransform(tx+3, ty, 0);
                    b2body.applyLinearImpulse(new Vector2(0, 1),b2body.getWorldCenter(),true);

            }

        }


    }

    public void update(float dt){

        setPosition(b2body.getPosition().x-getWidth()/2,b2body.getPosition().y-getHeight()/2);
        setRegion(getFrame(dt));

        x=b2body.getPosition().x;
        y=b2body.getPosition().y;

    }
    void createBullet(float x, float y, float impulseX, float impulsey, ArrayList<Bullet> bullets) {

        Bullet b = new QiqiBullet(world, screen, x, y,this);
        bullets.add(b);
        b.b2body.applyLinearImpulse(new Vector2(impulseX, impulsey), b.b2body.getWorldCenter(), true);
        if(impulseX>0){
        b.idle.flip(true,false);
        }
    }
    Bullet createTeleportBullet(float x, float y, float impulseX, float impulsey, ArrayList<Bullet> bullets) {


        Bullet b = new QiqiBullet.QiqiTeleportBullet(world, screen, x, y,this);
        bullets.add(b);

        b.b2body.applyLinearImpulse(new Vector2(impulseX, impulsey), b.b2body.getWorldCenter(), true);
        if(impulseX>0){
            b.idle.flip(true,false);
        }
        return b;
    }






}
