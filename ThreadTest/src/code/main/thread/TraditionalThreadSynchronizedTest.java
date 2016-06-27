package code.main.thread;

public class TraditionalThreadSynchronizedTest {

	public static void main(String[] args) {
		new TraditionalThreadSynchronizedTest().init();
	}

	private void init() {
		final Output out = new Output();
		new Thread(new Runnable() {

			public void run() {
				while(true){
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					out.out("张无忌");
				}
			}
		}).start();
		new Thread(new Runnable() {

			public void run() {
				while(true){
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					out.out("赵敏");
				}
			}
		}).start();
		
	}
	class Output {
		public void out(String name) {
			int len = name.length();
			synchronized (this) {
				for (int i = 0; i < len; i++) {
					System.out.print(name.charAt(i));
				}
				System.out.println();
			}
		}
	}
}
