package test.memorycollections;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import redis.clients.jedis.Jedis;
import java.util.Objects;

public class MemoryList implements List<String> {

    private Jedis jedis;
    private int lasIndex = 0;

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
    public void add(int index, String element) {
        String ind;
        try {
            ind = String.valueOf(index);
        } catch (ClassCastException e) {
            throw new ClassCastException();
        }
        try {
            if (index > lasIndex) {
                throw new IndexOutOfBoundsException();
            }
            jedis.set(ind, element);
        } catch (NullPointerException e) {
            throw new NullPointerException();
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException();
        } catch (IndexOutOfBoundsException e) {
            throw new IndexOutOfBoundsException();
        }
        lasIndex++;
    }

    @Override
    public boolean add(String e) {
        String sizeString;
        if (!this.contains(e)) {
            try {
                sizeString = String.valueOf(size());
                jedis.set(sizeString, e);
                lasIndex++;
                return true;
            } catch (NullPointerException ex) {
                throw new NullPointerException();
            } catch (IllegalArgumentException ex) {
                throw ex;
            } catch (IndexOutOfBoundsException ex) {
                throw new IndexOutOfBoundsException();
            } catch (ClassCastException ex) {
                throw new ClassCastException();
            }
        }
        return false;
    }

    @Override
    public String get(int index) {
        String stringIndex = String.valueOf(index);
        return jedis.get(stringIndex);
    }

    @Override
    public String remove(int index) {
        String elem;
        try {
            elem = get(index);
            jedis.del(String.valueOf(index));
        } catch (UnsupportedOperationException e) {
            throw new UnsupportedOperationException();
        } catch (IndexOutOfBoundsException e) {
            throw new IndexOutOfBoundsException();
        }
        lasIndex--;
        return elem;
    }

    @Override
    public boolean contains(Object o) {
        for (int i = 0; i < size(); i++) {
            try {
                if (o.equals(get(i))) {
                    return true;
                }
            } catch (ClassCastException e) {
                throw new ClassCastException();
            } catch (NullPointerException e) {
                throw new NullPointerException();
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof MemoryList)) {
            return false;
        }
        MemoryList memoryList = (MemoryList) o;
        return Objects.equals(jedis, memoryList.jedis);
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
    public int indexOf(Object o) {
        for (int i = 0; i < size(); i++) {
            if (o.equals(get(i))) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public Iterator<String> iterator() {
        Iterator<String> it = new Iterator<String>() {
            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < size() && get(currentIndex) != null;
            }

            @Override
            public String next() {
                return get(currentIndex++);
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
        return it;
    }

    @Override
    public int lastIndexOf(Object o) {
        for (int i = size(); i >= 0; i--) {
            if (o.equals(get(i))) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public boolean remove(Object o) {
        String objectString;
        try {
            objectString = String.valueOf(indexOf(o));
        } catch (ClassCastException e) {
            throw new ClassCastException();
        }
        for (int i = 0; i < size(); i++) {
            if (this.contains(o)) {
                try {
                    jedis.del(objectString);
                } catch (NullPointerException ex) {
                    throw new NullPointerException();
                } catch (UnsupportedOperationException ex) {
                    throw new UnsupportedOperationException();
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends String> c) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean addAll(int index, Collection<? extends String> c) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public ListIterator<String> listIterator() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ListIterator<String> listIterator(int index) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public String set(int index, String element) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<String> subList(int fromIndex, int toIndex) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Object[] toArray() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        // TODO Auto-generated method stub
        return null;
    }

}
