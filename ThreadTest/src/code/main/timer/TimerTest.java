package code.main.timer;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class TimerTest {
	
	static int count = 0;
	
	public static void main(String[] args) {
//		new Timer().schedule(new TimerTask() {
//			
//			public void run() {
//				System.out.println("worked...");
//			}
//		}, 1000, 3000);
		
		//每隔1s输出 每隔2s输出
		class MyTimerTask extends TimerTask{
			public void run() {
				int val = count++ % 2;
				
				System.out.println("bombing!");
				new Timer().schedule(new MyTimerTask(), 1000 + 1000*val);
			}
		}
		
		new Timer().schedule(new MyTimerTask(), 1000);
	}

}
