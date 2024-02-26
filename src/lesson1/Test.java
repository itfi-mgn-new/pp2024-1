package lesson1;

public class Test {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		ThreadGroup	tg = new ThreadGroup("main group");
		Thread	t1 = new Thread(tg, ()->{
						while (!Thread.interrupted()) {
							try {
								System.err.println("T1 "+Thread.currentThread().getName());
								Thread.sleep(100);
							} catch (InterruptedException e) {
								break;
							}
						}
					});
		Thread	t2 = new Thread(tg, ()->System.err.println("T2 "+Thread.currentThread().getName()));
		Thread	t3 = new Thread(tg, ()->System.err.println("T3"));
		t1.setName("My thread");
//		t1.setDaemon(true);
		t1.start();
		t2.start();
		t3.start();
		t1.suspend();
		System.err.println("kdfjklsdjglkfj "+Thread.currentThread().getName());
		t1.resume();
		
		Thread.sleep(2000);
		
		tg.interrupt();

//		t1.interrupt();
		t1.join();
		t2.join();
		t3.join();
		System.err.println("kejroreterpotiertpoierioptpoeritopieoptioperitop "+t1.toString());
	}
}
