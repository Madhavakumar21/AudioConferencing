


import java.io.*;
import java.net.*;
import java.util.*;
import javax.sound.sampled.*;


class MyThread1 extends Thread
{
	Socket sock;
	public PrintWriter pwrite;

	public void wrapper(OutputStream ostrm)
	{
		this.pwrite = new PrintWriter(ostrm,true);
		this.start();
	}
	
	
	public void run()
	{
		try
		{
			/*
			String user_name;
			BufferedReader sendRead = new BufferedReader(new InputStreamReader(System.in));
			System.out.print("Your name: ");
			user_name = sendRead.readLine();
			System.out.flush();
			//OutputStream ostream = sock.getOutputStream();
			//this.pwrite = new PrintWriter(ostream, true);
			//InputStream istream = sock.getInputStream();
			//BufferedReader receiveread = new BufferedReader(new InputStreamReader(istream));
			//for(int i = 0, i < 3; i++)
			//{
			//	Socket sock = sersock.accept();
			//	BufferedReader sendRead = new BufferedReader(new InputStreamReader(System.in));
			//	OutputStream ostream = sock.getOutputStream();
			//	PrintWriter pwrite = new PrintWriter(ostream, true);
			//	InputStream istream = sock.getInputStream();
			//	BufferedReader receiveread = new BufferedReader(new InputStreamReader(istream));
			//}

			System.out.println("\nStart to chat:\n\n");
			String r_msg, s_msg;
			while(true)
			{
				//if((r_msg = receiveread.readLine()) != null)
				//{
				//	System.out.println(">>>> " + r_msg);
				//}
	
				//System.out.print("You: ");
				s_msg = sendRead.readLine();
				pwrite.println(user_name + ": " + s_msg);
				System.out.flush();
			}
			*/
		}
		catch(Exception e)
		{
			System.out.println("\n\nDISCONNECTED!!!!!!\n\n");
		}
	}
}


public class RadioClient
{
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
		
		///////////////////////////////////////////////////////////////////////
		AudioFormat format = new AudioFormat(50000, 8, 1, true, false);

		System.out.println("1");
		String r_msg, s_msg;
		//while(true)
		//{

		//System.out.print("You: ");
		//s_msg = sendRead.readLine();
		//pwrite.println(s_msg);
		//System.out.flush();




		/*
        byte[][] content = new byte[512][256];
		for(int k = 0; k < 512; k++) {
			istream.read(content[k], 0, 256);
		}
        */




		/*if((r_msg = receiveread.readLine()) != null)
		{
			System.out.println("\n" + r_msg + "\n");
		}*/

		//}
		try {
			System.out.println("2");
			SourceDataLine speakers;
            speakers = AudioSystem.getSourceDataLine(format);
            speakers.open(format);
            speakers.start();
            //byte[][] content = new byte[512][256];
            byte[] content = new byte[256];
            //for(int k = 0; k < 512; k++) {
            while(true) {
                content = new byte[256];
                istream.read(content, 0, 256);
                // write the captured audio to the speakers
                //speakers.write(content[k], 0, bytesRead[k]);
				speakers.write(content, 0, 256);
                //Thread.sleep(1);
            }
            //speakers.drain();
            //speakers.close();
            //System.out.println("3");
		} catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Do nothing.
        }
		////////////////////////////////////////////////////////////////////////
	}
}
