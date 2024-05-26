package com.oopfinal.game.sprite.bullet;

import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.oopfinal.game.OOPFinal;
import com.oopfinal.game.screens.GameScreen;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.oopfinal.game.sprite.characters.Player;

public class Bullet extends Sprite implements Disposable  {




    public enum State {FALLING, IDLING, JUMPING, RUNNING}
        public World world;
        public Body b2body;
        public TextureRegion idle;

        private Animation<TextureRegion> running;
        private Animation<TextureRegion> idling;
        private Animation<TextureRegion> jumping;
        public   float stateTimer;
        private boolean runningRight;
        public float x,y;
        Player player;
        TextureRegion region;
        public float damage=15;

    public float getDamage() {
        return damage;
    }

    public  Bullet(World world, GameScreen screen, float x, float y, Player player, TextureAtlas.AtlasRegion textureatlas){

            super(textureatlas);
            this.y=y;
            this.x=x;
            this.player=player;

            this.world=world;
        defineBullet();

        bulletAnimation();



    }
    void bulletAnimation(){

        idle =new TextureRegion(getTexture(),32*15,0,32*10,25);
        setBounds(0,0,32*16/OOPFinal.PPM,32*2/OOPFinal.PPM);



    }

        public void update(float dt){
            stateTimer += dt;
            setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);

            setRegion(idle);

        }

    @Override
    public void dispose() {


    }
    public Player getPlayer()
    {
        return player;
    }



    public  void  defineBullet(){
            BodyDef bdef =new BodyDef();
            bdef.position.set(x,y);
            bdef.type=BodyDef.BodyType.DynamicBody;
            b2body= world.createBody(bdef);

            FixtureDef fdef =new FixtureDef();
            CircleShape shape=new CircleShape();
            shape.setRadius(15/OOPFinal.PPM);

            fdef.shape=shape;
            fdef.friction=0;
            b2body.createFixture(fdef).setUserData("bullet");
            b2body.setGravityScale(0);

        }
    }

