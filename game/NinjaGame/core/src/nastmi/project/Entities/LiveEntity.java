package nastmi.project.Entities;

public class LiveEntity extends Entity {

    int health;
    private boolean isDead = false;

    public LiveEntity(int x, int y, float width, float height, float speed, int health) {
        super(x, y, width, height, speed);
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
}
