package nastmi.project.utilities;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import nastmi.project.Entities.Player;
import com.badlogic.gdx.math.Intersector;
import nastmi.project.ninjagame.GameScreen;


public class CollisionListener{
    public static Intersector intersector = new Intersector();
    public static void checkCollision(Player player, Array<Rectangle> arrOfCollisions,Player... p){
        Rectangle intersections = new Rectangle();
        Rectangle temp = new Rectangle();
        temp.setPosition(player.getRect().getX()+player.getCurrentSpeedX(),player.getRect().getY()+player.getCurrentSpeedY());
        for(Rectangle r:arrOfCollisions){
            if(intersector.intersectRectangles(player.getRect(),r,intersections)){
                if(player.getCurrentSpeedX()<0)
                    player.getRect().setX(r.getX()+player.getWidth());
                else if(player.getCurrentSpeedX()>0)
                    player.getRect().setX(r.getX()-player.getWidth());

            }
        }
        for(Player plr:p){
            if(intersector.intersectRectangles(player.getRect(),plr.getRect(),intersections)){
                if(player.getCurrentSpeedX()<0)
                    player.getRect().setX(plr.getRect().getX()+player.getWidth());
                else if(player.getCurrentSpeedX()>0)
                    player.getRect().setX(plr.getRect().getX()-player.getWidth());

            }
        }
    }

};
