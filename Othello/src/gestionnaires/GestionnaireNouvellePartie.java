package gestionnaires;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import composants_principaux.Controleur;
import strategies.Difficulty;

/**
 * Permet au joueur de commencer une nouvelle partie
 * @author Peck Patrice Alan
 */
public class GestionnaireNouvellePartie extends MouseAdapter {
	public Controleur controleur;
	public Difficulty difficulty;
	

	public GestionnaireNouvellePartie(Controleur controleur, Difficulty difficulty){
		this.controleur = controleur;
		this.difficulty = difficulty;
	}
	

	public void mouseClicked(MouseEvent event){
		javax.swing.JOptionPane.showMessageDialog(null, "Nouvelle partie");
		controleur.reset(difficulty);
	}
}