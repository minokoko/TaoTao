package com.taotao.test;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import com.taotao.rest.component.JedisClient;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

@Service
public class JedisTest {
	
	
	//单机版测试
	@Test
	public void testJedisSingle() throws Exception{
		//创建一个Jedis对象
		Jedis jedis = new Jedis("192.168.233.131",6379);
		jedis.set("test", "hello jedis");
		String string = jedis.get("test");
		System.out.println(string);
		jedis.close();
	}
	
	//使用连接池
	@Test
	public void testJedisPool() throws Exception{
		JedisPool jedisPool = new JedisPool("192.168.233.131",6379);
		Jedis jedis = jedisPool.getResource();
		
		jedis.set("test2", "hello jedis");
		String string = jedis.get("test2");
		System.out.println(string);
		jedis.close();
		
		
		//系统关闭时，关闭连接池
		jedisPool.close();
	}
	
	//集群版
	@Test
	public void TestJedisCluster(){
		Set<HostAndPort> nodes = new HashSet<HostAndPort>();
		nodes.add(new HostAndPort("192.168.233.131", 7001));
		nodes.add(new HostAndPort("192.168.233.131", 7002));
		nodes.add(new HostAndPort("192.168.233.131", 7003));
		nodes.add(new HostAndPort("192.168.233.131", 7004));
		nodes.add(new HostAndPort("192.168.233.131", 7005));
		nodes.add(new HostAndPort("192.168.233.131", 7006));
		
		//创建集群对象
		//JedisCluster cluster = new JedisCluster(nodes,5000,1000);
		JedisCluster cluster = new JedisCluster(nodes);
		
		cluster.set("cluster", "cluster test");
		
		String valueString = cluster.get("cluster");
		
		System.out.println(valueString);
		
		cluster.close();
		
	}
	
	@Test
	public void testJedisClientSpirng() throws Exception{
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
		JedisClient jedisClient = applicationContext.getBean(JedisClient.class);
		String result = jedisClient.get("test2");
		System.out.println(result);
	}
	
}
