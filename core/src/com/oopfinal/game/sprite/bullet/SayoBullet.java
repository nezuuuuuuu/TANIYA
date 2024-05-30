package com.oopfinal.game.sprite.bullet;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.oopfinal.game.OOPFinal;
import com.oopfinal.game.screens.GameScreen;
import com.oopfinal.game.sprite.characters.Player;

public class SayoBullet extends Bullet{
    public SayoBullet(World world, GameScreen screen, float x, float y, Player player) {
        super(world, screen, x, y, player, new TextureAtlas(Gdx.files.internal("atlas/skills.pack")).findRegion("allthree"));

    }

    public void update(float dt){
        stateTimer += dt;
        if(b2body.getLinearVelocity().x>0) {
            setPosition(b2body.getPosition().x - getWidth() + .5f, b2body.getPosition().y - getHeight() / 3);
        }else {
            setPosition(b2body.getPosition().x - getWidth() /6, b2body.getPosition().y - getHeight() / 3);

        }
        setRegion(idle);

    }


    @Override
    void bulletAnimation() {
        idle =new TextureRegion(getTexture(),32*15,50,32*10,25);
        setBounds(0,0,32*16/ OOPFinal.PPM,32*2/OOPFinal.PPM);

    }
    public static class PoweredUp extends Bullet {


        public PoweredUp(World world, GameScreen screen, float x, float y, Player player) {
            super(world, screen, x, y, player, new TextureAtlas(Gdx.files.internal("atlas/skills.pack")).findRegion("allthree"));
        }
        void bulletAnimation() {
            idle =new TextureRegion(new Texture("atlas//sayoball.png"),32*3,32*2,32*4,32*4);
            setBounds(0,0,32*1/ OOPFinal.PPM,32*1/OOPFinal.PPM);

        }
    }
}
