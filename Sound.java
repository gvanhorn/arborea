import javax.sound.sampled.*;
import java.io.*;


public class Sound{
	// String mainLoop;
	AudioInputStream mainLoop;
	AudioInputStream footstep;
	AudioInputStream swordClash;

	Sound(){
		try {
		mainLoop = AudioSystem.getAudioInputStream(new File("sounds/Arborea.wav").getAbsoluteFile());
		footstep = AudioSystem.getAudioInputStream(new File("sounds/footstep.wav").getAbsoluteFile());
		swordClash = AudioSystem.getAudioInputStream(new File("sounds/SwordClash.wav").getAbsoluteFile());
	    } catch(Exception ex) {
	        System.out.println("Error with playing sound.");
	        ex.printStackTrace();
	    }
	}

	public void playSound(String filename) {
	    
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

	public void playSoundStream(AudioInputStream audio){
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