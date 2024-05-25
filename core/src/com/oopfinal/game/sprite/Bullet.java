package com.oopfinal.game.sprite;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Disposable;
import com.oopfinal.game.OOPFinal;
import com.oopfinal.game.screens.GameScreen;

public class Bullet extends Sprite implements Disposable  {



    public enum State {FALLING, IDLING, JUMPING, RUNNING}
        public World world;
        public Body b2body;
        private TextureRegion idle;
        com.oopfinal.game.sprite.Player.State currentState;
        com.oopfinal.game.sprite.Player.State previousState;
        private Animation<TextureRegion> running;
        private Animation<TextureRegion> idling;
        private Animation<TextureRegion> jumping;
        private  float stateTimer;
        private boolean runningRight;
        public float x,y;

         Player player;

    public  Bullet(World world, GameScreen screen, float x, float y, Player player){

            super(screen.getAtlas().findRegion("SAYO_ALL_ANIMATION"));
            this.y=y;
            this.x=x;



            this.world=world;
            definePlayer();

            idle =new TextureRegion(getTexture(),0,0,32*6,32*8);
            setBounds(0,0,32*3/OOPFinal.PPM,32*3/OOPFinal.PPM);


        }
        public void update(float dt){
            setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);

            setRegion(idle);


        }

    @Override
    public void dispose() {


    }


        public com.oopfinal.game.sprite.Player.State getState(){

            if(b2body.getLinearVelocity().y>0){
                return com.oopfinal.game.sprite.Player.State.JUMPING;
            }
            else if(b2body.getLinearVelocity().y<0) {
                return com.oopfinal.game.sprite.Player.State.FALLING;
            }
            else if(b2body.getLinearVelocity().x!=0) {
                return com.oopfinal.game.sprite.Player.State.RUNNING;
            } else {

                return com.oopfinal.game.sprite.Player.State.IDLING;
            }

        }
        public  void  definePlayer(){
            BodyDef bdef =new BodyDef();
            bdef.position.set(x,y);
            bdef.type=BodyDef.BodyType.DynamicBody;
            b2body= world.createBody(bdef);

            FixtureDef fdef =new FixtureDef();
            CircleShape shape=new CircleShape();
            shape.setRadius(5/OOPFinal.PPM);

            fdef.shape=shape;
            fdef.friction=0;
            b2body.createFixture(fdef);
            b2body.setGravityScale(0);
        }
    }

