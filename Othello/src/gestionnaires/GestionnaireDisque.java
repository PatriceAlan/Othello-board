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

/**
 * Permet de placer un disque sur le plateau avec une souris
 * @author Peck Patrice Alan
 */
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
		if (plateau.playerTurn != Couleur.BLACK)
			return;
		Boutton currentBoutton = (Boutton) event.getSource();
		if (plateau.getCurrentPlayer().validMoves.contains(currentBoutton.createPoint())){
			Point destinationPoint = currentBoutton.createPoint();
			Commande commande = new Commande(plateau, controleur.plateau.playerTurn, destinationPoint);
			manageur.executeCommand(commande);
			
			vue.enableButtons(false);
			vue.updateView();
		    try{
		        SwingUtilities.invokeLater(new Runnable(){
		            public void run(){
		            	if (!plateau.whiteJoueur.validMoves.isEmpty())
		    				controleur.computerTurn();
		            	vue.enableButtons(true);
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
