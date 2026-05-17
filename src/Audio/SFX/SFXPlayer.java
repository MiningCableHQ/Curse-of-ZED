package Audio.SFX;

import javax.sound.sampled.*;
import java.io.File;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SFXPlayer {

    private float volume = 0.7f;
    private final ConcurrentHashMap<String, Clip> clipCache = new ConcurrentHashMap<>();
    private final ExecutorService sfxExecutor = Executors.newCachedThreadPool();

    private static final float[] SLIDER_VALUES = {0.0f, 0.5f, 0.75f, 1.0f};
    private static final float[] DB_TARGETS    = {-80f, -10f,  -3f,   6f};

    /** Load the clip into memory in background so first play is instant. */
    public void preloadSFX(AudioSFX sfx) {
        String path = sfx.getFilePath();
        if (clipCache.containsKey(path)) return;
        sfxExecutor.submit(() -> {
            try {
                Clip clip = AudioSystem.getClip();
                AudioInputStream audioIn = AudioSystem.getAudioInputStream(new File(path));
                clip.open(audioIn);
                applyVolumeToClip(clip);
                clipCache.put(path, clip);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Play an SFX. If preloaded, resets the cached clip and starts instantly
     * (no file I/O, < 1 ms). Falls back to background load on first use.
     */
    public void playSFX(AudioSFX sfx) {
        String path = sfx.getFilePath();
        Clip cached = clipCache.get(path);
        if (cached != null && cached.isOpen()) {
            if (cached.isRunning()) cached.stop();
            cached.setFramePosition(0);
            applyVolumeToClip(cached);
            cached.start();
            return;
        }
        // Not cached yet — load and play in background (only on first call)
        sfxExecutor.submit(() -> {
            try {
                Clip clip = AudioSystem.getClip();
                AudioInputStream audioIn = AudioSystem.getAudioInputStream(new File(path));
                clip.open(audioIn);
                applyVolumeToClip(clip);
                clipCache.putIfAbsent(path, clip);
                clip.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void setVolume(float volume) {
        this.volume = Math.max(0f, Math.min(1f, volume));
        clipCache.values().forEach(this::applyVolumeToClip);
    }

    public void stopAll() {
        clipCache.values().forEach(clip -> { if (clip.isRunning()) clip.stop(); });
    }

    private void applyVolumeToClip(Clip clip) {
        try {
            if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                FloatControl vc = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                vc.setValue(interpolateDB(volume));
            }
        } catch (Exception ignored) {}
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
