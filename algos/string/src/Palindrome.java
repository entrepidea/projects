public class Palindrome {

	public static boolean isPalindrome(String str){
		char[] data = str.toCharArray();
		int mid = data.length/2;
		if(data.length%2>0) //odd
		{
			mid += 1;
		}
		
		int len = data.length;
		
		for(int i=0;i<mid;i++){
			if(data[i]!=data[len-1-i]) return false;
		}
		return true;
	}
	
	public static void main(String... args){
		System.out.println(isPalindrome("civic"));
		System.out.println(isPalindrome("pop"));
		System.out.println(isPalindrome("radar"));
		System.out.println(isPalindrome("deified"));
		System.out.println(isPalindrome("de1wed"));
	}
}

