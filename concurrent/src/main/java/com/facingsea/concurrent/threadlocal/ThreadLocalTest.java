package com.facingsea.concurrent.threadlocal;

import java.util.Random;

/**
 * ThreadLocal测试
 * @author wangzhf
 *
 */
public class ThreadLocalTest {
	
	private static ThreadLocal<Integer> local = new ThreadLocal<Integer>();
	
	public static void main(String[] args) {
		for (int i = 0; i < 3; i++) {
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					int val = new Random().nextInt();
					local.set(val);
					new A().get();
					new B().get();
				}
			}).start();
		}
	}
	
	static class A{
		public void get(){
			int val = local.get();
			System.out.println("A get : " + val);
		}
	}

	static class B{
		public void get(){
			int val = local.get();
			System.out.println("B get : " + val);
		}
	}
}
