package composants_principaux;

import gestionnaires.GestionnaireAbandon;
import gestionnaires.GestionnaireNouvellePartie;
import gestionnaires.GestionnaireDisque;
import gestionnaires.GestionnaireRefaire;
import gestionnaires.GestionnaireAnnuler;
import strategies.MinimaxStrategy;
import strategies.AlphaBetaStrategy;
import strategies.Difficulty;
import strategies.RandomStrategy;

/**
 * \brief
 * The Controller in the MVC structure
 * @author Rodney Shaghoulian
 */
public class Controleur {
	public Plateau plateau; 					///< The Model in MVC structure
	public Manageur manageur;	///< Saves Commands so we can "undo" and "redo" them

	public Vue vue;						///< The "View" in MVC structure.
	public int whiteGamesWon = 0;			///< Number of games WHITE has won
	public int blackGamesWon = 0;			///< Number of games BLACK has won
	public int rows;						///< Number of rows on Board
	public int columns;						///< Number of columns on Board

	public Difficulty difficulty;			///< The difficulty (intelligence) of the computer A.I.
	
	/**
	 * Constructor: Initialize the Controller with a new Board and new View. Add mouseListeners to the View
	 */
	public Controleur(int rows, int columns, Difficulty difficulty) {
		this.rows = rows;
		this.columns = columns;
		this.difficulty = difficulty;
		initializeVariables();
		vue = new Vue(plateau, this); // purposely NOT put into intializeVariables() since that function is called often
		addMouseListeners();
	}
	
	public void reset(){
		initializeVariables();
		vue.initialize(plateau, this);
		addMouseListeners();
	}
	
	/**
	 * Reset the Controller with the new Board and updated View. Add mouseListeners to the View
	 */
	public void reset(Difficulty difficulty){
		this.difficulty = difficulty;
		initializeVariables();
		vue.initialize(plateau, this);
		addMouseListeners();
	}
	
	/**
	 * Create Board and CommandManager.
	 */
	public void initializeVariables(){
		plateau = new Plateau(rows, columns);
		manageur = new Manageur();
	}
	
	/**
	 * Add mouseListeners to the View
	 */
	public void addMouseListeners(){
		for (int i = 0; i < plateau.rows; i++){
			for (int j = 0; j < plateau.columns; j++){
				vue.addMouseListener(new GestionnaireDisque(this), vue.boutton[i][j]);
			}
		}
		vue.addMouseListener(new GestionnaireAnnuler(this), vue.undoButton);
		vue.addMouseListener(new GestionnaireRefaire(this), vue.redoButton);
		vue.addMouseListener(new GestionnaireAbandon(this), vue.whiteForfeit);
		vue.addMouseListener(new GestionnaireAbandon(this), vue.blackForfeit);
		vue.addMouseListener(new GestionnaireNouvellePartie(this, Difficulty.EASY),   vue.newGameEasy);
		vue.addMouseListener(new GestionnaireNouvellePartie(this, Difficulty.MEDIUM), vue.newGameMedium);
		vue.addMouseListener(new GestionnaireNouvellePartie(this, Difficulty.HARD),   vue.newGameHard);

	}
	
	/**
	 * Makes a move for the computer A.I.
	 */
	public void computerTurn(){
		if (difficulty == Difficulty.EASY)
			computerEasy();
		else if (difficulty == Difficulty.MEDIUM)
			computerMedium();
		else if (difficulty == Difficulty.HARD)
			computerHard();
	}
	
	/**
	 * Makes a move for the computer A.I. on EASY difficulty
	 */
	public void computerEasy(){		
		while (plateau.playerTurn == Couleur.WHITE && !plateau.gameEnded){
			RandomStrategy randomStrategy = new RandomStrategy(this);
			try{
				Thread.sleep(300);
			}
			catch (InterruptedException interruptedException) {
				System.out.println("Sleep exception occurred");
			}
			plateau = randomStrategy.move(plateau);
		}
	}

	/**
	 * Makes a move for the computer A.I. on MEDIUM difficulty
	 */
	public void computerMedium(){
		while (plateau.playerTurn == Couleur.WHITE && !plateau.gameEnded){
			MinimaxStrategy minimaxStrategy = new MinimaxStrategy(this);
			try{
				Thread.sleep(300);
			}
			catch (InterruptedException interruptedException) {
				System.out.println("Sleep exception occurred");
			}
			plateau = minimaxStrategy.move(plateau);
		}
	}
	
	/**
	 * Makes a move for the computer A.I. on HARD difficulty
	 */
	public void computerHard(){
		while (plateau.playerTurn == Couleur.WHITE && !plateau.gameEnded){
			AlphaBetaStrategy alphaBetaStrategy = new AlphaBetaStrategy(this);
			plateau = alphaBetaStrategy.move(plateau);
		}
	}
}
