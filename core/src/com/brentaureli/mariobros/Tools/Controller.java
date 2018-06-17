package com.brentaureli.mariobros.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.brentaureli.mariobros.MarioBros;
import com.brentaureli.mariobros.Screens.PlayScreen;

/**
 * Created by romek95a on 28.05.2018.
 */

public class Controller {
    Viewport viewport;
    Stage stage;
    boolean upPressed, downPressed, leftPressed, rightPressed;
    OrthographicCamera cam;

    public Controller(){
        cam=new OrthographicCamera();
        viewport=new FitViewport(800,400,cam);
        stage = new Stage(viewport, MarioBros.batch);
        Gdx.input.setInputProcessor(stage);

        Table table=new Table();
        table.left().bottom();
        Image upImg=new Image(new Texture("up.png"));
        upImg.setSize(80,80);
        upImg.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                upPressed=true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                upPressed=false;
            }
        });
        Image downImg=new Image(new Texture("down.png"));
        downImg.setSize(80,80);
        downImg.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                downPressed=true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                downPressed=false;
            }
        });
        Image rightImg=new Image(new Texture("right.png"));
        rightImg.setSize(80,80);
        rightImg.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                rightPressed=true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                rightPressed=false;
            }
        });
        Image leftImg=new Image(new Texture("left.png"));
        leftImg.setSize(80,80);
        leftImg.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                leftPressed=true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                leftPressed=false;
            }
        });
        table.row().pad(5, 5, 5, 5);
        table.add(leftImg).size(leftImg.getWidth(), leftImg.getHeight());
        table.add();
        table.add(rightImg).size(rightImg.getWidth(), rightImg.getHeight());
        table.add().size(500,upImg.getHeight());
        table.add(upImg).size(upImg.getWidth(), upImg.getHeight());
        stage.addActor(table);
    }
    public void draw(){
        stage.draw();
    }

    public boolean isUpPressed() {
        return upPressed;
    }

    public boolean isDownPressed() {
        return downPressed;
    }

    public boolean isLeftPressed() {
        return leftPressed;
    }


    public boolean isRightPressed() {
        return rightPressed;
    }

    public void resize(int width, int height){
        viewport.update(width,height);
    }
}
