package controller;

import java.util.ArrayList;
import java.util.List;

import model.Board;
import model.Game;
import model.Location;
import model.NotImplementedException;
import model.Player;

/**
 * A DumbAI is a Controller that always chooses the blank space with the
 * smallest column number from the row with the smallest row number.
 */
public class DumbAI extends Controller {
	
	private int row; 
	private int column;

	public DumbAI(Player me) {
		super(me);
		this.row = 0;
		this.column = 0;
	}

	protected @Override Location nextMove(Game g) {
		// Note: Calling delay here will make the CLUI work a little more
		// nicely when competing different AIs against each other.
		
		// TODO Auto-generated method stub
		List<Location> available = new ArrayList<Location>();
		
		// find available moves
		for (Location loc : Board.LOCATIONS) {
			if (g.getBoard().get(loc) == null && available.size() == 0) {
				available.add(loc);
			}
			
			//iterate backward or forward?
			for (int i = 0; i < available.size(); i++) {
				if ((g.getBoard().get(loc)== null) && (loc.row < available.get(i).row) && (loc.col < available.get(i).col)) {
					available.add(i-1, loc);
					i-=2;
				} else if ((g.getBoard().get(loc)== null) && (loc.row == available.get(i).row) && (loc.col == available.get(i).col-1))  {
					available.add(i-1,loc);
					i-=2;
				} else if ((g.getBoard().get(loc)== null) && (loc.row == available.get(i).row +1) && (loc.col == available.get(i).col-1))  {
					available.add(i-1,loc);
					i-=2;
				} else {
					available.add(loc);
				}
			}
		}
		
		// wait a bit
		delay();

		// choose a random move
		if (!available.isEmpty())
			//return available.get(random.nextInt(available.size()));
			return available.get(0);

		return null;
	}
}
