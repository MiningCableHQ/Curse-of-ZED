package Audio;

import javax.sound.sampled.*;
import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Audio {
    protected String name;
    protected String filePath;
    protected Clip clip;
    protected boolean isRunning = false;
    protected boolean isLooping = false;
    protected static ExecutorService audioExecutor = Executors.newSingleThreadExecutor();
    protected FloatControl volumeControl;
    protected float pendingVolume = 0.5f;   // 50% default
    protected boolean hasPlayed = false;

    // Target dB mapping: slider value -> desired dB
    //  0.0 -> -80 dB (mute)
    //  0.5 -> -10 dB (comfortable)
    //  0.75 -> -3 dB  (loud)
    //  1.0 -> +6 dB   (maximum)
    private static final float[] SLIDER_VALUES = {0.0f, 0.5f, 0.75f, 1.0f};
    private static final float[] DB_TARGETS   = {-80f, -10f, -3f, 6f};

    public Audio() {
        this.name = "";
        this.filePath = "";
    }

    public Audio(String name, String filePath) {
        this.name = name;
        this.filePath = filePath;
    }

    public void play() {
        play(false);
    }

    public void play(boolean loop) {
        audioExecutor.submit(() -> {
            try {

                if (hasPlayed && clip != null && clip.isOpen()) {
                    clip.setFramePosition(0);
                    if (loop) clip.loop(Clip.LOOP_CONTINUOUSLY);
                    clip.start();
                    System.out.println("Now playing: " + name);
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
                System.out.println("Now playing: " + name);
                while (isRunning && clip.isOpen()) Thread.sleep(100);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void stop() {
        isLooping = false;
        isRunning = false;
        // Don't close the clip here - just stop it
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
        // Keep hasPlayed as true, but don't close the clip
        System.out.println("Stopped: " + name);
    }

    // Add a separate method for complete cleanup if needed
    public void close() {
        isLooping = false;
        isRunning = false;
        stopCurrentClip();  // This closes the clip
        hasPlayed = false;   // Reset so it reloads next time
    }

    protected void stopCurrentClip() {
        try {
            if (clip != null) {
                if (clip.isRunning()) clip.stop();
                clip.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void applyVolume() {
        if (volumeControl == null) return;

        float actualMin = volumeControl.getMinimum();
        float actualMax = volumeControl.getMaximum();

        // Interpolate desired dB using the custom mapping
        float desiredDB = interpolateDB(pendingVolume);
        // Clamp to actual control range
        float finalDB = Math.max(actualMin, Math.min(actualMax, desiredDB));
        volumeControl.setValue(finalDB);
    }

    private float interpolateDB(float slider) {
        // Find which segment the slider falls into
        if (slider <= SLIDER_VALUES[0]) return DB_TARGETS[0];
        if (slider >= SLIDER_VALUES[SLIDER_VALUES.length-1]) return DB_TARGETS[DB_TARGETS.length-1];

        for (int i = 0; i < SLIDER_VALUES.length-1; i++) {
            if (slider >= SLIDER_VALUES[i] && slider <= SLIDER_VALUES[i+1]) {
                float t = (slider - SLIDER_VALUES[i]) / (SLIDER_VALUES[i+1] - SLIDER_VALUES[i]);
                // Linear interpolation between dB targets
                return DB_TARGETS[i] + t * (DB_TARGETS[i+1] - DB_TARGETS[i]);
            }
        }
        return DB_TARGETS[DB_TARGETS.length-1];
    }

    public void setVolume(float volume) {
        pendingVolume = Math.max(0f, Math.min(1f, volume));
        if (volumeControl != null) applyVolume();
    }

    public boolean isPlaying() {
        return isRunning && clip != null && clip.isRunning();
    }

    public boolean isClipReady() {
        return clip != null && clip.isOpen();
    }

    public void shutdown() {
        stop();
        audioExecutor.shutdown();
    }

    public void resetForReload() {
        hasPlayed = false;
        stopCurrentClip();
    }

    // Uses close() so clip.isOpen() becomes false, reliably exiting the
    // executor while-loop even when isRunning visibility is delayed across threads.
    public void stopCurrentMusic() {
        System.out.println("[AudioMusic] stopCurrentMusic() called - playing=" + isPlaying() + " name=" + name);
        close();
        System.out.println("[AudioMusic] stopCurrentMusic() done");
    }
}