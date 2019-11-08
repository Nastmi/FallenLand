package nastmi.project.ninjagame.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import nastmi.project.ninjagame.MainGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		//config.width = LwjglApplicationConfiguration.getDesktopDisplayMode().width;
		//config.height = LwjglApplicationConfiguration.getDesktopDisplayMode().height;
		//config.fullscreen = true;
        config.width = 1600;
        config.height = 900;
        //config.fullscreen = true;
		new LwjglApplication(new MainGame(), config);
	}
}
