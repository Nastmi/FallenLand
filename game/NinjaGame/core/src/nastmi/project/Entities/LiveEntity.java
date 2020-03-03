package nastmi.project.Entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import javax.xml.soap.Text;

public class LiveEntity extends Entity {

    int health;
    private boolean isDead = false;
    private Texture spritSheet;

    public LiveEntity(int x, int y, float width, float height, float speed, int health, Sprite startSprite) {
        super(x, y, width, height, speed, startSprite);
        this.health = health;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public boolean isDead() {
        return isDead;
    }

    public void setDead(boolean dead) {
        isDead = dead;
    }

    public void applyDamage(int damage){
        health = health - damage;
        if(health <= 0){
            isDead = true;
        }
    }
}
