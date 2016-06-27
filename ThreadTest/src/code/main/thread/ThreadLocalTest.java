package code.main.thread;

import java.util.Random;

public class ThreadLocalTest {
	
	private static ThreadLocal<Integer> data = new ThreadLocal<Integer>();
	
	public static void main(String[] args) {
		for(int i=0; i<2; i++){
			new Thread(new Runnable() {
				
				public void run() {
					int val = new Random().nextInt();
					System.out.println(Thread.currentThread().getName() + "..." + val);
					data.set(val);
					new A().get();
					new B().get();
				}
			}).start();
		}
	}
	
	static class A{
		public void get(){
			int val = data.get();
			System.out.println(Thread.currentThread().getName() + "===> " + val);
		}
	}
	
	static class B{
		public void get(){
			int val = data.get();
			System.out.println(Thread.currentThread().getName() + "===> " + val);
		}
	}

}
