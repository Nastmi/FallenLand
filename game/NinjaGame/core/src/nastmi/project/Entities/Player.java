package nastmi.project.Entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.BodyDef.*;

//Subclass of entity used for the main player.
public class Player extends Entity{
    int health;
    Sprite sprite;
    float oldX;
    float oldY;
    int jumpCounter;

    public float getOldX() {
        return oldX;
    }

    public float getOldY() {
        return oldY;
    }

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

    public void move(float dt){
        rect.setPosition(rect.getX()+currentSpeedX*dt,rect.getY()+currentSpeedY*dt);
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
                if(jumpCounter < 2) {
                    currentSpeedY += 12;
                    jumpCounter++;
                }
                break;
            case "stop":
                currentSpeedX = 0;
                break;
        }
    }

    public void applyGravity(float dt){
        if(currentSpeedY >= -10.0f) {
            currentSpeedY = currentSpeedY - 0.2f;
        }
        else if(currentSpeedY < -10.0f)
            currentSpeedY = -10.0f;

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


    public int getJumpCounter() {
        return jumpCounter;
    }

    public void setJumpCounter(int jumpCounter) {
        this.jumpCounter = jumpCounter;
    }
}
