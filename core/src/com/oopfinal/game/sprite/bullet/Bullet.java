package com.oopfinal.game.sprite.bullet;

import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.oopfinal.game.OOPFinal;
import com.oopfinal.game.screens.LogInScreen;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.oopfinal.game.sprite.characters.Player;

public class Bullet extends Sprite implements Disposable  {



    public enum State {FALLING, IDLING, JUMPING, RUNNING}
        public World world;
        public Body b2body;
        private TextureRegion idle;

        private Animation<TextureRegion> running;
        private Animation<TextureRegion> idling;
        private Animation<TextureRegion> jumping;
        private  float stateTimer;
        private boolean runningRight;
        public float x,y;
        Player player;
        TextureRegion region;

    public  Bullet(World world, LogInScreen screen, float x, float y, Player player, TextureAtlas.AtlasRegion textureatlas){

            super(textureatlas);
            this.y=y;
            this.x=x;
            this.player=player;

            this.world=world;
        defineBullet();

        bulletAnimation();



    }
    void bulletAnimation(){
        idle =new TextureRegion(getTexture(),0,0,32*6,32*8);
        setBounds(0,0,32*3/OOPFinal.PPM,32*3/OOPFinal.PPM);
        Array<TextureRegion> frames=new Array<>();

        for(int i=1; i<3;i++){
            for(int j=0;j<4;j++)
                frames.add(new TextureRegion(getTexture(),j*5+1,6*32*i,32*5,32*5));
        }

        running=new Animation<TextureRegion>(0.07f,frames);
        frames.clear();


    }

        public void update(float dt){
            stateTimer += dt;
            setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
            region = running.getKeyFrame(stateTimer, true);
            setRegion(region);

        }

    @Override
    public void dispose() {


    }



    public  void  defineBullet(){
            BodyDef bdef =new BodyDef();
            bdef.position.set(x,y);
            bdef.type=BodyDef.BodyType.DynamicBody;
            b2body= world.createBody(bdef);

            FixtureDef fdef =new FixtureDef();
            CircleShape shape=new CircleShape();
            shape.setRadius(35/OOPFinal.PPM);

            fdef.shape=shape;
            fdef.friction=0;
            b2body.createFixture(fdef);
            b2body.setGravityScale(0);
        }
    }

