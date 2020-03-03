package nastmi.project.Entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.BodyDef.*;

//Subclass of entity used for the main player.
public class Player extends LiveEntity{
    private int jumpCounter;


    public Player(int x, int y, float width, float height, float speed,int health, Sprite startSprite){
        super(x,y,width,height,speed,health,startSprite);
    }


    public void setSpeed(String direction){
        switch (direction) {
            case "right":
                if(super.getCurrentSpeedX() < 3.0f)
                    super.setCurrentSpeedX(super.getCurrentSpeedX()+super.getStartSpeed());
                break;
            case "left":
                if(super.getCurrentSpeedX() > -3.0f)
                    super.setCurrentSpeedX(super.getCurrentSpeedX()-super.getStartSpeed());
                break;
            case "up":
                if(jumpCounter < 3) {
                    super.setCurrentSpeedY(8);
                    jumpCounter++;
                }
                break;
            case "stop":
                super.setCurrentSpeedX(0);
                break;
        }
    }

    public int getJumpCounter() {
        return jumpCounter;
    }

    public void setJumpCounter(int jumpCounter) {
        this.jumpCounter = jumpCounter;
    }


}
