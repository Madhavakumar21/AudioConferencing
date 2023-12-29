


import java.io.*;
import java.util.Scanner;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;
import javax.sound.sampled.Line.Info;
import javax.sound.sampled.Clip;



public class AudioRepeat {

    public static void main(String[] args) throws IOException, LineUnavailableException {
        System.out.print("0");
        Scanner scanner = new Scanner(System.in);
        //int duration = 5; // sample for 5 seconds
        TargetDataLine line = null;
        System.out.print("1");
        scanner.nextLine();
        System.out.print("2");
        // find a DataLine that can be read
        // (maybe hardcode this if you have multiple microphones)
        /*Info[] mixerInfo = AudioSystem.getMixerInfo();
        line = (TargetDataLine) AudioSystem.getLine(mixerInfo[0]);
        if (line == null)
            throw new UnsupportedOperationException("No recording device found");*/
        AudioFormat af = new AudioFormat(64000, 8, 1, true, false);

        DataLine.Info info = new DataLine.Info(TargetDataLine.class, af);
        // checks if system supports the data line
        if (!AudioSystem.isLineSupported(info)) {
            System.out.println("Line not supported");
            System.exit(0);
        }
        line = (TargetDataLine) AudioSystem.getLine(info);

        line.open(af);
        line.start();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        //byte[] buf = new byte[(int)af.getSampleRate() * af.getFrameSize()];
        byte[] buf = new byte[line.getBufferSize() / 5];
        //long end = System.currentTimeMillis() + 1000 * duration;
        int size = 0;
        int len;
        System.out.print("3");
        //while (System.currentTimeMillis() < end && ((len = line.read(buf, 0, buf.length)) != -1)) {
        while ((!scanner.hasNextLine()) && ((len = line.read(buf, 0, buf.length)) != -1)) {
            baos.write(buf, 0, len);
            size += len;
        }
        System.out.print("4");
        line.stop();
        line.close();
        ////baos.close();
        ///////////////////////////////////
        //AudioFormat audioFormat = audioInputStream.getFormat();
        /*SourceDataLine line2 = null;
        DataLine.Info info2 = new DataLine.Info(SourceDataLine.class, af);
        try
        {
            line2 = (SourceDataLine) AudioSystem.getLine(info2);
            line2.open(af);
        }
        catch (LineUnavailableException e)
        {
            e.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        line2.start();
        int nBytesRead = 0;
        byte[] abData = new byte[64000]; //byte[] abData = new byte[128000];
        System.out.print("5");
        while (nBytesRead < size)
        {
            //try
            //{
                abData[0] = buf[nBytesRead];
            //}
            //catch (IOException e)
            //{
            //    e.printStackTrace();
            //}
            if (nBytesRead >= 0)
            {
                int nBytesWritten = line2.write(abData, 0, 1);
            }
            nBytesRead++;
        }
        System.out.print("6");
        line2.drain();
        line2.close();
        */
        System.out.println(size);
        Clip clip = AudioSystem.getClip();
        //AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File("./Input.wav"));
        AudioInputStream inputStream = new AudioInputStream(
            new ByteArrayInputStream(baos.toByteArray()), 
            new AudioFormat(64000, 8, 2, true, false), 
            64000
        );
        clip.open(inputStream);
        clip.start();
        ///////////////////////////////////
        System.out.print("7");
        scanner.nextLine();
        System.out.print("8");
        scanner.nextLine();
        System.out.print("9");
    }
}