package tlc;

import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.TreeSet;

	public class Etat implements Comparable{
		String name;
		//static int codeEtat=-1;
		boolean isInitial;
		boolean isFinal;
		TreeMap	<Integer,Transition> Transition;
		public Etat(){
			name="";
			//codeEtat++;
			isInitial=false;
			isFinal=false;
			Transition=new TreeMap<Integer,Transition>();
		}
		public Etat(String nom){
			name=nom;
			//codeEtat++;
			isInitial=false;
			isFinal=false;
			Transition=new TreeMap<Integer,Transition>();
		}
		public Etat(int nom,TreeMap<Integer,Transition> t)
		{
			name=String.valueOf(nom);
			isInitial=false;
			isFinal=false;
			Transition=new TreeMap<Integer,Transition>();
			for(int i=0;i<t.size();i++)
			{
				Transition.put(i, t.get(i));
			}
		}
		public Etat(Etat e){
			name=e.name;
			Transition=new TreeMap<Integer,Transition>();
			for (int i=0;i<e.Transition.size();i++){
				Transition t=new Transition(e.Transition.get(i));
				Transition.put(i, t);
			}
			
		}
		public String getname(){
			return name;
		}
		public void setname(String nom){
			name=nom;
		}
		public void setFinal(){
			isFinal=true;
		}
		public void setInitial(){
			isInitial=true;
		}
		public String toString(){
			String stri="";
			String strf="";
			String ch="";
			String ch1="";
			String ch2="";
			for (int i=0;i<Transition.size();i++)//i==>une transition
			{
				ch+=Transition.get(i).etiquette+" ";
				ch1="Liste des etats cibles de la transition "+ch+"\n"+/*Transition.get(i).toString()+*/",";
				ch2+=Transition.get(i).toString()+",";
				ch1+=ch2;
				
			}
			if (isInitial==true)
				stri="cet etat est un etat initial";
			if  (isFinal==true)
				strf="cet etat est un etat final";
			return("nom:"+name+"\n"+stri+"\n"+strf+"\nla liste des transitions: "+ch1);
		}
		public void setTransitions(int i,Transition T){
			Transition.put(i, T);
		}
		//methode utilisé dans Expression reguliere, elle incremente le nom des etats d'une valeur inc
		public void changerNomEtats(int inc){
			int NameInt=Integer.parseInt(name);
			NameInt+=inc;
			String NewNameEtat=String.valueOf(NameInt);
			setname(NewNameEtat);
			//les etats cibles
			for (int t=0;t<Transition.size();t++){
				for(int ec=0;ec<Transition.get(t).EtatCible.size();ec++){
					String NewNameEC=Transition.get(t).EtatCible.get(ec);
					int NameIntEC=Integer.parseInt(NewNameEC);
					NameIntEC+=inc;
					NewNameEC=String.valueOf(NameIntEC);
					Transition.get(t).EtatCible.put(ec,NewNameEC);					
				}					
				}
			}
                @Override
		public int compareTo(Object arg0) {
			// TODO Auto-generated method stub
			return 0;
		}
}