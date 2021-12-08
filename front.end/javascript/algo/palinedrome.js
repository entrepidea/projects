/**
 	This is JS solution to algo problem: Palinedrome. I didn't write it, code is as below.
	run the code: "node palinedrome.js"
	https://leetcode.com/problems/valid-palindrome-ii/discuss/1606795/Javascript-or-Two-Pointer-approach
	Date: 12/06/21
 * */
var validPalindrome = function(s) {

    for(let i = 0, j = s.length-1; i <= j; i++, j--){
        if(s[i] !== s[j]){
           return isPalindrome(i, j-1, s) || isPalindrome(i+1, j, s);
        }
    }
    return true;
};

function isPalindrome(i, j, str){

    for(; i <= j; i++, j--){
        if(str[i] !== str[j]) return false;
    }
    return true;
}

console.log(validPalindrome('abca'))
