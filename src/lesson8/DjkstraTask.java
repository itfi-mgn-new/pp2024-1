package lesson8;

public class DjkstraTask {

	public static void main(String[] args) {
		final Object	stick1 = new Object();
		final Object	stick2 = new Object();
		final Object	stick3 = new Object();
		final Object	stick4 = new Object();
		final Object	stick5 = new Object();
	
		Thread	t1 = new Thread(()->process(stick1, stick2));
		Thread	t2 = new Thread(()->process(stick2, stick3));
		Thread	t3 = new Thread(()->process(stick3, stick4));
		Thread	t4 = new Thread(()->process(stick4, stick5));
		Thread	t5 = new Thread(()->process(stick1, stick5));
		
		t1.start();
		t2.start();
		t3.start();
		t4.start();
		t5.start();
	}

	private static void process(final Object left, final Object right) {
		for (;;) {
			eat(left,  right);
			think();
		}
	}
	
	public static void eat(final Object left, final Object right) {
		synchronized(left) {	// left.monitorenter
			synchronized(right) {	// right.monitorenter
				System.err.println("Thread "+Thread.currentThread().getName()+" eating...");
//				try {
//					Thread.sleep(1000);
//				} catch (InterruptedException e) {
//				}					// right.monitorexit
			}
		}						// left.monitorexit
	}
	
	public static void think() {
		System.err.println("Thread "+Thread.currentThread().getName()+" thinking...");
//		try {
//			Thread.sleep(5000);
//		} catch (InterruptedException e) {
//		}
	}
}
