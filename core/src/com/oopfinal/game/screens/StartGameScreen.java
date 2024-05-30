package com.oopfinal.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.oopfinal.game.OOPFinal;

public class StartGameScreen implements Screen {
    private final OOPFinal game;
    private Stage stage;
    private Skin skin;
    private TextField player1Input;
    private TextField player2Input;
    private OrthographicCamera camera;
    private Texture backgroundTexture;
    private Image backgroundImage;

    public StartGameScreen(final OOPFinal game) {
        this.game = game;
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, 800, 480);
        this.stage = new Stage(new ScreenViewport(camera));
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("skins/uiskin.json"));

        backgroundTexture = new Texture(Gdx.files.internal("img/start-game-bg.png"));
        backgroundImage = new Image(backgroundTexture);
        backgroundImage.setFillParent(true);

        stage.addActor(backgroundImage);

        Table table = new Table();
        table.setFillParent(true);

        Label player1Label = new Label("Player 1 Name:", skin);
        player1Input = new TextField("", skin);

        Label player2Label = new Label("Player 2 Name:", skin);
        player2Input = new TextField("", skin);

        TextButton startButton = new TextButton("Start Game", skin);
        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen(game)); // Removed typecasting
                dispose();
            }
        });

        TextButton backButton = new TextButton("BACK", skin);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainMenuScreen(game));
                dispose();
            }
        });

        table.add(player1Label).pad(10);
        table.add(player1Input).pad(10).width(200).row();

        table.add(player2Label).pad(10);
        table.add(player2Input).pad(10).width(200).row();

        table.add(startButton).colspan(2).pad(10).row();
        table.add(backButton).colspan(2).pad(10);

        stage.addActor(table);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
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
        stage.dispose();
        skin.dispose();
        backgroundTexture.dispose();
    }
}
