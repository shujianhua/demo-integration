package org.demo.redis.service;

public interface IDemoRedisService {

	/**
	 * 测试加锁
	 */
	void getOneLock();

	/**
	 * 测试释放锁
	 */
	void releaseOneLock();

	/**
	 * 测试多线程竞争锁场景
	 */
	void testLockAndUnlock();

}
