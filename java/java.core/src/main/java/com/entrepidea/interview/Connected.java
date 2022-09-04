package com.entrepidea.interview;


/**
 *
 *Suppose you are given a text file containing pairs of city names, one pair per line, with the names on each line separated by a comma. The file might look something like (provided for illustration purposes only):
 Philadelphia, Pittsburgh
 Boston, New York
 Hartford, New York
 Los Angeles, San Diego
 New York, Croton-Harmon
 St. Petersburg, Tampa
 Each line of the file indicates that it is possible to travel between the two cities named. (More formally, if we think of the cities as nodes in a graph, each line of the file specifies an edge between two nodes.) In the example above it is possible to travel between Boston and New York, and also between Hartford and New York, so it follows that Boston and Hartford are connected. On the other hand, there is no path between Boston and Tampa, so they are not connected.
 Write a java program, called
 Connected
 , that reads in such a file and outputs whether two specified cities are connected. The program will be invoked from the command line as:
 java Connected
 <filename> <cityname1> <cityname2>
 and will output a single line stating
 yes
 or
 no
 .
 Here are some sample interactions, assuming the example file above is named
 cities.txt

 > java Connected cities.txt Boston Hartford
 yes
 > java Connected cities.txt Boston Tampa
 no
 > java Connected cities.txt Boston Ypsilanti
 no
 •	Commas will not appear within city names in the file. For example, "Washington, D.C." will not appear in the file as a city name.
 •	Your choice of algorithms and data structures should allow the program to handle arbitrarily large files reasonably efficiently. You can, however, assume that the file will fit in memory.
 •	The program is permitted to return any or no output when given a malformed input file or malformed command line; however it should not crash or otherwise terminate abnormally.
 •	The file is considered to be a list of all connections, not all cities; a city not in the file is connected to no other city.
 •	Please let us know how to build it (e.g. supply a Makefile) so that we can build and test it ourselves.
 •	Multiple source files should be tarred/zipped together.

 Note:
 	File sample above is provided for illustration purposes only, program should be able to detect connection where number of edges in the connectivity graph between cities is bigger than 2
 	The code should be production quality
 *
 * @note: This is a BGC java test,delivered to me by SGA consulting company in July,2019.
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
