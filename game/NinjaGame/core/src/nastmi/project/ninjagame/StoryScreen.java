package nastmi.project.ninjagame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
import nastmi.project.utilities.Sounds;

public class StoryScreen implements Screen, InputProcessor {

    final MainGame game;
    Viewport viewport;
    OrthographicCamera camera;
    Rectangle playButton;
    Rectangle optionButton;
    Rectangle infoButton;
    Texture menu;
    private int curStory;

    public StoryScreen(final MainGame game, int story){
        this.game = game;
        curStory = story;
        Gdx.input.setInputProcessor(this);
        camera = new OrthographicCamera();
        viewport = new FitViewport(160,90,camera);
        viewport.apply();
        camera.position.set(viewport.getWorldWidth()/2 ,viewport.getWorldHeight()/2,0);
        menu = new Texture(Gdx.files.internal("menus/story1.png"));

    }

    @Override
    public void render(float delta) {
        camera.update();
        game.batch.begin();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.draw(menu,0,0,160,90);
        game.batch.end();
        menu = new Texture(Gdx.files.internal("menus/story"+curStory+".png"));
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
        if(keycode == Input.Keys.SPACE){
            if(curStory == 4){
                game.setScreen(new Tutorial(game));
                dispose();
            }
            else if(curStory == 5){
                game.setScreen(new MenuScreen(game));
            }
            else{
                curStory++;
            }

        }
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
