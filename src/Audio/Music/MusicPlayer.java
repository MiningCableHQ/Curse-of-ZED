package Audio.Music;

import Audio.Audio;
import Main.GamePanel;

public class MusicPlayer {
    private Map1Music map1Music;
    private Map2Music map2Music;
    private ShopMusic shopMusic;
    private CombatMusic combatMusic;
    private BossMusic bossMusic;
    private RainMusic rainMusic;
    private CutsceneMusic cutsceneMusic;

    private Audio currentPlayingMusic = null;

    private GamePanel gp;

    public MusicPlayer(GamePanel gp) {
        map1Music = new Map1Music();
        map2Music = new Map2Music();
        shopMusic = new ShopMusic();
        combatMusic = new CombatMusic();
        bossMusic = new BossMusic();
        rainMusic = new RainMusic();
        cutsceneMusic = new CutsceneMusic();

        this.gp = gp;
    }

    /** Preload all music clips in the background so transitions are instant. */
    public void preloadAllMusic() {
        map1Music.preload();
        map2Music.preload();
        combatMusic.preload();
        bossMusic.preload();
        rainMusic.preload();
        cutsceneMusic.preload();
    }

    public void playMapMusic() {
        if (cutsceneMusic != null && cutsceneMusic.isPlaying()) return;
        Audio targetMusic = null;
        if (gp.currentMap == 0) targetMusic = map1Music;
        else if (gp.currentMap == 1 || gp.currentMap == 2) targetMusic = map2Music;

        if (targetMusic == null) return;

        if (targetMusic == currentPlayingMusic && targetMusic.isPlaying()) {
            System.out.println("[MusicPlayer] Map music already playing, skipping restart.");
            return;
        }

        // Stop previous track without closing its clip — keeps it hot for revisits.
        if (currentPlayingMusic != null && currentPlayingMusic != targetMusic) {
            currentPlayingMusic.stop();
        }

        // Only force a full reload if the clip isn't open yet (truly first play).
        if (!targetMusic.isClipReady()) {
            targetMusic.resetForReload();
        }

        currentPlayingMusic = targetMusic;
        targetMusic.play(true);
    }

    public void stopMapMusic() {
        if (currentPlayingMusic != null) {
            currentPlayingMusic.stop(); // Keep clip open for fast resume
            currentPlayingMusic = null;
        }
        if (rainMusic.isPlaying()) rainMusic.stop();
    }

    public void resetMapMusic() {
        currentPlayingMusic = null;
    }

    public void resumeMapMusic() {
        playMapMusic();
    }

    public void updateVolume(float volume) {
        map1Music.setVolume(volume);
        map2Music.setVolume(volume);
        shopMusic.setVolume(volume);
        combatMusic.setVolume(volume);
        bossMusic.setVolume(volume);
        rainMusic.setVolume(volume);
        cutsceneMusic.setVolume(volume);
    }

    public void playRainMusic() {
        if (!rainMusic.isClipReady()) rainMusic.resetForReload();
        rainMusic.play(true);
    }

    public void stopRainMusic() {
        if (rainMusic.isPlaying()) rainMusic.stop();
    }

    public void playCutsceneMusic() {
        if (currentPlayingMusic != null) currentPlayingMusic.stop();
        if (combatMusic.isPlaying()) combatMusic.stop();
        if (bossMusic.isPlaying()) bossMusic.stop();
        if (rainMusic.isPlaying()) rainMusic.stop();
        if (!cutsceneMusic.isClipReady()) cutsceneMusic.resetForReload();
        currentPlayingMusic = cutsceneMusic;
        cutsceneMusic.play(true);
    }

    public void stopCutsceneMusic() {
        if (cutsceneMusic.isPlaying()) cutsceneMusic.stop();
        currentPlayingMusic = null;
    }

    public Map1Music getMap1Music() { return map1Music; }
    public Map2Music getMap2Music() { return map2Music; }
    public ShopMusic getShopMusic() { return shopMusic; }
    public CombatMusic getCombatMusic() { return combatMusic; }
    public BossMusic getBossMusic() { return bossMusic; }

    public void playShopMusic() {
        if (currentPlayingMusic != null) {
            currentPlayingMusic.stop(); // Keep clip open
        }
        if (rainMusic.isPlaying()) rainMusic.stop();
        if (!shopMusic.isClipReady()) {
            shopMusic.resetForReload();
        }
        currentPlayingMusic = shopMusic;
        shopMusic.play(true);
        System.out.println("[MusicPlayer] Playing shop music");
    }

    public void stopAllMusic() {
        if (currentPlayingMusic != null) { currentPlayingMusic.stop(); currentPlayingMusic = null; }
        if (combatMusic.isPlaying()) combatMusic.stop();
        if (bossMusic.isPlaying()) bossMusic.stop();
        if (rainMusic.isPlaying()) rainMusic.stop();
        if (cutsceneMusic.isPlaying()) cutsceneMusic.stop();
        if (shopMusic.isPlaying()) shopMusic.stop();
    }
}
