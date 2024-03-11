package lesson3;

import java.util.concurrent.Semaphore;

public class SemaphoreTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		final Semaphore	sema = new Semaphore(3);
		final Thread	t1 = new Thread(()->{
							try {
								sema.acquire();	// synchronized(sema) {
								System.err.println("Enter semaphore "+Thread.currentThread().getName());
								sema.release();	// }
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						});
		final Thread	t2 = new Thread(()->{
						try {
							sema.acquire();		// synchronized(sema) {
							System.err.println("Enter semaphore "+Thread.currentThread().getName());
							sema.release();		// }
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					});
		t1.start();
		t2.start();
//		sema.release();
		System.err.println("Done");
	}
}
