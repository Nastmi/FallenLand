package nastmi.project.utilities;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.math.EarClippingTriangulator;
import com.badlogic.gdx.utils.Array;

//gets a tiled map and a world, then converts all the non-texture objects into bodies in the world.
public class CollisionBuilder {
    //all coordinates and sizes should be multiplied by unitScale, so you work with arbitrary units instead of pixels.
    public static void objectLayerToBox2D(TiledMap map, Array<Rectangle> arr, float unitScale){
        MapObjects objects = map.getLayers().get("collisionLayer").getObjects();
        for(MapObject obj : objects){
            BodyDef bodyDef = new BodyDef();
            bodyDef.type = BodyDef.BodyType.StaticBody;
            if(obj instanceof RectangleMapObject){
                //Only happens if object is rectangle, as they are simple to create, and we need to separate them from polygons.
                RectangleMapObject newObj = (RectangleMapObject) obj;
                Rectangle rect = new Rectangle();
                rect.set(newObj.getRectangle().x*unitScale,newObj.getRectangle().y*unitScale,newObj.getRectangle().getWidth()*unitScale,newObj.getRectangle().getHeight()*unitScale);
                arr.add(rect);
            }
            /*else if(obj instanceof PolygonMapObject){
               //Polygons are a bit more complex, so we need different code.
                PolygonMapObject newObj = (PolygonMapObject) obj;
                bodyDef.position.set(newObj.getPolygon().getX()*unitScale,newObj.getPolygon().getY()*unitScale);
                Body body = world.createBody(bodyDef);
                //We get the vertices of a polygon, where a pair of array floats represent x and y (index 0 = x, index 1 = y,...)
                float[] vertices = newObj.getPolygon().getVertices();
                /*Due to convex polygons not being implemented in Box2d, and due to the max vertice(point) amount being eight, we have to create triangles.
                the below method returns INDEXES of the given array. Those indexes tell us, in what order of vertices should each triangle be drawn*/
              /*  EarClippingTriangulator clip = new EarClippingTriangulator();
                short[] verticesIndexed = clip.computeTriangles(vertices).toArray();
                for(int i=0;i<verticesIndexed.length;i+=3){
                    //Each triangle is a seperate fixture for our body
                    float[] curVertices = new float[6];
                    //Manually create the necessary triangle, because a loop here would probably be more complex.
                    curVertices[0] = vertices[verticesIndexed[i]*2]*unitScale;
                    curVertices[1] = vertices[verticesIndexed[i]*2+1]*unitScale;
                    curVertices[2] = vertices[verticesIndexed[i+1]*2]*unitScale;
                    curVertices[3] = vertices[verticesIndexed[i+1]*2+1]*unitScale;
                    curVertices[4] = vertices[verticesIndexed[i+2]*2]*unitScale;
                    curVertices[5] = vertices[verticesIndexed[i+2]*2+1]*unitScale;
                    PolygonShape shape = new PolygonShape();
                    shape.set(curVertices);
                    FixtureDef fixDef = new FixtureDef();
                    fixDef.isSensor = true;
                    fixDef.shape = shape;
                    Fixture fixture = body.createFixture(fixDef);

                }
            }*/
        }
    }
}
