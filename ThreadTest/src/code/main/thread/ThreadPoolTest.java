package code.main.thread;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadPoolTest {

	public static void main(String[] args) {
		//创建指定个数的线程池，一旦创建，线程的个数就固定了
		//ExecutorService threadPool = Executors.newFixedThreadPool(3);
		//创建可变的线程池
		//ExecutorService threadPool = Executors.newCachedThreadPool();
		//创建单个线程，只有一个线程
		ExecutorService threadPool = Executors.newSingleThreadExecutor();
		for(int i=0; i<10; i++){
			final int task = i;
			threadPool.execute(new Runnable() {
				
				@Override
				public void run() {
					for(int i=0; i<10; i++){
						try {
							Thread.sleep(200);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						System.out.println(Thread.currentThread().getName() + " is looping of " + i + " in task of " + task);
					}
				}
			});
		}
		System.out.println("All of the tasks have commited!");
		//threadPool.shutdown();
		
		//
		Executors.newScheduledThreadPool(3).scheduleAtFixedRate(new Runnable() {
			
			@Override
			public void run() {
				System.out.println("Bombing !");
			}
		}, 
		6, //初始化时延迟时间 
		2, //启动后间隔执行时间
		TimeUnit.SECONDS //单位：秒
		);
	}
}
