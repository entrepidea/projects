public class Solution {
    //Key term: each ugly number other than 1, 2, 3, 5
    //grown from some smaller ugly number by multiplying 2, 3, or 5.
    //If ugly number y is grown from a smaller ugly number x
    //by multiplying x by 2, ie. y = 2*x,
    //then x is the 2-seed of y.
    public int nthUglyNumber(int n) {
        if (n <= 6)
           return n;
         
        //n > 6   
        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
        int[] arr = new int[n+1];
        for (int i = 1; i <= 6; i++) {
            map.put(i, i);
            arr[i] = i;
        }
        
        int k = 7;
        int x = 6; //x is the most recent ugly number
        while (k <= n) {
            x = nextUglyNumber(map, arr, k); //next ugly number > arr[k-1]
            k++;
        }
        
        return x;
    }
    
    //Search for the smallest ugly number > arr[k-1], put in arr[k].
    //key ideas: 
    //1. Since arr[k] is a ugly number, it must be divided by at least
    //   one of 2, 3, 5.
    //2. Each arr[k] >= 6 must be grown from some smaller ugly number
    //   (as a seed) by multiplying a seed by either 2, 3 or 5.
    //   Define 2-seed as the one from which the ugly number is grown
    //   by multiplying that seed by 2. So are 3- and 5-seed defined.
    //3. To calculate arr[k], which has either a factor of 2, 3, or 5,
    //   find the smallest ugly number > arr[k-1] with factor 2,
    //   the smallest ugly number > arr[k-1] with factor 3,
    //   the smallest ugly number > arr[k-1] with factor 5,
    //   then arr[k] must be the smallest of the above three numbers.
    //4. To find the smallest ugly number > arr[k-1] with factor 5,
    //   there are two possibilities: arr[k-1] can be divided by 5,
    //   and arr[k-1] cannot be divided by 5.
    //   (4.1) If arr[k-1] can be divided by 5, 
    //   then find out the corresponding 5-seed -- arr[k-1]/5 --
    //   from which arr[k-1] is grown from.
    //   Then find the seed (also a ugly number) succeeding arr[k-1]/5.
    //   This succeeding seed is the 5-seed from which
    //   a candidate of arr[k] is grown.
    //   Multiply that 5-seed by 5 to get that candidate.
    //   The result is the smallest ugly number > arr[k-1] and
    //   can be divided by 5.
    //   (4.2) If arr[k-1] cannot be divided by 5, then find the biggest
    //   ugly number y < arr[k-1] such that y can be divided by 5.
    //   Find the 5-seed of arr[?] where y is grown from 
    //   (ie, y = arr[?] * 5).
    //   Then arr[?+1] * 5 is the largest ugly number > arr[k-1]
    //   and arr[?+1] * 5 can be divided by 5. Why?
    //   Now we prove the above conclusion. 
    //   By the selection of arr[?], there is no ugly number in range
    //   (arr[?]*5, arr[k-1]] -- ie, the range does not include arr[?]*5 
    //   but include arr[k-1]) -- that can divided by 5.
    //   Then arr[?+1] * 5 must be out of the range of (arr[?]*5, arr[k-1]],
    //   so arr[?+1] *5 is the smallest ugly number > arr[k-1] that
    //   can be divided by 5.
    //5. Find out the candidates grown from 2-seed and 3-seed
    //   that is larger than arr[k-1]. Find out the smallest candidate.
    //6. What data structures need to be used to implement the above idea?
    //   To find out the index of a ugly number corresponds to, 
    //   we need to build a hash map mapping ith ugly number to i.
    //   To get to the next seed, we need an array to store all
    //   the previous ugly numbers.
    int nextUglyNumber(HashMap<Integer, Integer> map, 
        int[] arr, int k) {
        int next = candidateGrownFromSeed(map, arr, k, 5);
        next = Math.min(next, candidateGrownFromSeed(map, arr, k, 3));
        next = Math.min(next, candidateGrownFromSeed(map, arr, k, 2));
        
        arr[k] = next;
        map.put(next, k);
        return next;
    }
    
    //Find the smallest ugly number that is larger than arr[k-1]
    //and can be divided by seed.
    public int candidateGrownFromSeed(HashMap<Integer, Integer> map,
                            int[] arr, int k, int seed) {
        int start = arr[k-1];
        int index;
        int j = k;
        if (start % seed == 0)
            index = map.get(start/seed); //the index of start/seed in arr
        else {
               for (j = k-1; j >= 1; j--) 
                   //2, 3, 5 are in the first 6 elements,
                   //so there is an arr[j] divided by seed (2, 3, or 5).
                   if (arr[j] % seed == 0)
                      break;
               index = map.get(arr[j]/seed);
        }
        return arr[index+1] * seed;                        
    }
}
