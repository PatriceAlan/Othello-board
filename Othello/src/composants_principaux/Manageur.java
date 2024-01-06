package composants_principaux;

import java.util.Stack;

/**
 * \brief
 * Keeps Commands in Stacks so that we can "redo" and "undo" moves
 * @author Rodney Shaghoulian
 */
public class Manageur {
	public Stack<Commande> undos = new Stack<Commande>();	///< Commands saved in a Stack in the appropriate order to "undo"
	public Stack<Commande> redos = new Stack<Commande>();	///< Commands saved in a Stack in the appropriate order to "redo"
		
	/**
	 * Executes a Command on the current Board
	 * @param commande	The Command to execute
	 */
	public void executeCommand(Commande commande) {
		commande.execute();
		undos.push(commande);
		redos.clear();
	}

	/**
	 * Checks to see if an "undo" is available
	 * @return		true if available. false otherwise
	 */
	public boolean undoAvailable() {
		return !undos.empty();
	}

	/**
	 * Undoes the last move (including the computer A.I.'s move)
	 */
	public void undo() {
		if (undoAvailable()){
			Commande commande = undos.pop();
			commande.undo();
			redos.push(commande);
		}
	}

	/**
	 * Checks to see if a "redo" is available
	 * @return		true if available. false otherwise
	 */
	public boolean redoAvailable() {
		return !redos.empty();
	}

	/**
	 * Redoes the last move
	 */
	public void redo() {
		if (redoAvailable()){
			Commande commande = redos.pop();
			commande.execute();
			undos.push(commande);
		}
	}
}
