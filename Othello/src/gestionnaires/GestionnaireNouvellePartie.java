package gestionnaires;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import composants_principaux.Controleur;
import strategies.Difficulte;


public class GestionnaireNouvellePartie extends MouseAdapter {
	public Controleur controleur;
	public Difficulte difficulte;
	

	public GestionnaireNouvellePartie(Controleur controleur, Difficulte difficulte){
		this.controleur = controleur;
		this.difficulte = difficulte;
	}
	

	public void mouseClicked(MouseEvent event){
		javax.swing.JOptionPane.showMessageDialog(null, "Nouvelle partie");
		controleur.reinitialiser(difficulte);
	}
}