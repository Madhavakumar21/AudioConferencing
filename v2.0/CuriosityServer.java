


import java.io.*;
import java.net.*;
import java.util.List;
import java.util.*;
import javax.sound.sampled.*;



class MyThread extends Thread
{
	Socket sock;
	public PrintWriter pwrite;
	public CuriosityServer parent;
	public OutputStream ostream;
	//multi_server parent;
	/*
	MyThread my_thread1;
	MyThread my_thread2;
	MyThread my_thread3;
	MyThread my_thread4;
	MyThread my_thread5;
	*/
	List<MyThread> my_threads;
	int t_idx;

	//public void wrapper(Socket s, MyThread my_thread1, MyThread my_thread2, MyThread my_thread3, MyThread my_thread4, MyThread my_thread5)
	public void wrapper(Socket s, List<MyThread> my_threads, int t_idx)
	{
		/*
		this.my_thread1 = my_thread1;
		this.my_thread2 = my_thread2;
		this.my_thread3 = my_thread3;
		this.my_thread4 = my_thread4;
		this.my_thread5 = my_thread5;
		*/
		this.t_idx = t_idx;
		this.my_threads = my_threads;
		this.sock = s;
		this.start();
	}
	
	
	public void run()
	{
		try
		{
			//BufferedReader sendRead = new BufferedReader(new InputStreamReader(System.in));
			this.ostream = sock.getOutputStream();
			this.pwrite = new PrintWriter(ostream, true);
			InputStream istream = sock.getInputStream();
			BufferedReader receiveread = new BufferedReader(new InputStreamReader(istream));
			//for(int i = 0, i < 3; i++)
			//{
			//	Socket sock = sersock.accept();
			//	BufferedReader sendRead = new BufferedReader(new InputStreamReader(System.in));
			//	OutputStream ostream = sock.getOutputStream();
			//	PrintWriter pwrite = new PrintWriter(ostream, true);
			//	InputStream istream = sock.getInputStream();
			//	BufferedReader receiveread = new BufferedReader(new InputStreamReader(istream));
			//}
			
			
            byte[] content = new byte[256];
			while(true)
            {
                content = new byte[256];
                istream.read(content, 0, 256);
                //this.ostream.write(content);
                for(int i = 0; i < this.my_threads.size(); i++)
                {
                    if(i == this.t_idx)
                        continue;
                    if(this.my_threads.get(i).sock.isConnected())
                        this.my_threads.get(i).ostream.write(content);
                    /*
                    if(this.my_thread1.sock.isConnected()) this.my_thread1.pwrite.println(r_msg);
                    if(this.my_thread2.sock.isConnected()) this.my_thread2.pwrite.println(r_msg);
                    if(this.my_thread3.sock.isConnected()) this.my_thread3.pwrite.println(r_msg);
                    if(this.my_thread4.sock.isConnected()) this.my_thread4.pwrite.println(r_msg);
                    if(this.my_thread5.sock.isConnected()) this.my_thread5.pwrite.println(r_msg);
                    */
                    System.out.flush();
                }
            }
			
			
			//String r_msg, s_msg;
			//while(true)
			//{
			//	if((r_msg = receiveread.readLine()) != null)
			//	{
			//		/*
			//		System.out.println(">>>> " + r_msg);
			//		*/
			//
			//		for(int i = 0; i < this.my_threads.size(); i++)
			//		{
			//			if(this.my_threads.get(i).sock.isConnected())
			//				this.my_threads.get(i).pwrite.println(r_msg);
			//			/*
			//			if(this.my_thread1.sock.isConnected()) this.my_thread1.pwrite.println(r_msg);
			//			if(this.my_thread2.sock.isConnected()) this.my_thread2.pwrite.println(r_msg);
			//			if(this.my_thread3.sock.isConnected()) this.my_thread3.pwrite.println(r_msg);
			//			if(this.my_thread4.sock.isConnected()) this.my_thread4.pwrite.println(r_msg);
			//			if(this.my_thread5.sock.isConnected()) this.my_thread5.pwrite.println(r_msg);
			//			*/
			//			System.out.flush();
			//		}
			//	}
			//
			//	//System.out.print("You: ");
			//	//s_msg = sendRead.readLine();
			//	//pwrite.println(s_msg);
			//	//System.out.flush();
			//}





			///////////////////////////////////////
			/*AudioFormat format = new AudioFormat(50000, 8, 1, true, false);
			DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
			TargetDataLine line = null;
			try {
				System.out.println("1");
				line = (TargetDataLine) AudioSystem.getLine(info);
				line.open(format);
				line.start();
				//byte[][] content = new byte[512][256];
                byte[] content = new byte[256];
				int bytesRead;
				//for(int i = 0; i < 512; i++) {
                while(true) {
                    content = new byte[256];
					bytesRead = line.read(content, 0, content.length);
                    this.my_threads.get(0).ostream.write(content);
                    //Thread.sleep(1);
				}*/
				//System.out.println("2");
				/*for(int i = 0; i < this.my_threads.size(); i++)
				{
					if(this.my_threads.get(i).sock.isConnected()) {
						//this.my_threads.get(i).pwrite.println(r_msg);
						for(int z = 0; z < 512; z++) {
							this.my_threads.get(i).ostream.write(content[z]);
						}
					}
					System.out.flush();
				}*/
				//System.out.println("3");
			/*} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (line != null) {
					line.stop();
					line.close();
				}
			}*/
			///////////////////////////////////////




		}
		catch(Exception e)
		{
			this.my_threads.remove(this);
			System.out.println("\n\nDISCONNECTED!!!!!!\n\n");
		}
	}
}






public class CuriosityServer
{
	/*
	static MyThread my_thread1;
	static MyThread my_thread2;
	static MyThread my_thread3;
	static MyThread my_thread4;
	static MyThread my_thread5;
	*/
	
	static List<MyThread> my_threads = new ArrayList<MyThread>();
	static MyThread my_thread;

	public static void main(String args[]) throws Exception
	{
		BufferedReader sendRead = new BufferedReader(new InputStreamReader(System.in));
		/*
		my_thread1 = new MyThread();
		my_thread2 = new MyThread();
		my_thread3 = new MyThread();
		my_thread4 = new MyThread();
		my_thread5 = new MyThread();
		*/
		ServerSocket sersock = new ServerSocket(9999);
		System.out.println("\n\nServer ready for chatting.\n\n");

		while(true)
		{
			my_thread = new MyThread();
			my_thread.wrapper(sersock.accept(), my_threads, my_threads.size());
			my_threads.add(my_thread);

			/*
			my_thread1.wrapper(sersock.accept(), my_thread1, my_thread2, my_thread3, my_thread4, my_thread5);
			my_thread2.wrapper(sersock.accept(), my_thread1, my_thread2, my_thread3, my_thread4, my_thread5);
			my_thread3.wrapper(sersock.accept(), my_thread1, my_thread2, my_thread3, my_thread4, my_thread5);
			my_thread4.wrapper(sersock.accept(), my_thread1, my_thread2, my_thread3, my_thread4, my_thread5);
			my_thread5.wrapper(sersock.accept(), my_thread1, my_thread2, my_thread3, my_thread4, my_thread5);
			*/
			//my_thread1.parent = this;
			//my_thread2.parent = this;
			//my_thread3.parent = this;
		}
		//OutputStream ostream = sock.getOutputStream();
		//PrintWriter pwrite = new PrintWriter(ostream, true);
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

		///////////String s_msg;
		///////////while(true)
		///////////{
		//	if((r_msg = receiveread.readLine()) != null)
		//	{
		//		System.out.println(">>>> " + r_msg);
		//	}

			
			/*
			System.out.print("You: ");
			s_msg = sendRead.readLine();
			my_thread1.pwrite.println(s_msg);
			my_thread2.pwrite.println(s_msg);
			my_thread3.pwrite.println(s_msg);
			System.out.flush();
			*/
		///////////}
	}
}
