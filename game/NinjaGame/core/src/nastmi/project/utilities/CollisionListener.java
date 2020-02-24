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
                    if(intersections.getX()>r.getX()) {
                        player.getRect().setX(player.getRect().getX() + intersections.getWidth());
                        player.setCurrentSpeedX(0.0f);
                    }
                    else if(intersections.getX()<r.getX()+r.getWidth()){
                        player.getRect().setX(player.getRect().getX() - intersections.getWidth());
                        player.setCurrentSpeedX(0.0f);
                    }
                }
                else if(intersections.getWidth()>intersections.getHeight()){
                    if(intersections.getY()>r.getY()) {
                        player.getRect().setY(player.getRect().getY() + intersections.getHeight());
                        player.setJumpCounter(0);
                        player.setCurrentSpeedY(0.0f);
                    }
                    else if(intersections.getY()<r.getY()+r.getHeight()) {
                        player.getRect().setY(player.getRect().getY() - intersections.getHeight());
                        player.setCurrentSpeedY(0.0f);
                    }
                }
            }
        }
        for(Player plr:p){
            if(intersector.intersectRectangles(player.getRect(),plr.getRect(),intersections)){
                if(intersections.getHeight()>intersections.getWidth()){
                    if(intersections.getX()>plr.getRect().getX()) {
                        player.getRect().setX(player.getRect().getX() + intersections.getWidth());
                        player.setCurrentSpeedX(0.0f);
                    }
                    else if(intersections.getX()<plr.getRect().getX()+plr.getRect().width){
                        player.getRect().setX(player.getRect().getX() - intersections.getWidth());
                        player.setCurrentSpeedX(0.0f);
                    }
                }
                else if(intersections.getWidth()>intersections.getHeight()){
                    if(intersections.getY()>plr.getRect().getY()) {
                        player.getRect().setY(player.getRect().getY() + intersections.getHeight());
                        player.setJumpCounter(0);
                        player.setCurrentSpeedY(0.0f);
                    }
                    else if(intersections.getY()<plr.getRect().getY()+plr.getRect().getHeight()) {
                        player.getRect().setY(player.getRect().getY() - intersections.getHeight());
                        player.setCurrentSpeedY(0.0f);

                    }
                }
                else if(intersections.getWidth()==intersections.getHeight()){
                    System.out.println("why");
                }
            }
        }
    }

}
