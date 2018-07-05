package carlito.Tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import carlito.MarioBros;
import carlito.Screens.PlayScreen;
import carlito.Sprites.Enemy.Enemy;
import carlito.Sprites.Enemy.bagietson;
import carlito.Sprites.Enemy.objects;
import carlito.Sprites.Enemy.BOX;

import javax.swing.Box;

/**
 * Created by brentaureli on 8/28/15.
 */
public class B2WorldCreator {
        private Array<bagietson> bagiety;
        private Array<BOX> pudelka;
    public B2WorldCreator(PlayScreen screen){

        World world = screen.getWorld();
        TiledMap map = screen.getMap();

        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;


        for (MapObject object : map.getLayers().get(1).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / MarioBros.PPM, (rect.getY() + rect.getHeight() / 2) / MarioBros.PPM);

            body = world.createBody(bdef);
            shape.setAsBox(rect.getWidth() / 2 / MarioBros.PPM, rect.getHeight() / 2 / MarioBros.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);
        }

        bagiety = new Array<bagietson>();
        for(MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            bagiety.add(new bagietson(screen, rect.getX()/ MarioBros.PPM, rect.getY() / MarioBros.PPM));
        }
        pudelka =new Array<BOX>();
        for(MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            pudelka.add(new BOX(screen, rect.getX()/ MarioBros.PPM, rect.getY() / MarioBros.PPM));
        }


    }
    public Array<bagietson> wezBagiete() {
        return bagiety;
    }
    public Array<Enemy> getEnemies(){
        Array<Enemy> enemies = new Array<Enemy>();
        enemies.addAll(bagiety);
        return enemies;
    }
    public Array<BOX> getBoxes(){
        Array<BOX> boxes = new Array<BOX>();
        boxes.addAll(pudelka);
        return boxes;
    }
}


