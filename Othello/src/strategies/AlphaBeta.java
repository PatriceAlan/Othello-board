package strategies;

import entites.Commande;
import entites.Controleur;
import entites.Couleur;
import entites.Plateau;

import java.util.ArrayList;
import java.util.Collections;

public class AlphaBeta extends Strategie{

    public int profondeur = 7;
    public  int noeudsExplores = 0;

    public AlphaBeta(Controleur controleur) {
        super(controleur);
    }

    @Override
    public Plateau mouvement(Plateau plateau) {
        if (plateau.tour >= 48)
            profondeur = 60;

        int alpha = Integer.MIN_VALUE;
        int beta = Integer.MAX_VALUE;

        long tempsDebut = System.currentTimeMillis();
        Plateau plateauSuccesseur = alphaBeta(plateau,0, profondeur, alpha, beta);
        long tempsFin = System.currentTimeMillis();

        double temps = (tempsFin - tempsDebut) / 1000.0;
        System.out.println("Tour " + plateau.tour + "\n------\nA.I temps mouvement = " + " secondes");
        System.out.println("Noeuds explorés = " + noeudsExplores);

        Commande commande = getCommande(plateau, plateauSuccesseur);
        commande.execution();

        Utilitaire utilitaire = new Utilitaire(plateauSuccesseur);
        utilitaire.utiliteFinale();
        System.out.println("Utilité = " + utilitaire.valeur);
        return plateau;
    }

    public Plateau alphaBeta(Plateau plateau, int niveauActuel, int profondeurMax, int alpha, int beta){
        if (plateau.jeuTermine || (niveauActuel == profondeurMax))
            return plateau;
        Utilitaire utilitaire;
        ArrayList<Plateau> plateauxSuivants = tableauxAdjacents(plateau);
        Plateau meilleurPlateau = null;

        if (plateau.tourJoueur == Couleur.NOIR){
            Collections.sort(plateauxSuivants, Collections.reverseOrder(new ComparateurBlanc()));

            int max = Integer.MIN_VALUE;
            for (Plateau plateauxSuivant : plateauxSuivants) {
                noeudsExplores++;
                Plateau plateauSuivant2 = alphaBeta(plateauxSuivant, niveauActuel + 1, profondeurMax, alpha, beta);
                utilitaire = new Utilitaire(plateauSuivant2);
                utilitaire.utiliteFinale();
                if (utilitaire.valeur > max) {
                    max = utilitaire.valeur;
                    meilleurPlateau = plateauxSuivant;
                }
                alpha = Math.max(alpha, max);
                if (alpha >= beta)
                    break;
            }
        }
        else{
            int min = Integer.MAX_VALUE;
            for (Plateau plateauxSuivant : plateauxSuivants) {
                noeudsExplores++;
                Plateau plateauSuivant2 = alphaBeta(plateauxSuivant, niveauActuel + 1, profondeurMax, alpha, beta);
                utilitaire = new Utilitaire(plateauSuivant2);
                utilitaire.utiliteFinale();
                if (utilitaire.valeur < min) {
                    min = utilitaire.valeur;
                    meilleurPlateau = plateauxSuivant;
                }
                beta = Math.min(beta, utilitaire.valeur);
                if (alpha <= beta) {
                    return plateauSuivant2;
                }

            }
        }
        return meilleurPlateau;
    }
}
