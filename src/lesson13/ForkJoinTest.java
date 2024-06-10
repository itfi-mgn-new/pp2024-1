package lesson13;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ForkJoinTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		final ForkJoinPool	pool = ForkJoinPool.commonPool();
		final int[]			array = new int[16];
		
		Arrays.fill(array, 1);

		System.err.println("Sum="+pool.invoke(new SplitTask(array, 0, 15)));
	}
	
	
	private static class SplitTask extends RecursiveTask<Integer> {
		private static final long serialVersionUID = 1L;
		
		private final int[]	array;
		private final int	from, to;
		
		private SplitTask(final int[] array, final int from, final int to) {
			this.array = array; 
			this.from = from;
			this.to = to;
		}

		@Override
		protected Integer compute() {
			System.err.println("Calc: "+from+"/"+to+" at "+Thread.currentThread().getName());
			if (to<from) {
				System.err.println("Calc result will be 0");
				return 0;
			}
			else if (to == from) {
				System.err.println("Calc result="+array[from]);
				return array[from];
			}
			else {
				final int		mid = (to + from) / 2;
				
				if (mid == from) {
					final SplitTask	right = new SplitTask(array, mid+1, to);
					
					System.err.println("Fork special");
					right.fork();
					return array[mid] + right.join(); 
				}
				else {
					final int		from1 = from, to1 = mid - 1;
					final int		from2 = mid, to2 = to;
					final SplitTask	left = new SplitTask(array, from1, to1); 
					final SplitTask	right = new SplitTask(array, from2, to2);
					
					
					System.err.println("Fork left");
					left.fork();
					System.err.println("Fork right");
					right.fork();
					System.err.println("Join");
					final int		result = left.join() + right.join();
					
					System.err.println("Calc joined result="+array[from]);
					return result;
				}
			}
		}
		
	}

}
