package test.memorycollections;

import java.rmi.NotBoundException;
import java.util.*;

import org.javatuples.Pair;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

public class ConnectionProvider {
    private List<JedisShardInfo> shardsHosts;
    private List<JedisShardInfo> veryBusyHosts = new ArrayList<JedisShardInfo>();
    //ShardedJedis shardedJedis;

    public ConnectionProvider(List<Pair<String, Integer>> h) {
        
    }

    public void init(List<Pair<String, Integer>> pairHosts) throws Exception {
        if (shardsHosts.size() != 0){
            throw new Exception("Already initialized");
        }
        for (Pair<String,Integer> pair : pairHosts) {
            shardsHosts.add(new JedisShardInfo(pair.getValue0(), pair.getValue1()));
        }
        //shardedJedis = new ShardedJedis(shardsHosts, ShardedJedis.DEFAULT_KEY_TAG_PATTERN);
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        ShardedJedisPool pool = new ShardedJedisPool(jedisPoolConfig, shardsHosts);

    }

    // public Map<String,Integer> createMap(){
    //     return new MemoryMap(shardedJedis);
    // }



    public Jedis getHost() throws Exception {
        for (JedisShardInfo host : shardsHosts) {
            if(veryBusyHosts.contains(host))
                continue;
            else
            {
                veryBusyHosts.add(host);
                return new Jedis(host.getHost(), host.getPort());
            }
        }
        throw new Exception("All hosts are busy");
    }

    

    public boolean clean(JedisShardInfo host){
        return veryBusyHosts.remove(host);
    }
    
}
