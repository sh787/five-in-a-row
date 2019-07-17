package controller;

import java.util.ArrayList;

import model.Board;
import model.Location;
import model.NotImplementedException;
import model.Player;

/**
 * The TestAI uses min-max search to choose the next move to make.
 * 
 * The intention is to test MinMaxAI with simply-implemented versions 
 * of estimate() and moves().
 */
public class TestAI extends MinMaxAI {

	/** Initializes TestAI
	 * 
	 * @param me
	 * @param depth: initialize with depth of 2
	 */
	protected TestAI(Player me) {
		super(me, 2);
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		throw new NotImplementedException();

	}

	@Override
	protected int estimate(Board b) {
		throw new NotImplementedException();
		//return 0;
	}

	@Override
	protected Iterable<Location> moves(Board b) {
		throw new NotImplementedException();
		//return null;
	}

}
