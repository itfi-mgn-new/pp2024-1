package lesson5;

import java.util.concurrent.Exchanger;

public class ExchangerTEst {

	public static void main(String[] args) {
		final Exchanger<String> ex = new Exchanger<>();
		final Thread t1 = new Thread(()->{
					try {
						String got = ex.exchange("From thread1");
						System.err.println("Thread 1 receives: "+got);
					} catch (InterruptedException e) {
					}
				});
		final Thread t2 = new Thread(()->{
			try {
				String got = ex.exchange("From thread2");
				System.err.println("Thread 2 receives: "+got);
			} catch (InterruptedException e) {
			}
		});
		t1.start();
		t2.start();
	}

}
