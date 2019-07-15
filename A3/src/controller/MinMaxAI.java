package controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jdt.annotation.NonNull;

import model.Board;
import model.Board.State;
import model.Game;
import model.Location;
import model.NotImplementedException;
import model.Player;

/**
 * A MinMaxAI is a controller that uses the minimax algorithm to select the next
 * move.  The minimax algorithm searches for the best possible next move, under
 * the assumption that your opponent will also always select the best possible
 * move.
 *
 * <p>The minimax algorithm assigns a score to each possible game configuration
 * g.  The score is assigned recursively as follows: if the game g is over and
 * the player has won, then the score is infinity.  If the game g is over and
 * the player has lost, then the score is negative infinity.  If the game is a
 * draw, then the score is 0.
 * 
 * <p>If the game is not over, then there are many possible moves that could be
 * made; each of these leads to a new game configuration g'.  We can
 * recursively find the score for each of these configurations.
 * 
 * <p>If it is the player's turn, then they will choose the action that
 * maximizes their score, so the score for g is the maximum of all the scores
 * of the g's.  However, if it is the opponent's turn, then the opponent will
 * try to minimize the score for the player, so the score for g is the
 * <em>minimum</em> of all of the scores of the g'.
 * 
 * <p>You can think of the game as defining a tree, where each node in the tree
 * represents a game configuration, and the children of g are all of the g'
 * reachable from g by taking a turn.  The minimax algorithm is then a
 * particular traversal of this tree.
 * 
 * <p>In practice, game trees can become very large, so we apply a few
 * strategies to narrow the set of paths that we search.  First, we can decide
 * to only consider certain kinds of moves.  For five-in-a-row, there are
 * typically at least 70 moves available at each step; but it's (usually) not
 * sensible to go on the opposite side of the board from where all of the other
 * pieces are; by restricting our search to only part of the board, we can
 * reduce the space considerably.
 * 
 * <p>A second strategy is that we can look only a few moves ahead instead of
 * planning all the way to the end of the game.  This requires us to be able to
 * estimate how "good" a given board looks for a player.
 * 
 * <p>This class implements the minimax algorithm with support for these two
 * strategies for reducing the search space.  The abstract method {@link
 * #moves(Board)} is used to list all of the moves that the AI is willing to
 * consider, while the abstract method {@link #estimate(Board)} returns
 * the estimation of how good the board is for the given player.
 */
public abstract class MinMaxAI extends Controller {

	/**
	 * Return an estimate of how good the given board is for me.
	 * A result of infinity means I have won.  A result of negative infinity
	 * means that I have lost.
	 */
	protected abstract int estimate(Board b);
	
	/**
	 * Return the set of moves that the AI will consider when planning ahead.
	 * Must contain at least one move if there are any valid moves to make.
	 */
	protected abstract Iterable<Location> moves(Board b);
	
	
	/**
	 * The depth of the moves of which the algorithm will look into the future.
	 */
	private int minMaxDepth;
	

	
	
	/**
	 * Create an AI that will recursively search for the next move using the
	 * minimax algorithm.  When searching for a move, the algorithm will look
	 * depth moves into the future.
	 *
	 * <p>choosing a higher value for depth makes the AI smarter, but requires
	 * more time to select moves.
	 */
	protected MinMaxAI(Player me, int depth) {
		// TODO Auto-generated method stub
		super(me);
		this.minMaxDepth = depth;
	}
	
	/**
	 * Helper function to find best location.
	 * 
	 * @param g the game to be played
	 * @param depth the initial depth to be recursively implemented down to 0
	 * @param currentPlayer will either be super.me (this player) or the opponent
	 * @return will return the best score to be used in nextMove
	 */
	
	private int calcScore(Board b, int depth, Player currentPlayer) {
		if (depth == 0)
			return estimate(b);
		else if (b.getState() == State.DRAW)
			return 0;
		else if (b.getState() == State.HAS_WINNER)
			return Integer.MAX_VALUE;
		
		int best = 0;
		for (Location m: moves(b)) {
			Board b2 = b.update(currentPlayer, m);
			int newScore = calcScore(b2, depth-1, currentPlayer.opponent());
		
			if (currentPlayer == super.me) {
				if (newScore > best)
					best = newScore;
			} else {
				if (newScore < best)
					best = newScore;
			}
		}
		return best;
		
	}
		

	/**
	 * Return the move that maximizes the score according to the calcScore
	 * algorithm described above.
	 */
	protected @Override Location nextMove(Game g) {
		// TODO Auto-generated method stub
		List<Location> bestMoves = new ArrayList<Location>();
		int bestScore = 0;
		//Location bestMove = new Location(4, 4);
		//bestMoves.add(bestMove);
		
		for (Location loc: moves(g.getBoard())) {
			Board g2 = g.getBoard().update(super.me, loc);
			int score = calcScore(g2, this.minMaxDepth, super.me);
			if ((score > bestScore)){
				bestMoves.add(0, loc);
				bestScore = score;
				//bestMove = loc;

			}
			
		}
		for (int i = 0; i < bestMoves.size(); i++) {
			if (bestMoves.get(i) == null)
				return bestMoves.get(i);
		}
		
		return bestMoves.get(0);


	}
}
