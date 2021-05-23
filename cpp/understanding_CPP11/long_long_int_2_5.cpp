#include <climits>
#include <cstdio>

using namespace std;

int main(){
	long long int ll_min = LLONG_MIN;
	long long int ll_max = LLONG_MAX;
	unsigned long long ull_max = ULLONG_MAX;

	printf(" min of long long int is: %lld\n", ll_min);
	printf(" max of long long int is: %lld\n", ll_max);
	printf(" max of unsigned long long is: %llu\n", ull_max);

	return 0;
}

