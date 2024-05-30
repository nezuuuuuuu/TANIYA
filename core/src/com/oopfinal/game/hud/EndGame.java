package com.oopfinal.game.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class EndGame implements Disposable {
    Stage stage;
    public Viewport viewport;
    public EndGame() {
        viewport = new FillViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), new OrthographicCamera());




    }

    @Override
    public void dispose() {

    }
}
