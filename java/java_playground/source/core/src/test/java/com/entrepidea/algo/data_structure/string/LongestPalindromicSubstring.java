package com.entrepidea.algo.data_structure.string;

public class LongestPalindromicSubstring {
    public static void main(String[] args) {
        //String s = "babad";
        String s = "cbbd";
        System.out.println("Longest Palindromic Substring is: " + longestPalindrome(s));
    }

    public static String longestPalindrome(String s) {
        int n = s.length();
        if (n == 0) return "";

        boolean[][] dp = new boolean[n][n];
        int start = 0, maxLength = 1;

        // All substrings of length 1 are palindromes
        for (int i = 0; i < n; i++) {
            dp[i][i] = true;
        }

        // Check for substrings of length 2
        for (int i = 0; i < n - 1; i++) {
            if (s.charAt(i) == s.charAt(i + 1)) {
                dp[i][i + 1] = true;
                start = i;
                maxLength = 2;
            }
        }

        // Check for lengths greater than 2
        for (int length = 3; length <= n; length++) {
            for (int i = 0; i < n - length + 1; i++) {
                int j = i + length - 1;

                if (dp[i + 1][j - 1] && s.charAt(i) == s.charAt(j)) {
                    dp[i][j] = true;

                    if (length > maxLength) {
                        start = i;
                        maxLength = length;
                    }
                }
            }
        }

        return s.substring(start, start + maxLength);
    }
}

