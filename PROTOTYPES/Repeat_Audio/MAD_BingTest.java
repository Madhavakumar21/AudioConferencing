import javax.sound.sampled.*;
import java.io.*;

public class MAD_BingTest {
    public static void main(String[] args) {
        AudioFormat format = new AudioFormat(50000, 8, 1, true, false);
        DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
        TargetDataLine line = null;
        try {
            System.out.println("1");
            line = (TargetDataLine) AudioSystem.getLine(info);
            line.open(format);
            line.start();
            byte[][] content = new byte[512][256];
            int[] bytesRead = new int[512];
            for(int i = 0; i < 512; i++) {
                bytesRead[i] = line.read(content[i], 0, content[i].length);
            }
            System.out.println("2");
            SourceDataLine speakers;
            speakers = AudioSystem.getSourceDataLine(format);
            speakers.open(format);
            speakers.start();
            for(int k = 0; k < 512; k++) {
                // write the captured audio to the speakers
                speakers.write(content[k], 0, bytesRead[k]);
                Thread.sleep(1);
            }
            speakers.drain();
            speakers.close();
            System.out.println("3");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (line != null) {
                line.stop();
                line.close();
            }
        }
    }
}
