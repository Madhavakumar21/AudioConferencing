import java.io.*;
import java.net.*;
import java.util.*;



class MyThread extends Thread
{
	Socket sock;
	public PrintWriter pwrite;
    final static int length = 1048576;
	//multi_server parent; //public AudioConferencing_server parent;

	List<MyThread> my_threads;
	int t_idx;

	//public void wrapper(Socket s, MyThread my_thread1, MyThread my_thread2, MyThread my_thread3, MyThread my_thread4, MyThread my_thread5)
	public void wrapper(Socket s, List<MyThread> my_threads, int t_idx)
	{

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
			OutputStream ostream = sock.getOutputStream();
			this.pwrite = new PrintWriter(ostream, true);
			InputStream istream = sock.getInputStream();
			BufferedReader receiveread = new BufferedReader(new InputStreamReader(istream));

			String r_msg, s_msg;
            char[] bytes = new char[length];
            int charsRead;
			while(true)
			{
                charsRead = receiveread.read(bytes, 0, length);

                if (charsRead != -1) {

					for(int i = 0; i < this.my_threads.size(); i++)
					{
						if(this.my_threads.get(i).sock.isConnected()) {
							this.my_threads.get(i).pwrite.write(bytes);
                        }

						System.out.flush();
					}
                }

			}
		}
		catch(Exception e)
		{
			this.my_threads.remove(this);
			System.out.println("\n\nDISCONNECTED!!!!!!\n\n");
		}
	}
}



public class AudioConferencing_server
{

	static List<MyThread> my_threads = new ArrayList<MyThread>();
	static MyThread my_thread;

	public static void main(String args[]) throws Exception
	{
		BufferedReader sendRead = new BufferedReader(new InputStreamReader(System.in));

		ServerSocket sersock = new ServerSocket(9999);
		System.out.println("\n\nServer ready for chatting.\n\n");

		while(true)
		{
			my_thread = new MyThread();
			my_thread.wrapper(sersock.accept(), my_threads, my_threads.size());
			my_threads.add(my_thread);

		}

	}
}
