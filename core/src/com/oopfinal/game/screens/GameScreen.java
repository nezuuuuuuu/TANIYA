package com.oopfinal.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.oopfinal.game.OOPFinal;
import com.oopfinal.game.hud.TopHud;
import com.oopfinal.game.sprite.bullet.Bullet;
import com.oopfinal.game.sprite.bullet.NotRemovable;
import com.oopfinal.game.sprite.characters.Player;
import com.oopfinal.game.sprite.characters.QIqi;
import com.oopfinal.game.sprite.characters.Sayo;
import com.oopfinal.game.tools.BulletPool;
import com.oopfinal.game.tools.CollisionWithMap;
import com.oopfinal.game.tools.WorldContactListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class GameScreen implements Screen {
    OOPFinal game;
    private OrthographicCamera camera;
    Viewport viewport;
    TmxMapLoader mapLoader;
    TiledMap map;
    OrthogonalTiledMapRenderer renderer;
    Bullet bullet;

    private Player playerOne;
    private Player playerTwo;
    private TopHud hud;

    //box2dVariables
    public World world;
    Box2DDebugRenderer b2dr;
    //player
    Player player;
    //atlas
    private TextureAtlas atlas;
    float x,y;

    //BULLETS
    public     ArrayList<Bullet> bullets;

    public BulletPool bpool;


    public ArrayList<Player> players;
    public List<Bullet> toremove= Collections.synchronizedList(new ArrayList<Bullet>());

    CollisionWithMap collisionWithMap;

    public GameScreen(OOPFinal game) {
        bullets=new ArrayList<>();
        players=new ArrayList<>();
        atlas = new TextureAtlas(Gdx.files.internal("atlas/Sayo.pack"));

        this.game=game;
        camera=new OrthographicCamera();
        viewport=new FillViewport(OOPFinal.V_WIDTH/OOPFinal.PPM,OOPFinal.V_HEIGHT/OOPFinal.PPM,camera);
        mapLoader=new TmxMapLoader();
        map=mapLoader.load(String.valueOf(Gdx.files.internal("maps/MapByNico.tmx")));

        renderer=new OrthogonalTiledMapRenderer(map,1/OOPFinal.PPM);
        camera.position.set(viewport.getWorldWidth()/2,viewport.getWorldHeight()/2,0);
        world=new World(new Vector2(0,-10),true);
        b2dr=new Box2DDebugRenderer();
        collisionWithMap= new CollisionWithMap(world,map);

//        players.add(player=new Sayo(world,this));
//        players.add( new QIqi(world,this));
//        player.setPosition((viewport.getWorldWidth())/2,(viewport.getWorldHeight())/2);
        bpool=new BulletPool(world,this);
        players.add(playerOne = new QIqi(world, this)); // Your player one initialization
        players.add(playerTwo = new Sayo(world, this)); // Your player two initialization

        world.setContactListener(new WorldContactListener(this));
        hud = new TopHud(game.batch, playerOne, playerTwo);
    }

    public TextureAtlas getAtlas(){
        return atlas;
    }

    public void handlInput(float delta){
        for(Player p:players){
            p.handleInput(delta,bullets);
        }
        handleBullets(delta);
    }
    public  void update(float delta){
        handlInput(delta);

        world.step(1/60f,1,2);
        for (Player p:players){
            p.update(delta);
        }

        for(Bullet b: bullets){
            b.update(delta);

        }
//        camera.position.x=player.b2body.getPosition().x;
//        camera.position.y=player.b2body.getPosition().y;
        camera.update();
        renderer.setView(camera);

        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
        hud.update(delta);
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        update(delta);

        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.setProjectionMatrix(camera.combined);
        renderer.render();

//        b2dr.render(world,camera.combined);

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();

        for (Player p:players){
            p.draw(game.batch);
        }
        for(Bullet b: bullets){
            b.draw(game.batch);
        }

        game.batch.end();
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width,height);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
    }

    void  handleBullets(float delta){
        bullets.removeAll(toremove);
        for (Bullet bllets : bullets) {
            bllets.update(delta);
        }
        for (Bullet b : toremove) {
            world.destroyBody(b.b2body);
            bpool.free(b);
        }
        toremove.clear();
    }

}
