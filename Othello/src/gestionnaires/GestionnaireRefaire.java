package gestionnaires;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import composants_principaux.Plateau;
import composants_principaux.Couleur;
import composants_principaux.Manageur;
import composants_principaux.Controleur;
import composants_principaux.Vue;


public class GestionnaireRefaire extends MouseAdapter {
	public Manageur manageur;
	public Plateau plateau;
	public Vue vue;
	

	public GestionnaireRefaire(Controleur controleur){
		this.manageur = controleur.manageur;
		this.plateau = controleur.plateau;
		this.vue = controleur.vue;
	}
	

	public void mouseClicked(MouseEvent event){
		if (manageur.refaireDisponible()){
			do{
				manageur.refaire();
				vue.miseAjourVue();
			}while(plateau.tourJoueur == Couleur.BLANC);
		}
		else
			java.awt.Toolkit.getDefaultToolkit().beep();
	}
}