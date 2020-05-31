package nastmi.project.Entities.enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import nastmi.project.Entities.Bullet;
import nastmi.project.Entities.Enemy;
import nastmi.project.utilities.Globals;
import nastmi.project.utilities.Sounds;

public class WaveEnemy extends Enemy {

    private int moveDir;
    private int moveStage;
    private float startSpeedY;
    private boolean shoot;
    private int shootFrames;
    private int currentShootFrames;

    public WaveEnemy(float x, float y, float width, float height, float speed, int health, Sprite startSprite, Texture spriteSheet, int length, float frameTime, float ySpeed,boolean shoot, int shootFrames) {
        super(x, y, width, height, speed, health, startSprite, spriteSheet, length, frameTime);
        if(ySpeed > 0){
            moveDir = 1;
            moveStage = 1;
        }
        else{
            moveDir = -1;
            moveStage = -1;
        }

        this.startSpeedY = ySpeed;
        this.shoot = shoot;
        this.shootFrames = shootFrames;
        currentShootFrames = 0;
        this.shoot = shoot;
    }

    @Override
    public void calculateNextSpeed(){
        if(this.getMoveStage() == 1 && Math.abs(super.getCurrentSpeedY()) >= this.getStartSpeedY())
            this.setMoveStage(-this.getMoveStage());
        if(this.getMoveStage() == -1 && super.getCurrentSpeedY() <= 0f) {
            this.setMoveStage(-this.getMoveStage());
            this.setMoveDir(-this.getMoveDir());
        }
        super.setCurrentSpeedY(super.getCurrentSpeedY() + (0.1f*this.getMoveStage()*this.getMoveDir()));
    }

    @Override
    public Bullet[] shoot(){
        if(shoot){
            if(currentShootFrames >= shootFrames){
                Sounds.enemyFire.play(Globals.soundVolume);
                currentShootFrames = 0;
                return new Bullet[]{new Bullet(super.getRect().getX()-0.32f,super.getRect().getY()+(super.getHeight()/2),0.5f,0.375f,0f,new Sprite(new Texture("bullets/bulletEnemy2.png")),-5,0,true,5,false)};
            }
        }
        return null;
    }

    @Override
    public void framesUp(){
        currentShootFrames++;
    }

    public int getMoveDir() {
        return moveDir;
    }

    public void setMoveDir(int moveDir) {
        this.moveDir = moveDir;
    }

    public int getMoveStage() {
        return moveStage;
    }

    public void setMoveStage(int moveStage) {
        this.moveStage = moveStage;
    }

    public float getStartSpeedY(){
        return startSpeedY;
    }

    public void setStartSpeedY(float startSpeedY) {
        this.startSpeedY = startSpeedY;
    }
}
