import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import javax.swing.border.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.util.*;
import java.io.*;

public class QueryView implements ListSelectionListener
{
    private final JFrame wndw;
    private String targetnick;
    private Vector bowlerdb;
    private JList allBowlers,resultList;
    private JButton maxScore,minScore,avg,numPlayers,tp;
    private final JPanel ResultsPanel;
    QueryView()
    {
        wndw = BuildGeneralComponents.createWindow("Queries");
        JPanel queryPanel = new JPanel();
        queryPanel.setLayout(new BorderLayout());

        JPanel BowlersPanel = BuildGeneralComponents.createFlowPanel();
        BowlersPanel.setBorder(new TitledBorder("BOWLERS"));

        try {
            bowlerdb = new Vector(BowlerFile.getBowlers());
        } catch (Exception e) {
            System.err.println("File Error");
            bowlerdb = new Vector();
        }
        allBowlers = new JList(bowlerdb);
        allBowlers.setVisibleRowCount(8);
        allBowlers.setFixedCellWidth(120);
        JScrollPane bowlerPane = new JScrollPane(allBowlers);
        bowlerPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        allBowlers.addListSelectionListener(this);
        BowlersPanel.add(bowlerPane);


        ResultsPanel = BuildGeneralComponents.createFlowPanel();
        ResultsPanel.setBorder(new TitledBorder("RESULTS"));

//        party = new Vector();
        Vector empty = new Vector();
        empty.add("(Empty)");

        resultList = new JList(empty);
        resultList.setFixedCellWidth(200);
        resultList.setVisibleRowCount(8);
//        resultList.addListSelectionListener(this);
        JScrollPane resultPane = new JScrollPane(resultList);
        ResultsPanel.add(resultPane);


        JPanel ButtonPanel = BuildGeneralComponents.createGridPanel(5,1);
        ButtonPanel.setBorder(new TitledBorder("BUTTONS"));

        maxScore =  BuildGeneralComponents.createButton("Max Score",ButtonPanel);
        maxScore.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (targetnick != null)
                {
                    Vector temp=null;
                    try{
                        temp=ScoreHistoryFile.getMaxAndMInScoresOfPlayer(targetnick);
                    }
                    catch (Exception exp){System.err.println("Error: " + exp);}

                    Vector<String> ansVector = new Vector<>();
                    ansVector.add("Player: " + targetnick);
                    ansVector.add("Highest score: " + temp.get(0));
                    resultList.setListData(ansVector);
                }
                else
                {
                    Vector<String> ansVector = new Vector<>();
                    ansVector.add("Select a player");
                    resultList.setListData(ansVector);
                }
            }
        });
        minScore =  BuildGeneralComponents.createButton("Lowest Score",ButtonPanel);
        minScore.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (targetnick != null)
                {
                    Vector temp=null;
                    try{
                        temp=ScoreHistoryFile.getMaxAndMInScoresOfPlayer(targetnick);
                    }
                    catch (Exception exp){System.err.println("Error: " + exp);}

                    Vector<String> ansVector = new Vector<>();
                    ansVector.add("Player: " + targetnick);
                    ansVector.add("Lowest score: " + temp.get(1));
                    resultList.setListData(ansVector);
                }
                else
                {
                    Vector<String> ansVector = new Vector<>();
                    ansVector.add("Select a player");
                    resultList.setListData(ansVector);
                }
            }
        });
        avg =  BuildGeneralComponents.createButton("Average Score",ButtonPanel);
        avg.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (targetnick != null) {
                    Vector scores = null;
                    try{
                        scores = ScoreHistoryFile.getScores(targetnick);
                    } catch (Exception exp){System.err.println("Error: " + exp);}

                    assert scores != null;
                    Iterator scoreIt = scores.iterator();

                    double sum = 0, count = 0;
                    while (scoreIt.hasNext()) {
                        Score score = (Score) scoreIt.next();
                        sum = sum + (double)Integer.parseInt(score.getScore());
                        count = count + 1.0;
                    }
                    double averagevalue=sum/count;
                    averagevalue = Math.round(averagevalue*100.0)/100.0;
                    Vector<String> ansVector = new Vector<>();
                    ansVector.add("Player: " + targetnick);
                    ansVector.add("Average score: " + averagevalue);
                    resultList.setListData(ansVector);
                }
                else
                {
                    Vector<String> ansVector = new Vector<>();
                    ansVector.add("Select a player");
                    resultList.setListData(ansVector);
                }
            }
        });
        tp =  BuildGeneralComponents.createButton("Top Player",ButtonPanel);
        tp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Vector temp=null;
                try{
                    temp=ScoreHistoryFile.getTopPlayer();
                }
                catch (Exception exp){System.err.println("Error: " + exp);}
                Vector<String> ansVector = new Vector<>();
                ansVector.add("Top Player: " + temp.get(0));
                ansVector.add(temp.get(0)+ " highest score is " + temp.get(1));
                resultList.setListData(ansVector);
            }
        });
        numPlayers =  BuildGeneralComponents.createButton("Number of Bowlers",ButtonPanel);
        numPlayers.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int k=0;
                try{
                    k=BowlerFile.numBowlers();
                } catch (Exception exp){System.err.println("Error: " + exp);}
                Vector<String> ansVector = new Vector<>();
                ansVector.add("Number of Bowlers are: " + k);
                resultList.setListData(ansVector);
            }
        });

        queryPanel.add(BowlersPanel, "West");
        queryPanel.add(ResultsPanel,"Center");
        queryPanel.add(ButtonPanel, "East");

        wndw.getContentPane().add("Center", queryPanel);
        wndw.pack();

        BuildGeneralComponents.setWindow(wndw);
    }

    public void valueChanged(ListSelectionEvent e) {
        if (e.getSource().equals(allBowlers)) {
            targetnick = ((String) ((JList) e.getSource()).getSelectedValue());
        }
    }

}

