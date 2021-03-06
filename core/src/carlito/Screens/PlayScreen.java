package carlito.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import carlito.CarlitoEscape;
import carlito.Scenes.Hud;
import carlito.Sprites.Carlito;
import carlito.Sprites.Items.Item;
import carlito.Sprites.Items.ItemDef;
import carlito.Sprites.Items.Key;
import carlito.Tools.B2WorldCreator;
import carlito.Tools.Controller;
import carlito.Tools.MyCallbackListener;
import carlito.Tools.WorldContactListener;
import carlito.Sprites.Enemy.Enemy;
import java.util.concurrent.LinkedBlockingQueue;
import carlito.Sprites.Enemy.objects;


public class PlayScreen implements Screen{
    //Reference to our Game, used to set Screens
    private CarlitoEscape game;
    private TextureAtlas atlas;
    private TextureAtlas atlasKey;
    public static boolean alreadyDestroyed = false;

    //basic playscreen variables
    private OrthographicCamera gamecam;
    private Viewport gamePort;
    private Hud hud;

    //Tiled map variables
    private TmxMapLoader maploader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    //Box2d variables
    private World world;
    private Box2DDebugRenderer b2dr;
    private B2WorldCreator creator;

    //sprites
    private Carlito player;
    private Key key;

    private Music music;
    private Music intro;

    private Array<Item> items;
    private LinkedBlockingQueue<ItemDef> itemsToSpawn;

    Controller controller;

    boolean temp;

    String skin;
    public PlayScreen(CarlitoEscape game, String skin){
        atlas = new TextureAtlas("KarlitoGFX.atlas");
        atlasKey = new TextureAtlas("items.pack");
        this.game = game;
        //create cam used to follow mario through cam world
        gamecam = new OrthographicCamera();

        //create a FitViewport to maintain virtual aspect ratio despite screen size
        gamePort = new FitViewport(CarlitoEscape.V_WIDTH / CarlitoEscape.PPM, CarlitoEscape.V_HEIGHT / CarlitoEscape.PPM, gamecam);

        //create our game HUD for scores/timers/level info


        //Load our map and setup our map renderer
        maploader = new TmxMapLoader();
        map = maploader.load("level1.tmx");
        renderer = new OrthogonalTiledMapRenderer(map,1  / CarlitoEscape.PPM);

        //initially set our gamcam to be centered correctly at the start of of map
        gamecam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() /2, 0);

        //create our Box2D world, setting no gravity in X, -10 gravity in Y, and allow bodies to sleep
        world = new World(new Vector2(0, -10), true);
        //allows for debug lines of our box2d world.
        b2dr = new Box2DDebugRenderer();

        creator = new B2WorldCreator(this);

        //create mario in our game world
        player = new Carlito(this, skin);

        hud = new Hud(game.batch, player);

        world.setContactListener(new WorldContactListener());

        intro= CarlitoEscape.manager.get("audio/music/intro.ogg", Music.class);
        intro.setLooping(false);
        intro.setVolume(0.4f);
        intro.play();

        music = CarlitoEscape.manager.get("audio/music/997.ogg", Music.class);
        music.setLooping(true);
        music.setVolume(0.4f);

        items = new Array<Item>();
        itemsToSpawn = new LinkedBlockingQueue<ItemDef>();

        controller= new Controller();

        this.skin=skin;

        temp=false;

        //wspolrzedne podane przez konstruktor
        key=new Key(this, (float)34.572796631,(float)0.7);
       // box=new BOX(this, (float)5, (float)0.54);
    }

    public void spawnItem(ItemDef idef){
        itemsToSpawn.add(idef);
    }

    public void handleSpawningItems(){
        if(!itemsToSpawn.isEmpty()){
            ItemDef idef=itemsToSpawn.poll();
            if(idef.type==Key.class){
                items.add(new Key(this, 250/ CarlitoEscape.PPM, 150 / CarlitoEscape.PPM));
            }
            /*if(idef.type==BOX.class){
                items.add(new BOX(this, 230/ CarlitoEscape.PPM, 100 / CarlitoEscape.PPM));
            }*/
        }
    }

    public TextureAtlas getAtlas(){
        return atlas;
    }
    public TextureAtlas getAtlasKey(){
        return atlasKey;
    }

    @Override
    public void show() {

    }

    public void handleInput(float dt){
        //control our player using immediate impulses
        if(player.currentState != Carlito.State.DEAD) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.UP))
                player.jump();
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.b2body.getLinearVelocity().x <= 2){
                player.b2body.applyLinearImpulse(new Vector2(0.1f, 0), player.b2body.getWorldCenter(), true);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.b2body.getLinearVelocity().x >= -2)
                player.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), player.b2body.getWorldCenter(), true);

            if (controller.isUpPressed())
                player.jump();
            if (controller.isRightPressed() && player.b2body.getLinearVelocity().x <= 2)
                player.b2body.applyLinearImpulse(new Vector2(0.1f, 0), player.b2body.getWorldCenter(), true);
            if (controller.isLeftPressed() && player.b2body.getLinearVelocity().x >= -2)
                player.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), player.b2body.getWorldCenter(), true);

            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE))
                player.jump();
        }
    }

    public void update(float dt){
        //handle user input first
        handleInput(dt);
        handleSpawningItems();

        //takes 1 step in the physics simulation(60 times per second)
        world.step(1 / 60f, 6, 2);

        player.update(dt);
        key.update(dt);

        for(Enemy enemy : creator.getEnemies()) {
            enemy.update(dt);
            if(enemy.getX() < player.getX() + 224 / CarlitoEscape.PPM) {
                enemy.b2body.setActive(true);
            }
        }
        for(objects boxes : creator.getBoxes()) {
            boxes.update(dt);
        }

        for(Item item : items)
            item.update(dt);

        hud.update(dt);

        //attach our gamecam to our players.x coordinate
        if(player.currentState != Carlito.State.DEAD) {
            gamecam.position.x = player.b2body.getPosition().x;
        }

        //update our gamecam with correct coordinates after changes
        gamecam.update();
        //tell our renderer to draw only what our camera can see in our game world.
        renderer.setView(gamecam);

        MyCallbackListener.sendWsp =player.b2body.getPosition().x;
        //zaczyna grac jesli intro przestanie, ustawia flage zeby nie zaczynac w kolko
        if(!intro.isPlaying() && !temp && player.currentState != Carlito.State.DEAD){
            music.play();
            temp=true;
        }
    }

    @Override
    public void render(float delta) {
        //separate our update logic from render
        update(delta);

        //Clear the game screen with Black
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //render our game map
        renderer.render();

        //renderer our Box2DDebugLines
        b2dr.render(world, gamecam.combined);

        game.batch.setProjectionMatrix(gamecam.combined);
        game.batch.begin();
        player.draw(game.batch);
        key.draw(game.batch);

        for (Enemy enemy : creator.getEnemies())
            enemy.draw(game.batch);

        for (objects boxes : creator.getBoxes())
            boxes.draw(game.batch);

        for (Item item : items)
            item.draw(game.batch);
        game.batch.end();

        //Set our batch to now draw what the Hud camera sees.
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();

        if(gameOver()){
            game.setScreen(new GameOverScreen(game));
            dispose();
        }
        controller.draw();


    }

    public boolean gameOver(){
        //jesli zginal albo skonczyl mu sie czas
        if(player.currentState == Carlito.State.DEAD && player.getStateTimer() > 3 ){
            MyCallbackListener.sendWsp=37;
            MyCallbackListener.result=2;
            return true;
        }
        //jesli tamten wygral
        else if(MyCallbackListener.receiveWsp==36){
            MyCallbackListener.result=2;
            return true;
        }
        //jesli wygral
        else if(player.isFree){
            MyCallbackListener.sendWsp=36;
            MyCallbackListener.result=1;
            CarlitoEscape.manager.get("audio/music/intro.ogg", Music.class).stop();
            CarlitoEscape.manager.get("audio/music/997.ogg", Music.class).stop();
            CarlitoEscape.manager.get("audio/sounds/mariowin.wav", Sound.class).play();
            return true;
        }
        //jesli tamten dal znac ze przegral
        else if(MyCallbackListener.receiveWsp==37){
            MyCallbackListener.result=1;
            CarlitoEscape.manager.get("audio/music/intro.ogg", Music.class).stop();
            CarlitoEscape.manager.get("audio/music/997.ogg", Music.class).stop();
            CarlitoEscape.manager.get("audio/sounds/mariowin.wav", Sound.class).play();
            return true;
        }
        return false;
    }

    @Override
    public void resize(int width, int height) {
        //updated our game viewport
        gamePort.update(width,height);

        controller.resize(width,height);
    }

    public TiledMap getMap(){
        return map;
    }
    public World getWorld(){
        return world;
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
        //dispose of all our opened resources
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        hud.dispose();
    }

    public Hud getHud(){ return hud; }
}
