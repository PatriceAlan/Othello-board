package strategies;

import entites.Commande;
import entites.Controleur;
import entites.Couleur;
import entites.Plateau;

import java.awt.*;
import java.util.ArrayList;

public abstract class Strategie {

    public Controleur controleur;

    public Strategie(Controleur controleur) {
        this.controleur = controleur;
    }

    public abstract Plateau mouvement(Plateau plateau);

    public ArrayList<Plateau> tableauxAdjacents(Plateau plateau){
        ArrayList<Plateau> plateaux = new ArrayList<>();
        ArrayList<Point> mouvementsPossibles;
        if (plateau.tourJoueur == Couleur.NOIR)
            mouvementsPossibles = plateau.joueurNoir.coupsPossibles;
        else
            mouvementsPossibles = plateau.joueurBlanc.coupsPossibles;
        for (Point mouvementPossible : mouvementsPossibles) {
            Plateau plateauCopie = new Plateau(plateau);
            Commande commande = new Commande(plateauCopie, plateau.tourJoueur, mouvementPossible);
            commande.execution();
            plateaux.add(plateauCopie);
        }
        return plateaux;
    }

    public Commande getCommande(Plateau plateau, Plateau plateauSuccesseur){
        for (int lig = 0; lig < plateau.lignes; lig++){
            for (int col = 0; col < plateau.colonnes; col++){
                if (plateau.othellier[lig][col].couleur == Couleur.VIDE && plateauSuccesseur.othellier[lig][col].couleur != Couleur.VIDE)
                    return new Commande(plateau, plateauSuccesseur.othellier[lig][col].couleur, new Point(col, lig));
            }
        }
        return null;
    }
}

