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
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.DataLine.Info;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author achermann
 */
public class EffectPlayer implements Runnable, SoundController {

    private Thread soundThread = null;
    private String[] nameList = {"Applause", "Ahem"};
    private String[] effectList = {"ch/ringo/lorry/res/audio/Applause.wav", "ch/ringo/lorry/res/audio/Ahem.wav"};

    private void play() {
        if (effectList == null) {
            System.out.println("Effect list is empty");
            return;
        }
        for (int i = 0; i < effectList.length; i++) {
            play(effectList[i]);
        }
    }

    public void play(String name) {
        URL url = null;
        for (int i = 0; i < nameList.length; i++) {
            System.out.println(name + " vs. " + nameList[i]);
            if (name.equals((nameList[i]))) {
                url = ClassLoader.getSystemResource(effectList[i]);
                break;
            }
        }
        if (url != null) {
            play(url);
        }
    }

    private void play(URL url) {
        System.out.println("Playing " + url);
        try {
            AudioInputStream audio = AudioSystem.getAudioInputStream(url);
            AudioFormat format = audio.getFormat();
            Info info = new DataLine.Info(Clip.class, format);
            Clip clip = (Clip) AudioSystem.getLine(info);
            clip.open(audio);
            clip.start();
//            clip.drain();
//            clip.close();
        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(EffectPlayer.class
                    .getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(EffectPlayer.class
                    .getName()).log(Level.SEVERE, null, ex);
        } catch (LineUnavailableException ex) {
            Logger.getLogger(EffectPlayer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void skip() {

    }

    @Override
    public void run() {
        play();
    }

    @Override
    public void start() {
        soundThread = new Thread(new EffectPlayer());
        soundThread.start();
    }

    @Override
    public void stop() {
        // TODO: stop is deprecated. Use state and interrupt()
        soundThread.stop();
    }

}
