package tlc;

import java.util.Iterator;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.TreeSet;

public class Automate implements Comparable{
	//String name;
		TreeMap <Integer,Etat> Etats;
		TreeSet <String> alphabet;
		
		public Automate(){
			Etats=new TreeMap <Integer,Etat>();
			alphabet=new TreeSet <String>();
		}
		public void setAlphabet(String str){
			StringTokenizer stk=new StringTokenizer(str," ,;:");
			String stk2;
			while(stk.hasMoreTokens()){
				stk2=stk.nextToken();
				alphabet.add(stk2);			
			}	
		}
		public Automate(Automate aut1){
			Etats=new TreeMap <Integer,Etat>();
			alphabet=new TreeSet <String>();
			for (int i=0;i<aut1.Etats.size();i++){
				Etat e=new Etat(aut1.Etats.get(i));
				Etats.put(i,e);
			}
			alphabet=aut1.alphabet;
		}
		public boolean inAlphabet(String str){//pour nous permettre de verifier si la transition appartient bien a l'alphabet
			boolean appartient=false;
			for(String s: alphabet){
				if (str.compareTo(s)==0){
					appartient=true;
				}		
			}
			return appartient;					
	}
		public void setEtats(String str){
			
			StringTokenizer stk=new StringTokenizer(str," ,;:");
			String stk2;
			int i=0;
			while(stk.hasMoreTokens()){
				stk2=stk.nextToken();
				Etat e=new Etat(stk2);
				Etats.put(i,e);
				i++;
			}		
		}
		public void setEtatInitial(String str){		
					for(Etat e:Etats.values()){
						if (e.name.compareTo(str)==0)
							e.setInitial();
					}
		}	
		public boolean etatExist(String str){
			boolean exist=false;
			for(Etat e: Etats.values()){
				if (e.name.compareTo(str)==0){
					exist=true;
				}
			}
			return exist;
		}
		public void setEtatsFinaux(String str){//testé reste exception à ajouter
			StringTokenizer stk=new StringTokenizer(str," ,;:");
			String stk2;
			while(stk.hasMoreTokens()){
				stk2=stk.nextToken();
					for(Etat e:Etats.values()){
						if (e.name.compareTo(stk2)==0)
							e.setFinal();
					}
				}	
		}
		public void setTransitions(){
                        Scanner sc=new Scanner(System.in);
			for(Etat i: Etats.values())
			{
				
                                System.out.println("donner les transitions de l'etat "+i.name+"\n");
				String str=sc.nextLine();
				int j=0;//parcoure les transitions de l'etat i
				StringTokenizer stk=new StringTokenizer(str," ,;:");
				String stk2;//stk2= une transition
				while(stk.hasMoreTokens())//&& inAlphabet(stk2) à ajouter apres
				{
					stk2=stk.nextToken();
					int k=0;//parcoure les etats cibles d'une transition
					
					Transition T=new Transition(stk2);
					System.out.println("donner les états cibles la transition "+stk2+" de l' etat "+i.name);		
					String str2=sc.nextLine();
					StringTokenizer stk3=new StringTokenizer(str2," ,;:");
					while(stk3.hasMoreTokens()){
						String stk4=stk3.nextToken();
						int h=0;
						while(h<Etats.size())
						{
							if (Etats.get(h).getname().compareTo(stk4)==0){
								T.EtatCible.put(k,stk4);
								k++;//un autre etat cible
							}
							h++;
						}
					}
							if (T.EtatCible!=null)
								i.Transition.put(j, T);
							j++;//une autre transition
						}
			}						
		}
		/*public String toString(){inutile
			String ch ="";
			//System.out.println("hello");
			for(int i=0;i<Etats.size();i++){
					ch+="Etat "+i+" "+Etats.get(i).toString()+"\n";
				//System.out.println(Etats.get(i).toString());
			}
			return ch;
		}*/
		public void afficherAutomate(){
			String ch="";
			for (Etat e:Etats.values()){
				for (Transition t:e.Transition.values()){
					for (String ec:t.EtatCible.values()){
						System.out.println("("+e.getname()+","+t.etiquette+")"+"=>"+ec);
					}
				}
			}
		}		
		public void addEtat(int i,Etat e){//pas encore utilisé
			
			Etats.put(i, e);
	}

		public int grepKeyEtat(String str){//testé pas encore utilisé
			int nb = -1;
			int i=0;
			while(i<Etats.size()){
				if(Etats.get(i).getname().compareTo(str)==0){
					nb=i;
				}
				i++;
			}
			return nb;
		}
		public int compareTo(Object arg0) {
			// TODO Auto-generated method stub
			return 0;
		}
		public  TreeMap<Integer,String> getFinaux()
		{
			TreeMap<Integer,String> tr=new TreeMap<Integer,String>();
			int k=0;
			for(int i=0;i<Etats.size();i++)
			{
				if(Etats.get(i).isFinal==true) {tr.put(k,Etats.get(i).name);k++;}
			}
			return tr;
		}
		
		public  Etat getInitial()
		{
			Etat e=new Etat();
			boolean trouve=false;
			int i=-1;
			while(trouve=false && i<Etats.size()){
				i++;
				if(Etats.get(i).isInitial==true){
					trouve=true;
					e=new Etat(Etats.get(i));
				}
			}
			return e;	
		}
	public Automate deterministe()
		{
			Transition Tnull=new Transition(" ");
			TreeMap<Integer,String> alph=new TreeMap<Integer,String>();
			alph=getMapFromSet(alphabet);
		boolean bool=true;
		TreeMap<Integer, Transition> Tr=new TreeMap<Integer, Transition>();
		Transition[] trt=new Transition[50];
		Transition[][] M=new Transition[50][alphabet.size()];
		Automate a=new Automate();
		int k=0;
		int f=0;
		a.alphabet=alphabet;
		for(int i=0;i<alph.size();i++)
		{
			TreeSet<String> t=new TreeSet<String>();
			t=getEtatCible(0,alph.get(i));
			M[k][i]=new Transition(alph.get(i),t);
			Transition trs=new Transition(M[k][i]);
			Tr.put(k+i,trs.setEtiquette(" "));
			trt[k+i]=M[k][i];
		}
		Transition[] trte=new Transition[50];
		int d=2;
		int n=0;
		while(f==0&&k<=n)
		{
		
			for(int i=0;i<alph.size();i++)
			{
				//System.out.println(i+"-"+k);
				Transition trs=new Transition(M[k][i]);
				trt[n]=M[k][i];
				trs.setEtiquette(" ");
				boolean b=trs.existeTab(trte, n);
				if(b==false)
				{
				trte[n]=trs;
				for(int v=0;v<alph.size();v++)
				{	
					TreeSet<String> t=new TreeSet<String>();
					for(int j=0;j<M[k][i].EtatCible.size();j++)
					{	
						TreeSet<String> l=new TreeSet<String>();
						l=getEtatCible(Integer.parseInt(M[k][i].EtatCible.get(j))-1,alph.get(v));
						if(!l.isEmpty())
						{
						Iterator iter=l.iterator();
						while(iter.hasNext())
						{
						t.add((String)iter.next());
						}
						}
					}
						M[n+1][v]=new Transition(alph.get(v),t);
						trs=new Transition(M[n+1][v]);
						Tr.put(d+v,trs.setEtiquette(" "));
						trt[d+v]=M[n+1][v];}
				n++;
				d+=2;}
		}
		k++;
		int h=0;
		while(h<Tr.size()&&bool==true)
		{
			if(Tr.get(h).existeTab(trte, n)==false) bool=false;
			else h++;
		}
		if(bool==true) f=1;
		}
		System.out.println("**********Affichage de la Matrice**************");
		afficheMatrice(M,n+1);
		a=transformation(M,n,alphabet.size(),trte);
		return a;	
			
		}
		public TreeSet<String> getEtatCible(int i,String c)
		{
			TreeSet<String> t=new TreeSet<String>();
			for(int k=0;k<Etats.get(i).Transition.size();k++)
			{
				if(Etats.get(i).Transition.get(k).etiquette.compareTo(c)==0)
				{
					for(int z=0;z<Etats.get(i).Transition.get(k).EtatCible.size();z++)
					{
						t.add(Etats.get(i).Transition.get(k).EtatCible.get(z));
					}
				}
			}
			return(t);	
		}
		public TreeMap<Integer,String> getMapFromSet(TreeSet<String> t)
		{
			TreeMap<Integer,String> tr=new TreeMap<Integer,String>();
			Iterator iter=t.iterator();
			int i=0;
			while(iter.hasNext())
			{
				tr.put(i,((String)iter.next()));
				i++;
			}
			return tr;
			
			
		}
		public void afficheMatrice(Transition[][] M,int k)
		{
			for(int j=0;j<k;j++)
			{
			for(int i=0;i<alphabet.size();i++)
			{
					System.out.print(M[j][i].toString()+"\t");
			}
			System.out.println(" ");
			}
		}
		public Automate transformation(Transition[][] M, int l,int c,Transition[] t)
		{
			boolean b=false;
			Automate a=new Automate();
			TreeMap<Integer,String> f=new TreeMap<Integer,String>();
			f=getFinaux();
			/*for(int i=0;i<f.size();i++)
			{
				System.out.println("les etats finaux sont"+f.get(i));
			}*/
			a.alphabet=this.alphabet;
			TreeMap <Integer,Etat> etat=new TreeMap<Integer,Etat>();
			//System.out.println(l);
			//System.out.println(c);
			for(int i=0;i<l+1;i++)
			{TreeMap<Integer,Transition> tm=new TreeMap<Integer,Transition>();
				for(int j=0;j<c;j++)
				{
					tm.put(j,M[i][j]);
					}
				Etat et=new Etat(i+1,tm);
				etat.put(i,et);
			}
			for(int i=0;i<etat.size();i++)
			{
				
				for(int j=0;j<c;j++)
				{	
					int ind=getIndex(etat.get(i).Transition.get(j),t,l);
					etat.get(i).Transition.get(j).setEtatCible(ind);
					
				}
			}
			for(int i=0;i<l;i++)
			{
				if(t[i].setEtatFinal(f)==true) {System.out.println(t[i].toString());etat.get(i+1).setFinal();}
			}
			a.Etats=etat;
			a.Etats.get(0).setInitial();
			return a;
			
		}
		public TreeMap<Integer,Transition> construireEtatVide(int i)
		{
			TreeMap<Integer,String> alph=new TreeMap<Integer,String>();
			alph=getMapFromSet(alphabet);
			TreeMap<Integer,Transition> tm=new TreeMap<Integer,Transition>();
			for(int j=0;j<alphabet.size();j++)
			{
				Transition Tr=new Transition(alph.get(j),i);
				tm.put(j,Tr);
			}
			return tm;
			
		}
		public int getIndex(Transition tr,Transition[] t,int n)
		{	
			int i=0;
			while(i<n)
			{	
				if(t[i].compareAscii(tr)==true) return(i+2);
						i++;
			}
			return(0);
		}
		public int existe(Transition[] t,int n)
		{
			Transition[] tr=new Transition[50];
			int k=0;
			tr[0]=t[0];
			k++;
			for(int i=1;i<n;i++)
			{
				if(!t[i].existeTab(tr,k)) {tr[k]=t[i];k++;}
			}
			for(int i=0;i<k;i++)
			{
				t[i]=tr[i];
			}
			return(k);
		}
		public Transition[] eliminerEtatVide(Transition[] t,int l)
		{
			int k=0;
			Transition[] tr=new Transition[50];
			for(int i=0;i<0;i++)
			{
				if(t[i].EtatCible.size()==0) {tr[k]=t[i];/*System.out.println("on est dans tr"+tr[k].toString())*/;k++;}
			}
			return tr;
			
			
		}
		public void removeInitial(){
			for (Etat e:Etats.values() ){
				if (e.isInitial==true)
					e.isInitial=false;
			}
		}
		public void removeFinal(){
			for (Etat e:Etats.values() ){
				if (e.isFinal==true)
					e.isFinal=false;
			}
		}
		public void renommerEtatsSelonKey(){
			int i=0;
			while (i<Etats.size()){
				
			}
		}	
                public boolean comparer(Automate aut0,Automate aut1){
                  
                    return false;
                }
                public void minimiser()
	{
		int k=0;
		int j=0;
		TreeMap<Integer,String> e1=new TreeMap<Integer,String>();
		TreeMap<Integer,String> e2=new TreeMap<Integer,String>();
		for(int i=0;i<Etats.size();i++)
		{
			if(Etats.get(i).isFinal==true) {e1.put(k,Etats.get(i).name);k++;}
			else{e2.put(j,Etats.get(i).name);j++;}
		}
		
		
		
	}
	public TreeMap<Integer,String> regroupement(TreeMap<Integer,String> t)
	{
		TreeMap<Integer,String> tm=new TreeMap<Integer,String>();
		boolean[][] M=new boolean[10][alphabet.size()];
		TreeMap<Integer,String> alph=new TreeMap<Integer,String>();
		alph=getMapFromSet(alphabet);
		TreeSet<String> ts=new TreeSet<String>();
		int k=0;
		if(t.size()==1) {tm.put(0,t.get(0));return tm;} 
		else
		{
			for(int i=0;i<t.size();i++)
			{
				for(int j=0;j<alphabet.size();j++)
				{
					ts=getEtatCible(Integer.parseInt(t.get(i))-1,alph.get(j));
					Iterator iter=ts.iterator();
					while(iter.hasNext())
					{
						if(t.containsValue((String)iter.next())) k++;
						i++;
					}
				if(k==ts.size()) M[i][j]=true;
				else M[i][j]=false;
				}
		
			}
		}
		
		
		return tm;
		
		
	}
}