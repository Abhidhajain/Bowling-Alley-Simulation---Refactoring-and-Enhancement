import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

public class ScoreCalculation implements  Serializable {
    public Party party;
    public HashMap scores;
    public boolean partyAssigned;
    public boolean gutter;
    public int[] curScores;
    public int[][] cumulScores;
    public int[][] finalScores;

    public ScoreCalculation(){
        scores = new HashMap();
        partyAssigned = false;
        gutter=false;
    }

    public Party getParty() {
        return party;
    }

    /**
     * getScore()
     * <p>
     * Method that calculates a bowlers score
     *
     * @param Cur   The bowler that is currently up
     * @param frame The frame the current bowler is on
     * @return The bowlers total score
     */
    public boolean isSpare(int[] curScore,int i,int current){
        return (i % 2 == 1 && curScore[i - 1] + curScore[i] == 10 && i < current - 1);
    }
    public boolean isStrike(int[] curScore,int i,int current){
        return (i < current && i % 2 == 0 && curScore[i] == 10);
    }

    public void calcStrike(int i, int bowlIndex, int[] curScore,Bowler Cur) {
        cumulScores[bowlIndex][i / 2] += 10;
        if (curScore[i + 1] != -1) {
            cumulScores[bowlIndex][i / 2] += curScore[i + 1] + curScore[i + 2] + cumulScores[bowlIndex][(i / 2) - 1];
        } else {
            cumulScores[bowlIndex][i / 2] += curScore[i + 2];
            if (i / 2 > 0) {
                cumulScores[bowlIndex][i / 2] += cumulScores[bowlIndex][(i / 2) - 1];
            }
            if (curScore[i + 3] != -1) {
                cumulScores[bowlIndex][(i / 2)] += curScore[i + 3];
            } else {
                cumulScores[bowlIndex][(i / 2)] += curScore[i + 4];
            }
        }
    }

    public int getScore(Bowler Cur, int frame, int bowlIndex, int ball) {
        int[] curScore;
        int strikeballs = 0;
        int totalScore = 0;
        curScore = (int[]) scores.get(Cur);
        int current = 2*frame+ball-1;
        for (int i = 0; i != 10; i++) {
            cumulScores[bowlIndex][i] = 0;
        }
        for (int i = 0; i != current + 2; i++) {
            //Spare:
            if (isSpare(curScore,i,current)) {
                System.out.println("SPARE");
                cumulScores[bowlIndex][(i / 2)] += curScore[i + 1] + curScore[i];
            }
            else if (i > 18) {
                cumulScores[bowlIndex][9] += curScore[i];
            }
            else if (i == 18) {
                cumulScores[bowlIndex][9] += cumulScores[bowlIndex][8] + curScore[i];
            }//strike:
            else if (isStrike(curScore,i,current))
            {
                System.out.println("STRIKE");
                boolean canStrike=false;
                 if (curScore[i + 2] != -1)
                 {
                    if (curScore[i + 3] != -1 || curScore[i + 4] != -1)
                    {
                        calcStrike(i, bowlIndex, curScore,Cur);
                    }
                    else if(canStrike)
                    {
                        cumulScores[bowlIndex][i / 2] += 10;
                        if (curScore[i + 1] != -1) {
                            cumulScores[bowlIndex][i / 2] += curScore[i + 1] + curScore[i + 2] + cumulScores[bowlIndex][(i / 2) - 1];
                        } else {
                            cumulScores[bowlIndex][i / 2] += curScore[i + 2];
                            if (i / 2 > 0) {
                                cumulScores[bowlIndex][i / 2] += cumulScores[bowlIndex][(i / 2) - 1];
                            }
                            if (curScore[i + 3] != -1) {
                                cumulScores[bowlIndex][(i / 2)] += curScore[i + 3];
                            } else {
                                cumulScores[bowlIndex][(i / 2)] += curScore[i + 4];
                            }
                        }
                    }
                    else
                    {
                        break;
                    }
                 }
                 else
                 {
                    break;
                 }
            }//gutter
            else if(gutter)
            {
                if(i==2 && curScore[0]==0 && curScore[1]==0)
                {
                    System.out.println("Penalty for Gutters: " + curScore[2]/2);
                    cumulScores[bowlIndex][(i / 2)] -= curScore[2]/2;
                }
                else
                {
                    int highScore = Integer.MIN_VALUE;
                    System.out.println("Penalty for Gutter");
                    for (int j=0; j<10; ++j){
                        if (cumulScores[bowlIndex][j] > highScore) {
                            highScore = cumulScores[bowlIndex][j];
                        }
                    }
                    cumulScores[bowlIndex][(i / 2)] -= highScore/2;
                }

            } //normalThrow:
            else {
                if (i == 0) {
                    cumulScores[bowlIndex][i / 2] += curScore[i];
                }
                else if (i % 2 == 0) {
                    cumulScores[bowlIndex][i / 2] += cumulScores[bowlIndex][i / 2 - 1] + curScore[i];
                }
                else if (curScore[i] != -1) {
                    cumulScores[bowlIndex][i / 2] += curScore[i];
                }
            }
//            curScore[0]=0;
//            curScore[1]=0;
            if((i%2==1 && curScore[i-1]+curScore[i]==0 && i<current-1))
                gutter = true;
            else
                gutter=false;
        }
        return totalScore;
    }

    /**
     * resetScores()
     * <p>
     * resets the scoring mechanism, must be called before scoring starts
     *
     * @pre the party has been assigned
     * @post scoring system is initialized
     */
    public Vector get_Top2_players(){
        System.out.println(Arrays.deepToString(cumulScores));
        Vector<Bowler> vec = new Vector<Bowler>();
        int max = -100, max_ind = -1 ;
        for(int i = 0; i < party.getPartySize() ; i++){
            if(cumulScores[i][9] > max){
                max = cumulScores[i][9];
                max_ind = i;
            }
        }
        int smax = -100, smax_ind = -1;
        for(int i = 0; i < party.getPartySize() ; i++){
            if(cumulScores[i][9] > smax && i != max_ind){
                smax = cumulScores[i][9];
                smax_ind = i;
            }
        }
        System.out.println(max_ind+" "+smax_ind);
        vec.add((Bowler)party.getMembers().get(max_ind));
        vec.add((Bowler)party.getMembers().get(smax_ind));
        return vec;
    }
    public void resetScores(Party party) {
        Iterator bowlIt = (party.getMembers()).iterator();

        while (bowlIt.hasNext()) {
            int[] toPut = new int[25];
            for (int i = 0; i != 25; i++) {
                toPut[i] = -1;
            }
            scores.put(bowlIt.next(), toPut);
        }
    }


    /**
     * markScore()
     * <p>
     * Method that marks a bowlers score on the board.
     *
     * @param lane		The current lane
     * @param frameNumber  the frame number
     * @param ball		The ball the bowler is on
     * @param score	The bowler's score
     */
    public void markScore(Lane lane, int frameNumber, int ball, int score) {
        int[] curScore;
        int index = (lane.frameNumber*2 + ball);

        curScore = (int[]) scores.get(lane.currentThrower);
        curScore[index - 1] = score;
        scores.put(lane.currentThrower, curScore);
        getScore(lane.currentThrower, lane.frameNumber, lane.bowlIndex, ball);
        LaneSubscribe.publish(lane, lane.lanePublish());
    }
    /** isPartyAssigned()
     *
     * checks if a party is assigned to this lane
     *
     * @return true if party assigned, false otherwise
     */
    public boolean isPartyAssigned() {
        return partyAssigned;
    }
}



