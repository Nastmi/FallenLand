package nastmi.project.Entities;

public class Obstacle extends Entity{

    private int damage;

    public Obstacle(int x, int y, float width, float height, float speed, int damage) {
        super(x, y, width, height, speed);
        this.damage = damage;
    }
}
