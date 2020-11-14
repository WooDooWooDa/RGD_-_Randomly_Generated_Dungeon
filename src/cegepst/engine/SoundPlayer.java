package cegepst.engine;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class SoundPlayer {

    public static synchronized void play(final String fileName) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Clip clip = AudioSystem.getClip();
                    AudioInputStream inputStream = AudioSystem.getAudioInputStream(this.getClass().getClassLoader().getResourceAsStream(fileName));
                    clip.open(inputStream);
                    clip.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static synchronized void playLoop(String fileName) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Clip clip = AudioSystem.getClip();
                    AudioInputStream inputStream = AudioSystem.getAudioInputStream(this.getClass().getClassLoader().getResourceAsStream(fileName));
                    clip.open(inputStream);
                    clip.loop(Clip.LOOP_CONTINUOUSLY);
                    clip.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
