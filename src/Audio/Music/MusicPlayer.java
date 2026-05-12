package Audio.Music;

import Audio.Audio;
import Main.GamePanel;

public class MusicPlayer {
    private Map1Music map1Music;
    private Map2Music map2Music;
    private ShopMusic shopMusic;
    private CombatMusic combatMusic;
    private BossMusic bossMusic;

    private Audio currentPlayingMusic = null;

    private GamePanel gp;

    public MusicPlayer(GamePanel gp){
        map1Music = new Map1Music();
        map2Music = new Map2Music();
        shopMusic = new ShopMusic();
        combatMusic = new CombatMusic();
        bossMusic = new BossMusic();

        this.gp = gp;
    }

    public void playMapMusic() {
        Audio targetMusic = null;
        if (gp.currentMap == 0) targetMusic = map1Music;
        else if (gp.currentMap == 1 || gp.currentMap == 2) targetMusic = map2Music;

        if (targetMusic == null) return;

        // If the same music is already playing and wasn't explicitly stopped, do nothing.
        // 'currentPlayingMusic' is set when we start map music; after battle we set it to null.
        if (targetMusic == currentPlayingMusic && targetMusic.isPlaying()) {
            System.out.println("[MusicPlayer] Map music already playing, skipping restart.");
            return;
        }

        // If we're changing to a different track, stop the previous one.
        if (currentPlayingMusic != null && currentPlayingMusic != targetMusic) {
            currentPlayingMusic.stopCurrentMusic();
        }

        // Force a clean reload (in case the clip was closed, e.g., after battle)
        targetMusic.resetForReload();

        currentPlayingMusic = targetMusic;
        targetMusic.play(true);
    }

    public void stopMapMusic() {
        if (currentPlayingMusic != null) {
            currentPlayingMusic.stopCurrentMusic();
            currentPlayingMusic = null;
        }
    }

    public void resetMapMusic() {
        currentPlayingMusic = null;
    }

    public void resumeMapMusic() {
        resetMapMusic();
        playMapMusic();
    }

    public void updateVolume(float volume){
        map1Music.setVolume(volume);
        map2Music.setVolume(volume);
        shopMusic.setVolume(volume);
        combatMusic.setVolume(volume);
        bossMusic.setVolume(volume);
    }

    public Map1Music getMap1Music() {
        return map1Music;
    }

    public Map2Music getMap2Music() {
        return map2Music;
    }

    public ShopMusic getShopMusic() {
        return shopMusic;
    }

    public CombatMusic getCombatMusic() {
        return combatMusic;
    }

    public BossMusic getBossMusic() {
        return bossMusic;
    }

    public void playShopMusic() {
        if (currentPlayingMusic != null) {
            currentPlayingMusic.stopCurrentMusic();
        }
        shopMusic.resetForReload();
        currentPlayingMusic = shopMusic;
        shopMusic.play(true);
        System.out.println("[MusicPlayer] Playing shop music");
    }
}