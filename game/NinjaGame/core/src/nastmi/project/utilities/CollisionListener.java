package nastmi.project.utilities;

import com.badlogic.gdx.physics.box2d.*;
import nastmi.project.ninjagame.GameScreen;


public class CollisionListener implements ContactListener {


    @Override
    public void beginContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();
        a.getBody().setTransform(GameScreen.player.getOldX(),GameScreen.player.getOldY(),a.getBody().getAngle());
        System.out.println(a.getBody()+" collided with "+b.getBody());
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
};
