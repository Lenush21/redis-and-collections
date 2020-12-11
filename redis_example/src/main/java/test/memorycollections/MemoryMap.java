package test.memorycollections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import redis.clients.jedis.Jedis;
import java.util.List;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;
import static redis.clients.jedis.ScanParams.SCAN_POINTER_START;
import java.util.AbstractMap;


public class MemoryMap implements Map<String, Integer> {

    private Jedis jedis;

    public void establishConnection(String hostname, int port) {
            jedis = new Jedis(hostname, port);
            if (jedis.isConnected() == false){
                System.out.println("Jedis Connection Exception");
            } else{
            System.out.println("Connection is successfully established");
            }
    }

    public void closeConnection() {
        jedis.quit();
        jedis.close();
    }

    public MemoryMap(){
        
    }

    @Override
    public int size() {
        return jedis.dbSize().intValue();
    }

    @Override
    public Integer remove(Object key) {
        String keyString;
        int result;
        try {
            keyString = key.toString();
        } catch (ClassCastException e) {
            throw new ClassCastException();
        }
        try {
            if (jedis.exists(keyString)) {
                result = Integer.parseInt(jedis.get(keyString));
                jedis.del(keyString);
            } else {
                return null;
            }
        } catch (NullPointerException e) {
            throw new NullPointerException();
        }
        return result;
    }

    @Override
    public Integer put(String key, Integer value) {
        int prevValue;
        String val;
        try {
            val = String.valueOf(value);

        } catch (ClassCastException e) {
            throw new ClassCastException();
        }
        try {
            if (jedis.exists(key)) {
                prevValue = Integer.parseInt(jedis.get(key));
            } else {
                jedis.set(key, val);
                return null;
            }
            jedis.set(key, val);

        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException();
        } catch (NullPointerException exception){
            throw new NullPointerException();
        }
        return prevValue;
    }

    @Override
    public boolean isEmpty() {
        if (size() > 0) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public Integer get(Object key) {
        String keyString;
        int result;
        try {
            keyString = String.valueOf(key);
        } catch (ClassCastException e) {
            throw new ClassCastException();
        }
        
        if (jedis.exists(keyString)) {
            result = Integer.valueOf(jedis.get(keyString));
            return result;
        } else {
            System.out.println("Couldn't find the key");
            return null;
        }
    }

    @Override
    public Collection<Integer> values() {
        List<Integer> listVal = new ArrayList<Integer>();
        List<String> keys = List.copyOf(keySet());
        for (int i = 0; i < keys.size(); i++){
            int tmp = Integer.parseInt(jedis.get(keys.get(i)));
            listVal.add(tmp);
        }
        return listVal;
    }


    @Override
    public Set<String> keySet() {
        ScanParams params = new ScanParams();
        params.match("*");
        String cur = SCAN_POINTER_START;
        boolean cycleIsFinished = false;
        while (!cycleIsFinished) {
            ScanResult<String> scanResult = jedis.scan(cur, params);
            List<String> result = scanResult.getResult();
            Set<String> resultSet = Set.copyOf(result);
            
            cur = scanResult.getCursor();
            
            if (cur.equals("0")) {
                cycleIsFinished = true;
                return resultSet;
            }
        }
        return null;
    }

    @Override
    public boolean containsValue(Object value) {
        try{
        for (Integer s : values())
            if (s == value){
                return true;
            } 
        return false;
    } catch (NullPointerException e){
        throw new NullPointerException();
        }
    }   

    @Override
    public boolean containsKey(Object key) {
        try{
        String keyString = String.valueOf(key);
        if (jedis.exists(keyString) == true){
            return true;
        } else {
        return false;
        }} catch (ClassCastException e){
            throw new ClassCastException();
        } catch (NullPointerException e){
            throw new NullPointerException();
        }
    }

    @Override
    public Set<Entry<String, Integer>> entrySet() {
        List<String> listKey = List.copyOf(keySet());
        List<Integer> listVal = List.copyOf(values());
        Set<Entry<String, Integer>> m = new HashSet<Entry<String, Integer>>();
        for (int i = 0; i < listKey.size(); i++){
            m.add(new AbstractMap.SimpleEntry(listKey.get(i), listVal.get(i)));
            }
        return m;
    }

    @Override
    public void putAll(Map<? extends String, ? extends Integer> m) {
        try{
        for (Map.Entry<? extends String,? extends Integer> entry : m.entrySet()) {
            put(entry.getKey(), entry.getValue());
            }
        } catch (UnsupportedOperationException e){
            throw new UnsupportedOperationException();
        } catch (ClassCastException e){
            throw new ClassCastException();
        } catch (NullPointerException e){

            throw new IllegalArgumentException();
        } catch (IllegalArgumentException e){
            throw new IllegalArgumentException();
        }
    }


    @Override
    public void clear() {
        jedis.flushDB();
    }

}
