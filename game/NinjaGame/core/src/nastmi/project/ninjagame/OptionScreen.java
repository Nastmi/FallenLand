package nastmi.project.ninjagame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import nastmi.project.utilities.Globals;

public class OptionScreen implements Screen, InputProcessor {

    final MainGame game;
    private Viewport viewport;
    private OrthographicCamera camera;
    private ShapeRenderer renderer;
    private Texture menu;
    private Rectangle backButton;
    private Texture vfx;
    private Texture sfx;
    private Texture resolution;


    public OptionScreen(final MainGame game){
        this.game = game;
        Gdx.input.setInputProcessor(this);
        camera = new OrthographicCamera();
        viewport = new FitViewport(144,81,camera);
        viewport.apply();
        camera.position.set(viewport.getWorldWidth()/2 ,viewport.getWorldHeight()/2,0);
        renderer = new ShapeRenderer();
        menu = new Texture(Gdx.files.internal("menus/options.png"));
        backButton = new Rectangle(58,4,23,10);
    }

    @Override
    public void render(float delta) {
        camera.update();
        game.batch.begin();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.draw(menu,0,0,144,81);
        game.batch.end();
        //testRender(backButton);
    }

    public void setValues(){

    }

    public void testRender(Rectangle ... arrRect){
        for(Rectangle r:arrRect){
            renderer.setProjectionMatrix(camera.combined);
            renderer.begin(ShapeRenderer.ShapeType.Line);
            renderer.setColor(Color.BLUE);
            renderer.rect(r.getX(),r.getY(),r.getWidth(),r.getHeight());
            renderer.end();
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width,height);
    }

    @Override
    public void show() {

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
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Vector3 mousePos = new Vector3(screenX,screenY,0);
        camera.unproject(mousePos);
        if(backButton.contains(mousePos.x,mousePos.y)){
            game.setScreen(new MenuScreen(game));
            dispose();
        }
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

