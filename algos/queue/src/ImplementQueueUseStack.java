//Implement the following operations of a queue using stacks.

//push(x) -- Push element x to the back of queue.
//pop() -- Removes the element from in front of queue.
//peek() -- Get the front element.
//empty() -- Return whether the queue is empty.
//Notes:
//You must use only standard operations of a stack -- 
//which means only push to top, peek/pop from top, size, 
//and is empty operations are valid.
//Depending on your language, stack may not be supported natively. 
//You may simulate a stack by using a list or deque (double-ended queue), 
//as long as you use only standard operations of a stack.
//You may assume that all operations are valid 
//(for example, no pop or peek operations will be called on an empty queue).

class MyQueue {
    Stack<Integer> que;
    
    public MyQueue() {
        que = new Stack<Integer>();
    }
    
    // Push element x to the back of queue.
    public void push(int x) {
        que.push(x);
    }

    // Removes the element from in front of queue.
    public void pop() {
        Stack<Integer> tmp = new Stack<Integer>();
        int elm;
        
        //Put the elements in sk to tmp.
        //Suppose the elements in sk is a->b->c->d,
        //where a is push to sk first while d is pushed in sk last,
        //after the following while-loop,
        //tmp has elements d->c->b->a.
        while (que.size() > 0) {
            elm = que.pop();
            tmp.push(elm);
        }
        
        tmp.pop(); //remove the first element in the que.
        
        //put the rest elements in tmp back to sk.
        while (tmp.size() > 0) {
            elm = tmp.pop();
            que.push(elm);
        }
    }

    // Get the front element.
    public int peek() {
        //The following if-statement is not needed
        //if we can make sure that peek operation
        //will not be performed on an empty queue/stack.
        //if (que.isEmpty())
        //   return (int)Float.NaN; //NaN means "not a number"
           //Note that there is no such thing as Integer.NaN in java. 
           
        Stack<Integer> tmp = new Stack<Integer>();
        
        //Warning:without the following two statements, 
        //the system might complain that elm may not be initialized.
        int elm = que.pop();
        tmp.push(elm);
        
        //Put the elements in sk to tmp.
        //Suppose the elements in sk is a->b->c->d,
        //where a is push to sk first while d is pushed in sk last,
        //after the following while-loop,
        //tmp has elements d->c->b->a.
        while (que.size() > 0) {
            elm = que.pop();
            tmp.push(elm);
        }
        int firstElm = elm;
        
        //put the elements in tmp back to sk.
        while (tmp.size() > 0) {
            elm = tmp.pop();
            que.push(elm);
        }
        
        return firstElm;
    }

    // Return whether the queue is empty.
    public boolean empty() {
        return que.isEmpty();    
    }
}
