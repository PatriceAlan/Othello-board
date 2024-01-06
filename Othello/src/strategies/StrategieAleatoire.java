package strategies;

import java.awt.Point;
import java.util.ArrayList;

import composants_principaux.Plateau;
import composants_principaux.Couleur;
import composants_principaux.Commande;
import composants_principaux.Controleur;


public class StrategieAleatoire extends Strategie {


	public StrategieAleatoire(Controleur controleur){
		super(controleur);
	}
	

	public Plateau mouvement(Plateau plateau){
		ArrayList<Point> mouvementsValides;
		if (plateau.tourJoueur == Couleur.NOIR)
			mouvementsValides = plateau.joueurNoir.mouvementsValides;
		else
			mouvementsValides = plateau.joueurBlanc.mouvementsValides;
		if (mouvementsValides.isEmpty())
			return null;
		Point move = choisirMouvementAleatoire(mouvementsValides);
		Commande commande = new Commande(plateau, controleur.plateau.tourJoueur, move);
		manageur.executionCommande(commande);
		vue.miseAjourVue();
		return plateau;
	}
	

	public Point choisirMouvementAleatoire(ArrayList<Point> mouvementsValides){
		int nombreMouvements = mouvementsValides.size();
		int num = (int) (Math.random() * nombreMouvements);
		return mouvementsValides.get(num);
	}
}
