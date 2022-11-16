/*
 * Party.java
 *
 * Version:
 *   $Id$
 *
 * Revisions:
 *   $Log: Party.java,v $
 *   Revision 1.3  2003/02/09 21:21:31  ???
 *   Added lots of comments
 *
 *   Revision 1.2  2003/01/12 22:23:32  ???
 *   *** empty log message ***
 *
 *   Revision 1.1  2003/01/12 19:09:12  ???
 *   Adding Party, Lane, Bowler, and Alley.
 *
 */

/**
 *  Container that holds bowlers
 *
 */

import java.util.*;

public class Party {

	/** Vector of bowlers in this party */	
    private Vector myBowlers;
	public void assignParty(Lane lane) {
		lane.scoreCalculation.party = this;
		lane.resetBowlerIterator();
		lane.scoreCalculation.partyAssigned = true;
		lane.scoreCalculation.curScores = new int[lane.scoreCalculation.party.getMembers().size()];
		lane.scoreCalculation.cumulScores = new int[lane.scoreCalculation.party.getMembers().size()][10];
		lane.scoreCalculation.finalScores = new int[lane.scoreCalculation.party.getMembers().size()][128]; //Hardcoding a max of 128 games
		lane.gameNumber = 0;

		lane.scoreCalculation.resetScores(lane.scoreCalculation.party);
		lane.gameFinished = false;
		lane.frameNumber = 0;
	}
	
	/**
	 * Constructor for a Party
	 * 
	 * @param bowlers	Vector of bowlers that are in this party
	 */
		
    public Party( Vector bowlers ) {
		myBowlers = new Vector(bowlers);
    }

	/**
	 * Accessor for members in this party
	 * 
	 * @return 	A vector of the bowlers in this party
	 */
	public int getPartySize(){
		return myBowlers.size();
	}
    public Vector getMembers() {
		return myBowlers;
    }
	public void resetBowlers(Lane lane, Vector bowlers){
		myBowlers = bowlers;
		lane.bowlerIterator = bowlers.iterator();
	}
}
