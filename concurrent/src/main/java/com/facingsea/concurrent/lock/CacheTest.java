package com.facingsea.concurrent.lock;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 使用Lock实现对象缓存
 * @author wangzhf
 *
 */
public class CacheTest {

	private static Map<String, Object> cache = new HashMap<String, Object>();
	
	private ReadWriteLock lock = new ReentrantReadWriteLock();
	
	public static void main(String[] args) {
		CacheTest ct = new CacheTest();
		for (int i = 0; i < 3; i++) {
			Object obj = ct.getData("aa" + i);
			System.out.println(obj.toString());
		}
		for (int i = 0; i < 3; i++) {
			Object obj = ct.getData("aa" + i);
			System.out.println(obj.toString());
		}
	}
	
	public Object getData(String key){
		Object obj = null;
		lock.readLock().lock();
		try {
			obj = cache.get(key);
			if(obj == null){
				System.out.println("no value found.");
				lock.readLock().unlock();
				lock.writeLock().lock();
				try {
					if(obj == null ){
						obj = "cache " + new Random().nextInt();
						cache.put(key, obj); // cache
					}
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					lock.writeLock().unlock();
					lock.readLock().lock();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			lock.readLock().unlock();
		}
		
		return obj;
	}
}
