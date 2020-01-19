import java.io.*;
import java.util.*;
public class LocNotManager {
//*new will
	// Load notifications from file. Assume format is correct. The notifications are
	// indexed by latitude then by longitude.
	public static Map<Double, Map<Double, LocNot>> load(String fileName) {//O.O
  		Map<Double, Map<Double, LocNot>> po1 = new BST<Double, Map<Double, LocNot>>();//temp
		Map<Double, LocNot> po2 = new BST<Double, LocNot>();
		try {
			File f = new File(fileName);
			 Scanner pp = new Scanner(f);//input
		 while(pp.hasNext()){
			 double lat = pp.nextDouble();
	            double lng = pp.nextDouble();
	            int maxNbRepeats=pp.nextInt();
	            int nbRepeats=pp.nextInt();
	            String text=pp.nextLine();
	    		LocNot lop=new LocNot (text,lat,lng,maxNbRepeats,nbRepeats);
	    		po2.insert(lop.getLng(), lop);//addnot method 
	    		po1.insert(lop.getLat(), po2);} pp.close();
		}
		   catch(FileNotFoundException e){e.printStackTrace();}
		return   po1;}    
//************************************************************************************************
	// Save notifications to file.  
	public static void save(String fileName, Map<Double, Map<Double, LocNot>> nots){//O.O
        try {
		File f = new File(fileName);
	   FileOutputStream os= new FileOutputStream (f);
	   PrintWriter pw = new PrintWriter(os);
	   pw.println(nots.retrieve().toString());
       pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
//*********************************************************************************
	// Return all notifications sorted first by latitude then by longitude. 
	public static List<LocNot> getAllNots(Map<Double, Map<Double, LocNot>> nots) {
		List<Pair<Double, Map<Double, LocNot>>>  l = new LinkedList<Pair<Double, Map<Double, LocNot>>>(); // return 
       List<LocNot> ln=new LinkedList <LocNot>();//stor
	    l=nots.getAll(); //link-list  List<Pair<Double, Map<Double, LocNot>>> 
	    if(!l.empty()){//l is emt
	    l.findFirst();
	    //^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
	    while(!l.last()) {
		List<Pair<Double,LocNot>> lp= (List<Pair<Double, LocNot>> )l.retrieve().second.getAll();//l the in 
		lp.findFirst();
		//List<Pair<Double,LocNot>>
      if(!lp.empty()){
		while(!lp.last()) {
	    ln.insert(lp.retrieve().second);
		lp.findNext();}
		ln.insert(lp.retrieve().second);} 
		l.findNext();}
	    //last l 
	   /* Map<Double, LocNot> m=l.retrieve().second;
	    List<Pair<Double,LocNot>> lp=m.getAll();*/
       List<Pair<Double,LocNot>> lp= (List<Pair<Double, LocNot>> )l.retrieve().second.getAll();//l the in 
	    lp.findFirst();
	    while(!lp.last()) {
		ln.insert(lp.retrieve().second);
		lp.findNext();}
	    ln.insert(lp.retrieve().second);//last
	    return ln;}return ln;}
    //***********************************************************************************
	// Add a notification. Returns true if insert took place, false otherwise.
	public static boolean addNot(Map<Double, Map<Double, LocNot>> nots, LocNot not){
		Map<Double, LocNot> lp= new BST<Double, LocNot>();
      //LocNot o=new LocNot(((LocNot) nots.retrieve()).getText(),((LocNot) nots.retrieve()).getLat(),((LocNot) nots.retrieve()).getLng(),((LocNot) nots.retrieve()).getMaxNbRepeats(),((LocNot) nots.retrieve()).getNbRepeats());
      if(nots.find(not.getLat())) {//small node 
			lp=nots.retrieve();
         lp.insert(not.getLng(),not);}//big node 
		else {lp.insert(not.getLng(),not) ;
			  nots.insert(not.getLat(),lp);}return true ;}
//*****************************************************************************
	// Delete the notification at (lat, lng). Returns true if delete took place, false otherwise.
	public static boolean delNot(Map<Double, Map<Double, LocNot>> nots, double lat, double lng) {//O.O
       // LocNot o=new LocNot(((LocNot) nots.retrieve()).getText(),((LocNot) nots.retrieve()).getLat(),((LocNot) nots.retrieve()).getLng(),((LocNot) nots.retrieve()).getMaxNbRepeats(),((LocNot) nots.retrieve()).getNbRepeats());
        List<LocNot>  lis=getAllNots(nots);
        lis.findFirst();
        if(!lis.empty()) {
        	while(!lis.last()) {
        		if((lis.retrieve().getLat() == lat) && (lis.retrieve().getLng()==lng)) {
        		lis.remove();return true ; }
        		lis.findNext();} }
		        return false;}
//****************************************************************************
	// Return the list of notifications within a square of side dst (in meters) centered at the position (lat, lng) (it does not matter if the notification is active or not). Do not call Map.getAll().
	public static List<LocNot> getNotsAt(Map<Double, Map<Double, LocNot>> nots, double lat, double lng, double dst) {
		//find interval of latitude[lat1,lat2]
		double po=GPS.angle(dst/2);//convert (distance /2) to angel 
		double lat2=lat+po;//lat2=add the angle to latitude 
		double lat1=lat-po; //  lat1=latitude-angle 
		//find interval of longitude [long1,long2]
		double Lng2=lng+po;//long2=add the angle to longitude
		double Lng1=lng-po;//  long1=longitude-angle 
		//^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
		//double loc=GPS.dist(lat1, Lng1, lat2, Lng2);//distance
		List<Pair<Double, Map<Double, LocNot>>>  l = new LinkedList<Pair<Double, Map<Double, LocNot>>>(); 
         l=nots.getRange(lat1,lat2); //*
         List<LocNot> ln=new LinkedList<LocNot>();
         if(l.empty())return ln;//getRange lat 
        	 l.findFirst();
        	 while(!l.last()) {
        		 Map<Double, LocNot> m =l.retrieve().second;
        		 List<Pair<Double,LocNot>> lp=m.getRange(Lng1,Lng2);
             if(!lp.empty()){
        		 lp.findFirst();
        			while(!lp.last()) {
        				ln.insert(lp.retrieve().second);
        				lp.findNext();}//insert into ln 
                  
        			ln.insert(lp.retrieve().second);//last
        			l.findNext();}}//last
            
        		Map<Double, LocNot> m =l.retrieve().second;
        		 List<Pair<Double,LocNot>> lp=m.getRange(Lng1,Lng2);
             if(!lp.empty()){
        		 lp.findFirst();
        		while(!lp.last()) {
        		ln.insert(lp.retrieve().second);
        		lp.findNext();}
        		ln.insert(lp.retrieve().second);//last
        	 }return ln;} 
//*****************************************************************************
	// Return the list of active notifications within a square of side dst (in meters) centered at the position (lat, lng). Do not call Map.getAll().
	public static List<LocNot> getActiveNotsAt(Map<Double, Map<Double, LocNot>> nots, double lat, double lng, double dst) {
		double po=GPS.angle( dst/2);
		double lat2=lat+po; 
		double lat1=lat-po; 
		double Lng2=lng+po;
		double Lng1=lng-po;
		//^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
		List<Pair<Double, Map<Double, LocNot>>>  l = new LinkedList<Pair<Double, Map<Double, LocNot>>>(); 
         l=nots.getRange(lat1,lat2); 
         List<LocNot> ln=new LinkedList<LocNot>();
         if(!l.empty()){
          if(!l.last()) {
        	 l.findFirst();
        	 while(!l.last()) {
        		 Map<Double, LocNot> m =l.retrieve().second;
        		 List<Pair<Double,LocNot>> lp=m.getRange(Lng1,Lng2);
             if(!lp.empty()){
        		 lp.findFirst();
        			while(!lp.last()) {
        				ln.insert(lp.retrieve().second);
        				lp.findNext();}
        			ln.insert(lp.retrieve().second);//last
        			l.findNext();}}//last
        		Map<Double, LocNot> m =l.retrieve().second;
        		 List<Pair<Double,LocNot>> lp=m.getRange(Lng1,Lng2);
             if(!lp.empty()){
        		 lp.findFirst();
        		while(!lp.last()) {
        		ln.insert(lp.retrieve().second);
        		lp.findNext();}
        		ln.insert(lp.retrieve().second);//last
        	 }}}
		//^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
		if(!ln.empty()) {//*l
			ln.findFirst();
			while(!ln.last()) {
				if(!ln.retrieve().isActive())
					ln.remove();
				ln.findNext();}
			if(!ln.retrieve().isActive()) //last 
				ln.remove();}
			return ln ;
		}
//******************************************************************************
	// Perform task of any active notification within a square of side dst (in meters) centered at the position (lat, lng) (call method perform). Do not call Map.getAll().
	public static void perform(Map<Double, Map<Double, LocNot>> nots, double lat, double lng, double dst) {
	   List<LocNot> l = new LinkedList<LocNot>();
       l=getActiveNotsAt(nots,lat,lng,dst);
       if(!l.empty()) {//isActive 
    	   l.findFirst();
    	   while(!l.last()) {
        if(l.retrieve().isActive())
    	 l.retrieve().perform();//incras  nbreq
          l.findNext(); }
     if(l.retrieve().isActive())
    	 l.retrieve().perform();}
       return ;
       }
//******************************************************************************
	// Return a map that maps every word to the list of notifications in which it appears. The list must have no duplicates.
	public static Map<String, List<LocNot>> index(Map<Double, Map<Double, LocNot>> nots) {
		Map<String, List<LocNot>> b=new BST<String, List<LocNot>>();
		List<Pair<Double, Map<Double, LocNot>>>  l = new LinkedList<Pair<Double, Map<Double, LocNot>>>(); 
	    l=nots.getAll(); 
	    List<LocNot> ln=new LinkedList <LocNot>();
	    if(!l.empty()) {
	    l.findFirst();
	    //^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
	    while(!l.last()) {
		List<Pair<Double,LocNot>> lp= (List<Pair<Double, LocNot>> )l.retrieve().second.getAll();
		if(!lp.empty()) {
		lp.findFirst();
		while(!lp.last()) {
	    ln.insert(lp.retrieve().second);
		lp.findNext();}
		ln.insert(lp.retrieve().second);//last lp
		l.findNext();}}
	    //last l 
	    Map<Double, LocNot> m=l.retrieve().second;
	    List<Pair<Double,LocNot>> lp=m.getAll();
	    if(!lp.empty()) {
	    lp.findFirst();
	    while(!lp.last()) {
		ln.insert(lp.retrieve().second);
		lp.findNext();}
	    ln.insert(lp.retrieve().second);}}
		//^^^^^^^^^^^^^^
		List<String> w = new LinkedList<String>();
		if(!ln.empty()) {
		ln.findFirst();
		while(!ln.last()) {
			String t = ln.retrieve().getText();
			String [] s=t.split(" "); //split 
			for(String wo : s) {
				w.insert(wo);}
			ln.findNext();}}//*l
		if(!w.empty()) {
		w.findFirst();
		while(!w.last()) {
			List<LocNot> loc = new 	LinkedList <LocNot> ();
			if(!l.empty()) {
			l.findFirst();
			while(!l.last()) {
				String t = ln.retrieve().getText();
				if(t.compareTo(w.retrieve())==0) {//contains
					loc.insert(ln.retrieve());}
				l.findNext();}
				b.insert(w.retrieve(), loc);
				w.findNext();}}}
			return b;}
//*******************************************************************************
	// Delete all notifications containing the word w. 		List<LocNot> l=getAllNots(nots);
	public static void delNots(Map<Double, Map<Double, LocNot>> nots, String w) {//O.O
     List<Pair<Double, Map<Double, LocNot>>>  l = new LinkedList<Pair<Double, Map<Double, LocNot>>>() ; 
		l.findFirst();
      if(!l.empty()){
      while(!l.last()){
      if(l.retrieve().toString().contains(w))
      l.remove();
      l.findNext();}
       if(l.retrieve().toString().contains(w))
      l.remove();}}
    //********************************************************************************
	// Print a list of notifications in the same format used in file.
	public static void print(List<LocNot> l) {
		System.out.println("-------------------------------------------------------------------------------------");
		if (!l.empty()) {
			l.findFirst();
			while (!l.last()) {
				System.out.println(l.retrieve());
				l.findNext();
			}
			System.out.println(l.retrieve());
		} else {
			System.out.println("Empty");
		}
		System.out.println("------------------");
	}
//********************************************************************************
	// Print an index.
	public static void print(Map<String, List<LocNot>> ind) {
		System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		List<Pair<String, List<LocNot>>> l = ind.getAll();
		if (!l.empty()) {
			l.findFirst();
			while (!l.last()) {
				System.out.println(l.retrieve().first);
				print(l.retrieve().second);
				l.findNext();
			}
			System.out.println(l.retrieve().first);
			print(l.retrieve().second);
		} else {
			System.out.println("Empty");
		}
		System.out.println("++++++++++++++++++");
	}

}
