package test.memorycollections;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.Arrays;
import redis.clients.jedis.Jedis;
import java.util.Objects;

public class MemorySet implements Set<String> {
    private Jedis jedis;

    @Override
    public boolean add(String e) {
        if (!this.contains(e)){
            jedis.set(e, e);
            return true;
        }
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends String> c) {
        Iterator<? extends String> it = c.iterator();
        while (it.hasNext()){
            if (!this.contains(it.next())){
                this.add(it.next());
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean contains(Object o) {
        Iterator<String> iter = this.iterator();
        while (iter.hasNext()){
            if (o.equals(iter.next())){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        Iterator<?> iter = c.iterator();
        while (iter.hasNext()){
            if (!jedis.exists(String.valueOf(iter.next()))){
                return false;
            }
        }
        return true;
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
        if (this.contains(o)){
            jedis.del(String.valueOf(o));
            return true;
        }
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean flag = false;
        for (Iterator<?> i = c.iterator(); i.hasNext();) {
            if (this.contains(i.next())) {
                jedis.del(String.valueOf(i.next()));
                flag = true;
            }
        }
        return flag;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean flag = false;
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
        for (int i = 0; i < size(); i++) {
            objs[i] = jedis.get(String.valueOf(i));
        }
        return objs;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        Object[] original = toArray();
        T[] result = Arrays.copyOf(a, original.length);
        for (int i = 0; i < original.length; i++) {
            result[i] = (T) original[i];
        }
        return result;
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
                String currentIndexString = String.valueOf(currentIndex++);
                return jedis.get(currentIndexString);
            }
            @Override
            public void remove() {
                //Iterator.super.remove();
                String currentIndexString = String.valueOf(currentIndex);
                jedis.del(currentIndexString);
            }
        };
        return it;
    }

}
