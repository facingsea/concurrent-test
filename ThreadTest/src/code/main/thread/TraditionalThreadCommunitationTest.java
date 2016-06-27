package code.main.thread;

/**
 * 线程通信
 * 子线程执行10次。主线程执行100次，如此循环50次
 * @author wangzhf
 *
 */
public class TraditionalThreadCommunitationTest {
	
	public static void main(String[] args) {
		new TraditionalThreadCommunitationTest().init();
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
		
		public synchronized void sub(int i){
			while (!isShouldSub){
				try {
					this.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			for(int j=0; j<10; j++){
				System.out.println("sub loop is " + j + " in total " + i);
			}
			isShouldSub = false;
			this.notify();
		}
		
		public synchronized void main(int i){
			while (isShouldSub){
				try {
					this.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			for(int j=0; j<100; j++){
				System.out.println("Main loop is " + j + " in total " + i);
			}
			isShouldSub = true;
			this.notify();
		}
	}
}
