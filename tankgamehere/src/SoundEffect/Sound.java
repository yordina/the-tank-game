package SoundEffect;

import java.applet.Applet;
import java.applet.AudioClip;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import Tank.TankWorld;



public class Sound {
	URL soundFile;
	public static void player (String file, boolean isLoop){
		try{
			URL link = TankWorld.getTankGame().getClass().getClassLoader().getResource(file);
			//URL link = null; //TODO add the resource
			AudioClip clip = Applet.newAudioClip(link);
			if(isLoop){
		        AudioInputStream inputStream = AudioSystem.getAudioInputStream(link);
				Clip clipLoop = AudioSystem.getClip();
		        clipLoop.open(inputStream);
		        clipLoop.loop(Clip.LOOP_CONTINUOUSLY);
			}else{clip.play();}
			
		}catch(Exception e){
			System.err.print(e+"ad sound class");
		}
	}

}
