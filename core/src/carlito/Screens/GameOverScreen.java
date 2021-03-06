package carlito.Screens;

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

import carlito.CarlitoEscape;
import carlito.Tools.MyCallbackListener;

/**
 * Created by brentaureli on 10/8/15.
 */
public class GameOverScreen implements Screen {
    private Viewport viewport;
    private Stage stage;

    private Game game;

    public GameOverScreen(Game game){
        this.game = game;
        viewport = new FitViewport(CarlitoEscape.V_WIDTH, CarlitoEscape.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, ((CarlitoEscape) game).batch);

        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.WHITE);

        Table table = new Table();
        table.center();
        table.setFillParent(true);


        Label win = new Label("You won!", font);
        Label lose = new Label("You lose!", font);
        Label katarzynaRymszaLabel = new Label("Katarzyna Rymsza", font);
        Label jaroslawRutkowskiLabel = new Label("Jaroslaw Rutkowski", font);
        Label bartlomiejZdybelLabel = new Label("Bartlomiej Zdybel", font);
        Label lukaszWoznicaLabel = new Label("Lukasz Woznica", font);
        Label romanAdamskiLabel = new Label("Credits : Roman Adamski", font);

        if(MyCallbackListener.result==1){
            table.add(win).expandX().padTop(5f);
            table.row();
        }
        else if(MyCallbackListener.result==2){
            table.add(lose).expandX().padTop(5f);
            table.row();
        }
        else if(MyCallbackListener.result==0){
            table.add(lose).expandX().padTop(5f);
            table.row();
        }
        table.add(romanAdamskiLabel).expandX().padTop(25f);
        table.row();
        table.add(jaroslawRutkowskiLabel).expandX().padTop(2f);
        table.row();
        table.add(katarzynaRymszaLabel).expandX().padTop(2f);
        table.row();
        table.add(bartlomiejZdybelLabel).expandX().padTop(2f);
        table.row();
        table.add(lukaszWoznicaLabel).expandX().padTop(2f);

        stage.addActor(table);
    }

    @Override
    public void show() {

    }
    @Override
    public void render(float delta) {
        if(Gdx.input.justTouched()) {
            MyCallbackListener.end=1;
            //game.setScreen(new PlayScreen((CarlitoEscape) game, "KARLITO"));
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
