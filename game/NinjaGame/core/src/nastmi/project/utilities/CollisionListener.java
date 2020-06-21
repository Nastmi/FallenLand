package nastmi.project.utilities;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;
import nastmi.project.Entities.Bullet;
import nastmi.project.Entities.Enemy;
import nastmi.project.Entities.Player;
import com.badlogic.gdx.math.Intersector;
import nastmi.project.Entities.enemies.BounceEnemy;
import nastmi.project.ninjagame.GameScreen;
import nastmi.project.ninjagame.MainGame;
import nastmi.project.ninjagame.StoryScreen;


public class CollisionListener{
    public static Intersector intersector = new Intersector();
    public static Rectangle intersections = new Rectangle();
    public static void checkCollision(Player player, Array<Rectangle> arrOfCollisions, String axis, Array<Enemy> arrEnemies, Array<Bullet> bullet1, Array<Bullet> bullet2, Rectangle end, MainGame game, boolean gMode){
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
        if(!gMode){
            for(Enemy e:arrEnemies){
                if(intersector.intersectRectangles(player.getRect(),e.getRect(),intersections)){
                    if(e instanceof BounceEnemy)
                        player.reactToDamage(4);
                    else
                        player.reactToDamage(2);
                }
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
        if(intersector.intersectRectangles(player.getRect(),end,intersections)){
            Globals.currentLevel++;
            if(Globals.currentLevel >= 4){
                Sounds.levelTheme.stop();
                Sounds.victoryTheme.setVolume(Globals.musicVolume+0.2f);
                Sounds.victoryTheme.play();
                game.setScreen(new StoryScreen(game,5));
            }
            else{
                game.setScreen(new GameScreen(game));
            }
        }
        if(player.getRect().getY() < -0.5){
            player.reactToDamage(5);
            player.setCurrentSpeedY(12);
            if(player.getHealth() <= 0){
                player.setDead(true);
            }
        }

    }
    public static void checkForDamage(Array<Bullet> playerBullets,Array<Bullet> enemyBullets,Array<Enemy> enemyArray, Player player, boolean gMode){
        for(Bullet b:playerBullets){
            for(Enemy e:enemyArray){
                if(intersector.intersectRectangles(b.getRect(),e.getRect(),intersections)){
                    playerBullets.removeValue(b,false);
                    e.respondToDamage(b.getDamage());
                }
            }
        }
        if(!gMode){
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

}
