package nastmi.project.utilities;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
/*The default renderers have a renderObject method, but it is empty. As such, we simply extend OrthogonalTiledMapRenderer and fill the method.
this draw every layer, including object layers*/
public class ObjectLayerRenderer extends OrthogonalTiledMapRenderer {
    public ObjectLayerRenderer(TiledMap map) {
        super(map);
    }
    public ObjectLayerRenderer(TiledMap map, float unitScale) {
        super(map,unitScale);
    }

    @Override
    public void renderObject(MapObject object){
        if(object instanceof TextureMapObject){
            TextureMapObject curObject = (TextureMapObject) object;
            batch.draw(curObject.getTextureRegion(),curObject.getX()*unitScale,curObject.getY()*unitScale,curObject.getTextureRegion().getRegionWidth()*unitScale,curObject.getTextureRegion().getRegionHeight()*unitScale);

        }
    }
}
