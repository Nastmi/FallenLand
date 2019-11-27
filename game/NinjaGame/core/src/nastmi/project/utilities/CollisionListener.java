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
                if(intersections.getHeight()>intersections.getWidth()){
                    if(player.getCurrentSpeedX()<0)
                        player.getRect().setX(r.getX()+player.getWidth());
                    else if(player.getCurrentSpeedX()>0)
                        player.getRect().setX(r.getX()-player.getWidth());
                }
                else if(intersections.getWidth()>intersections.getHeight()){
                    if(player.getCurrentSpeedY()<0) {
                        player.getRect().setY(r.getY() + r.getHeight());
                        player.setJumpCounter(0);
                    }
                    else if(player.getCurrentSpeedY()>0) {
                        player.getRect().setY(r.getY() - player.getRect().getHeight());
                    }
                }


            }
        }
        for(Player plr:p){
            if(intersector.intersectRectangles(player.getRect(),plr.getRect(),intersections)){
                System.out.println("Height: "+intersections.getHeight()+" width: "+intersections.getWidth());
                if(intersections.getHeight()>intersections.getWidth()){
                    if(player.getCurrentSpeedX()<0)
                        player.getRect().setX(plr.getRect().getX()+plr.getWidth());
                    else if(player.getCurrentSpeedX()>0)
                        player.getRect().setX(plr.getRect().getX()-player.getWidth());
                }
                else if(intersections.getWidth()>intersections.getHeight()){
                    System.out.println("HEre");
                    if(player.getCurrentSpeedY()<0)
                        player.getRect().setY(plr.getRect().getY()+plr.getHeight());
                    else if(player.getCurrentSpeedY()>0) {
                        player.getRect().setY(plr.getRect().getY() - player.getRect().getHeight());
                        player.setCurrentSpeedY(0.0f);
                    }
                }
            }
        }
    }

};
