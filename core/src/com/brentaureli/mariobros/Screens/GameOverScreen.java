package com.brentaureli.mariobros.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.brentaureli.mariobros.MarioBros;
import com.brentaureli.mariobros.Screens.Tools.MyCallbackListener;

/**
 * Created by brentaureli on 10/8/15.
 */
public class GameOverScreen implements Screen {
    private Viewport viewport;
    private Stage stage;

    private Game game;

    public GameOverScreen(Game game){
        this.game = game;
        viewport = new FitViewport(MarioBros.V_WIDTH, MarioBros.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, ((MarioBros) game).batch);

        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.WHITE);

        Table table = new Table();
        table.center();
        table.setFillParent(true);


        Label win = new Label("Wygrales!", font);
        Label lose = new Label("Ruchasz male dzieci!", font);
        Label katarzynaRymszaLabel = new Label("Katarzyna Rymsza", font);
        Label jaroslawRutkowskiLabel = new Label("Jaroslaw Rutkowski", font);
        Label bartlomiejZdybelLabel = new Label("Bartlomiej Zdybel", font);
        Label lukaszWoznicaLabel = new Label("Lukasz Woznica", font);
        Label romanAdamskiLabel = new Label("Roman Adamski", font);

        if(MyCallbackListener.result==1){
            table.add(win).expandX().padTop(10f);
            table.row();
        }
        else if(MyCallbackListener.result==2){
            table.add(lose).expandX().padTop(10f);
            table.row();
        }
        else if(MyCallbackListener.result==0){
            table.add(lose).expandX().padTop(10f);
            table.row();
        }
        table.add(romanAdamskiLabel).expandX().padTop(10f);
        table.row();
        table.add(jaroslawRutkowskiLabel).expandX().padTop(10f);
        table.row();
        table.add(katarzynaRymszaLabel).expandX().padTop(10f);
        table.row();
        table.add(bartlomiejZdybelLabel).expandX().padTop(10f);
        table.row();
        table.add(lukaszWoznicaLabel).expandX().padTop(10f);

        stage.addActor(table);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        if(Gdx.input.justTouched()) {
            MyCallbackListener.end=1;
            //game.setScreen(new PlayScreen((MarioBros) game, "KARLITO"));
            //dispose();
        }
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

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
    }
}
