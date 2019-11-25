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
        rect.setPosition(rect.getX()+currentSpeedX*dt,rect.getY()+currentSpeedY);
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
            case "stop":
                currentSpeedX = 0;
                break;
        }
    }

    public void applyGravity(float dt){
        if(currentSpeedY >= -3.0f) {
            currentSpeedY = currentSpeedY - 3.0f * dt;
            System.out.println(currentSpeedY);
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

    public void setOldX(float oldX) {
        this.oldX = oldX;
    }

    public void setOldY(float oldY) {
        this.oldY = oldY;
    }


}
