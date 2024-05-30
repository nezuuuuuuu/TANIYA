package com.oopfinal.game.sprite.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.oopfinal.game.OOPFinal;
import com.oopfinal.game.screens.GameScreen;
import com.oopfinal.game.sprite.bullet.Bullet;
import com.oopfinal.game.sprite.bullet.QiqiBullet;

import java.util.ArrayList;

public class QIqi extends Player{
    Bullet teleportBullet;
    boolean teleporting=false;

    int cooldown=0;



    public QIqi(World world, GameScreen screen) {
        super(world, screen, new TextureAtlas(Gdx.files.internal("atlas/qiqi.pack")).findRegion("walk"));



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

        if (Gdx.input.isKeyPressed(Input.Keys.Q)) {

            if(teleportBullet==null) {
                if(runningRight){
                    teleportBullet=createTeleportBullet(x + 1f, y, 4f, 0, bullets);

                }else {
                    teleportBullet=createTeleportBullet(x - 1f, y, -4f, 0, bullets);

                }
                teleportBullet.update(dt);
                cooldown++;

            }
        }

        else if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {


            if (teleportBullet != null) {
                teleportBullet.update(dt);

                float tx = teleportBullet.getX();
                float ty = teleportBullet.getY();

                if(Math.abs(tx - x) > 2 || Math.abs(ty - y) > 2){
                    System.out.println("Bullet: "+tx+" "+ty);
                    System.out.println("Payer: "+x+" "+y);

                    world.destroyBody(teleportBullet.b2body);
                    bullets.remove(teleportBullet);

                    teleportBullet = null;
                    b2body.setTransform(tx+3, ty, 0);
                    b2body.applyLinearImpulse(new Vector2(0, 1),b2body.getWorldCenter(),true);
                    System.out.println("Payer: "+b2body.getPosition().x+" "+b2body.getPosition().y);

                }

            }

        }


    }

    public void update(float dt){

        setPosition(b2body.getPosition().x-getWidth()/2,b2body.getPosition().y-getHeight()/2);
        setRegion(getFrame(dt));

        x=b2body.getPosition().x;
        y=b2body.getPosition().y;
        System.out.println(x+" "+y);



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
    public  void  definePlayer(){

        bdef =new BodyDef();

        bdef.position.set(17.3218f , 6.021669f);
        bdef.type=BodyDef.BodyType.DynamicBody;
        b2body= world.createBody(bdef);

        FixtureDef fdef =new FixtureDef();
        shape=new CircleShape();

        shape.setRadius(35/OOPFinal.PPM);

        fdef.shape=shape;
        fdef.friction=0;
        b2body.createFixture(fdef).setUserData("player");


        EdgeShape head=new EdgeShape();
        head.set(new Vector2(-9/OOPFinal.PPM,50/OOPFinal.PPM),new Vector2(9/OOPFinal.PPM,50/OOPFinal.PPM));
        fdef.shape=head;
        fdef.isSensor=true;
        b2body.createFixture(fdef).setUserData("head");


        EdgeShape foot=new EdgeShape();
        foot.set(new Vector2(-9/OOPFinal.PPM,-50/OOPFinal.PPM),new Vector2(9/OOPFinal.PPM,-50/OOPFinal.PPM));
        fdef.shape=foot;
        fdef.isSensor=true;
        b2body.createFixture(fdef).setUserData("foot");
        idle =new TextureRegion(getTexture(),0,0,32*7,32*9);
        setBounds(0,0,32*3/OOPFinal.PPM,32*3/OOPFinal.PPM);
        setRegion(idle);
        Array<TextureRegion> frames=new Array<>();

        for(int i=0; i<18;i++)
            frames.add(new TextureRegion(getTexture(),i*32*8,0,32*7,32*9));
        running=new Animation<TextureRegion>(0.07f,frames);
        frames.clear();

    }

    public void setToInitial() {
        // Ensure the body is not null
        if (b2body != null) {
            // Check if the world is not locked
            if (!b2body.getWorld().isLocked()) {
                // Set the transform with position and angle (angle is 0 here)
                b2body.setTransform(17.3218f, 6.021669f, 0);

                // Apply a linear impulse to the body
                b2body.applyLinearImpulse(new Vector2(0, 1), b2body.getWorldCenter(), true);

                // Set the health of the player
                setHealth(2000f);
            } else {
                System.err.println("Cannot set transform: world is locked.");
            }
        } else {
            System.err.println("Cannot set transform: b2body is null.");
        }
    }




}
