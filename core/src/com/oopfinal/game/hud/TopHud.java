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
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.oopfinal.game.OOPFinal;
import com.oopfinal.game.crud.MySQLConnector;
import com.oopfinal.game.screens.GameOverScreen;
import com.oopfinal.game.screens.GameScreen;
import com.oopfinal.game.sprite.characters.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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

    private OOPFinal game; // Add a reference to your game

    public TopHud(SpriteBatch sb, OOPFinal game) { // Accept MainGame as a parameter
        this.playerOne = GameScreen.player1;
        this.playerTwo = GameScreen.player2;
        this.game = game; // Initialize the game reference

        worldTimer = 120;
        timeCount = 0;

        viewport = new FillViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), new OrthographicCamera());
        stage = new Stage(viewport, sb); // We must create order by creating a table in our stage

        heartTexture = new Texture(Gdx.files.internal("UI/health.png")); //tofix
        heartIcon = new TextureRegion(heartTexture);

        white = new BitmapFont(Gdx.files.internal("skins/Copperlate-Light.fnt"), false);
        labelStyle = new Label.LabelStyle(white, Color.BLACK);
        Label.LabelStyle labelStyle2 = new Label.LabelStyle(white, Color.YELLOW.cpy());
//        Pixmap labelColor = new Pixmap(10, 10, Pixmap.Format.RGB888);
//        labelColor.setColor(Color.RED);
//        labelColor.fill();

        Table playerOneTable = new Table();
        playerOneHealthLabel = new Label(String.format("%03f", playerOne.getHealth()), labelStyle2);
        playerOneTable.bottom().left();
        playerOneTable.setFillParent(true);
        playerOneTable.add(new Image(heartIcon)).padLeft(10).padBottom(10).width(Value.percentWidth(0.05f)).height(Value.percentHeight(0.05f));
        playerOneTable.add(playerOneHealthLabel).padLeft(viewport.getScreenWidth()/8).padBottom(10);

        // Create table for player two health display
        Table playerTwoTable = new Table();
        playerTwoHealthLabel = new Label(String.format("%03f", playerTwo.getHealth()), labelStyle2);
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

        table.add(playerOneScoreLabel).expandX();
        table.add(worldtimerLabel).expandX();
        table.add(playerTwoScoreLabel).expandX();

        table.row(); // THIS CREATES A NEW ROW
        table.add(scoreLabel).expandX();

        // add table to our stage
        stage.addActor(table);
        stage.addActor(playerOneTable);
        stage.addActor(playerTwoTable);
    }

    public void update(float dt) {
        timeCount += dt;
        if (timeCount >= 4) { // Adjusted from 4 to 1 to make timer decrement every second
            worldTimer--;
            worldtimerLabel.setText(String.format("%03d", worldTimer));
            timeCount = 0;
        }
        playerOneHealthLabel.setText(playerTwo.getHealth().toString());
        playerTwoHealthLabel.setText(playerOne.getHealth().toString());
        playerTwoScoreLabel.setText(playerOne.getScore());
        playerOneScoreLabel.setText(playerTwo.getScore());

        if (worldTimer <= 0) {
            String query = "UPDATE game SET player1score ="+playerTwo.score+", player2score ="+playerOne.score+" WHERE gameid ="+GameScreen.GAME_ID;
            try(Connection c = MySQLConnector.getConnection();
                PreparedStatement pst = c.prepareStatement(query)){
                pst.executeUpdate();


            }catch (SQLException e){
                throw new RuntimeException(e);
            }

            game.setScreen(new GameOverScreen(game)); // Switch to GameOverScreen when timer reaches 0
        }
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
