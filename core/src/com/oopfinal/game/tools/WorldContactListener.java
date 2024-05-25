package com.oopfinal.game.tools;

import com.badlogic.gdx.physics.box2d.*;

import java.util.ArrayList;
import com.oopfinal.game.sprite.characters.Player;


public class WorldContactListener implements ContactListener {

    ArrayList<Player> players;
    @Override
    public void beginContact(Contact contact) {
        Fixture fixturea= contact.getFixtureA();
        Fixture fixtureb= contact.getFixtureB();

        Fixture toSet=fixtureb;
        if(fixturea.getUserData()=="foot"||fixtureb.getUserData()=="foot"){

            if(fixturea.getUserData()=="ground"||fixtureb.getUserData()=="ground"){

                if(fixturea.getUserData()=="foot"){
                    toSet=fixturea;
                }


                for(Player player:players){
                    if(player.b2body==toSet.getBody()){
                        player.jumped=0;
                    }
                }
            }


        }



    }

    public WorldContactListener(ArrayList<Player> players) {
        this.players = players;
    }

    @Override
    public void endContact(Contact contact) {
        Fixture fixturea= contact.getFixtureA();
        Fixture fixtureb= contact.getFixtureB();
        Fixture toSet=fixtureb;
        if(fixturea.getUserData()=="wall"||fixtureb.getUserData()=="wall"){
            for (Player p: players){
                if(p.b2body==fixtureb.getBody()||p.b2body==fixturea.getBody()){
                    p.canRun=true;
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
