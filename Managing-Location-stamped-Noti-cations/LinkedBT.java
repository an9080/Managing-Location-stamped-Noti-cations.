public class LinkedBT<T> implements BT<T> {
	BTNode<T> root,current;
	public LinkedBT() {
		current=root=null;
	}
	
	public boolean empty() {
		return root==null;
	}

	
	public boolean full() {
		return false;
	}

	
	public boolean find(Relative rel) {
	//if(current==null)return false;
		switch (rel) {
		   case Root:	// Easy case
			current = root;
			return true;
		   case Parent:
	       if(current == root) return false;
		    current=current.parent;
			return true;
		   case LeftChild:
			if(current.left == null) return false;
			current = current.left;
			return true;
		   case RightChild:
			if(current.right == null) return false;
			current = current.right;
			return true;
		   default:
			return false;
		}
	}

	

	
	public T retrieve() {
		return current.data;
	}

	public void update(T val) {
      current.data=val;		
	}

	
	public boolean insert(T val, Relative rel) {
		BTNode<T> temp= new BTNode<T>(val);
		switch(rel) {
		   case Root:
			if(!empty()) return false;
			current = root = temp;
			return true;
		   case Parent:	
         return false;
		   case LeftChild:
			if(current.left != null) return false;
			current.left = temp;
			temp.parent = current;
			current=temp;
			return true;
		   case RightChild:
			if(current.right != null) return false;
			current.right = temp;
			temp.parent = current;
			current=temp;
			return true;
		   default:
			return false;
		}
	
	}
 
	
	public void deleteSub() {
		if(current == root){
			current = root = null;
		}
		else {
			BTNode<T> p = current;
         current=current.parent;
			if(current.left == p)
				current.left = null;
			else 
				current.right = null;
			current = root;
		}

		
	}

	
	public boolean isLeaf() {
		if(current==null)return false;
	  if( (current.left==null) &&  (current.right==null))
      return  true ;
      else return false ;
	}


	public BTNode<T> getRoot() {
		return root;
	}
}