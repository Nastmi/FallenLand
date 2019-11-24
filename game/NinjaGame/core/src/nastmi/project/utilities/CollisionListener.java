package nastmi.project.utilities;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import nastmi.project.Entities.Player;
import com.badlogic.gdx.math.Intersector;


public class CollisionListener{
    public static Intersector intersector = new Intersector();
    public static void checkCollision(Player player, Array<Rectangle> arrOfCollisions){
        Rectangle interesctions = new Rectangle();
        for(Rectangle r:arrOfCollisions){
            if(intersector.intersectRectangles(player.getRect(),r,interesctions))
                
        }

    }

};
