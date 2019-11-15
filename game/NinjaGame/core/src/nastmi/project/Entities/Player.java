package nastmi.project.Entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.World;

//Subclass of entity used for the main player.
public class Player extends Entity{
    int health;
    Sprite sprite;
    float oldX;
    float oldY;

    public float getOldX() {
        return oldX;
    }

    public float getOldY() {
        return oldY;
    }

    public Player(int x, int y, Sprite startSprite, float width, float height){
        this.x = x;
        this.y = y;
        this.sprite = startSprite;
        this.height = height;
        this.width = width;
        this.health = 20;
    }


    public int getHealth() {
        return health;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public void setOldX(float oldX) {
        this.oldX = oldX;
    }

    public void setOldY(float oldY) {
        this.oldY = oldY;
    }
}
