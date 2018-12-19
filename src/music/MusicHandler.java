package music;

import gamestate.GameStateManager;
import gamestate.StateObserver;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;
import java.io.File;


public class MusicHandler implements StateObserver {
    private static String FIRST_LEVEL_THEME = "resources/Theme/First_Level_Theme.wav";
    private static String SECOND_LEVEL_THEME = "resources/Theme/Second_Level_Theme.wav";


    private Clip clip;
    private AudioInputStream audioInputStream;
    private static MusicHandler instance;





    private MusicHandler() {

        try {

            audioInputStream = AudioSystem.getAudioInputStream(new File(MusicHandler.FIRST_LEVEL_THEME));
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static MusicHandler getInstance(){
        if(instance == null){
            instance = new MusicHandler();
        }
        return instance;
    }


    @Override
    public void updateObserver(GameStateManager.State state) {

        clip.stop();

        try{
        if(state == GameStateManager.State.LEVEL1STATE){

            audioInputStream = AudioSystem.getAudioInputStream(new File(MusicHandler.FIRST_LEVEL_THEME));
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
        else if (state == GameStateManager.State.LEVEL2STATE){
            audioInputStream = AudioSystem.getAudioInputStream(new File(MusicHandler.SECOND_LEVEL_THEME));
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
        }catch (Exception e ){
            e.printStackTrace();
        }


    }
}
