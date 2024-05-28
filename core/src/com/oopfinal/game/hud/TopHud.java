package com.oopfinal.game.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import com.oopfinal.game.sprite.characters.Player;

public class TopHud implements Disposable{
    public Stage stage;
    public ExtendViewport viewport;
    public Table table;
    private Integer worldTimer;
    private float timeCount;
    private static Integer score;
    // Now we create our widgets. Our widgets will be labels, essentially text, that allow us to display Game Information
    private Label countdownLabel;
    static Label scoreLabel;
    private Label timeLabel;
    private Label levelLabel;
    private Label worldLabel;
    private Label marioLabel;
    private BitmapFont white;

    private Player playerOne;
    private Player playerTwo;
    private Label playerOneHealthLabel;
    private Label playerTwoHealthLabel;
    private Texture heartTexture;
    private TextureRegion heartIcon;

    public TopHud(SpriteBatch sb, Player playerOne, Player playerTwo){
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;

        worldTimer = 30;
        timeCount = 0;
        score = 0;

        viewport = new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), new OrthographicCamera());
        stage = new Stage(viewport, sb); // We must create order by creating a table in our stage

        heartTexture = new Texture(Gdx.files.internal("UI/health.png")); //tofix
        heartIcon = new TextureRegion(heartTexture);

        white = new BitmapFont(Gdx.files.internal("font/white16.fnt"), false);
        Label.LabelStyle labelStyle = new Label.LabelStyle(white, Color.RED);


        Table playerOneTable = new Table();
        playerOneHealthLabel = new Label(String.format("%03d", playerOne.getHealth()), labelStyle);
        playerOneTable.bottom().left();
        playerOneTable.setFillParent(true);
        playerOneTable.add(new Image(heartIcon)).padLeft(100).padBottom(30);
        playerOneTable.add(playerOneHealthLabel).padLeft(10).padBottom(30);

        // Create table for player two health display
        Table playerTwoTable = new Table();
        playerTwoHealthLabel = new Label(String.format("%03d", playerTwo.getHealth()), labelStyle);
        playerTwoTable.bottom().right();
        playerTwoTable.setFillParent(true);
        playerTwoTable.add(playerTwoHealthLabel).padRight(10).padBottom(30);
        playerTwoTable.add(new Image(heartIcon)).padRight(100).padBottom(30);

        table = new Table();
        table.top(); // Will put it at the top of our stage
        table.setFillParent(true);

        countdownLabel = new Label(String.format("%03d", worldTimer), labelStyle);
        scoreLabel = new Label(String.format("%06d", score), labelStyle);
        timeLabel = new Label("TIME", labelStyle);
        levelLabel = new Label("WASTE LAND", labelStyle);
        worldLabel = new Label("ROUND 1", labelStyle);
        marioLabel = new Label("SCORES:", labelStyle);

        table.add(marioLabel).expandX().padTop(10); // This expand X makes everything in the row share the row equally
        table.add(worldLabel).expandX().padTop(10);
        table.add(timeLabel).expandX().padTop(10);

        table.row(); // THIS CREATES A NEW ROW
        table.add(scoreLabel).expandX();
        table.add(levelLabel).expandX();
        table.add(countdownLabel).expandX();

        // add table to our stage
        stage.addActor(table);
        stage.addActor(playerOneTable);
        stage.addActor(playerTwoTable);
    }

    public void update(float dt) {
        timeCount += dt;
        if(timeCount >= 1){
            worldTimer--;
            countdownLabel.setText(String.format("%03d", worldTimer));
            timeCount = 0;
        }
    }

    public static void addScore(int value){
        score += value;
        scoreLabel.setText(String.format("%06d", score));
    }

    public Integer getTime(){
        return worldTimer;
    }

    @Override
    public void dispose() {
        stage.dispose();
//        heartTexture.dispose();
    }
}