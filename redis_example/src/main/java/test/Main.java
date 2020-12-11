package test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.javatuples.Pair;

import test.memorycollections.ConnectionProvider;
import test.memorycollections.MemoryList;
import test.memorycollections.MemoryMap;
import test.memorycollections.MemorySet;


public class Main {
    public static void main(String[] args) throws Exception {
        List<Pair<String, Integer>> list = new ArrayList<Pair<String, Integer>>();
        list.add(new Pair<String, Integer>("192.168.109.113", 8091));
        list.add(new Pair<String, Integer>("192.168.109.113", 7778));
        list.add(new Pair<String, Integer>("192.168.109.113", 7780));
        list.add(new Pair<String, Integer>("192.168.109.113", 7779));
        ConnectionProvider cp = new ConnectionProvider(list);
        // for (int i = 0; i < list.size(); i++) {
        //         var r = cp.getHost();
        //         r.append("key", "value");
        //         r.close();
        // }
        cp.init(list);



        MemoryMap memoryMap = new MemoryMap();
        //MemoryMap map = new MemoryMap();

        // 1 Establish connection
        System.out.println("Establish connection");
        memoryMap.establishConnection("192.145.88.14", 7777);
        System.out.println("----------------------------------------------------------------------------");

        // 2 Size
        System.out.println("Size");
        memoryMap.put("77", 7);
        System.out.println("Size " + memoryMap.size());
        System.out.println("----------------------------------------------------------------------------");

        // 3 Put
        System.out.println("Put");
        memoryMap.put("11", 1);
        memoryMap.put("22", 2);
        memoryMap.put("33", 3);
        memoryMap.put("44", 4);
        memoryMap.put("55", 5);
        memoryMap.put("66", 6);
        System.out.println("New size " + memoryMap.size());
        System.out.println("----------------------------------------------------------------------------");

        // 4 Remove
        System.out.println("Remove");
        memoryMap.remove("11");
        System.out.println("Size after remove " + memoryMap.size());
        System.out.println("----------------------------------------------------------------------------");

        // 5 Get
        System.out.println("Get");
        System.out.println(memoryMap.get("33"));
        System.out.println("----------------------------------------------------------------------------");

        // 6 IsEmpty
        System.out.println("isEmpty");
        if (memoryMap.isEmpty() == true) {
            System.out.println("MemoryMap is empty");
        } else {
            System.out.println("MemoryMap is not empty");
        }
        System.out.println("----------------------------------------------------------------------------");

        // 7 KeySet
        System.out.println("KeySet");
        System.out.println("KeySet" + memoryMap.keySet());
        System.out.println("----------------------------------------------------------------------------");

        // 8 Values
        System.out.println("Values");
        System.out.println("Values" + memoryMap.values());
        System.out.println("----------------------------------------------------------------------------");

        // 9 Contains value
        System.out.println("Contains value");
        if (memoryMap.containsValue(5)) {
            System.out.println("MemoryMap has this value");
        } else {
            System.out.println("MemoryMap doesn't have this value");
        }
        System.out.println("----------------------------------------------------------------------------");

        // 10 Contain key
        System.out.println("Contains key");
        if (memoryMap.containsKey("133")) {
            System.out.println("MemoryMap has this key");
        } else {
            System.out.println("MemoryMap doesn't have this key");
        }
        System.out.println("----------------------------------------------------------------------------");

        // 11 EntrySet
        System.out.println("EntrySet");
        System.out.println(memoryMap.entrySet());
        System.out.println("----------------------------------------------------------------------------");

        // 12 PutAll
        // map.establishConnection("192.145.88.14", 7777);
        // map.putAll(memoryMap);
        // System.out.println(map);
        memoryMap.clear();
        memoryMap.closeConnection();

        MemoryList memoryList = new MemoryList();
        memoryList.establishConnection("192.145.88.14", 7777);
        memoryList.clear();

        // 1 Add(ind, elem)
        System.out.println("Add(ind, elem)");
        memoryList.add(0, "4555");
        memoryList.add(1, "7");
        memoryList.add(2, "4");
        memoryList.add(3, "7");
        memoryList.add(4, "7");
        //System.out.println("----------------------------------------------------------------------------");

        System.out.println("Get");
        for (int i = 0; i < memoryList.size(); i++) {
            // 2 Get
            System.out.print(memoryList.get(i) + " ");
        }
        System.out.println("----------------------------------------------------------------------------");

        // 3 Size
        System.out.println("Size " + memoryList.size());
        System.out.println("----------------------------------------------------------------------------");

        // 4 isEmpty
        System.out.println("is empty");
        if (memoryList.isEmpty()) {
            System.out.println("MemoryList is empty");
        } else {
            System.out.println("MemoryList is not empty");
        }
        System.out.println("----------------------------------------------------------------------------");

        // 5 String Remove(index)
        System.out.println("String Remove(index)");
        memoryList.remove(4);

        for (int i = 0; i < memoryList.size(); i++) {
            System.out.print(memoryList.get(i) + " " + " ");
        }
        System.out.println("Size " + memoryList.size());
        System.out.println("----------------------------------------------------------------------------");

        // 6 Contains
        System.out.println("Contains");
        if (memoryList.contains("5")) {
            System.out.println("MemoryList has it");
        } else {
            System.out.println("MemoryList doesn't have it");
        }
        System.out.println("----------------------------------------------------------------------------");

        // 7 Clear

        // System.out.println("lear");
        // memoryList.clear();
        // System.out.println("----------------------------------------------------------------------------");

        // 8 Hash Code
        System.out.println("Hashcode");
        System.out.println(memoryList.hashCode());
        System.out.println("----------------------------------------------------------------------------");

        // 9 Equals
        System.out.println("Equals");
        if (memoryList.get(0).equals(memoryList.get(1))) {
            System.out.println("Equals");
        } else {
            System.out.println("Not equals");
        }
        System.out.println("----------------------------------------------------------------------------");

        // 10 Iterator
        System.out.println("Iterator");
        Iterator<String> iterator = memoryList.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
        System.out.println("----------------------------------------------------------------------------");

        // 11 IndexOf
        System.out.println("Index of");
        System.out.println(memoryList.indexOf("7"));
        System.out.println(memoryList.indexOf("1"));
        System.out.println("----------------------------------------------------------------------------");

        // 12 LastIndexOf
        System.out.println("LastIndexOf");
        System.out.println(memoryList.lastIndexOf("7"));
        System.out.println("----------------------------------------------------------------------------");

        // 13 Add(elem)
        System.out.println("Add(elem)");
        memoryList.add("5");

        for (int i = 0; i < memoryList.size(); i++) {
            System.out.print(memoryList.get(i) + " ");
        }
        System.out.println("----------------------------------------------------------------------------");

        // 14 boolean remove(index)
        System.out.println("boolean remove(index)");
        memoryList.remove("5");
        for (int i = 0; i < memoryList.size(); i++) {
            System.out.print(memoryList.get(i) + " ");
        }
        System.out.println("----------------------------------------------------------------------------");

        
        // 18 listIterator
        System.out.println("listIterator");
        ListIterator<String> listIterator = memoryList.listIterator();
        while (listIterator.hasNext()) {
            String element = listIterator.next();
            System.out.println(element);
        }

        while (listIterator.hasPrevious()) {
            String element = listIterator.previous();
            System.out.print(element + " ");
        }
        System.out.println("----------------------------------------------------------------------------");

        // 19 listIterator(index)
        System.out.println("listIterator(index)");
        ListIterator<String> itrf = memoryList.listIterator(3);
        while (itrf.hasNext())
            System.out.println(itrf.next());

        while (itrf.hasPrevious())
            System.out.print(itrf.previous() + " ");

        System.out.println("----------------------------------------------------------------------------");

        // 20 toArray()
        System.out.println("ToArray");
        Object[] arrayList = memoryList.toArray();
        for (int i = 0; i < arrayList.length; i++){
            System.out.println(arrayList[i]);
        }

        // 21 toArray(T[] a)
        System.out.println("ToArray(T a)");
        Object[] arrList = new Object[memoryList.size()];
        memoryList.toArray(arrList);
        for (int i = 0; i < arrList.length; i++){
            System.out.println(arrList[i]);
        }
        System.out.println("----------------------------------------------------------------------------");

        // 22 subList
        System.out.println("Sublist");
        System.out.println(memoryList.subList(0, 1));
        System.out.println("----------------------------------------------------------------------------");

        //23 set
        System.out.println("Set");
        memoryList.set(1, "007");
        System.out.println(memoryList.subList(0, memoryList.size() - 1));

        memoryList.clear();
        memoryList.closeConnection();

        MemorySet memorySet = new MemorySet();

        System.out.println("Establish connection");
        memorySet.establishConnection("192.145.88.14", 7778);
        System.out.println("----------------------------------------------------------------------------");
        memorySet.clear();

        // 1 boolean add (e)
        System.out.println("boolean add (e)");
        //memorySet.add("0");
        memorySet.add("2");
        memorySet.add("1");
        memorySet.add("3");
        memorySet.add("4");
        for (String string : memorySet) {
            System.out.println(string);
        }
        System.out.println("----------------------------------------------------------------------------");

        // 2 Size
        System.out.println("Size " + memorySet.size());

        // 3 Iterator
        System.out.println("Iterator");
        Iterator<String> it = memorySet.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }
        System.out.println("----------------------------------------------------------------------------");

        // 4 Contains
        System.out.println("Contains");
        if (memorySet.contains("5")) {
            System.out.println("MemorySet has it");
        } else {
            System.out.println("MemorySet doesn't have it");
        }
        System.out.println("----------------------------------------------------------------------------");

        // 5 Equals
        System.out.println("Equals");
        for (String string : memorySet) {
            if (string.equals("1")) {
                System.out.println("Equals");
            } else {
                System.out.println("Not equals");
            }
        }
        System.out.println("----------------------------------------------------------------------------");

        // 6 Hash Code
        System.out.println("Hashcode");
        System.out.println(memorySet.hashCode());
        System.out.println("----------------------------------------------------------------------------");

        // 7 clear
        // memorySet.clear();

        // 8 boolean remove
        System.out.println("boolean remove");
        memorySet.remove("1");
        for (String string : memorySet) {
            System.out.println(string);
        }
        System.out.println("----------------------------------------------------------------------------");

        // 9 IsEmpty
        System.out.println("isEmpty");
        if (memorySet.isEmpty() == true) {
            System.out.println("MemorySet is empty");
        } else {
            System.out.println("MemorySet is not empty");
        }
        System.out.println("----------------------------------------------------------------------------");

        // 10 toArray()
        System.out.println("ToArray");
        Object[] array = memorySet.toArray();
        for (int i = 0; i < array.length; i++){
            System.out.println(array[i]);
        }
        System.out.println("----------------------------------------------------------------------------");

        // 11 toArray(T[] a)
        System.out.println("ToArray(T a)");
        Object[] arr = new Object[memorySet.size()];
        memorySet.toArray(arr);
        for (int i = 0; i < arr.length; i++){
            System.out.println(arr[i]);
        }
        System.out.println("----------------------------------------------------------------------------");

        memorySet.clear();
        memorySet.closeConnection();
    }
}
