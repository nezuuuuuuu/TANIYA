package com.oopfinal.game.sprite.characters;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.oopfinal.game.OOPFinal;
import com.oopfinal.game.screens.GameScreen;
import com.oopfinal.game.sprite.bullet.Bullet;
import com.oopfinal.game.tools.BulletPool;

import java.util.ArrayList;

public abstract class Player extends Sprite {


    public enum State {FALLING, IDLING, JUMPING, RUNNING}
    public World world;
    public Body b2body;
    public TextureRegion idle;
    public State currentState;
    public State previousState;
    public Animation<TextureRegion> running;
    public Animation<TextureRegion> idling;
    public Animation<TextureRegion> jumping;
    public  float stateTimer;
    public boolean runningRight;

    public int jumped=0;


    public CircleShape shape;
    Rectangle footer;
    GameScreen screen;

    public boolean canRun=true;
    float x;
    float y;
    BulletPool bulletPool;
    Float hp=100f;




    public  Player(World world, GameScreen screen, TextureAtlas.AtlasRegion atlasRegion){
        super(atlasRegion);
        this.screen=screen;
        this.world=world;
        bulletPool=new BulletPool(world,screen);

        currentState=State.IDLING;
        previousState=State.IDLING;
        stateTimer=0;
        runningRight=true;
        Array<TextureRegion> frames=new Array<>();

        for(int i=0; i<15;i++)
            frames.add(new TextureRegion(getTexture(),i*32*6,0,32*6,32*8));
        running=new Animation<TextureRegion>(0.1f,frames);
        frames.clear();




        definePlayer();
        idle =new TextureRegion(getTexture(),0,0,32*6,32*8);
        setBounds(0,0,32*3/OOPFinal.PPM,32*3/OOPFinal.PPM);
        setRegion(idle);
   }
   public void update(float dt){
        setPosition(b2body.getPosition().x-getWidth()/2,b2body.getPosition().y-getHeight()/2);
        setRegion(getFrame(dt));
//       System.out.println(jumped);
       x=b2body.getPosition().x;
       y=b2body.getPosition().y;

   }

   public  TextureRegion getFrame(float dt){
        currentState=getState();
        TextureRegion region;
        switch (currentState){
            case RUNNING:
                region= running.getKeyFrame(stateTimer,true);
                break;
            case FALLING:

            case JUMPING:
                region=idle;
                break;
            default:
                region= idle;
                break;
        }
        if ((b2body.getLinearVelocity().x>0.5||runningRight)&&region.isFlipX()) {
           region.flip(true,false);
           runningRight=true;
//            System.out.println(b2body.getLinearVelocity().y);
       }
       else if((b2body.getLinearVelocity().x<-.5||!runningRight)&&!region.isFlipX()){
            region.flip(true,false);
            runningRight=false;
//            System.out.println(b2body.getLinearVelocity().y);

        }
        stateTimer=currentState==previousState? stateTimer+dt:0;
        previousState = currentState;
       return region;
   }
   public  State getState(){

       if(b2body.getLinearVelocity().y>1){
//           System.out.println("going up");
           return State.JUMPING;
       }
       else if(b2body.getLinearVelocity().y<-1) {
//           System.out.println("going down");

           return State.FALLING;
        }
       else if(b2body.getLinearVelocity().x!=0) {
           return State.RUNNING;
       } else {

           return State.IDLING;
       }

   }
    public  void  definePlayer(){
        BodyDef bdef =new BodyDef();
        bdef.position.set(32*10/ OOPFinal.PPM+2,500/OOPFinal.PPM);
        bdef.type=BodyDef.BodyType.DynamicBody;
        b2body= world.createBody(bdef);

        FixtureDef fdef =new FixtureDef();
        shape=new CircleShape();

        shape.setRadius(35/OOPFinal.PPM);

        fdef.shape=shape;
        fdef.friction=0;
        b2body.createFixture(fdef).setUserData("body");


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
    }


    abstract public void handleInput(float dt, ArrayList<Bullet> bullets);
    public void  setJumped(){
        jumped=(jumped+1)%3;
    }


    public void take(Bullet bullet,Player player){
            hp=hp-bullet.getDamage();
    }

    abstract void createBullet(float x, float y, float impulseX, float impulsey, ArrayList<Bullet> bullets);

}
