package strategies;

import java.util.Comparator;

import composants_principaux.Plateau;


public class PlateauComparateurBlanc implements Comparator<Plateau>{

	@Override
	public int compare(Plateau plateau1, Plateau plateau2){
		Utilite utilite1 = new Utilite(plateau1);
		utilite1.utiliteFinale();
		
		Utilite utilite2 = new Utilite(plateau2);
		utilite2.utiliteFinale();
		
		return utilite1.value - utilite2.value;
	}
}

