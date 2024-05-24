package com.oopfinal.game.sprite;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.oopfinal.game.OOPFinal;
import com.oopfinal.game.screens.LogInScreen;

public class Sayo extends Player{
    public Sayo(World world, LogInScreen screen) {
        super(world, screen);
        currentState=State.IDLING;
        previousState=State.IDLING;
        stateTimer=0;
        runningRight=true;
        Array<TextureRegion> frames=new Array<>();
//        for(int i=0; i<1;i++)
//            frames.add(new TextureRegion(getTexture(),0,0,32*6,32*8));
//        idling=new Animation<TextureRegion>(0.1f,frames);
//        frames.clear();

        for(int i=0; i<15;i++)
            frames.add(new TextureRegion(getTexture(),i*32*6,0,32*6,32*8));
        running=new Animation<TextureRegion>(0.1f,frames);
        frames.clear();



        this.world=world;
        definePlayer();
        idle =new TextureRegion(getTexture(),0,0,32*6,32*8);
        setBounds(0,0,32*3/ OOPFinal.PPM,32*3/OOPFinal.PPM);
        setRegion(idle);
    }
    public void update(float dt){
        setPosition(b2body.getPosition().x-getWidth()/2,b2body.getPosition().y-getHeight()/2);
        setRegion(getFrame(dt));


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
            System.out.println(b2body.getLinearVelocity().y);
        }
        else if((b2body.getLinearVelocity().x<-.5||!runningRight)&&!region.isFlipX()){
            region.flip(true,false);
            runningRight=false;
            System.out.println(b2body.getLinearVelocity().y);

        }
        stateTimer=currentState==previousState? stateTimer+dt:0;
        previousState = currentState;
        return region;
    }
    public  State getState(){

        if(b2body.getLinearVelocity().y>1){
            System.out.println("going up");
            return State.JUMPING;
        }
        else if(b2body.getLinearVelocity().y<-1) {
            System.out.println("going down");

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
        b2body.createFixture(fdef);


    }

}
