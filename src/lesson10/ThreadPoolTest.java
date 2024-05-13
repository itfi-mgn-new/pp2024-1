package lesson10;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

public class ThreadPoolTest {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		final ExecutorService executor = Executors.newSingleThreadExecutor();
		final Callable<String> c = new Callable<String>() {
									@Override
									public String call() throws Exception {
//										throw new NullPointerException();
										System.err.println("sdsdsd");
										return "vassya";
									}
								};
		
		executor.execute(()->{System.err.println("sxsdsd  1 ");});
		executor.execute(()->{System.err.println("sxsdsd  2 ");});
		executor.execute(()->{System.err.println("sxsdsd  3 ");});
		
		final Future<String> f = executor.submit(c);
			
		System.err.println("DO SOMETHING");
		try {
			final String result = f.get();
			
			System.err.println("REsult="+result);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		
		executor.shutdown();
		
		ScheduledExecutorService maintener = Executors.newScheduledThreadPool(1);
	
		maintener.scheduleAtFixedRate(
				()->{System.err.println("sdsd");}, 
				1000, 500, TimeUnit.MILLISECONDS);

		System.err.println("THE END");
		Thread.sleep(10000);
		maintener.shutdown();
		
		Timer	t = new Timer(true);
		
		TimerTask tt = new TimerTask() {
			@Override
			public void run() {
				System.err.println("TT run");
			}
		};
		
		t.schedule(tt, 1000);
		t.schedule(tt, 1000, 500);
		t.scheduleAtFixedRate(tt, 1000, 500);
	}
}
