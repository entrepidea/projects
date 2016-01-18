public class RotateString {

	public RotateString(){
		
	}
	
	static class Node{
		public char val;
		public Node next; 
		public Node(char v){val = v;}
	}
	
	private static Node constructList(String s){
		char[] arr = new char[s.length()];
		arr = s.toCharArray();
		Node head = new Node(arr[0]);
		Node p = head;
		for(int i=1;i<arr.length;i++){
			Node temp = new Node(arr[i]);
			p.next = temp;
			p = p.next;
		}
		p.next = head;
		return head;
	}
	
	public static boolean isRotate(String s1, String s2){
		Node p = constructList(s1);
		char[] arr = new char[s2.length()];
		arr = s2.toCharArray();
		char firstC = arr[0];
		int count=0;
		boolean found = false;
		while(count<arr.length){
			if(p.val == firstC){
				found = true;
				break;
			}
			p = p.next;
		}
		if(found){
			for(int i=0;i<arr.length;i++){
				if(p.val!=arr[i]){
					return false;
				}
				p = p.next;
			}
			return true;
		}
		else{
			return false;
		}
	}
	public static void main(String[] args){
		String s1 = "erwat";
		String s2 = "water";
		System.out.println(isRotate(s1,s2));
	}
}

