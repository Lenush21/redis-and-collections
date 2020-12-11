package test.memorycollections;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.ArrayList;
import java.util.Arrays;
import redis.clients.jedis.Jedis;
import java.util.Objects;

public class MemorySet implements Set<String> {
    private Jedis jedis;
    private ArrayList<String> keys = new ArrayList<String>();
    private int lastIndex;
    private String getIndex(){
        keys.add(String.valueOf(lastIndex));
        return String.valueOf(lastIndex++);
    }

    public void establishConnection(String hostname, int port) {
        jedis = new Jedis(hostname, port);
        if (jedis.isConnected() == false) {
            System.out.println("Jedis Connection Exception");
        } else {
            System.out.println("Connection is successfully established");
        }
    }

    public void closeConnection() {
        jedis.quit();
        jedis.close();
    }

    @Override
    public boolean add(String e) {
        if (e == null) {
            throw new NullPointerException();
        }
        if (!(e instanceof String)) {
            throw new IllegalArgumentException();
        }
        if (!this.contains(e)) {
            jedis.set(getIndex(), e);
            return true;
        }
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends String> c) {
        boolean flag = false;
        if (c.size() == 0) {
            throw new NullPointerException();
        }
        Iterator<? extends String> it = c.iterator();
        while (it.hasNext()) {
            if (!(it.next() instanceof String)) {
                throw new IllegalArgumentException();
            }
            if (!this.contains(it.next())) {
                this.add(it.next());
                flag = true;
            }
        }
        return flag;
    }

    @Override
    public boolean contains(Object o) {
        if (o == null) {
            throw new NullPointerException();
        }
        Iterator<String> iter = this.iterator();
        while (iter.hasNext()) {
            if (o.equals(iter.next())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        boolean flag = false;
        if (c.size() == 0) {
            throw new NullPointerException();
        }
        Iterator<?> iter = c.iterator();
        while (iter.hasNext()) {
            if (!jedis.exists(String.valueOf(iter.next()))) {
                flag = true;
            }
        }
        return flag;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof MemorySet)) {
            return false;
        }
        MemorySet memorySet = (MemorySet) o;
        return Objects.equals(jedis, memorySet.jedis);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(jedis);
    }

    @Override
    public void clear() {
        jedis.flushDB();
    }

    @Override
    public boolean remove(Object o) {
        if (o == null) {
            throw new NullPointerException();
        }
        for (String k : keys) {
            if (jedis.get(k).equals(o)){
                jedis.del(k);
                keys.remove(k);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean flag = false;
        if (c.size() == 0) {
            throw new NullPointerException();
        }
        for (Iterator<?> i = c.iterator(); i.hasNext();) {
            var next = i.next();
            if (this.contains(next)) {
                remove(next);
                flag = true;
            }
        }
        return flag;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean flag = false;
        if (c.size() == 0) {
            throw new NullPointerException();
        }
        for (int i = 0; i < size(); i++) {
            if (!c.contains(jedis.get(String.valueOf(i)))) {
                jedis.del(String.valueOf(i));
                flag = true;
            }
        }
        return flag;
    }

    @Override
    public int size() {
        return jedis.dbSize().intValue();
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
    public Object[] toArray() {
        Object[] objs = new Object[size()];
        var i =0;
        for (String k : keys) {
            objs[i++]=jedis.get(k);
        }
        return objs;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        if (a.length == 0) {
            throw new NullPointerException();
        }
        var i =0;
        for (String kString : keys) {
            a[i] = (T)jedis.get(kString);
            if(i++ == a.length -1)
                return a;
        }
        return a;
    }

    @Override
    public Iterator<String> iterator() {
        Iterator<String> it = new Iterator<String>() {

            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < size();
            }

            @Override
            public String next() {
                //String currentIndexString = String.valueOf(currentIndex++);
                return jedis.get(keys.get(currentIndex++));
            }

            @Override
            public void remove() {
                var key = keys.get(currentIndex);
                jedis.del(key);
                keys.remove(currentIndex);
            }
        };
        return it;
    }

}
