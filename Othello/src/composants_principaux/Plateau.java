/** \brief The main classes of our Othello Program */
package composants_principaux;
import java.awt.Point;
import java.util.ArrayList;

/**
 * \brief
 * This is where the 2-player game of Othello will be played.
 * @author Rodney Shaghoulian
 */
public class Plateau {
	/* Essential Info */
	public final int rows;		///< Number of rows on Board.
	public final int columns;	///< Number of columns on Board.
	public Disque[][] tile;		///< Board is a 2-dimensional array of Disks.
	
	/* Players */
	public Joueur blackJoueur;	///< Player 1 on Board. Uses BLACK Disks.
	public Joueur whiteJoueur;	///< Player 2 on Board. Uses WHITE Disks.
	
	/* Statuses */
	public Couleur playerTurn;	///< Either WHITE's turn or BLACK's turn
	public boolean gameEnded;	///< True if game is over. False otherwise.
	public Couleur winner;		///< Either Color.BLACK or Color.WHITE when not null.
	public int turn;			///< # of moves that have happened. Value from 1 to 60.
	
	/**
	 * Constructor - Initializes a Board given number of rows and columns (usually 8x8)
	 * @param rows		Number of rows on Othello Board (usually 8)
	 * @param columns	Number of columns on Othello Board (usually 8)
	 */
	public Plateau(int rows, int columns){
		/* Initialize Essential Info */
		this.rows = rows;
		this.columns = columns;
		tile = new Disque[rows][columns];
		
		initializeBoard();
		
		/* Initialize Players */
		blackJoueur = new Joueur(this, Couleur.BLACK, false);
		whiteJoueur = new Joueur(this, Couleur.WHITE, false);
		
		/* Initialize Statuses */
		playerTurn = Couleur.BLACK;
		gameEnded = false;
		winner = null;
		turn = 1; 
	}
	
	/**
	 * Copy Constructor - Creates a deep copy of a Board
	 * @param otherPlateau	The other Board to create a deep copy of
	 */
	public Plateau(Plateau otherPlateau){
		/* Copy essential info */
		rows = otherPlateau.rows;
		columns = otherPlateau.columns;
		tile = new Disque[rows][columns];
		for (int row = 0; row < rows; row++){
			for (int col = 0; col < columns; col++){
				tile[row][col] = new Disque(otherPlateau.tile[row][col]);
			}
		}
		
		/* Copy Players */
		blackJoueur = new Joueur(otherPlateau.blackJoueur);
		whiteJoueur = new Joueur(otherPlateau.whiteJoueur);
		
		/* Copy statuses */
		playerTurn  = otherPlateau.playerTurn;
		gameEnded   = otherPlateau.gameEnded;
		winner      = otherPlateau.winner;
		turn		= otherPlateau.turn;
	}
	
	/**
	 * Initializes the Board by creating Disks with Color.NONE for empty spots. \n
	 * Puts 2 BLACK Disks and 2 WHITE Disks in center of Board. 
	 */
	public void initializeBoard(){
		for (int row = 0; row < rows; row++){
			for (int col = 0; col < columns; col++){
				tile[row][col] = new Disque(Couleur.NONE);
			}
		}
		
		int centerRoundDown = rows / 2 - 1;
		int centerRoundUp = rows / 2;
		/* Place 2 Black Disks in center of Board */
		tile[centerRoundDown][centerRoundDown].changeColor(Couleur.BLACK);
		tile[centerRoundUp][centerRoundUp].changeColor(Couleur.BLACK);
		
		/* Place 2 White Disks in center of Board */
		tile[centerRoundDown][centerRoundUp].changeColor(Couleur.WHITE);
		tile[centerRoundUp][centerRoundDown].changeColor(Couleur.WHITE);
	}
	
	/**
	 * Determines if a position exists on a Board
	 * @param pos	The position we are checking x and y coordinates for
	 * @return		true/false depending on whether position is valid on Board
	 */
	public boolean validPosition(Point pos){
		return (pos.x >= 0 && pos.y >= 0 && pos.x < columns && pos.y < rows);
	}
	
	/**
	 * Determines the Color (WHITE, BLACK, NONE) of the Disk at a position on the Board
	 * @param pos	The position that a Disk may reside
	 * @return		The color of the Disk (WHITE, BLACK, NONE)
	 */
	public Couleur diskColor(Point pos){
		return tile[pos.y][pos.x].couleur;
	}
	
	/**
	 * Gets opposite color. BLACK and WHITE are opposites
	 * @param couleur	the color to get the opposite of.
	 * @return		BLACK if parameter is WHITE. WHITE if parameter is BLACK.
	 */
	public Couleur getOppositeColor(Couleur couleur){
		if (couleur == Couleur.BLACK)
			return Couleur.WHITE;
		else if (couleur == Couleur.WHITE)
			return Couleur.BLACK;
		return null;
	}
	
	/**
	 * Toggles playerTurn from WHITE to BLACK, and vice versa
	 */
	public void updateTurn(){
		if (playerTurn == Couleur.WHITE && blackJoueur.validMoves.size() > 0)
			playerTurn = Couleur.BLACK;
		else if (playerTurn == Couleur.BLACK && whiteJoueur.validMoves.size() > 0)
			playerTurn = Couleur.WHITE;
		turn = blackJoueur.score + whiteJoueur.score - 3;
	}
	
	/**
	 * Determines if game is over. Updates "gameEnded" and "winner" if necessary.
	 */
	public void updateGameStatus(){
		if (blackJoueur.validMoves.isEmpty() && whiteJoueur.validMoves.isEmpty()){
			gameEnded = true;
			if (blackJoueur.score > whiteJoueur.score)
				winner = Couleur.BLACK;
			else if (whiteJoueur.score > blackJoueur.score)
				winner = Couleur.WHITE;
			else
				winner = Couleur.NONE;
		}
	}
	
	/**
	 * Updates Board: by updating valid moves, turn, and game status
	 */
	public void updateBoard(){
		blackJoueur.updateValidMoves(this);
		whiteJoueur.updateValidMoves(this);
		updateTurn();
		updateGameStatus();
	}
	
	/**
	 * Determines if a move is valid
	 * @param pos	the desired position to place a Disk.
	 * @param couleur	the color of the Disk being placed.
	 * @return		true if move is valid. false otherwise.
	 */
	public boolean validMove(Point pos, Couleur couleur){
		if (pos == null || couleur == null || !validPosition(pos) || couleur == Couleur.NONE || tile[pos.y][pos.x].couleur != Couleur.NONE)
			return false;
		
		if (validInDirection(pos, couleur, 1, 0))			// Right
			return true;
		else if (validInDirection(pos, couleur, -1, 0))	// Left
			return true;
		else if (validInDirection(pos, couleur, 0, 1))	// Up
			return true;
		else if (validInDirection(pos, couleur, 0, -1))	// Down
			return true;
		else if (validInDirection(pos, couleur, 1, 1))	// NorthEast
			return true;
		else if (validInDirection(pos, couleur, 1, -1))	// NorthWest
			return true;
		else if (validInDirection(pos, couleur, -1, 1))	// SouthEast
			return true;
		else if (validInDirection(pos, couleur, -1, -1))	// SouthWest
			return true;
		return false;
	}
	
	/**
	 * Determines if a a Disk placed in a position will flip Disks in a certain Direction
	 * @param pos			the desired position to place a Disk.
	 * @param couleur			the color of the Disk being placed.
	 * @param xDirection	the x Direction to test flipping Disks (-1, 0, 1).
	 * @param yDirection	the y Direction to test flipping Disks (-1, 0, 1).
	 * @return				true if valid. false otherwise.
	 */
	public boolean validInDirection(Point position, Couleur couleur, int xDirection, int yDirection){
		Point pos = new Point(position);
		Couleur opponentCouleur = getOppositeColor(couleur);
		
		/* For valid move: immediate neighbor should be opposing Color */
		pos.x += xDirection;
		pos.y += yDirection;
		if (!validPosition(pos) || diskColor(pos) != opponentCouleur)
			return false;
		
		/* For valid move: there should be a Disk of our own Color at the end */
		pos.x += xDirection;
		pos.y += yDirection;
		while(validPosition(pos)){
			if (diskColor(pos) == couleur)
				return true;
			else if (diskColor(pos) == Couleur.NONE)
				return false;
			pos.x += xDirection;
			pos.y += yDirection;
		}
		return false;
	}
	
	/**
	 * Removes a Disk by changing it's color to Color.NONE
	 * @param pos		The position of the Disk to remove
	 */
	public void removeDisk(Point pos){
		Couleur oldCouleur = tile[pos.y][pos.x].couleur;
		tile[pos.y][pos.x].changeColor(Couleur.NONE);
		
		/* Subtract from scores */
		if (oldCouleur == Couleur.BLACK)
			blackJoueur.score--;
		else if (oldCouleur == Couleur.WHITE)
			whiteJoueur.score--;
	}
	
	/**
	 * Flips a Disk by changing it's color 
	 * @param pos		The position of the Disk.
	 * @param newCouleur	The color the Disk should be changed to.
	 */
	public void flipDisk(Point pos, Couleur newCouleur){
		Couleur oldCouleur = tile[pos.y][pos.x].couleur; // oldColor could be "NONE"
		tile[pos.y][pos.x].changeColor(newCouleur);
		
		/* Subtract from scores */
		if (oldCouleur == Couleur.BLACK)
			blackJoueur.score--;
		else if (oldCouleur == Couleur.WHITE)
			whiteJoueur.score--;
		
		/* Add to scores */
		if (newCouleur == Couleur.BLACK)
			blackJoueur.score++;
		else if (newCouleur == Couleur.WHITE)
			whiteJoueur.score++;
	}
	
	/**
	 * Flips (captures) opponent's disks
	 * @param pos	The position of the Disk that was placed.
	 * @param couleur	The color of the Disk.
	 * @return		The Points that were flipped
	 */
	public ArrayList<Point> flipCaptures(Point pos, Couleur couleur){
		ArrayList<Point> disksFlipped = new ArrayList<Point>();
		ArrayList<Point> disksFlippedInDirection = new ArrayList<Point>();
		
		if (!validPosition(pos))
			return disksFlipped;
		disksFlippedInDirection = flipInDirection(pos, couleur,  1,  0);	// Right
		disksFlipped.addAll(disksFlippedInDirection);
		disksFlippedInDirection = flipInDirection(pos, couleur, -1,  0);	// Left
		disksFlipped.addAll(disksFlippedInDirection);
		disksFlippedInDirection = flipInDirection(pos, couleur,  0,  1);	// Up
		disksFlipped.addAll(disksFlippedInDirection);
		disksFlippedInDirection = flipInDirection(pos, couleur,  0, -1);	// Down
		disksFlipped.addAll(disksFlippedInDirection);
		disksFlippedInDirection = flipInDirection(pos, couleur,  1,  1);	// NorthEast
		disksFlipped.addAll(disksFlippedInDirection);
		disksFlippedInDirection = flipInDirection(pos, couleur,  1, -1);	// NorthWest
		disksFlipped.addAll(disksFlippedInDirection);
		disksFlippedInDirection = flipInDirection(pos, couleur, -1,  1);	// SouthEast
		disksFlipped.addAll(disksFlippedInDirection);
		disksFlippedInDirection = flipInDirection(pos, couleur, -1, -1);	// SouthWest
		disksFlipped.addAll(disksFlippedInDirection);
		return disksFlipped;
	}
	
	/**
	 * Flip disks in a certain direction
	 * @param pos			the position the new Disk that was placed.
	 * @param couleur			the color of the Disk that was just placed.
	 * @param xDirection	the x Direction to flip Disks (-1, 0, 1).
	 * @param yDirection	the y Direction to flip Disks (-1, 0, 1).
	 * @return				the Points that were flipped
	 */
	public ArrayList<Point> flipInDirection(Point position, Couleur couleur, int xDirection, int yDirection){
		ArrayList<Point> disksFlipped = new ArrayList<Point>();
		Point pos = new Point(position);
		if (!validInDirection(pos, couleur, xDirection, yDirection))
			return disksFlipped;
		Couleur opponentCouleur = getOppositeColor(couleur);
		pos.x += xDirection;
		pos.y += yDirection;
		while (validPosition(pos) && diskColor(pos) == opponentCouleur){
			flipDisk(pos, couleur);
			disksFlipped.add(new Point(pos));
			pos.x += xDirection;
			pos.y += yDirection;
		}
		return disksFlipped;
	}
	
	/**
	 * Places a Disk on a Board. Captures opponents Disks. \n
	 * Assumption: Function assumes it's a valid move.
	 * @param pos	The position where the Disk will be placed.
	 * @param couleur	The color of the Disk.
	 */
	public void placeDisk(Point pos, Couleur couleur){
		flipDisk(pos, couleur);
		flipCaptures(pos, couleur);
		updateBoard();
	}
	
	/**
	 * returns the player whose turn it currently is
	 * @return	blackPlayer or whitePlayer
	 */
	public Joueur getCurrentPlayer(){
		if (playerTurn == Couleur.BLACK)
			return blackJoueur;
		else
			return whiteJoueur;
	}
	
	/**
	 * Calculates the number of 'X squares' that are occupied by a Disk of a certain Color. \n
	 * which are not next to a corner owned by that color.
	 * @param couleur		the Color of the Disks that we want to check the corners for.
	 * @return			the number of 'X squares' that are occupied by a Disk of a certain Color.
	 */
	public int xSquaresOwned(Couleur couleur){
		int badSpots = 0;
		if (diskColor(new Point(1, 1)) == couleur)
			badSpots++;
		if (diskColor(new Point(1, rows - 2)) == couleur)
			badSpots++;
		if (diskColor(new Point(columns - 2, 1)) == couleur)
			badSpots++;
		if (diskColor(new Point(columns - 2, rows - 2)) == couleur)
			badSpots++;
		return badSpots;
	}
	
	/**
	 * Calculates the number of bad 'X squares' that are occupied by a Disk of a certain Color. \n
	 * which are not next to an occupied corner
	 * @param couleur		the Color of the Disks that we want to check the corners for.
	 * @return			the number of lone 'X squares' that are occupied by a Disk of a certain Color.
	 */
	public int badXSquaresOwned(Couleur couleur){
		int badSpots = 0;
		if ((diskColor(new Point(1, 1)) == couleur) && (diskColor(new Point(0, 0)) == Couleur.NONE))
			badSpots++;
		if ((diskColor(new Point(1, rows - 2)) == couleur) && (diskColor(new Point(0, rows - 1)) == Couleur.NONE))
			badSpots++;
		if ((diskColor(new Point(columns - 2, 1)) == couleur) && (diskColor(new Point(columns - 1, 0)) == Couleur.NONE))
			badSpots++;
		if ((diskColor(new Point(columns - 2, rows - 2)) == couleur) && (diskColor(new Point(columns - 1, rows - 1)) == Couleur.NONE))
			badSpots++;
		return badSpots;
	}
	
	/**
	 * Calculates the number of 'C squares' are occupied by a Disk of a certain Color,
	 * @param couleur		the Color of the Disks that we want to check the C squares for.
	 * @return			the number of 'C squares' that are occupied by a Disk of a certain Color.
	 */
	public int cSquaresOwned(Couleur couleur){
		int cSquares = 0;
		
		if (diskColor(new Point(0, 1)) == couleur)
			cSquares++;
		if (diskColor(new Point(1, 0)) == couleur)
			cSquares++;
		
		if (diskColor(new Point(0, rows - 2)) == couleur)
			cSquares++;
		if (diskColor(new Point(1, rows - 1)) == couleur)
			cSquares++;
		
		if (diskColor(new Point(columns - 2, 0)) == couleur)
			cSquares++;
		if (diskColor(new Point(columns - 1, 1)) == couleur)
			cSquares++;
		
		if (diskColor(new Point(columns - 1, rows - 2)) == couleur)
			cSquares++;
		if (diskColor(new Point(columns - 2, rows - 1)) == couleur)
			cSquares++;
		
		return cSquares;
	}
	
	/**
	 * Calculates the number of bad 'C squares' are occupied by a Disk of a certain Color, \n
	 * which are not next to an occupied corner
	 * @param couleur		the Color of the Disks that we want to check the C squares for.
	 * @return			the number of lone 'C squares' that are occupied by a Disk of a certain Color.
	 */
	public int badCSquaresOwned(Couleur couleur){
		int cSquares = 0;
		
		if ((diskColor(new Point(0, 1)) == couleur) && (diskColor(new Point(0, 0)) == Couleur.NONE))
			cSquares++;
		if ((diskColor(new Point(1, 0)) == couleur) && (diskColor(new Point(0, 0)) == Couleur.NONE))
			cSquares++;
		
		if ((diskColor(new Point(0, rows - 2)) == couleur) && (diskColor(new Point(0, rows - 1)) == Couleur.NONE))
			cSquares++;
		if ((diskColor(new Point(1, rows - 1)) == couleur) && (diskColor(new Point(0, rows - 1)) == Couleur.NONE))
			cSquares++;
		
		if ((diskColor(new Point(columns - 2, 0)) == couleur) && (diskColor(new Point(columns - 1, 0)) == Couleur.NONE))
			cSquares++;
		if ((diskColor(new Point(columns - 1, 1)) == couleur) && (diskColor(new Point(columns - 1, 0)) == Couleur.NONE))
			cSquares++;
		
		if ((diskColor(new Point(columns - 1, rows - 2)) == couleur) && (diskColor(new Point(columns - 1, rows - 1)) == Couleur.NONE))
			cSquares++;
		if ((diskColor(new Point(columns - 2, rows - 1)) == couleur) && (diskColor(new Point(columns - 1, rows - 1)) == Couleur.NONE))
			cSquares++;
		
		return cSquares;
	}
	
	/**
	 * Calculates the number of corners are occupied by a Disk of a certain Color.
	 * @param couleur		the Color of the Disks that we want to check the corners for.
	 * @return			the number of corners that are occupied by a Disk of a certain Color.
	 */
	public int cornersOwned(Couleur couleur){
		int corners = 0;
		if (diskColor(new Point(0, 0)) == couleur)
			corners++;
		if (diskColor(new Point(0, rows - 1)) == couleur)
			corners++;
		if (diskColor(new Point(columns - 1, 0)) == couleur)
			corners++;
		if (diskColor(new Point(columns - 1, rows - 1)) == couleur)
			corners++;
		return corners;
	}
	
	/**
	 * Enables printing of a Board to the console using System.out.println()
	 */
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		/* Append who owns which squares */
		for (int row = rows - 1; row >= 0; row--){		//Notice we loop backwards through rows.
			for (int col = 0; col < columns; col++){
				if (tile[row][col].couleur == Couleur.BLACK)
					sb.append("B");
				else if (tile[row][col].couleur == Couleur.WHITE)
					sb.append("W");
				else
					sb.append("-");
				sb.append("\t");
			}
			sb.append("\n");
		}
		return sb.toString();
	}
}
