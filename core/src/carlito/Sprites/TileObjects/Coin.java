package carlito.Sprites.TileObjects;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import carlito.MarioBros;
import carlito.Scenes.Hud;
import carlito.Screens.PlayScreen;
import carlito.Sprites.Items.ItemDef;
import carlito.Sprites.Mario;

/**
 * Created by brentaureli on 8/28/15.
 */
public class Coin extends InteractiveTileObject {
    private static TiledMapTileSet tileSet;
    private final int BLANK_COIN = 28;
    BodyDef bdef = new BodyDef();
    public Coin(PlayScreen screen, MapObject object){
        super(screen, object);
        bdef.type = BodyDef.BodyType.DynamicBody;
        tileSet = map.getTileSets().getTileSet("tileset_gutter");
        getCell().setTile(tileSet.getTile(124));
        fixture.setUserData(this);
        setCategoryFilter(MarioBros.COIN_BIT);
    }

    @Override
    public void onHeadHit(Mario mario) {

        mario.isFree=true;

    }
}
