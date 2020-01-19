
public class BTUtils {
public static <T> int nbLeaf(BT<T> bt, T e) {
		if(bt.empty())return 0;
			bt.find(Relative.Root);
			return  nbLeafrcs(bt,e);
			}
//*****************************************************************************
	private  static <T> int nbLeafrcs(BT<T> bt, T e) {
		int k=0 , j=0;
		if(bt.empty())return  0;
      if(bt.isLeaf() && (bt.retrieve()==e)) {  
      bt.find(Relative.Parent);
	      return 1; }
	   //if(bt.isLeaf()) return 0;
		if(bt.find(Relative.RightChild))
		{
			j=nbLeafrcs(bt,e);
		}
      if(bt.find(Relative.LeftChild))
		{
			k=nbLeafrcs(bt,e);
		}
		bt.find(Relative.Parent);
	    return k+j;}
	
		
//*******************************************************************
 public static <T> void pruneBranch(BT<T> bt, T e) {
		if(bt.empty())return;
bt.find(Relative.Root);
foundpruneBranch(bt,e);

	}
 
	private static <T> void foundpruneBranch(BT<T> bt, T e) {
		if(bt.empty())return ;
			if(bt.retrieve()==(e)) {
				bt.deleteSub(); 
		     return;}
	    else
		if( bt.find(Relative.LeftChild))	
		{	foundpruneBranch(bt,e);
		}
		else
			if(bt.find(Relative.RightChild))
			{	foundpruneBranch(bt,e);	
	              }bt.find(Relative.Parent);	}    }


