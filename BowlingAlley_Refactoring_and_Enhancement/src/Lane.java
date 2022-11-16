
import java.util.Vector;
import java.util.Iterator;
import java.util.HashMap;
import java.util.Date;
import java.util.Map;
import java.io.Serializable;


public class Lane extends Thread implements Serializable, PinsetterObserver {
	public Party party;
	public Pinsetter setter;
	public Vector subscribers;
	public boolean gameIsHalted;
	public boolean gameFinished;
	public Iterator bowlerIterator;
	public int ball;
	public int bowlIndex;
	public int frameNumber;
	public boolean tenthFrameStrike;
	private boolean throw_;
	public boolean canThrowAgain;
	public int gameNumber;
	public ScoreCalculation scoreCalculation;
	public Bowler currentThrower;			// = the thrower who just took a throw

	/** Lane()
	 *
	 * Constructs a new lane and starts its thread
	 *
	 * @pre none
	 * @post a new lane has been created and its thered is executing
	 */
	public Lane() {
		setter = new Pinsetter();
		subscribers = new Vector();
		scoreCalculation = new ScoreCalculation();
		throw_ = true;
		gameIsHalted = false;
		gameNumber = 0;
		setter.subscribe( this );
		this.start();

	}

	/** run()
	 *
	 * entry point for execution of this lane
	 */
	public void run() {
		Bowler first = null, second = null;
		boolean give_chance = false;

		while (true) {
			if (scoreCalculation.partyAssigned && !gameFinished) {	// we have a party on this lane,
				// so next bower can take a throw

				while (gameIsHalted) {
					try {
						sleep(10);
					} catch (Exception e) {}
				}
				if(give_chance) {
					if (bowlerIterator.hasNext()) {
						currentThrower = (Bowler) bowlerIterator.next();

						canThrowAgain = true;
						tenthFrameStrike = false;
						ball = 0;
						while (canThrowAgain) {
							try {
								sleep(10);
							} catch (Exception e) {
							}
							if (throw_) {
								setter.ballThrown();
								ball++;
								throw_ = false;
							}
						}
						if (frameNumber == 2) {
							scoreCalculation.finalScores[bowlIndex][gameNumber] = scoreCalculation.cumulScores[bowlIndex][9];
							try {
								Date date = new Date();
								String dateString = "" + date.getHours() + ":" + date.getMinutes() + " " + date.getMonth() + "/" + date.getDay() + "/" + (date.getYear() + 1900);
								ScoreHistoryFile.addScore(currentThrower.getNickName(), dateString, new Integer(scoreCalculation.cumulScores[bowlIndex][9]).toString());
							} catch (Exception e) {
								System.err.println("Exception in addScore. " + e);
							}
						}


						setter.reset();
						bowlIndex++;
					} else {
						frameNumber++;
						resetBowlerIterator();
						bowlIndex = 0;
						if (frameNumber > 2) {
							gameFinished = true;
							gameNumber++;
						}
					}
				}
				else{
				if (bowlerIterator.hasNext()) {
					currentThrower = (Bowler)bowlerIterator.next();

					canThrowAgain = true;
					tenthFrameStrike = false;
					ball = 0;
					while (canThrowAgain) {
						try {
							sleep(10);
						} catch (Exception e) {}
						if(throw_){
							setter.ballThrown();
							ball++;
							throw_=false;
						}
					}

					if (frameNumber == 9){
						scoreCalculation.finalScores[bowlIndex][gameNumber] = scoreCalculation.cumulScores[bowlIndex][9];
						try{
							Date date = new Date();
							String dateString = "" + date.getHours() + ":" + date.getMinutes() + " " + date.getMonth() + "/" + date.getDay() + "/" + (date.getYear() + 1900);
							ScoreHistoryFile.addScore(currentThrower.getNickName(), dateString, new Integer(scoreCalculation.cumulScores[bowlIndex][9]).toString());
						} catch (Exception e) {System.err.println("Exception in addScore. "+ e );}
					}


					setter.reset();
					bowlIndex++;

				} else {
					frameNumber++;
					resetBowlerIterator();
					bowlIndex = 0;
					if (frameNumber > 9) {
						gameFinished = true;
						gameNumber++;
					}
				}
			}}
				else if (scoreCalculation.partyAssigned && gameFinished) {
				boolean temp = false;
				if (scoreCalculation.party.getPartySize() >= 2 && give_chance == false) {
					Vector<Bowler> top_2_players = scoreCalculation.get_Top2_players();
					first = top_2_players.firstElement();
					second = top_2_players.lastElement();

					ChanceToRunnerUpPrompt chance = new ChanceToRunnerUpPrompt(((Bowler) scoreCalculation.party.getMembers().get(0)).getNickName(), second.getNickName(), first.getNickName());
					int result = chance.getResult();
					chance.destroy();

					System.out.println("result was: " + result);

					if (result == 1) {                    // yes, Give second highest 1 chance,
						System.out.println("Giving chance\n");

						scoreCalculation.resetScores(scoreCalculation.party);
						gameFinished = false;
						gameNumber -= 1;    // Reverting the gameNumber++ from checkGameFinished
						frameNumber = 0;
						give_chance = true;
						temp = true;
						scoreCalculation.party.resetBowlers(this, top_2_players);
					}
				}
				if (!temp) {
					EndGamePrompt egp = new EndGamePrompt(((Bowler) scoreCalculation.party.getMembers().get(0)).getNickName() + "'s Party");
					int result = egp.getResult();
					egp.distroy();
					egp = null;


					System.out.println("result was: " + result);

					// TODO: send record of scores to control desk
					if (result == 1) {                    // yes, want to play again
						scoreCalculation.resetScores(scoreCalculation.party);
						gameFinished = false;
						frameNumber = 0;
						resetBowlerIterator();

					} else if (result == 2) {// no, dont want to play another game
						Vector printVector;
						EndGameReport egr = new EndGameReport(((Bowler) scoreCalculation.party.getMembers().get(0)).getNickName() + "'s Party", scoreCalculation.party);
						printVector = egr.getResult();
						scoreCalculation.partyAssigned = false;
						Iterator scoreIt = scoreCalculation.party.getMembers().iterator();
						scoreCalculation.party = null;
						scoreCalculation.partyAssigned = false;

						LaneSubscribe.publish(this, lanePublish());
						LaneSubscribe.sendScore(scoreIt, printVector, this);


					}
				}
			}


			try {
				sleep(10);
			} catch (Exception e) {}
		}

	}

	/** recievePinsetterEvent()
	 *
	 * recieves the thrown event from the pinsetter
	 *
	 * @pre none
	 * @post the event has been acted upon if desiered
	 *
	 * @param pe 		The pinsetter event that has been received.
	 */
	public void gaming(){

		throw_=true;
	}

	public void receivePinsetterEvent(PinsetterEvent pe) {

		if (pe.pinsDownOnThisThrow() >=  0) {			// this is a real throw
			scoreCalculation.markScore(this,frameNumber+1, pe.getThrowNumber(), pe.pinsDownOnThisThrow());

			// next logic handles the ?: what conditions dont allow them another throw?
			// handle the case of 10th frame first
			if (frameNumber == 9) {
				if (pe.totalPinsDown() == 10) {
					setter.resetPins();
					if(pe.getThrowNumber() == 1) {
						tenthFrameStrike = true;
					}
				}

				if ((pe.totalPinsDown() != 10) && (pe.getThrowNumber() == 2 && tenthFrameStrike == false)) {
					canThrowAgain = false;
				}

				if (pe.getThrowNumber() == 3) {
					canThrowAgain = false;
				}
			} else { // its not the 10th frame

				if (pe.pinsDownOnThisThrow() == 10) {		// threw a strike
					canThrowAgain = false;
				} else if (pe.getThrowNumber() == 2) {
					canThrowAgain = false;
				} else if (pe.getThrowNumber() == 3)
					System.out.println("I'm here...");
			}
		} else {								//  this is not a real throw, probably a reset
		}
	}

	/** resetBowlerIterator()
	 *
	 * sets the current bower iterator back to the first bowler
	 *
	 * @pre the party as been assigned
	 * @post the iterator points to the first bowler in the party
	 */
	public void resetBowlerIterator() {
		bowlerIterator = (scoreCalculation.party.getMembers()).iterator();
	}


	/** lanePublish()
	 *
	 * Method that creates and returns a newly created laneEvent
	 *
	 * @return		The new lane event
	 */
	public LaneEvent lanePublish(  ) {
		Map<Object,Object> params = new HashMap<Object,Object>();
		params.put("calculateScore",scoreCalculation);
		params.put("bowlIndex",bowlIndex);
		params.put("currentThrower",currentThrower) ;
		params.put("frameNumber",frameNumber+1);
		params.put("ball",ball);
		params.put("gameIsHalted",gameIsHalted);

		return new LaneEvent(params);

	}


	/**
	 * Accessor to get this Lane's pinsetter
	 *
	 * @return		A reference to this lane's pinsetter
	 */

	public Pinsetter getPinsetter() {
		return setter;
	}

	/**
	 * Pause the execution of this game
	 */
	public void pauseGame() {
		gameIsHalted = true;
		LaneSubscribe.publish(this,lanePublish());
	}

	/**
	 * Resume the execution of this game
	 */
	public void unPauseGame() {
		gameIsHalted = false;
		LaneSubscribe.publish(this,lanePublish());
	}

}
