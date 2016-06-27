package com.facingsea.concurrent.blockingqueue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;


/**
 * 基于链表的阻塞队列，同ArrayListBlockingQueue类似，其内部也维持着一个数据缓冲队列（该队列由一个链表构成），
 * 当生产者往队列中放入一个数据时，队列会从生产者手中获取数据，并缓存在队列内部，而生产者立即返回；
 * 只有当队列缓冲区达到最大值缓存容量时（LinkedBlockingQueue可以通过构造函数指定该值），才会阻塞生产者队列，
 * 直到消费者从队列中消费掉一份数据，生产者线程会被唤醒，反之对于消费者这端的处理也基于同样的原理。
 * 而LinkedBlockingQueue之所以能够高效的处理并发数据，还因为其对于生产者端和消费者端分别采用了独立的锁来控制数据同步，
 * 这也意味着在高并发的情况下生产者和消费者可以并行地操作队列中的数据，以此来提高整个队列的并发性能。
 * 作为开发者，我们需要注意的是，如果构造一个LinkedBlockingQueue对象，而没有指定其容量大小，
 * LinkedBlockingQueue会默认一个类似无限大小的容量（Integer.MAX_VALUE），这样的话，如果生产者的速度一旦大于消费者的速度，
 * 也许还没有等到队列满阻塞产生，系统内存就有可能已被消耗殆尽了。
 * 
 * @author wangzhf
 *
 */
public class LinkedBlockingQueueTest {
	
	public static void main(String[] args) {
		BlockingQueue<Integer> queue = new LinkedBlockingQueue<Integer>();
		
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
