public class BST <K extends Comparable<K>, T> implements Map<K, T> {//<T extends Comparable<T>>
	BSTNode<K,T> root, current;
	//new one ,two
	public BST() {
		root = current = null;
	}
//****************************************************************
   // Return true if the tree is empty. Must be O(1).
	public boolean empty() {
		return root == null;
	}
//****************************************************************
	// Return true if the tree is full. Must be O(1).
	public boolean full() {
		return false;
	}
//****************************************************************
	// Removes all elements in the map.
	public void clear() { root=null;}
	//*******************************************************************
	// Return the data of the current element
	public T retrieve() {
		return current.data;
	}
	//**********************************************************************
	// Update the data of current element.
	public void update(T e) {
		current.data = e;
	}
	//************************************************************************
	// Search for element with key k and make it the current element if it exists. If the element does not exist the current is unchanged and false is returned. This method must be O(log(n)) in average.
	public boolean find(K key) {
		BSTNode<K,T> p = root,q = root;
		if(empty()) return false;
		while(p != null) {
			q = p;
			if(p.key.compareTo(key) == 0) {
				current = p;
				return true;
			}
			else if(p.key.compareTo(key) == 1) //key.compareTo(p.key) < 0
				p = p.left;//p = p.right
			else
				p = p.right;//p = p.left;
		}
      current = q;
		return false;	}
	//*************************************************************************
	// Return the number of keys one needs to compare to in order to find key.
	public int nbKeyComp(K key) {
		int coun=0;
		BSTNode<K,T> p = root;
		if(empty()) return coun;
		while(p != null) {
			if(p.key.compareTo(key) == 0) {
				return coun;
			}
			else if(key.compareTo(p.key) < 0) //key.compareTo(p.key) < 0
				p = p.left;//p = p.right
			else
				p = p.right;//p = p.left;
			
			coun++;
		}return coun;}
	//******************************************************************************
	// Insert a new element if does not exist and return true. The current points to the new element. If the element already exists, current does not change and false is returned. This method must be O(log(n)) in average.
	public boolean insert(K key, T data) {
      BSTNode<K,T> p=root ; BSTNode<K,T> q = root;
      BSTNode<K,T> n=new BSTNode<K,T>(key,data);
      if(empty()){
      root=current=n;
      return true;
      }
      while(p!=null){
      q=p;
      if(p.key.compareTo(key)==0)
      return false ;
      else 
      if(key.compareTo(p.key)<0)
      p=p.left;
      else 
      p=p.right; }
      if(q.key.compareTo(key)>0){
      q.left=n;
      current=q.left;
      return true ;
      }
      else{
      q.right=n;
      current=q.right;
      return true;}}
	//********************************************************************************
	// Remove the element with key k if it exists and return true. If the element does not exist false is returned (the position of current is unspecified after calling this method). This method must be O(log(n)) in average.
	public boolean remove(K key) {
		K k1 = key;    
		   BSTNode<K,T> p = root;   
		   BSTNode<K,T> q = null;      
		   while (p != null) {  
		          if (k1.compareTo(p.key) <0) {  //k1 < p.key         
		            q =p;        
		            p = p.left; } 
		            else if (k1.compareTo(p.key)  >0 ) { //k1 > p.key
		            q = p;  
		             p = p.right;} 
		             else {         
		   if ((p.left != null) && (p.right != null)) { 
		      BSTNode<K,T> min = p.right;      
		               q = p;         
		 while (min.left != null) {       
		  q = min;            
		   min = min.left;      
		    }         
		   p.key = min.key;        
		  p.data = min.data;   
		  k1 = min.key;             
		   p = min; }
		if (p.left != null) { 
		p = p.left; } 
		else {  
		 p = p.right;  } 
		if (q == null) {    
		root = p;   } 
		else {
		if (k1.compareTo(q.key) < 0) {//k1 < q.key   ?? p.key
		q.left = p; } 
		else {  
		q.right = p;   }}
		 current = root;
		 return true;}}
		  return false; }
	//*************************************************************************
	// Return all data in the map in increasing order of the keys.
	public List<Pair<K, T>> getAll() {
		List<Pair<K, T>> Lis= new LinkedList<Pair<K,T>>();
      BSTNode<K,T>  p=root;
		   Lis=recgetAll(p,Lis);
		   return Lis;}
		   private List<Pair<K, T>> recgetAll(BSTNode<K,T> p,List<Pair<K, T>> list){
		   if (p == null) return list ; 
		   recgetAll(p.left, list);
		     Pair<K,T> pL=new Pair<K,T>(p.key,p.data);
		    list.insert(pL);
		    recgetAll(p.right, list);
		     return list;}
	//*************************************************************************
	// Return all elements of the map with key k such that k1 <= k <= k2 in increasing order of the keys.
	public List<Pair<K,T>> getRange(K k1, K k2) {
		 List<Pair<K,T>> q=new  LinkedList<Pair<K,T>>();
		 K ke=k1 , kp=k2;
		 BSTNode<K,T> p=root; 
			return getlikelist(p,kp,q,ke);
	}
	                                                       //next k2                     //first k1
	   private List<Pair<K, T>> getlikelist(BSTNode<K,T> p, K ke,List<Pair<K, T>> list,K k){
		   if(p==null) {return list;}
		   if(p.key.compareTo(k)<0) {
			   getlikelist(p.right,ke,list,k);}
		   if(p.key.compareTo(ke)>0) {
		 getlikelist(p.left,ke,list,k); }
		   if((p.key.compareTo(k)>=0)&&(p.key.compareTo(ke)<=0)) {
				   getlikelist(p.left,ke,list,k);
		   Pair<K, T> L =new Pair<K, T>(p.key,p.data);
		   list.insert(L);
		   getlikelist(p.right,ke,list,k); }
		   return list;
		   }
	//**************************************************************************
	// Return the number of keys one needs to compare to in order to find all keys in the range [k1, k2].
	public int nbKeyComp(K k1, K k2) {
		 int count =0;
		   if(empty()) return count;
		   if(k1.compareTo(k2)>0)return 1;
        if(k1.compareTo(k2)==0)return 1;
			List<Pair<K, T>> Lis2 = getAll();
			Lis2.findFirst();
			while(!Lis2.last()) {
			if(Lis2.retrieve().first.compareTo(k1) > 0)	{
			if(Lis2.retrieve().first.compareTo(k2)  < 0)	
			count++;}
			 Lis2.findNext(); }
	        return count;}}

