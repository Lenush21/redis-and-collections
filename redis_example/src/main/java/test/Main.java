package test;

import test.memorycollections.MemoryMap;

public class Main 
{
    public static void main( String[] args )
    {
        MemoryMap memoryMap = new MemoryMap();
        MemoryMap map = new MemoryMap();

        //1 Establish connection
        memoryMap.establishConnection("192.145.88.14", 7777);

        //2 Size
        //memoryMap.put("77", 7);
        //System.out.println("memoryMap.size());

        //3 Put
        //memoryMap.put("11", 1);
        //memoryMap.put("22", 2);
        //memoryMap.put("33", 3);
        //memoryMap.put("44", 4);
        //memoryMap.put("55", 5);
        //memoryMap.put("66", 6);
        //System.out.println(memoryMap.size());
        
        //4 Remove
        //memoryMap.remove("14");
        System.out.println( memoryMap.size());

        //5 Get
        //System.out.println(memoryMap.get("13"));

        //6 IsEmpty
        // if (memoryMap.isEmpty() == true){
        //     System.out.println("Empty");
        // } else{
        //     System.out.println("Not empty");
        // }
        
        //7 KeySet
        //System.out.println(memoryMap.keySet());
        
        //8 Values
        //memoryMap.values();

        //9 Contains value
        // if (memoryMap.containsValue(5)){
        //         System.out.println("True);
        //     } else{
        //         System.out.println("False");
        //     }
        
        //10 Contain key
        // if (memoryMap.containsKey("133")){
        //             System.out.println("True");
        //         } else{
        //             System.out.println("False");
        //         }

        //11 EntrySet
        //memoryMap.entrySet();

        //12 PutAll
        //map.establishConnection("192.145.88.14", 7777);
        //map.putAll(memoryMap);
        //System.out.println(map);
       
 
        
    }
}
