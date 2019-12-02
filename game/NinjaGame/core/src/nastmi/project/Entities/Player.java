package nastmi.project.Entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.BodyDef.*;

//Subclass of entity used for the main player.
public class Player extends Entity{
    int health;
    Sprite sprite;
    int jumpCounter;


    public Player(int x, int y, Sprite startSprite, float width, float height, float speed){
        this.x = x;
        this.y = y;
        this.sprite = startSprite;
        this.height = height;
        this.width = width;
        this.health = 20;
        this.startSpeed = speed;
        this.rect = new Rectangle();
        this.rect.set(x,y,width,height);
    }


    public void setSpeed(String direction){
        switch (direction) {
            case "right":
                if(currentSpeedX < 3.0f)
                    currentSpeedX += startSpeed;
                break;
            case "left":
                if(currentSpeedX > -3.0f)
                    currentSpeedX -= startSpeed;
                break;
            case "up":
                if(jumpCounter < 3) {
                    currentSpeedY = 8;
                    jumpCounter++;
                }
                break;
            case "stop":
                currentSpeedX = 0;
                break;
        }
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

    public int getJumpCounter() {
        return jumpCounter;
    }

    public void setJumpCounter(int jumpCounter) {
        this.jumpCounter = jumpCounter;
    }
}
