package nastmi.project.Entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.BodyDef.*;

//Subclass of entity used for the main player.
public class Player extends Entity{
    int health;
    Sprite sprite;
    float oldX;
    float oldY;
    float speed;
    String name;
    boolean colided;

    public String getName() {
        return name;
    }

    public float getOldX() {
        return oldX;
    }

    public float getOldY() {
        return oldY;
    }

    public Player(int x, int y, Sprite startSprite, float width, float height, World world,String name, float speed){
        this.x = x;
        this.y = y;
        this.oldX = x;
        this.oldY = y;
        this.sprite = startSprite;
        this.height = height;
        this.width = width;
        this.health = 20;
        this.speed = speed;
        bodyDef= new BodyDef();
        bodyDef.type = BodyType.DynamicBody;
        bodyDef.position.set(this.x+this.width/2,this.y+this.height/2);
        body = world.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(this.height/2,this.width/2);
        fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.isSensor = true;
        fixture = body.createFixture(fixtureDef);
        body.setUserData(this);
        this.name = name;
    }

    public void move(String direction, float dt){
        switch (direction) {
            case "right":
                body.setTransform(body.getPosition().x+speed*dt,body.getPosition().y,body.getAngle());
                break;
            case "left":
                body.setTransform(body.getPosition().x-speed*dt,body.getPosition().y,body.getAngle());
                break;
            case "up":
                body.setTransform(body.getPosition().x,body.getPosition().y+speed*dt,body.getAngle());
                break;
            case "down":
                body.setTransform(body.getPosition().x,body.getPosition().y-speed*dt,body.getAngle());
                break;
        }
    }

    public void updatePosition(){
        oldX = x;
        oldY = y;
        x = body.getPosition().x-width/2;
        y = body.getPosition().y-height/2;
    }

    public void reverseUpdatePosition(){
        x = oldX;
        y = oldY;
        oldX = x = body.getPosition().x-width/2;
        oldY = body.getPosition().y-height/2;
    }

    public void setOldPosition(){
        body.setTransform(oldX+width/2,oldY+height/2,body.getAngle());
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

    @Override
    public float getSpeed() {
        return speed;
    }

    @Override
    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public boolean isColided() {
        return colided;
    }

    public void setColided(boolean colided) {
        this.colided = colided;
    }
}
