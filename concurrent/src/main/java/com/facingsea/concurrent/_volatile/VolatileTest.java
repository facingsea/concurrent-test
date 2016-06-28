package com.facingsea.concurrent._volatile;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 在jvm中，一个线程更新了共享变量i，另外一个线程立即去读取共享区中的i时，读到的可能不是刚才另外那个线程更新过的结果，
 * 这就类似数据库中的事务隔离级别中的read uncommited，volatile就是解决这个问题的
 * 
 * @author wangzhf
 *
 */
public class VolatileTest {
	
	private static volatile int i = 0;
	
	public static void main(String[] args) {
		ExecutorService service = Executors.newCachedThreadPool();
		service.execute(new RunThread(1));
		service.execute(new RunThread(2));
	}
	
	static class RunThread implements Runnable{
		
		private int count ;
		
		public RunThread(int count) {
			this.count = count;
		}

		@Override
		public void run() {
			i++;
			System.out.println(count + " " + i);
		}
		
	}
	
}


