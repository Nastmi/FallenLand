package nastmi.project.Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import nastmi.project.utilities.Globals;
import nastmi.project.utilities.Sounds;


public class Enemy extends LiveEntity{
    //1 = up, -1 = down
    private int moveDir;
    //1 = speeding up, -1 = slowing down
    private int moveStage;
    private Animation<TextureRegion> animation;
    private Texture spriteSheet;
    private float elapsedTime;
    TextureRegion[] animFrames;
    float frameTime;

    public Enemy(float x, float y, float width, float height, float speed, int health, Sprite startSprite, Texture spriteSheet, int length, float frameTime) {
        super(x, y, width, height, speed, health, startSprite);
        super.setCurrentSpeedX(super.getStartSpeed());
        super.setCurrentSpeedY(0);
        this.spriteSheet = spriteSheet;
        TextureRegion[][] tmp = TextureRegion.split(spriteSheet,spriteSheet.getWidth()/length,spriteSheet.getHeight()/1);
        animFrames = new TextureRegion[length];
        int idx = 0;
        for(int i=0;i<1;i++){
            for(int j=0;j<length;j++){
                animFrames[idx++] = tmp[i][j];
            }
        }
        animation = new Animation<TextureRegion>(frameTime,animFrames);
        frameTime = frameTime;
        elapsedTime = 0f;


    }

    public void calculateNextSpeed(){

    }

    @Override
    public void draw(SpriteBatch batch){
        elapsedTime+= Gdx.graphics.getDeltaTime();
        TextureRegion currentFrame = animation.getKeyFrame(elapsedTime,true);
        batch.draw(currentFrame,this.getRect().getX(),this.getRect().getY(),super.getRect().getWidth(),super.getRect().getHeight());
    }

    @Override
    public void moveY(float dt){
        super.getRect().setPosition(super.getRect().getX(),super.getRect().getY()+super.getCurrentSpeedY()*dt);
    }

    @Override
    public void applyGravity(float dt, float gravity, float maxYSpeed){
    }

    public Bullet[] shoot(){
        return null;
    }

    public void respondToCollision(String axis, Rectangle collisionObject , Rectangle intersections){

    }

    public void respondToDamage(int damage){
        super.setHealth(super.getHealth()-damage);
        Sounds.enemyDamage.play(Globals.soundVolume);
    }


    public void framesUp(){};

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

    public Animation<TextureRegion> getAnimation() {
        return animation;
    }

    public void setAnimation(Animation<TextureRegion> animation) {
        this.animation = animation;
    }

    public Texture getSpriteSheet() {
        return spriteSheet;
    }

    public void setSpriteSheet(Texture spriteSheet) {
        this.spriteSheet = spriteSheet;
    }

    public float getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(float elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    public TextureRegion[] getAnimFrames() {
        return animFrames;
    }

    public void setAnimFrames(TextureRegion[] animFrames) {
        this.animFrames = animFrames;
    }

    public float getFrameTime() {
        return frameTime;
    }

    public void setFrameTime(float frameTime) {
        this.frameTime = frameTime;
    }
}
