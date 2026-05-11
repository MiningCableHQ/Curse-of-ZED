package Audio.SFX;

import Audio.Audio;

import javax.sound.sampled.*;
import java.io.File;

public class AudioSFX extends Audio {

    private FloatControl volumeControl;
    private boolean isRunning = false;

    public AudioSFX(String name, String filePath) {
        super(name, filePath);
    }

    @Override
    public void play() {
        play(false);
    }

    @Override
    public void play(boolean loop) {
        // Submit to background thread so it doesn't block
        audioExecutor.submit(() -> {
            try {
                stopCurrentClip();

                clip = AudioSystem.getClip();
                AudioInputStream audioIn = AudioSystem.getAudioInputStream(new File(filePath));
                clip.open(audioIn);

                // Get volume control (optional)
                if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                    volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                }

                this.isRunning = true;

                clip.start();

                System.out.println("Playing: " + name);

                // Wait for clip to finish (non-blocking for the main app)
                while (isRunning && clip.isOpen()) {
                    Thread.sleep(100);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void stop() {
        isRunning = false;
        stopCurrentClip();
    }

    private void stopCurrentClip() {
        try {
            if (clip != null) {
                if (clip.isRunning()) {
                    clip.stop();
                }
                clip.close();
                System.out.println("Stopped: " + name);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setVolume(float volume) { // 0.0 to 1.0
        if (volumeControl != null) {
            float min = volumeControl.getMinimum();
            float max = volumeControl.getMaximum();
            float gain = min + (max - min) * volume;
            volumeControl.setValue(gain);
        }
    }

    @Override
    public boolean isPlaying() {
        return isRunning && clip != null && clip.isRunning();
    }

    @Override
    public void shutdown() {
        stop();
        audioExecutor.shutdown();
    }
}