package nastmi.project.ninjagame;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.BodyDef.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import nastmi.project.Entities.Obstacle;
import nastmi.project.Entities.Player;
import nastmi.project.utilities.CollisionBuilder;
import nastmi.project.utilities.CollisionListener;
import nastmi.project.utilities.ObjectLayerRenderer;

import java.util.Arrays;

public class GameScreen implements Screen, InputProcessor {
    final MainGame game;
    private static OrthographicCamera  camera;
    static float GAME_WORLD_WIDTH;
    static float GAME_WORLD_HEIGHT;
    MapProperties prop;
    private Viewport viewport;
    TiledMap map;
    ObjectLayerRenderer renderer;
    static boolean rightPressed = false;
    static boolean leftPressed = false;
    static boolean upPressed = false;
    static boolean downPressed = false;
    static Player player;
    Player test;
    Player third;
    Obstacle spike;
    CollisionListener Listener;
    ShapeRenderer renderShape;
    Array<Rectangle> arrOfCollisions;
    Array<Object> sourcesOfDamage;

    public GameScreen(final MainGame game){
        this.game=game;
        arrOfCollisions = new Array<>();
        sourcesOfDamage = new Array<>();
        //Create camera, and link it to a viewport, so sizes of textures scale properly.
        camera = new OrthographicCamera();
        viewport = new FitViewport(14.4f,8.1f,camera);
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
        CollisionBuilder.objectLayerToBox2D(map,arrOfCollisions,1/48f);
        player = new Player(10,7,0.5f,1,3,20,new Sprite(new Texture("charIdle.png")));
        test = new Player(7,5,2,2,0,1,new Sprite(new Texture("enemy.png")));
        third = new Player(7,13,2,2,0,1,new Sprite(new Texture("enemy.png")));
        spike = new Obstacle(5,5,0.5f,0.5f,0,new Sprite(new Texture("obstacle.gif")),2);
        sourcesOfDamage.add(spike);
        renderShape = new ShapeRenderer();
    }


    @Override
    public void render(float delta) {
        camera.update();
        changePlayerSpeed();
        cameraFollow();
        float dt = Math.min(Gdx.graphics.getDeltaTime(), 1 / 60f);
        player.applyGravity(dt);
        player.moveX(dt);
        CollisionListener.checkCollision(player,arrOfCollisions,"x",test,third);
        player.moveY(dt);
        CollisionListener.checkCollision(player,arrOfCollisions,"y",test,third);
        player.frameUp();
        CollisionListener.checkForDamage(sourcesOfDamage,player);
        renderer.setView(camera);
        renderer.render();
        game.batch.begin();
        game.batch.setProjectionMatrix(camera.combined);
        drawPlayer(player,game.batch);
        drawPlayer(test,game.batch);
        drawObstacle(spike,game.batch);
        game.batch.end();
        debugRender(arrOfCollisions,renderShape,camera,player.getRect(),test.getRect(),third.getRect());
    }

    private static void changePlayerSpeed(){
        if(rightPressed) {
            player.setSpeed("right");
        }
        if(leftPressed) {
            player.setSpeed("left");
        }
        if(upPressed){
            //player.setSpeed("up");
        }
        if(downPressed) {
            player.setSpeed("down");
        }
        if(!rightPressed && !leftPressed){
            player.setSpeed("stop");
        }
    }
    private static void cameraFollow(){
        if(player.getRect().getX()<camera.viewportWidth/2 || player.getRect().getX()>GAME_WORLD_WIDTH-camera.viewportWidth/2){}
        else
            camera.position.x = player.getRect().getX();
        if(player.getRect().getY()<camera.viewportHeight/2 || player.getRect().getY()>GAME_WORLD_HEIGHT-camera.viewportHeight/2){}
        else
            camera.position.y = player.getRect().getY();
    }
    private static void debugRender(Array<Rectangle> arr, ShapeRenderer renderShape, Camera camera, Rectangle... r){
        for(Rectangle rect:arr){
            renderShape.setProjectionMatrix(camera.combined);
            renderShape.begin(ShapeRenderer.ShapeType.Line);
            renderShape.setColor(Color.BLUE);
            renderShape.rect(rect.x, rect.y, rect.width, rect.height);
            renderShape.end();
        }
        for(Rectangle rect:r){
            renderShape.setProjectionMatrix(camera.combined);
            renderShape.begin(ShapeRenderer.ShapeType.Line);
            renderShape.setColor(Color.BLUE);
            renderShape.rect(rect.x, rect.y, rect.width, rect.height);
            renderShape.end();
        }
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
        batch.draw(playerToDraw.getSprite().getTexture(),playerToDraw.getRect().getX(),playerToDraw.getRect().getY(),playerToDraw.getWidth(),playerToDraw.getHeight());
    }

    public static void drawObstacle(Obstacle o, Batch batch){
        batch.draw(o.getSprite().getTexture(),o.getRect().getX(),o.getRect().getY(),o.getWidth(),o.getHeight());
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
            player.setSpeed("up");
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
            player.setSpeed("stop");
        }
        if(keycode == Input.Keys.RIGHT){
            rightPressed = false;
            player.setSpeed("stop");
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
