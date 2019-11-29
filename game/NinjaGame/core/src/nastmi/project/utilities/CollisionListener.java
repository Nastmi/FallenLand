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
                    if(intersections.getX()>r.getX()+r.getWidth()/2)
                        player.getRect().setX(r.getX()+player.getWidth());
                    else if(intersections.getX()<r.getX()+r.getWidth()/2)
                        player.getRect().setX(r.getX()-player.getWidth());
                }
                else if(intersections.getWidth()>intersections.getHeight()) {
                    if (intersections.getY() > r.getY() + r.getHeight() / 2) {
                        player.getRect().setY(r.getY() + r.getHeight());
                        player.setJumpCounter(0);
                        player.setCurrentSpeedY(0);
                    }
                    else if (intersections.getY() < r.getY() + r.getHeight() / 2) {
                        player.getRect().setY(r.getY() - player.getRect().getHeight());
                    }
                }

            }
        }
        for(Player plr:p){
            if(intersector.intersectRectangles(player.getRect(),plr.getRect(),intersections)){
                if(intersections.getHeight()>intersections.getWidth()){

                    System.out.println("duck");
                    if(intersections.getX()>plr.getRect().getX()+plr.getRect().getWidth()/2) {
                        player.getRect().setX(plr.getRect().getX() + plr.getWidth());
                        player.setCurrentSpeedX(0.0f);
                    }
                    else if(intersections.getX()<plr.getRect().getX()+plr.getRect().getWidth()/2){

                        player.getRect().setX(plr.getRect().getX()-player.getWidth());
                        player.setCurrentSpeedX(0.0f);
                    }
                }
                else if(intersections.getWidth()>intersections.getHeight()){
                    System.out.println("buck");
                    if(intersections.getY()>plr.getRect().getY()+plr.getRect().getHeight()/2) {
                        player.getRect().setY(plr.getRect().getY() + plr.getHeight());
                        player.setJumpCounter(0);
                        player.setCurrentSpeedY(0.0f);
                    }
                    else if(intersections.getY()<plr.getRect().getY()+plr.getRect().getHeight()/2) {
                        player.getRect().setY(plr.getRect().getY() - player.getRect().getHeight());
                        player.setCurrentSpeedY(0.0f);

                    }
                }
                else if(intersections.getWidth()==intersections.getHeight()){
                    System.out.println("cuck");
                }
            }
        }
    }

}
