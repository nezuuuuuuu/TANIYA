package com.oopfinal.game.screens;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.oopfinal.game.OOPFinal;

public class GameOverScreen implements Screen {

    private Stage stage;
    private SpriteBatch batch;
    private OOPFinal game;
    private OrthographicCamera camera;
    private Skin skin, skin2;
    private Texture backgroundTexture;
    private Image backgroundImage;

    public GameOverScreen(OOPFinal game) {
        this.game = game;
        this.batch = new SpriteBatch();
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        this.stage = new Stage(new ScreenViewport(), batch);

        // Load the skin from the uiskin.json file
        skin = new Skin(Gdx.files.internal("skins/uiskin.json"));

        backgroundTexture = new Texture(Gdx.files.internal("img/red-gradient-bg.jpg"));
        backgroundImage = new Image(backgroundTexture);
        backgroundImage.setFillParent(true);

        stage.addActor(backgroundImage);

        Label.LabelStyle labelStyle = skin.get(Label.LabelStyle.class);
        labelStyle.font.getData().setScale(1.5f);

        Table table = new Table();
        table.setFillParent(true);

        Label gameOverLabel = new Label("GAME OVER", skin, "title");
        TextButton playAgainButton = new TextButton("Tap to Play Again", skin);

        table.add(gameOverLabel).expandX().padBottom(20);
        table.row();
        table.add(playAgainButton).expandX();

        stage.addActor(table);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();

        if (Gdx.input.isTouched()) {
            game.setScreen(new MainMenuScreen(game));
            dispose();
        }
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        stage.dispose();
        batch.dispose();
        skin.dispose(); // Dispose the skin when done
    }
}

