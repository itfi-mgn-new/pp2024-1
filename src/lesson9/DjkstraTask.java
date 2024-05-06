package lesson9;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;


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
		Thread	t5 = new Thread(()->process(stick5, stick1));
		
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
		try {
			try(final SetOfStick sos = Officiant.getSticks(left, right)) {
				System.err.println("Thread "+Thread.currentThread().getName()+" eating...");
				Thread.sleep(1000);
			}
		} catch (InterruptedException e) {
		}					// right.monitorexit
	}
	
	public static void think() {
		System.err.println("Thread "+Thread.currentThread().getName()+" thinking...");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
		}
	}
	
	public static interface SetOfStick extends AutoCloseable {
		@Override
		void close() throws RuntimeException;
	}
}


class Officiant {
	private static final Thread	t = new Thread(()->process());
	private static final BlockingQueue<Request> q = new ArrayBlockingQueue<>(10);
	
	static {
		t.setDaemon(true);
		t.start();
	}
	
	static DjkstraTask.SetOfStick getSticks(final Object left, final Object right) throws InterruptedException {
		final Request rq = new Request(RequestType.GET, left, right);
		
		q.put(rq);
		rq.latch.await();
		
		return new DjkstraTask.SetOfStick() {
			@Override
			public void close() throws RuntimeException {
				final Request rq = new Request(RequestType.FREE, left, right);
				try {
					q.put(rq);
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			}
		};
	}
	
	private static void process() {
		final Set<Object> allocated = new HashSet<>();
		final List<Request> awaited = new ArrayList<>();
		
		for(;;) {
			try {
				final Request rq = q.take();
				
				switch (rq.type) {
					case FREE:
						allocated.remove(rq.left);
						allocated.remove(rq.right);
						break;
					case GET:
						awaited.add(0, rq);
						break;
					default:
						break;
				}
				for(int index = awaited.size()-1; index >=0; index--) {
					final Request current = awaited.get(index);
					
					if (!allocated.contains(current.left) && !allocated.contains(current.right)) {
						allocated.add(current.left);
						allocated.add(current.right);
						awaited.remove(index).latch.countDown();
					}
				}
			} catch (InterruptedException e) {
				break;
			}
			
		}
	}
	
	private static enum RequestType {
		GET,
		FREE
	}
	
	private static class Request {
		private final RequestType type;
		private final Object left;
		private final Object right;
		private final CountDownLatch latch = new CountDownLatch(1);
		
		public Request(final RequestType type, final Object left, final Object right) {
			super();
			this.type = type;
			this.left = left;
			this.right = right;
		}
	}
}