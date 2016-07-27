package code.main.thread;


/**
 * 多个线程共享数据
 * @author wangzhf
 *
 */
public class MultiThreadShareDataTest {
	public static void main(String[] args) {
		final ShareData1 data1 = new ShareData1();
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				data1.increment();
			}
		}).start();
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				data1.decrement();
			}
		}).start();
	}
}

class ShareData1{
	private int j = 0;
	public synchronized void increment(){
		j++;
	}
	public synchronized void decrement(){
		j--;
	}
}
