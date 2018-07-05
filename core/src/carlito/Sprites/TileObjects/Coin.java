package carlito.Sprites.TileObjects;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.physics.box2d.BodyDef;
import carlito.CarlitoEscape;
import carlito.Screens.PlayScreen;
import carlito.Sprites.Carlito;

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
        setCategoryFilter(CarlitoEscape.COIN_BIT);
    }

    @Override
    public void onHeadHit(Carlito carlito) {

        carlito.isFree=true;

    }
}
