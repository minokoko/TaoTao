package com.taotao.rest.component;

public interface JedisClient {

	public String set(String key,String value);
	public String get(String key);
	public Long hset(String key,String item,String value);
	public String hget(String key,String item);
	public Long hdel(String key,String item);
	//自增1
	public Long incr(String key);
	//自减1
	public Long decr(String key);
	public Long expire(String key,int second);
	public Long ttl(String key);
	
	
}
