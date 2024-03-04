package lesson2;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

public class LockTest {
	private static List<Integer> values = new ArrayList<>();
	
	
	public static void main(String[] args) {
		final ReentrantReadWriteLock	rwLock = new ReentrantReadWriteLock();
		
		Thread t1 = new Thread(()->{
						for(int index = 0; index < 100; index++) {
								WriteLock wl = rwLock.writeLock();
								try {
									wl.lock();		// synchronized(rwLoc) {
									
									values.add(index);
									
								} finally {
									wl.unlock();	// }
								}
//								synchronized(values) {
//									values.add(index);
//								}
							try {
								Thread.sleep(10);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					});
		Thread t2 = new Thread(()->{
							for (;;) {
								int sum = 0;
								ReadLock rl = rwLock.readLock();
								try {
									rl.lock();		// synchronized(rwLoc) {
									
									for(int value : values) {
										sum +=  value;
									}
									
								} finally {
									rl.unlock();	// }
								}
//								synchronized(values) {
//									for(int value : values) {
//										sum +=  value;
//									}
//								}
								System.err.println("sum="+sum);
							}
						});
		Thread t3 = new Thread(()->{
							for (;;) {
								int sum = 0;
								ReadLock rl = rwLock.readLock();
								try {
									rl.lock();		// synchronized(rwLoc) {
									
									for(int value : values) {
										sum +=  value;
									}
									
								} finally {
									rl.unlock();	// }
								}
//								synchronized(values) {
//									for(int value : values) {
//										sum +=  value;
//									}
//								}
								System.err.println("sum="+sum);
							}
						});
		t1.start();
		t2.setDaemon(true);
		t2.start();
		t3.setDaemon(true);
		t3.start();
	}

}
