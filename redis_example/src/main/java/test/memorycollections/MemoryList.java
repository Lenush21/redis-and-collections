package test.memorycollections;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
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

        if (index > lasIndex) {
            throw new IndexOutOfBoundsException();
        }
        try {
            jedis.set(ind, element);
        } catch (NullPointerException e) {
            throw new NullPointerException();
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException();
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
        if (index < 0 || index > (size() - 1)) {
            throw new IndexOutOfBoundsException();
        }
        elem = get(index);
        try {
            jedis.del(String.valueOf(index));
        } catch (UnsupportedOperationException e) {
            throw new UnsupportedOperationException();
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
                    lasIndex--;
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
        for (String s : c) {
            if (!this.contains(s)) {
                this.add(s);
                lasIndex++;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean addAll(int index, Collection<? extends String> c) {
        if (c == null || c.isEmpty()) {
            return false;
        }
        if (index < 0 || index > (c.size() - 1)) {
            throw new IndexOutOfBoundsException();
        }

        int i = 0;
        boolean collectionChanged = false;
        for (Iterator<? extends String> iter = c.iterator(); iter.hasNext(); ++i) {
            String elem = iter.next();
            if (i >= index) {
                collectionChanged = true;
                add(elem);
                lasIndex++;
            }
        }
        return collectionChanged;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        Iterator<?> e = c.iterator();
        while (e.hasNext())
            if (!jedis.exists(String.valueOf(e.next()))){
                return false;
            }
        return true;
        // return false;
    }

    @Override
    public ListIterator<String> listIterator() {
        ListIterator<String> it = new ListIterator<String>() {

            private int currentIndex = 0;
            private int lastRet = -1;
            private Jedis j = jedis;

            @Override
            public boolean hasNext() {
                return currentIndex < size();
            }

            public String next() {
                lastRet++;
                return get(currentIndex++);
            }

            @Override
            public boolean hasPrevious() {
                return lastRet != -1;
            }

            @Override
            public String previous() {
                lastRet--;
                return get(--currentIndex);
            }

            @Override
            public void add(String e) {
                String currentIndexString = String.valueOf(currentIndex++);
                j.set(currentIndexString, e);

            }

            @Override
            public void remove() {
                j.del(get(currentIndex));
                currentIndex--;
            }

            @Override
            public void set(String e) {
                String currentIndexString = String.valueOf(currentIndex);
                j.set(currentIndexString, e);

            }

            @Override
            public int nextIndex() {
                return currentIndex + 1;
            }

            @Override
            public int previousIndex() {
                return currentIndex - 1;
            }

        };
        return it;
    }

    @Override
    public ListIterator<String> listIterator(int index) {
        ListIterator<String> it = new ListIterator<String>() {

            private int currentIndex = index;
            private int lastRet = index - 1;
            private Jedis j = jedis;

            @Override
            public boolean hasNext() {
                return currentIndex < size();
            }

            public String next() {
                lastRet++;
                return get(currentIndex++);
            }

            @Override
            public boolean hasPrevious() {
                return lastRet != -1;
            }

            @Override
            public String previous() {
                lastRet--;
                return get(--currentIndex);
            }

            @Override
            public void add(String e) {
                String currentIndexString = String.valueOf(currentIndex++);
                j.set(currentIndexString, e);

            }

            @Override
            public void set(String e) {
                String currentIndexString = String.valueOf(currentIndex);
                j.set(currentIndexString, e);

            }

            @Override
            public int nextIndex() {
                return currentIndex + 1;
            }

            @Override
            public int previousIndex() {
                return currentIndex - 1;
            }

            @Override
            public void remove() {
                j.del(get(currentIndex));

            }

        };

        return it;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        List list = (List)c;
        boolean flag = false;
        for (int i = c.size() - 1; i >= 0; i--){
            if (this.contains(list.get(i))){
                jedis.del(String.valueOf(i));
                flag = true;
            }
        } 
        return flag;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean flag = false;
        for (int i = 0; i < size(); i++) {
            if (!c.contains(get(i))) {
                remove(i);
                lasIndex--;
                flag = true;
            }
        }
        return flag;
    }

    @Override
    public String set(int index, String element) {
        if (index < 0 || index > (size() - 1)) {
            throw new IndexOutOfBoundsException();
        }

        String indexString = String.valueOf(index);
        String prevElement = jedis.get(indexString);
        jedis.set(indexString, element);
        return prevElement;
    }

    @Override
    public List<String> subList(int fromIndex, int toIndex) {
        List<String> list = new ArrayList<String>();
        if (fromIndex < 0 || toIndex > (size() - 1)) {
            throw new IndexOutOfBoundsException();
        }
        for (int i = fromIndex; i < size(); i++) {
            list.add(get(i));
            if (i == toIndex)
                break;
        }
        return list;
    }

    @Override
    public Object[] toArray() {
        Object[] objs = new Object[size()];
        for (int i = 0; i < size(); i++) {
            objs[i] = get(i);
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
}
