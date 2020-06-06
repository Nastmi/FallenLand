package nastmi.project.ninjagame;

import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import nastmi.project.Entities.Bullet;
import nastmi.project.Entities.Enemy;
import nastmi.project.Entities.Player;
import nastmi.project.utilities.*;

public class GameScreen implements Screen, InputProcessor {
    final MainGame game;
    private static OrthographicCamera  camera;
    static float GAME_WORLD_WIDTH;
    static float GAME_WORLD_HEIGHT;
    static final float gravity = 0.3f;
    static final float maxYSpeed = 10.0f;
    MapProperties prop;
    private Viewport viewport;
    TiledMap map;
    ObjectLayerRenderer renderer;
    static boolean rightPressed = false;
    static boolean leftPressed = false;
    static boolean upPressed = false;
    static boolean downPressed = false;
    static boolean yPressed = false;
    public boolean shooting = false;
    static Player player;
    ShapeRenderer renderShape;
    Array<Rectangle> arrOfCollisions;
    Array<Enemy> arrEnemies;
    Array<Bullet> friendlyBullets;
    Array<Bullet> enemyBullets;
    MapObjects enemyObjects;
    Sound enemyDeath;
    private boolean paused = false;
    Texture pauseMenu;

    public GameScreen(final MainGame game){
        Sounds.levelTheme.play();
        Sounds.levelTheme.setVolume(Globals.musicVolume);
        Sounds.levelTheme.setLooping(true);
        this.game=game;
        arrOfCollisions = new Array<>();
        arrEnemies  = new Array<>();
        friendlyBullets = new Array<>();
        enemyBullets = new Array<>();
        //Create camera, and link it to a viewport, so sizes of textures scale properly.
        camera = new OrthographicCamera();
        viewport = new FitViewport(14.4f,8.1f,camera);
        //viewport = new FitViewport(16.0f,9.0f,camera);
        viewport.apply();
        camera.position.set(viewport.getWorldWidth()/2 ,viewport.getWorldHeight()/2,0);
        Gdx.input.setInputProcessor(this);
        //Load and render map
        map = new TmxMapLoader().load("tiledMaps/maps/level1.tmx");
        renderer = new ObjectLayerRenderer(map,1/32f);
        prop = map.getProperties();
        GAME_WORLD_WIDTH = prop.get("width",Integer.class);
        GAME_WORLD_HEIGHT = prop.get("height",Integer.class);
        //Create a world and build it's collisions.
        CollisionBuilder.objectLayerToBox2D(map,arrOfCollisions,1/32f);
        MapLayer temp = map.getLayers().get("otherLayer");
        MapObjects tempO = temp.getObjects();
        RectangleMapObject o = (RectangleMapObject) tempO.get(0);
        player = new Player(o.getRectangle().getX()*1/32f,o.getRectangle().getY()*1/32f,0.75f,1f,2.5f,20,new Sprite(new Texture("enemy.png")),new Texture(Gdx.files.internal("player/charIdle.png")),2,0.2f);
        renderShape = new ShapeRenderer();
        enemyObjects = EnemySpawner.addEnemies(map);
        pauseMenu = new Texture(Gdx.files.internal("menus/pauseMenu.png"));
    }


    @Override
    public void render(float delta) {
        camera.update();
        float dt = Math.min(Gdx.graphics.getDeltaTime(), 1 / 60f);
        if(!paused){
            changeSpeed();
            cameraFollow();
            player.applyGravity(dt,gravity,maxYSpeed);
            moveCollisionAll(dt);
            player.frameUp();
            player.setDir(rightPressed,leftPressed,upPressed,downPressed);
            player.createAnimation();
            CollisionListener.checkForDamage(friendlyBullets,enemyBullets,arrEnemies,player);
            removeDeadEnemies();
            EnemySpawner.spawnEnemies(enemyObjects,arrEnemies,player,1/32f);
            if(player.isDead()){
                Sounds.playerDeath.play(Globals.soundVolume);
                game.setScreen(new GameScreen(game));
                dispose();
            }
        }
        renderer.setView(camera);
        renderer.render();
        game.batch.begin();
        game.batch.setProjectionMatrix(camera.combined);
        drawAll(game.batch);
        shootAll();
        if(paused){
            game.batch.draw(pauseMenu,camera.position.x-2f,camera.position.y-2f,4f,4f);
        }
        game.batch.end();
       // debugRender(arrOfCollisions,renderShape,camera,player.getRect(),test.getRect(),third.getRect());
    }


    private  void shootAll(){
        Bullet[] temp = player.shoot(rightPressed,leftPressed,upPressed,downPressed,shooting);
        if(temp != null)
            friendlyBullets.addAll(temp);
        for(Enemy e:arrEnemies){
            Bullet[] temp2 = e.shoot();
            if(temp2 != null){
                enemyBullets.addAll(temp2);
            }
        }
    }

    private void removeDeadEnemies(){
        for(Enemy e:arrEnemies){
            e.framesUp();
            if (e.getHealth() <= 0){
                arrEnemies.removeValue(e,false);
                Sounds.enemyDeath.play(Globals.soundVolume);
            }
            else if(player.getRect().getX()-e.getRect().getX() > 20){
                arrEnemies.removeValue(e,false);
            }
        }
    }

    private void moveCollisionAll(float dt){
        player.moveX(dt);
        for(Enemy e:arrEnemies){
            e.moveX(dt);
        }
        Array<Bullet> temp = new Array<>(friendlyBullets);
        CollisionListener.checkCollision(player,arrOfCollisions,"x",arrEnemies,friendlyBullets,enemyBullets);
        player.moveY(dt);
        for(Enemy e:arrEnemies){
            e.moveY(dt);
            e.applyGravity(dt,gravity,maxYSpeed);
        }
        for(Bullet b:friendlyBullets){
            b.moveX(dt);
            b.moveY(dt);
        }
        for(Bullet b:enemyBullets){
            b.moveX(dt);
            b.moveY(dt);
        }
        CollisionListener.checkCollision(player,arrOfCollisions,"y",arrEnemies,friendlyBullets,enemyBullets);
    }


    public void drawAll(SpriteBatch batch){
        player.draw(batch);
        for(Enemy e:arrEnemies){
            e.draw(batch);
        }
        for(Bullet b:friendlyBullets){
            b.draw(batch);
        }
        for(Bullet b:enemyBullets){
            b.draw(batch);
        }
    }

    private void changeSpeed(){
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
        /*if(!rightPressed && !leftPressed){
            player.setSpeed("stop");
        }*/
        for(Enemy e:arrEnemies){
            e.calculateNextSpeed();
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
            renderShape.rect(rect.getX(), rect.getY(), rect.width, rect.height);
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

    @Override
    public boolean keyDown(int keycode) {
        if(keycode == Input.Keys.LEFT){
            player.setLastDir("none");
            leftPressed = true;
        }
        if(keycode == Input.Keys.RIGHT){
            player.setLastDir("none");
            rightPressed = true;
        }
        if(keycode == Input.Keys.Y){
            yPressed = true;
            player.setSpeed("up");
        }
        if(keycode == Input.Keys.DOWN){
            downPressed = true;
        }
        if(keycode == Input.Keys.UP){
            upPressed = true;
        }
        if(keycode == Input.Keys.X){
            shooting = true;
        }
        if(keycode == Input.Keys.ESCAPE){
            paused = !paused;
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if(keycode == Input.Keys.LEFT){
            leftPressed = false;
            if(!rightPressed)
                player.setLastDir("left");
            player.setSpeed("stop");
        }
        if(keycode == Input.Keys.RIGHT){
            rightPressed = false;
            if(!leftPressed)
                player.setLastDir("right");
            player.setSpeed("stop");
        }
        if(keycode == Input.Keys.UP){
            upPressed = false;
        }
        if(keycode == Input.Keys.DOWN){
            downPressed = false;
        }
        if(keycode == Input.Keys.X){
            shooting = false;
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
