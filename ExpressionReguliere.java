package tlc;

import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Vector;

public class ExpressionReguliere implements Comparable {

	public Postfixe expression;
	public Vector<Automate> pile;
	//private Automate aut0;
	
	public ExpressionReguliere(String expr){
		expression=new Postfixe(expr);
		expression.traiterExpression();
		pile=new Vector<Automate>();
	}
	public void setExpression(String expr){
		expression=new Postfixe(expr);
		expression.traiterExpression();
	}	
	public Automate automateEpsilon(){
		String str="0 1";//2 etats
		Automate aut=new Automate();
		aut.setEtats(str);
		Transition T=new Transition("epsilon");
		T.EtatCible.put(0,"1");
		aut.Etats.get(0).Transition.put(0,T);
		return aut;
		}
	public Automate automateLettre(String lettre){
		String str="0 1";//2 etats
		Automate aut=new Automate();
		aut.setEtats(str);
		aut.setEtatInitial("0");
		Transition T=new Transition(lettre);
		T.EtatCible.put(0,"1");
		aut.Etats.get(0).Transition.put(0,T);
		aut.setEtatsFinaux("1");
		return aut;
	}

	public Automate automate1OUautomate2 (Automate aut01,Automate aut02){//testé
		Automate aut1=new Automate(aut01);
		Automate aut2=new Automate(aut02);
		aut1.removeInitial();
		aut2.removeInitial();
		aut1.removeFinal();
		aut2.removeFinal();
		Automate aut=new Automate();
		//ajout des automates aut1 et aut 2----------------------------------------------------
		int i=1;
		for (Etat e:aut1.Etats.values()){
			aut.addEtat(i, e);
			aut.Etats.get(i).changerNomEtats(1);
			i++;
		}
		int i2=i;//etat initial de aut2 on le garde pour lier les transitions 
		for (Etat e:aut2.Etats.values()){
			aut.addEtat(i, e);
			aut.Etats.get(i).changerNomEtats(i2);
			i++;
		}
		//creation de l etat initial-----------------------------------------------------------
		Etat e0=new Etat("0");
		e0.setInitial();
		aut.Etats.put(0, e0);
		Transition T1=new Transition("epsilon");
		T1.EtatCible.put(0,aut.Etats.get(1).name);
		T1.EtatCible.put(1,aut.Etats.get(i2).name);
		aut.Etats.get(0).Transition.put(0, T1);	
		//creation de letat final--------------------------------------------------------------
		String nomEtatFinal=aut.Etats.get(i-1).getname();
		int n=Integer.parseInt(nomEtatFinal);
		n++;
		nomEtatFinal=String.valueOf(n);
		Etat ef=new Etat(nomEtatFinal);
		ef.setFinal();
		aut.addEtat(i, ef);
		Transition T2=new Transition("epsilon");
		T2.EtatCible.put(0,aut.Etats.get(i).name);
		aut.Etats.get(i2-1).Transition.put(0, T2);
		aut.Etats.get(i-1).Transition.put(0, T2);
		//lié les transitions epsilon à letat final	*/
		return aut;
	}
	
	public Automate automate1CONCATautomate2 (Automate aut01,Automate aut02){//testé
		Automate aut1=new Automate(aut01);
		Automate aut2=new Automate(aut02);
		aut2.removeInitial();
		aut1.removeFinal();
		Automate aut=new Automate();
		int i=0;
		for (Etat e:aut1.Etats.values()){
			aut.Etats.put(i,e);
			i++;
		}
		int i2=i;//etat initial de aut2 on le garde pour lier les transitions 			
		for (Etat e:aut2.Etats.values()){
			aut.addEtat(i, e);
			aut.Etats.get(i).changerNomEtats(i2);
			i++;
		}
		int j=0;
		Transition T1=new Transition("epsilon");
		T1.EtatCible.put(0,aut.Etats.get(i2).name);
		aut.Etats.get(i2-1).Transition.put(0, T1);
		return aut;
	}
	
	public Automate automateFermeture (Automate aut0){
		Automate aut1=new Automate(aut0);
		aut1.removeInitial();
		aut1.removeFinal();
		Automate aut=new Automate();
		int i=1;
		for (Etat e:aut1.Etats.values()){
			aut.Etats.put(i,e);
			aut.Etats.get(i).changerNomEtats(1);
			i++;
		}
		//creation de l etat initial et final----------------------------------------------------
		Etat e0=new Etat("0");
		e0.setInitial();
		aut.Etats.put(0, e0);	
		//creation de letat final--------------------------------------------------------------
		String nomEtatFinal=aut.Etats.get(i-1).getname();
		int n=Integer.parseInt(nomEtatFinal);
		n++;
		nomEtatFinal=String.valueOf(n);
		Etat ef=new Etat(nomEtatFinal);
		ef.setFinal();
		aut.addEtat(i, ef);
		//ajout des epsilons transitions--------------------------------------------------------
		Transition T1=new Transition("epsilon");
		T1.EtatCible.put(0,aut.Etats.get(1).name);//etat cible 1
		T1.EtatCible.put(1,aut.Etats.get(i).name);
		
		aut.Etats.get(0).Transition.put(0, T1);		
		aut.Etats.get(i-1).Transition.put(0, T1);
		return aut;
	}
	//------------------------------------------------------------------------------------
	public Automate automateThompson()
	{	
		Automate aut=new Automate();
		for(int i=0;i<expression.Resultat.size();i++){
			char c=expression.Resultat.get(i);
			String s=Character.toString(c);		
			if (s.compareTo(".")!=0&&s.compareTo("*")!=0&&s.compareTo("|")!=0)
			{
				aut=new Automate(automateLettre(s));
				pile.add(aut);
			}
			else
			{
				if (s.compareTo(".")==0)
				{
					aut=new Automate(automate1CONCATautomate2(pile.get(0), pile.get(1)));
					pile.remove(1);
					pile.remove(0);
					pile.add(aut);	
				}
				else
				{
					if (s.compareTo("|")==0)
					{
					aut=new Automate(automate1OUautomate2(pile.get(0), pile.get(1)));
					pile.remove(1);
					pile.remove(0);
					pile.add(aut);
					}
					else 
					{
					int n=pile.size();
					aut = new Automate(automateFermeture(pile.lastElement()));
					pile.remove(n-1);
					pile.add(aut);				
					}			
				}
			}				
}
	return aut;
}
        public Automate deterministe(){//automate de thompson l'etat 0 est tjrs l etat final selon les algorithmes traités
		Automate aut=automateThompson();
		TreeMap	<Integer,TreeSet> nouveauEtats=new TreeMap <Integer,TreeSet>();
		Automate autDeter= new Automate();
		TreeSet <Integer> trs=new TreeSet <Integer>();//remplie des indices des etats de aut
		//Etat e=new Etat(aut.Etats.get(0));
		nouveauEtats.put(0,epsilonFermeture(aut,0));
		Etat e=new Etat("0");
		autDeter.Etats.put(0, e);		
		int i=0;
		int k=0;//nbr transitions
		for (String alph:aut.alphabet){
			Transition t=new Transition(alph);
			TreeSet <Integer> x=transiter(aut,nouveauEtats.get(0),alph);
			int j=0;
			boolean exist=false;
			while (j<nouveauEtats.size() && exist==false){
				while(x.containsAll(nouveauEtats.get(j))==false){
					j++;
					exist=false;
				}//si exist j va retourner son indice ds nouveaux etats qui est le meme dans autDet
			}
			autDeter.Etats.get(0).Transition.put(k, t);
			if (exist==false){
				i++;
				nouveauEtats.put(i,x);
				e=new Etat(String.valueOf(i));
				autDeter.Etats.put(i, e);
				autDeter.Etats.get(0).Transition.get(k).EtatCible.put(0, String.valueOf(i));
				
			}
			else{
				autDeter.Etats.get(0).Transition.put(k, t);
				autDeter.Etats.get(0).Transition.get(k).EtatCible.put(0,String.valueOf(j));
			}
		}
			//---------------------
			for (int l:nouveauEtats.keySet()){
				for (String alph:aut.alphabet){
					Transition t=new Transition(alph);
					autDeter.Etats.get(l).Transition.put(k, t);
					TreeSet <Integer> x=transiter(aut,nouveauEtats.get(l),alph);
					int j=0;
					boolean exist=false;
					while (j<nouveauEtats.size() && exist==false){
						while(x.containsAll(nouveauEtats.get(j))==false){
							j++;
							exist=false;
						}//si exist j va retourner son indice ds nouveaux etats qui est le meme dans autDet
					}
					if (exist==false){
						i++;
						nouveauEtats.put(i,x);
						e=new Etat(String.valueOf(i));
						autDeter.Etats.put(i, e);
						autDeter.Etats.get(l).Transition.get(k).EtatCible.put(0, String.valueOf(i));
						
					}
					else{
						autDeter.Etats.get(l).Transition.put(k, t);
						autDeter.Etats.get(l).Transition.get(k).EtatCible.put(0,String.valueOf(j));
					}
			}
		}
		return autDeter;
	}
	////retourne l'epsilon fermeture d'un etat de clé key-----------------------------------
	public TreeSet<Integer> epsilonFermeture(Automate aut, int key)
	{
		TreeSet<Integer> trs =new TreeSet<Integer>();
		trs.add(key);
		boolean bool=false;
		int i=0;
		while (i<aut.Etats.get(key).Transition.size() && bool==false){

			if (aut.Etats.get(key).Transition.get(i).etiquette.compareTo("epsilon")==0)
				bool=true;
			i++;
		}
                i--;
		int j=0;
			while( j<aut.Etats.get(key).Transition.get(i).EtatCible.size()){				
				String nextEtat=aut.Etats.get(key).Transition.get(i).EtatCible.get(j);
				int keyEtat=Integer.parseInt(nextEtat);
				trs.add(keyEtat);
//				trs.addAll(epsilonFermeture(aut,keyEtat));
				j++;					
			}
		return trs;
	}
	public TreeSet<Integer> transiter(Automate aut,TreeSet<Integer> keyEtats,String transition){
		TreeSet<Integer> trs =new TreeSet<Integer>();
		for(int e:keyEtats){
			int i=0;
			if (aut.Etats.get(e).Transition.containsValue(transition)){
				while (aut.Etats.get(e).Transition.get(i).etiquette.compareTo(transition)!=0)
					i++;
				int j=0;
				while (j<aut.Etats.get(e).Transition.get(i).EtatCible.size())
				{
					trs.add(e);
					String nextEtat=aut.Etats.get(e).Transition.get(i).EtatCible.get(j);
					int keyEtat=Integer.parseInt(nextEtat);
					trs.add(keyEtat);
					trs.addAll(epsilonFermeture(aut,keyEtat));
					j++;
				}	
			}
			if (trs.isEmpty())
				trs.add(20);//etat puit
		}
			return trs;	
		}
	
	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}
 }
