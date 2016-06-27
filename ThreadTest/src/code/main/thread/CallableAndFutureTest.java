package code.main.thread;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 测试Callable和Futrue
 * @author wangzhf
 *
 */
public class CallableAndFutureTest {

	public static void main(String[] args) {
		ExecutorService threadPool = Executors.newSingleThreadExecutor();
		Future<String> future = threadPool.submit(new Callable<String>() {

			@Override
			public String call() throws Exception {
				System.out.println(Thread.currentThread().getName() + " is worked....");
				return "Hello";
			}
		});
		try {
			System.out.println(future.get());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		
		//提交一组callable任务，并批量获取结果
		ExecutorService threadPool2 = Executors.newFixedThreadPool(10);
		CompletionService<Integer> completionService = new ExecutorCompletionService<Integer>(threadPool2);
		for(int i=0; i<10; i++){
			final int sequence = i;
			completionService.submit(new Callable<Integer>() {
	
				@Override
				public Integer call() throws Exception {
					System.out.println(Thread.currentThread().getName() + "is worked...");
					Thread.sleep(new Random().nextInt(5000));
					return sequence;
				}
			});
		}
		
		//获取执行结果
		for(int i =0; i<10; i++){
			try {
				System.out.println(completionService.take().get());
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
	}
}
