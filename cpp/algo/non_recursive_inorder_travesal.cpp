/**
 * no-recusive inorder traversal - use stack.
 * http://www.cppblog.com/guogangj/archive/2009/10/16/98772.html
 *
 * NOTE: I tried to use standard library's stack for this execise. TODO still compilation error.
 *
 * Date: 01/04/22
 *
 * */
#include <iostream>
#include <stack>

using namespace std;

class TreeNode{
	public:				
		char m_cVal;
		TreeNode* m_pLeft;
		TreeNode* m_pRight;
		TreeNode(char v);
		~TreeNode();
};

TreeNode::TreeNode(char v){
	m_cVal = v;
	m_pLeft = 0;
	m_pRight = 0;	
}

TreeNode::~TreeNode(){}

int main(int argc, char** argv){
		//construct a tree				
    TreeNode nA('A');
    TreeNode nB('B');
    TreeNode nC('C');
    TreeNode nD('D');
    TreeNode nE('E');
    TreeNode nF('F');
    TreeNode nG('G');
    TreeNode nH('H');
    TreeNode nI('I');
    TreeNode nJ('J');
    TreeNode nK('K');
    TreeNode nL('L');

    nA.m_pLeft = &nB;
    nA.m_pRight = &nC;
    nB.m_pRight = &nD;
    nD.m_pRight = &nG;
    nC.m_pLeft = &nE;
    nC.m_pRight = &nF;
    nF.m_pRight = &nH;
    nH.m_pLeft = &nI;
    nH.m_pRight = &nJ;
    nI.m_pLeft = &nK;
    nI.m_pRight = &nL;

		stack<TreeNode*> st;

		//Inorder traversal
    TreeNode *pVal = &nA;
    int iPopped = 0;
    while(pVal!=0)
    {
        if(pVal->m_pLeft!=0 && iPopped==0)
        {
            st.push(pVal);
            pVal = pVal->m_pLeft;
            iPopped = 0;
        }
        else if(pVal->m_pRight!=0)
        {
            cout<<pVal->m_cVal<<" "<<endl;
            pVal = pVal->m_pRight;
            iPopped = 0;
        }
        else
        {
            cout<<pVal->m_cVal<<" "<<endl;
            if(0==st.pop(pVal))
                break;
            iPopped = 1;
        }
    }

    return 0;

}
