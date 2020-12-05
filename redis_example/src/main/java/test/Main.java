package test;

import java.util.Iterator;

import test.memorycollections.MemoryList;
import test.memorycollections.MemoryMap;

public class Main {
    public static void main(String[] args) {
        MemoryMap memoryMap = new MemoryMap();
        MemoryMap map = new MemoryMap();

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
        memoryList.establishConnection("192.145.88.14", 7778);

        // 1 Add(ind, elem)
        // memoryList.add(5, "1");
        // memoryList.add(6, "2");
        // memoryList.add(7, "3");
        // memoryList.add(8, "4");
        // memoryList.add(9, "5");
        memoryList.add(0, "4555");
        memoryList.add(1, "7");
        memoryList.add(2, "7");

        // for (int i = 0; i < 20; i++){
        // //2 Get
        // System.out.print(memoryList.get(i));
        // }

        // 3 Size
        // System.out.println(memoryList.size());

        // 4 isEmpty
        // if (memoryList.isEmpty()){
        // System.out.println("empty");
        // } else{
        // System.out.println(" not empty");
        // }

        // 5 Remove
        // memoryList.remove(11);
        // for (int i = 0; i < memoryList.size(); i++){
        // System.out.print(memoryList.get(i) + " ");
        // }
        // System.out.println(memoryList.size());

        // 6 contains
        // if (memoryList.contains("5")){
        // System.out.println("+");
        // } else {
        // System.out.println("-");
        // }

        // 7 flesh
        // memoryList.clear();

        // 8 hash
        // System.out.println(memoryList.hashCode());

        // 9 equals

        // 10 iterator
        Iterator<String> iterator = memoryList.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
        // //11 indexof
        // System.out.println(memoryList.indexOf("7"));
        // System.out.println(memoryList.indexOf("1"));
        // //12 last indexof
        // System.out.println(memoryList.lastIndexOf("7"));
    }
}
