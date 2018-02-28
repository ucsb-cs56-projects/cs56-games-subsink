package edu.ucsb.cs56.projects.games.subsink;

import edu.ucsb.cs56.projects.games.subsink.Entity;

import java.awt.*;
import java.io.*;

import edu.ucsb.cs56.projects.games.subsink.ImageLoader;
import sun.audio.*;


/**
 * Created by andy on 2/25/18.
 */
public class Explosion {

    public void playExplosionSound() throws IOException {

            // open the sound file as a Java input stream
            String gongFile = "assets/sounds/explosion.wav";
            InputStream in = new FileInputStream(gongFile);

            // create an audiostream from the inputstream
            AudioStream audioStream = new AudioStream(in);

            // play the audio clip with the audioplayer class
            AudioPlayer.player.start(audioStream);

    }

    public void playDropChargeSound(){

        // open the sound file as a Java input stream
        String gongFile = "assets/sounds/drop_charge.wav";
        InputStream in = null;
        try {
            in = new FileInputStream(gongFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // create an audiostream from the inputstream
        AudioStream audioStream = null;
        try {
            audioStream = new AudioStream(in);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // play the audio clip with the audioplayer class
        AudioPlayer.player.start(audioStream);

    }


}
