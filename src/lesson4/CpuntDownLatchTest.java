package lesson4;

import java.util.concurrent.CountDownLatch;

public class CpuntDownLatchTest {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		final CountDownLatch	latchStart = new CountDownLatch(1);
		final int[]				values = new int[3];
		final CountDownLatch	latchEnd = new CountDownLatch(3);
		
		final Thread	t1 = new Thread(()->{
								try {
									latchStart.await();
								} catch (InterruptedException e) {
								}
								System.err.println("T 1 "+values[0]);
								latchEnd.countDown();
							});
		final Thread	t2 = new Thread(()->{
								try {
									latchStart.await();
								} catch (InterruptedException e) {
								}
								System.err.println("T 2 "+values[1]);
								latchEnd.countDown();
							});
		final Thread	t3 = new Thread(()->{
								try {
									latchStart.await();
								} catch (InterruptedException e) {
								}
								System.err.println("T 3 "+values[2]);
								latchEnd.countDown();
							});
		
		
		t1.start();
		t2.start();
		t3.start();

		values[0] = 100;
		values[1] = 200;
		values[2] = 300;

		latchStart.countDown();
		
//		t1.join();
//		t2.join();
//		t3.join();

		latchEnd.await();
		System.err.println("The end");
	}

}
