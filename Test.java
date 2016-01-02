package tlc;
import java.util.Scanner;
import java.util.TreeSet;
import java.util.Vector;

public class Test {
public static void main(String args[]){
		//----------------------------------------Automate--------------------------------------------
                Automate a=new Automate();
                Automate deterministe=new Automate();
                Automate optimale=new Automate();
		System.out.println("veuillez entrer la liste des elements de l'alphabet a1 a2 a3...");
		Scanner sc=new Scanner(System.in);
		String str=sc.nextLine();
		a.setAlphabet(str);
		System.out.println("veuillez entrer la liste des états etat1 etat2 etat3...");
		str=sc.nextLine();
		a.setEtats(str);
		System.out.println("veuillez entrer l'etat initial");
		str=sc.nextLine();
		a.setEtatInitial(str);
		System.out.println("veuillez entrer la liste des états finaux etat1 etat2...");
		str=sc.nextLine();
		a.setEtatsFinaux(str);
		a.setTransitions();
		System.out.println("****************************Affichage de l'AEF ****************************");
		a.afficherAutomate();
		System.out.println("******************Transformation en AEF Deterministe**********************");
		deterministe=a.deterministe();
                deterministe.afficherAutomate();
                System.out.println("*****************Minimisation du nombre d'etats de l'AEF******************");
                //optimale=deterministe.minimiser();
		//-----------------------------------Expression regulière-----------------------------------------------
		System.out.println("Veuillez entrer l'expression régulière de l'automate (les operateurs utilisés sont '.', '*' et '|' et les parenthèses): ");
                Scanner sc2=new Scanner(System.in);
		String str2=sc2.nextLine();
		ExpressionReguliere expr=new ExpressionReguliere(str2);
		Automate aut=expr.automateThompson();
		System.out.println("l'automate non déterministe de cette expression régulière est: ");
                aut.afficherAutomate();
                System.out.println("l'automate optimale de cette expression reguliere est: ");
                /*aut=expr.deterministe();
                aut=aut.minimiser();
                aut.afficherAutomate();
                if (aut.comparer(aut, a))
                    System.out.println("Cette expression est bien accepté par cette automate");
                else {
                    System.out.println("Cette expression n'est pas accepté par cette automate");
                };*/
                
}
}