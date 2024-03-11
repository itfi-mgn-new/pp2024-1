package lesson3;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

public class FileLokTest {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		try(RandomAccessFile file = new RandomAccessFile("c:/users/student/z.txt", "rw");
		    FileChannel channel = file.getChannel()) {
			
			try (FileLock lock1 = channel.lock(0, 10, true)) {
				// TODO:
				
			}

			FileLock lock =null;

			try{lock = channel.lock(0, 10, false);
				// TODO:
			} finally {
				lock.close();
			}
		}
	}

}
