package nastmi.project.ninjagame;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.BodyDef.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import nastmi.project.Entities.Player;
import nastmi.project.utilities.CollisionBuilder;
import nastmi.project.utilities.CollisionListener;
import nastmi.project.utilities.ObjectLayerRenderer;

import java.util.Arrays;

public class GameScreen implements Screen, InputProcessor {
    final MainGame game;
    private OrthographicCamera  camera;
    float GAME_WORLD_WIDTH;
    float GAME_WORLD_HEIGHT;
    MapProperties prop;
    private Viewport viewport;
    TiledMap map;
    ObjectLayerRenderer renderer;
    boolean rightPressed = false;
    boolean leftPressed = false;
    boolean upPressed = false;
    boolean downPressed = false;
    World world;
    Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();
    Player player;
    Player test;
    CollisionListener Listener;


    public GameScreen(final MainGame game){
        this.game=game;
        Listener = new CollisionListener();
        //Create camera, and link it to a viewport, so sizes of textures scale properly.
        camera = new OrthographicCamera();
        viewport = new FitViewport(16,9,camera);
        viewport.apply();
        camera.position.set(viewport.getWorldWidth()/2 ,viewport.getWorldHeight()/2,0);
        Gdx.input.setInputProcessor(this);
        //Load and render map
        map = new TmxMapLoader().load("tiledMaps/maps/grassland_1_1.tmx");
        renderer = new ObjectLayerRenderer(map,1/48f);
        prop = map.getProperties();
        GAME_WORLD_WIDTH = prop.get("width",Integer.class);
        GAME_WORLD_HEIGHT = prop.get("height",Integer.class);
        //Create a world and build it's collisions.
        world = new World(new Vector2(0, 0),false);
        world.setContactListener(Listener);
        CollisionBuilder.objectLayerToBox2D(map,world,1/48f);
        player = new Player(10,7,new Sprite(new Texture("char.png")),1,1,world,"good",3);
        test = new Player(7,7,new Sprite(new Texture("enemy.png")),1,1,world,"bad",1);
    }


    @Override
    public void render(float delta) {
        camera.update();
        renderer.setView(camera);
        renderer.render();
        debugRenderer.render(world, camera.combined);
        game.batch.begin();
        game.batch.setProjectionMatrix(camera.combined);
        drawPlayer(player,game.batch);
        drawPlayer(test,game.batch);
        game.batch.end();
        float dt = Math.min(Gdx.graphics.getDeltaTime(), 1 / 60f);
        if(rightPressed) {
            player.move("right",dt);
        }
        if(leftPressed) {
            player.move("left",dt);
        }
        if(upPressed){
            player.move("up",dt);
        }
        if(downPressed){
            player.move("down",dt);
        }
        player.updatePosition();
        if(world.getContactCount()>0){
            Array<Contact> arr = world.getContactList();
            for(Contact contact:arr){
                Fixture a = contact.getFixtureA();
                Fixture b = contact.getFixtureB();
                WorldManifold manifold = contact.getWorldManifold();
                Vector2[] points = manifold.getPoints();
                if(a.getBody().getUserData() instanceof Player){
                    Player plr = (Player)a.getBody().getUserData();
                    plr.setOldPosition();
                    plr.setX(plr.getOldX());
                    plr.setY(plr.getOldY());
                }
                else if(b.getBody().getUserData() instanceof Player){
                    Player plr = (Player)b.getBody().getUserData();
                    plr.setOldPosition();
                }
            }
        }
        world.step(1/60f, 6, 2);
    }


    @Override
    public void show() {

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width,height);
        //camera.position.set(viewport.getWorldWidth()/2,viewport.getWorldHeight()/2,0);

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    public static void drawPlayer(Player playerToDraw, Batch batch){
        batch.draw(playerToDraw.getSprite().getTexture(),playerToDraw.getX(),playerToDraw.getY(),playerToDraw.getWidth(),playerToDraw.getHeight());
    }

    @Override
    public boolean keyDown(int keycode) {
        if(keycode == Input.Keys.LEFT){
            leftPressed = true;
        }
        if(keycode == Input.Keys.RIGHT){
            rightPressed = true;
        }
        if(keycode == Input.Keys.UP){
            upPressed = true;
        }
        if(keycode == Input.Keys.DOWN){
            downPressed = true;
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if(keycode == Input.Keys.LEFT){
            leftPressed = false;
        }
        if(keycode == Input.Keys.RIGHT){
            rightPressed = false;
        }
        if(keycode == Input.Keys.UP){
            upPressed = false;
        }
        if(keycode == Input.Keys.DOWN){
            downPressed = false;
        }
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
