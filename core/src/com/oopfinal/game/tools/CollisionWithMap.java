package com.oopfinal.game.tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.physics.box2d.*;
import com.oopfinal.game.OOPFinal;

import java.util.ArrayList;

public class CollisionWithMap {
    public ArrayList<Body> objects=new ArrayList<>();
    public CollisionWithMap(World world, TiledMap map) {


        BodyDef bdef=new BodyDef();

        FixtureDef fdef =new FixtureDef();
        Body body;
        objects=new ArrayList<>();
        PolygonShape shape=new PolygonShape();

        //collissions
//        ---blocks
        for (MapObject object : map.getLayers().get(6).getObjects()) {
            Rectangle rectangle=((RectangleMapObject) object).getRectangle();

            bdef.type=BodyDef.BodyType.StaticBody;
            bdef.position.set((rectangle.getX()+rectangle.getWidth()/2)/ OOPFinal.PPM,(rectangle.getY()+rectangle.getHeight()/2)/OOPFinal.PPM);
            body= world.createBody(bdef);
            shape.setAsBox((rectangle.getWidth()/2)/OOPFinal.PPM,(rectangle.getHeight()/2)/OOPFinal.PPM);
            fdef.shape=shape;
            body.createFixture(fdef).setUserData("wall");
            objects.add(body);

        }

        for (MapObject object:map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)){

            Rectangle rectangle=((RectangleMapObject) object).getRectangle();

            bdef.type=BodyDef.BodyType.StaticBody;
            bdef.position.set((rectangle.getX()+rectangle.getWidth()/2)/ OOPFinal.PPM,(rectangle.getY()+rectangle.getHeight()/2)/OOPFinal.PPM);
            body= world.createBody(bdef);
            shape.setAsBox((rectangle.getWidth()/2)/OOPFinal.PPM,(rectangle.getHeight()/2)/OOPFinal.PPM);
            fdef.shape=shape;
            body.createFixture(fdef).setUserData("ground");
            objects.add(body);
        }


    }

    private static PolygonShape getRectangle(RectangleMapObject rectangleObject) {
        Rectangle rectangle = rectangleObject.getRectangle();
        PolygonShape polygon = new PolygonShape();
        Vector2 size = new Vector2((rectangle.x + rectangle.width * 0.5f) / OOPFinal.PPM,
                (rectangle.y + rectangle.height * 0.5f ) / OOPFinal.PPM);
        polygon.setAsBox(rectangle.width * 0.5f /OOPFinal.PPM,
                rectangle.height * 0.5f / OOPFinal.PPM,
                size,
                0.0f);
        return polygon;
    }

    private static PolygonShape getPolygon(PolygonMapObject polygonObject) {
        PolygonShape polygon = new PolygonShape();
        float[] vertices = polygonObject.getPolygon().getTransformedVertices();

        float[] worldVertices = new float[vertices.length];

        for (int i = 0; i < vertices.length; ++i) {

            worldVertices[i] = vertices[i] / OOPFinal.PPM;
        }

        polygon.set(worldVertices);
        return polygon;
    }

    private static ChainShape getPolyline(PolylineMapObject polylineObject) {
        float[] vertices = polylineObject.getPolyline().getTransformedVertices();
        Vector2[] worldVertices = new Vector2[vertices.length / 2];

        for (int i = 0; i < vertices.length / 2; ++i) {
            worldVertices[i] = new Vector2();
            worldVertices[i].x = vertices[i * 2] / OOPFinal.PPM;
            worldVertices[i].y = vertices[i * 2 + 1] / OOPFinal.PPM;
        }

        ChainShape chain = new ChainShape();
        chain.createChain(worldVertices);
        return chain;
    }







}
