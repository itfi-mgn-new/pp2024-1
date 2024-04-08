package lesson6;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;

public class Example {

	public static void main(String[] args) throws InterruptedException {
		final BlockingQueue<Request> queue = new ArrayBlockingQueue<>(10);
		
		final Thread r = new Thread(()->receive(queue));
		final Thread t1 = new Thread(()->transmit(queue));
		final Thread t2 = new Thread(()->transmit(queue));
		final Thread t3 = new Thread(()->transmit(queue));
		
		t1.start();
		t2.start();
		t3.start();
		r.start();
		
		t1.join();
		t2.join();
		t3.join();
		
		queue.put(new Request(-1));
	}
	
	
	private static void transmit(final BlockingQueue<Request> queue) {
		for(int index = 0; index < 1000; index++) {
			final Request	rq = new Request(Math.random());
			
			try {
				queue.put(rq);
				// TODO something
				rq.latch.await();
				System.err.println(rq.result);
			} catch (InterruptedException e) {
				break;
			}
		}
	}


	private static void receive(final BlockingQueue<Request> queue) {
		while (!Thread.interrupted()) {
			try {
				final Request 	rq = queue.take();
				
				if (rq.source < 0) {
					
					break;
				}
				else {
					rq.result = rq.source * rq.source;
					rq.latch.countDown();
				}
			} catch (InterruptedException e) {
				break;
			}
		}
	}


	private static class Request {
		final double			source;
		volatile double			result;
		final CountDownLatch	latch = new CountDownLatch(1);
		
		public Request(double source) {
			this.source = source;
		}

		@Override
		public String toString() {
			return "Request [source=" + source + ", result=" + result + ", latch=" + latch + "]";
		}
	}

}
