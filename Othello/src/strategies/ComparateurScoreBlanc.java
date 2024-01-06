package strategies;

import java.util.Comparator;

import composants_principaux.Plateau;


public class ComparateurScoreBlanc implements Comparator<Plateau>{

	@Override
	public int compare(Plateau plateau1, Plateau plateau2){
		Utilite utilite1 = new Utilite(plateau1);
		utilite1.utiliteDiffScore();
		
		Utilite utilite2 = new Utilite(plateau2);
		utilite2.utiliteDiffScore();
		
		return utilite1.value - utilite2.value;
	}
}