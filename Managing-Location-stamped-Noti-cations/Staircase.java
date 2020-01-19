public class Staircase {
	
	
	public static BT<Integer> getTree(int n, int k1, int k2) {//it ok
		BT<Integer> bt=new LinkedBT<Integer>();
		bt.insert(0,Relative.Root);
		grttreerce(bt,n,k1,k2);
	return bt;
	}
	private static BT<Integer> grttreerce(BT<Integer> bt,int n, int k1, int k2){//it' ok
		int coun=bt.retrieve();
		if(coun+k1<=n) {
			bt.insert(coun+k1,Relative.LeftChild);
			grttreerce(bt,n,k1,k2);
			bt.find(Relative.Parent);}
	      if(coun+k2<=n) {
			bt.insert(coun+k2,Relative.RightChild);
			grttreerce(bt,n,k1,k2);
			bt.find(Relative.Parent);}
         return bt;
          }
	
	public static BT<Integer> getTreeWithout(int n, int k1, int k2, int k) {//*eseption
		BT<Integer> bt=new LinkedBT<Integer>(); //			bt = Staircase.getTreeWithout(4,1,2,3);
		bt=getTree(n,k1,k2);
		 BTUtils.pruneBranch(bt,k);
       return bt;
		}
	
	/*private static void gettreewrce(BT<Integer> bt,int e){
		if(bt.empty())return ;
			if(bt.retrieve()==(e)) {
				bt.deleteSub(); 
		     return;}
	    else
		if( bt.find(Relative.LeftChild))	
		{	gettreewrce(bt,e);
		}
		else
			if(bt.find(Relative.RightChild))
			{	gettreewrce(bt,e);	
	}
			bt.find(Relative.Parent);		
}*/
	
	//************************ count number **********************************
	public static int  getNbSol(int n, int k1, int k2) {
		BT<Integer> bt=new LinkedBT<Integer>();
		 bt=getTree(n,k1,k2);//			int n = Staircase.getNbSol(5, 1, 2);
		/*if(bt.empty())return 0;
		bt.find(Relative.Root);
			int j=count(bt,n);*/
			return BTUtils.nbLeaf(bt,n);
	}
	public static int getNbSolWithout(int n, int k1, int k2, int k) {
		BT<Integer> bt=new LinkedBT<Integer>();
		bt=getTreeWithout(n,k1,k2,k);
      return BTUtils.nbLeaf(bt,n);
}

	/*private static int count(BT<Integer> bt,int e) {
	int k=0 , j=0;
		if(bt.empty())return  0;
      if(bt.isLeaf() && (bt.retrieve()==e)) {  
      bt.find(Relative.Parent);
	      return 1; }
		if(bt.find(Relative.RightChild))
		{
			j=count(bt,e);
		}
      if(bt.find(Relative.LeftChild))
		{
			k=count(bt,e);
		}
		bt.find(Relative.Parent);
	    return k+j;}*/}


