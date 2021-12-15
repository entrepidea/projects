#include <vector>
/**
 * @Desc: create a simple memory pool
 * @Source: 算法竞赛入门经典（习题与解答）, 1.1.6
 * @Date: 10/20/19
 * 
*/
using namespace std;

template<typename T>
struct Pool{
    private:
        vector<T*> buf;
    public:
        T* create_new(){
            buf.push_back(new T());
            return buf.back();
        }
        void dispose(){
            for(int i=0;i<buf.size();i++){
                delete buf[i];
            }
        }
};

struct Node{};

int main(int argc, char** argv){
    Pool<Node> p;
    for(int i=0;i<10;i++){
        p.create_new();
    }
    p.dispose();
    return 0;
}