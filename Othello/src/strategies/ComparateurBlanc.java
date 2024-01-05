package strategies;

import entites.Plateau;

import java.util.Comparator;

public class ComparateurBlanc implements Comparator<Plateau> {


    @Override
    public int compare(Plateau plateau1, Plateau plateau2) {
        Utilitaire utilitaire1 = new Utilitaire(plateau1);
        utilitaire1.utiliteFinale();

        Utilitaire utilitaire2 = new Utilitaire(plateau2);
        utilitaire2.utiliteFinale();

        return utilitaire1.valeur - utilitaire2.valeur;
    }
}
