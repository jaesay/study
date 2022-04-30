package study;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockTests {

	public static void main(String[] args) {
		new ReadWriteLockTests().test1();
	}
	
	private void test1() {
		ExecutorService executor = Executors.newFixedThreadPool(200);
		Map<String, String> map = new HashMap<>();
		ReentrantLock lock1 = new ReentrantLock();

		executor.submit(() -> {
		    lock1.lock();
		    try {
		        Thread.sleep(2*1000);
		        map.put("foo", "bar");
		    } catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
		        lock1.unlock();
		    }
		});
		
		Runnable readTask = () -> {
			    lock1.lock();
			    try {
			        System.out.println(map.get("foo"));
		        Thread.sleep(2*1000);
		    } catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
		        lock1.unlock();
		    }
		};
		
		for(int i=0; i<100; i++) {
			executor.submit(readTask);
		}
		
		//executor.shutdown();
	}

	void test() {
		ExecutorService executor = Executors.newFixedThreadPool(200);
		Map<String, String> map = new HashMap<>();
		ReadWriteLock lock = new ReentrantReadWriteLock();

		executor.submit(() -> {
		    lock.writeLock().lock();
		    try {
		        Thread.sleep(2*1000);
		        map.put("foo", "bar");
		    } catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
		        lock.writeLock().unlock();
		    }
		});
		
		Runnable readTask = () -> {
			    lock.readLock().lock();
			    try {
			        System.out.println(map.get("foo"));
		        Thread.sleep(2*1000);
		    } catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
		        lock.readLock().unlock();
		    }
		};
		
		for(int i=0; i<100; i++) {
			executor.submit(readTask);
		}
		
		executor.shutdown();
		//stop(executor);
	}
	
	
}
