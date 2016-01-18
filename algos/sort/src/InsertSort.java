import java.util.Random;

public class InsertSort{

static void printData(int[] data){
		for(int d : data){
			System.out.print(d);
			System.out.print('\t');
		}
		System.out.println();
	}
	
	static int[] createData(int n){
		int[] data = new int[n];
		Random rand = new Random();
		for(int i=0;i<n;i++){
			data[i] = rand.nextInt(100);
		}
		return data;
	}
	
	/*static void sort(int[] data, int n){
		int numSorted = 1;
		int i;
		while(numSorted<n){
			int temp = data[numSorted];
			for( i=numSorted;i>0;i--){
				if(temp<data[i-1]){
					data[i] = data[i-1];
				}
				else{
					break;
				}
			}
			data[i]=temp;
			numSorted++;
		}
	}*/
	
	static void sort(int[] data, int n){
		for(int j=1;j<n;j++){
			for(int i=j;i>0;i--){
				if(data[i]<data[i-1]){
					int temp = -1;
					temp = data[i-1];
					data[i-1] = data[i];
					data[i] = temp;
				}
			}
		}
	}
	public static void main(String[] args) {
		int[] data = createData(10);
		printData(data);
		sort(data, data.length);
		printData(data);

	}

	
}
