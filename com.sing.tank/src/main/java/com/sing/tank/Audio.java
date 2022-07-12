package com.sing.tank;

import javax.sound.sampled.*;
import java.io.IOException;

public class Audio {
    /*** 音频格式**/
    private AudioFormat audioFormat;
    private SourceDataLine sourceDataLine;
    private DataLine.Info dataLineInfo;
    private AudioInputStream audioInputStream;
    byte[] b = new byte[1024 * 1024 * 15];

    public Audio(String fileName) {
        try {
            audioInputStream = AudioSystem.getAudioInputStream(Audio.class.getClassLoader().getResource(fileName));
            audioFormat = audioInputStream.getFormat();
            dataLineInfo = new DataLine.Info(SourceDataLine.class, audioFormat);
            sourceDataLine = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
            // FloatControl floatControl = (FloatControl) sourceDataLine.getControl(FloatControl.Type.MASTER_GAIN);
            // floatControl.setValue(-40);
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void loop() {
        while (true) {
            int len = 0;
            try {
                sourceDataLine.open(audioFormat, 1024 * 1024 * 15);
                sourceDataLine.start();
                audioInputStream.mark(100000000);
                while ((len = audioInputStream.read(b)) > 0) {
                    sourceDataLine.write(b, 0, len);

                }
                audioInputStream.reset();
                sourceDataLine.drain();
                sourceDataLine.close();
            } catch (LineUnavailableException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void play() {
        int len = 0;
        byte[] b = new byte[1024 * 5];
        try {
            sourceDataLine.open(audioFormat, 1024 * 5);
            sourceDataLine.start();

            audioInputStream.mark(100000000);
            while ((len = audioInputStream.read(b)) > 0) {
                sourceDataLine.write(b, 0, len);

            }
            //audioInputStream.reset();
            sourceDataLine.drain();
            sourceDataLine.close();
        } catch (LineUnavailableException | IOException e) {
            e.printStackTrace();
        } finally {
            close();
        }

    }

    public void close() {
        try {
            audioInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
