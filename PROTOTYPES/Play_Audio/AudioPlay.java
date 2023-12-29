


import javax.sound.sampled.*;
import java.io.*;
import java.util.Scanner;
 

public class AudioPlay {
    Clip clip;

    public synchronized void playSound() {
        //new Thread(new Runnable() {
            // The wrapper thread is unnecessary, unless it blocks on the
            // Clip finishing; see comments.
            //public void run() {
                try {
                    clip = AudioSystem.getClip();
                    //AudioInputStream inputStream = AudioSystem.getAudioInputStream(AudioPlay.class.getResourceAsStream("./" + url));
                    AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File("./Input.wav"));
                    clip.open(inputStream);
                    clip.start();
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            //}
        //}).start();
    }


    public static void main(String[] args) {
        final AudioPlay player = new AudioPlay();
        player.playSound();
        System.out.print("Press Enter to Stop playing...");
        (new Scanner(System.in)).nextLine();
        //player.clip.drain();
        //player.clip.close();
    }
}
