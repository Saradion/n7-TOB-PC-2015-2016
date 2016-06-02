import java.util.HashMap;
import java.util.Map;

/**
 * Created by bigaigm on 13/04/16.
 *
 * @author Mathias Bigaignon
 * @version 1.0
 */
class ScoresMemory implements Scores{
    private Map<String, Integer> scores;

    ScoresMemory() {
        this.scores = new HashMap<>();
    }

    public void register(Joueur j) {
        this.scores.put(j.getNom(), 0);
    }

    public void increment(String joueur) {
        this.scores.put(joueur, this.scores.get(joueur) + 1);
    }

    public int getScore(String joueur) {
        return this.scores.get(joueur);
    }

    public void remove(String joueur) {
        this.scores.remove(joueur);
    }

    @Override
    public String toString() {
        return scores.toString();
    }
}
