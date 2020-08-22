package cn.tedu.redis;

import redis.clients.jedis.Jedis;

import java.util.List;

/*
通过传递redis集群的信息,将jedis的api做二次封装
实现每一个对key-value操作的hash取余,set get
 */
public class MyShardJedis {
    //数据分片信息,所有redis节点对象
    private List<Jedis> nodes;
    public MyShardJedis(List<Jedis> nodes){
        this.nodes=nodes;
    }
    //任何对key-value做读写操作之前需要先计算hash取余
    //通过hash取余找到对应key的jedis连接对象
    //key不变,jedis对象就不变
    public Jedis hashKeys(String key){
        //hash取余结果
        int result=(key.hashCode()&Integer.MAX_VALUE)%nodes.size();
        //假如 nodes.size()3 result=[0,1,2] 对应list下标
        return nodes.get(result);
    }
    //一次重新封装jedis的各种方法 set get
    public void set(String key,String value){
        //先得决定key到底要使用哪个jedis执行set命令
        Jedis jedis = hashKeys(key);
        jedis.set(key,value);
    }
    //读取数据计算
    public String get(String key){
        Jedis jedis = hashKeys(key);
        return jedis.get(key);
    }
    //所有jedis方法重新封装一遍,MyShardJedis就能实现redis集群的分布式计算
}
