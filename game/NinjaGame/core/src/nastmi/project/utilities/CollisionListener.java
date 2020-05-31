package nastmi.project.utilities;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;
import nastmi.project.Entities.Bullet;
import nastmi.project.Entities.Enemy;
import nastmi.project.Entities.Player;
import com.badlogic.gdx.math.Intersector;


public class CollisionListener{
    public static Intersector intersector = new Intersector();
    public static Rectangle intersections = new Rectangle();
    public static void checkCollision(Player player, Array<Rectangle> arrOfCollisions, String axis, Array<Enemy> arrEnemies, Array<Bullet> bullet1, Array<Bullet> bullet2){
        Rectangle temp = new Rectangle();
        temp.setPosition(player.getRect().getX()+player.getCurrentSpeedX(),player.getRect().getY()+player.getCurrentSpeedY());
        for(Rectangle r:arrOfCollisions){
            if(intersector.intersectRectangles(player.getRect(),r,intersections)){
                player.respondToCollision(axis,r,intersections);
            }
        }
        for(Enemy e:arrEnemies){
            for(Rectangle r:arrOfCollisions){
                if(intersector.intersectRectangles(r,e.getRect(),intersections)){
                    e.respondToCollision(axis,r,intersections);
                }
            }
        }
        for(Enemy e:arrEnemies){
            if(intersector.intersectRectangles(player.getRect(),e.getRect(),intersections)){
                player.reactToDamage(2);
            }
        }
        for(Enemy e:arrEnemies){
            for(Rectangle r:arrOfCollisions){
                if(intersector.intersectRectangles(r,e.getRect(),intersections)){
                    e.respondToCollision(axis,r,intersections);
                }
            }
        }
        for(Bullet b:bullet1){
            for(Rectangle r:arrOfCollisions){
                if(intersector.intersectRectangles(b.getRect(),r,intersections))
                    bullet1.removeValue(b,false);
            }
        }
        for(Bullet b:bullet2){
            for(Rectangle r:arrOfCollisions){
                if(intersector.intersectRectangles(b.getRect(),r,intersections))
                    bullet2.removeValue(b,false);
            }
        }
        if(player.getRect().getY() < -0.5){
            player.reactToDamage(5);
            player.setCurrentSpeedY(12);
        }

    }
    public static void checkForDamage(Array<Bullet> playerBullets,Array<Bullet> enemyBullets,Array<Enemy> enemyArray, Player player){
        for(Bullet b:playerBullets){
            for(Enemy e:enemyArray){
                if(intersector.intersectRectangles(b.getRect(),e.getRect(),intersections)){
                    playerBullets.removeValue(b,false);
                    e.respondToDamage(b.getDamage());
                }
            }
        }
        for(Bullet b:enemyBullets){
            if(intersector.intersectRectangles(b.getRect(),player.getRect(),intersections)){
                if(player.getLastInstanceDamage() > player.getiFrames())
                    enemyBullets.removeValue(b,false);
                player.reactToDamage(b.getDamage());
                if(player.getHealth() <= 0){
                    player.setDead(true);
                }
            }
        }
    }

}
