public class Palindrome2 {
	public boolean isPalindrome(String s) {
		int len = s.length();

		s = s.toLowerCase();
		int i = 0;
		int j = len - 1;
		char ch;
		char ch2;
		while (i < j) {
			// key: get rid of all letters that is not alpha-numerical
			ch = s.charAt(i);
			while (i < j && !isAlphaNumber(ch)) {
				i++;
				ch = s.charAt(i);
			}

			// The following if-statement can be omitted.
			if (i == j)
				return true;

			// key: get rid of all letters that is not alpha-numerical
			ch2 = s.charAt(j);
			while (j > i && !isAlphaNumber(ch2)) {
				j--;
				ch2 = s.charAt(j);
			}

			// The following if-statement can be omitted.
			if (i == j)
				return true;

			if (ch != ch2)
				return false;

			// ch == ch2 and both ch and ch2 are alpha numerical.
			i++;
			j--;
		}

		return true;
	}

	boolean isAlphaNumber(char ch) {
		if (ch >= 'a' && ch <= 'z' || ch >= '0' && ch <= '9')
			return true;
		return false;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String s ="A man, a plan, a canal: Panama";
		boolean b= new Palindrome2().isPalindrome(s);
		System.out.println(b);
	}

}

