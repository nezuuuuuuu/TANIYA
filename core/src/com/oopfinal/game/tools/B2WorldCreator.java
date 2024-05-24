package com.oopfinal.game.tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.oopfinal.game.OOPFinal;

public class B2WorldCreator {
    public B2WorldCreator(World world, TiledMap map) {


        BodyDef bdef=new BodyDef();
        PolygonShape shape=new PolygonShape();
        FixtureDef fdef =new FixtureDef();
        Body body;

        //collissions
        //---blocks
        for (MapObject object:map.getLayers().get(1).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rectangle=((RectangleMapObject) object).getRectangle();
            bdef.type=BodyDef.BodyType.StaticBody;
            bdef.position.set((rectangle.getX()+rectangle.getWidth()/2)/ OOPFinal.PPM,(rectangle.getY()+rectangle.getHeight()/2)/OOPFinal.PPM);
            body= world.createBody(bdef);
            shape.setAsBox((rectangle.getWidth()/2)/OOPFinal.PPM,(rectangle.getHeight()/2)/OOPFinal.PPM);
            fdef.shape=shape;
            body.createFixture(fdef);
        }

    }
}
