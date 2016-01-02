package tlc;
import java.util.Vector;

public class Postfixe {
public Vector<Character> Resultat=null;
public Vector<Character> pile=null;
private final char ou='|';
private final char concat='.';
private final char  fermeture='*';

private String expression=null;//expression en infixé à transformer en postfixé

public Postfixe(String expression){
	 
	this.expression=expression;
	Resultat=new Vector<Character>();
	pile=new Vector<Character>();
	
}
public void setValue(String c){
	this.expression=c;
	this.Resultat.removeAllElements();	
}

public boolean isParentheseOuvrante(char c){
	if(c=='(') return true;
	return false;
	
}

public boolean isParentheseFermante(char c){
	if(c==')') return true;
	return false;
	
}
public boolean isOperande(char c){
	return (!isOperateur(c) && !this.isParenthese(c) );
}
public boolean isOperateur(char c){
	
	if(c==ou || c==concat||c==fermeture) return true;
	 return false;
}
public int prioriteOperateur(char c){
	
	if(c==ou || c==concat ) return 1;
	if(this.isParenthese(c)) return 0;
	 return 2;
}
	
private boolean isParenthese(char c) {
	// TODO Auto-generated method stub
	if(c=='(' || c==')') return true;
			return false;
}
public void afficher(){
	
	if(!this.Resultat.isEmpty())
	     for(int j=0;j<this.Resultat.size();j++) System.out.print(this.Resultat.elementAt(j));

}


public void traiterExpression(){//c'est là que tout se passe
	
if(isValide()){
	int n=this.expression.length();
	    for(int j=0;j<n;j++){
		         char c=this.expression.charAt(j);
	    	      if(this.isParentheseOuvrante(c))
	    	    	  this.pile.add(c);
	    	                       if(this.isOperande(c)) this.Resultat.add(c);
	    	                          if(this.isOperateur(c)){
	    	                        	       if(this.pile.isEmpty())   pile.add(c);
	    	                        	        else{
	    	                        	        	    char t=this.pile.get(this.pile.size()-1);//le sommet de lapile
	    	                        	        	   if(this.isOperateur(t) && this.prioriteOperateur(t)<this.prioriteOperateur(c)||this.isParenthese(t))
	    	                        	        		   pile.add(c);
	    	                        	        	      else {
	    	                        	        		         int y=pile.size();
	    	                        	        		             for(int h=y-1;h>=0;h--){
	    	                        	        		        	   char po=pile.get(h);
	    	                        	        		        	       if(this.isOperateur(po) && this.prioriteOperateur(po)<this.prioriteOperateur(c)|| this.isParenthese(po))
	    	                        	        		        	       break;
	    	                        	        		        	       this.Resultat.add(po);
	    	                        	        		        	       this.pile.removeElementAt(h);
	    	                        	        		               }
	    	                        	        		             this.pile.add(c);
	    	                        	        		   
	    	                        	        	       }
	    	                        	        	
	    	                        	        	
	    	                        	           }
	    	                        	  
	    	                        	  
	    	                                   }
	    	                       if(this.isParentheseFermante(c)){
	    	                    	      
	    	                    	   int y=pile.size();
      	        		             for(int h=y-1;h>=0;h--){//on depile
      	        		            	  char po=pile.get(h);
      	        		        	       if(this.isParentheseOuvrante(po)){
      	        		        	    	 this.pile.removeElementAt(h);
      	        		        	       break;
      	        		        	       }
      	        		        	       this.Resultat.add(po);
      	        		        	       this.pile.removeElementAt(h);
      	        		               }
	    	                    	   
	    	                    	   
	    	                       }   
	    
	    }
		int f=this.pile.size()-1;
while(!this.pile.isEmpty())	{
	this.Resultat.add(this.pile.get(f));
	this.pile.removeElementAt(f);
	f--;
}
	
	}

else
	System.out.println("Expession Vide!!!");
}
private boolean isValide() {
	// TODO Auto-generated method stub
	return (this.expression!=null );
}
}