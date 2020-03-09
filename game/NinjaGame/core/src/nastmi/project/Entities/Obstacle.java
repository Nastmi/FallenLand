package nastmi.project.Entities;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class Obstacle extends Entity{

    private int damage;

    public Obstacle(){

    }

    public Obstacle(int x, int y, float width, float height, float speed, Sprite startSprite, int damage) {
        super(x, y, width, height, speed,startSprite);
        this.damage = damage;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }


}
