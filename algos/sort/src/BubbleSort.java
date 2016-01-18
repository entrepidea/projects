public class BubbleSort {

	public static void bubbleSort(int[] data)
	{
	   for (int k = 0; k < data.length - 1; k++)
	   {
	      boolean isSorted = true;

	      for (int i = 1; i < data.length - k; i++)
	      {
	         if (data[i] < data[i - 1])
	         {
	            int tempVariable = data[i];
	            data[i] = data[i - 1];
	            data[i - 1] = tempVariable;

	            isSorted = false;

	         }
	      }

	      if (isSorted)
	         break;
	   }
	}
	
	public static void main(String[] args){
		int[] data = {3,4,1,8,0,-10,11,1000};	
		bubbleSort(data);
		for(int i : data){
			System.out.print(i);
			System.out.print(" ");
		}
		System.out.println();
	}
}
