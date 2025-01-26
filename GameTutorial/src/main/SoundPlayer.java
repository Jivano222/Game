package main;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;


/**
 * 
 */
public class SoundPlayer {
	public Clip sword_slash;
	public Clip door_unlock;
	
	public SoundPlayer() {
		loadSound();
	}
	
	public void loadSound() {
        try {
            // Load the sound file as a resource
        	File soundFile = new File("res/sounds/sword_sound_edited.wav");
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);
            sword_slash = AudioSystem.getClip();
            sword_slash.open(audioStream);
            
//            audioStream = AudioSystem.getAudioInputStream(getClass().getResource("/sounds/SFX2.wav"));
//            sound2 = AudioSystem.getClip();
//            sound2.open(audioStream);
//            
//            audioStream = AudioSystem.getAudioInputStream(getClass().getResource("/sounds/SFX3.wav"));
//            sound3 = AudioSystem.getClip();
//            sound3.open(audioStream);

            // Store the clip in the map
            
        } catch (Exception e) {
            System.err.println("Error loading sound: ");
            e.printStackTrace();
        }
    }
	
		
		
	
	
	
	/**
	 * Takes a preloaded audio clip and plays it
	 * @param sound clip to be played
	 */
	 public static void playSound(Clip sound) {
		 System.out.println("Sound played");
		    if (sound != null) {
		        if (sound.isRunning()) {
		            sound.stop(); // Stop if already playing
		        }
		        sound.setFramePosition(0); // Reset to the start
		        sound.start(); // Play the sound
		    }
		}

  
}
