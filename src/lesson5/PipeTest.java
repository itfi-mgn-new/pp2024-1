package lesson5;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

public class PipeTest {

	public static void main(String[] args) throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		try(PipedInputStream is = new PipedInputStream();
			PipedOutputStream os = new PipedOutputStream(is)) {
			
			Thread t = new Thread(()->got(is));
			t.start();
			os.write("dfldkkfl".getBytes());
			os.flush();			
			Thread.sleep(2000);
		}
	}

	private static void got(PipedInputStream is) {
		// TODO Auto-generated method stub
		System.err.println("Enter ");
		
		byte[] buffer = new byte[100];
		
		try {
			int len = is.read(buffer);
			System.err.println("REad: "+new String(buffer,0,len));
		} catch (IOException e) {
		}
		
	}

}
