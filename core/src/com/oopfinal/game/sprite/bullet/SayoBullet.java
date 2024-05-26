package com.oopfinal.game.sprite.bullet;

import com.badlogic.gdx.Gdx;
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



    void BulletAnimation(Animation<TextureRegion> running) {


        Array<TextureRegion> frames=new Array<>();

        for(int i=1; i<3;i++){
            for(int j=0;j<4;j++)
                frames.add(new TextureRegion(getTexture(),j*5+1,6*32*i,32*5,32*5));
        }

        running=new Animation<TextureRegion>(0.07f,frames);
        frames.clear();
        return;
    }

    @Override
    void bulletAnimation() {
        idle =new TextureRegion(getTexture(),32*15,0,32*10,25);
        setBounds(0,0,32*16/ OOPFinal.PPM,32*2/OOPFinal.PPM);

    }
}
