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
    String name;

    public String getName() {
        return name;
    }

    public float getOldX() {
        return oldX;
    }

    public float getOldY() {
        return oldY;
    }

    public Player(int x, int y, Sprite startSprite, float width, float height,String name, float speed){
        this.x = x;
        this.y = y;
        this.oldX = x;
        this.oldY = y;
        this.sprite = startSprite;
        this.height = height;
        this.width = width;
        this.health = 20;
        this.startSpeed = speed;
        this.name = name;
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
                /*body.setTransform(body.getPosition().x,body.getPosition().y+currentSpeed*dt,body.getAngle());
                break;*/
            case "down":
                /*body.setTransform(body.getPosition().x,body.getPosition().y-currentSpeed*dt,body.getAngle());
                break;*/
            case "stop":
                currentSpeedX = 0;
                break;
        }
    }

    /*public void move(float dt){
        body.setTransform(body.getPosition().x+currentSpeedX*dt,body.getPosition().y+currentSpeedY*dt,body.getAngle());
    }*/


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
