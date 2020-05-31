package nastmi.project.Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import nastmi.project.utilities.Globals;
import nastmi.project.utilities.Sounds;

//Subclass of entity used for the main super.
public class Player extends LiveEntity{
    private int jumpCounter;
    private int iFrames = 120;
    private int lastInstanceDamage = iFrames+1;
    private int shotFrames = 15;
    private int lastShot = shotFrames+1;
    private String lastDir = "right";
    private int dir;
    private float bulletSpeed = 8f;
    private Animation<TextureRegion> animation;
    private Texture spriteSheet;
    private float elapsedTime;
    TextureRegion[] animFrames;
    private int length;
    private float frameTime;
    private boolean fixX;

    public Player(float x, float y, float width, float height, float speed,int health, Sprite startSprite, Texture spriteSheet, int length, float frameTime){
        super(x,y,width,height,speed,health,startSprite);
        super.getRect().width = 0.4375f;
        super.getRect().height = 1;
        frameTime = frameTime;
        this.frameTime = frameTime;
        this.elapsedTime = 0f;
        this.length = length;
        createAnimation();
        this.dir = 2;
        super.getRect().setX(super.getX()+0.25f);
    }


    public void createAnimation(){
        int fileDir = Math.abs(dir);
        spriteSheet = new Texture(Gdx.files.internal("player/char"+fileDir+".png"));
        if(((lastDir.equals("left") || lastDir.equals("right"))) && dir != 0 && dir!= 4){
            spriteSheet = new Texture(Gdx.files.internal("player/charIdle.png"));
        }
        TextureRegion[][] tmp = TextureRegion.split(spriteSheet,spriteSheet.getWidth()/length,spriteSheet.getHeight()/1);
        animFrames = new TextureRegion[length];
        int idx = 0;
        for(int i=0;i<1;i++){
            for(int j=0;j<length;j++){
                animFrames[idx++] = tmp[i][j];
            }
        }
        animation = new Animation<TextureRegion>(frameTime,animFrames);
        if(dir < 0 || ((dir == 0 || dir == 4) && lastDir == "left")){
            for(TextureRegion t:animation.getKeyFrames()){
                t.flip(true,false);

            }
        }
    }

    @Override
    public void moveX(float dt){
        super.getRect().setPosition(super.getRect().getX()+super.getCurrentSpeedX()*dt,super.getRect().getY());
        super.setX(super.getRect().getX()-0.21875f);
    }

    @Override
    public void draw(SpriteBatch batch){
        elapsedTime+= Gdx.graphics.getDeltaTime();
        TextureRegion currentFrame = animation.getKeyFrame(elapsedTime,true);
        Color clr = batch.getColor();
        float oldA = clr.a;
        if(lastInstanceDamage < iFrames){
            if(lastInstanceDamage < 20 || (lastInstanceDamage > 40 && lastInstanceDamage < 60) || (lastInstanceDamage > 80 && lastInstanceDamage < 100)){
                clr.a = clr.a*0.3f;
            }
        }
        batch.setColor(clr);
        batch.draw(currentFrame,super.getX(),this.getRect().getY(),1f,1.125f);
        clr.a = oldA;
        batch.setColor(clr);
    }


    public void setSpeed(String direction){
        switch (direction) {
            case "right":
                if(super.getCurrentSpeedX() < super.getStartSpeed())
                    super.setCurrentSpeedX(super.getCurrentSpeedX()+super.getStartSpeed());
                break;
            case "left":
                if(super.getCurrentSpeedX() > -super.getStartSpeed())
                    super.setCurrentSpeedX(super.getCurrentSpeedX()-super.getStartSpeed());
                break;
            case "up":
                if(jumpCounter < 2) {
                    super.setCurrentSpeedY(8);
                    Sounds.playerJump.play(Globals.soundVolume);
                    jumpCounter++;
                }
                break;
            case "stop":
                super.setCurrentSpeedX(0);
                break;
        }
    }

    @Override
    public void reactToDamage(int damage){
        if(lastInstanceDamage > iFrames) {
            Sounds.playerDamage.play(Globals.soundVolume);
            super.setHealth(super.getHealth()-damage);
            lastInstanceDamage = 0;
        }
    }

     public void setDir(boolean r, boolean l, boolean u,boolean d){
        if(r) {
            if (u)
                dir = 1;
            else if (d)
                dir = 3;
            else
                dir = 2;
        }
        else if(l){
            if(u)
                dir = -1;
            else if(d)
                dir = -3;
            else
                dir = -2;
        }
        else if(d){
            dir = 4;
        }
        else if(u){
            dir = 0;
        }
        /*else if(lastDir.equals("right")){
            dir = 2;
        }
        else if(lastDir.equals(("left"))){
            dir = -2;
        }*/
    }

    public Bullet[] shoot(boolean r, boolean l, boolean u,boolean d,boolean t){
        if(t){
            if(lastShot >= shotFrames){
                Sounds.playerFire.play(Globals.soundVolume);
                int fileDir = Math.abs(dir);
                Texture txt;
                Sprite tempBullet = new Sprite(new Texture(Gdx.files.internal("bullets/bulletFriendly"+fileDir+".png")));
                lastShot = 0;
                if (dir == 1)
                    return new Bullet[]{new Bullet(super.getRect().getX() + super.getWidth(), super.getRect().getY()+super.getHeight(), 0.5f, 0.5f, 0f, tempBullet, bulletSpeed, bulletSpeed,true,5,false)};
                else if (dir == 3)
                    return new Bullet[]{new Bullet(super.getRect().getX() + super.getWidth(), super.getRect().getY()-0.5f, 0.5f, 0.5f, 0f, tempBullet, bulletSpeed, -bulletSpeed,true,5,false)};
                else if (dir == 2)
                    return new Bullet[]{new Bullet(super.getRect().getX() + super.getWidth()-0.125f, super.getRect().getY() + (super.getHeight() / 2)-0.125f, 0.5f, 0.375f, 0f, tempBullet, bulletSpeed, 0,true,5,false)};
                if(dir == -1)
                    return new Bullet[]{new Bullet(super.getRect().getX()-0.6f,super.getRect().getY()+super.getHeight()-0.125f,0.5f,0.5f,0f,tempBullet,-bulletSpeed,bulletSpeed,true,5,true)};
                else if(dir == -3)
                    return new Bullet[]{new Bullet(super.getRect().getX()-0.46f,super.getRect().getY()-0.125f,0.5f,0.5f,0f,tempBullet,-bulletSpeed,-bulletSpeed,true,5,true)};
                else if(dir == -2)
                    return new Bullet[]{new Bullet(super.getRect().getX()-0.6f,super.getRect().getY()+(super.getHeight()/2)-0.125f,0.5f,0.375f,0f,tempBullet,-bulletSpeed,0,true,5,true)};
                else if(dir == 4)
                    return new Bullet[]{new Bullet(super.getRect().getX()+super.getWidth()/2-0.3f,super.getRect().getY()-0.2f,0.375f,0.5f,0f,tempBullet,0,-bulletSpeed,true,5,false)};
                else if(dir == 0)
                    return new Bullet[]{new Bullet(super.getRect().getX()+super.getWidth()/2-0.3f,super.getRect().getY()+super.getHeight(),0.375f,0.5f,0f,tempBullet,0,bulletSpeed,true,5,false)};
            }
        }
        return null;
    }

    public void respondToCollision(String axis,Rectangle collisionObject ,Rectangle intersections){

        if(axis.equals("x")){
            if(intersections.getX()>collisionObject.getX()) {
                super.getRect().setX(super.getRect().getX() + intersections.getWidth());
            }
            else if(intersections.getX()<collisionObject.getX()+collisionObject.width){
                super.getRect().setX(super.getRect().getX() - intersections.getWidth());
            }
        }
        else if(axis.equals("y")){
            if(intersections.getY()>collisionObject.getY()) {
                super.getRect().setY(super.getRect().getY() + intersections.getHeight());
                setJumpCounter(0);
                super.setCurrentSpeedY(0.0f);
            }
            else if(intersections.getY()<collisionObject.getY()+collisionObject.getHeight()) {
                super.getRect().setY(super.getRect().getY() - intersections.getHeight());
                super.setCurrentSpeedY(0.0f);

            }
        }
        else if(intersections.getWidth()==intersections.getHeight()){
        }
    }

    public void frameUp(){
        lastInstanceDamage++;
        lastShot++;
    }

    public int getJumpCounter() {
        return jumpCounter;
    }

    public void setJumpCounter(int jumpCounter) {
        this.jumpCounter = jumpCounter;
    }


    public int getiFrames() {
        return iFrames;
    }

    public void setiFrames(int iFrames) {
        this.iFrames = iFrames;
    }

    public int getLastInstanceDamage() {
        return lastInstanceDamage;
    }

    public void setLastInstanceDamage(int lastInstanceDamage) {
        this.lastInstanceDamage = lastInstanceDamage;
    }

    public int getShotFrames() {
        return shotFrames;
    }

    public void setShotFrames(int shotFrames) {
        this.shotFrames = shotFrames;
    }

    public int getLastShot() {
        return lastShot;
    }

    public void setLastShot(int lastShot) {
        this.lastShot = lastShot;
    }

    public String getLastDir() {
        return lastDir;
    }

    public void setLastDir(String lastDir) {
        this.lastDir = lastDir;
    }

    public float getBulletSpeed() {
        return bulletSpeed;
    }

    public void setBulletSpeed(float bulletSpeed) {
        this.bulletSpeed = bulletSpeed;
    }
}
