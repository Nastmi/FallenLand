package nastmi.project.utilities;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.math.EarClippingTriangulator;
import java.time.Duration;
import java.time.Instant;

//gets a tiled map and a world, then converts all the non-texture objects into bodies in the world.
public class CollisionBuilder {

    public static void objectLayerToBox2D(TiledMap map, World world, float unitScale){
        MapObjects objects = map.getLayers().get("collisionLayer").getObjects();
        for(MapObject obj : objects){
            BodyDef bodyDef = new BodyDef();
            bodyDef.type = BodyDef.BodyType.StaticBody;
            if(obj instanceof RectangleMapObject){
                RectangleMapObject newObj = (RectangleMapObject) obj;
                bodyDef.position.set(newObj.getRectangle().x*unitScale,newObj.getRectangle().y*unitScale);
                Body body = world.createBody(bodyDef);
                PolygonShape shape = new PolygonShape();
                shape.setAsBox(newObj.getRectangle().width*unitScale,newObj.getRectangle().height*unitScale);
                Fixture fixture = body.createFixture(shape,0.0f);
            }
            else if(obj instanceof PolygonMapObject){
                Instant start = Instant.now();
                PolygonMapObject newObj = (PolygonMapObject) obj;
                bodyDef.position.set(newObj.getPolygon().getX()*unitScale,newObj.getPolygon().getY()*unitScale);
                Body body = world.createBody(bodyDef);
                //PolygonShape shape = new PolygonShape();
                float[] vertices = newObj.getPolygon().getVertices();
                EarClippingTriangulator clip = new EarClippingTriangulator();
                short[] verticesIndexed = clip.computeTriangles(vertices).toArray();
               System.out.println("length "+verticesIndexed.length);
                System.out.println("length of vertices "+vertices.length);
                for(int i=0;i<verticesIndexed.length;i+=3){
                    float[] curVertices = new float[6];
                    curVertices[0] = vertices[verticesIndexed[i]*2]*unitScale;
                    curVertices[1] = vertices[verticesIndexed[i]*2+1]*unitScale;
                    curVertices[2] = vertices[verticesIndexed[i+1]*2]*unitScale;
                    curVertices[3] = vertices[verticesIndexed[i+1]*2+1]*unitScale;
                    curVertices[4] = vertices[verticesIndexed[i+2]*2]*unitScale;
                    curVertices[5] = vertices[verticesIndexed[i+2]*2+1]*unitScale;
                    PolygonShape shape = new PolygonShape();
                    shape.set(curVertices);
                    Fixture fixture = body.createFixture(shape,0.0f);

                }
                Instant end = Instant.now();
                Duration timeElapsed = Duration.between(start, end);
                System.out.println("Time taken: "+ timeElapsed.toMillis() +" milliseconds");
            }
        }
    }
}
