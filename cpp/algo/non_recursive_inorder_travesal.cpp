/**
 * no-recusive inorder traversal - use stack.
 * http://www.cppblog.com/guogangj/archive/2009/10/16/98772.html
 *
 * NOTE: I tried to use standard library's stack for this execise. 
 * The implementation code actually borrowed from: https://mp.weixin.qq.com/s?__biz=MzUyNjQxNjYyMg==&mid=2247486365&idx=3&sn=0c9dd00f69159cfe2ad07899eaadb16f&chksm=fa0e641ccd79ed0a7b327fe338ff589499c0115048af185cc5d327ab6f4f8d85aec26f2de331&scene=21#wechat_redirect
 * which is easier to understand.
 *
 * Date: 01/09/22
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

		/*
		 *   How does the tree looks like after construction
    //      A
    //    /   \
    //   /     \
    //  B       C
    //   \     / \
    //    D   E   F
    //     \       \
    //      G       H
    //             / \
    //            I   J
    //           / \
    //          K   L
		 *
		 * */

		stack<TreeNode*> st;

		//Inorder traversal
    TreeNode *pCur = &nA;
    int iPopped = 0;
    while(pCur!=0||!st.empty())
		{
			while(pCur!=0){
				st.push(pCur);
				pCur = pCur->m_pLeft;	
			}				
			pCur = st.top();
			st.pop();
			cout<<pCur->m_cVal<<", ";		
			pCur = pCur->m_pRight;									
    }
		//the answer:
		//B, D, G, A, E, C, F, K, I, L, H, J,
		cout<<endl;
    return 0;

}
