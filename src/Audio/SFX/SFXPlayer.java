package Audio.SFX;

import javax.sound.sampled.*;
import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SFXPlayer {

    private float volume = 0.7f;
    private Clip currentClip;

    // Separate executor so SFX are never blocked by the music's while-loop
    private final ExecutorService sfxExecutor = Executors.newCachedThreadPool();

    // Same dB mapping as Audio.java
    private static final float[] SLIDER_VALUES = {0.0f, 0.5f, 0.75f, 1.0f};
    private static final float[] DB_TARGETS    = {-80f, -10f,  -3f,   6f};

    public void playSFX(AudioSFX sfx) {
        System.out.println("[SFXPlayer] Playing: " + sfx.getClass().getSimpleName());
        sfxExecutor.submit(() -> {
            try {
                synchronized (this) {
                    if (currentClip != null && currentClip.isRunning()) {
                        currentClip.stop();
                        currentClip.close();
                    }
                }

                Clip clip = AudioSystem.getClip();
                AudioInputStream audioIn = AudioSystem.getAudioInputStream(
                        new File(sfx.getFilePath()));
                clip.open(audioIn);

                if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                    FloatControl vc = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                    vc.setValue(interpolateDB(volume));
                }

                synchronized (this) {
                    currentClip = clip;
                }

                clip.start();
                clip.addLineListener(event -> {
                    if (event.getType() == LineEvent.Type.STOP) {
                        clip.close();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void setVolume(float volume) {
        this.volume = Math.max(0f, Math.min(1f, volume));
    }

    public void stopAll() {
        sfxExecutor.submit(() -> {
            synchronized (this) {
                if (currentClip != null && currentClip.isRunning()) {
                    currentClip.stop();
                    currentClip.close();
                    currentClip = null;
                }
            }
        });
    }

    private float interpolateDB(float slider) {
        if (slider <= SLIDER_VALUES[0]) return DB_TARGETS[0];
        if (slider >= SLIDER_VALUES[SLIDER_VALUES.length - 1]) return DB_TARGETS[DB_TARGETS.length - 1];
        for (int i = 0; i < SLIDER_VALUES.length - 1; i++) {
            if (slider >= SLIDER_VALUES[i] && slider <= SLIDER_VALUES[i + 1]) {
                float t = (slider - SLIDER_VALUES[i]) / (SLIDER_VALUES[i + 1] - SLIDER_VALUES[i]);
                return DB_TARGETS[i] + t * (DB_TARGETS[i + 1] - DB_TARGETS[i]);
            }
        }
        return DB_TARGETS[DB_TARGETS.length - 1];
    }
}
