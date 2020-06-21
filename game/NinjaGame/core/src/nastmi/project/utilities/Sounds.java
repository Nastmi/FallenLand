package nastmi.project.utilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;


public class Sounds {
    public static Sound enemyDeath = Gdx.audio.newSound(Gdx.files.internal("sound/enemyDeath.wav"));
    public static Sound enemyFire = Gdx.audio.newSound(Gdx.files.internal("sound/enemyFire.wav"));
    public static Sound enemyDamage = Gdx.audio.newSound(Gdx.files.internal("sound/enemyDamage.wav"));
    public static Sound enemyClank = Gdx.audio.newSound(Gdx.files.internal("sound/enemyClank.wav"));
    public static Sound playerFire = Gdx.audio.newSound(Gdx.files.internal("sound/playerFire.wav"));
    public static Sound playerDamage = Gdx.audio.newSound(Gdx.files.internal("sound/playerDamage.wav"));
    public static Sound playerDeath = Gdx.audio.newSound(Gdx.files.internal("sound/playerDeath.wav"));
    public static Sound playerJump = Gdx.audio.newSound(Gdx.files.internal("sound/playerJump.wav"));
    public static Music menuTheme = Gdx.audio.newMusic(Gdx.files.internal("music/menuMusic.mp3"));
    public static Music levelTheme = Gdx.audio.newMusic(Gdx.files.internal("music/levelMusic.mp3"));
    public static Music victoryTheme = Gdx.audio.newMusic(Gdx.files.internal("music/victoryTheme.mp3"));



}


