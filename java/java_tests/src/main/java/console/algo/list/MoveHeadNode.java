package console.algo.list;

/*
* TODO A single link list, how do you move the head to the middle of the link? (MS phone interview 05/14/18)
* */
public class MoveHeadNode {
    static class Node {
        public Node(int v){
            val = v;
        }
        public int val;
        public Node next;
    }

    public static Node initList(){
        Node head = new Node(0);
        Node temp = head;
        for(int i=1;i<10;i++){
            Node node = new Node(i);
            temp.next = node;
            temp = node;
        }
        return head;
    }

    //test init is done
    public static void printList(Node head){
        Node node = head;
        while(node.next!=null){
            System.out.print(node.val+",");
            node = node.next;
        }
        System.out.println();
    }

    public static int len(Node head){
        Node node = head;
        int count = 0;
        while(node.next!=null){
            count++;
            node = node.next;
        }
        return count;
    }

    public static Node locateMid(Node head){
        int loc = Math.round(len(head)/2);
        Node temp = head;
        for(int i=0;i<loc;i++){
            temp = temp.next;
        }
        return temp;
    }

    public static Node moveHead(Node head){
        Node newHead = head.next;
        Node temp = head;
        while(temp.next!=null){
            temp = temp.next;
        }
        Node tail = temp;
        temp.next = head;
        return newHead;
    }

    public static void main(String... args){
        Node head = initList();
        printList(head);
        System.out.println(locateMid(head).val);
    }
}
