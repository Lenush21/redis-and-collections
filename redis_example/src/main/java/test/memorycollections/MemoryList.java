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
        if (index > lasIndex) {
            throw new IndexOutOfBoundsException();
        }
        if (element == null) {
            throw new NullPointerException();
        }
        if (!(element instanceof String)) {
            throw new IllegalArgumentException();
        }
        try {
            ind = String.valueOf(index);
        } catch (ClassCastException e) {
            throw new ClassCastException();
        }
        jedis.set(ind, element);
        lasIndex++;
    }

    @Override
    public boolean add(String e) {
        if (e == null) {
            throw new NullPointerException();
        }
        if (!(e instanceof String)) {
            throw new IllegalArgumentException();
        }
        String sizeString;
        if (!this.contains(e)) {
            try {
                sizeString = String.valueOf(size());
                jedis.set(sizeString, e);
                lasIndex++;
                return true;
            } catch (ClassCastException ex) {
                throw new ClassCastException();
            }
        }
        return false;
    }

    @Override
    public String get(int index) {
        if (index < 0 || index > (this.size() - 1)) {
            throw new IndexOutOfBoundsException();
        }
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
        jedis.del(String.valueOf(index));
        lasIndex--;
        return elem;
    }

    @Override
    public boolean contains(Object o) {
        if (o == null) {
            throw new NullPointerException();
        }
        for (int i = 0; i < size(); i++) {
            try {
                if (o.equals(get(i))) {
                    return true;
                }
            } catch (ClassCastException e) {
                throw new ClassCastException();
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
        if (o == null) {
            throw new NullPointerException();
        }
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
        if (o == null) {
            throw new NullPointerException();
        }
        for (int i = size() -1; i >= 0; i--) {
            if (o.equals(get(i))) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public boolean remove(Object o) {
        String objectString;
        if (o == null) {
            throw new NullPointerException();
        }
        try {
            objectString = String.valueOf(indexOf(o));
        } catch (ClassCastException e) {
            throw new ClassCastException();
        }
        for (int i = 0; i < size(); i++) {
            if (this.contains(o)) {
                jedis.del(objectString);
                lasIndex--;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends String> c) {
        boolean flag = false;
        for (String s : c) {
            if (!this.contains(s)) {
                if (c.size() == 0) {
                    throw new NullPointerException();
                }
                this.add(s);
                lasIndex++;
                flag = true;
            }
        }
        return flag;
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
        boolean flag = false;
        while (e.hasNext()) {
            if (e.next() == null) {
                throw new NullPointerException();
            }
            if (!jedis.exists(String.valueOf(e.next()))) {
                flag = true;
            }
        }
        return flag;
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
        if (c.size() == 0) {
            throw new NullPointerException();
        }
        List list = (List) c;
        boolean flag = false;
        for (int i = c.size() - 1; i >= 0; i--) {
            if (this.contains(list.get(i))) {
                jedis.del(String.valueOf(i));
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
        if (element == null) {
            throw new NullPointerException();
        }
        if (!(element instanceof String)) {
            throw new IllegalArgumentException();
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
        if (a.length == 0) {
            throw new NullPointerException();
        }
        var i = 0;
        for (String s : this) {
            a[i] = (T)jedis.get(String.valueOf(indexOf(s)));
            if(i++ == a.length -1)
                return a;
        }
        return a;
    }
}
