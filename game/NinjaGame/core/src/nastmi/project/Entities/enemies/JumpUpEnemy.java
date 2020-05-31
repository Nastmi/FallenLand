package nastmi.project.Entities.enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import nastmi.project.Entities.Bullet;
import nastmi.project.Entities.Enemy;
import nastmi.project.utilities.Globals;
import nastmi.project.utilities.Sounds;

public class JumpUpEnemy extends Enemy {

    private int jumpFrames;
    private int currentFrames;
    private float ySpeed;
    private boolean shoot;
    private int shootFrames;
    private int currentShootFrames;

    public JumpUpEnemy(float x, float y, float width, float height, float speed, int health, Sprite startSprite, Texture spriteSheet, int length, float frameTime, float ySpeed, int jumpFrames, boolean shoot, int shootFrames) {
        super(x, y, width, height, speed, health, startSprite, spriteSheet, length, frameTime);
        currentFrames = 0;
        this.jumpFrames = jumpFrames;
        this.ySpeed = ySpeed;
        this.shoot = shoot;
        this.shootFrames = shootFrames;
        currentShootFrames = 0;
    }

    @Override
    public Bullet[] shoot(){
        if(shoot){
            if(currentShootFrames >= shootFrames){
                Sounds.enemyFire.play(Globals.soundVolume);
                currentShootFrames = 0;
                return new Bullet[]{new Bullet(super.getRect().getX()-0.32f,super.getRect().getY()+(super.getHeight()/2)+0.12f,0.5f,0.375f,0f,
                        new Sprite(new Texture("bullets/bulletEnemy2.png")),-5,0,true,5,false)};
            }
        }

        return null;
    }

    @Override
    public void calculateNextSpeed(){
        if(currentFrames >= jumpFrames){
            super.setCurrentSpeedY(ySpeed);
            currentFrames = 0;
        }
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

    @Override
    public void framesUp(){
        currentFrames++;
        currentShootFrames++;
        ;
    }

}

