package com.oopfinal.game.sprite.bullet;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.oopfinal.game.OOPFinal;
import com.oopfinal.game.screens.GameScreen;
import com.oopfinal.game.sprite.characters.Player;

public class QiqiBullet extends Bullet {
    public QiqiBullet(World world, GameScreen screen, float x, float y, Player player) {
        super(world, screen, x, y, player, new TextureAtlas(Gdx.files.internal("atlas/skills.pack")).findRegion("allthree"));

    }


    void bulletAnimation(){

        idle =new TextureRegion(getTexture(),32*15,0,32*10,25);
        setBounds(0,0,32*16/OOPFinal.PPM,32*2/OOPFinal.PPM);



    }
    public static class QiqiTeleportBullet extends Bullet implements NotRemovable{


        public QiqiTeleportBullet(World world, GameScreen screen, float x, float y, Player player) {
            super(world, screen, x, y, player,  new TextureAtlas(Gdx.files.internal("atlas/skills.pack")).findRegion("allthree"));
            this.damage=0;
        }
        public  void  defineBullet(){
            BodyDef bdef =new BodyDef();
            bdef.position.set(x,y);
            bdef.type=BodyDef.BodyType.DynamicBody;
            b2body= world.createBody(bdef);

            FixtureDef fdef =new FixtureDef();
            CircleShape shape=new CircleShape();
            shape.setRadius(50/ OOPFinal.PPM);

            fdef.shape=shape;
            fdef.friction=0;
            b2body.createFixture(fdef).setUserData("QiqiTeleport");
            b2body.setGravityScale(0);
        }
        void bulletAnimation(){
            idle =new TextureRegion(new Texture("atlas//teleport.png"),32*2,32*2,32*14,32*14);
            setBounds(0,0,32*3/OOPFinal.PPM,32*3/OOPFinal.PPM);
        }
        public void update(float dt){
            stateTimer += dt;
            setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);

            setRegion(idle);

        }
    }

    public void update(float dt){
        stateTimer += dt;
        if(b2body.getLinearVelocity().x>0) {
            setPosition(b2body.getPosition().x - getWidth() + 0.8f, b2body.getPosition().y - getHeight() / 2);
        }else{
            setPosition(b2body.getPosition().x - getWidth() + 4.3f, b2body.getPosition().y - getHeight() / 2);

        }
        setRegion(idle);

    }


}
