package code.main.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;


/**
 *使用阻塞队列实现
 *原理：依靠queue的阻塞方法，当queue1有值时，queue1的put方法将阻塞
 *
 * 线程通信
 * 子线程执行10次。主线程执行100次，如此循环50次
 * @author wangzhf
 *
 */
public class BlockingQueueCommunitationTest {
	
	public static void main(String[] args) {
		new BlockingQueueCommunitationTest().init();
	}
	
	private void init(){
		final Business b = new Business();
		new Thread(new Runnable(){
			public void run() {
				for(int i=0; i<50; i++){
					b.sub(i);
				}
			}
		}).start();
		
		for(int i=0; i<50; i++){
			b.main(i);
		}
	}
	
	private class Business{
		
		BlockingQueue<Integer> queue1 = new ArrayBlockingQueue<Integer>(1);
		BlockingQueue<Integer> queue2 = new ArrayBlockingQueue<Integer>(1);
		
		//匿名构造，执行在当前类的构造方法之前
		{
			try {
				queue2.put(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		public void sub(int i){
			try {
				queue1.put(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			for(int j=0; j<10; j++){
				System.out.println("sub loop is " + j + " in total " + i);
			}
			try {
				queue2.take();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		public void main(int i){
			try {
				queue2.put(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			for(int j=0; j<100; j++){
				System.out.println("Main loop is " + j + " in total " + i);
			}
			try {
				queue1.take();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
