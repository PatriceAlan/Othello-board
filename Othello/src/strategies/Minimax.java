package strategies;

import entites.Commande;
import entites.Controleur;
import entites.Couleur;
import entites.Plateau;

import java.util.ArrayList;

public class Minimax extends Strategie{

    public int profondeur = 5;

    public Minimax(Controleur controleur) {
        super(controleur);
    }
    @Override
    public Plateau mouvement(Plateau plateau) {
        Plateau plateauSuccesseur = minimax(plateau,0, profondeur);
        Commande commande = getCommande(plateau, plateauSuccesseur);
        commande.execution();
        return plateau;
    }

    public Plateau minimax(Plateau plateau, int niveauActuel, int profondeurMax){
        if (plateau.jeuTermine || (niveauActuel == profondeurMax))
            return plateau;
        Utilitaire utilitaire;
        ArrayList<Plateau> plateauxSuivants = tableauxAdjacents(plateau);
        Plateau meilleurPlateau = null;

        if (plateau.tourJoueur == Couleur.NOIR){
            int max = Integer.MIN_VALUE;
            for (int i = 0; i < plateauxSuivants.size(); i++) {
                Plateau plateauSuivant = plateauxSuivants.get(i);
                Plateau plateauSuivant2 = minimax(plateauSuivant, niveauActuel + 1, profondeurMax);
                utilitaire = new Utilitaire(plateauSuivant2);
                utilitaire.utiliteFinale();
                if (utilitaire.valeur > max){
                    max = utilitaire.valeur;
                    meilleurPlateau = plateauSuivant;
                }
            }
        }
        else{
            int min = Integer.MAX_VALUE;
            for (int i = 0; i < plateauxSuivants.size(); i++) {
                Plateau plateauSuivant = plateauxSuivants.get(i);
                Plateau plateauSuivant2 = minimax(plateauSuivant, niveauActuel + 1, profondeurMax);
                utilitaire = new Utilitaire(plateauSuivant2);
                utilitaire.utiliteFinale();
                if (utilitaire.valeur < min){
                    min = utilitaire.valeur;
                    meilleurPlateau = plateauSuivant;
                }
            }
        }
        return meilleurPlateau;
    }
}
