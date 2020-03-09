package nastmi.project.utilities;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import nastmi.project.Entities.Obstacle;
import nastmi.project.Entities.Player;
import com.badlogic.gdx.math.Intersector;
import nastmi.project.ninjagame.GameScreen;

import javax.lang.model.type.ArrayType;


public class CollisionListener{
    public static Intersector intersector = new Intersector();
    public static Rectangle intersections = new Rectangle();
    public static void checkCollision(Player player, Array<Rectangle> arrOfCollisions,String axis,Player... p){
        Rectangle temp = new Rectangle();
        temp.setPosition(player.getRect().getX()+player.getCurrentSpeedX(),player.getRect().getY()+player.getCurrentSpeedY());
        for(Rectangle r:arrOfCollisions){
            if(intersector.intersectRectangles(player.getRect(),r,intersections)){
                if(axis.equals("x")){
                    if(intersections.getX()>r.getX()) {
                        player.getRect().setX(player.getRect().getX() + intersections.getWidth());
                        player.setCurrentSpeedX(0.0f);
                    }
                    else if(intersections.getX()<r.getX()+r.getWidth()){
                        player.getRect().setX(player.getRect().getX() - intersections.getWidth());
                        player.setCurrentSpeedX(0.0f);
                    }
                }
                else if(axis.equals("y")){
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
                if(axis.equals("x")){
                    if(intersections.getX()>plr.getRect().getX()) {
                        player.getRect().setX(player.getRect().getX() + intersections.getWidth());
                        player.setCurrentSpeedX(0.0f);
                    }
                    else if(intersections.getX()<plr.getRect().getX()+plr.getRect().width){
                        player.getRect().setX(player.getRect().getX() - intersections.getWidth());
                        player.setCurrentSpeedX(0.0f);
                    }
                }
                else if(axis.equals("y")){
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
    public static void checkForDamage(Array<Object> damageSources, Player player){
        for(Object o:damageSources){
            if(o instanceof Obstacle){
                Obstacle temp = new Obstacle();
                temp = (Obstacle)o;
                if(intersector.intersectRectangles(temp.getRect(),player.getRect(),intersections)){
                    player.reactToDamage(temp.getDamage());
                }
            }
        }
    }

}
