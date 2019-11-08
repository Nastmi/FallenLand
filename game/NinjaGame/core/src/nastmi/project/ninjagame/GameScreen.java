package nastmi.project.ninjagame;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.BodyDef.*;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import nastmi.project.utilities.CollisionBuilder;
import nastmi.project.utilities.ObjectLayerRenderer;

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
    BodyDef bodyDef;
    Body body;


    public GameScreen(final MainGame game){
        world = new World(new Vector2(0, -10),true);
        this.game=game;
        camera = new OrthographicCamera();
        viewport = new FitViewport(16,9,camera);
        viewport.apply();
        camera.position.set(viewport.getWorldWidth()/2+10.6f ,viewport.getWorldHeight()/2,0);
        Gdx.input.setInputProcessor(this);
        map = new TmxMapLoader().load("tiledMaps/maps/grassland_1_1.tmx");
        renderer = new ObjectLayerRenderer(map,1/48f);
        prop = map.getProperties();
        GAME_WORLD_WIDTH = prop.get("width",Integer.class);
        GAME_WORLD_HEIGHT = prop.get("height",Integer.class);
        CollisionBuilder.objectLayerToBox2D(map,world,1/48f);
        bodyDef = new BodyDef();
        bodyDef.type = BodyType.DynamicBody;
        bodyDef.position.set(camera.position.x,camera.position.y);
        body = world.createBody(bodyDef);
        PolygonShape poly = new PolygonShape();
        poly.setAsBox(0.5f,0.5f);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = poly;
        //fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 1f;
        Fixture fixture = body.createFixture(fixtureDef);


    }

    @Override
    public void render(float delta) {
        camera.update();
        renderer.setView(camera);
        renderer.render();
        game.batch.begin();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.end();
        if(rightPressed) {
            if(camera.position.x < GAME_WORLD_WIDTH-8)
                camera.translate(0.05f, 0);
        }
        if(leftPressed) {
            if(camera.position.x > 8)
                camera.translate(-0.05f, 0);
        }
        if(upPressed) {
            if(camera.position.y < GAME_WORLD_HEIGHT-4.5)
                camera.translate(0, 0.05f);
        }
        if(downPressed) {
            if(camera.position.y > 4.5)
                camera.translate(0, -0.05f);
        }
        debugRenderer.render(world, camera.combined);
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
