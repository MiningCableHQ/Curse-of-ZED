package Audio.SFX;

import Audio.Audio;

import javax.sound.sampled.*;
import java.io.File;

public class AudioSFX extends Audio {
    public AudioSFX(String name, String filePath) {
        super(name, filePath);
    }

    public String getFilePath() { return filePath; }

    @Override
    public void play(boolean loop) {
        audioExecutor.submit(() -> {
            try {

                if (hasPlayed && clip != null && clip.isOpen()) {
                    clip.setFramePosition(0);
                    if (loop) clip.loop(Clip.LOOP_CONTINUOUSLY);
                    clip.start();
                    System.out.println("Playing SFX: " + name);
                    isRunning = true;
                    return;
                }

                // If clip is invalid or first time playing, reload
                stopCurrentClip();  // Clean up properly
                clip = AudioSystem.getClip();
                AudioInputStream audioIn = AudioSystem.getAudioInputStream(new File(filePath));
                clip.open(audioIn);
                if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                    volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                    applyVolume();
                }
                this.isLooping = loop;
                this.isRunning = true;
                this.hasPlayed = true;
                if (loop) clip.loop(Clip.LOOP_CONTINUOUSLY);
                clip.start();
                System.out.println("Playing SFX: " + name);
                while (isRunning && clip.isOpen()) Thread.sleep(100);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}