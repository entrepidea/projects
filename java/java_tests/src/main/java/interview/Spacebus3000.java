package interview;

/**
 *
 * BGC interview question 11/19
 *
 * Spacebus3000 : It s the year 3000 and there s a company called Spacebus that offers rides between certain spaceports.
 * Imagine that you are given a file listing the routes that Spacebus offers. Here s an example of what the file could look like
 *
 * Infrared Spacehub, Grand Nebula Spaceport
 * Blue Nova Space Market, Heavy Element Spacemine
 * Asteroid Research Institute, Oort Cloud Observation Facility
 * Infrared Spacehub, Oort Cloud Observation Facility
 * Oort Cloud Observation Facility, Double Ring Space Habitat
 *
 * Each line means there s a Spacebus route going back and forth between the two spaceports.
 * In the example above, one can take a Spacebus from Asteroid Research Institute to Oort Cloud Observation Facility,
 * and also from Oort Cloud Observation Facility to Infrared Spacehub,
 * and also from Infrared Spacehub to Grand Nebula Spaceport.
 *
 * So it follows that one could travel all the way from Asteroid Research Institute to Grand Nebula Spaceport just using Spacebus.
 *
 * On the other hand, there is no way to get from Asteroid Research Institute to Heavy Element Spacemine using Spacebus.
 *
 * Write a Java (or C++) program called  Spacebus3000  that takes three arguments -
 * the name of a file listing Spacebus s routes
 * and the names of two spaceports
 * and outputs  yes  or  no
 *
 * depending on whether one could travel from one of the spaceports to the other of the spaceports just using Spacebus.
 *
 * If either of the spaceport arguments isn't in the file, then the program should output "no".
 *
 * Here are some sample Java interactions, assuming the example file above is named spacebusroutes.txt
 *
 * java Spacebus3000 spacebusroutes.txt  Oort Cloud Observation Facility   Asteroid Research Institute
 * > yes
 *
 * java Spacebus3000 spacebusroutes.txt  Asteroid Research Institute   Grand Nebula Spaceport
 * > yes
 *
 * java Spacebus3000 spacebusroutes.txt  Asteroid Research Institute   Heavy Element Spacemine
 * > no
 *
 * java Spacebus3000 spacebusroutes.txt  Asteroid Research Institute  Pluto
 * > no
 *
 * You can assume that none of the names of the spaceports will contain commas. That is, in the file, there will only be one comma on each line, separating the names of the two spaceports.
 *
 * You won't need to write any code to handle the quotes that appear around the spaceport names in the example command-line interactions. The command-line environment will naturally take care of that.
 * In the last example command-line interaction, as far as the Java method Spacebus3000.main() is concerned, args[1] is Asteroid Research Institute, and args[2] is Pluto.
 *
 * There is no requirement concerning how the program should behave if the file isn't formatted as expected or if the program isn't called with the expected number of arguments.
 *
 * It's best if you just send us the .java or .cpp file(s) you write, unzipped, without any .class or .exe files.
 * */



import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;

public class Spacebus3000 {
    public static void main(String[] args) throws Exception {
        if (args.length != 3) {
            System.out.println("need three parameters, file name, start, destination");
            return;
        }

        String fileName = args[0]; //spacebusroutes.txt
        String start = args[1]; //"Asteroid Research Institute";
        String destination = args[2]; //"Grand Nebula Spaceport"; //yes

        String answer =	pathExistOrNot(fileName, start, destination);

        System.out.println(answer);
    }

    public static String pathExistOrNot(String fileName, String start, String destination) throws Exception {
        LinkedList<String> nodes = getNodes(fileName);
        LinkedList<String>[] adjList = getAdjList(fileName);
        HashMap<String, Integer> map = mapStringToInt(nodes);

        //Array visited indicates whether a node has been visited or not.
        boolean[] visited = new boolean[nodes.size()];
        for (int i = 0; i < visited.length; i++)
            visited[i] = false;

        //width-first search adjList to check whether there is a path
        //from start to destination.
        Queue<String> q = new PriorityQueue<String>();
        q.add(start);

        String currNode;
        String connectNode; //a neighbor node of currentNode
        int j, k;
        while (!q.isEmpty()) {
            currNode = q.remove();

            if (currNode.equals(destination))
                return "yes";

            j = map.get(currNode); //find out the index of currNode in adjList.
            for (int i = 0; i < adjList[j].size(); i++) {
                connectNode = adjList[j].get(i);
                k = map.get(connectNode);

                if (!visited[k]) { //if connectNode (a neighbor) is not visited
                    visited[k] = true;
                    q.add(connectNode);
                }
            }
        }

        return "no";
    }

    public static LinkedList<String> getNodes(String fileName) throws Exception {
        LinkedList<String> nodes = new LinkedList<String>();

        String str;
        String[] pair = new String[2];
        String currNode;

        File file = new File(fileName);
        Scanner sc = new Scanner(file);
        while (sc.hasNextLine()) {
            str = sc.nextLine();

            pair = str.split(","); //pair of nodes connected by an edge
            for (int i = 0; i < pair.length; i++) {
                currNode = pair[i].trim();
                if (!nodes.contains(currNode)) //currNode is not in list of nodes yet
                    nodes.add(currNode);
            }
        }
        sc.close();

        return nodes;
    }

    public static HashMap<String, Integer> mapStringToInt(LinkedList<String> nodes) throws Exception {
        HashMap<String, Integer> map = new HashMap<String, Integer>();

        for (int i = 0; i < nodes.size(); i++)
            map.put(nodes.get(i), i);

        return map;
    }

    public static LinkedList<String>[] getAdjList(String fileName) throws Exception {
        //Get an empty array list to hold nodes of a graph.
        LinkedList<String> nodes = getNodes(fileName);
        HashMap<String, Integer> map = mapStringToInt(nodes);

        int numNodes = nodes.size();
        @SuppressWarnings("unchecked")
        LinkedList<String> adjList[] = (LinkedList<String>[]) new LinkedList<?>[numNodes];

        //Put each node to the beginning of a linked list.
        for (int i = 0; i < numNodes; i++) {
            adjList[i] = new LinkedList<String>(); //create an empty linked list for adjList[i].
            adjList[i].add(nodes.get(i));
        }

        String str;
        String[] pair = new String[2];
        String currNode;
        String connectNode;
        int j;

        File file = new File(fileName);
        Scanner sc = new Scanner(file);
        while (sc.hasNext()) {
            str = sc.nextLine();
            pair = str.split(",");

            for (int i = 0; i < pair.length; i++)  {
                //adjacent list of both nodes of a bi-direction edge need to be updated
                currNode = pair[i].trim();
                connectNode = pair[(i+1) % 2].trim();
                j = map.get(currNode);
                adjList[j].add(connectNode);
            }
        }
        sc.close();

        return adjList;
    }
}
