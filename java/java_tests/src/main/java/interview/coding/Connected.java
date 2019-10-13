package interview.coding;

/**
 *
 * @desc: This is a BGC java test,delivered to me by SGA consulting company in July,2019.
 * For the whole description, please see resources/data/CodeTest[3365].doc.
 * After some searching it seems this is a special case of a general algo known as Union-Find on disjoints data structure.
 * However, below is an implementation using the idea of Breath First Search, implemented using a queue.
 *
 * @date: 07/24/19
 *
 * */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Connected {

    private static Map<String, Queue<String>> constructMapFromFile(String fileName){
        Map<String, Queue<String> > map = new HashMap<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName)) ;
            String line = null;
            while((line = br.readLine())!=null){
                //System.out.println(line);
                String[] cityPair = line.split(",");
                if(map.get(cityPair[0])==null){
                    Queue<String> values = new LinkedList<>();
                    values.add(cityPair[1]);
                    map.put(cityPair[0], values);
                }
                else{
                    Queue<String> values = map.get(cityPair[0]);
                    values.add(cityPair[1]);
                    map.put(cityPair[0], values);
                }
                if(map.get(cityPair[1])==null){
                    Queue<String> values = new LinkedList<>();
                    values.add(cityPair[0]);
                    map.put(cityPair[1], values);
                }
                else{
                    Queue<String> values = map.get(cityPair[1]);
                    values.add(cityPair[0]);
                    map.put(cityPair[1], values);
                }
            }
            br.close();

            //test
            Iterator<Map.Entry<String, Queue<String>>> iter = map.entrySet().iterator();
            while(iter!=null && iter.hasNext()){
                Map.Entry<String, Queue<String>> entry = iter.next();
                String key = entry.getKey();
                Queue<String> val = entry.getValue();
                System.out.println("key="+key+", value="+val);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    private static boolean find(String startCity, String destination, Map<String, Queue<String> >map){

        Set<String> s = new HashSet<>();
        Queue<String> q = map.get(startCity);
        while(!q.isEmpty()){
            String city = q.remove();
            if(city.equals(destination)){
                return true; //found
            }
            if (s.contains(city) || city.equals(startCity)){//already vetted, ignore
                continue;
            }
            else{
                s.add(city);
                Queue<String> temp = map.get(city);
                while(!temp.isEmpty()){
                    String e = temp.remove();
                    if(s.contains(e) || startCity.equals(e)){
                        continue; //already vetted
                    }
                    q.add(e);
                }

            }
        }

        return false;
    }

    public static void main(String[] args){
        String fileName = args[0];
        String startCity = args[1];
        String destinationCity = args[2];
        System.out.printf("cities: %s, start city: %s, destination: %s\n", fileName, startCity, destinationCity);

        Map<String, Queue<String> > map = constructMapFromFile(fileName);

        boolean found = find(startCity, destinationCity, map);
        System.out.println(found?"Yes":"No");

    }
}
