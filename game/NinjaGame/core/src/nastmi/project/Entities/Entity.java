package nastmi.project.Entities;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.utils.Array;

//Base class for entities. Should never actually be used on it's own, use subclasses instead, depending on the type of entity.
public class Entity {
    private float x;
    private float y;
    private float width;
    private float height;
    private float startSpeed;
    private float currentSpeedX;
    private float currentSpeedY;
    private Rectangle rect;
    private Sprite sprite;

    public Entity(){

    }
    
    public Entity(float x, float y, float width, float height, float speed, Sprite startSprite){
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
        this.startSpeed = speed;
        this.rect = new Rectangle();
        this.rect.set(x,y,width,height);
        this.sprite = startSprite;
    }

    public void draw(SpriteBatch batch){
        batch.draw(this.getSprite().getTexture(),this.getRect().getX(),this.getRect().getY(),this.getWidth(),this.getHeight());
    }

    public void debugRender(Array<Rectangle> arr, ShapeRenderer renderShape, Camera camera, Rectangle... r){
        renderShape.setProjectionMatrix(camera.combined);
        renderShape.begin(ShapeRenderer.ShapeType.Line);
        renderShape.setColor(Color.BLUE);
        renderShape.rect(rect.x, rect.y, rect.width, rect.height);
        renderShape.end();
    }
    
    public void applyGravity(float dt, float gravity,float maxYSpeed){
        if(currentSpeedY >= -maxYSpeed) {
            currentSpeedY = currentSpeedY - gravity;
        }
        else if(currentSpeedY < -maxYSpeed)
            currentSpeedY = -maxYSpeed;
    }

    public void moveX(float dt){
        rect.setPosition(rect.getX()+currentSpeedX*dt,rect.getY());
    }

    public void moveY(float dt){
        rect.setPosition(rect.getX(),rect.getY()+currentSpeedY*dt);
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


    public float getStartSpeed() {
        return startSpeed;
    }

    public void setStartSpeed(float startSpeed) {
        this.startSpeed = startSpeed;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }
}
