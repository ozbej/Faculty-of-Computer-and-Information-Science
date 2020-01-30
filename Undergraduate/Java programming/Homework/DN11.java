import java.io.File;
import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.TreeMap;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map.Entry;

public class DN11{
	public static void main(String[] args) throws Exception{
        Scanner sc = new Scanner(new File(args[0]));
        HashMap<String, Integer> besede = new HashMap();
        int key;
        while(sc.hasNext()){
            String next = sc.next().replaceAll("[.,!?()-+;-]", "");
            if (besede.containsKey(next)){
            	key = besede.get(next);
            	key++;
            	besede.put(next, key);                }
            else{
                besede.put(next, 1);
            }
        }

        if(args[1].equals("1")){
            Map<String, Integer> keySort = new TreeMap<>(besede);
            printMap(keySort);
        }

        if(args[1].equals("2")){
        Map<String, Integer> valueSort = new TreeMap<>(besede);
        Map<String, Integer> valueSortDesc = sortByComparator(valueSort);
        printMap(valueSortDesc);
        }
    }

    private static Map<String, Integer> sortByComparator(Map<String, Integer> unsortMap)
    {
        List<Entry<String, Integer>> list = new LinkedList<Entry<String, Integer>>(unsortMap.entrySet());
        Collections.sort(list, new Comparator<Entry<String, Integer>>()
        {
            public int compare(Entry<String, Integer> o1,
                    Entry<String, Integer> o2)
            {
                    return o2.getValue().compareTo(o1.getValue());
            }
        });

        Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
        for (Entry<String, Integer> entry : list)
        {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }

    public static void printMap(Map<String, Integer> map)
    {
        for (Entry<String, Integer> entry : map.entrySet())
        {
            System.out.printf("%-2s", entry.getValue());
            System.out.println("    " + entry.getKey());
        }
    }
}