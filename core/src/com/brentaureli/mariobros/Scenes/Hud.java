package com.brentaureli.mariobros.Scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.brentaureli.mariobros.MarioBros;
import com.brentaureli.mariobros.Sprites.Mario;
import com.brentaureli.mariobros.Tools.MyCallbackListener;

/**
 * Created by brentaureli on 8/17/15.
 */
public class Hud implements Disposable{

    //Scene2D.ui Stage and its own Viewport for HUD
    public Stage stage;
    private Viewport viewport;

    //Mario score/time Tracking Variables
    private Integer worldTimer;
    private boolean timeUp; // true when the world timer reaches 0
    private float timeCount;
    private float score;

    //Scene2D widgets
    private Label countdownLabel;
    private static Label scoreLabel;
    private Label timeLabel;
    private Label marioLabel;
    private static Label scoreLabel2;
    private Label marioLabel2;

    public Hud(SpriteBatch sb, Mario mario){
        worldTimer = 100;
        timeCount = 0;
        score= MyCallbackListener.receiveWsp;


        viewport = new FitViewport(MarioBros.V_WIDTH, MarioBros.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);

        //define a table used to organize our hud's labels
        Table table = new Table();
        //Top-Align table
        table.top();
        //make the table fill the entire stage
        table.setFillParent(true);

        //define our labels using the String, and a Label style consisting of a font and color
        countdownLabel = new Label(String.format("%03d", worldTimer), new Label.LabelStyle(new BitmapFont(), Color.YELLOW));
        scoreLabel =new Label(String.format("%f", score), new Label.LabelStyle(new BitmapFont(), Color.YELLOW));
        timeLabel = new Label("TIME", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        marioLabel = new Label("ENEMY", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        scoreLabel2 =new Label(String.format("%f", score), new Label.LabelStyle(new BitmapFont(), Color.YELLOW));
        marioLabel2 = new Label("YOU", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        //add our labels to our table, padding the top, and giving them all equal width with expandX
        if(MyCallbackListener.sinlgePlay==1)
            table.add(marioLabel).expandX();
        table.add(marioLabel2).expandX();
        table.add(timeLabel).expandX();
        //add a second row to our table
        table.row();
        if(MyCallbackListener.sinlgePlay==1)
            table.add(scoreLabel).expandX();
        table.add(scoreLabel2).expandX();
        table.add(countdownLabel).expandX();
        //add our table to the stage
        stage.addActor(table);
    }

    public void update(float dt){
        timeCount += dt;
        if(timeCount >= 1){
            if (worldTimer > 0) {
                worldTimer--;
            } else {
                timeUp = true;
            }
            countdownLabel.setText(String.format("%03d", worldTimer));
            timeCount = 0;

        }
    }

    public static void addScore(float value){

        scoreLabel.setText(String.format("%.0f", value)+"%");
       // System.out.println("ELO"+value);
    }
    public static void addScore2(float value){

        scoreLabel2.setText(String.format("%.0f", value)+"%");
        // System.out.println("ELO"+value);
    }

    @Override
    public void dispose() { stage.dispose(); }

    public boolean isTimeUp() { return timeUp; }
}
