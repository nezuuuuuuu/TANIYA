package com.oopfinal.game.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.oopfinal.game.screens.GameScreen;
import com.oopfinal.game.sprite.characters.Player;

public class TopHud implements Disposable {
    public Stage stage;
    public Viewport viewport;
    public Table table;
    private Integer worldTimer;
    private float timeCount;

    // Widgets for displaying game information
    private Label countdownLabel;
    static Label scoreLabel;
    private Label timeLabel;
    private Label levelLabel;
    private Label worldtimerLabel;

    private BitmapFont white;

    private Player playerOne;
    private Player playerTwo;
    private Label playerOneHealthLabel;
    private Label playerTwoHealthLabel;
    private Label playerOneScoreLabel;
    private Label playerTwoScoreLabel;
    private Texture heartTexture;
    private TextureRegion heartIcon;
    Label.LabelStyle labelStyle;

    public TopHud(SpriteBatch sb) {
        this.playerOne = GameScreen.player1;
        this.playerTwo = GameScreen.player2;

        worldTimer = 120;
        timeCount = 0;

        viewport = new FillViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), new OrthographicCamera());
        stage = new Stage(viewport, sb); // We must create order by creating a table in our stage

        heartTexture = new Texture(Gdx.files.internal("UI/health.png")); //tofix
        heartIcon = new TextureRegion(heartTexture);

        white = new BitmapFont(Gdx.files.internal("font/white16.fnt"), false);
        labelStyle = new Label.LabelStyle(white, Color.RED);
        Pixmap labelColor = new Pixmap(10, 10, Pixmap.Format.RGB888);
        labelColor.setColor(Color.RED);
        labelColor.fill();

        Table playerOneTable = new Table();
        playerOneHealthLabel = new Label(String.format("%03f", playerOne.getHealth()), labelStyle);
        playerOneTable.bottom().left();
        playerOneTable.setFillParent(true);
        playerOneTable.add(new Image(heartIcon)).padLeft(10).padBottom(10).width(Value.percentWidth(0.05f)).height(Value.percentHeight(0.05f));
        playerOneTable.add(playerOneHealthLabel).padLeft(viewport.getScreenWidth()/8).padBottom(10);

        // Create table for player two health display
        Table playerTwoTable = new Table();
        playerTwoHealthLabel = new Label(String.format("%03f", playerTwo.getHealth()), labelStyle);
        playerTwoTable.bottom().right();
        playerTwoTable.setFillParent(true);
        playerTwoTable.add(playerTwoHealthLabel).padRight(viewport.getScreenWidth()/8).padBottom(10);
        playerTwoTable.add(new Image(heartIcon)).padRight(10).padBottom(10).width(Value.percentWidth(0.05f)).height(Value.percentHeight(0.05f));

        table = new Table();
        table.top();

        table.padTop(viewport.getScreenHeight()/20);// Will put it at the top of our stage
        table.setFillParent(true);
        playerOneScoreLabel = new Label(String.format("%03d", 0), labelStyle);
        playerTwoScoreLabel = new Label(String.format("%03d", 0), labelStyle);

        worldtimerLabel = new Label(String.valueOf(worldTimer), labelStyle);

        table.add(playerOneScoreLabel).expandX().padTop(10);
        table.add(worldtimerLabel).expandX().padTop(10);
        table.add(playerTwoScoreLabel).expandX().padTop(10);

        table.row(); // THIS CREATES A NEW ROW
        table.add(scoreLabel).expandX();

        // add table to our stage
        stage.addActor(table);
        stage.addActor(playerOneTable);
        stage.addActor(playerTwoTable);






    }

    public void update(float dt) {
        timeCount += dt;
        if (timeCount >= 4) {
            worldTimer--;
            worldtimerLabel.setText(String.format("%03d", worldTimer));
            timeCount = 0;
        }
        playerOneHealthLabel.setText(playerOne.getHealth().toString());
        playerTwoHealthLabel.setText(playerTwo.getHealth().toString());
        playerTwoScoreLabel.setText(playerTwo.getScore());
        playerOneScoreLabel.setText(playerOne.getScore());
    }

    public static void addScore(int value) {
        // Add score logic here
    }

    public Integer getTime() {
        return worldTimer;
    }

    @Override
    public void dispose() {
        stage.dispose();
        heartTexture.dispose();
    }
}
