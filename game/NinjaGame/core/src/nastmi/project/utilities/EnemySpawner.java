package nastmi.project.utilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.*;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.utils.Array;
import nastmi.project.Entities.Enemy;
import nastmi.project.Entities.Player;
import nastmi.project.Entities.enemies.*;

import java.util.Iterator;

public class EnemySpawner {


    //returns an array describing all enemies in the map
    public static  MapObjects addEnemies(TiledMap map){
        MapLayers layers = map.getLayers();
        MapLayer enemyLayer = layers.get("enemyLayer");
        return enemyLayer.getObjects();
    }


    //all enemies that are 1 screen or less away from the player are spawned, then removed from enemyObjects as to only spawn them once
    public static void spawnEnemies(MapObjects enemyObjects, Array<Enemy> enemyArray, Player p,float unitScale){
        Iterator i = enemyObjects.iterator();
        while(i.hasNext()){
            RectangleMapObject object  = (RectangleMapObject) i.next();
            if((object.getRectangle().getX()*unitScale-p.getRect().getX())<=16){
                String s = object.getProperties().get("enemyType").toString();
                switch(s){
                    case "basic":
                        enemyArray.add(new BasicEnemy(object.getRectangle().getX()*unitScale,object.getRectangle().getY()*unitScale,0.5f,1f,
                       (float)object.getProperties().get("xSpeed"),20,new Sprite(new Texture(Gdx.files.internal("trippyboi.png"))),new Texture(Gdx.files.internal("enemies/basicBot.png")),3,0.1f));
                        break;
                    case "bounce":
                        enemyArray.add(new BounceEnemy(object.getRectangle().getX()*unitScale,object.getRectangle().getY()*unitScale,0.75f,0.75f,
                        (float)object.getProperties().get("xSpeed"),20,new Sprite(new Texture(Gdx.files.internal("trippyboi.png"))),new Texture(Gdx.files.internal("enemies/ball.png")),1,0.1f,(float)object.getProperties().get("ySpeed")));
                        break;
                    case "jumpUp":
                        enemyArray.add(new JumpUpEnemy(object.getRectangle().getX()*unitScale,object.getRectangle().getY()*unitScale,0.5f,1f,
                        (float)object.getProperties().get("xSpeed"),20,new Sprite(new Texture(Gdx.files.internal("trippyboi.png"))),new Texture(Gdx.files.internal("enemies/jumpBot.png")),5,0.1f,(float)object.getProperties().get("ySpeed"),
                        (int)object.getProperties().get("jumpFrames"),(boolean)object.getProperties().get("shoot"),(int)object.getProperties().get("shootFrames")));
                        break;
                    case "wave":
                        enemyArray.add(new WaveEnemy(object.getRectangle().getX()*unitScale,object.getRectangle().getY()*unitScale,1f,1f,
                         (float)object.getProperties().get("xSpeed"),20,new Sprite(new Texture(Gdx.files.internal("trippyboi.png"))),new Texture(Gdx.files.internal("enemies/flyingBot.png")),4,0.1f,(float)object.getProperties().get("ySpeed"),(boolean)object.getProperties().get("shoot"),(int)object.getProperties().get("shootFrames")));
                        break;
                    case "shoot":
                        enemyArray.add(new ShootEnemy(object.getRectangle().getX()*unitScale,object.getRectangle().getY()*unitScale,1.5f,0.75f,
                         (float)object.getProperties().get("xSpeed"),20,new Sprite(new Texture(Gdx.files.internal("trippyboi.png"))),new Texture(Gdx.files.internal("enemies/turretBot.png")),4,0.3f,(int)object.getProperties().get("shootFrames")));
                        break;
                    case "threeShoot":
                        enemyArray.add(new ThreeShootEnemy(object.getRectangle().getX()*unitScale,object.getRectangle().getY()*unitScale,1f,0.75f,
                         (float)object.getProperties().get("xSpeed"),20,new Sprite(new Texture(Gdx.files.internal("trippyboi.png"))),new Texture(Gdx.files.internal("enemies/threeShootBot.png")),2,0.5f,(int)object.getProperties().get("firstShot"),(int)object.getProperties().get("shotDelay")));
                        break;
                    case "wallShoot":
                        enemyArray.add(new WallShootEnemy(object.getRectangle().getX()*unitScale,object.getRectangle().getY()*unitScale,0.75f,0.75f,
                                (float)object.getProperties().get("xSpeed"),20,new Sprite(new Texture(Gdx.files.internal("trippyboi.png"))),new Texture(Gdx.files.internal("enemies/wallShootEnemy.png")),2,0.3f,(int)object.getProperties().get("shootFrames")));
                        break;
                }
                i.remove();
            }
        }
    }

}
