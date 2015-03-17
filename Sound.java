import javax.sound.sampled.*;
import java.io.*;


public class Sound{
	// String mainLoop;
	AudioInputStream audioIn;

	Clip mainClip, footstepClip, victoryClip, swordClashClip, whooshClip, defeatClip;


	Sound(){
		try {
		audioIn = AudioSystem.getAudioInputStream(new File("sounds/Arborea.wav").getAbsoluteFile());
		mainClip = AudioSystem.getClip();
		mainClip.open(audioIn);

		audioIn = AudioSystem.getAudioInputStream(new File("sounds/footstep.wav").getAbsoluteFile());
		footstepClip = AudioSystem.getClip();
		footstepClip.open(audioIn);

		audioIn = AudioSystem.getAudioInputStream(new File("sounds/Victory_Theme.wav").getAbsoluteFile());
		victoryClip = AudioSystem.getClip();
		victoryClip.open(audioIn);

		audioIn = AudioSystem.getAudioInputStream(new File("sounds/SwordClash.wav").getAbsoluteFile());
		swordClashClip = AudioSystem.getClip();
		swordClashClip.open(audioIn);
		
		audioIn = AudioSystem.getAudioInputStream(new File("sounds/whoosh.wav").getAbsoluteFile());
		whooshClip = AudioSystem.getClip();
		whooshClip.open(audioIn);

		audioIn = AudioSystem.getAudioInputStream(new File("sounds/defeat_theme.wav").getAbsoluteFile());
		defeatClip = AudioSystem.getClip();
		defeatClip.open(audioIn);

		playClip(mainClip);
	    } catch(Exception ex) {
	        System.out.println("Error with playing sound.");
	        ex.printStackTrace();
	    }
	}

	// public void playSound(String filename) {
	    
	//     try {
	//         AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(filename).getAbsoluteFile());
	//         Clip clip = AudioSystem.getClip();
	//         clip.open(audioInputStream);
	//         clip.start();
	//     } catch(Exception ex) {
	//         System.out.println("Error with playing sound.");
	//         ex.printStackTrace();
	//     }
	// }

	public void playClip(Clip clip){
	    try {
	        clip.start();
	    } catch(Exception ex) {
	        System.out.println("Error with playing sound.");
	        ex.printStackTrace();
	    }
	}

	public void stopClip(Clip clip){
	    try {
	        clip.stop();
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