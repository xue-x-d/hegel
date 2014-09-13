package com.shomop.common.cache;


/**
 * cache
 * @author spencer.xue
 * @date 2014-7-30
 */
public interface Cache<K,V> {

	V get(K key) throws CacheException;

	void put(K key, V value) throws CacheException;

	void put(K key, V value, int TTL) throws CacheException;

	void update(K key, V value) throws CacheException;
	
	long size(K key);

	void remove(K key) throws CacheException;
 
}