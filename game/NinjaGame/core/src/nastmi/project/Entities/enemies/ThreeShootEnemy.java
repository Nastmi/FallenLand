package nastmi.project.Entities.enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import nastmi.project.Entities.Bullet;
import nastmi.project.Entities.Enemy;
import nastmi.project.utilities.Globals;
import nastmi.project.utilities.Sounds;

public class ThreeShootEnemy extends Enemy {

    public int shootFrames;
    int firstShot;
    int shotDelay;


    public ThreeShootEnemy(float x, float y, float width, float height, float speed, int health, Sprite startSprite, Texture spriteSheet, int length, float frameTime, int firstShoot, int shotDelay) {
        super(x, y, width, height, speed, health, startSprite, spriteSheet, length, frameTime);
        shootFrames = 0;
        this.firstShot = firstShoot;
        this.shotDelay = shotDelay;
    }

    @Override
    public void applyGravity(float dt,float gravity,float maxYSpeed){
        if (super.getCurrentSpeedY() >= -maxYSpeed) {
            super.setCurrentSpeedY(super.getCurrentSpeedY() - gravity);
        } else if (super.getCurrentSpeedY() < -maxYSpeed)
            super.setCurrentSpeedY(-maxYSpeed);
    }

    @Override
    public void framesUp(){
        shootFrames++;
    }

    @Override
    public Bullet[] shoot(){
        if(shootFrames == firstShot){
            Sounds.enemyFire.play(Globals.soundVolume);
            return new Bullet[]{new Bullet(super.getRect().getX()-0.32f,super.getRect().getY()+(super.getHeight()/2)+0.2f,0.5f,0.5f,0f,new Sprite(new Texture("bullets/bulletEnemy1.png")),-5,5,true,5,true)};

        }
        else if(shootFrames == firstShot+shotDelay){
            Sounds.enemyFire.play(Globals.soundVolume);
            return new Bullet[]{new Bullet(super.getRect().getX()+0.32f,super.getRect().getY()+(super.getHeight()/2)+0.2f,0.375f,0.5f,0f,new Sprite(new Texture("bullets/bulletEnemy0.png")),0,5,true,5,false)};
        }
        else if(shootFrames >= firstShot+shotDelay*2){
            Sounds.enemyFire.play(Globals.soundVolume);
            shootFrames = 0;
            return new Bullet[]{new Bullet(super.getRect().getX()+0.7f,super.getRect().getY()+(super.getHeight()/2)+0.2f,0.5f,0.5f,0f,new Sprite(new Texture("bullets/bulletEnemy1.png")),5,5,true,5,false)};
        }
        return null;
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
