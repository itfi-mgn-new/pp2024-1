package lesson2;

public class SharedTest {
	static volatile int	val = 0;
	
	public static void main(String[] args) throws InterruptedException {
		final Object	lock = new Object();
		final Object	lock1 = lock;
		
		Thread	t1 = new Thread(()->{
						for (int index = 0; index < 100000; index++) {
							synchronized(lock) {	// try { aload lock
													//		 monitorenter
								val++;
								
							}						//  } finally {
													//       aload lock
													//		 monotorexit
													//  }
						}
					});
		Thread	t2 = new Thread(()->{
						for (int index = 0; index < 100000; index++) {
							synchronized(lock1) {
								val++;
							}
						}
					});
		t1.start();
		t2.start();
		t1.join();
		t2.join();
		System.err.println("Val="+val);
	}

}
