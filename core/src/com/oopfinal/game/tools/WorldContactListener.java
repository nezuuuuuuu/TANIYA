package com.oopfinal.game.tools;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.physics.box2d.*;

import com.oopfinal.game.screens.GameScreen;
import com.oopfinal.game.sprite.bullet.Bullet;
import com.oopfinal.game.sprite.characters.Player;

public class WorldContactListener implements ContactListener {

    private final GameScreen gameScreen;

    public WorldContactListener(GameScreen screen) {
        this.gameScreen = screen;
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        handleFootGroundCollision(fixtureA, fixtureB);
        handleBulletCollision(fixtureA, fixtureB);
    }

    private void handleFootGroundCollision(Fixture fixtureA, Fixture fixtureB) {
        if ("foot".equals(fixtureA.getUserData()) || "foot".equals(fixtureB.getUserData())) {
            if ("ground".equals(fixtureA.getUserData()) || "ground".equals(fixtureB.getUserData())) {
                Fixture toSet = "foot".equals(fixtureA.getUserData()) ? fixtureA : fixtureB;

                for (Player player : gameScreen.players) {
                    if (player.b2body == toSet.getBody()) {
                        player.jumped = 0;
                    }
                }
            }
        }
    }

    synchronized private void handleBulletCollision(Fixture fixtureA, Fixture fixtureB) {

        System.out.println("Colided:  "+fixtureA.getUserData()+" and "+fixtureB.getUserData());


        Bullet bullet = null;
        Player player = null;

        //        if("bullet".equals(fixtureA.getUserData()) && "bullet".equals(fixtureB.getUserData())) {
        //            for (Bullet b : gameScreen.bullets) {
        //                if (b.b2body == fixtureA.getBody() || b.b2body == fixtureB.getBody()) {
        //                    gameScreen.toremove.add(b);
        //                }
        //            }
        //            return;
        //        }

        if ("bullet".equals(fixtureA.getUserData()) || "bullet".equals(fixtureB.getUserData())) {

            for (Bullet b : gameScreen.bullets) {
                if (b.b2body == fixtureA.getBody() || b.b2body == fixtureB.getBody()) {
                    bullet = b;

                    if (b.getPlayer().b2body == fixtureA.getBody() || b.getPlayer().b2body == fixtureB.getBody()) {
                        return;
                    }
                    break;
                }
            }
            //
            //            if ("player".equals(fixtureA.getUserData()) || "player".equals(fixtureB.getUserData())) {
            //
            //                for (Player p : gameScreen.players) {
            //                    if (p.b2body == fixtureA.getBody() || p.b2body == fixtureB.getBody()) {
            //                        if (bullet != null && bullet.getPlayer() != p) {
            //                            player = p;
            //                            player.take(bullet, bullet.getPlayer());
            //                        }
            //                        break;
            //                    }
            //                }
            //            }

            if (bullet != null) {

                System.out.println(gameScreen.world.getBodyCount());
                if(gameScreen.toremove.indexOf(bullet)<0){
                    gameScreen.toremove.add(bullet);
                }






            }
        }
    }

    @Override
    public void endContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();
            handleWallCollision(fixtureA, fixtureB);

    }

    private void handleWallCollision(Fixture fixtureA, Fixture fixtureB) {
        if ("wall".equals(fixtureA.getUserData()) || "wall".equals(fixtureB.getUserData())) {
            for (Player player : gameScreen.players) {
                if (player.b2body == fixtureA.getBody() || player.b2body == fixtureB.getBody()) {
                    player.canRun = true;
                }
            }
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
