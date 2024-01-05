package strategies;

import entites.Couleur;
import entites.Plateau;

public class Utilitaire {

    public Plateau plateau;
    public int valeur;

    public Utilitaire(Plateau plateau){
        this.plateau = plateau;
        this.valeur = 0;
    }

    public void utiliteDiffScore(){
        if (plateau.jeuTermine)
            utiliteJeuTermine();
        else
            valeur = plateau.joueurNoir.score - plateau.joueurBlanc.score;
    }

    public void utiliteCoins(){
        if (plateau.jeuTermine)
            utiliteJeuTermine();
        else
            valeur = plateau.coinsOccupes(Couleur.NOIR) - plateau.coinsOccupes(Couleur.BLANC);
    }

    public void utiliteMouvementsValides(){
        if (plateau.jeuTermine)
            utiliteJeuTermine();
        else
            valeur = plateau.joueurNoir.coupsPossibles.size() - plateau.joueurBlanc.coupsPossibles.size();
    }

    public void utiliteFinale(){
        if (plateau.jeuTermine)
            utiliteJeuTermine();
        else{
            int utiliteNoir = plateau.joueurNoir.coupsPossibles.size()
                    + (-63 + plateau.tour) * plateau.mauvaisesCCasesOccupees(Couleur.NOIR)
                    + (-60 + plateau.tour) * plateau.mauvaisesXCasesOccupees(Couleur.NOIR)
                    + ( 66 - plateau.tour) * plateau.coinsOccupes(Couleur.NOIR);
            int utiliteBlanc = plateau.joueurBlanc.coupsPossibles.size()
                    + (-63 + plateau.tour) * plateau.mauvaisesCCasesOccupees(Couleur.BLANC)
                    + (-60 + plateau.tour) * plateau.mauvaisesXCasesOccupees(Couleur.BLANC)
                    + ( 66 - plateau.tour) * plateau.coinsOccupes(Couleur.BLANC);
            if (plateau.tour >= 44){
                utiliteNoir += plateau.joueurNoir.score;
                utiliteBlanc += plateau.joueurBlanc.score;
            }
            valeur = utiliteNoir - utiliteBlanc;
        }
    }

    public void utiliteJeuTermine(){
        if (plateau.joueurNoir.score > plateau.joueurBlanc.score)
            valeur = 1000;
        else if (plateau.joueurNoir.score < plateau.joueurBlanc.score)
            valeur = -1000;
        else
            valeur = 0;
    }
}
