package nastmi.project.utilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.utils.Array;

import java.awt.*;


public class Globals {

    public static float soundVolume = 0.8f;
    public static float musicVolume = 0.4f;
    public static String resolution = "900p";
    public static boolean fullscreen = false;
    public static int currentLevel;



    public  static void resDown(){
        switch (resolution){
            case "1080p":
                resolution = "900p";
                break;
            case "900p":
                resolution = "810p";
                break;
            case "810p":
                resolution = "720p";
                break;
        }
        setResolution();
    }

    public static void resUp(){
        if(!resolution.equals("1080p")){
            switch (resolution){
                case "900p":
                    resolution = "1080p";
                    break;
                case "810p":
                    resolution = "900p";
                    break;
                case "720p":
                    resolution = "810p";
                    break;
            }
            setResolution();
        }
    }

    public static void changeFullscreen(){
        fullscreen = !fullscreen;
        setResolution();
    }

    public static void setResolution(){
        if(fullscreen){
           /* Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            Gdx.graphics.setWindowedMode(screenSize.width,screenSize.height);*/
            Graphics.DisplayMode currentMode = Gdx.graphics.getDisplayMode();
            Gdx.graphics.setFullscreenMode(currentMode);
        }
        else{
            if(resolution.equals("1080p"))
                Gdx.graphics.setWindowedMode(1920,1080);
            else if(resolution.equals("900p"))
                Gdx.graphics.setWindowedMode(1600,900);
            else if(resolution.equals("810p"))
                Gdx.graphics.setWindowedMode(1440,810);
            else if(resolution.equals("720p"))
                Gdx.graphics.setWindowedMode(1280,720);
            else if(resolution.equals("600p"))
                Gdx.graphics.setWindowedMode(1200,600);
        }




    }

}
