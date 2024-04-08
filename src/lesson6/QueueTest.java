package lesson6;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class QueueTest {
	
	
	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		final BlockingQueue<String> queue = new ArrayBlockingQueue<>(10);
		final Thread r = new Thread(()->{
							while(!Thread.interrupted()) {
								try{final String	val = queue.take();
								
									System.err.println("Recv: "+val);
								} catch (InterruptedException e) {
									break;
								} 
							}
					});
		final Thread t = new Thread(()->{
						for(int index = 0; index < 100; index++) {
							try {
								System.err.println("Send: "+index);								
								queue.put("value "+index);
							} catch (InterruptedException e) {
								break;
							}
						}
					});
		r.start();
		t.start();
		
		t.join();
		r.interrupt();
	}

}
