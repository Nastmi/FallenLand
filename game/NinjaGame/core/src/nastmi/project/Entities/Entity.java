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

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }
}
