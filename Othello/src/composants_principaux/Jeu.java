package composants_principaux;

import strategies.Difficulte;


public class Jeu {

	public static void main(String[] args) {
		new Controleur(8, 8, Difficulte.DIFFICILE);
	}
}