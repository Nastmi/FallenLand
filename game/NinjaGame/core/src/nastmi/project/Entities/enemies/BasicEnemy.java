package nastmi.project.Entities.enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import nastmi.project.Entities.Enemy;

public class BasicEnemy extends Enemy {

    private boolean flip;

    public BasicEnemy(float x, float y, float width, float height, float speed, int health, Sprite startSprite, Texture spriteSheet, int length, float frameTime) {
        super(x, y, width, height, speed, health, startSprite, spriteSheet, length, frameTime);
        flip = false;
    }

    @Override
    public void calculateNextSpeed(){

    }

    @Override
    public void draw(SpriteBatch batch){
        super.setElapsedTime(super.getElapsedTime()+Gdx.graphics.getDeltaTime());
        if(flip){
            for(TextureRegion t:super.getAnimation().getKeyFrames()){
                t.flip(true,false);
            }
            flip = false;
        }
        TextureRegion currentFrame = super.getAnimation().getKeyFrame(super.getElapsedTime(),true);
        batch.draw(currentFrame,this.getRect().getX(),this.getRect().getY(),super.getRect().getWidth(),super.getRect().getHeight());
    }

    @Override
    public void respondToCollision(String axis, Rectangle collisionObject , Rectangle intersections){
        if(axis.equals("x")){
            if(intersections.getX()>collisionObject.getX()) {
                super.getRect().setX(super.getRect().getX() + intersections.getWidth());
            }
            else if(intersections.getX()<collisionObject.getX()+collisionObject.width){
                super.getRect().setX(super.getRect().getX() - intersections.getWidth());
            }
            setCurrentSpeedX(-(getCurrentSpeedX()));
            flip = true;
        }
        else if(axis.equals("y")){
            if(intersections.getY()>collisionObject.getY()) {
                super.getRect().setY(super.getRect().getY() + intersections.getHeight());
                super.setCurrentSpeedY(0.0f);
            }
            else if(intersections.getY()<collisionObject.getY()+collisionObject.getHeight()) {
                super.getRect().setY(super.getRect().getY() - intersections.getHeight());
                super.setCurrentSpeedY(0.0f);
            }
        }
    }

    @Override
    public void applyGravity(float dt,float gravity,float maxYSpeed){
            if (super.getCurrentSpeedY() >= -maxYSpeed) {
                super.setCurrentSpeedY(super.getCurrentSpeedY() - gravity);
            } else if (super.getCurrentSpeedY() < -maxYSpeed)
                super.setCurrentSpeedY(-maxYSpeed);
    }
}
