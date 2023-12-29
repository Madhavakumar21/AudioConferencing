import java.io.*;
import java.net.*;
import java.util.*;

import javax.sound.sampled.*;


class MyThread1 extends Thread
{
	Socket sock;
	public PrintWriter pwrite;
    final static int length = 1048576;
	//public multi_server parent; AudioConferencing_client

	public void wrapper(OutputStream ostrm)
	{
		this.pwrite = new PrintWriter(ostrm,true);
		this.start();
	}
	
	
	public void run()
	{
		try
		{
			String user_name;
			BufferedReader sendRead = new BufferedReader(new InputStreamReader(System.in));
			//System.out.print("Your name: ");
			//user_name = sendRead.readLine();
			System.out.flush();


			System.out.println("\nStart to chat:\n\n");
			//String r_msg, s_msg;

            AudioFormat format = new AudioFormat(100000, 8, 1, true, false);
            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
            TargetDataLine line = null;

            try {
                //System.out.println("1");
                line = (TargetDataLine) AudioSystem.getLine(info);
                line.open(format);
                line.start();
                byte[] content = new byte[length];
                int bytesRead;

                char[] bytes = new char[length];

			    while(true)
			    {

                    //for(int i = 0; i < 512; i++) {
                    bytesRead = line.read(content, 0, content.length);
                    //}
                    //System.out.println("2");

                    //bytes = char content;
                    bytes = new String(content).toCharArray();

                    pwrite.write(bytes);
                    System.out.flush();

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
		catch(Exception e)
		{
			System.out.println("\n\nDISCONNECTED!!!!!!\n\n");
		}
	}
}


public class AudioConferencing_client
{
    final static int length = 1048576;
	public static void main(String args[]) throws Exception
	{
		//Socket sock = new Socket("172.16.42.8", 9999);
		Socket sock = new Socket("localhost", 9999);
		BufferedReader sendRead = new BufferedReader(new InputStreamReader(System.in));
		OutputStream ostream = sock.getOutputStream();
		//PrintWriter pwrite = new PrintWriter(ostream,true);
		InputStream istream = sock.getInputStream();
		BufferedReader receiveread = new BufferedReader(new InputStreamReader(istream));
		System.out.println("\n\nClient ready for chatting.\n\n");
		
		MyThread1 t = new MyThread1();
		//t.pwrite = this.pwrite;
		t.wrapper(ostream);
		
		
		//String r_msg, s_msg;
        AudioFormat format = new AudioFormat(100000, 8, 1, true, false);
        //DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
        TargetDataLine line = null;

        SourceDataLine speakers;
        try {
            //System.out.println("1");
            //line = (TargetDataLine) AudioSystem.getLine(info);
            //line.open(format);
            //line.start();
            byte[] content = new byte[length];


            char[] bytes = new char[length];
            int charsRead;

            speakers = AudioSystem.getSourceDataLine(format);
            speakers.open(format);
            speakers.start();

            while(true)
            {
                charsRead = receiveread.read(bytes, 0, length);

                if (charsRead != -1) {
                //if((r_msg = receiveread.readLine()) != null)
                //{
                //    System.out.println("\n" + r_msg + "\n");
                //}

                    //for(int i = 0; i < 512; i++) {
                    //    bytesRead[i] = line.read(content[i], 0, content[i].length);
                    //}
                    //System.out.println("2");


                    //content = byte bytes;
                    content = new String(bytes).getBytes("UTF-8");

                    //for(int k = 0; k < 512; k++) {
                    // write the captured audio to the speakers
                    speakers.write(content, 0, charsRead);
                    Thread.sleep(1);
                    //}
                    //System.out.println("3");

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //speakers.drain();
            //speakers.close();

            if (line != null) {
                line.stop();
                line.close();
            }
        }
	}
}