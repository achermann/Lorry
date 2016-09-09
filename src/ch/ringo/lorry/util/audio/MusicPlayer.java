/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.ringo.lorry.util.audio;

import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.DataLine.Info;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author achermann
 */
public class MusicPlayer implements Runnable, SoundController {

    private Thread soundThread = null;
    private String songList[] = {"ch/ringo/lorry/res/audio/Popcorn.wav",
        "ch/ringo/lorry/res/audio/OxygenePart4.wav"};

    private void play() {
        if (songList == null) {
            System.out.println("Song list is empty");
            return;
        }
        for (int i = 0;; i++) {
            play(songList[i % songList.length]);
        }
    }

    private void play(String name) {
        System.out.println("Playing " + name);
        try {
            URL url = ClassLoader.getSystemResource(name);
            AudioInputStream audio = AudioSystem.getAudioInputStream(url);
            AudioFormat format = audio.getFormat();
            Info info = new DataLine.Info(SourceDataLine.class, format);
            SourceDataLine line = (SourceDataLine) AudioSystem.getLine(info);
            line.open();
            line.start();
            int nBytesRead = 0;
            final int EXTERNAL_BUFFER_SIZE = 524288;
            byte[] abData = new byte[EXTERNAL_BUFFER_SIZE];
            while (nBytesRead
                    != -1) {
                nBytesRead = audio.read(abData, 0, abData.length);
                if (nBytesRead >= 0) {
                    line.write(abData, 0, nBytesRead);
                }
            }
            line.drain();
            line.close();
        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(MusicPlayer.class
                    .getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MusicPlayer.class
                    .getName()).log(Level.SEVERE, null, ex);
        } catch (LineUnavailableException ex) {
            Logger.getLogger(MusicPlayer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void skip() {
        
    }

    @Override
    public void run() {
        play();
    }

    public void start() {
        soundThread = new Thread(new MusicPlayer());
        soundThread.start();
    }

    public void stop() {
        // TODO: stop is deprecated. Use state and interrupt()
        soundThread.stop();
    }

}
