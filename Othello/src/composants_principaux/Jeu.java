package composants_principaux;

import strategies.Difficulty;

/** 
 * \brief The main game engine/logic
 * @author Peck Patrice Alan
 */
public class Jeu {
	/**
	 * \brief This is the main game loop. It creates a Controller for an Othello game.
	 * @param args	Command Line arguments (which we won't use)
	 */
	public static void main(String[] args) {
		new Controleur(8, 8, Difficulty.HARD);
	}
}