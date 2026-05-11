package Audio;

import Audio.Music.Map1Music;

public class AudioPlay {
    public static void main(String[] args) {
        Audio map1BG = new Map1Music();

        map1BG.play();

        try{
            Thread.sleep(5000);
        }catch(Exception e){
            e.printStackTrace();
        }

        map1BG.stop();

        try{
            Thread.sleep(3000);
        }catch(Exception e){
            e.printStackTrace();
        }

        map1BG.play();
    }
}
