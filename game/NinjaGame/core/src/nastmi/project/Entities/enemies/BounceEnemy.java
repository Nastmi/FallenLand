package nastmi.project.Entities.enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import nastmi.project.Entities.Enemy;
import nastmi.project.utilities.Globals;
import nastmi.project.utilities.Sounds;

public class BounceEnemy extends Enemy {

    private float ySpeed;

    public BounceEnemy(float x, float y, float width, float height, float speed, int health, Sprite startSprite, Texture spriteSheet, int length, float frameTime, float ySpeed) {
        super(x, y, width, height, speed, health, startSprite, spriteSheet, length, frameTime);
        super.setCurrentSpeedY(0.01f);
        this.ySpeed = ySpeed;
    }

    @Override
    public void calculateNextSpeed(){
        if(super.getCurrentSpeedY() == 0){
            super.setCurrentSpeedY(ySpeed);
        }
    }

    @Override
    public void respondToDamage(int damage){
        Sounds.enemyClank.play(Globals.soundVolume);
    }

    @Override
    public void applyGravity(float dt, float gravity, float maxYSpeed){
        if(super.getCurrentSpeedY() >= -maxYSpeed) {
            super.setCurrentSpeedY(super.getCurrentSpeedY() - gravity);
        }
        else if(super.getCurrentSpeedY() < -maxYSpeed)
            super.setCurrentSpeedY(-maxYSpeed);
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
}
