import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * JeuCartes définit un jeu de cartes avec plusieurs de familles de cartes,
 * avec pour chaque famille le même nombre de cartes.
 *
 * @param <C> type des cartes du jeu
 * @author Mathias Bigaignon
 * @version 1.1
 */
public class JeuCartes<C> implements Iterable<C> {
    private List<C> cartes;

    JeuCartes() {
        this.cartes = new ArrayList<>();
    }

    void ajouter(C c) {
        this.cartes.add(c);
    }

    //@ pure
    public String toString() {
        return String.valueOf(this.cartes);
    }

    void battre() {
        Collections.shuffle(this.cartes);
    }

    @Override
    public Iterator<C> iterator() {
        return this.cartes.iterator();
    }

}
