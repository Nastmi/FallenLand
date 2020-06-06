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
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import nastmi.project.utilities.Globals;
import nastmi.project.utilities.Sounds;

public class OptionScreen implements Screen, InputProcessor {

    final MainGame game;
    private Viewport viewport;
    private OrthographicCamera camera;
    private ShapeRenderer renderer;
    private Texture menu;
    private Rectangle backButton;
    private Texture vfx;
    private Texture sfx;
    private Texture fullscreen;
    private Texture resolution;
    private Rectangle resolutionRec;
    private Rectangle vfxRec;
    private Rectangle sfxRec;
    private Rectangle fullScreenCheck;


    public OptionScreen(final MainGame game){
        this.game = game;
        Gdx.input.setInputProcessor(this);
        camera = new OrthographicCamera();
        viewport = new FitViewport(144,81,camera);
        viewport.apply();
        camera.position.set(viewport.getWorldWidth()/2 ,viewport.getWorldHeight()/2,0);
        renderer = new ShapeRenderer();
        menu = new Texture(Gdx.files.internal("menus/options.png"));
        backButton = new Rectangle(26,63,14,12);
        resolutionRec = new Rectangle(38,49,64,12);
        sfxRec = new Rectangle(38,32,64,12);
        vfxRec = new Rectangle(38,15,64,12);
        fullScreenCheck = new Rectangle(44,3,10,11);
        vfx = new Texture(Gdx.files.internal("buttonValues/volume"+Globals.musicVolume+".png"));
        sfx = new Texture(Gdx.files.internal("buttonValues/volume"+Globals.soundVolume+".png"));
        fullscreen = new Texture(Gdx.files.internal("buttonValues/x.png"));
    }

    @Override
    public void render(float delta) {
        camera.update();
        game.batch.begin();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.draw(menu,0,0,144,81);
        vfx = new Texture(Gdx.files.internal("buttonValues/volume"+Globals.musicVolume+".png"));
        sfx = new Texture(Gdx.files.internal("buttonValues/volume"+Globals.soundVolume+".png"));
        resolution = new Texture(Gdx.files.internal("buttonValues/"+Globals.resolution+".png"));
        game.batch.draw(sfx,sfxRec.getX()+26.5f,sfxRec.getY()+3,11,6);
        game.batch.draw(vfx,vfxRec.getX()+26.5f,vfxRec.getY()+3,11,6);
        game.batch.draw(resolution,resolutionRec.getX()+10.0f,resolutionRec.getY()+3.0f,44,6);
        if(Globals.fullscreen)
            game.batch.draw(fullscreen,46,5,6,7);
        game.batch.end();
        //testRender(fullScreenCheck,resolutionRec,vfxRec,sfxRec,backButton);
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
        viewport.unproject(mousePos);
        System.out.println(fullScreenCheck.getX()+" "+mousePos.x);
        if(backButton.contains(mousePos.x,mousePos.y)){
            game.setScreen(new MenuScreen(game));
            dispose();
        }
        if(resolutionRec.contains(mousePos.x,mousePos.y)){
            if(mousePos.x < resolutionRec.getX()+26.5){
                Globals.resDown();
                backButton = new Rectangle(26,63,14,12);
                resolutionRec = new Rectangle(38,49,64,12);
                sfxRec = new Rectangle(38,32,64,12);
                vfxRec = new Rectangle(38,15,64,12);
                fullScreenCheck = new Rectangle(44,3,10,11);
            }
            else if(mousePos.x > resolutionRec.getX()+26.5){
                Globals.resUp();
                backButton = new Rectangle(26,63,14,12);
                resolutionRec = new Rectangle(38,49,64,12);
                sfxRec = new Rectangle(38,32,64,12);
                vfxRec = new Rectangle(38,15,64,12);
                fullScreenCheck = new Rectangle(44,3,10,11);
            }
        }
        if(vfxRec.contains(mousePos.x,mousePos.y)){
            if(mousePos.x < vfxRec.getX()+26.5 && Globals.musicVolume > 0.0f){
                Globals.musicVolume-=0.1;
            }
            else if(mousePos.x > vfxRec.getX()+26.5 && Globals.musicVolume < 1.0f){
                Globals.musicVolume+=0.1;
            }
            double scale = Math.pow(10,2);
            Globals.musicVolume = (float) (Math.round(Globals.musicVolume*scale)/scale);
            Sounds.menuTheme.setVolume(Globals.musicVolume);
            Sounds.levelTheme.setVolume(Globals.soundVolume);
        }
        if(sfxRec.contains(mousePos.x,mousePos.y)){
            if(mousePos.x < sfxRec.getX()+26.5 && Globals.soundVolume > 0.0f){
                Globals.soundVolume-=0.1;
            }
            else if(mousePos.x > sfxRec.getX()+26.5 && Globals.soundVolume < 1.0f){
                Globals.soundVolume+=0.1;
            }
            double scale = Math.pow(10,2);
            Globals.soundVolume = (float) (Math.round(Globals.soundVolume*scale)/scale);
        }
        if(fullScreenCheck.contains(mousePos.x,mousePos.y)){
            Globals.changeFullscreen();
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

