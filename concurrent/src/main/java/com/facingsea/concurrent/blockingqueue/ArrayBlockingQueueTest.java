package com.facingsea.concurrent.blockingqueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * refer: http://wsmajunfeng.iteye.com/blog/1629354
 * 基于数组的阻塞队列实现，在ArrayBlockingQueue内部，维护了一个定长数组，以便缓存队列中的数据对象，
 * 这是一个常用的阻塞队列，除了一个定长数组外，ArrayBlockingQueue内部还保存着两个整形变量，分别标识着队列的头部和尾部在数组中的位置。
 * ArrayBlockingQueue在生产者放入数据和消费者获取数据，都是共用同一个锁对象，由此也意味着两者无法真正并行运行，
 * 这点尤其不同于LinkedBlockingQueue；按照实现原理来分析，ArrayBlockingQueue完全可以采用分离锁，从而实现生产者和消费者操作的完全并行运行。
 * Doug Lea之所以没这样去做，也许是因为ArrayBlockingQueue的数据写入和获取操作已经足够轻巧，
 * 以至于引入独立的锁机制，除了给代码带来额外的复杂性外，其在性能上完全占不到任何便宜。 
 * ArrayBlockingQueue和LinkedBlockingQueue间还有一个明显的不同之处在于，前者在插入或删除元素时不会产生或销毁任何额外的对象实例，
 * 而后者则会生成一个额外的Node对象。这在长时间内需要高效并发地处理大批量数据的系统中，其对于GC的影响还是存在一定的区别。
 * 而在创建ArrayBlockingQueue时，我们还可以控制对象的内部锁是否采用公平锁，默认采用非公平锁。
 * 
 * BlockingQueue的核心方法：
 * 放入数据：
 *　　offer(anObject):表示如果可能的话,将anObject加到BlockingQueue里,即如果BlockingQueue可以容纳,
 *　　　　				则返回true,否则返回false.（本方法不阻塞当前执行方法的线程）
 *　　offer(E o, long timeout, TimeUnit unit),可以设定等待的时间，如果在指定的时间内，还不能往队列中
 *　　　　				加入BlockingQueue，则返回失败。
 *　　put(anObject):把anObject加到BlockingQueue里,如果BlockQueue没有空间,则调用此方法的线程被阻断
 *　　　　				直到BlockingQueue里面有空间再继续.
 * 获取数据：
 *　　poll(time):取走BlockingQueue里排在首位的对象,若不能立即取出,则可以等time参数规定的时间,
 *　　　　			取不到时返回null;
 *　　poll(long timeout, TimeUnit unit)：从BlockingQueue取出一个队首的对象，如果在指定时间内，
 *　　　　			队列一旦有数据可取，则立即返回队列中的数据。否则知道时间超时还没有数据可取，返回失败。
 *　　take():取走BlockingQueue里排在首位的对象,若BlockingQueue为空,阻断进入等待状态直到
 *　　　　			BlockingQueue有新的数据被加入; 
 *　　drainTo():一次性从BlockingQueue获取所有可用的数据对象（还可以指定获取数据的个数）， 
 *　　　　			通过该方法，可以提升获取数据效率；不需要多次分批加锁或释放锁。
 * 
 * @author wangzhf
 *
 */
public class ArrayBlockingQueueTest {
	
	public static void main(String[] args) {
		BlockingQueue<Integer> queue = new ArrayBlockingQueue<Integer>(3);
		
		ExecutorService service = Executors.newCachedThreadPool();
		
		Producer p1 = new Producer(queue, 1);
		Producer p2 = new Producer(queue, 2);
		Producer p3 = new Producer(queue, 3);
		Producer p4 = new Producer(queue, 4);
		
		Customer c1 = new Customer(queue, 1);
		
		service.execute(p1);
		service.execute(p2);
		service.execute(p3);
		service.execute(p4);
		service.execute(c1);
	}
	
}


/**
 * 生产者
 * @author wangzhf
 *
 */
class Producer implements Runnable{
	
	private BlockingQueue<Integer> queue;
	private int id;
	private static AtomicInteger res = new AtomicInteger();
	
	public Producer(BlockingQueue<Integer> queue, int id) {
		this.queue = queue;
		this.id = id;
	}
	
	@Override
	public void run() {
		
		while (true) {
			try {
				Thread.sleep(2000);
				System.out.println("Producer " + id + " start to put data...");
				queue.put(res.incrementAndGet());
				System.out.println("Producer " + id + " put data over.");
			} catch (InterruptedException e) {
				e.printStackTrace();
				break ;
			}
		}
		
	}
}

/**
 * 消费者
 * @author wangzhf
 *
 */
class Customer implements Runnable{
	
	private BlockingQueue<Integer> queue;
	private int id;
	
	public Customer(BlockingQueue<Integer> queue, int id) {
		this.queue = queue;
		this.id = id;
	}
	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(1000);
				System.out.println("Customer " + id + " start to get data...");
				int res = queue.take();
				System.out.println("Customer " + id + " get the res: " + res);
			} catch (InterruptedException e) {
				e.printStackTrace();
				break ;
			}
		}
	}
}
