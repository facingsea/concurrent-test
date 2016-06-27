package code.main.thread;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 手动写一个缓存：里面可以放多个对象，缓存起来
 * @author wangzhf
 *
 */
public class CacheDemo {
	
	private static Map<String, Object> cache = new HashMap<String, Object>();
	
	private ReadWriteLock lock = new ReentrantReadWriteLock();
	
	public synchronized Object getData(String key){
		Object obj = null;
		lock.readLock().lock();
		try{
			obj = cache.get(key);
			if(obj == null ){
				lock.readLock().unlock();
				lock.writeLock().lock();
				try{
					if(obj == null){
						obj = "aaa" + new Random().nextInt(); //实际代码是应该去访问数据库去查找相应的对象
					}
				} finally {
					lock.writeLock().unlock();
					lock.readLock().lock();
				}
			}
		} finally {
			lock.readLock().unlock();
		}
		return obj;
	}

}
