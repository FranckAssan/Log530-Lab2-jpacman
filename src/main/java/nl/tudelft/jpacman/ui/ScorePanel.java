package nl.tudelft.jpacman.ui;

import java.awt.GridLayout;
import java.util.*;

import javax.swing.*;

import nl.tudelft.jpacman.level.Player;

/**
 * A panel consisting of a column for each player, with the numbered players on
 * top and their respective scores underneath.
 *
 * @author Jeroen Roosen 
 *
 */
public class ScorePanel extends JPanel {

    /**
     * Default serialisation ID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The map of players and the labels their scores are on.
     */
    private final Map<Player, List<JLabel>> scoreLabels;

//    private final Map<Player, JLabel> nbDeVieLabels;

    /**
     * The default way in which the score is shown.
     */
    public static final ScoreFormatter DEFAULT_SCORE_FORMATTER =
        (Player player) -> String.format("Score: %3d", player.getScore());

    public static final ScoreFormatter DEFAULT_VIE_FORMATTER =
        (Player player) -> String.format("Vie: %3d", player.getNbVies());

    /**
     * The way to format the score information.
     */
    private ScoreFormatter scoreFormatter = DEFAULT_SCORE_FORMATTER;
    private ScoreFormatter vieFormatter = DEFAULT_VIE_FORMATTER;

    /**
     * Creates a new score panel with a column for each player.
     *
     * @param players
     *            The players to display the scores of.
     */
    public ScorePanel(List<Player> players) {
        super();
        assert players != null;

        setLayout(new GridLayout(3, players.size()));

        for (int i = 1; i <= players.size(); i++) {
            add(new JLabel("Player " + i, SwingConstants.CENTER));
        }

        scoreLabels = new LinkedHashMap<>();

        for (Player player : players) {
            JLabel scoreLabel = new JLabel("0", SwingConstants.CENTER);
            JLabel vieLabel = new JLabel(String.valueOf(player.getNbVies()),
                SwingConstants.CENTER);

            List<JLabel> labelList = new LinkedList<>();
            labelList.add(scoreLabel);
            labelList.add(vieLabel);
            scoreLabels.put(player, labelList);
            add(scoreLabel);
            add(vieLabel);
        }

    }

    /**
     * Refreshes the scores of the players.
     */
    protected void refresh() {
        for (Map.Entry<Player, List<JLabel>> entry : scoreLabels.entrySet()) {
            Player player = entry.getKey();
            String score = "";
            if (!player.isAlive()) {
                score = "You died. ";
            }
            score += scoreFormatter.format(player);
            entry.getValue().get(0).setText(score);

            String vie = "";
            vie += vieFormatter.format(player);
            entry.getValue().get(1).setText(vie);
        }
    }

    /**
     * Provide means to format the score for a given player.
     */
    public interface ScoreFormatter {

        /**
         * Format the score of a given player.
         * @param player The player and its score
         * @return Formatted score.
         */
        String format(Player player);
    }


    /**
     * Let the score panel use a dedicated score formatter.
     * @param scoreFormatter Score formatter to be used.
     */
    public void setScoreFormatter(ScoreFormatter scoreFormatter) {
        assert scoreFormatter != null;
        this.scoreFormatter = scoreFormatter;
    }
}
