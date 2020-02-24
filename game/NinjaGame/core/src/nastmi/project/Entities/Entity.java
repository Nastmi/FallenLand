package nastmi.project.Entities;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;

//Base class for entities. Should never actually be used on it's own, use subclasses instead, depending on the type of entity.
public class Entity {
    float x;
    float y;
    float oldX;
    float oldY;
    float width;
    float height;
    float startSpeed;
    float currentSpeedX;
    float currentSpeedY;
    Rectangle rect;

    public void applyGravity(float dt){
        if(currentSpeedY >= -10.0f) {
            currentSpeedY = currentSpeedY - 0.2f;
        }
        else if(currentSpeedY < -10.0f)
            currentSpeedY = -10.0f;

    }

    public void move(float dt){
        oldX = rect.getX();
        oldY = rect.getY();
        rect.setPosition(rect.getX()+currentSpeedX*dt,rect.getY()+currentSpeedY*dt);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getCurrentSpeedX() {
        return currentSpeedX;
    }

    public void setCurrentSpeedX(float currentSpeedX) {
        this.currentSpeedX = currentSpeedX;
    }

    public float getCurrentSpeedY() {
        return currentSpeedY;
    }

    public void setCurrentSpeedY(float currentSpeedY) {
        this.currentSpeedY = currentSpeedY;
    }

    public Rectangle getRect() {
        return rect;
    }

    public void setRect(Rectangle rect) {
        this.rect = rect;
    }

    public float getOldX() {
        return oldX;
    }

    public void setOldX(float oldX) {
        this.oldX = oldX;
    }

    public float getOldY() {
        return oldY;
    }

    public void setOldY(float oldY) {
        this.oldY = oldY;
    }

    public float getStartSpeed() {
        return startSpeed;
    }

    public void setStartSpeed(float startSpeed) {
        this.startSpeed = startSpeed;
    }
}
