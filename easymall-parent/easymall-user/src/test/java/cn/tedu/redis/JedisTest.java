package cn.tedu.redis;

import org.junit.Test;
import redis.clients.jedis.*;

import java.util.*;

/**
 * 利用测试注解实现如下测测试代码编写
 * 1. 创建jedis连接redis的客户端对象实现操作redis增删拆改功能
 * 2. 单节点redis基础之上掌握分布式结构
 *    2.1hash取余
 *    2.2一致性hash
 * 3.连接池
 */
public class JedisTest {
    //创建一个jedis对象,提供远程访问的参数 host port端口
    @Test
    public void test01(){
        Jedis jedis=new Jedis("10.9.104.184",6380);
        //只要提供的连接是有效的,就可以调用jedis的api方法实现增删拆改等等操作
        //写一个字符串 String
        jedis.set("name","王老师");
        //读一个数据
        String name = jedis.get("name");
        System.out.println("读取数据name:"+name);
        //hash类型
        jedis.hset("user","age","18");
        //读hash类型
        String user_age = jedis.hget("user", "age");
        System.out.println("拿到user属性age:"+user_age);
    }

    //对于任意一个key值做hash取余公式计算
    @Test
    public void test02(){
        int a=-4858;
        System.out.println("二进制:"+Integer.toBinaryString(a));
        /*String key= UUID.randomUUID().toString();
        System.out.println("得到key值:"+key);
        int hashCode = key.hashCode();
        System.out.println("该key对应hashCode:"+hashCode);
        int andResult=hashCode&Integer.MAX_VALUE;
        System.out.println("31位保真运算结果:"+andResult);*/
    }
    //对应redis集群6379 6380 6381做hash取余散列计算数据分片
    @Test
    public void test(){
        //假设这是系统生成的key-value数据
        String key="ahahahahahaha";
        String value="wangcuihua";
        //写数据之前,先准备节点所有信息
        List<Jedis> nodes=new ArrayList<>();
        nodes.add(new Jedis("10.9.104.184",6379));
        nodes.add(new Jedis("10.9.104.184",6380));
        nodes.add(new Jedis("10.9.104.184",6381));
        MyShardJedis jedis=new MyShardJedis(nodes);
        for(int i=0;i<100;i++){
            key=UUID.randomUUID().toString();
            value="value"+i;
            jedis.set(key,value);
            System.out.println(jedis.get(key));
        }
    }

    //jedis的分片对象
    @Test
    public void test03(){
        //创建一个ShardedJedis 需要先收集一下所有节点信息
        List<JedisShardInfo> infos=new ArrayList<>();
        infos.add(new JedisShardInfo("10.9.104.184",9000,1000,1000,5));
        infos.add(new JedisShardInfo("10.9.104.184",6380));
        infos.add(new JedisShardInfo("10.9.104.184",6381));
        //利用这个list创建分片对象
        ShardedJedis sJedis=new ShardedJedis(infos);
        for(int i=0;i<100;i++){
            String key=UUID.randomUUID().toString();
            sJedis.set(key,"");
        }
    }
    //连接池的使用
    @Test
    public void test04(){
        //每个jedis都可以交给连接池管理
        JedisPool pool=new JedisPool("10.9.104.184",9000);
        //可以从连接池中获取连接资源jedis
        Jedis jedis = pool.getResource();
        //操作api
        jedis.set("a","daf");
        pool.returnResource(jedis);
        //还提供一个分片连接池,获取的对象是ShardedJedis
        //收集节点信息
        List<JedisShardInfo> infos=new ArrayList<>();
        infos.add(new JedisShardInfo("10.9.104.184",9000,1000,1000,5));
        infos.add(new JedisShardInfo("10.9.104.184",6380));
        infos.add(new JedisShardInfo("10.9.104.184",6381));
        //创建分片连接池
        ShardedJedisPool spool=new ShardedJedisPool(new JedisPoolConfig(),infos);
        //获取分片连接池资源
        ShardedJedis resource = spool.getResource();
        spool.returnResource(resource);
    }

    //连接哨兵,实现对一个高可用redis集群操作的功能
    @Test
    public void test05(){
        //代码将不再直接连接任意一个redis 6382 6383 6384 需要连接哨兵
        //收集哨兵连接信息 26379 26380 26381
        Set<String> infos=new HashSet<>();
        infos.add(new HostAndPort("10.9.104.184",26379).toString());
        infos.add(new HostAndPort("10.9.104.184",26380).toString());
        infos.add(new HostAndPort("10.9.104.184",26381).toString());
        //通过哨兵信息,封装一个哨兵连接池
        //需要两个参数,一个哨兵集群可以管理多个主从,每个主从名字不同,这里需要指定
        //哨兵管理的主从名称 mymaster
        JedisSentinelPool pool=new JedisSentinelPool("mymaster",infos);
        //从哨兵连接池中就能获取jedis连接对象 当前主节点,还能获取主从一些信息
        System.out.println("当前主节点:"+pool.getCurrentHostMaster());
        Jedis jedisMaster = pool.getResource();
        jedisMaster.set("name","aldjflads");
        System.out.println("读取name:"+jedisMaster.get("name"));
    }

    //连接集群
    @Test
    public void test06(){
        //8000 8001 8002 8003 8004 8005,在代码中只需要至少传递一个节点信息
        //就可以连接到集群
        //收集可用的节点信息
        Set<HostAndPort> infos=new HashSet<>();
        infos.add(new HostAndPort("10.9.104.184",8000));
        //构造jedisCluster
        JedisCluster cluster=new JedisCluster(infos);
        /*for(int i=0;i<100;i++){
            cluster.set("key:"+i,"");
        }*/
        cluster.set("name","王老师");
        System.out.println(cluster.get("name"));
    }
}
