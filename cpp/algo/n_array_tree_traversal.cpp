#include <vector>
#include <iostream>
/**
 *	Given the root of an n-ary tree, return the preorder traversal of its nodes' values.
		Nary-Tree input serialization is represented in their level order traversal. 
		Each group of children is separated by the null value (See examples)
 *
 * 	https://leetcode.com/problems/n-ary-tree-preorder-traversal/
 *
 * Date: 12/31/21
 *
 * */
using namespace std;

class Node {
	public:
		int val;
		vector<Node*> children;

		Node(){}

		Node(int _val){
			val = _val;
		}		

		Node(int _val, vector<Node*> _children){
			val = _val;
			children = _children;
		}
};

Node* construct_tree(vector<int> &v){
	for(int i=0;i<v.size();i++){
		cout<<v[i]<<endl;
	}

	Node* root = new Node(v[0]);				
	Node* rlt = root;
	int count = 0;									
	for(int i=1;i<v.size();i++){
		cout<<"come here...."<<"i="<<i<<",vector[i]="<<v[i] <<endl;				
		if(v[i]!=0){
			Node* temp = new Node(v[i]);
			cout<<"root value: "<<root->val<<", temp value: "<<temp->val<<endl;
			root->children.push_back(temp);
		}
		else{	//the element is NULL
			if(root->children.size()!=0){						
				root = root->children[count++];
			}
		}
	}				
	return rlt;
}

void traverse(Node* root, vector<int> &v){
	if (root ==NULL){
		return;
	}
	v.push_back(root->val);
	for(int i=0;i<root->children.size();i++){
		traverse(root->children[i],v);
	}
}

vector<int> preorder(Node* root){
	if(root==NULL){
		return {};
	}
	vector<int> v;
	traverse(root,v);
	return v;
}

int main(int argc, char** argv){
	vector<int> v{1,0,3,2,4,0,5,6};
 	Node* root = construct_tree(v);
	
	cout<<"tree constructed, root is: "<<root->val<<endl;

	vector<int> rlt = preorder(root);
	for(int i=0;i<rlt.size();i++){
		cout<<rlt[i]<<" ";
	}
	cout<<endl;

	//TODO the test below didn't work. The constructing of a tree out of an array
	//failed to handle the array which has two or more consecutive null (0) values.
	//
	int arr[] = {1,0,2,3,4,5,0,0,6,7,0,8,0,9,10,0,0,11,0,12,0,13,0,0,14};
	int n = sizeof(arr)/sizeof(arr[0]);
	vector<int> v2(arr,arr+n);
	for(int i=0;i<v2.size();i++){
		cout<<v2[i]<<",";
	}
	cout<<endl;
	root = construct_tree(v2);
	rlt = preorder(root);
	for(int i=0;i<rlt.size();i++){
		cout<<rlt[i]<<" ";
	}
	cout<<endl;

	return 0;
}
