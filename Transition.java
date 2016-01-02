package tlc;

import java.util.Iterator;
import java.util.TreeMap;
import java.util.TreeSet;

public class Transition implements Comparable{
	String etiquette;//faut verifier qu'il appartient a un alphabet donnée
	TreeMap<Integer,String> EtatCible;//ou de type Etat
	
	public Transition(String etiq){
		etiquette=etiq;
		EtatCible=new TreeMap<Integer,String>() ;
	}
	public Transition(String c,int s)
	{
		etiquette=c;
		EtatCible=new TreeMap<Integer,String>() ;
		EtatCible.put(0,String.valueOf(s));	
	}
	public String toString(){
		String ch="";
		ch+=etiquette+":";
		for(int i=0;i<EtatCible.size();i++)
		{
				ch+=EtatCible.get(i)+" ";
		}
		return ch;
	}
	public void addEtatCible(int i,String S){
		EtatCible.put(i, S);
	}
	public void setEtatCible(int i)
	{
		TreeMap<Integer,String> EtatC=new TreeMap<Integer,String>() ;
		EtatC.put(0, String.valueOf(i));
		EtatCible=EtatC;
	}
	public Transition(String c,TreeSet<String> t){
		etiquette=c;
		EtatCible=new TreeMap<Integer,String>();
		Iterator iter=t.iterator();
		int i=0;
		while(iter.hasNext())
		{
			String stk4=(String)iter.next();
			EtatCible.put(i,stk4);
			i++;
			
		}
	}
	public Transition setEtiquette(String c)
	{
		this.etiquette=c;
		return this;
	}
	/*public Transition(Transition t)
	{	
		this.etiquette= new String(t.etiquette);
		EtatCible=new TreeMap<Integer,String>();
		if(t.EtatCible.size()!=0) EtatCible=t.EtatCible;
	}*/
	public Transition(Transition t)
	{	
		this.etiquette=t.etiquette;	
		EtatCible=new TreeMap<Integer,String>();
		if(t.EtatCible.size()!=0) 
			for( int i=0;i<t.EtatCible.size();i++){
				String ec=new String(t.EtatCible.get(i));
				EtatCible.put(i, ec);
			}
	}
	public int compareTo(Object arg0) {
	// TODO Auto-generated method stub
	return 0;
}
	public boolean setEtatFinal(TreeMap<Integer,String> f)
	{
		boolean b=false;
		int i=0;
		while(i<f.size())
		{
			for(int k=0;k<EtatCible.size();k++)
			{
				if(EtatCible.get(k).compareTo(f.get(i))==0) return true;
					
			}i++;
			}
			return b;
	}

	
	public int compareTo(Transition T) { 
        int b =0;
        b=etiquette.compareTo(T.etiquette);
        boolean bool=EtatCible.size()==T.EtatCible.size();
        if(bool==false) b=+2;
        for(int i=0;i<EtatCible.size();i++)
        {
        	if(!EtatCible.containsValue(T.EtatCible.get(i))) b++;
        }
        return b;
    }
	public boolean existe(TreeSet<Transition> t)
	{
		boolean b=false;
		Iterator iter=t.iterator();
		while(iter.hasNext())
		{
			if(((Transition)iter.next()).compareTo(this)==0)
				b=true;
		}
		return(b);	
	}
	public boolean existeTab(Transition[] t, int n)
	{
		boolean b=false;
		for(int i=0;i<n;i++)
		{
			if(t[i].compareTo(this)==0) b=true;
		}
		return(b);	
	}
	public boolean compareAscii(Transition T)
	{
		boolean b=false;
		int i=0;
		int j=0;
		  for(int k=0;k<EtatCible.size();k++)
	        {
			  	i=+(int)EtatCible.get(k).charAt(0);
	        }
		  for(int k=0;k<T.EtatCible.size();k++)
	        {
			  	j=+(int)T.EtatCible.get(k).charAt(0);
	        }
		  if(i==j) b=true;
		  return b;
		
	}
}