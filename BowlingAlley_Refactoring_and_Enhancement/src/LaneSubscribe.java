import java.io.Serializable;
import java.util.Iterator;
import java.util.Vector;

public class LaneSubscribe implements Serializable {
    /** subscribe
     *
     * Method that will add a subscriber
     *
     * @param adding Observer that is to be added
     */
    public static void subscribe(Lane lane, LaneObserver adding ) {
        lane.subscribers.add( adding );
    }

    /** publish
     *
     * Method that publishes an event to subscribers
     *
     * @param event	Event that is to be published
     */
    public static void publish(Lane lane, LaneEvent event ) {
        if( lane.subscribers.size() > 0 ) {
            Iterator eventIterator = lane.subscribers.iterator();

            while ( eventIterator.hasNext() ) {
                ( (LaneObserver) eventIterator.next()).receiveLaneEvent( event );
            }
        }
    }
    public static void sendScore(Iterator scoreIt, Vector printVector, Lane lane){
        int myIndex = 0;
        while (scoreIt.hasNext()){
            Bowler thisBowler = (Bowler)scoreIt.next();
            ScoreReport sr = new ScoreReport( thisBowler, lane.scoreCalculation.finalScores[myIndex++], lane.gameNumber );
            sr.sendEmail(thisBowler.getEmail());
            Iterator printIt = printVector.iterator();
            while (printIt.hasNext()){
                if (thisBowler.getNick() == (String)printIt.next()){
                    System.out.println("Printing " + thisBowler.getNick());
                    sr.sendPrintout();
                }
            }

        }
    }
}

