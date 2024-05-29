package com.oopfinal.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.oopfinal.game.OOPFinal;
import com.badlogic.gdx.utils.Align;
import com.oopfinal.game.managers.MusicManager;

public class MainMenuScreen implements Screen {

    private final OOPFinal game;
    private Stage stage;
    private Texture backgroundTexture;
    private Animation<TextureRegion> backgroundAnimation;
    private SpriteBatch batch;
    private float stateTime;
    private MusicManager musicManager = null;


    Skin btnSkin = new Skin(Gdx.files.internal("skins/uiskin.json"));

    Label titleLabel = new Label("TANIYA", btnSkin, "title");
    Label subtitleLabel = new Label("BATTLEGROUNDS", btnSkin, "subtitle");
    Label playLabel = new Label("Start Game", btnSkin);
    Label optionsLabel = new Label("Options", btnSkin);
    Label historyLabel = new Label("History", btnSkin);
    Label exitLabel = new Label("Exit", btnSkin);


    Table table = new Table();

    public MainMenuScreen(OOPFinal game, MusicManager musicManager) {
        this.game = game;
        this.musicManager = musicManager;
        stage = new Stage(new ScreenViewport());
        batch = new SpriteBatch();
        backgroundTexture = new Texture(Gdx.files.internal("mainmenu/mainmenubg.png"));
        Gdx.input.setInputProcessor(stage);

        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        float fontScale = Math.min(screenWidth, screenHeight) / 600f; // Adjust 600f based on your preference
        int fontSize = Math.round(16 * fontScale);

        Label.LabelStyle labelStyle = btnSkin.get(Label.LabelStyle.class);
        labelStyle.font.getData().setScale(fontScale);

        playLabel.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                 game.setScreen(new GameScreen(game));
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                playLabel.setColor(Color.BLACK);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                playLabel.setColor(Color.WHITE);
            }

            @Override
            public boolean mouseMoved(InputEvent event, float x, float y) {
                return super.mouseMoved(event, x, y);
            }
        });

        optionsLabel.addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                game.setScreen(new OptionScreen(game));
//            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                optionsLabel.setColor(Color.BLACK);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {

                optionsLabel.setColor(Color.WHITE);
            }

            @Override
            public boolean mouseMoved(InputEvent event, float x, float y) {
                return super.mouseMoved(event, x, y);
            }
        });

        historyLabel.addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                game.setScreen(new OptionScreen(game));
//            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                historyLabel.setColor(Color.BLACK);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {

                historyLabel.setColor(Color.WHITE);
            }

            @Override
            public boolean mouseMoved(InputEvent event, float x, float y) {
                return super.mouseMoved(event, x, y);
            }
        });

        exitLabel.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                exitLabel.setColor(Color.BLACK);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                exitLabel.setColor(Color.WHITE);
            }

            @Override
            public boolean mouseMoved(InputEvent event, float x, float y) {
                return super.mouseMoved(event, x, y);
            }
        });


//        Table table = new Table();
        table.setFillParent(true);
        table.center();

        table.add(titleLabel).padBottom(10).row();
        table.add(subtitleLabel).padBottom(50).row();
        playLabel.setAlignment(Align.center);
        optionsLabel.setAlignment(Align.center);
        exitLabel.setAlignment(Align.center);
        historyLabel.setAlignment(Align.center);


        table.add(playLabel).fillX().uniformX();
//        table.row().pad(-50, 0, 10, 0);
        table.add(optionsLabel).fillX().uniformX();
        table.row();
        table.add(historyLabel).fillX().uniformX();
        table.row();
        table.add(exitLabel).fillX().uniformX();


        stage.addActor(table);

        Image background = new Image(backgroundTexture);
        background.setFillParent(true);
        stage.addActor(background);
        background.toBack();


    }

    @Override
    public void show() {
        musicManager.play();
    }

    @Override
    public void render(float delta) {
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);

        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        float fontScale = Math.min(screenWidth, screenHeight) / 600f; // Adjust 600f based on your preference
        float subtitleFontScale = fontScale * 0.8f; // Adjust the scale factor for the subtitle font

        // Apply font scale to the labels
        Label.LabelStyle labelStyle = btnSkin.get(Label.LabelStyle.class);
        labelStyle.font.getData().setScale(fontScale);

        titleLabel.getStyle().font.getData().setScale(fontScale);
        titleLabel.setSize(titleLabel.getPrefWidth() * fontScale, titleLabel.getPrefHeight() * fontScale);

        subtitleLabel.getStyle().font.getData().setScale(subtitleFontScale);
        subtitleLabel.setSize(subtitleLabel.getPrefWidth() * subtitleFontScale, subtitleLabel.getPrefHeight() * subtitleFontScale);

        playLabel.getStyle().font.getData().setScale(fontScale);
        playLabel.setSize(playLabel.getPrefWidth() * fontScale, playLabel.getPrefHeight() * fontScale);

        optionsLabel.getStyle().font.getData().setScale(fontScale);
        optionsLabel.setSize(optionsLabel.getPrefWidth() * fontScale, optionsLabel.getPrefHeight() * fontScale);

        historyLabel.getStyle().font.getData().setScale(fontScale);
        historyLabel.setSize(historyLabel.getPrefWidth() * fontScale, historyLabel.getPrefHeight() * fontScale);

        exitLabel.getStyle().font.getData().setScale(fontScale);
        exitLabel.setSize(exitLabel.getPrefWidth() * fontScale, exitLabel.getPrefHeight() * fontScale);

        // Update table layout
        table.clear();
        table.add(titleLabel).padBottom(10).row();
        table.add(subtitleLabel).padBottom(100 * fontScale).row(); // Reduced padding below subtitle
        table.add(playLabel).fillX().uniformX().padBottom(10 * fontScale).row(); // Added padding below each label
        table.add(optionsLabel).fillX().uniformX().padBottom(10 * fontScale).row();
        table.add(historyLabel).fillX().uniformX().padBottom(10 * fontScale).row();
        table.add(exitLabel).fillX().uniformX();
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
        backgroundTexture.dispose();
    }
}
