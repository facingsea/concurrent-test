package code.main.thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 使用Condition实现
 * 线程通信
 * 子线程执行10次。主线程执行100次，如此循环50次
 * @author wangzhf
 *
 */
public class ConditionCommunitationTest {
	
	public static void main(String[] args) {
		new ConditionCommunitationTest().init();
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
		private boolean isShouldSub = true;
		private Lock lock = new ReentrantLock();
		Condition condition = lock.newCondition();
		public void sub(int i){
			lock.lock();
			try{
				//防止虚假唤醒
				while (!isShouldSub){
					try {
						//this.wait();
						condition.await();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				for(int j=0; j<10; j++){
					System.out.println("sub loop is " + j + " in total " + i);
				}
				isShouldSub = false;
				//this.notify();
				condition.signal();
			} finally {
				lock.unlock();
			}
		}
		
		public void main(int i){
			lock.lock();
			try{
				while (isShouldSub){
					try {
						//this.wait();
						condition.await();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				for(int j=0; j<100; j++){
					System.out.println("Main loop is " + j + " in total " + i);
				}
				isShouldSub = true;
				//this.notify();
				condition.signal();
			} finally {
				lock.unlock();
			}
		}
	}
}
