package test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.javatuples.Pair;

import test.memorycollections.ConnectionProvider;
import test.memorycollections.MemoryList;
import test.memorycollections.MemoryMap;


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



        // MemoryMap memoryMap = new MemoryMap();
        // MemoryMap map = new MemoryMap();

        // 1 Establish connection
        // memoryMap.establishConnection("192.145.88.14", 7777);

        // 2 Size
        // memoryMap.put("77", 7);
        // System.out.println("memoryMap.size());

        // 3 Put
        // memoryMap.put("11", 1);
        // memoryMap.put("22", 2);
        // memoryMap.put("33", 3);
        // memoryMap.put("44", 4);
        // memoryMap.put("55", 5);
        // memoryMap.put("66", 6);
        // System.out.println(memoryMap.size());

        // 4 Remove
        // memoryMap.remove("14");
        // System.out.println(memoryMap.size());

        // 5 Get
        // System.out.println(memoryMap.get("13"));

        // 6 IsEmpty
        // if (memoryMap.isEmpty() == true){
        // System.out.println("Empty");
        // } else{
        // System.out.println("Not empty");
        // }

        // 7 KeySet
        // System.out.println(memoryMap.keySet());

        // 8 Values
        // memoryMap.values();

        // 9 Contains value
        // if (memoryMap.containsValue(5)){
        // System.out.println("True);
        // } else{
        // System.out.println("False");
        // }

        // 10 Contain key
        // if (memoryMap.containsKey("133")){
        // System.out.println("True");
        // } else{
        // System.out.println("False");
        // }

        // 11 EntrySet
        // memoryMap.entrySet();

        // 12 PutAll
        // map.establishConnection("192.145.88.14", 7777);
        // map.putAll(memoryMap);
        // System.out.println(map);

        MemoryList memoryList = new MemoryList();
        memoryList.establishConnection("192.145.88.14", 7777);
        memoryList.clear();

        // 1 Add(ind, elem)
        // memoryList.add(5, "1");
        // memoryList.add(6, "2");
        // memoryList.add(7, "3");
        // memoryList.add(8, "4");
        // memoryList.add(9, "5");
        memoryList.add(0, "4555");
        memoryList.add(1, "7");
        memoryList.add(2, "4");

        // for (int i = 0; i < memoryList.size(); i++){
        // //2 Get
        // System.out.print(memoryList.get(i) + " ");
        // }

        // 3 Size
        // System.out.println(memoryList.size());

        // 4 isEmpty
        // if (memoryList.isEmpty()){
        // System.out.println("empty");
        // } else{
        // System.out.println(" not empty");
        // }

        // 5 String Remove(index)
        // memoryList.remove(11);
        // for (int i = 0; i < memoryList.size(); i++){
        // System.out.print(memoryList.get(i) + " " + " ");
        // }
        // System.out.println(memoryList.size());

        // 6 Contains
        // if (memoryList.contains("5")){
        // System.out.println("+");
        // } else {
        // System.out.println("-");
        // }

        // 7 Clear
        // memoryList.clear();

        // 8 Hash Code
        // System.out.println(memoryList.hashCode());

        // 9 Equals
        // if (memoryList.get(0).equals(memoryList.get(1))) {
        //     System.out.println("Equals");
        // } else {
        //     System.out.println("Not equals");
        // }

        // // 10 Iterator
        // // Iterator<String> iterator = memoryList.iterator();
        // // while (iterator.hasNext()) {
        // // System.out.println(iterator.next());
        // // }
        // // //11 IndexOf
        // // System.out.println(memoryList.indexOf("7"));
        // // System.out.println(memoryList.indexOf("1"));
        // // //12 LastIndexOf
        // // System.out.println(memoryList.lastIndexOf("7"));

        // // 13 Add(elem)
        // System.out.println("----------------------------------------------------------------------------");

        // System.out.println("Add(elem)");
        // memoryList.add("5");

        // for (int i = 0; i < memoryList.size(); i++) {
        //     System.out.print(memoryList.get(i) + " ");
        // }

        // // 14 boolean remove(index)
        // System.out.println("----------------------------------------------------------------------------");
        // System.out.println("boolean remove(index)");
        // memoryList.remove("5");
        // for (int i = 0; i < memoryList.size(); i++) {
        //     System.out.print(memoryList.get(i) + " ");
        // }

        // // 15 AddAll (collection)
        // System.out.println("----------------------------------------------------------------------------");
        // System.out.println("AddAll (collection)");
        // MemoryList memoryList2 = new MemoryList();
        // memoryList2.establishConnection("192.145.88.14", 7777);
        // memoryList2.add("1");
        // memoryList2.add("2");
        // memoryList2.add("5");

        // memoryList.addAll(memoryList2);
        // for (int i = 0; i < memoryList.size(); i++) {
        //     System.out.print(memoryList.get(i) + " ");
        // }
        // 16 AddAll (index, collection)
        // System.out.println("----------------------------------------------------------------------------");
        // System.out.println("AddAll (index, collection)");
        // MemoryList memoryList3 = new MemoryList();
        // memoryList3.establishConnection("192.145.88.14", 7777);
        // memoryList3.add("55");
        // memoryList3.add("32");
        // memoryList3.add("21");
        // memoryList.addAll(3, memoryList3);
        // for (int i = 0; i < memoryList.size(); i++) {
        //     System.out.print(memoryList.get(i) + " ");
        // }

        // 17 ContainsAll
        // System.out.println("----------------------------------------------------------------------------");
        // System.out.println("AddAll (index, collection)");
        // if (memoryList.containsAll(memoryList2)) {
        //     System.out.println("Contains");
        // } else {
        //     System.out.println("Not really");
        // }
        for (int i = 0; i < memoryList.size(); i++) {
                System.out.print(memoryList.get(i) + " ");
            }
    

        // 18 listIterator
        System.out.println("----------------------------------------------------------------------------");
        System.out.println("listIterator");
        ListIterator<String> listIterator = memoryList.listIterator();
        while (listIterator.hasNext()) {
            String element = listIterator.next();
            System.out.print(element + "+");
        }

        while (listIterator.hasPrevious()) {
            String element = listIterator.previous();
            System.out.print(element + " ");
        }
        // 19 listIterator(index)
        System.out.println("----------------------------------------------------------------------------");
        System.out.println("listIterator(index)");
        ListIterator<String> itrf = memoryList.listIterator(3);
        while (itrf.hasNext())
            System.out.print(itrf.next() + "+");

        while (itrf.hasPrevious())
            System.out.print(itrf.previous() + " ");

        

        //memoryList.closeConnection();
        // memoryList2.closeConnection();
        // memoryList3.closeConnection();
        System.out.println("----------------------------------------------------------------------------");

        //20 RemoveAll
        for (int i = 0; i < memoryList.size(); i++) {
            System.out.print(memoryList.get(i) + " ");
        }
        System.out.println("RemoveAll");
        MemoryList memoryList4 = new MemoryList();
        memoryList4.establishConnection("192.145.88.14", 7777);
        memoryList4.add("4");
        memoryList4.add("7");
        

        memoryList.removeAll(memoryList4);
        for (int i = 0; i < memoryList.size(); i++) {
            System.out.print(memoryList.get(i) + " ");
        }
        // //21 RetailAll
        // System.out.println("----------------------------------------------------------------------------");
        // System.out.println("RetainAll");
        // memoryList.add("4");
        // memoryList.add("7");
        // memoryList.add("21");
        // memoryList4.add("25");
        // memoryList.retainAll(memoryList4);
       
        // for (int i = 0; i < memoryList.size(); i++) {
        //     System.out.print(memoryList.get(i) + " ");
        // }        

    }
}
