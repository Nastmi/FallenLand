package nastmi.project.ninjagame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import nastmi.project.utilities.Globals;

public class MainGame extends Game {
	SpriteBatch batch;
	Texture img;
	
	@Override
	public void create () {
        batch = new SpriteBatch();
        //Globals.setResolution();
        this.setScreen(new MenuScreen(this));
	}

	@Override
	public void render () {
        Gdx.gl.glClearColor(0, 0, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		super.render();

	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
