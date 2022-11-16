/**
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */

import java.util.*;
import java.io.*;

public class ScoreHistoryFile {

	private static String SCOREHISTORY_DAT = "SCOREHISTORY.DAT";

	public static void addScore(String nick, String date, String score)
		throws IOException, FileNotFoundException {

		String data = nick + "\t" + date + "\t" + score + "\n";

		RandomAccessFile out = new RandomAccessFile(SCOREHISTORY_DAT, "rw");
		out.skipBytes((int) out.length());
		out.writeBytes(data);
		out.close();
	}

	public static Vector getScores(String nick)
		throws IOException, FileNotFoundException {
		Vector scores = new Vector();

		BufferedReader in =
			new BufferedReader(new FileReader(SCOREHISTORY_DAT));
		String data;
		while ((data = in.readLine()) != null) {
			// File format is nick\tfname\te-mail
			String[] scoredata = data.split("\t");
			//"Nick: scoredata[0] Date: scoredata[1] Score: scoredata[2]
			if (nick.equals(scoredata[0])) {
				scores.add(new Score(scoredata[0], scoredata[1], scoredata[2]));
			}
		}
		return scores;
	}

	public static Vector getMaxAndMInScoresOfPlayer(String targetnick) throws IOException {
		Vector info = new Vector();
		BufferedReader in = new BufferedReader(new FileReader(SCOREHISTORY_DAT));
		String msg;
		while ((msg = in.readLine()) != null) {
			String[] scoredata = msg.split("\t");
			info.add(new Score(scoredata[0], scoredata[1], scoredata[2]));
		}

		Iterator infoIter = info.iterator();
		int score_max=Integer.MIN_VALUE;
		int score_min=Integer.MAX_VALUE;
		while (infoIter.hasNext()) {
			Score score = (Score) infoIter.next();
			if (score.getNick().equals(targetnick))
			{
				int currScore = Integer.parseInt(score.getScore());
				score_max=Math.max(currScore,score_max);
				score_min=Math.min(currScore,score_min);
			}
		}
		Vector res = new Vector();
		res.add(score_max);
		res.add(score_min);
		return res;
	}
	public static Vector getTopPlayer() throws IOException
	{
		Vector info = new Vector();
		BufferedReader in = new BufferedReader(new FileReader(SCOREHISTORY_DAT));
		String msg;
		while ((msg = in.readLine()) != null) {
			String[] scoredata = msg.split("\t");
			info.add(new Score(scoredata[0], scoredata[1], scoredata[2]));
		}
		String str="";
		Iterator infoIter = info.iterator();
		int score_max=Integer.MIN_VALUE;
		while (infoIter.hasNext()) {
			Score score = (Score) infoIter.next();
			int currScore = Integer.parseInt(score.getScore());
			if(currScore>score_max)
			{
				score_max=Math.max(currScore,score_max);
				str=score.getNick();
			}
		}
		Vector ans = new Vector();
		ans.add(str);
		ans.add(score_max);
		return ans;
	}


}
