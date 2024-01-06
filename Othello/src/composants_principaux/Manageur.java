package composants_principaux;

import java.util.Stack;


public class Manageur {
	public Stack<Commande> annuler = new Stack<Commande>();
	public Stack<Commande> refaire = new Stack<Commande>();
		

	public void executionCommande(Commande commande) {
		commande.execution();
		annuler.push(commande);
		refaire.clear();
	}


	public boolean annulerDisponible() {
		return !annuler.empty();
	}


	public void annuler() {
		if (annulerDisponible()){
			Commande commande = annuler.pop();
			commande.annuler();
			refaire.push(commande);
		}
	}


	public boolean refaireDisponible() {
		return !refaire.empty();
	}


	public void refaire() {
		if (refaireDisponible()){
			Commande commande = refaire.pop();
			commande.execution();
			annuler.push(commande);
		}
	}
}
