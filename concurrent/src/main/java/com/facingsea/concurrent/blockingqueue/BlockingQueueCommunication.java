package com.facingsea.concurrent.blockingqueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * 利用BlockingQueue阻塞的原理实现主线程与子线程交替执行
 * 原理：
 * 	1. 创建两个容量为1的blockingQueue为q1、q2，q1代表主线程，q2代表子线程
 *  2. 初始化时往q2中放入数据，使其阻塞，让q1先执行
 * 
 * @author wangzhf
 *
 */
public class BlockingQueueCommunication {

	public static void main(String[] args) {
		new BlockingQueueCommunication().run();
	}

	private void run(){
		final Work work = new Work();
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				for (int i = 0; i < 10; i++) {
					work.subThreadRun(i);
				}
			}
		}).start();
		
		// main
		for (int i = 0; i < 10; i++) {
			work.mainThreadRun(i);
		}
	}
	
	private class Work{
		
		private BlockingQueue<Integer> q1 = new ArrayBlockingQueue<Integer>(1);
		private BlockingQueue<Integer> q2 = new ArrayBlockingQueue<Integer>(1);
		
		//匿名构造，执行在当前类的构造方法之前
		{
			try {
				q2.put(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		public void mainThreadRun(int times){
			try {
				q2.put(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			//q2已有数据，子线程阻塞，主线程执行
			for(int j=0; j<10; j++){
				System.out.println("Main loop is " + j + " in total " + times);
			}
			// 主线程执行完，
			try {
				q1.take();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		public void subThreadRun(int times){
			try {
				q1.put(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			//q2已有数据，主线程阻塞，子线程执行
			for(int j=0; j<10; j++){
				System.out.println("Sub loop is " + j + " in total " + times);
			}
			// 子线程执行完毕，主线程执行
			try {
				q2.take();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
}

