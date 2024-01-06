package gestionnaires;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.SwingUtilities;

import composants_principaux.Boutton;
import composants_principaux.Couleur;
import composants_principaux.Plateau;
import composants_principaux.Commande;
import composants_principaux.Manageur;
import composants_principaux.Controleur;
import composants_principaux.Vue;


public class GestionnaireDisque extends MouseAdapter {
	public Controleur controleur;
	public Plateau plateau;
	public Vue vue;
	public Manageur manageur;
	

	public GestionnaireDisque(Controleur controleur){
		this.controleur = controleur;
		this.plateau = controleur.plateau;
		this.vue = controleur.vue;
		this.manageur = controleur.manageur;
	}
	

	public void mouseClicked(MouseEvent event){
		if (plateau.tourJoueur != Couleur.NOIR)
			return;
		Boutton currentBoutton = (Boutton) event.getSource();
		if (plateau.getJoueurActuel().mouvementsValides.contains(currentBoutton.creationPoint())){
			Point destinationPoint = currentBoutton.creationPoint();
			Commande commande = new Commande(plateau, controleur.plateau.tourJoueur, destinationPoint);
			manageur.executionCommande(commande);
			
			vue.activerBouttons(false);
			vue.miseAjourVue();
		    try{
		        SwingUtilities.invokeLater(new Runnable(){
		            public void run(){
		            	if (!plateau.joueurBlanc.mouvementsValides.isEmpty())
		    				controleur.tourOrdi();
		            	vue.activerBouttons(true);
		            }
		        });
		    }
		    catch (final Throwable e){
		    	System.out.println("Erreur !");
		    }
		}
		else
			javax.swing.JOptionPane.showMessageDialog(null, "Mouvement illegal. Réessayer !");
	}
}
