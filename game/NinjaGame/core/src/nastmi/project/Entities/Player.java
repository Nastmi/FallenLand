package nastmi.project.Entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.World;

//Subclass of entity used for the main player.
public class Player extends Entity{
    int health;
    Sprite sprite;

    public Player(int x,int y, Sprite startSprite){
        this.x = x;
        this.y = y;
        this.sprite = startSprite;
        this.health = 20;
    }


}
