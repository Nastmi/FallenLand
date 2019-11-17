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

    public Player(int x, int y, Sprite startSprite, float width, float height, World world,String name){
        this.x = x;
        this.y = y;
        this.sprite = startSprite;
        this.height = height*1/48f;
        this.width = width*1/48f;
        this.health = 20;
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

    public void move(String direction){
        switch (direction) {
            case "right":
                body.setTransform(body.getPosition().x+0.1f,body.getPosition().y,body.getAngle());
                break;
            case "left":
                body.setTransform(body.getPosition().x-0.1f,body.getPosition().y,body.getAngle());
                break;
        }
        updatePosition();
    }

    public void updatePosition(){
        oldX = x;
        oldY = y;
        x = body.getPosition().x-width/2;
        y = body.getPosition().y-height/2;

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
