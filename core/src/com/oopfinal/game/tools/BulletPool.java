package com.oopfinal.game.tools;

import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Pool;
import com.oopfinal.game.screens.LogInScreen;
import com.oopfinal.game.sprite.Bullet;

public class BulletPool extends Pool<Bullet> {
    private World world;
    private LogInScreen screen;

    public BulletPool(World world, LogInScreen screen) {
        this.world = world;
        this.screen = screen;
    }

    @Override
    protected Bullet newObject() {
        return null;
    }

    @Override
    public void free(Bullet bullet) {

        super.free(bullet);
    }
}