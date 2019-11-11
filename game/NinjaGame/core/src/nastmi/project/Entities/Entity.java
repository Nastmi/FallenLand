package nastmi.project.Entities;
//Base class for entities. Should never actually be used on it's own, use subclasses instead, depending on the type of entity.
public class Entity {
    float x;
    float y;
    float width;
    float height;
    float speed;

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public float getSpeed() {
        return speed;
    }
}
