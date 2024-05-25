package com.oopfinal.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.oopfinal.game.OOPFinal;
import com.oopfinal.game.sprite.Bullet;
import com.oopfinal.game.sprite.Player;
import com.oopfinal.game.tools.BulletPool;
import com.oopfinal.game.tools.CollisionWithMap;
import com.oopfinal.game.tools.WorldContactListener;

import java.util.ArrayList;

public class GameScreen implements Screen {
    OOPFinal game;
    Viewport viewport;
    private OrthographicCamera camera;
    TmxMapLoader mapLoader;
    TiledMap map;
    OrthogonalTiledMapRenderer renderer;
    Bullet bullet;


   //box2dVariables
    private World world;
    Box2DDebugRenderer b2dr;
    //player
    Player player;
    //atlas
    private TextureAtlas atlas;
    float x,y;

    //BULLETS

    ArrayList<Bullet> bullets;
    BulletPool bpool;
    ArrayList<Player> players;
    CollisionWithMap collisionWithMap;

    public GameScreen(OOPFinal game) {
        bullets=new ArrayList<>();
        players=new ArrayList<>();
        atlas = new TextureAtlas(Gdx.files.internal("maps/atlas/Sayo.pack")); // Changed to use Gdx.files


        this.game=game;
        camera=new OrthographicCamera();
        viewport=new FitViewport(OOPFinal.V_WIDTH/OOPFinal.PPM,OOPFinal.V_HEIGHT/OOPFinal.PPM,camera);
        mapLoader=new TmxMapLoader();
        map = mapLoader.load(Gdx.files.internal("maps/MapByNico.tmx").path()); // Changed to use Gdx.files

        renderer=new OrthogonalTiledMapRenderer(map,1/OOPFinal.PPM);
        camera.position.set(viewport.getWorldWidth()/2,viewport.getWorldHeight()/2,0);
        world=new World(new Vector2(0,-10),true);
        b2dr=new Box2DDebugRenderer();
        collisionWithMap= new CollisionWithMap(world,map);


        player=new Player(world,this);
        players.add(player);
//        player.setPosition((viewport.getWorldWidth())/2,(viewport.getWorldHeight())/2);
        bpool=new BulletPool(world,this);

        world.setContactListener(new WorldContactListener(players));

    }
    public TextureAtlas getAtlas(){
        return atlas;
    }
    public void handleInput(float delta){

        for(Player p:players){
            p.handleInput(delta,bullets,x,y);
        }
        handleBullets(delta);


    }
    public  void update(float delta){
        handleInput(delta);

        world.step(1/60f,1,2);
        for (Player p:players){
            p.update(delta);
        }


        for(Bullet b: bullets){
            b.update(delta);



        }
//        camera.position.x=player.b2body.getPosition().x;
        x=player.b2body.getPosition().x;
        y=player.b2body.getPosition().y;
//        camera.position.y=player.b2body.getPosition().y;
        camera.update();
        renderer.setView(camera);

    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        update(delta);

        // Toggle full-screen mode when F11 is pressed
        if (Gdx.input.isKeyJustPressed(Input.Keys.F11)) {
            if (Gdx.graphics.isFullscreen()) {
                Gdx.graphics.setWindowedMode(1280, 720);
            } else {
                Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
            }
        }

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.setProjectionMatrix(camera.combined);
        renderer.render();

        b2dr.render(world, camera.combined);

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();

        for (Player p : players) {
            player.draw(game.batch);
        }

        for (Bullet b : bullets) {
            b.draw(game.batch);
        }
        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width,height);
        camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();

    }
    void  handleBullets(float delta){
        ArrayList<Bullet> toremove=new ArrayList<>();
        for(Bullet b:bullets){
            if(Math.abs(b.b2body.getLinearVelocity().x)<1f&&Math.abs(b.b2body.getLinearVelocity().y)<1f){
                toremove.add(b);

            }
        }

        bullets.removeAll(toremove);


        for (Bullet bllets:bullets){
            bllets.update(delta);
        }
        for(Bullet b:toremove){
            world.destroyBody(b.b2body);
            bpool.free(b);
        }
    }
}
