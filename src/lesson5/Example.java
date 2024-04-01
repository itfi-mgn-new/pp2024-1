package lesson5;

import java.util.concurrent.Exchanger;

public class Example {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// 1. There are 3 sender and 1 receiver;
		// 2. every sender sends to receiver 10 numbers;
		// 3. receiver receives all the numbers and calculate it's sum
		final Exchanger<Integer> ex = new Exchanger<>();
		final Thread t1 = new Thread(()->send(ex));
		final Thread t2 = new Thread(()->send(ex));
		final Thread t3 = new Thread(()->send(ex));
		final Thread r = new Thread(()->receive(ex));

		t1.start();
		t2.start();
		t3.start();
		r.start();
	}


	private static void send(final Exchanger<Integer> ex) {
		try {
			for (int value : new int[] {1,2,3,4,5,6,7,8,9,10}) {
				synchronized(ex) {
					ex.exchange(value);
				}
			}
			synchronized(ex) {
				ex.exchange(null);
			}
		} catch (InterruptedException e) {
		}
	}

	private static void receive(final Exchanger<Integer> ex) {
		int count = 3;
		int sum = 0;
		
		try {
			while (count > 0) {
				final Integer got = ex.exchange(null);
				
				if (got == null) {
					count--;
				}
				else  {
					sum += got;
				}
			}
			System.err.println("Sum="+sum);
		} catch (InterruptedException e) {
		} 
	}
}
