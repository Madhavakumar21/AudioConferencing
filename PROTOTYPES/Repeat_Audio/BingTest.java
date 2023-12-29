import javax.sound.sampled.*;
import java.io.*;

public class BingTest {
    public static void main(String[] args) {
        AudioFormat format = new AudioFormat(64000, 8, 1, true, false);
        DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
        TargetDataLine line = null;
        try {
            line = (TargetDataLine) AudioSystem.getLine(info);
            line.open(format);
            line.start();
            byte[] buffer = new byte[262144];
            int bytesRead;
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            while (true) {
                bytesRead = line.read(buffer, 0, buffer.length);
                output.write(buffer, 0, bytesRead);
                // write the captured audio to the speakers
                SourceDataLine speakers = AudioSystem.getSourceDataLine(format);
                speakers.open(format);
                speakers.start();
                speakers.write(buffer, 0, bytesRead);
                speakers.drain();
                speakers.close();
            }
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
