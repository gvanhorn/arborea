import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javax.sound.sampled.*;
import java.io.*;


public class Sound{
	// String mainLoop;
	AudioInputStream mainLoop;
	AudioInputStream footstep;

	Sound(){
		try {
		mainLoop = AudioSystem.getAudioInputStream(new File("sounds/Arborea.wav").getAbsoluteFile());
		footstep = AudioSystem.getAudioInputStream(new File("sounds/footstep.wav").getAbsoluteFile());
	    } catch(Exception ex) {
	        System.out.println("Error with playing sound.");
	        ex.printStackTrace();
	    }
	}

	public static void playSound(String filename) {
	    
	    try {
	        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(filename).getAbsoluteFile());
	        Clip clip = AudioSystem.getClip();
	        clip.open(audioInputStream);
	        clip.start();
	    } catch(Exception ex) {
	        System.out.println("Error with playing sound.");
	        ex.printStackTrace();
	    }
	}

	public static void playSoundStream(AudioInputStream audio){
	    try {
	        Clip clip = AudioSystem.getClip();
	        clip.open(audio);
	        clip.start();
	    } catch(Exception ex) {
	        System.out.println("Error with playing sound.");
	        ex.printStackTrace();
	    }

	}
}