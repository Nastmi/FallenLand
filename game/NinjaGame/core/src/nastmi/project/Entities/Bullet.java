package nastmi.project.Entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Bullet extends Entity{

    private boolean destroyOnColision;
    private int damage;
    private boolean flip;


    public Bullet(float x, float y, float width, float height, float speed, Sprite startSprite,float speedX, float speedY, boolean destroy, int damage, boolean flip) {
        super(x, y, width, height, speed,startSprite);
        super.setCurrentSpeedX(speedX);
        super.setCurrentSpeedY(speedY);
        destroyOnColision = destroy;
        this.damage = damage;
        if(flip){
            super.getSprite().flip(true,false);
        }
    }

    @Override
    public void draw(SpriteBatch batch){
        batch.draw(this.getSprite(),this.getRect().getX(),this.getRect().getY(),this.getWidth(),this.getHeight());
    }

    public boolean isDestroyOnColision() {
        return destroyOnColision;
    }

    public void setDestroyOnColision(boolean destroyOnColision) {
        this.destroyOnColision = destroyOnColision;
    }

    public int getDamage(){
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }
}
